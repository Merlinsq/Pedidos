package com.example.pedi2;

import static java.security.AccessController.getContext;

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

public class CustomListAdapter extends ArrayAdapter<Producto> {

    public CustomListAdapter(Context context, List<Producto> productos) {
        super(context, 0, productos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }

        Producto producto = getItem(position);

        TextView textViewCodigo = convertView.findViewById(R.id.textViewCodigo);
        TextView textViewDescripcion = convertView.findViewById(R.id.textViewDescripcion);
        TextView textViewCantidad = convertView.findViewById(R.id.textViewCantidad);

        textViewCodigo.setText(producto.getCodigo());
        textViewDescripcion.setText(producto.getDescripcion());


        return convertView;
    }
}
