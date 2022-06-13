package com.example.fracci;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.fracci.crash.CrashChecker;
import com.example.fracci.crash.CrashRadar;
import com.example.fracci.crash.GeneralData;
import com.example.fracci.server.ServerConnection;

public class MainService extends Service {
    public static ServerConnection serverConnection;

    @Override
    public void onCreate() {
        Toast.makeText(this, "well done :)", Toast.LENGTH_SHORT).show();
        serverConnection = new ServerConnection(this);
        new GeneralData(getApplicationContext());
        new Thread(() -> new CrashChecker()).start();
        new Thread(() -> new CrashRadar()).start();
        // загрузка карты аварий
        new Thread(() -> {
            while(true) {
                serverConnection.get(null);
                try {
                    Thread.sleep(1000 * 60 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "I don't work :(", Toast.LENGTH_SHORT).show();
//        startService(new Intent(getApplicationContext(), MainService.class)); вылетало
    }

}