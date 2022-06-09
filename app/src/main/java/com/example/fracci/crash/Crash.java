package com.example.fracci.crash;

public class Crash extends Car{

    public Crash(double latitude, double longitude) {
        super(latitude, longitude, null);
    }

    @Override
    public String toString() {
        return "Crash " +
                "lat " + latitude +
                " long " + longitude;
    }

    public Crash setLat(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Crash setLong(double longitude) {
        this.longitude = longitude;
        return this;
    }
}
