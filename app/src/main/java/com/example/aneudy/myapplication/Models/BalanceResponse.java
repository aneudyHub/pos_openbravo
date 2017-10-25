package com.example.aneudy.myapplication.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ignacio on 10/8/2017.
 */

public class BalanceResponse {
    @SerializedName("balance")
    @Expose
    private Double Balance;

    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }
}
