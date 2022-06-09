package com.example.fracci.server;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fracci.MainService;
import com.example.fracci.R;
import com.example.fracci.crash.Crash;
import com.example.fracci.crash.GeneralData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class SettingsFragment extends Fragment {
    private Context context;
    public static CrashAdapter adapter;
    float[] results_dist = new float[3];
    public static double dop_la1, dop_lo1, latitude2, longitude2, angle, latitude_dtp, longitude_dtp;

    public Geocoder gcd;
    Location location, locationB, locationA;
    Handler handler;
    public ArrayList<Double> latLng111 = new ArrayList();
    public ArrayList<Double> lonLng111 = new ArrayList();

    boolean permissions = false;
    private Double latLng1 = 0.0, lonLng1 = 0.0;

    private Timer mTimer;
    //private MyTimerTask mMyTimerTask, mMyTimerTask2;
    private MyTimerTask mMyTimerTaskRes;
    private MyTimerTaskGeo mMyTimerTaskGeo;
    private Location location2;
TextView lattitude1,longitude1,angle1,speed1;
    public SettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //задаем разметку фрагменту
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        //ну и контекст, так как фрагменты не содержат собственного
        context = view.getContext();
        SettingsFragment fb = new SettingsFragment();
        lattitude1=view.findViewById(R.id.latitude);
        longitude1=view.findViewById(R.id.longitude);
        angle1=view.findViewById(R.id.angle);
        speed1=view.findViewById(R.id.speed);
        // Output.latitude = view.findViewById(R.id.latitude);
        //  Output.latitude.setText("12345");
        Button button2 = (Button) view.findViewById(R.id.button2);
        // sufgan
       // setOutput();
        //  update();
        for (double[] latlon : new double[][]{{-260.080518, 30.344870}, {60.065965, 30.333228}, {60.080787, 0.341495}}) {
            Server.reportedCrash(new Crash(latlon[0], latlon[1]));
        }
        latLng111.add(0.0);
        lonLng111.add(0.0);
        // NastyaGordienko
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            permissions = true;
        }
        // ПОДГОТОВКА К ОПРЕДЕЛЕНИЮ МЕСТОПОЛОЖЕНИЯ
        LocationManager locationManager = (LocationManager) context.getSystemService(context.getApplicationContext().LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 900, 10, new MyLocationListener());

        mTimer = new Timer();
        mMyTimerTaskRes = new MyTimerTask();
        mMyTimerTaskGeo = new MyTimerTaskGeo();
        mTimer.schedule(mMyTimerTaskRes, 0, 500);
        mTimer.schedule(mMyTimerTaskGeo, 0, 1000);

        gcd = new Geocoder(context, Locale.getDefault());


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    show_coord(view);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void show_coord(View view) throws IOException {
       lattitude1.setText(GeneralData.car.latitude+" ");
        longitude1.setText(GeneralData.car.longitude+" ");
       //Toast.makeText(context, GeneralData.car.latitude+""+GeneralData.car.longitude, Toast.LENGTH_SHORT).show();
        /** mTimer = new Timer();
         mMyTimerTask = new MyTimerTask();
         mMyTimerTask2 = new MyTimerTask();

         if (permissions) {
         mTimer.schedule(mMyTimerTask, 1000, 5000);
         mTimer.schedule(mMyTimerTask2, 2500, 6000);
         }**/
//        List<Address> addresses = gcd.getFromLocation(latitude2, longitude2, 1);
//        String str = "";
//        if (addresses.size() > 0) {
//            str = addresses.get(0).getAddressLine(0);
//        }
//        Toast.makeText(context.getApplicationContext(), "" + latitude2 + "," + longitude2 + "/" + str, Toast.LENGTH_SHORT).show();
    }

    public void distanceBetween(View view) {
    }

    /*
            @Override
            public void onPointerCaptureChanged(boolean hasCapture) {
                super.onPointerCaptureChanged(hasCapture);
            }
    */
    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            SettingsFragment.this.location = location;

            latitude2 = location.getLatitude();
            longitude2 = location.getLongitude();
            latLng111.add(latitude2);
            lonLng111.add(longitude2);

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
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.start:
                    context.startService(new Intent(context.getApplicationContext(), MainService.class));
                    break;



                case R.id.speed_log:
                    double s = location.getSpeed();
                    Output.speed.setText("" + s);
                    Toast.makeText(context, "" + s, Toast.LENGTH_SHORT).show();
                    break;
            }
            // update();
        }
    }

    private static class CrashAdapter extends ArrayAdapter {
        LinkedList<Crash> crashes;

        public CrashAdapter(@NonNull Context context, int resource, LinkedList<Crash> crases) {
            super(context, resource);
            this.crashes = crases;
        }

        @Override
        public int getCount() {
            return crashes.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_crash, null);
            }
            TextView name = convertView.findViewById(R.id.name);
            name.setText("Авария " + position);
            TextView latitude = convertView.findViewById(R.id.latitude);
            latitude.setText("latitude: " + crashes.get(position).latitude);
            TextView longitude = convertView.findViewById(R.id.longitude);
            longitude.setText("longitude: " + crashes.get(position).longitude);
            TextView distance = convertView.findViewById(R.id.distance);
            distance.setText("" + GeneralData.car.distanceTo(crashes.get(position)));
            return convertView;
        }
    }

    public static class Output {
        public static TextView speed, angle, distTo;
        public static TextView latitude, longitude;
        public static TextView BRAKING, STOP, COLLISION, CHAOS;
    }


    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if (latLng111.size() >= 2) {
                latLng1 = latLng111.get(latLng111.size() - 2);
                lonLng1 = lonLng111.get(lonLng111.size() - 2);

                latLng111.remove(latLng111.size() - 2);
                lonLng111.remove(lonLng111.size() - 2);
                //logcat masive size
            }


        }
    }


    private class MyTimerTaskGeo extends TimerTask {
        @Override
        public void run() {
            //  Output.latitude.setText("" + latitude2);
            //  Output.longitude.setText("" + longitude2);
            //Output.angle.setText(""+angle());
            //Output.speed.setText(""+getSpeed(view));

        }

    }


}