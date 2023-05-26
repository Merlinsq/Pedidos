package com.example.pedi2.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductoCantidad implements Parcelable {
    private Producto producto;
    private int cantidad;

    public ProductoCantidad(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }



    public static final Creator<ProductoCantidad> CREATOR = new Creator<ProductoCantidad>() {
        @Override
        public ProductoCantidad createFromParcel(Parcel in) {
            return new ProductoCantidad(in);
        }

        @Override
        public ProductoCantidad[] newArray(int size) {
            return new ProductoCantidad[size];
        }
    };

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) producto, flags); // No es necesario convertir a Parcelable
        dest.writeInt(cantidad);
    }

    protected ProductoCantidad(Parcel in) {
        producto = in.readParcelable(Producto.class.getClassLoader()); // No es necesario convertir a Parcelable
        cantidad = in.readInt();
    }



    public char getCodigo() {
        return 0;
    }
}

