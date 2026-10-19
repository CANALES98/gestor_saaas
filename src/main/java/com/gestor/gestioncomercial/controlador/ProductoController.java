package com.gestor.gestioncomercial.controlador;

import com.gestor.gestioncomercial.entidad.Categoria;
import com.gestor.gestioncomercial.entidad.Producto;
import com.gestor.gestioncomercial.repositorio.CategoriaRepository;
import com.gestor.gestioncomercial.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Listar todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Registrar un nuevo producto
    @PostMapping
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto) {
        // Validar que la categoría enviada exista
        if (producto.getCategoria() == null || producto.getCategoria().getIdCategoria() == null) {
            return ResponseEntity.badRequest().body("Debe especificar el ID de la categoría.");
        }

        Categoria categoriaExistente = categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                .orElse(null);

        if (categoriaExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La categoría especificada no existe.");
        }

        producto.setCategoria(categoriaExistente);
        Producto nuevoProducto = productoRepository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }
}