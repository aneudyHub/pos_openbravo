package com.example.aneudy.myapplication.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aneudy.myapplication.ConceptosAveria;
import com.example.aneudy.myapplication.Materiale;

import java.util.ArrayList;
import java.util.HashMap;

import static android.provider.BaseColumns._ID;

import com.example.aneudy.myapplication.provider.Contrato.Materiales;
import com.example.aneudy.myapplication.provider.Contrato.Tareas;
import com.example.aneudy.myapplication.provider.Contrato.Conceptos;

/**
 * Clase auxiliar para controlar accesos a la base de datos SQLite
 */
public class HelperTareas extends SQLiteOpenHelper {

    static final int VERSION = 1;
    Context context;

    static final String NOMBRE_BD = "people_app.db";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "username";
    private static final String KEY_UID = "user_id";
    private static final String KEY_UBrigada = "Brigada";

    // Login Tokes Columns names
    private static final String ACCESS_TOKEN ="access_token";
    private static final String REFRESH_TOKEN ="refresh_token";
    private static final String EXPIRATION_TOKEN ="expiration_token";

    interface Tablas {
        String CONTACTO = "contacto";
        String MATERIALES ="materiales";
        String CONCEPTOS="conceptos";
        String USER="user";
        String Token="tokens";
    }

    public HelperTareas(Context context) {
        super(context, NOMBRE_BD, null, VERSION);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL(
                "CREATE TABLE " + Tablas.CONTACTO + "("
                        + Tareas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Tareas.TAREA + " TEXT NOT NULL UNIQUE, "
                        + Tareas.CONTRATO + " TEXT,"
                        + Tareas.NOMBRE + " TEXT,"
                        + Tareas.ORDEN + " TEXT,"
                        + Tareas.FECHA_ORDEN + " DATE,"
                        + Tareas.TIPO + " TEXT,"
                        + Tareas.DIRECCION + " TEXT,"
                        + Tareas.SECTOR + " TEXT,"
                        + Tareas.CIUDAD + " TEXT,"
                        + Tareas.CONCEPTO + " TEXT,"
                        + Tareas.PRIORIDAD + " INTEGER,"
                        + Tareas.TELEFONO + " TEXT,"
                        + Tareas.CELULAR + " TEXT,"
                        + Tareas.REFERENCIA + " TEXT,"
                        + Tareas.GEO + " TEXT,"
                        + Tareas.BRIGADA + " TEXT,"
                        + Tareas.ASIGNACION + " TEXT,"
                        + Tareas.ID_USUARIO + " INTEGER DEFAULT 0,"
                        + Tareas.CADENA + " TEXT,"
                        + Tareas.CONCEPTO_CIERRE + " TEXT,"
                        + Tareas.OBSERVACION + " TEXT DEFAULT '',"
                        + Tareas.FIRMA + " LONGBLOB DEFAULT '',"
                        + Tareas.FECHA_CIERRE + " DATE,"
                        + Tareas.HORA_LLEGADA + " TEXT  DEFAULT '0',"
                        + Tareas.HORA_INICIO + " TEXT,"
                        + Tareas.HORA_FIN + " TEXT,"
                        + Tareas.INSERTADO + " INTEGER DEFAULT 1,"
                        + Tareas.MODIFICADO + " INTEGER DEFAULT 0,"
                        + Tareas.ELIMINADO + " INTEGER DEFAULT 0)");

        db.execSQL(
                "CREATE TABLE "+ Tablas.MATERIALES+" ("
                        + _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Materiales.CODIGO+ " INTEGER DEFAULT 0,"
                        + Materiales.NOMBRE+ " TEXT NOT NULL,"
                        + Materiales.CANTIDAD+ " INTEGER DEFAULT 0);"
        );

        db.execSQL(
                "CREATE TABLE " + Tablas.CONCEPTOS + "("
                        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Conceptos.CODIGO + " INTEGER NOT NULL UNIQUE,"
                        + Conceptos.DESCRIPCION + " TEXT);"
                      );

        db.execSQL(
                "CREATE TABLE " + Tablas.USER + "("
                        + KEY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_NAME + " TEXT,"
                        + KEY_UBrigada + " TEXT,"
                        + KEY_UID + " TEXT);"
        );

        db.execSQL(
                "CREATE TABLE " + Tablas.Token + "("
                        + ACCESS_TOKEN + " TEXT,"
                        + REFRESH_TOKEN + " TEXT,"
                        + EXPIRATION_TOKEN + " TEXT);"
        );*/

        db.execSQL("CREATE TABLE usuarios(id TEXT,name TEXT,money TEXT);");

    }

