package com.example.fracci.crash;

public class Car {
    public double latitude;
    public double longitude;
    public Double vector;

    public Car(double latitude, double longitude, Double vector) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.vector = vector;
    }

    public boolean proximity(Car car, double maxProximity) {// перевости из радианов
        boolean result = false;
        double zeroLatitude = car.latitude - this.latitude;
        double zeroLongitude = car.longitude - this.longitude;
        if (Math.pow(zeroLatitude, 2) + Math.pow(zeroLongitude, 2) <  Math.pow(maxProximity, 2)) {// находится ли в кругу
            double atan = Math.atan(zeroLongitude == 0 ? 0.001 : zeroLongitude / zeroLatitude);// вычисление угла вектора езды
            result = Math.abs((zeroLatitude>0 ? atan : atan + 180) - this.vector) <= 15;// сопоставление с местонахождением аварии; 15 - максимальное отклонение
        }
        return result;
    }

    public double distanceTo(Car car) {
        double zeroLatitude = car.latitude - this.latitude;
        double zeroLongitude = car.longitude - this.longitude;
        return Math.sqrt(Math.pow(zeroLatitude, 2) + Math.pow(zeroLongitude, 2));
    }

    @Override
    public String toString() {
        return "Car " +
                " lat " + latitude +
                " long " + longitude +
                " v " + vector;
    }
}
