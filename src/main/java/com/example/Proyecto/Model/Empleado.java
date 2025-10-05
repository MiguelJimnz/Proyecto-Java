package com.example.Proyecto.Model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "detalles", "pagos", "pedidos"})
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empleadoId;

    private String nombre;
    private String rol;
    private String telefono;
    private String fotoUrl;
    private LocalDate fechaIngreso;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedido> pedidos;


    public Empleado() {
    }

    public Empleado(Long empleadoId, String nombre, String rol, String telefono, String fotoUrl, LocalDate fechaIngreso, List<Pedido> pedidos) {
        this.empleadoId = empleadoId;
        this.nombre = nombre;
        this.rol = rol;
        this.telefono = telefono;
        this.fotoUrl = fotoUrl;
        this.fechaIngreso = fechaIngreso;
        this.pedidos = pedidos;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
