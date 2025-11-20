package com.example.Proyecto.Service;

import com.example.Proyecto.Model.Pago;
import com.example.Proyecto.Model.Pedido;
import com.example.Proyecto.Repository.PagoRepository;
import com.example.Proyecto.Repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TransaccionService transaccionService;


    @Autowired
    private PedidoRepository pedidoRepository;
    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    public Pago findById(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        pagoRepository.deleteById(id);
    }

    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Transactional // ✅ CRÍTICO: Asegurar que todo se ejecute en una transacción
    public Pago registrarPago(Pago pago) {
        System.out.println("💰 [INICIO] Registrando pago...");
        System.out.println("   - Pedido ID: " + pago.getPedido().getPedidoId());
        System.out.println("   - Monto: $" + pago.getMonto());

        try {
            // 1. Validar que el pedido existe
            Pedido pedido = pedidoRepository.findById(pago.getPedido().getPedidoId())
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + pago.getPedido().getPedidoId()));

            System.out.println("✅ Pedido encontrado: " + pedido.getPedidoId());

            // 2. Establecer la relación completa
            pago.setPedido(pedido);

            // 3. Guardar el pago
            Pago pagoGuardado = pagoRepository.save(pago);
            System.out.println("✅ Pago guardado - ID: " + pagoGuardado.getPagoId());

            // 4. Actualizar estado del pedido
            pedido.setEstado("Pagado");
            pedidoRepository.save(pedido);
            System.out.println("✅ Estado del pedido actualizado a 'Pagado'");

            // 5. Registrar transacción
            System.out.println("💵 Registrando transacción...");
            transaccionService.registrarTransaccionDesdePago(pagoGuardado);
            System.out.println("✅ Transacción registrada exitosamente");

            return pagoGuardado;

        } catch (Exception e) {
            System.out.println("❌ ERROR en registrarPago: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al registrar pago: " + e.getMessage(), e);
        }
    }

}
