package com.example.pedi2.clases;

public class Cliente {
    private int id;
    private String codigo;
    private String nombre;
    private String direccion;
    private String cl;

    public Cliente(int id, String codigo, String nombre, String direccion, String cl) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.cl = cl;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCl() {
        return cl;
    }
}

