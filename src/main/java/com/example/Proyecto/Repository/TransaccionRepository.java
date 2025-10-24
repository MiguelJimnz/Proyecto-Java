package com.example.Proyecto.Repository;

import com.example.Proyecto.Model.Transacciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transacciones, Long> {
}
