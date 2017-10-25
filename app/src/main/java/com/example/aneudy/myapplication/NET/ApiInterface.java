package com.example.aneudy.myapplication.NET;


import android.provider.ContactsContract;

import com.example.aneudy.myapplication.Clients;
import com.example.aneudy.myapplication.Insertar;
import com.example.aneudy.myapplication.Models.BalanceResponse;
import com.example.aneudy.myapplication.Models.Receipt;
import com.example.aneudy.myapplication.Models.User;
import com.example.aneudy.myapplication.Product;
import com.example.aneudy.myapplication.ProductosResponse;
import com.example.aneudy.myapplication.ResponseMSJ;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by aneudy on 23/1/2017.
 */

public interface ApiInterface {
    @GET("Productos/list")
    Call<Product> getProductos();

    @GET("Productos/list")
    Call<ProductosResponse> getProductos1();

    @POST("Facturas/pagos")
    Call<ResponseMSJ> insertar(@Body Insertar insertar);

    @POST("Facturas/abonar")
    Call<ResponseMSJ> abonar(@Body Insertar insertar);

    @GET("Clientes/find")
    Call<List<Clients>> getClientes(@Query("ref") String ref);

    @GET("Clientes/balance")
    Call<BalanceResponse> getBalance(@Query("client") String Client);


    @GET("Facturas/reimprimir")
    Call<Receipt> reprint(@Query("customer") String cliente);


    @FormUrlEncoded
    @POST("Usuarios/login")
    Call<User> Login(@Field("usuario") String usuario,@Field("password") String password,@Field("imei") String imei);
}
