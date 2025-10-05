package com.example.Proyecto.Repository;

import com.example.Proyecto.Model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {}
