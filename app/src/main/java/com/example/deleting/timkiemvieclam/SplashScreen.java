package com.example.deleting.timkiemvieclam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.deleting.timkiemvieclam.Util.ConnectivityReceiver;
import com.example.deleting.timkiemvieclam.Util.Initialization;

/**
 * Created by Deleting on 3/23/2017.
 */

public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    public static String message;
    private static int tmp_check;
    private static Initialization initialization;
    final Bundle data = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectivityReceiver sconn = new ConnectivityReceiver();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_screen_layout);
        initialization = new Initialization();
        if (tmp_check != 1) {
            initialization.Create(this);
            Log.d("test", "data ban đã được tạo");
            tmp_check = 1;
        } else {
            Log.d("test", "data ban đã được tạo, không thể tạo lại");
        }

        boolean checkconn = sconn.isConnected(this);
        data.putBoolean("check", checkconn);
        if (checkconn == true) {
            Log.d("test", "connected");
        } else {
            Log.d("test", "not connected");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
