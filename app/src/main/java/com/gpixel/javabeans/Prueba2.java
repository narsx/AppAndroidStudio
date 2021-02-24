package com.gpixel.javabeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Prueba2 implements Serializable {
    @SerializedName("results")
    @Expose
    private Juego results;

    public Juego getResults() {
        return results;
    }

    public void setResults(Juego results) {
        this.results = results;
    }
}
