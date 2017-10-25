package com.example.aneudy.myapplication.Data;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract Class entre el provider y las aplicaciones
 */

public class Contract {

    /**
     * Autoridad del Content Provider
     */
    public final static String AUTHORITY = "com.example.aneudy.myapplication.Data.Provider";
    /**
     * Representación de la tabla a consultar
     */
    public static final String PRODUCTOS = "productos";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + AUTHORITY + PRODUCTOS;
    /**
     * Tipo MIME que retorna la consulta de {@link CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + PRODUCTOS;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + PRODUCTOS);
    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;
    /**
     * Código para URIs de multiples registros
     */
    public static final int ALLROWS = 1;
    /**
     * Código para URIS de un solo registro
     */
    public static final int SINGLE_ROW = 2;


    // Asignación de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PRODUCTOS, ALLROWS);
        uriMatcher.addURI(AUTHORITY, PRODUCTOS + "/#", SINGLE_ROW);
    }

    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }


        public final static String ID = "id";

        public final static String REFERENCE = "reference";

        public final static String CODE = "code";

        public final static String CODETYPE = "codetype";

        public final static String NAME = "name";

        public final static String PRICEBUY = "pricebuy";

        public final static String PRICESELL = "pricesell";

        public final static String CATEGORY = "category";

        public final static String TAXCAT = "taxcat";

        public final static String ATTRIBUTESET_ID = "attributeset_id";
        public final static String STOCKCOST = "stockcost";

        public final static String STOCKVOLUME = "stockvolume";

        public final static String ISCOM = "iscom";

        public final static String ISSCALE = "isscale";

        public final static String ATTRIBUTES = "attributes";

    }

}
