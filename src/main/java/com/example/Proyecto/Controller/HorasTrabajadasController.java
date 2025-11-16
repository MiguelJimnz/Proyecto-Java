package com.example.Proyecto.Controller;


import com.example.Proyecto.Model.HorasTrabajadas;
import com.example.Proyecto.Repository.HorasTrabajadasRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horas")
public class HorasTrabajadasController {
    private final HorasTrabajadasRepository repository;

    public HorasTrabajadasController(HorasTrabajadasRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<HorasTrabajadas> listar() {
        return repository.findAll();
    }

    @PostMapping
    public HorasTrabajadas registrar(@RequestBody HorasTrabajadas horas) {
        return repository.save(horas);
    }
}
