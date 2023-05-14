package com.example.hw2;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class ManualFunctions {
    private Context context;
    private static ManualFunctions _instance = null;

    public ManualFunctions(Context context) {
        this.context = context;
    }

    public static void initHelper(Context context) {
        if (_instance == null) {
            _instance = new ManualFunctions(context);
        }
    }

    public void toast(String st) {
        Toast.makeText(context, st, Toast.LENGTH_SHORT).show();
    }

    public void vibrate() {
        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }


    public static ManualFunctions getGameService() {
        return _instance;
    }
}
