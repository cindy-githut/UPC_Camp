package com.upc.upc_camp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button send;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar myActionBar = getSupportActionBar();

        //For hiding android actionbar
        myActionBar.hide();

//        if (SharedPreferencesData.getSharedPreference(this).getString("name", null) != null){
//
//            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//            intent.putExtra("name", SharedPreferencesData.getSharedPreference(this).getString("name", null));
//            startActivity(intent);
//            finish();

//            if (SharedPreferencesData.getSharedPreference(this).getString("going", null) != null){
//
//                if(SharedPreferencesData.getSharedPreference(this).getString("going", null).equals("going")){
//
//                    Log.d("TETE", "Vgoing");
//                    Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
//                    intent.putExtra("getLocation", SharedPreferencesData.getSharedPreference(this).getString("name", null));
//                    startActivity(intent);
//                    finish();
//
//                }else{
//                    Log.d("TETE", "VgoinVVg");
//
//                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                    intent.putExtra("name", SharedPreferencesData.getSharedPreference(this).getString("name", null));
//                    startActivity(intent);
//                    finish();
//                }
//
//            }else{
//
//                Log.d("TETE", "Vgoing");
//
//
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.putExtra("name", SharedPreferencesData.getSharedPreference(this).getString("name", null));
//                startActivity(intent);
//                finish();
//
//            }


       // }

        name = (EditText) findViewById(R.id.edname);
        send = (Button) findViewById(R.id.btnsignin);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(name.getText().toString())){

                    name.setError("Name can not be empty");
                    name.findFocus();

                }else{

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("name", name.getText().toString().trim());

                    SharedPreferences.Editor editor = SharedPreferencesData.storeSharedPreference(MainActivity.this);

                    editor.putString("name", name.getText().toString().trim());

                    editor.apply();

                    startActivity(intent);
                }
            }
        });

    }
}
