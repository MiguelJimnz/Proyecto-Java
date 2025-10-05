package com.example.Proyecto.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "materiales_usados")
public class MaterialUsado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usoId;
    @ManyToOne
    @JsonBackReference
    private Inventario inventario;
    @ManyToOne
    @JoinColumn(name = "detalle_id")
    private DetallePedido detallePedido;
    @Column(name = "cantidad_usada")
    private Integer cantidadUsada;

    public MaterialUsado() {
    }

    public MaterialUsado(Long usoId, DetallePedido detallePedido, Inventario inventario, Integer cantidadUsada) {
        this.usoId = usoId;
        this.detallePedido = detallePedido;
        this.inventario = inventario;
        this.cantidadUsada = cantidadUsada;
    }

    public Long getUsoId() {
        return usoId;
    }

    public void setUsoId(Long usoId) {
        this.usoId = usoId;
    }

    public DetallePedido getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(DetallePedido detallePedido) {
        this.detallePedido = detallePedido;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Integer getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(Integer cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }
}
