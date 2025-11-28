package com.example.gestortareas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import com.example.gestortareas.R;
import com.example.gestortareas.api.*;

import java.util.HashMap;
import java.util.Map;
import retrofit2.*;

public class RegisterActivity extends AppCompatActivity {

    EditText txtNombre, txtEmail, txtPass;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtNombre = findViewById(R.id.txtNombre);
        txtEmail = findViewById(R.id.txtEmailR);
        txtPass = findViewById(R.id.txtPassR);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        ApiService api = ApiClient.getClient().create(ApiService.class);

        btnRegistrar.setOnClickListener(v -> {
            Map<String, String> body = new HashMap<>();
            body.put("nombre", txtNombre.getText().toString());
            body.put("email", txtEmail.getText().toString());
            body.put("password", txtPass.getText().toString());

            api.register(body).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this,
                                "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(RegisterActivity.this,
                            "Registro exitoso", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this,
                            "Fallo: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        });
    }
}