    public void insertar_login(String id,String name,String money){
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("name",name);
        values.put("money",money);
        this.getWritableDatabase().insert("usuarios",null,values);
    }

    public void logout(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete("usuarios", null, null);
        db.close();

        Log.d("User", "Deleted all user info from sqlite");
    }
/*
*
* helper para materiales
*
* */
    public void insertar_Materiales(int id,String Nombre,int cantidad){
        ContentValues values = new ContentValues();
        values.put(Materiales.CODIGO,id);
        values.put(Materiales.NOMBRE,Nombre);
        values.put(Contrato.Materiales.CANTIDAD,cantidad);
        this.getWritableDatabase().insert(Tablas.MATERIALES,null,values);
    }

    public ArrayList<Materiale> leer_Materiales(){
        final ArrayList<Materiale> materials = new ArrayList<>();
        String columnas[] = {Materiales.CODIGO,Materiales.NOMBRE, Materiales.CANTIDAD};
        Cursor c = this.getReadableDatabase().query(Tablas.MATERIALES,columnas,null,null,null,null,null);
        int id,ui,ip;
        id=c.getColumnIndex(Materiales.CODIGO);
        ui=c.getColumnIndex(Materiales.NOMBRE);
        ip=c.getColumnIndex(Materiales.CANTIDAD);


        while (c.moveToNext()){
            Materiale m = new Materiale(c.getInt(id),c.getString(ui),c.getInt(ip));
            materials.add(m);
        }
        Log.d("cursor leer", String.valueOf(materials));
        return materials;

    }

    public ArrayList<ConceptosAveria> leer_ConceptosAveria(){
        final ArrayList<ConceptosAveria> conceptos = new ArrayList<>();
        String columnas[] = {Conceptos.CODIGO,Conceptos.DESCRIPCION};
        Cursor c = this.getReadableDatabase().query(Tablas.CONCEPTOS,columnas,null,null,null,null,null);
        int id,ui;
        id=c.getColumnIndex(Conceptos.CODIGO);
        ui=c.getColumnIndex(Conceptos.DESCRIPCION);


        while (c.moveToNext()){
            ConceptosAveria m = new ConceptosAveria(c.getInt(id),c.getString(ui));
            conceptos.add(m);
        }
        Log.d("cursor leer", String.valueOf(conceptos));
        return conceptos;

    }

    public int ComprobarModificado(int Condicion){
        SQLiteDatabase db = getReadableDatabase();
        //String codigo = UPreferencias.obtenerBrigada(context);
        String codigo = "100";
       Cursor mCount= db.rawQuery("select count(*) from "+ Tablas.CONTACTO +" where modificado = "+ Condicion
               + " and eliminado= 0"+" and Brigada = " + codigo, null);
        //Cursor mCount= db.rawQuery("select count(*) from contacto where modificado="+Condicion, null);

        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }

    public int ComprobarMateriales(String Cod){
        SQLiteDatabase db = getReadableDatabase();
        Cursor Cmateriales= db.rawQuery("select count(*) from "+ Tablas.MATERIALES +" where codigo = "+ Cod, null);
        //Cursor mCount= db.rawQuery("select count(*) from contacto where modificado="+Condicion, null);
        Cmateriales.moveToFirst();
        int count= Cmateriales.getInt(0);
        Cmateriales.close();
        return count;
    }

