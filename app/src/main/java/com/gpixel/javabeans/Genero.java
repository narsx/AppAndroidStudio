package com.gpixel.javabeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Genero implements Serializable {
    @SerializedName("name")
    @Expose
    String Nombre;

    public String getNombre() {
        return Nombre;
    }
}
