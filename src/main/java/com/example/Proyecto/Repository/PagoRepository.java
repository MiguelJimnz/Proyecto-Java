package com.example.Proyecto.Repository;

import com.example.Proyecto.Model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByPedidoPedidoId(Long pedidoId);
}