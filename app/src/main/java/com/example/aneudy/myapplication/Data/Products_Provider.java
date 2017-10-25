package com.example.aneudy.myapplication.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by aneudy on 2/2/2017.
 */

public class Products_Provider extends ContentProvider{


    /**
     * Instancia del administrado de BD
     */
    private DataHelper Data;


    @Override
    public boolean onCreate() {

        Data = new DataHelper(getContext(),DataGeneral_config.DATABASE_NAME,null,DataGeneral_config.DATABASE_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = Data.getWritableDatabase();

        int match = Contract.uriMatcher.match(uri);

        Cursor c;

        switch (match){
            case Contract.ALLROWS:
                    c = db.query(Contract.PRODUCTOS, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                    c.setNotificationUri(getContext().getContentResolver(),Contract.CONTENT_URI);
                break;

            case Contract.SINGLE_ROW:
                // Consultando un solo registro basado en el Id del Uri
                long idActividad = ContentUris.parseId(uri);
                c = db.query(Contract.PRODUCTOS, projection,
                        Contract.Columnas._ID + " = " + idActividad,
                        selectionArgs, null, null, sortOrder);
                c.setNotificationUri(
                        getContext().getContentResolver(),
                        Contract.CONTENT_URI);
                break;

            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);

        }

        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (Contract.uriMatcher.match(uri)) {
            case Contract.ALLROWS:
                return Contract.MULTIPLE_MIME;
            case Contract.SINGLE_ROW:
                return Contract.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Producto desconocido: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //validar
        if(Contract.uriMatcher.match(uri) != Contract.ALLROWS){
            throw new IllegalArgumentException("URI desconocida : " + uri);
        }
        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }

        // Si es necesario, verifica los valores

        // Inserción de nueva fila
        SQLiteDatabase db = Data.getWritableDatabase();
        long rowId = db.insert(Contract.PRODUCTOS,
                null, contentValues);
        if (rowId > 0) {
            Uri uri_actividad =
                    ContentUris.withAppendedId(
                            Contract.CONTENT_URI, rowId);
            getContext().getContentResolver().
                    notifyChange(uri_actividad, null);
            return uri_actividad;
        }
        throw new SQLException("Falla al insertar fila en : " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = Data.getWritableDatabase();

        int match = Contract.uriMatcher.match(uri);
        int affected;

        switch (match) {
            case Contract.ALLROWS:
                affected = db.delete(Contract.PRODUCTOS,
                        selection,
                        selectionArgs);
                break;
            case Contract.SINGLE_ROW:
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(Contract.PRODUCTOS,
                        Contract.Columnas._ID + "=" + idActividad
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                getContext().getContentResolver().
                        notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = Data.getWritableDatabase();
        int affected;
        switch (Contract.uriMatcher.match(uri)) {
            case Contract.ALLROWS:
                affected = db.update(Contract.PRODUCTOS, values,
                        selection, selectionArgs);
                break;
            case Contract.SINGLE_ROW:
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(Contract.PRODUCTOS, values,
                        Contract.Columnas._ID + "=" + idActividad
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }
}
