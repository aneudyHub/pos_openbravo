package com.example.aneudy.myapplication.Models;

import com.example.aneudy.myapplication.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ignacio on 1/8/2017.
 */

public class Receipt {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("details")
    @Expose
    private List<Product> details;
    @SerializedName("subtotal")
    @Expose
    private Double subTotal;
    @SerializedName("total")
    @Expose
    private Double Total;
    @SerializedName("taxes")
    @Expose
    private Double Taxes;
    @SerializedName("client")
    @Expose
    private String Client;
    @SerializedName("cashier")
    @Expose
    private String Cashier;
    @SerializedName("date")
    @Expose
    private String Date;
    @SerializedName("payment")
    @Expose
    private String payMethod;

    public int getTicketType() {
        return ticketType;
    }

    public void setTicketType(int ticketType) {
        this.ticketType = ticketType;
    }

    @SerializedName("TICKETTYPE")
    @Expose
    private int ticketType;


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("description")
    @Expose
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @SerializedName("footer")
    @Expose
    private String footer;

    private Double Pending;

    public Receipt(){}


    public Receipt(String id, Double total, String client, String cashier, String date, String payMethod, Double pending) {
        this.id = id;
        Total = total;
        Client = client;
        Cashier = cashier;
        Date = date;
        this.payMethod = payMethod;
        Pending = pending;
    }

    public int getItemsCount(){
        int cu=0;
        for (Product product:this.details) {
            cu+=product.getCantidad();
        }
        return cu;
    }
    public Receipt(String id, List<Product> details, Double subTotal, Double total,Double Taxes, String client, String cashier,String Date,String payMethod,String Client) {
        this.id = id;
        this.details = details;
        this.subTotal = subTotal;
        this.Taxes=Taxes;
        Total = total;
        Client = client;
        Cashier = cashier;
        this.Date=Date;
        this.payMethod=payMethod;
        this.Client=Client;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getDetails() {
        return details;
    }

    public void setDetails(List<Product> details) {
        this.details = details;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        Client = client;
    }

    public String getCashier() {
        return Cashier;
    }

    public void setCashier(String cashier) {
        Cashier = cashier;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Double getTaxes() {
        return Taxes;
    }

    public void setTaxes(Double taxes) {
        Taxes = taxes;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Double getPending() {
        return Pending;
    }

    public void setPending(Double pending) {
        Pending = pending;
    }
}
