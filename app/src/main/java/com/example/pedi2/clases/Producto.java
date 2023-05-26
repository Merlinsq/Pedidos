package com.example.pedi2.clases;

public class Producto {
    private int id;
    private String codigo;
    private String descripcion;


    public Producto(int id, String codigo, String descripcion) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;

    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }


}
