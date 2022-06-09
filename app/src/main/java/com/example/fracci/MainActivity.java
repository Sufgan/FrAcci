package com.example.fracci;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

    HomeFragment homeM;
    SettingsFragment setM;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeM=new HomeFragment();
        setM=new SettingsFragment();

        //Объявляем элементы дизайна(кнопки,картинки)----------------------------------------------------------
        Button btn_start=findViewById(R.id.btn_start);
        mImageView = (ImageView) findViewById(R.id.imageView1);
        mImageView.setImageResource(R.drawable.main_img);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }
                startService(new Intent(getApplicationContext(), MainService.class));
            }
        });


        //Объявляем toolbar & navDrawer-----------------------------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);

        //Подключаем toolbar+navDrawer------------------------------------------------------------------------
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;//or super.onOptionsItemSelected, false won't show menu
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.place_holder, setM).commit();
                return true;

            case R.id.nav_back_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.place_holder, homeM).commit();
                return true;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}