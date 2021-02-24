package com.gpixel.Usuario;

import android.app.Application;

import com.gpixel.javabeans.Comentario;
import com.gpixel.javabeans.Juego;

import java.util.ArrayList;

public class Aplicacion extends Application {
    private String usuario;
    private String idusuario;
    private Juego juego;
    private UsuarioPojo us;
    private String urlfoto;
    private ArrayList<UsuarioPojo> alUs;
    private ArrayList<Comentario>alCom;

    public ArrayList<UsuarioPojo> getAlUs() {
        return alUs;
    }

    public ArrayList<Comentario> getAlcom() {
        return alCom;
    }

    public void setAlCom(ArrayList<Comentario> alCom) {
        this.alCom = alCom;
    }

    public void setAlUs(ArrayList<UsuarioPojo> alUs) {
        this.alUs = alUs;
    }


    public String getUrlfoto() {
        return urlfoto;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setIdusuario(String idusuario) { this.idusuario = idusuario; }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public Juego getJuego() {
        return juego;
    }

    public UsuarioPojo getUs() {
        return us;
    }

    public void setUs(UsuarioPojo us) {
        this.us = us;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public Aplicacion() {
    }
}
