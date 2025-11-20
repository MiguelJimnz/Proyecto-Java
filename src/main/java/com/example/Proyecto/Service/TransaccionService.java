package com.example.Proyecto.Service;

import com.example.Proyecto.Model.Pago;
import com.example.Proyecto.Model.TipoTransaccion;
import com.example.Proyecto.Model.Transacciones;
import com.example.Proyecto.Repository.PagoRepository;
import com.example.Proyecto.Repository.TransaccionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;




@Service
public class TransaccionService {
    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Transactional
    public void registrarTransaccionDesdePago(Pago pago) {
        System.out.println("💰 [TransaccionService] Registrando transacción desde pago ID: " + pago.getPagoId());

        try {
            if (pago.getPagoId() == null) {
                throw new RuntimeException("El pago debe tener un ID antes de crear la transacción");
            }

            Transacciones transaccion = new Transacciones();
            transaccion.setTipo(TipoTransaccion.INGRESO);
            transaccion.setDescripcion("Pago de pedido #" + pago.getPedido().getPedidoId() + " - Método: " + pago.getMetodo());
            transaccion.setMonto(pago.getMonto());
            transaccion.setFecha(LocalDateTime.now());
            transaccion.setPago(pago);

            // Si hay empleado en el pedido, asociarlo
            if (pago.getPedido() != null && pago.getPedido().getEmpleado() != null) {
                transaccion.setEmpleado(pago.getPedido().getEmpleado());
            }

            System.out.println("📝 Datos de la transacción:");
            System.out.println("   - Tipo: " + transaccion.getTipo());
            System.out.println("   - Descripción: " + transaccion.getDescripcion());
            System.out.println("   - Monto: $" + transaccion.getMonto());
            System.out.println("   - Pago ID: " + transaccion.getPago().getPagoId());

            Transacciones guardada = transaccionRepository.save(transaccion);
            System.out.println("✅ Transacción guardada exitosamente - ID: " + guardada.getIdTransaccion());

        } catch (Exception e) {
            System.out.println("❌ ERROR en registrarTransaccionDesdePago: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al crear transacción: " + e.getMessage(), e);
        }
    }

    public List<Transacciones> obtenerTodasLasTransacciones() {
        System.out.println("📋 Obteniendo todas las transacciones...");
        List<Transacciones> transacciones = transaccionRepository.findAllWithRelaciones();
        System.out.println("✅ Transacciones encontradas: " + transacciones.size());
        return transacciones;
    }

    @Transactional
    public Transacciones registrarTransaccion(Transacciones transaccion) {
        if (transaccion.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }

        if (transaccion.getFecha() == null) {
            transaccion.setFecha(LocalDateTime.now());
        }

        return transaccionRepository.save(transaccion);
    }

    public Map<String, Object> obtenerResumenTransacciones(LocalDate inicio, LocalDate fin) {
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime finDateTime = fin.atTime(23, 59, 59);

        List<Transacciones> transacciones = transaccionRepository
                .findByFechaBetweenOrderByFechaDesc(inicioDateTime, finDateTime);

        double totalIngresos = transacciones.stream()
                .filter(t -> t.getTipo() == TipoTransaccion.INGRESO)
                .mapToDouble(Transacciones::getMonto)
                .sum();

        double totalEgresos = transacciones.stream()
                .filter(t -> t.getTipo() == TipoTransaccion.EGRESO)
                .mapToDouble(Transacciones::getMonto)
                .sum();

        double balance = totalIngresos - totalEgresos;

        return Map.of(
                "totalIngresos", totalIngresos,
                "totalEgresos", totalEgresos,
                "balance", balance,
                "totalTransacciones", transacciones.size(),
                "transacciones", transacciones
        );
    }
}
