package com.example.fracci.crash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GeneralData {
    public static Car car;
    public static double speed = 0;

    private static Location[] location;

    protected static SensorManager sensorManager;
    protected Context context;

    public static boolean COLLISION = false;
    public static boolean MOVEMENT_IN_CHAOS = false;

    public static boolean BRAKING = false;
    public static boolean STOP = false;
    public static final int MODE_NO_MOVE = 0;
    public static final int MODE_LIGHT_MOVE = 1;
    public static final int MODE_NORMAL_MOVE = 2;
    public static final int MODE_FAST_MOVE = 3;
    private static int MODE = 0;

    private static double A = 10;
    private static final double G = 9.80665;

    // можно добавить анстройки к этип переменным
    static double maxDistance = 0.02;// 1 km  ~  0.018
    static double maxVector = 15;

    public static Map<Double, Set<Double>> crashesMap = new HashMap<Double, Set<Double>>();

    @SuppressLint("MissingPermission")
    public GeneralData(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        location = new Location[10];
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
        Log.i("LOCATION", "locationManager on");

        car = new Car(0, 0, 0.0);

    }

    public static void addSensor(int TYPE_SENSOR, int SPEED) {
        sensorManager.registerListener(new SensorListener(),
                sensorManager.getDefaultSensor(TYPE_SENSOR),
                SPEED);
        Log.i("SensorLidtener", "add " + TYPE_SENSOR);
    }

    public static int getMODE() {
        return MODE;
    }

    public static void setMODE(int MODE) {
        GeneralData.MODE = MODE;
    }

    public static void addCrashMap(ArrayList<Crash> crashList) {
        for (Crash crash : crashList) {
            Set<Double> value_long;
            if (crashesMap.containsKey(crash.latitude)) {
                if (crashesMap.get(crash.latitude).contains(crash.latitude)) continue;
                value_long = crashesMap.get(crash.latitude);
            } else {
                value_long = new HashSet<Double>();
            }
            value_long.add(crash.longitude);
            crashesMap.put(crash.latitude, value_long);
        }
    }

    static class SensorListener implements SensorEventListener {
        protected double[] last_xyz = new double[]{0, 0, 0};

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_GRAVITY:
                    boolean s1 = Math.abs(last_xyz[0]-sensorEvent.values[0])>6;
                    boolean s2 = Math.abs(last_xyz[1]-sensorEvent.values[1])>6;
                    boolean s3 = Math.abs(last_xyz[2]-sensorEvent.values[2])>6;
                    for (int i = 0; i<3; i++) {
                        last_xyz[i] = sensorEvent.values[i];
                    }
                    MOVEMENT_IN_CHAOS = (s1 && s2) || (s2 && s3) || (s1 && s3);
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    COLLISION = Math.abs(sensorEvent.values[0]) + Math.abs(sensorEvent.values[1]) + Math.abs(sensorEvent.values[2]) > (G + A);
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    private double zeroLat, zeroLong;

    LocationListener locationListener = new LocationListener() {
        int n = 0;

        @Override
        public void onLocationChanged(@NonNull Location location) {
            GeneralData.location[n%10] = location;
            car.latitude = location.getLatitude();
            car.longitude = location.getLongitude();
            speed = location.getSpeed();
            if (n != 0) {
                if (GeneralData.location[(n-1)%10].getSpeed() - speed > 10) {
                    BRAKING = true;
                }
                if (GeneralData.location[(n-1)%10].getSpeed() - speed < -1) {
                    BRAKING = false;
                }
                //вектор
                zeroLat = car.latitude - GeneralData.location[(n-1)%10].getLatitude();
                zeroLong = car.longitude - GeneralData.location[(n-1)%10].getLongitude();
                car.vector = zeroLat >= 0 ? Math.toDegrees(Math.atan(zeroLong / zeroLat)) : Math.toDegrees(Math.atan(zeroLong / zeroLat))+180;
                //zeroLat >= 0 ? Math.atan(zeroLong / zeroLat) : Math.atan(zeroLong / zeroLat)+180;
            //car.vector= SettingsFragment.angle();
            }
            if (speed < 1) {
                STOP = true;
            }
            Log.i("LOCATION", car.latitude + "\t" + car.longitude + "\t" + car.vector);

            n++;
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };
}