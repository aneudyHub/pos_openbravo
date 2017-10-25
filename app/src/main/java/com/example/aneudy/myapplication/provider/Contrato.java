package com.example.aneudy.myapplication.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.UUID;

/**
 * Contrato con la estructura de la base de datos y forma de las URIs
 */
public class Contrato {

    interface ColumnasUsuario{
        String USUARIO="usuario";
        String ID="id";
        String MONEY="money";
    }

    interface ColumnasSincronizacion {
        String MODIFICADO = "modificado";
        String ELIMINADO = "eliminado";
        String INSERTADO = "insertado";
    }

    interface ColumnasTarea {
        //String ID_CONTACTO = "idContacto"; // Pk
        String TAREA = "Tarea";
        String CONTRATO = "Contrato";
        String NOMBRE = "Nombre";
        String ORDEN = "Orden";
        String FECHA_ORDEN = "FechaOrden";
        String TIPO = "Tipo";
        String DIRECCION = "Direccion";
        String SECTOR = "Sector";
        String CIUDAD = "Ciudad";
        String CONCEPTO = "Concepto";
        String PRIORIDAD = "Prioridad";
        String TELEFONO = "Telefono";
        String CELULAR = "Celular";
        String REFERENCIA ="Referencia";
        String GEO = "Geo";
        String FECHA_CIERRE ="fechaCierre";
        String HORA_INICIO ="horainicio";
        String HORA_LLEGADA ="horallegada";
        String HORA_FIN ="horafin";
        String CADENA = "cadena";
        String CONCEPTO_CIERRE = "conceptoCierre";
        String OBSERVACION = "observacion";
        String FIRMA = "firma";
        String BRIGADA= "Brigada";
        String ASIGNACION= "Asignacion";
        String ID_USUARIO ="idUser";

    }

    interface ColumnasMateriales {
        //String ID_CONTACTO = "idContacto"; // Pk
        String CODIGO = "codigo";
        String NOMBRE = "Nombre";
        String CANTIDAD = "cantidad";
    }

    interface ColumnasConceptos{
        String CODIGO="codigo";
        String DOCUMENTO="documento";
        String DESCRIPCION="descripcion";
        String TIPOC="tipoc";
        String CATEGORIA="categoria";
    }




    // Autoridad del Content Provider
    public final static String AUTORIDAD = "com.telenord.Tecnico";

    // Uri base
    public final static Uri URI_CONTENIDO_BASE = Uri.parse("content://" + AUTORIDAD);


    /**
     * Controlador de la tabla "contacto"
     */

    public static class Usuario implements ColumnasUsuario{

    }


    public static class Tareas
            implements BaseColumns, ColumnasTarea, ColumnasSincronizacion {

        public static final Uri URI_CONTENIDO =
                URI_CONTENIDO_BASE.buildUpon().appendPath(RECURSO_CONTACTO).build();

        public final static String MIME_RECURSO =
                "vnd.android.cursor.item/vnd." + AUTORIDAD + "/" + RECURSO_CONTACTO;

        public final static String MIME_COLECCION =
                "vnd.android.cursor.dir/vnd." + AUTORIDAD + "/" + RECURSO_CONTACTO;


        /**
         * Construye una {@link Uri} para el {@link #TAREA} solicitado.
         */
        public static Uri construirUriTarea(String idContacto) {
            return URI_CONTENIDO.buildUpon().appendPath(idContacto).build();
        }

        public static String generarIdContacto() {
            return "C-" + UUID.randomUUID();
        }

        public static String obtenerIdContacto(Uri uri) {
            return uri.getLastPathSegment();
        }
    }


     /*
    * controlador de la tabla de materiales
    *
    * */

    public static class Materiales
            implements BaseColumns, ColumnasMateriales, ColumnasSincronizacion {

        public static final Uri URI_CONTENIDO =
                URI_CONTENIDO_BASE.buildUpon().appendPath(RECURSO_MATERIALES).build();

        public final static String MIME_RECURSO =
                "vnd.android.cursor.item/vnd." + AUTORIDAD + "/" + RECURSO_MATERIALES;

        public final static String MIME_COLECCION =
                "vnd.android.cursor.dir/vnd." + AUTORIDAD + "/" + RECURSO_MATERIALES;


        /**
         * Construye una {@link Uri} para el {@link } solicitado.
         */
        public static Uri construirUriTarea(String idContacto) {
            return URI_CONTENIDO.buildUpon().appendPath(idContacto).build();
        }

        public static String generarIdContacto() {
            return "C-" + UUID.randomUUID();
        }

        public static String obtenerIdContacto(Uri uri) {
            return uri.getLastPathSegment();
        }
    }


    public static class Conceptos
            implements BaseColumns, ColumnasConceptos, ColumnasSincronizacion {

        public static final Uri URI_CONTENIDO =
                URI_CONTENIDO_BASE.buildUpon().appendPath(RECURSO_CONCEPTOS).build();

        public final static String MIME_RECURSO =
                "vnd.android.cursor.item/vnd." + AUTORIDAD + "/" + RECURSO_CONCEPTOS;

        public final static String MIME_COLECCION =
                "vnd.android.cursor.dir/vnd." + AUTORIDAD + "/" + RECURSO_CONCEPTOS;


        /**
         * Construye una {@link Uri} para el {@link } solicitado.
         */
        public static Uri construirUriTarea(String idContacto) {
            return URI_CONTENIDO.buildUpon().appendPath(idContacto).build();
        }

        public static String generarIdContacto() {
            return "C-" + UUID.randomUUID();
        }

        public static String obtenerIdContacto(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    // Recursos
    public final static String RECURSO_CONTACTO = "contactos";
    public final static String RECURSO_MATERIALES = "materiales";
    public final static String RECURSO_CONCEPTOS = "conceptos";

}
