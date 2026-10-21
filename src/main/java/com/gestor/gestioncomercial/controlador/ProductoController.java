package com.gestor.gestioncomercial.controlador;

import com.gestor.gestioncomercial.dto.ProductoRequestDTO;
import com.gestor.gestioncomercial.dto.ProductoResponseDTO;
import com.gestor.gestioncomercial.servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoResponseDTO> listarProductos() {
        return productoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<?> guardarProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        try {
            ProductoResponseDTO nuevoProducto = productoService.guardar(productoRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/reducir-stock")
    public ResponseEntity<?> reducirStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        try {
            productoService.reducirStock(id, cantidad);
            return ResponseEntity.ok("Stock actualizado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}