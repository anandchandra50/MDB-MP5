package com.ac.mdbmp5;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WeatherParser {
    static String apiKey = "723c641c1108b54458bf6b4abd70cb4c";
    static String url = "https://api.darksky.net/forecast/";
    static WeatherDay wd;

    public static void tenDayData(Context context) {

    }

    public static WeatherDay parseDay(JSONObject response, int index) {
        JSONObject currently;
        JSONObject today;
        JSONArray hourly;
        String description = null;
        String icon = null;
        int tempHigh = 0;
        int tempLow = 0;
        int raining = -1;
        try {
            currently = response.getJSONObject("currently");
            today = response.getJSONObject("daily").getJSONArray("data").getJSONObject(index);
            hourly = response.getJSONObject("hourly").getJSONArray("data");
            description = currently.getString("summary");
            icon =  currently.getString("icon");
            tempHigh =  today.getInt("temperatureMax");
            tempLow = today.getInt("temperatureMin");
            if(today.getDouble("precipProbability") > 0) {
                for (int i = 0; i < hourly.length(); i++) {
                    if (hourly.getJSONObject(i).getDouble("precipProbability") > 0) {
                        raining = i;
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new WeatherDay(description, icon, tempHigh, tempLow, raining);
    }
}
