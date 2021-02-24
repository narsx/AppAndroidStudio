package com.gpixel.javabeans;

public class Usuario {
    public Usuario(String nombre, String correo, String contrasenna) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenna = contrasenna;
    }

    String nombre;
    String correo;
    String contrasenna;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }
}

