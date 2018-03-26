package com.example.muzlive.webviewtest;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VideoActivity extends AppCompatActivity {

    WebView webView;
    CustomWebChromeClient customWebChromeClient;
    CustomWebViewClient customWebViewClient;

    OrientationEventListener orientationListener;

    boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        screenOrientationLock();

        setWidgets();

        setWebView();

        setOrientationListener(getBaseContext());

        webView.loadUrl("Add Your html5 URL!!!!");
    }

    private void screenOrientationLock() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    private void setWidgets() {
        webView = (WebView)findViewById(R.id.webView);
        customWebChromeClient = new CustomWebChromeClient();
        customWebViewClient = new CustomWebViewClient();
    }

    private void setWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        webView.setWebChromeClient(customWebChromeClient);
        webView.setWebViewClient(customWebViewClient);
    }

    private void setOrientationListener(Context context) {
        orientationListener = new OrientationEventListener(context) {
            @Override
            public void onOrientationChanged(int i) {
                if(isFullScreen) {
                    if (i > 260 && i < 280) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else if ((i > 350 && i < 360) || (i > 0 && i < 10)) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }
            }
        };
    }

    private class CustomWebChromeClient extends WebChromeClient {

        private View myView = null;
        private CustomViewCallback customViewCallback = null;
        final private ViewGroup parent = (ViewGroup)webView.getParent();

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if(customViewCallback != null) {
                customViewCallback.onCustomViewHidden();
                customViewCallback = null;
                return;
            }

            parent.removeView(webView);
            parent.addView(view);
            myView = view;
            customViewCallback = callback;

            isFullScreen = true;
            orientationListener.enable();
        }

        @Override
        public void onHideCustomView() {
            if(myView!=null) {
                if(customViewCallback != null) {
                    customViewCallback.onCustomViewHidden();
                    customViewCallback = null;
                }

                parent.removeView(myView);
                parent.addView(webView);
                myView = null;
            }

            isFullScreen = false;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            orientationListener.disable();
        }
    }

    private class CustomWebViewClient extends WebViewClient {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
