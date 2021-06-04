package com.example.project_ql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        SharedPreferences setting= getSharedPreferences("PREFS",0);
        pass=setting.getString("password","");

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pass.equals("")) {
                    Intent intent=new Intent(getApplicationContext(),CreatPassActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(getApplicationContext(),EnterPassActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },2000);

    }
}