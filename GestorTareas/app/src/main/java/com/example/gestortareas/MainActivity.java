package com.example.gestortareas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestortareas.adapters.TareasAdapter;
import com.example.gestortareas.api.ApiClient;
import com.example.gestortareas.api.ApiService;
import com.example.gestortareas.models.Tarea;
import com.example.gestortareas.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private Button btnNueva;
    private ApiService api;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        btnNueva = findViewById(R.id.btnNueva);

        api = ApiClient.getClient().create(ApiService.class);
        session = new SessionManager(this);

        // Si NO hay token, regresar al login
        if (session.getToken() == null || session.getToken().isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        btnNueva.setOnClickListener(v ->
                startActivity(new Intent(this, NuevaTareaActivity.class)));

        cargarTareas();
    }

    private void cargarTareas() {

        String token = session.getToken();
        if (!token.startsWith("Bearer ")) {
            token = "Bearer " + token;
        }

        api.getTareas(token).enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(Call<List<Tarea>> call, Response<List<Tarea>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Error al obtener tareas", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Tarea> lista = response.body();
                recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recycler.setAdapter(new TareasAdapter(lista, MainActivity.this));
            }

            @Override
            public void onFailure(Call<List<Tarea>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarTareas(); // recarga después de crear o editar
    }
}
