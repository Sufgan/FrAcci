package com.example.fracci;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static Context context;
    SharedPreferences sharedPreferences;

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
    }

    void start() {
        NavigationView nv = findViewById(R.id.navigationView);
        nv.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.place_holder, new HomeFragment())
                .commit();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", true);
        editor.commit();
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
