package com.gestor.gestioncomercial.servicio;

import com.gestor.gestioncomercial.dto.ProductoRequestDTO;
import com.gestor.gestioncomercial.dto.ProductoResponseDTO;
import java.util.List;

public interface ProductoService {
    List<ProductoResponseDTO> listarTodos();
    ProductoResponseDTO buscarPorId(Long id);
    ProductoResponseDTO guardar(ProductoRequestDTO productoRequestDTO);
    void reducirStock(Long idProducto, Integer cantidad);
}