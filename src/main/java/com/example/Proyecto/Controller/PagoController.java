package com.example.Proyecto.Controller;

import com.example.Proyecto.Model.Pago;
import com.example.Proyecto.Model.Pedido;
import com.example.Proyecto.Repository.PagoRepository;
import com.example.Proyecto.Repository.PedidoRepository;
import com.example.Proyecto.Service.PagoService;
import com.example.Proyecto.Service.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    // ✅ Obtener todos los pagos
    @GetMapping
    public ResponseEntity<List<Pago>> getAllPagos() {
        try {
            List<Pago> pagos = pagoService.findAll();
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            System.out.println("❌ Error al obtener pagos: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Obtener un pago por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        try {
            Pago pago = pagoService.findById(id);
            if (pago == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pago);
        } catch (Exception e) {
            System.out.println("❌ Error al obtener pago: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Crear/registrar un nuevo pago
    @PostMapping
    public ResponseEntity<Pago> guardarPago(@RequestBody Pago pago) {
        try {
            System.out.println("💾 Guardando pago para pedido: " + pago.getPedido().getPedidoId());
            Pago pagoGuardado = pagoService.registrarPago(pago);
            return ResponseEntity.ok(pagoGuardado);
        } catch (Exception e) {
            System.out.println("❌ Error al guardar pago: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Obtener pagos por pedido
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<Pago>> getPagosByPedido(@PathVariable Long pedidoId) {
        try {
            // Agregar este método al PagoRepository si no existe:
            // List<Pago> findByPedidoPedidoId(Long pedidoId);

            List<Pago> pagos = pagoService.findByPedido(pedidoId);
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            System.out.println("❌ Error al obtener pagos del pedido: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Eliminar pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        try {
            pagoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar pago: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}

