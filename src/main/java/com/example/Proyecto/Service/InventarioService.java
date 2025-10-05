package com.example.Proyecto.Service;

import com.example.Proyecto.Model.Inventario;
import com.example.Proyecto.Repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository repo;

    public List<Inventario> findAll() { return repo.findAll(); }
    public Inventario save(Inventario i) { return repo.save(i); }
    public Inventario findById(Long id) { return repo.findById(id).orElse(null); }
    public void delete(Long id) { repo.deleteById(id); }
}