    public String getMateriales(String Cod){
        SQLiteDatabase db = getReadableDatabase();
        String codigo = "";
        Cursor Cmateriales= db.rawQuery("select Nombre from "+ Tablas.MATERIALES +" where codigo = "+ Cod, null);
        //Cursor mCount= db.rawQuery("select count(*) from contacto where modificado="+Condicion, null);
        Cmateriales.moveToFirst();
        codigo = Cmateriales.getString(0);
        Cmateriales.close();
        return codigo;
    }

   /* public String getUserName(String id){

    }*/



    public boolean hasObject(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + Tablas.MATERIALES
                + " WHERE " + Materiales.CODIGO + " =? ";
        Cursor cursor = db.rawQuery(selectString, new String[] {id}); //add the String your searching by here

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;
        }
        cursor.close();          // Don't forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;
    }

    public void insertar_Conceptos(int id,String Descripcion){
        ContentValues values = new ContentValues();
        values.put(Conceptos.CODIGO,id);
        values.put(Conceptos.DESCRIPCION,Descripcion);
        this.getWritableDatabase().insert(Tablas.CONCEPTOS,null,values);
    }

    public ArrayList<ConceptosAveria> leer_Conceptos(){
        final ArrayList<ConceptosAveria> Concep = new ArrayList<>();
        String columnas[] = {
                Conceptos.CODIGO,
                Conceptos.DESCRIPCION
        };
        Cursor c = this.getReadableDatabase().query(Tablas.CONCEPTOS,columnas,null,null,null,null,null);
        int id,des;
        id=c.getColumnIndex(Conceptos.CODIGO);
        des=c.getColumnIndex(Conceptos.DESCRIPCION);


        while (c.moveToNext()){
            ConceptosAveria conceptosAveria = new ConceptosAveria(c.getInt(id),c.getString(des));
            Concep.add(conceptosAveria);
        }
        Log.d("cursor leer conceptos", String.valueOf(Concep));
        return Concep;
    }

    public boolean hasObject_Conceptos(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + Tablas.CONCEPTOS
                + " WHERE " + Conceptos.CODIGO + " =? ";
        Cursor cursor = db.rawQuery(selectString, new String[] {id}); //add the String your searching by here

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;
        }
        cursor.close();          // Don't forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;
    }

    public void addUser(String username, String user_id, String brigada) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, username); // Name
        values.put(KEY_UID, user_id); // Email
        values.put(KEY_UBrigada, brigada);
        // Inserting Row
        long id = db.insert(Tablas.USER, null, values);
        db.close(); // Closing database connection

        //Log.d("User", "New user inserted into sqlite: " + id);
    }



    public void addToken(String token,String refreshToken,String expirationToken){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ACCESS_TOKEN, token); // token
        values.put(REFRESH_TOKEN, refreshToken); // refresh token
        values.put(EXPIRATION_TOKEN, expirationToken); // time expiration
        // Inserting Row
        long id = db.insert(Tablas.Token, null, values);
        db.close(); // Closing database connection

        //Log.d("Token", "New TOKEN inserted into sqlite: " + id);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM usuarios";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("id", cursor.getString(0));
            user.put("name", cursor.getString(1));
            user.put("money", cursor.getString(2));
        }
        cursor.close();
        db.close();

        return user;
    }





    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(Tablas.USER, null, null);
        db.close();

        Log.d("User", "Deleted all user info from sqlite");
    }

    public HashMap<String, String> getTokenDetails() {
        HashMap<String, String> token = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + Tablas.Token;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            token.put(ACCESS_TOKEN, cursor.getString(0));
            token.put(REFRESH_TOKEN, cursor.getString(1));
            token.put(EXPIRATION_TOKEN, cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return user
       // Log.d("Token", "Fetching TOKEN from Sqlite: " + token.toString());

        return token;
    }

    public void deleteTokens() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(Tablas.Token, null, null);
        db.close();

        //Log.d("Token", "Deleted all tokens info from sqlite");
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.CONTACTO);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.MATERIALES);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.CONCEPTOS);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.USER);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.Token);
        } catch (SQLiteException e) {
            // Manejo de excepciones
        }
        onCreate(db);
    }
}
