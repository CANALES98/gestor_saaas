package com.gestor.gestioncomercial.servicio;

import com.gestor.gestioncomercial.entidad.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> listarTodas();
    Optional<Categoria> buscarPorId(Long id);
    Categoria guardar(Categoria categoria);
    void eliminar(Long id);
}