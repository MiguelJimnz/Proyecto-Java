package com.example.Proyecto.Model;

public enum TipoTarea {
    CORTE("Corte de tela"),
    COSTURA("Costura y confección"),
    PLANCHADO("Planchado y acabados"),
    DISEÑO("Diseño y patronaje"),
    BORDADO("Bordado y decoración"),
    ACABADO("Acabados finales"),
    REPARACION("Reparaciones"),
    AJUSTE("Ajustes y pruebas"),
    OTRO("Otras tareas");

    private final String descripcion;

    TipoTarea(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
