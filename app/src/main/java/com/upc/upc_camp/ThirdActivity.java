package com.upc.upc_camp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ThirdActivity extends AppCompatActivity {

    private TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond, directions, transport;
    private TextView tvEvent;
    private Handler handler;
    private Runnable runnable;
    OkHttpClient client;

    String address;

    public void setToolbar(){


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle("Count Down");
        //toolbar.inflateMenu(R.menu.menu_profile);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        txtTimerDay = (TextView) findViewById(R.id.txtTimerDay);
        txtTimerHour = (TextView) findViewById(R.id.txtTimerHour);
        txtTimerMinute = (TextView) findViewById(R.id.txtTimerMinute);
        txtTimerSecond = (TextView) findViewById(R.id.txtTimerSecond);
        tvEvent = (TextView) findViewById(R.id.tvhappyevent);
        directions = (TextView) findViewById(R.id.directions);
        transport = (TextView) findViewById(R.id.transport);
        client = new OkHttpClient();

        ActionBar myActionBar = getSupportActionBar();

        //For hiding android actionbar
        myActionBar.hide();

        setToolbar();

        if(getIntent().hasExtra("getLocation")){
            try {
                getCurrentLocation();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(getIntent().hasExtra("transport")){
            transport.setText("Please contact Brian Mashego at 0xx xxx xxxx to organise transport");
        }

        countDownStart();

        if(getIntent().hasExtra("currentAddress")){
            address = getIntent().getStringExtra("currentAddress");
            getLocation("http://maps.googleapis.com/maps/api/directions/json?origin="+address+"&destination=Janefurse Limpopo&sensor=false");

        }
    }
    private void getCurrentLocation() throws IOException {

        // create class object
        GPSTracker gps = new GPSTracker(ThirdActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            getAddress(gps.latitude, gps.longitude);

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }
    private void getAddress(double latitude, double longitude) throws IOException {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1);

        String city = "";
        String country = "";

        if(addresses.get(0).getLocality() != null){
            city = addresses.get(0).getLocality();
        }

        if(addresses.get(0).getCountryName() != null){
            country = addresses.get(0).getCountryName();
        }

        getLocation("http://maps.googleapis.com/maps/api/directions/json?origin="+city + ", " + country+"&destination=Janefurse Limpopo&sensor=false");


    }

    private void getLocation(String url){


        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String responseString = response.body().string();

                try{

                    ThirdActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (response.isSuccessful()) {

                                try {

                                    Log.d("LLL",responseString );
                                    JSONObject object = new JSONObject(responseString);

                                    JSONArray routes = object.getJSONArray("routes");

                                    JSONObject routesObject = routes.getJSONObject(0);

                                    JSONArray legs = routesObject.getJSONArray("legs");

                                    JSONObject jsonObject = legs.getJSONObject(0);

                                    String distanceString="";
                                    String durationString="";

                                    if(jsonObject.has("distance") && jsonObject.getJSONObject("distance") != null){

                                         distanceString = jsonObject.getJSONObject("distance").getString("text");

                                    }

                                    if(jsonObject.has("duration") && jsonObject.getJSONObject("duration") != null){

                                         durationString = jsonObject.getJSONObject("duration").getString("text");

                                    }

                                    if(!Objects.equals(distanceString, "")){

                                        directions.setText("You are " + distanceString + " away from the events venue." );

                                        if(!Objects.equals(durationString, "")){
                                            directions.append("");
                                                    directions.append("\nIt will take you approximately " + durationString + " from " + address + " to Jane Furse Limpopo, see you there :)"  );
                                        }

                                    }


                                } catch (JSONException es) {
                                    es.printStackTrace();
                                }

                            }
                        }
                    });

                }catch(Exception exc){
                    Log.d("Error:", exc.getLocalizedMessage());
                }

            }
        });

    }

    public void countDownStart() {

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");

                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse("2017-07-12");
                    Date currentDate = new Date();

                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        txtTimerDay.setText("" + String.format("%02d", days));
                        txtTimerHour.setText("" + String.format("%02d", hours));
                        txtTimerMinute.setText(""
                                + String.format("%02d", minutes));
                        txtTimerSecond.setText(""
                                + String.format("%02d", seconds));
                    } else {

                        tvEvent.setVisibility(View.VISIBLE);
                        tvEvent.setText("The event has started, enjoy! \n With Lots of Love from UPC");
                        textViewGone();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }

    public void textViewGone() {

        findViewById(R.id.LinearLayout10).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout11).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout12).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout13).setVisibility(View.GONE);
        findViewById(R.id.textView1).setVisibility(View.GONE);
        findViewById(R.id.textView2).setVisibility(View.GONE);

    }
}