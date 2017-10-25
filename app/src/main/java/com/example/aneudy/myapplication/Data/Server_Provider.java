package com.example.aneudy.myapplication.Data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.security.Provider;

/**
 * Created by aneudy on 3/2/2017.
 */

public class Server_Provider extends ContentProvider{

    /**
     * Instancia del administrado de BD
     */
    private DataHelper Data;

    private ContentResolver resolver;


    //casos

    /*
    * PRODUCTOS 100
    * SERVER 200
    * TOKEN 300
    *
    * */

    public static final UriMatcher uriMatcher;

    public static final int PRODUCTOS=100;
    public static final int PRODUCTOS_ID=101;
    public static final int SERVER=200;
    public static final int SERVER_ID=201;
    public static final int TOKEN=300;
    public static final int TOKEN_ID=301;


    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ServerContract.AUTORIDAD_CONTENIDO,ServerContract.PRODUCTS_ROUTE,PRODUCTOS);
        uriMatcher.addURI(ServerContract.AUTORIDAD_CONTENIDO,ServerContract.PRODUCTS_ROUTE+"/*",PRODUCTOS_ID);

        uriMatcher.addURI(ServerContract.AUTORIDAD_CONTENIDO,ServerContract.SERVER_ROUTE,SERVER);
        uriMatcher.addURI(ServerContract.AUTORIDAD_CONTENIDO,ServerContract.SERVER_ROUTE+"/*",SERVER_ID);

        uriMatcher.addURI(ServerContract.AUTORIDAD_CONTENIDO,ServerContract.TOKEN_ROUTE,TOKEN);
        uriMatcher.addURI(ServerContract.AUTORIDAD_CONTENIDO,ServerContract.TOKEN_ROUTE+"/*",TOKEN_ID);


    }

    @Override
    public boolean onCreate() {
        Data = new DataHelper(getContext(),DataGeneral_config.DATABASE_NAME,null,DataGeneral_config.DATABASE_VERSION);
        resolver = getContext().getContentResolver();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = Data.getWritableDatabase();

        int match = uriMatcher.match(uri);

        String id;

        Cursor c=null;

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (match){
            case SERVER:
                c = db.query(ServerContract.SERVER_ROUTE, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case SERVER_ID:
                id = ServerContract.Servers.obtenerIdServer(uri);
                c = db.query(ServerContract.SERVER_ROUTE, projection,
                        ServerContract.Servers._ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs,
                        null, null, sortOrder);

                break;

            case PRODUCTOS:
                c = db.query(ServerContract.PRODUCTS_ROUTE, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case PRODUCTOS_ID:
                id = ServerContract.Products.obtenerIdProducto(uri);
                c = db.query(ServerContract.PRODUCTS_ROUTE, projection,
                        ServerContract.Products.ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs,
                        null, null, sortOrder);
                break;

            case TOKEN:
                c = db.query(ServerContract.TOKEN_ROUTE, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case TOKEN_ID:
                id = ServerContract.Token.obtenerIdToken(uri);
                c = db.query(ServerContract.TOKEN_ROUTE, projection,
                        ServerContract.Token._ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs,
                        null, null, sortOrder);
                break;



            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);

        }
        c.setNotificationUri(resolver,uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SERVER:
                return ServerContract.generarMime(ServerContract.SERVER_ROUTE);
            case SERVER_ID:
                return ServerContract.generarMimeItem(ServerContract.SERVER_ROUTE);
            case PRODUCTOS:
                return ServerContract.generarMime(ServerContract.PRODUCTS_ROUTE);
            case PRODUCTOS_ID:
                return ServerContract.generarMimeItem(ServerContract.PRODUCTS_ROUTE);
            case TOKEN:
                return ServerContract.generarMime(ServerContract.TOKEN_ROUTE);
            case TOKEN_ID:
                return ServerContract.generarMimeItem(ServerContract.TOKEN_ROUTE);
            default:
                throw new IllegalArgumentException("Server desconocido: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("PROVIDER", "Inserción en " + uri + "( " + values.toString() + " )\n");

        SQLiteDatabase bd = Data.getWritableDatabase();

        switch (uriMatcher.match(uri)){
            case SERVER:
                insertOrUpdateById(bd,uri, ServerContract.SERVER_ROUTE,values);
                resolver.notifyChange(uri,null);
                return ServerContract.Servers.crearUriServer(values.getAsString(ServerContract.SERVER_ROUTE));

            case PRODUCTOS:
                insertOrUpdateById(bd,uri, ServerContract.PRODUCTS_ROUTE,values);
                resolver.notifyChange(uri,null);
                return ServerContract.Products.crearUriProducto(values.getAsString(ServerContract.PRODUCTS_ROUTE));


            case TOKEN:
                insertOrUpdateById(bd,uri, ServerContract.TOKEN_ROUTE,values);
                resolver.notifyChange(uri,null);
                return ServerContract.Token.crearUriToken(values.getAsString(ServerContract.TOKEN_ROUTE));

        }
       return  uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return 0;
    }


    private void insertOrUpdateById(SQLiteDatabase db, Uri uri, String table,
                                    ContentValues values) throws SQLException{
        Cursor c = query(uri,new String[]{"codigo"},ServerContract.Products.ID+"=?",new String[]{values.get(ServerContract.Products.ID).toString()},null);

        if(c.getCount()>0){
            c.moveToFirst();
            Uri urinew = Uri.withAppendedPath(uri,c.getString(c.getColumnIndex(ServerContract.Products.ID)));
            int r = update(urinew,values,ServerContract.Products.ID+"=?",new String[]{c.getString(c.getColumnIndex(ServerContract.Products.ID))});
            if(r==0){

            }
        }else{
            db.insertOrThrow(table, null, values);
        }

    }
}
