package com.ac.mdbmp5;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.CustomViewHolder> {
    ArrayList<WeatherDay> weatherSchedule = new ArrayList<>();
    public WeatherAdapter(final String pokemon) {
//        new AsyncTask<Void, Void, Void>() {
//            protected Void doInBackground(Void... voids) {
//                try {
//                    //Do the HTTP Request, build the weather arraylist
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                    Log.e("bad", "url");
//                } catch (ProtocolException p) {
//                    p.printStackTrace();
//                    Log.e("bad", "protocol");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e("bad", "io");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.e("bad", e.getMessage());
//                }
//                return null;
//            }
//
//            @Override
//            protected void onProgressUpdate(Void... values) {
//                notifyDataSetChanged();
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                notifyDataSetChanged();
//            }
//        }.execute();
    }

    @Override
    public int getItemCount() {
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
//        holder.moveNameTextView.setText(moveNames.get(position));
//        holder.moveDetailsTextView.setText(moveDeets.get(position).replace("null", "-"));
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView moveNameTextView;
        TextView moveDetailsTextView;

        public CustomViewHolder (View view) {
            super(view);
//            SET ALL ATTRIBUTES OF THE WEATHERDAY
//            this.moveNameTextView = (TextView) view.findViewById(R.id.moveView);
//            this.moveDetailsTextView = (TextView) view.findViewById(R.id.moveDetailsView);
        }
    }
}
