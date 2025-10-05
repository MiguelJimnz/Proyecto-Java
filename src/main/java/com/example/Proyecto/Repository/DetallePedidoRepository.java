package com.example.Proyecto.Repository;

import com.example.Proyecto.Model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByPedidoPedidoId(Long pedidoId);
}
