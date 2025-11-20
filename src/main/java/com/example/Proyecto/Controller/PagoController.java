package com.example.Proyecto.Controller;

import com.example.Proyecto.Model.Pago;
import com.example.Proyecto.Model.Pedido;
import com.example.Proyecto.Repository.PagoRepository;
import com.example.Proyecto.Repository.PedidoRepository;
import com.example.Proyecto.Service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;


    @GetMapping
    public List<Pago> getAll() {
        return pagoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getById(@PathVariable Long id) {
        Pago p = pagoService.findById(id); // ✅ Corregido
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> update(@PathVariable Long id, @RequestBody Pago p) {
        if (pagoService.findById(id) == null) return ResponseEntity.notFound().build(); // ✅ Corregido
        p.setPagoId(id);
        return ResponseEntity.ok(pagoService.save(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> registrarPago(@RequestBody Pago pago) {
        try {
            System.out.println("📥 Recibiendo pago - Pedido ID: " + pago.getPedido().getPedidoId());
            Pago pagoGuardado = pagoService.registrarPago(pago);
            System.out.println("✅ Pago guardado exitosamente - ID: " + pagoGuardado.getPagoId());
            return ResponseEntity.ok(pagoGuardado);
        } catch (Exception e) {
            System.out.println("❌ Error al registrar pago: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }
}
