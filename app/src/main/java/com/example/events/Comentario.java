package com.example.events;

public class Comentario {
    private int id;
    private String nombreEvento;
    private String contenido;
    private float valoracion;

    public Comentario(String nombreEvento, String contenido, float valoracion) {
        this.nombreEvento = nombreEvento;
        this.contenido = contenido;
        this.valoracion = valoracion;
    }

    public Comentario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }
}
