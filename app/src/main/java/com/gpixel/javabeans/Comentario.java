package com.gpixel.javabeans;

import com.gpixel.Usuario.UsuarioPojo;

public class Comentario {
    UsuarioPojo usuario;
    String idJuego;
    String texto;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UsuarioPojo getUsuario() {
        return usuario;
    }

    public Comentario() {
    }

    public Comentario(UsuarioPojo usuario, String idJuego, String texto, String id) {
        this.usuario = usuario;
        this.idJuego = idJuego;
        this.texto = texto;
        this.id = id;
    }

    public void setUsuario(UsuarioPojo usuario) {
        this.usuario = usuario;
    }

    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
