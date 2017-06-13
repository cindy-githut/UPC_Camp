package com.upc.upc_camp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {


    TextView welcome;
    RecyclerView photos;
    LinearLayout multiphotoslayout;
    Button notgoing, going, interested;
    String currentAddress;
    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        setToolbar();
        welcome = (TextView) findViewById(R.id.welcome);
        photos = (RecyclerView) findViewById(android.R.id.list);
        multiphotoslayout = (LinearLayout) findViewById(R.id.multiphotoslayout);

        final String name = getIntent().getStringExtra("name");
        String msg = "";

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        Log.d("timeOfDay", timeOfDay+"");

        if(timeOfDay >= 0 && timeOfDay < 12){
            msg = "Good Morning ";
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            msg = "Good Afternoon ";
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            msg = "Good Evening ";
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            msg = "Good Night ";
        }


        welcome.setText(msg + name);

        images(multiphotoslayout);

        try{
            getCurrentLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notgoing = (Button) findViewById(R.id.notgoing);
        going = (Button) findViewById(R.id.going);
        interested = (Button) findViewById(R.id.interested);

        final SharedPreferences.Editor editor = SharedPreferencesData.storeSharedPreference(SecondActivity.this);

        notgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("going", "not");
                editor.apply();

                startActivityForResult(new Intent(SecondActivity.this, NotGoingActivity.class), 200);

            }
        });

        going.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("currentAddress", currentAddress);

                final CustomDialog codeDialog = new CustomDialog(SecondActivity.this,
                        "Do you have transport to get to the events' venue?", "Yes","No");

                codeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // Dismiss the view
                        if (codeDialog.getButtonClicked() == codeDialog.RIGHT_BUTTON) {

                            codeDialog.dismiss();
                            intent.putExtra("transport", currentAddress);
                            editor.putString("going", "going");
                            editor.apply();
                            startActivity(intent);

                        }else  if (codeDialog.getButtonClicked() == codeDialog.LEFT_BUTTON) {

                            codeDialog.dismiss();
                            editor.putString("going", "going");
                            editor.apply();
                            startActivity(intent);

                        }
                    }

                });

                codeDialog.show();
                codeDialog.setCanceledOnTouchOutside(false);


            }
        });

    }
    public void setToolbar(){


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle("Welcome");
        //toolbar.inflateMenu(R.menu.menu_profile);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

//        try{
//
//            if(currentAddress.equals("")){
//
//                getCurrentLocation();
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void getCurrentLocation() throws IOException {

        // create class object
        GPSTracker gps = new GPSTracker(SecondActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.latitude;
            longitude = gps.longitude;

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

        currentAddress = city + ", " + country;

    }

    private void images(LinearLayout multiphotoslayout){

        final LinearLayout photosLayout =  multiphotoslayout;

        photosLayout.removeAllViews();
       // multiphotoslayout.removeView(view);

        for (int i = 0; i<= 10; i++) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.multiplephotositem, null);

            final ImageView photoImage = (ImageView) view.findViewById(R.id.imageView55);

            if(i==0){

                Picasso.with(getApplicationContext())
                        .load(R.drawable.host).fit().centerCrop()
                        .into(photoImage);

                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "1");
                        startActivity(intent);

                    }
                });

            }

            if(i==1){
                Picasso.with(getApplicationContext())
                        .load(R.drawable.image).fit().centerCrop()
                        .into(photoImage);
                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "2");
                        startActivity(intent);

                    }
                });
            }
            if(i==2){
                Picasso.with(getApplicationContext())
                        .load(R.drawable.n).fit().centerCrop()
                        .into(photoImage);
                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "3");
                        startActivity(intent);

                    }
                });
            }
            if(i==3){
                Picasso.with(getApplicationContext())
                        .load(R.drawable.image3).fit().centerCrop()
                        .into(photoImage);
                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "4");
                        startActivity(intent);
                    }
                });
            }
            if(i==4){
                Picasso.with(getApplicationContext())
                        .load(R.drawable.image2).fit().centerCrop()
                        .into(photoImage);
                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "5");
                        startActivity(intent);

                    }
                });
            }
            if(i==5){
                Picasso.with(getApplicationContext())
                        .load(R.drawable.image2).fit().centerCrop()
                        .into(photoImage);
                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "6");
                        startActivity(intent);

                    }
                });
            }
            if(i==6){
                Picasso.with(getApplicationContext())
                        .load(R.drawable.image2).fit().centerCrop()
                        .into(photoImage);
                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "7");
                        startActivity(intent);

                    }
                });
            }
            if(i==7){
                Picasso.with(getApplicationContext())
                        .load(R.drawable.image2).fit().centerCrop()
                        .into(photoImage);
                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "8");
                        startActivity(intent);

                    }
                });
            }
            if(i==8){
                Picasso.with(getApplicationContext())
                        .load(R.drawable.image2).fit().centerCrop()
                        .into(photoImage);
                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "9");
                        startActivity(intent);

                    }
                });
            }
            if(i==9){
                Picasso.with(getApplicationContext())
                        .load(R.drawable.image2).fit().centerCrop()
                        .into(photoImage);
                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "10");
                        startActivity(intent);

                    }
                });
            }
            if(i==10){
                Picasso.with(getApplicationContext())
                        .load(R.drawable.image3).fit().centerCrop()
                        .into(photoImage);
                photoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, ViewPhoto.class);
                        intent.putExtra("image", "11");
                        startActivity(intent);

                    }
                });
            }

            try{
                photosLayout.addView(view);

            }catch (Exception exc){

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        if(requestCode == 200 && resultCode == RESULT_OK){

            // Make sure the request was successful
            Toast.makeText(SecondActivity.this, "Thank you for your feedback.", Toast.LENGTH_SHORT).show();

        }
    }

}
