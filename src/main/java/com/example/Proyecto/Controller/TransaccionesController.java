package com.example.Proyecto.Controller;


import com.example.Proyecto.Model.Transacciones;
import com.example.Proyecto.Repository.TransaccionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionesController {
    private TransaccionRepository repository;

    public void TransaccionController(TransaccionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Transacciones> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Transacciones registrar(@RequestBody Transacciones transaccion) {
        return repository.save(transaccion);
    }
}
