package com.example.gestortareas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestortareas.api.ApiClient;
import com.example.gestortareas.api.ApiService;
import com.example.gestortareas.models.Tarea;
import com.example.gestortareas.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarTareaActivity extends AppCompatActivity {

    EditText txtTituloEdit, txtDescEdit, txtFechaEdit, txtEstadoEdit;
    Button btnGuardarCambios;

    ApiService api;
    SessionManager session;

    int idTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);

        api = ApiClient.getClient().create(ApiService.class);
        session = new SessionManager(this);

        txtTituloEdit = findViewById(R.id.edtTitulo);
        txtDescEdit = findViewById(R.id.edtDescripcion);
        txtFechaEdit = findViewById(R.id.edtFecha);

        btnGuardarCambios = findViewById(R.id.btnGuardar);

        // recibir datos del adapter
        idTarea = getIntent().getIntExtra("id", 0);
        txtTituloEdit.setText(getIntent().getStringExtra("titulo"));
        txtDescEdit.setText(getIntent().getStringExtra("descripcion"));
        txtFechaEdit.setText(getIntent().getStringExtra("fecha_limite"));
        txtEstadoEdit.setText(getIntent().getStringExtra("estado"));

        btnGuardarCambios.setOnClickListener(v -> guardarCambios());
    }

    private void guardarCambios() {

        String token = "Bearer " + session.getToken();

        Tarea t = new Tarea(
                txtTituloEdit.getText().toString(),
                txtDescEdit.getText().toString(),
                txtFechaEdit.getText().toString(),
                txtEstadoEdit.getText().toString()
        );

        api.updateTarea(token, idTarea, t).enqueue(new Callback<Tarea>() {
            @Override
            public void onResponse(Call<Tarea> call, Response<Tarea> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(EditarTareaActivity.this,
                            "Error al actualizar", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(EditarTareaActivity.this,
                        "Tarea actualizada", Toast.LENGTH_SHORT).show();

                finish();
            }

            @Override
            public void onFailure(Call<Tarea> call, Throwable t) {
                Toast.makeText(EditarTareaActivity.this,
                        "Fallo: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
