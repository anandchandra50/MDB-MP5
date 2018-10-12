package com.ac.mdbmp5;

import android.content.Context;
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

import java.util.HashMap;


public class WeatherParser extends AsyncTask<Integer, Integer, Void>{
    String apiKey = "723c641c1108b54458bf6b4abd70cb4c";
    static String url = "https://api.darksky.net/forecast/723c641c1108b54458bf6b4abd70cb4c/39.7684,-86.1581?exclude=alerts,flags";

    @Override
    protected Void doInBackground(Integer... integers) {

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public static void todayData(Context context) {

    }

    public static void tenDayData(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject currently = response.getJSONObject("currently");
                            JSONObject today = response.getJSONObject("daily").getJSONArray("data").getJSONObject(0);
                            JSONArray hourly = response.getJSONObject("hourly").getJSONArray("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("BOBOBOB", error.toString());

                    }
                });
        queue.add(jsonObjectRequest);
    }

    public static void getTodayData(Context context) {

    }

    private WeatherDay parseDay(JSONObject currently, JSONObject today, JSONArray hourly) {
        String description = null;
        String icon = null;
        int tempHigh = 0;
        int tempLow = 0;
        int raining = -1;
        try {
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
