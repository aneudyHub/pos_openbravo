package com.example.aneudy.myapplication;

import com.google.gson.annotations.SerializedName;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Insertar {

    @SerializedName("money")
    @Expose
    private String Money;
    @SerializedName("person")
    @Expose
    private String person;
    @SerializedName("primero")
    @Expose
    private String primero;
    @SerializedName("tipopago")
    @Expose
    private String tipopago;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("detalle")
    @Expose
    private String detalle;

    /**
     * No args constructor for use in serialization
     *
     */
    public Insertar() {
    }

    /**
     *
     * @param total
     * @param primero
     * @param tipopago
     * @param detalle
     */
    public Insertar(String primero, String tipopago, Double total, String detalle) {
        super();
        this.primero = primero;
        this.tipopago = tipopago;
        this.total = total;
        this.detalle = detalle;
    }

    public String getPrimero() {
        return primero;
    }

    public void setPrimero(String primero) {
        this.primero = primero;
    }

    public String getTipopago() {
        return tipopago;
    }

    public void setTipopago(String tipopago) {
        this.tipopago = tipopago;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}