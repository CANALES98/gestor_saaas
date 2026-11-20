package com.gestor.gestioncomercial.controlador;

import com.gestor.gestioncomercial.dto.MesaRequestDTO;
import com.gestor.gestioncomercial.dto.MesaResponseDTO;
import com.gestor.gestioncomercial.servicio.MesaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    public ResponseEntity<List<MesaResponseDTO>> listarMesas() {
        return ResponseEntity.ok(mesaService.listarMesas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mesaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<MesaResponseDTO> crearMesa(@RequestBody MesaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaService.crearMesa(request));
    }


    @PatchMapping("/{id}/estado")
    public ResponseEntity<MesaResponseDTO> cambiarEstadoMesa(@PathVariable Long id,
                                                             @RequestParam String estadoMesa) {
        return ResponseEntity.ok(mesaService.cambiarEstadoMesa(id, estadoMesa));
    }
}