package com.gestor.gestioncomercial.servicio;

import com.gestor.gestioncomercial.entidad.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listarTodos();
    Optional<Producto> buscarPorId(Long id);
    Producto guardar(Producto producto);
}