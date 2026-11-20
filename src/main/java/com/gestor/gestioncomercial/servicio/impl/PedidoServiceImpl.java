package com.gestor.gestioncomercial.servicio.impl;

import com.gestor.gestioncomercial.dto.DetallePedidoResponseDTO;
import com.gestor.gestioncomercial.dto.PedidoItemRequestDTO;
import com.gestor.gestioncomercial.dto.PedidoRequestDTO;
import com.gestor.gestioncomercial.dto.PedidoResponseDTO;
import com.gestor.gestioncomercial.entidad.DetallePedido;
import com.gestor.gestioncomercial.entidad.Mesa;
import com.gestor.gestioncomercial.entidad.Pedido;
import com.gestor.gestioncomercial.entidad.Producto;
import com.gestor.gestioncomercial.repositorio.MesaRepository;
import com.gestor.gestioncomercial.repositorio.PedidoRepository;
import com.gestor.gestioncomercial.repositorio.ProductoRepository;
import com.gestor.gestioncomercial.servicio.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProductoRepository productoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository,
                             MesaRepository mesaRepository,
                             ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<PedidoResponseDTO> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    @Override
    public PedidoResponseDTO obtenerPorId(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + idPedido));
        return mapearAResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO request) {
        Mesa mesa = mesaRepository.findById(request.getIdMesa())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con id: " + request.getIdMesa()));

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("El pedido debe tener al menos un producto");
        }

        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setEstadoPedido("ABIERTO");

        BigDecimal totalPedido = BigDecimal.ZERO;
        List<DetallePedido> detalles = new ArrayList<>();

        for (PedidoItemRequestDTO item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + item.getIdProducto()));

            int cantidad = item.getCantidad() != null ? item.getCantidad() : 1;
            BigDecimal precioUnitario = producto.getPrecioVenta();
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);

            detalles.add(detalle);
            totalPedido = totalPedido.add(subtotal);
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(totalPedido);

        mesa.setEstadoMesa("OCUPADA");
        mesaRepository.save(mesa);

        Pedido guardado = pedidoRepository.save(pedido);
        return mapearAResponse(guardado);
    }

    @Override
    @Transactional
    public PedidoResponseDTO cerrarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + idPedido));

        pedido.setEstadoPedido("CERRADO");

        Mesa mesa = pedido.getMesa();
        mesa.setEstadoMesa("LIBRE");
        mesaRepository.save(mesa);

        return mapearAResponse(pedidoRepository.save(pedido));
    }

    private PedidoResponseDTO mapearAResponse(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setIdPedido(pedido.getIdPedido());
        dto.setIdMesa(pedido.getMesa().getIdMesa());
        dto.setNumeroMesa(pedido.getMesa().getNumero());
        dto.setFechaCreacion(pedido.getFechaCreacion());
        dto.setEstadoPedido(pedido.getEstadoPedido());
        dto.setTotal(pedido.getTotal());

        List<DetallePedidoResponseDTO> detallesDto = pedido.getDetalles().stream().map(det -> {
            DetallePedidoResponseDTO dDto = new DetallePedidoResponseDTO();
            dDto.setIdDetallePedido(det.getIdDetallePedido());
            dDto.setIdProducto(det.getProducto().getIdProducto());
            dDto.setNombreProducto(det.getProducto().getNombre());
            dDto.setCantidad(det.getCantidad());
            dDto.setPrecioUnitario(det.getPrecioUnitario());
            dDto.setSubtotal(det.getSubtotal());
            return dDto;
        }).toList();

        dto.setDetalles(detallesDto);
        return dto;
    }
}