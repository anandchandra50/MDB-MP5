package com.ac.mdbmp5;

import android.content.Context;
import android.app.SearchManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListAdapter;
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
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private LocationManager locationManager;
    private static Location _location;
    private RequestQueue queue;
    private static WeatherDay today;
    private static String city;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    void getLocation() {
        _location = new Location(LocationManager.NETWORK_PROVIDER);
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, new LocationListener() {
                @Override
                public void onLocationChanged(Location loc) {
                    _location = loc;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static ArrayList<WeatherDay> weatherSchedule;
        private static WeatherAdapter adapter;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public void fillActivity(final View rootView, Context context) {

            String url = "https://api.darksky.net/forecast/723c641c1108b54458bf6b4abd70cb4c/39.7684,-86.1581?exclude=alerts,flags";
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject currently = response.getJSONObject("currently");
                                JSONObject today = response.getJSONObject("daily").getJSONArray("data").getJSONObject(0);
                                JSONArray hourly = response.getJSONObject("hourly").getJSONArray("data");
                                WeatherDay day = WeatherParser.parseDay(currently, today, hourly);

                                ((TextView) rootView.findViewById(R.id.highTextView)).setText("high: " + Integer.toString(day.tempHigh) + "º");
                                ((TextView) rootView.findViewById(R.id.lowTextView)).setText("low: " + Integer.toString(day.tempLow) + "º");
                                ((TextView) rootView.findViewById(R.id.weatherTextView)).setText(Integer.toString(day.temperature));
                                ((TextView) rootView.findViewById(R.id.descriptionTextView)).setText(day.description);
                                if (day.raining != -1 && day.raining != 0) {
                                    ((TextView) rootView.findViewById(R.id.rainTextView)).setText("rain: " + day.raining + "%");
                                } else {
                                    ((TextView) rootView.findViewById(R.id.rainTextView)).setText("rain: none");
                                }

                                // clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
                                switch (day.icon) {
                                    case "clear-night":
                                        ((ImageView) rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_night);
                                        break;
                                    case "rain":
                                        ((ImageView) rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_rain);
                                        break;
                                    case "wind":
                                        ((ImageView) rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_wind);
                                        break;
                                    case "fog":
                                        ((ImageView) rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_fog);
                                        break;
                                    case "cloudy":
                                        ((ImageView) rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_cloudy);
                                        break;
                                    case "partly-cloudy":
                                        ((ImageView) rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_partly_cloudy);
                                        break;
                                    case "partly-cloudy-night":
                                        ((ImageView) rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_partly_cloudy_night);
                                        break;
                                    default:
                                        ((ImageView) rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_clear_day);
                                }

                                TextView location = rootView.findViewById(R.id.placeTextView);

                                JSONObject nextHour = new JSONObject(hourly.get(0).toString());
                                long timestamp = nextHour.getLong("time") * 1000;
                                ((TextView) rootView.findViewById(R.id.firstLaterTemp)).setText(Integer.toString(nextHour.getInt("temperature")) + "º");
                                ((TextView) rootView.findViewById(R.id.firstLaterTime)).setText(WeatherParser.hourForStamp(timestamp));

                                nextHour = new JSONObject(hourly.get(1).toString());
                                timestamp = nextHour.getLong("time") * 1000;
                                ((TextView) rootView.findViewById(R.id.secondLaterTemp)).setText(Integer.toString(nextHour.getInt("temperature")) + "º");
                                ((TextView) rootView.findViewById(R.id.secondLaterTime)).setText(WeatherParser.hourForStamp(timestamp));

                                nextHour = new JSONObject(hourly.get(2).toString());
                                timestamp = nextHour.getLong("time") * 1000;
                                ((TextView) rootView.findViewById(R.id.thirdLaterTemp)).setText(Integer.toString(nextHour.getInt("temperature")) + "º");
                                ((TextView) rootView.findViewById(R.id.thirdLaterTime)).setText(WeatherParser.hourForStamp(timestamp));

                                nextHour = new JSONObject(hourly.get(3).toString());
                                timestamp = nextHour.getLong("time") * 1000;
                                ((TextView) rootView.findViewById(R.id.fourthLaterTemp)).setText(Integer.toString(nextHour.getInt("temperature")) + "º");
                                ((TextView) rootView.findViewById(R.id.fourthLaterTime)).setText(WeatherParser.hourForStamp(timestamp));


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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int position = getArguments().getInt(ARG_SECTION_NUMBER);
            final View rootView;
            switch (position) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    // USE ASYNC TASK HERE
                    // SET ALL ROOTVIEW PROPERTIES

                    fillActivity(rootView, getContext());

                    return rootView;
                case 2:
                    weatherSchedule = new ArrayList<>();
                    rootView = inflater.inflate(R.layout.fragment_moves, container, false);
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
                    adapter = new WeatherAdapter(getContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
//                    fillData(getContext());
                    return rootView;
            }
            return null;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "0";
                case 1:
                    return "1";
            }
            return null;
        }
    }
}
