package com.example.fracci.server;

import com.example.fracci.crash.Car;
import com.example.fracci.crash.Crash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Server {
    private static Map<Double, Set<Double>> crashesMap = new HashMap<Double, Set<Double>>();

    public static Crash reportedCrash(Crash crash) {
        Set<Double> value_long;
        if (crashesMap.containsKey(crash.latitude)) {
            value_long = crashesMap.get(crash.latitude);
        } else {
            value_long = new HashSet<Double>();
        }
        value_long.add(crash.longitude);
        crashesMap.put(crash.latitude, value_long);
        return crash;
    }

    static Map<Double, Set<Double>> getCrashes(Car car, double maxProximity) {
        Map<Double, Set<Double>> crashesNear = new HashMap<Double, Set<Double>>();

        for (Double latitude : crashesMap.keySet()) {
            if (Math.abs(car.latitude - latitude) < maxProximity) {
                for (Double longotude : crashesMap.get(latitude)) {
                    if (Math.abs(car.longitude - longotude) < maxProximity) {
                        Set<Double> value;
                        if (crashesNear.containsKey(latitude)) {
                            value = crashesNear.get(latitude);
                        } else {
                            value = new HashSet<Double>();
                        }
                        value.add(longotude);
                        crashesNear.put(latitude, value);
                    }
                }
            }
        }

        return crashesNear;
    }
}
