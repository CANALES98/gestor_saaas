package com.gestor.gestioncomercial.repositorio;

import com.gestor.gestioncomercial.entidad.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar productos activos
    List<Producto> findByEstadoTrue();

    // Buscar por categoría
    List<Producto> findByCategoriaIdCategoria(Long idCategoria);

    // Buscar por código de barras
    Optional<Producto> findByCodigoBarras(String codigoBarras);
}
