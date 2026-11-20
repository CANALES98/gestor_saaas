package com.gestor.gestioncomercial.controlador;

import com.gestor.gestioncomercial.dto.PedidoRequestDTO;
import com.gestor.gestioncomercial.dto.PedidoResponseDTO;
import com.gestor.gestioncomercial.servicio.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@RequestBody PedidoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crearPedido(request));
    }

    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<PedidoResponseDTO> cerrarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.cerrarPedido(id));
    }
}