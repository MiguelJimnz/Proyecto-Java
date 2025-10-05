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
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    @Autowired
    private PagoService service;
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;


    @GetMapping public List<Pago> getAll() { return service.findAll(); }

    @GetMapping("/{id}") public ResponseEntity<Pago> getById(@PathVariable Long id) {
        Pago p = service.findById(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}") public ResponseEntity<Pago> update(@PathVariable Long id, @RequestBody Pago p) {
        if (service.findById(id) == null) return ResponseEntity.notFound().build();
        p.setPagoId(id); return ResponseEntity.ok(service.save(p));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
    /*@PostMapping("/pagos")
    public Pago registrarPago(@RequestBody Pago pago) {

        Pago pagoGuardado = PagoRepository.save(pago);


        Pedido pedido = pago.getPedido();
        if (pedido != null) {
            pedido.setEstado("Pagado");
            pedidoRepository.save(pedido);
        }

        return pagoGuardado;
    }*/
    @PostMapping
    public Pago registrarPago(@RequestBody Pago pago) {
        System.out.println("ID del pedido recibido en el pago: " + pago.getPedido().getPedidoId());

        Pago pagoGuardado = pagoRepository.save(pago);

        Pedido pedido = pago.getPedido();
        if (pedido != null) {
            Optional<Pedido> pedidoBD = pedidoRepository.findById(pedido.getPedidoId());
            if (pedidoBD.isPresent()) {
                Pedido pedidoActual = pedidoBD.get();
                pedidoActual.setEstado("Pagado");
                pedidoRepository.save(pedidoActual);
            }
        }

        return pagoGuardado;
    }
}
