package com.example.Proyecto.Controller;

import com.example.Proyecto.Model.MaterialUsado;
import com.example.Proyecto.Repository.MaterialUsadoRepository;
import com.example.Proyecto.Service.MaterialUsadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materialusado")
public class MaterialUsadoController {
    @Autowired
    private MaterialUsadoService service;

    @GetMapping public List<MaterialUsado> getAll() { return service.findAll(); }
    @PostMapping public MaterialUsado create(@RequestBody MaterialUsado m) { return service.save(m); }
    @GetMapping("/{id}") public ResponseEntity<MaterialUsado> getById(@PathVariable Long id) {
        MaterialUsado m = service.findById(id);
        return m != null ? ResponseEntity.ok(m) : ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}") public ResponseEntity<MaterialUsado> update(@PathVariable Long id, @RequestBody MaterialUsado m) {
        if (service.findById(id) == null) return ResponseEntity.notFound().build();
        m.setUsoId(id); return ResponseEntity.ok(service.save(m));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
