package com.example.Proyecto.Service;

import com.example.Proyecto.Model.Empleado;
import com.example.Proyecto.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository repo;

    public List<Empleado> findAll() { return repo.findAll(); }
    public Empleado save(Empleado e) { return repo.save(e); }
    public Empleado findById(Long id) { return repo.findById(id).orElse(null); }
    public void delete(Long id) { repo.deleteById(id); }
}
