package com.example.Proyecto.Controller;

import com.example.Proyecto.Model.Inventario;
import com.example.Proyecto.Repository.InventarioRepository;
import com.example.Proyecto.Service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    @Autowired
    private InventarioService service;

    @GetMapping public List<Inventario> getAll() { return service.findAll(); }
    @PostMapping public Inventario create(@RequestBody Inventario i) { return service.save(i); }
    @GetMapping("/{id}") public ResponseEntity<Inventario> getById(@PathVariable Long id) {
        Inventario i = service.findById(id);
        return i != null ? ResponseEntity.ok(i) : ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}") public ResponseEntity<Inventario> update(@PathVariable Long id, @RequestBody Inventario i) {
        if (service.findById(id) == null) return ResponseEntity.notFound().build();
        i.setInventarioId(id); return ResponseEntity.ok(service.save(i));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
