package com.example.gestortareas.models;

public class Tarea {
    public int id;
    public String titulo;
    public String descripcion;
    public String fecha_limite;
    public String estado;

    public Tarea(String titulo, String descripcion, String fecha_limite, String estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_limite = fecha_limite;
        this.estado = estado;
    }
}
