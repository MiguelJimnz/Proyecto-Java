package com.example.Proyecto.Repository;

import com.example.Proyecto.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT p FROM Pedido p WHERE LOWER(p.estado) = LOWER(:estado)")
    List<Pedido> findByEstado(@Param("estado") String estado);
    @Query("SELECT p FROM Pedido p JOIN FETCH p.cliente JOIN FETCH p.empleado")
    List<Pedido> findAllWithClienteAndEmpleado();

    @Modifying
    @Query("UPDATE Pedido p SET p.estado = :estado WHERE p.pedidoId = :pedidoId")
    void actualizarEstado(@Param("pedidoId") Long pedidoId, @Param("estado") String estado);
}
