package com.example.Proyecto.Service;

import com.example.Proyecto.Model.MaterialUsado;
import com.example.Proyecto.Repository.MaterialUsadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialUsadoService {
    @Autowired
    private MaterialUsadoRepository repo;

    public List<MaterialUsado> findAll() { return repo.findAll(); }
    public MaterialUsado save(MaterialUsado m) { return repo.save(m); }
    public MaterialUsado findById(Long id) { return repo.findById(id).orElse(null); }
    public void delete(Long id) { repo.deleteById(id); }
}
