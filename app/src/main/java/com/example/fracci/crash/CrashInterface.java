package com.example.fracci.crash;

import java.util.ArrayList;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CrashInterface {
    @GET("/test")
    //у Query пишем в кавычках то что в эклипсе Query ?firstName=Kat&lastName=Jojo
    public Call<LinkedList<Crash>> test(@Query("latt") String latt, @Query("lngg") String lngg);
    @GET("/coords/send")
    public Call<Void> sendCoords(@Query("latt") String latt, @Query("lngg") String lngg);
    @GET("/coords/get")
    public Call<ArrayList<Crash>> getCoords();

}


