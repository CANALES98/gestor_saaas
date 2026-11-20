package com.gestor.gestioncomercial.repositorio;

import com.gestor.gestioncomercial.entidad.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstadoPedido(String estadoPedido);
    Optional<Pedido> findByMesa_IdMesaAndEstadoPedido(Long idMesa, String estadoPedido);
}