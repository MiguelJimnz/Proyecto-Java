package com.example.Proyecto.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class Transacciones {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;

    private String tipo; // "Ingreso" o "Egreso"
    private String descripcion;
    private Double monto;
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = true)
    private Empleado empleado;

    public Transacciones() {
    }

    public Transacciones(Long idTransaccion, String tipo, String descripcion, Double monto, LocalDateTime fecha, Empleado empleado) {
        this.idTransaccion = idTransaccion;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fecha = fecha;
        this.empleado = empleado;
    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
