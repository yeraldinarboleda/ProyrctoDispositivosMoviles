package com.example.gestortareas.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context ctx) {
        prefs = ctx.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveToken(String token) {
        editor.putString("TOKEN", token);
        editor.apply();
    }

    public String getToken() {
        return prefs.getString("TOKEN", null);
    }

    public void logout(){
        editor.clear();
        editor.apply();
    }
}
