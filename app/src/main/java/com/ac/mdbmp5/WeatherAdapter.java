package com.ac.mdbmp5;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.CustomViewHolder> {
    static ArrayList<WeatherDay> weatherSchedule = new ArrayList<>();

    public WeatherAdapter(Context context) {

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, WeatherParser.url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray daily = response.getJSONObject("daily").getJSONArray("data");
                            weatherSchedule.clear();
                            for (int i = 0; i < 7; i++) {
                                JSONObject day = new JSONObject(daily.get(i).toString());
                                // int temperature, String description, String icon, int tempHigh, int tempLow, int raining

                                WeatherDay newDay = new WeatherDay(-1, "",
                                        day.getString("icon"), day.getInt("temperatureMax"),
                                        day.getInt("temperatureMin"), -1);
                                weatherSchedule.add(newDay);
                            }
                            notifyDataSetChanged();
                            System.out.println("UPDATED");
                            System.out.println(weatherSchedule);

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
//        this.weatherSchedule = weatherSchedule;
    }

    @Override
    public int getItemCount() {
        System.out.println("SIZE IS " + Integer.toString(weatherSchedule.size()));
        return weatherSchedule.size();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.move_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
//        SET ITEMS OF THE CARD
//        holder.listNameTextView.setText(weatherSchedule.get(i).);
        String range = weatherSchedule.get(position).tempLow + "º—" + weatherSchedule.get(position).tempHigh + "º";
        holder.listRangeTextView.setText(range);
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, position);
        dt = c.getTime();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
        String day = simpleDateformat.format(dt);
        holder.listNameTextView.setText(day);

//        holder.moveNameTextView.setText(moveNames.get(position));
//        holder.moveDetailsTextView.setText(moveDeets.get(position).replace("null", "-"));
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView listNameTextView;
        TextView listRangeTextView;
        ImageView listIconImageView;

        public CustomViewHolder (View view) {
            super(view);
//            SET ALL ATTRIBUTES OF THE WEATHERDAY
            this.listNameTextView = (TextView) view.findViewById(R.id.listDayTextView);
            this.listRangeTextView = (TextView) view.findViewById(R.id.listRangeTextView);
            this.listIconImageView = (ImageView) view.findViewById(R.id.listIconImageView);

        }
    }
}
