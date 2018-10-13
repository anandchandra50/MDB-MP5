package com.ac.mdbmp5;

public class WeatherDay {
    String description;
    String icon;
    int tempHigh;
    int tempLow;
    int raining;

    public WeatherDay(String description, String icon, int tempHigh, int tempLow, int raining) {
        this.description = description;
        this.icon = icon;
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
        this.raining = raining;

    }
}
