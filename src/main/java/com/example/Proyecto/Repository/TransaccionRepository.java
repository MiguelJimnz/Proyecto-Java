package com.example.Proyecto.Repository;

import com.example.Proyecto.Model.TipoTransaccion;
import com.example.Proyecto.Model.Transacciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transacciones, Long> {
    List<Transacciones> findByOrderByFechaDesc();

    List<Transacciones> findByTipoOrderByFechaDesc(TipoTransaccion tipo);

    List<Transacciones> findByFechaBetweenOrderByFechaDesc(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT t FROM Transacciones t LEFT JOIN FETCH t.empleado LEFT JOIN FETCH t.pago ORDER BY t.fecha DESC")
    List<Transacciones> findAllWithRelaciones();
}
