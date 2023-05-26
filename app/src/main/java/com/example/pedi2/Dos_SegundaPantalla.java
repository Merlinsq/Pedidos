package com.example.pedi2;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pedi2.clases.Cliente;
import com.example.pedi2.clases.MiBaseDeDatosHelper;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Dos_SegundaPantalla extends AppCompatActivity {

    private StringBuilder clientesInfo;
    private MiBaseDeDatosHelper dbHelper;
    private TextView tvClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dos_segunda_activity);

        dbHelper = new MiBaseDeDatosHelper(this);

        Button btnCargarClientes = findViewById(R.id.btagrgarcli);
        btnCargarClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarClientesDesdeExcel();
            }
        });


        Button btnCargarProductos = findViewById(R.id.button3);
        btnCargarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarProductosDesdeExcel();
            }
        });



        Button btnSiguiente = findViewById(R.id.button5);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Dos_SegundaPantalla.this, Tres_TerceraPantalla.class);
                startActivity(intent);
            }
        });


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button BorrarBase = findViewById(R.id.Borrar);

        BorrarBase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dbHelper.borrarTablaClientes();
                dbHelper.borrarTablaProductos();
            }
        });
    }

    private void cargarClientesDesdeExcel() {
        try {
            System.setProperty("javax.xml.stream.supportDTD", "false");

            // Obtén una instancia del objeto Workbook para leer el archivo Excel
            InputStream inputStream = getAssets().open("clientes.xls");
            Workbook workbook;

            if (inputStream.markSupported()) {
                workbook = new HSSFWorkbook(inputStream); // Utilizar HSSFWorkbook para archivos XLS
            } else {
                workbook = new XSSFWorkbook(inputStream); // Utilizar XSSFWorkbook para archivos XLSX
            }

            // Obtén la primera hoja de cálculo del archivo Excel
            Sheet sheet = workbook.getSheetAt(0);

            // Recorre las filas de la hoja de cálculo
            for (Row row : sheet) {

                // Obtén los valores de cada celda en la fila
                Cell codigoCell = row.getCell(0);
                String codigo = codigoCell.getStringCellValue();


                Cell nombreCell = row.getCell(1);
                String nombre = nombreCell.getStringCellValue();

                Cell direccionCell = row.getCell(2);
                String direccion = direccionCell.getStringCellValue();

                Cell clCell = row.getCell(3);
                String cl = clCell.getStringCellValue();

                // Inserta los datos en la base de datos
                insertarClienteEnBD(codigo, nombre, direccion, cl);

                System.out.println("Código:MSQ " + codigo);
                System.out.println("Nombre: MSQ " + nombre);
                System.out.println("Dirección:MSQ " + direccion);
                System.out.println("CL:MSQ " + cl);
                System.out.println();
            }




            // Cierra el archivo Excel y el flujo de entrada
            workbook.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void cargarProductosDesdeExcel() {
        try {
            System.setProperty("javax.xml.stream.supportDTD", "false");

            // Obtén una instancia del objeto Workbook para leer el archivo Excel
            InputStream inputStream = getAssets().open("productos.xls");
            Workbook workbook;

            if (inputStream.markSupported()) {
                workbook = new HSSFWorkbook(inputStream); // Utilizar HSSFWorkbook para archivos XLS
            } else {
                workbook = new XSSFWorkbook(inputStream); // Utilizar XSSFWorkbook para archivos XLSX
            }

            // Obtén la primera hoja de cálculo del archivo Excel
            Sheet sheet = workbook.getSheetAt(0);

            // Recorre las filas de la hoja de cálculo
            for (Row row : sheet) {
                // Obtén los valores de cada celda en la fila
                Cell codigoCell = row.getCell(0);
                String codigo = codigoCell.getStringCellValue();

                Cell descripcionCell = row.getCell(1);
                String descripcion = descripcionCell.getStringCellValue();



                // Inserta los datos en la base de datos
                dbHelper.insertarProductosEnBD(codigo, descripcion);

                System.out.println("Código: " + codigo);
                System.out.println("Descripción: " + descripcion);
                System.out.println();
            }

            // Cierra el archivo Excel y el flujo de entrada
            workbook.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void insertarClienteEnBD(String codigo, String nombre, String direccion, String cl) {
        long clienteId = dbHelper.insertarClienteEnBD(codigo, nombre, direccion, cl);
        // Realizar acciones adicionales después de insertar el cliente en la base de datos
        // ...

    }

    private void mostrarClientesDesdeBD() {
        List<Cliente> clientes = dbHelper.obtenerClientesDesdeBD();
        StringBuilder clientesInfo = new StringBuilder();

        for (Cliente cliente : clientes) {
            clientesInfo.append("Código: ").append(cliente.getCodigo()).append("\n");
            clientesInfo.append("Nombre: ").append(cliente.getNombre()).append("\n");
            clientesInfo.append("Dirección: ").append(cliente.getDireccion()).append("\n");
            clientesInfo.append("CL: ").append(cliente.getCl()).append("\n\n");

            String codigo = cliente.getCodigo();
            String nombre = cliente.getNombre();
            String direccion = cliente.getDireccion();
            String cl = cliente.getCl();

            System.out.println("Código: " + codigo);
            System.out.println("Nombre: " + nombre);
            System.out.println("Dirección: " + direccion);
            System.out.println("CL: " + cl);
            System.out.println();


        }
        Log.d("Clientes", clientesInfo.toString());
        tvClientes.setText(clientesInfo.toString());

    }
}