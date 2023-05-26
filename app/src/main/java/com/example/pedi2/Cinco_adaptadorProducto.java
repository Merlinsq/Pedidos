package com.example.pedi2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pedi2.clases.Producto;

import java.util.List;

public class Cinco_adaptadorProducto extends ArrayAdapter<Producto> {

    public Cinco_adaptadorProducto(Context context, List<Producto> productos) {
        super(context, 0, productos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.cinco_adaptadorproducto, parent, false);
        }

        Producto producto = getItem(position);

        TextView textViewCodigo = itemView.findViewById(R.id.textViewCodigo);
        TextView textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);

        textViewCodigo.setText("Código: " + producto.getCodigo());
        textViewDescripcion.setText("Nombre: " + producto.getDescripcion());

        return itemView;
    }
}
