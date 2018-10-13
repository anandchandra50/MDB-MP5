package com.ac.mdbmp5;

import android.os.AsyncTask;

import com.android.volley.toolbox.HttpResponse;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {

    static String url = "https://api.darksky.net/forecast/723c641c1108b54458bf6b4abd70cb4c/39.7684,-86.1581?exclude=alerts,flags";

    public static class FetchJSON extends AsyncTask<Void, Void, Void> {
        private String urlString = "https://api.darksky.net/forecast/723c641c1108b54458bf6b4abd70cb4c/39.7684,-86.1581?exclude=alerts,flags";
        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("HERE");
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    System.out.println("HERE READING");
                    readStream(in);
                } finally {
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        private void readStream(InputStream stream) {
            System.out.println(stream);
        }

    }

}
