package com.gpixel.javabeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Prueba implements Serializable {
    @SerializedName("error")
    @Expose
    private String error;


    @SerializedName("limit")
    @Expose
    private String limit;
    @SerializedName("offset")
    @Expose
    private String offset;
    @SerializedName("number_of_page_results")
    @Expose
    private String number_of_page_results;
    @SerializedName("number_of_total_results")
    @Expose
    private String number_of_total_results;

    @SerializedName("status_code")
    @Expose
    private String status_code;

    @SerializedName("results")
    @Expose
    private ArrayList<Juego> results;

    @SerializedName("version")
    @Expose
    private String version;

    public Prueba(String error, String limit, String offset, String number_of_page_results, String number_of_total_results, String status_code, ArrayList<Juego> results, String version) {
        this.error = error;
        this.limit = limit;
        this.offset = offset;
        this.number_of_page_results = number_of_page_results;
        this.number_of_total_results = number_of_total_results;
        this.status_code = status_code;
        this.results = results;
        this.version = version;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getNumber_of_page_results() {
        return number_of_page_results;
    }

    public void setNumber_of_page_results(String number_of_page_results) {
        this.number_of_page_results = number_of_page_results;
    }

    public String getNumber_of_total_results() {
        return number_of_total_results;
    }

    public void setNumber_of_total_results(String number_of_total_results) {
        this.number_of_total_results = number_of_total_results;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public ArrayList<Juego> getResults() {
        return results;
    }

    public void setResults(ArrayList<Juego> results) {
        this.results = results;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
