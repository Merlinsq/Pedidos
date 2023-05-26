package com.example.pedi2;

import static com.example.pedi2.Cuatro_muestraProductos.cantidad;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedi2.clases.ProductoCantidad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tres_TerceraPantalla extends AppCompatActivity {

    private static final int REQUEST_CODE_ACTIVIDAD_SIGUIENTE =0 ;
    private TextView fechaTextView;
    private EditText fechaEditText;
    private int Cantidad;
    private Calendar calendar;
    private static final int REQUEST_CODE_PRODUCT_SELECTION = 1;
    private ListView productListView;
    private ArrayList<String> productList;
    private ArrayAdapter<String> productAdapter;
    private Cuatro_muestraProductos cuatroMuestraProductos;

    private ArrayList<ProductoCantidad> productlist;
    private ArrayAdapter<String> productListViewAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tres_tercera_activity);

        // Inicialización de vistas y variables
        int cantidad = Cuatro_muestraProductos.cantidad;

        fechaEditText = findViewById(R.id.editTextDate2);
        productList = new ArrayList<>();
        calendar = Calendar.getInstance();
        fechaTextView = findViewById(R.id.tVfecha);
        productListView = findViewById(R.id.listViewProductos);
        productlist = new ArrayList<>();
        productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Collections.unmodifiableList(productList));
        productListView.setAdapter(productAdapter);

        // Configuración del evento click para mostrar el calendario
        fechaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }
        });

        // Configuración del botón para abrir la selección de productos
        Button selectProductButton = findViewById(R.id.AgregarPR);
        selectProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSeleccionProductos();
            }
        });

        // Obtención y formateo de la fecha actual
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaFormateada = formatoFecha.format(fechaActual);

        // Establecer la fecha formateada en el TextView
        fechaTextView.setText(fechaFormateada);

        // Obtención de datos enviados desde la actividad anterior (si existen)
        Intent intent = getIntent();
        ArrayList<ProductoCantidad> productosSeleccionados = intent.getParcelableArrayListExtra("productosSeleccionados");
        ArrayList<Integer> cantidades = intent.getIntegerArrayListExtra("cantidades");

        // Verificar si se enviaron productos y cantidades
        if (productosSeleccionados != null && cantidades != null) {
            // Asignar las cantidades a los productos seleccionados
            for (int i = 0; i < productosSeleccionados.size(); i++) {
                ProductoCantidad productoCantidad = productosSeleccionados.get(i);
                int cantidad1 = cantidades.get(i);
                productoCantidad.setCantidad(cantidad);
                productlist.add(productoCantidad);
            }
        }
    }

    // Método para mostrar el calendario y seleccionar una fecha
    public void mostrarCalendario() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                actualizarFechaEditText();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void actualizarFechaEditText() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaFormateada = formatoFecha.format(calendar.getTime());
        fechaEditText.setText(fechaFormateada);
    }

    private void abrirSeleccionProductos() {
        Intent intent = new Intent(Tres_TerceraPantalla.this, Cuatro_muestraProductos.class);
        startActivityForResult(intent, REQUEST_CODE_PRODUCT_SELECTION);
        productAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PRODUCT_SELECTION && resultCode == Activity.RESULT_OK) {
            ArrayList<ProductoCantidad> productosSeleccionados = data.getParcelableArrayListExtra("productosSeleccionados");

            if (requestCode == REQUEST_CODE_ACTIVIDAD_SIGUIENTE && resultCode == RESULT_OK) {
                int cantidadSeleccionada = data.getIntExtra("cantidadSeleccionada", 0);
                // Realiza la acción deseada con la cantidad seleccionada
            }


            Toast.makeText(this, "Producto seleccionado: " + productosSeleccionados +
                    "\nCantidad: " + cantidad, Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> obtenerProductosFiltrados() {
        List<String> productosFiltrados = new ArrayList<>();

        // Lógica para obtener los datos de los productos filtrados
        // ...

        // Asegurarse de que la lista no sea nula
        if (productosFiltrados == null) {
            productosFiltrados = new ArrayList<>();
            productAdapter.notifyDataSetChanged();
        }

        return productosFiltrados;
    }


    private void iniciarSiguienteActividad() {
        Intent intent = new Intent(Tres_TerceraPantalla.this, Cinco_adaptadorProducto.class);
        intent.putExtra("cantidadSeleccionada", cantidad);
        // Agrega otros datos necesarios al intent si los tienes
        startActivityForResult(intent, REQUEST_CODE_ACTIVIDAD_SIGUIENTE);
    }

}
