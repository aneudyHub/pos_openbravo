package com.example.aneudy.myapplication.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aneudy on 2/2/2017.
 */

public class DataHelper extends SQLiteOpenHelper{

    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
        prueba(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTables(SQLiteDatabase sqLiteDatabase){
        String productos = "CREATE TABLE " + Contract.PRODUCTOS + " (" +
                Contract.Columnas._ID + " TEXT PRIMARY KEY , " +
                Contract.Columnas.REFERENCE + " TEXT, " +
                Contract.Columnas.CODE + " TEXT, " +
                Contract.Columnas.CODETYPE + " TEXT, " +
                Contract.Columnas.NAME + " TEXT, " +
                Contract.Columnas.PRICEBUY + " TEXT, " +
                Contract.Columnas.PRICESELL + " TEXT, " +
                Contract.Columnas.CATEGORY + " TEXT, " +
                Contract.Columnas.TAXCAT + " TEXT, " +
                Contract.Columnas.ATTRIBUTESET_ID + " TEXT, " +
                Contract.Columnas.STOCKCOST + " TEXT, " +
                Contract.Columnas.STOCKVOLUME + " TEXT, " +
                Contract.Columnas.ISCOM + " TEXT, " +
                Contract.Columnas.ISSCALE + " TEXT, " +
                Contract.Columnas.ATTRIBUTES + " TEXT);";

        sqLiteDatabase.execSQL(productos);

        String Servers = "CREATE TABLE " + ServerContract.SERVER_ROUTE + " ("+
                ServerContract.Servers._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ServerContract.Servers.HOST_NAME+ " TEXT , "+
                ServerContract.Servers.PORT+ " TEXT , "+
                ServerContract.Servers.NAME+ " TEXT , "+
                ServerContract.Servers.AUTH+ " INTEGER );";

        sqLiteDatabase.execSQL(Servers);



//        String Usuarios = "CREATE TABLE" + ServerContract.SERVER + " ("+
//                ServerContract.Columnas._ID+ "TEXT PRIMARY KEY , "+
//                ServerContract.Columnas.HOST_NAME+ " TEXT ,"+
//                ServerContract.Columnas.PORT+ " TEXT ,"+
//                ServerContract.Columnas.NAME+ " TEXT ,"+
//                ServerContract.Columnas.AUTH+ " INTEGER );";
//
//        sqLiteDatabase.execSQL(Usuarios);



    }

    public void prueba(SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put(ServerContract.Servers.HOST_NAME,"http://192.168.10.49");
        values.put(ServerContract.Servers.PORT,"8089");
        values.put(ServerContract.Servers.NAME,"prueba");
        values.put(ServerContract.Servers.AUTH,0);
        database.insert(ServerContract.SERVER_ROUTE,null,values);

    }
}
