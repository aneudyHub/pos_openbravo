package com.example.aneudy.myapplication;

/**
 * Created by aneudy on 2/9/2016.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


import com.example.aneudy.myapplication.provider.HelperTareas;

import java.util.ArrayList;

/**
 * Created by aneudy on 30/8/2016.
 */
public class Materiale {
    private int id;
    private String Nombre;
    private int Cantidad;
    public int modificado;

    Context ctx;

    HelperTareas db;

    String URL = "";


    public Materiale(Context ctx){

        this.ctx = ctx;
        db = new HelperTareas(ctx);

        URL = "192.168.10.49:8080" + "Materiales/listar";


    }

    public Materiale(int id, String Nombre, int Cantidad){
        this.id =id;
        this.Nombre=Nombre;
        this.Cantidad=Cantidad;
    }



    public void setId(int id){this.id=id;}
    public void setNombre(String Nombre){this.Nombre=Nombre;}
    public void setCantidad(int cantidad){this.Cantidad=cantidad;}

    public int getId(){return this.id;}
    public String getNombre(){return this.Nombre;}
    public int getCantidad(){
        return this.Cantidad;
    }


    public ArrayList<Materiale> get_Materiales_All(){
        return db.leer_Materiales();
    }

    //pendiente resolver
    public void showAlert(String mensaje){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setMessage(mensaje);

        alertDialogBuilder.setPositiveButton("salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void aplicarSanidad() {
        id = 0;
        Nombre = Nombre == null ? "" : Nombre;
        Cantidad = 0;
    }




}
