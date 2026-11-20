package com.gestor.gestioncomercial.repositorio;

import com.gestor.gestioncomercial.entidad.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Optional<Mesa> findByNumero(String numero);
}