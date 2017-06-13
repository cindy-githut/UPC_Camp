package com.upc.upc_camp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class NotGoingActivity extends AppCompatActivity {

    Button btnCancelHelp, btnSave;

    public void setToolbar(){


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle("Why :(");
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
        setContentView(R.layout.activity_not_going);

        ActionBar myActionBar = getSupportActionBar();

        //For hiding android actionbar
        myActionBar.hide();

        setToolbar();

        btnCancelHelp = (Button) findViewById(R.id.btnCancelHelp);
        btnSave = (Button) findViewById(R.id.btnReportHelp);

        btnCancelHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent thisintent = new Intent();
                setResult(RESULT_OK, thisintent);
                finish();

            }
        });

    }
}
