package com.example.Proyecto.Service;

import com.example.Proyecto.Model.Pago;
import com.example.Proyecto.Repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {
    @Autowired
    private PagoRepository repo;

    public List<Pago> findAll() { return repo.findAll(); }
    public Pago save(Pago p) { return repo.save(p); }
    public Pago findById(Long id) { return repo.findById(id).orElse(null); }
    public void delete(Long id) { repo.deleteById(id); }
}
