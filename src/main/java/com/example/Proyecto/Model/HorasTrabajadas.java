package com.example.Proyecto.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
public class HorasTrabajadas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHora;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido; // Relación opcional con pedido

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    @Column(name = "total_horas")
    private Double totalHoras;

    @Enumerated(EnumType.STRING)
    private TipoTarea tipoTarea;

    private String descripcion;
    private Boolean verificado = false;

    // Constructor por defecto
    public HorasTrabajadas() {}

    // Constructor completo
    public HorasTrabajadas(Long idHora, Empleado empleado, Pedido pedido, LocalDate fecha,
                           LocalTime horaInicio, LocalTime horaFin, TipoTarea tipoTarea,
                           String descripcion, Boolean verificado) {
        this.idHora = idHora;
        this.empleado = empleado;
        this.pedido = pedido;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoTarea = tipoTarea;
        this.descripcion = descripcion;
        this.verificado = verificado;
        calcularHoras(); // Calcular al crear
    }

    // Calcular horas automáticamente antes de persistir
    @PrePersist
    @PreUpdate
    public void calcularHoras() {
        if (horaInicio != null && horaFin != null) {
            long minutos = ChronoUnit.MINUTES.between(horaInicio, horaFin);
            this.totalHoras = minutos / 60.0;

            // Validar que la hora fin sea después de la hora inicio
            if (minutos < 0) {
                throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
            }
        }
    }

    // Getters y Setters
    public Long getIdHora() { return idHora; }
    public void setIdHora(Long idHora) { this.idHora = idHora; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
        calcularHoras(); // Recalcular al cambiar
    }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
        calcularHoras(); // Recalcular al cambiar
    }

    public Double getTotalHoras() { return totalHoras; }
    public void setTotalHoras(Double totalHoras) { this.totalHoras = totalHoras; }

    public TipoTarea getTipoTarea() { return tipoTarea; }
    public void setTipoTarea(TipoTarea tipoTarea) { this.tipoTarea = tipoTarea; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getVerificado() { return verificado; }
    public void setVerificado(Boolean verificado) { this.verificado = verificado; }
}
