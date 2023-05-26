package com.example.pedi2.clases;

public class TipoPedido {
    private int id;
    private String contado;
    private String credito;

    public TipoPedido(int id, String contado, String credito) {
        this.id = id;
        this.contado = contado;
        this.credito = credito;
    }

    public int getId() {
        return id;
    }

    public String getContado() {
        return contado;
    }

    public String getCredito() {
        return credito;
    }
}

