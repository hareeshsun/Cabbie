package com.suntechnologies.cabbie;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;

/**
 * Created by hareeshs on 20-06-2018.
 */

public class SunCabbie extends Application implements Application.ActivityLifecycleCallbacks {

    private Context applicationContext;
    private Context currentActivityContext;
    private Activity currentActivity;
    private FirebaseApp firebaseApp;

    private static SunCabbie currentSession;

    public SunCabbie() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        currentSession = this;
        applicationContext = this;
        firebaseApp = FirebaseApp.initializeApp(this);
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        currentSession = null;
        applicationContext = null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static SunCabbie getCurrentSession(){
        return currentSession;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
        currentActivityContext = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
        currentActivityContext = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if(currentActivity == activity){
            currentActivity = null;
            currentActivityContext = null;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public Context getApplicationContext()
    {
        return applicationContext;
    }

    public Activity getCurrentActivity()
    {
        return currentActivity;
    }

    public Context getCurrentActivityContext()
    {
        return currentActivityContext;
    }

}
