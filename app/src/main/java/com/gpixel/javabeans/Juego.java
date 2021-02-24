package com.gpixel.javabeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Juego implements Serializable {

    @SerializedName("image")
    @Expose
    private Imagen imagen;

    @SerializedName("platforms")
    @Expose
    private ArrayList <plataformas>plataformasAL;
    @SerializedName("name")
    @Expose
    private String nombre;

    @SerializedName("deck")
    @Expose
    private String descripcion;



    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("original_release_date")
    @Expose
    private String fecha;
    @SerializedName("genres")
    @Expose
    private ArrayList<Genero> genero;

    public ArrayList<Genero> getGenero() {
        return genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<plataformas> getPlataformasAL() {
        return plataformasAL;
    }

    public Juego(String nombre, String descripcion, String id, String fecha, ArrayList<plataformas>plataformasAL, Imagen imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
        this.fecha = fecha;
        this.plataformasAL=plataformasAL;
        this.imagen=imagen;
    }

    public Juego(Imagen imagen, ArrayList<plataformas> plataformasAL, String nombre, String descripcion, String id, String fecha, ArrayList<Genero> genero) {
        this.imagen = imagen;
        this.plataformasAL = plataformasAL;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
        this.fecha = fecha;
        this.genero = genero;
    }

    public void setGenero(ArrayList<Genero> genero) {
        this.genero = genero;
    }

    public Imagen getimagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
