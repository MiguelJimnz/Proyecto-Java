package com.example.Proyecto.Repository;

import com.example.Proyecto.Model.HorasTrabajadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HorasTrabajadasRepository extends JpaRepository<HorasTrabajadas, Long> {
    // Encontrar horas por empleado
    List<HorasTrabajadas> findByEmpleadoEmpleadoId(Long empleadoId);

    // Encontrar horas por pedido
    List<HorasTrabajadas> findByPedidoPedidoId(Long pedidoId);

    // Encontrar horas por rango de fechas
    List<HorasTrabajadas> findByFechaBetween(LocalDate inicio, LocalDate fin);

    // Encontrar horas por empleado y rango de fechas
    List<HorasTrabajadas> findByEmpleadoEmpleadoIdAndFechaBetween(Long empleadoId, LocalDate inicio, LocalDate fin);

    // Encontrar horas no verificadas
    @Query("SELECT h FROM HorasTrabajadas h WHERE h.verificado = false")
    List<HorasTrabajadas> findHorasNoVerificadas();

    // Encontrar horas por tipo de tarea
    List<HorasTrabajadas> findByTipoTarea(String tipoTarea);

    // Resumen de horas por empleado en un período
    @Query("SELECT h.empleado.empleadoId, SUM(h.totalHoras) FROM HorasTrabajadas h " +
            "WHERE h.fecha BETWEEN :inicio AND :fin GROUP BY h.empleado.empleadoId")
    List<Object[]> findResumenHorasPorEmpleado(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    // Horas trabajadas hoy
    @Query("SELECT h FROM HorasTrabajadas h WHERE h.fecha = CURRENT_DATE")
    List<HorasTrabajadas> findHorasDeHoy();
}
