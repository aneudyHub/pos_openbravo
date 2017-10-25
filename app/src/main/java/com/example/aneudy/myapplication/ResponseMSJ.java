package com.example.aneudy.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMSJ {

    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("estado")
    @Expose
    private Integer estado;

    @SerializedName("receipt")
    @Expose
    private String receipt;

    @SerializedName("date")
    @Expose
    private String date;




    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseMSJ() {
    }

    /**
     *
     * @param estado
     * @param mensaje
     */
    public ResponseMSJ(String mensaje, Integer estado) {
        super();
        this.mensaje = mensaje;
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}