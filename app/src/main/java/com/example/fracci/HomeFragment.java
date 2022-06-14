package com.example.fracci;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment {
    View view;
    GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        createMapView();
        return view;
    }

    private void createMapView() {
        try {
            if (null == googleMap) {
                ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        HomeFragment.this.googleMap = googleMap;
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