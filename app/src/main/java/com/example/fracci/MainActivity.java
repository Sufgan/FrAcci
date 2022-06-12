package com.example.fracci;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.fracci.server.HomeFragment;
import com.example.fracci.server.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.place_holder, new HomeFragment())
                .commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.place_holder, new HomeFragment())
                        .commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.place_holder, new SettingsFragment())
                        .commit();
                break;
        }
        return false;
    }
}
//    getSupportFragmentManager().beginTransaction().replace(R.id.place_holder, setM).commit();