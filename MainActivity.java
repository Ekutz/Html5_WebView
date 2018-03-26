package com.example.muzlive.webviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ActivityTransitionLauncher;

public class MainActivity extends AppCompatActivity {

    ImageView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thumbnail = (ImageView)findViewById(R.id.thumbnail);
        Glide.with(this).load(R.mipmap.snsd_burned).into(thumbnail);

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AnimActivity.class);
                ActivityTransitionLauncher.with(MainActivity.this).from(thumbnail).launch(i);
            }
        });
    }
}
