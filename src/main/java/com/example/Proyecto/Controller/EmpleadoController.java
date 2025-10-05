package com.example.Proyecto.Controller;

import com.example.Proyecto.Model.Empleado;
import com.example.Proyecto.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService service;

    @GetMapping
    public List<Empleado> getAll() {
        return service.findAll();
    }
    @PostMapping
    public Empleado create(@RequestBody Empleado e) {
        return service.save(e);
    }
    @GetMapping("/{id}") public ResponseEntity<Empleado> getById(@PathVariable Long id) {
        Empleado e = service.findById(id);
        return e != null ? ResponseEntity.ok(e) : ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}") public ResponseEntity<Empleado> update(@PathVariable Long id, @RequestBody Empleado e) {
        if (service.findById(id) == null) return ResponseEntity.notFound().build();
        e.setEmpleadoId(id);; return ResponseEntity.ok(service.save(e));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
