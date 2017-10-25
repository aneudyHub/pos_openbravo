package com.example.aneudy.myapplication;

/**
 * POJO de los contactos
 */
public class Tarea {

    public String Tarea;
    public String Contrato;
    public String Nombre;
    public String Orden;
    public String FechaOrden;
    public String Tipo;
    public String Direccion;
    public String Sector;
    public String Ciudad;
    public String Concepto;
    public String Prioridad;
    public String Telefono;
    public String Celular;
    public String Referencia;
    public String Geo;
    public String Brigada;
    public String Asignacion;
    public int modificado;


    public Tarea(String tarea,
                 String contrato, String nombre, String orden, String fechaOrden, String tipo,
                 String direccion, String sector, String ciudad, String concepto, String prioridad,
                 String telefono, String celular, String referencia, String geo, String brigada, String asignacion, int modificado) {

        this.Tarea = tarea;
        this.Contrato = contrato;
        this.Nombre = nombre;
        this.Orden = orden;
        this.FechaOrden = fechaOrden;
        this.Tipo = tipo;
        this.Direccion = direccion;
        this.Sector = sector;
        this.Ciudad = ciudad;
        this.Concepto = concepto;
        this.Prioridad = prioridad;
        this.Telefono = telefono;
        this.Celular = celular;
        this.Referencia = referencia;
        this.Geo =  geo;
        this.Brigada =  brigada;
        this.Asignacion = asignacion;
        this.modificado = modificado;
    }

    public void aplicarSanidad() {

        Tarea = Tarea == null ? "" : Tarea;
        Contrato = Contrato == null ? "" : Contrato;
        Nombre = Nombre == null ? "" : Nombre;
        Orden = Orden == null ? "" : Orden;
        FechaOrden = FechaOrden == null ? "" : FechaOrden;
        Tipo = Tipo == null ? "" : Tipo;
        Direccion = Direccion == null ? "" : Direccion;
        Sector = Sector == null ? "" : Sector;
        Ciudad = Ciudad == null ? "" : Ciudad;
        Concepto = Concepto == null ? "" : Concepto;
        Prioridad = Prioridad == null ? "" : Prioridad;
        Telefono = Telefono == null ? "" : Telefono;
        Celular =  Celular == null ? "" : Celular;
        Referencia = Referencia == null ? "" : Referencia;
        Geo = Geo == null ? "": Geo;
        Brigada = Brigada == null ? "": Brigada;
        Asignacion = Asignacion == null ? "": Asignacion;
        modificado = 0;
    }

    public boolean compararCon(Tarea otro) {
        return
                Tarea.equals(otro.Tarea) &&
                Contrato.equals(otro.Contrato) &&
                Nombre.equals(otro.Nombre) &&
                Orden.equals(otro.Orden) &&
                FechaOrden.equals(otro.FechaOrden)&&
                Tipo.equals(otro.Tipo)&&
                Direccion.equals(otro.Direccion)&&
                Sector.equals(otro.Sector)&&
                Ciudad.equals(otro.Ciudad)&&
                Concepto.equals(otro.Concepto)&&
                Prioridad.equals(otro.Prioridad)&&
                Telefono.equals(otro.Telefono)&&
                Celular.equals(otro.Celular)&&
                Referencia.equals(otro.Referencia)&&
                Geo.equals(otro.Geo)&&
                Brigada.equals(otro.Brigada);
    }
}
