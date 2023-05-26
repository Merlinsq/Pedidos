package com.example.pedi2.clases;

public class Pedido {
    private int id;
    private int clienteId;
    private int tipoPedidoId;
    private int productoId;
    private int cantidad;

    public Pedido(int id, int clienteId, int tipoPedidoId, int productoId, int cantidad) {
        this.id = id;
        this.clienteId = clienteId;
        this.tipoPedidoId = tipoPedidoId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getTipoPedidoId() {
        return tipoPedidoId;
    }

    public int getProductoId() {
        return productoId;
    }

    public int getCantidad() {
        return cantidad;
    }
}

