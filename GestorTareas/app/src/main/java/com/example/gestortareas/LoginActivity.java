package com.example.gestortareas;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.example.gestortareas.R;
import com.example.gestortareas.api.*;
import com.example.gestortareas.api.ApiClient;
import com.example.gestortareas.models.LoginResponse;
import com.example.gestortareas.utils.SessionManager;
import java.util.HashMap;
import java.util.Map;
import retrofit2.*;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail, txtPass;
    Button btnLogin, btnIrRegistro;
    ApiService api;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnIrRegistro = findViewById(R.id.btnIrRegistro);

        api = ApiClient.getClient().create(ApiService.class);
        session = new SessionManager(this);

        btnLogin.setOnClickListener(v -> login());
        btnIrRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }

    void login() {
        Map<String, String> body = new HashMap<>();
        body.put("email", txtEmail.getText().toString());
        body.put("password", txtPass.getText().toString());

        api.login(body).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> res) {
                if (res.isSuccessful()) {
                    session.saveToken(res.body().token);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {}
        });
    }
}
