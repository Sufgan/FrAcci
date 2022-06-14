package com.example.fracci.crash;

import static com.example.fracci.crash.GeneralData.car;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fracci.MainActivity;
import com.example.fracci.R;
import com.example.fracci.SettingsFragment;

public class CrashRadar {
    private boolean RUN = true;
    Crash crash;
    static String CHANNEL_ID = "Cat channel";
    static Context context;

    public CrashRadar() {
        crash = new Crash(0, 0);
        while (RUN) {
            try {
                cycle();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    CrashRadar(Context context) {
        this.context = context;
    }

    public void cycle() throws InterruptedException {
        Log.i("TEST", "check crash");
        checkCrash();
        Thread.sleep((long) ((85 - GeneralData.speed%80) * 1000));
    }

    void checkCrash() {
        for (Double x : GeneralData.crashesMap.keySet()) {
            for (Double y : GeneralData.crashesMap.get(x)) {
                if (isInRadius(crash = new Crash(x, y))) {
                    if (isInVector()) {
                        pushNoti(MainActivity.context);
                    }
                }
            }
        }
    }

    private double zeroLat, zeroLong;

    private boolean isInVector() {
        double arcAngle = Math.toDegrees(Math.atan(zeroLong / zeroLat));
        arcAngle = zeroLat >= 0 ? arcAngle : arcAngle+180;
        return Math.abs(car.vector - arcAngle) < GeneralData.maxVector;
    }

    private boolean isInRadius(Crash crash) {
        zeroLat = crash.latitude - car.latitude;
        zeroLong = crash.longitude - car.longitude;
        return Math.abs(zeroLat) < GeneralData.maxDistance && Math.abs(zeroLong) < GeneralData.maxDistance;
    }

    public static void pushNoti(Context context) {
//        SettingsFragment setti=new SettingsFragment(context);
//        setti.mPlayer= MediaPlayer.create(context, setti.rawMusic);
//        setti.mPlayer.start();

    }

//    protected static void pushNoti() {
//        Log.i("CrashRadar", "we find the crash");
//        // посылка уведомления об аварии
//        createNotificationChannel();
//
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(context, CHANNEL_ID)
//                        .setSmallIcon(R.drawable.ic_baseline_warning_24)
//                        .setContentTitle("ОСТОРОЖНО")
//                        .setContentText("В радиусе 1000 м авария")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager =
//                NotificationManagerCompat.from(context);
//        notificationManager.notify(1, builder.build());
//    }
//
//    private static void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "FrAcci";
//            String description = "channel";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

    public void stop() {
        RUN = false;
    }
}