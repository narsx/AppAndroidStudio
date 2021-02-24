package com.gpixel.javabeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Imagen implements Serializable {

    @SerializedName("small_url")
    @Expose
    private String imagen;
    @SerializedName("icon_url")
    @Expose
    private String icono;

    public String getIcono() {
        return icono;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Imagen(String imagen) {
        this.imagen = imagen;
    }
}
