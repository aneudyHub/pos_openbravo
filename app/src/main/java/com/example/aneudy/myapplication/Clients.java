package com.example.aneudy.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aneudy on 27/7/2017.
 */

public class Clients
{
    @SerializedName("ID")
    @Expose
    private String Id;
    @SerializedName("TAXID")
    @Expose
    private String TAID;
    @SerializedName("NAME")
    @Expose
    private String Name;
    @SerializedName("PHONE")
    @Expose
    private String Telephone;
    @SerializedName("ADDRESS")
    @Expose
    private String Address;

    public Clients(String id, String TAID, String name, String telephone, String address) {
        Id = id;
        this.TAID = TAID;
        Name = name;
        Telephone = telephone;
        Address = address;
    }

    public Clients() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTAID() {
        return TAID;
    }

    public void setTAID(String TAID) {
        this.TAID = TAID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
