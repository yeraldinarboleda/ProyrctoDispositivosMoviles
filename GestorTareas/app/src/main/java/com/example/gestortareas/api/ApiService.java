package com.example.gestortareas.api;

import com.example.gestortareas.models.LoginResponse;
import com.example.gestortareas.models.Tarea;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @POST("register")
    Call<Map<String, String>> register(@Body Map<String, String> body);

    @POST("login")
    Call<LoginResponse> login(@Body Map<String, String> body);

    @GET("items")
    Call<List<Tarea>> getTareas(@Header("Authorization") String token);

    @POST("items")
    Call<Map<String, String>> crearTarea(
            @Header("Authorization") String token,
            @Body Tarea tarea
    );

    @PUT("items/{id}")
    Call<Tarea> updateTarea(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Body Tarea tarea
    );

    @DELETE("items/{id}")
    Call<Void> deleteTarea(
            @Header("Authorization") String token,
            @Path("id") int id
    );

}
