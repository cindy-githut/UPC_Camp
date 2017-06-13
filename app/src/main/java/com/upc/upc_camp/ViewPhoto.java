package com.upc.upc_camp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewPhoto extends AppCompatActivity {

    ImageView streamimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        ActionBar myActionBar = getSupportActionBar();

        //For hiding android actionbar
        myActionBar.hide();

        setToolbar();

        streamimage = (ImageView) findViewById(R.id.streamimage);

        if(getIntent().getStringExtra("image").equals("1")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.host)
                    .into(streamimage);
        }

        if(getIntent().getStringExtra("image").equals("2")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.image)
                    .into(streamimage);
        }
        if(getIntent().getStringExtra("image").equals("3")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.n)
                    .into(streamimage);
        }
        if(getIntent().getStringExtra("image").equals("4")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.image3)
                    .into(streamimage);
        }
        if(getIntent().getStringExtra("image").equals("5")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.image2)
                    .into(streamimage);
        }
        if(getIntent().getStringExtra("image").equals("6")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.image2)
                    .into(streamimage);
        }
        if(getIntent().getStringExtra("image").equals("7")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.image2)
                    .into(streamimage);
        }
        if(getIntent().getStringExtra("image").equals("8")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.image2)
                    .into(streamimage);
        }
        if(getIntent().getStringExtra("image").equals("9")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.image2)
                    .into(streamimage);
        }
        if(getIntent().getStringExtra("image").equals("10")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.image2)
                    .into(streamimage);
        }
        if(getIntent().getStringExtra("image").equals("11")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.image3)
                    .into(streamimage);
        }

    }

    public void setToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle("");
        //toolbar.inflateMenu(R.menu.menu_profile);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

}
