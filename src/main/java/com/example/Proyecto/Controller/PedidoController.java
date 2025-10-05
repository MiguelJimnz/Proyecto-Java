package com.example.Proyecto.Controller;

import com.example.Proyecto.Model.Pedido;
import com.example.Proyecto.Repository.PedidoRepository;
import com.example.Proyecto.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @GetMapping
    public List<Pedido> listar() {
        return service.obtenerPedidosCompletos();
    }
    @PostMapping
    public Pedido create(@RequestBody Pedido p) {
        return service.save(p);
    }
    @GetMapping("/{id}") public ResponseEntity<Pedido> getById(@PathVariable Long id) {

        Pedido p = service.findById(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}") public ResponseEntity<Pedido> update(@PathVariable Long id, @RequestBody Pedido p) {
        if (service.findById(id) == null) return ResponseEntity.notFound().build();
        p.setPedidoId(id); return ResponseEntity.ok(service.save(p));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
    @GetMapping("/estado/{estado}")
    public List<Pedido> getByEstado(@PathVariable String estado) {
        return service.findByEstado(estado);
    }
}
