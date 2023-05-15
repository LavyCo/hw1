package com.example.hw2;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

public class MySp {

    private final String SP_FILE_NAME = "MY_SP_FILE";
    private SharedPreferences prefs = null;
    public static final String SP_KEY_NAME = "SP_KEY_NAME";
    public static final String SP_KEY_SCORE = "SP_KEY_SCORE";
    private static MySp _instance = null;


    private MySp(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void initHelper(Context context) {
        if (_instance == null) {
            _instance = new MySp(context);
        }
    }

    public static MySp get_my_SP() {
        return _instance;
    }



    public void putStringToSP(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringFromSP(String key, String def) {
        return prefs.getString(key, def);
    }




}
