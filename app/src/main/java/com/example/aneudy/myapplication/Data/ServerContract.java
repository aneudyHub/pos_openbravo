package com.example.aneudy.myapplication.Data;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.UUID;

/**
 * Created by aneudy on 3/2/2017.
 */

public class ServerContract {

    public static final String AUTORIDAD_CONTENIDO = "com.example.aneudy.myapplication.Data.Provider";

    public static final Uri URI_BASE = Uri.parse("content://" + AUTORIDAD_CONTENIDO);

    public static final String SERVER_ROUTE="servers";
    public static final String PRODUCTS_ROUTE="products";
    public static final String TOKEN_ROUTE="token";

    // [TIPOS_MIME]
    public static final String BASE_CONTENIDOS = "openbravo.";

    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
            + BASE_CONTENIDOS;

    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
            + BASE_CONTENIDOS;

    public static String generarMime(String id) {
        if (id != null) {
            return TIPO_CONTENIDO + id;
        } else {
            return null;
        }
    }

    public static String generarMimeItem(String id) {
        if (id != null) {
            return TIPO_CONTENIDO_ITEM + id;
        } else {
            return null;
        }
    }
    // [/TIPOS_MIME]


    interface ColumnasServer{
        String _ID = "_id";
        String HOST_NAME = "reference";
        String PORT = "code";
        String NAME = "codetype";
        String AUTH = "name";
    }

    interface  ColumnasProducts{
        String ID = "_id";
        String REFERENCE = "reference";
        String CODE = "code";
        String CODETYPE = "codetype";
        String NAME = "name";
        String PRICEBUY = "pricebuy";
        String PRICESELL = "pricesell";
        String CATEGORY = "category";
        String TAXCAT = "taxcat";
        String ATTRIBUTESET_ID = "attributeset_id";
        String STOCKCOST = "stockcost";
        String STOCKVOLUME = "stockvolume";
        String ISCOM = "iscom";
        String ISSCALE = "isscale";
        String ATTRIBUTES = "attributes";
    }

    interface ColumnasToken{
        String _ID="_id";
        String USER="user_id";
        String ACCESS_TOKEN="access_token";
        String REFRESH_TOKEN="refresh_token";
        String SCOPE="scope";
        String EXPIRE_TIME="expire_time";
    }

    public static class Products implements ColumnasProducts{
        public static final Uri URI_CONTENIDO =
                URI_BASE.buildUpon().appendPath(PRODUCTS_ROUTE).build();

        public static Uri crearUriProducto(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String generarIdProducto() {
            return "PRO-" + UUID.randomUUID().toString();
        }

        public static String obtenerIdProducto(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static class Servers implements ColumnasServer{
        public static final Uri URI_CONTENIDO =
                URI_BASE.buildUpon().appendPath(SERVER_ROUTE).build();

        public static Uri crearUriServer(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String generarIdServer() {
            return "SER-" + UUID.randomUUID().toString();
        }

        public static String obtenerIdServer(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static class Token implements ColumnasToken{
        public static final Uri URI_CONTENIDO =
                URI_BASE.buildUpon().appendPath(TOKEN_ROUTE).build();

        public static Uri crearUriToken(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String generarIdToken() {
            return "TKN-" + UUID.randomUUID().toString();
        }

        public static String obtenerIdToken(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

}
