package com.ac.mdbmp5;

public class WeatherDay {
    int temperature;
    String description;
    String icon;
    int tempHigh;
    int tempLow;
    int raining;

    public WeatherDay(int temperature, String description, String icon, int tempHigh, int tempLow, int raining) {
        this.temperature = temperature;
        this.description = description;
        this.icon = icon;
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
        this.raining = raining;
    }
}
