package com.example.fracci.crash;

import static com.example.fracci.crash.GeneralData.car;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.fracci.MainService;

public class CrashChecker{
    private boolean RUN = true;

    public CrashChecker() {
        GeneralData.addSensor(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL);
        GeneralData.addSensor(Sensor.TYPE_GRAVITY, SensorManager.SENSOR_DELAY_NORMAL);
        while(RUN) {
            try {
                Log.i("TEST", "check me to crash");
                cycle();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cycle() throws InterruptedException {
        while (GeneralData.BRAKING) {
            if (GeneralData.COLLISION || GeneralData.MOVEMENT_IN_CHAOS) {
                Thread.sleep(1000 * 10);
                if (GeneralData.STOP) {
                    Log.i("CRASH", "new detected\t" + car.latitude + "\t" + car.longitude);
                    MainService.serverConnection.send(null);
                }
            }
        }
        Thread.sleep(1000);
    }

    public void stop() {
        RUN = false;
    }
}