package com.example.gestortareas;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

import com.example.gestortareas.R;
import com.example.gestortareas.api.*;
import com.example.gestortareas.models.Tarea;
import com.example.gestortareas.utils.SessionManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.*;

public class NuevaTareaActivity extends AppCompatActivity {

    EditText txtTitulo, txtDesc, txtFecha;
    Button btnGuardar;
    ApiService api;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDesc = findViewById(R.id.txtDescripcion);
        txtFecha = findViewById(R.id.txtFecha);
        btnGuardar = findViewById(R.id.btnGuardar);

        api = ApiClient.getClient().create(ApiService.class);
        session = new SessionManager(this);

        btnGuardar.setOnClickListener(v -> guardar());
    }

    void guardar() {
        Tarea t = new Tarea(
                txtTitulo.getText().toString(),
                txtDesc.getText().toString(),
                txtFecha.getText().toString(),
                "pendiente"
        );

        api.crearTarea(session.getToken(), t).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                finish();
            }
            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {}
        });
    }
}
