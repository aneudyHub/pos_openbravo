package com.example.aneudy.myapplication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.aneudy.myapplication.R;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    ProgressBar progressBar;
    Thread myTheard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar =(ProgressBar)findViewById(R.id.progressBar);

        myTheard = new Thread(){
            @Override
            public void run() {
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    sleep(3000);
                    startActivity(new Intent(Splash.this,Clients.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myTheard.start();


    }
}
