package com.example.fracci;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.fracci.MainActivity;
import com.example.fracci.MainService;
import com.example.fracci.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("", "b");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}