package com.example.aneudy.myapplication;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductosResponse
{

    @SerializedName("products")
    @Expose
    private ArrayList<Product> products = null;




    /**
     * No args constructor for use in serialization
     *
     */
    public ProductosResponse() {
    }

    /**
     *
     * @param products
     */
    public ProductosResponse(ArrayList<Product> products) {
        super();
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

}