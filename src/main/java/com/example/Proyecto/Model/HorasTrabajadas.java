package com.example.Proyecto.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
@Entity
public class HorasTrabajadas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHora;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double totalHoras;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    public HorasTrabajadas() {
    }

    public HorasTrabajadas(Long idHora, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Double totalHoras, Empleado empleado) {
        this.idHora = idHora;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.totalHoras = totalHoras;
        this.empleado = empleado;
    }

    public Long getIdHora() {
        return idHora;
    }

    public void setIdHora(Long idHora) {
        this.idHora = idHora;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Double getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(Double totalHoras) {
        this.totalHoras = totalHoras;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
