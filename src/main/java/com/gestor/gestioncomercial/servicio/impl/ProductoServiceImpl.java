package com.gestor.gestioncomercial.servicio.impl;

import com.gestor.gestioncomercial.entidad.Categoria;
import com.gestor.gestioncomercial.entidad.Producto;
import com.gestor.gestioncomercial.repositorio.CategoriaRepository;
import com.gestor.gestioncomercial.repositorio.ProductoRepository;
import com.gestor.gestioncomercial.servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto guardar(Producto producto) {
        // Validar que la categoría exista antes de guardar el producto
        if (producto.getCategoria() != null && producto.getCategoria().getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                    .orElseThrow(() -> new RuntimeException("La categoría especificada no existe"));
            producto.setCategoria(categoria);
        }
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void reducirStock(Long idProducto, Integer cantidad) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }
}