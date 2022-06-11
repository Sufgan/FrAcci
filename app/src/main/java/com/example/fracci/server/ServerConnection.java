package com.example.fracci.server;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fracci.crash.Car;
import com.example.fracci.crash.Crash;
import com.example.fracci.crash.CrashInterface;
import com.example.fracci.crash.GeneralData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerConnection {
    Retrofit retrofit;
    public CrashInterface serv;
    public static Map<Double, Set<Double>> crashesNearMap = new HashMap<Double, Set<Double>>();
    private Context context;

    public ServerConnection(Context context){
        retrofit=new Retrofit.Builder().baseUrl(" https://fracci.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build();
        serv=retrofit.create(CrashInterface.class);
        this.context=context;
    }

    public void get(View view) {
        Call<ArrayList<Crash>> call_get=serv.getCoords();
        call_get.enqueue(new Callback<ArrayList<Crash>>() {
            @Override
            public void onResponse(Call<ArrayList<Crash>> call, Response<ArrayList<Crash>> response) {
                ArrayList<Crash> list=response.body();
                GeneralData.addCrashMap(list);
                //chat.setText(list.get(list.size()-1).lat+" "+list.get(list.size()-1).lng+" ");
            }
            //http://192.168.0.160:8080/java/test     @Override
            public void onFailure(Call<ArrayList<Crash>> call, Throwable t) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void getApplicationContext(Context context){
        this.context=context;
    }

    public void send(View view) {
        Call<Void> call=serv.sendCoords(String.valueOf(GeneralData.car.latitude),String.valueOf(GeneralData.car.longitude));
        class MyThread extends Thread{
            @Override
            public void run() {
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        MyThread t1=new MyThread();
        t1.start();
    }

}