package com.example.muzlive.webviewtest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;

public class AnimActivity extends AppCompatActivity {



    ImageView videoImage, videoCdCase;

    ExitActivityTransition exitTrans;

    Bundle savedBundle;

    Animation anim;

    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(AnimActivity.this, VideoActivity.class));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        savedBundle = savedInstanceState;

        setWidgets();
    }

    private void setWidgets() {
        videoImage = (ImageView)findViewById(R.id.videoImage);
        videoCdCase = (ImageView)findViewById(R.id.videoCdCase);
        Glide.with(AnimActivity.this).load(R.mipmap.circle).into(videoCdCase);

        exitTrans = ActivityTransition.with(getIntent()).to(videoImage).start(savedBundle);
        Glide.with(AnimActivity.this).load(R.mipmap.snsd_burned).into(videoImage);

        anim = AnimationUtils.loadAnimation(AnimActivity.this, R.anim.slide_right_to_left);
        videoCdCase.startAnimation(anim);

        h.sendEmptyMessageDelayed(0, 1000);
    }



    @Override
    public void onBackPressed() {
        anim = AnimationUtils.loadAnimation(AnimActivity.this, R.anim.slide_left_to_right);
        videoCdCase.startAnimation(anim);
        videoCdCase.setVisibility(View.INVISIBLE);
        exitTrans.exit(AnimActivity.this);
    }
}
