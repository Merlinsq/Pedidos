package com.example.pedi2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pedi2.clases.MiBaseDeDatosHelper;
import com.example.pedi2.clases.Producto;

import java.util.ArrayList;
import java.util.List;

public class Cuatro_muestraProductos extends AppCompatActivity {

    private ListView listViewProductos;
    private Cinco_adaptadorProducto adapter;
    public static int cantidad;
    private AlertDialog alertDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuatro_muestraactivity);

        listViewProductos = findViewById(R.id.listViewProductos);
        adapter = new Cinco_adaptadorProducto(this, new ArrayList<>());

        // Obtener los productos de la base de datos y agregarlos al adaptador
        MiBaseDeDatosHelper dbHelper = new MiBaseDeDatosHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM productos", null);
        List<Producto> productos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));

                Producto producto = new Producto(id, codigo, descripcion);
                productos.add(producto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        adapter.addAll(productos);
        listViewProductos.setAdapter(adapter);

        listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Producto productoSeleccionado = adapter.getItem(position);
                mostrarDialogoCantidad(productoSeleccionado);
                System.out.println("Producto: " + productoSeleccionado);
            }

        });

    }

    private void mostrarDialogoCantidad(final Producto productoSeleccionado) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Cuatro_muestraProductos.this);
        //builder.setTitle("Cantidad Vendida");
        //builder.setMessage("Ingrese la cantidad:");

        // Inflar el layout del diálogo personalizado
        View dialogView = LayoutInflater.from(Cuatro_muestraProductos.this).inflate(R.layout.dialog_custom, null);
        builder.setView(dialogView);

        // Obtener referencias a los elementos del diálogo
        TextView textViewTitle = dialogView.findViewById(R.id.textViewTitle);
        TextView textViewMessage = dialogView.findViewById(R.id.textViewMessage);
        EditText editTextCantidad = dialogView.findViewById(R.id.editTextCantidad);
        Button buttonAceptar = dialogView.findViewById(R.id.buttonAceptar);

        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cantidadString = editTextCantidad.getText().toString();
                int cantidad1 = Integer.parseInt(cantidadString);

                // Obtener el código del producto seleccionado
                String codigo = productoSeleccionado.getCodigo();
                String descripcion = productoSeleccionado.getDescripcion();

                Intent intent = new Intent();
                intent.putExtra("cantidadSeleccionada", cantidad1);
                intent.putExtra("codigoSeleccionado", codigo);
                intent.putExtra("descripcionSeleccionada", descripcion);
                setResult(RESULT_OK, intent);
                cantidad = cantidad1;

                finish();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}