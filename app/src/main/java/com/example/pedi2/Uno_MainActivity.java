package com.example.pedi2;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Uno_MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uno_activity_main);

        etUsername = findViewById(R.id.edtextUser);
        etPassword = findViewById(R.id.etpContra);
        Button btnLogin = findViewById(R.id.btAcepta);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            // Verificar si los datos de usuario y contraseña son correctos
            if (validateCredentials(username, password)) {
                // Datos de inicio de sesión correctos, navegar a la segunda pantalla
                Intent intent = new Intent(Uno_MainActivity.this, Dos_SegundaPantalla.class);
                startActivity(intent);
            } else {
                // Datos de inicio de sesión incorrectos, mostrar mensaje de error
                Toast.makeText(Uno_MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateCredentials(String username, String password) {
        // Verificar si los datos de usuario y contraseña son correctos
        // Aquí puedes realizar la validación con la base de datos u otro método de tu elección
        // Por ejemplo, puedes comparar los valores ingresados con los almacenados en la base de datos

        // Validar el usuario y la contraseña de forma estática
        return username.equals("msandi") && password.equals("msandi2023");
    }
}