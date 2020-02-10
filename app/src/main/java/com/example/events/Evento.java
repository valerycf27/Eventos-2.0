package com.example.events;

import android.graphics.Bitmap;

public class Evento {
    private String nombre;
    private String lugar;
    private String fecha;
    private String hora;
    private long aforo;
    private String organizador;
    private String artistasInvitados;
    private String descripcion;
    private float precio;
    private float estrellas;
    private boolean guardado;
    private float ID;
    private Bitmap imagen;


    public Evento (float ID, String nombre, String lugar, String fecha, String hora, long aforo, String organizador, String artistasInvitados, String descripcion, float precio, float estrellas, boolean guardado){
        this.ID = ID;
        this.lugar = lugar;
        this.nombre = nombre;
        this.fecha=fecha;
        this.hora= hora;
        this.aforo=aforo;
        this.organizador=organizador;
        this.artistasInvitados=artistasInvitados;
        this.descripcion=descripcion;
        this.precio=precio;
        this.estrellas=estrellas;
        this.guardado=guardado;
    }

    public Evento (String nombre, String lugar, String fecha, String hora, long aforo, String organizador, String artistasInvitados, String descripcion, float precio, float estrellas, boolean guardado){
        this.lugar = lugar;
        this.nombre = nombre;
        this.fecha=fecha;
        this.hora= hora;
        this.aforo=aforo;
        this.organizador=organizador;
        this.artistasInvitados=artistasInvitados;
        this.descripcion=descripcion;
        this.precio=precio;
        this.estrellas=estrellas;
        this.guardado=guardado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public long getAforo() {
        return aforo;
    }

    public void setAforo(long aforo) {
        this.aforo = aforo;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public String getArtistasInvitados() {
        return artistasInvitados;
    }

    public void setArtistasInvitados(String artistasInvitados) {
        this.artistasInvitados = artistasInvitados;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(float estrellas) {
        this.estrellas = estrellas;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public float getID() {
        return ID;
    }

    public void setID(float ID) {
        this.ID = ID;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public void guardar(){
        guardado = !guardado;
    }

}
