package com.example.fracci;
import static android.media.MediaPlayer.create;

import static com.example.fracci.MainActivity.context;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.app.Fragment;

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

    public SettingsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        EditText edit_adress = view.findViewById(R.id.edit_adress);
        mSettings = context.getSharedPreferences(APP_PREFERENCES,context.MODE_PRIVATE);
        btn_set = view.findViewById(R.id.btn_set);
        mPlayer = MediaPlayer.create(context, R.raw.trevognia_signalka);
        radioGroup = view.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        Toast.makeText(context, "Ничего не выбрано",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_blue:
                        if (mPlayer.isPlaying()) {
                            mPlayer.stop();
                        }
                        rawMusic=R.raw.trevognia_signalka;
                        mPlayer= mPlayer.create(context, rawMusic);
                        mPlayer.start();
                        break;
                    case R.id.radio_green:

                        if (mPlayer.isPlaying()) {
                            mPlayer.stop();
                        }
                        rawMusic=R.raw.pojarnaya;
                        mPlayer= mPlayer.create(context, rawMusic);
                        mPlayer.start();
                        break;
                    case R.id.radio_gray:
                        if (mPlayer.isPlaying()) {
                            mPlayer.stop();
                        }
                        rawMusic=R.raw.gorn;
                        mPlayer= mPlayer.create(context, rawMusic);
                        mPlayer.start();
                        break;
                }
            }
        });

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strAdress = edit_adress.getText().toString();
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_ADRESS, strAdress);
                editor.apply();
            }
        });

        if(mSettings.contains(APP_PREFERENCES_ADRESS)) {
            edit_adress.setText(mSettings.getString(APP_PREFERENCES_ADRESS, ""));
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    public MediaPlayer mPlayer;
    public int rawMusic= R.raw.trevognia_signalka;
    public View.OnClickListener radioButtonClickListener;
    public RadioGroup radioGroup;

    Button btn_set;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_ADRESS = "https://fracci.herokuapp.com/";
    SharedPreferences mSettings;

}