package com.gestor.gestioncomercial.servicio;

import com.gestor.gestioncomercial.dto.PedidoRequestDTO;
import com.gestor.gestioncomercial.dto.PedidoResponseDTO;
import java.util.List;

public interface PedidoService {
    List<PedidoResponseDTO> listarPedidos();
    PedidoResponseDTO obtenerPorId(Long idPedido);
    PedidoResponseDTO crearPedido(PedidoRequestDTO request);
    PedidoResponseDTO cerrarPedido(Long idPedido);
}