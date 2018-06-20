package com.suntechnologies.cabbie;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by hareeshs on 15-06-2018.
 */

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 5000;
    private static int TITLE_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);

        ImageView sunLogo = (ImageView) findViewById(R.id.imageView);
        final ImageView appTitle = (ImageView) findViewById(R.id.appTitle);

        ObjectAnimator sunLogoAnimation = ObjectAnimator.ofFloat(sunLogo, "translationY",0,-500);
        sunLogoAnimation.setDuration(2000);
        sunLogoAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        sunLogoAnimation.start();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                appTitle.setVisibility(View.VISIBLE);
                ObjectAnimator appTitleAnimation = ObjectAnimator.ofFloat(appTitle, "translationX",-500,50);
                appTitleAnimation.setDuration(2000);
                appTitleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                appTitleAnimation.start();

            }
        }, TITLE_TIME_OUT);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
