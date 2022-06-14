package com.example.fracci;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static Context context;
    SharedPreferences sharedPreferences;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        sharedPreferences = getSharedPreferences("FrAcci", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("firstStart", true)) {
            setContentView(R.layout.first_aktivity);
            findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContentView(R.layout.activity_main);
                    MainActivity.this.startService(new Intent(MainActivity.this, MainService.class));
                    start();
                }
            });

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstStart", false);
            editor.commit();
        } else {
            setContentView(R.layout.activity_main);
            startService(new Intent(MainActivity.this, MainService.class));
            start();
        }

        createMapView();
    }

    void start() {
        getFragmentManager().beginTransaction()
                .replace(R.id.place_holder, new HomeFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", true);
        editor.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                //getSupportFragmentManager().beginTransaction().replace(R.id.place_holder, setM).commit();
                getFragmentManager().beginTransaction()
                        .replace(R.id.place_holder, new SettingsFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.nav_home:
                getFragmentManager().beginTransaction()
                        .replace(R.id.place_holder, new HomeFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                //    getSupportFragmentManager().beginTransaction().replace(R.id.place_holder, homeM).commit();
                break;
        }
        return true;
    }

    private void createMapView() {
        try {
            if (null == googleMap) {
                ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        MainActivity.this.googleMap = googleMap;
                        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(60.05282377, 30.33493361))
                                .zoom(20)
                                .bearing(0)
                                .tilt(45)
                                .build();
                        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
                    }
                });
            }
        } catch (NullPointerException exception) {
            Log.e("mapApp", exception.toString());
        }

    }
    public void go(View view) {
        if (null != googleMap) {
            String[] latLng = new String[2];
            latLng[0]="60.05282377";
            latLng[1]="30.33493361";
            double latitude = Double.parseDouble(latLng[0]);
            double longitude = Double.parseDouble(latLng[1]);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title("Mark")
                    .draggable(false)
            );
        }
    }

}
