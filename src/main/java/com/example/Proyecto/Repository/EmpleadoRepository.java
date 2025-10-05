package com.example.Proyecto.Repository;

import com.example.Proyecto.Model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {}
