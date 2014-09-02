package demo.simpleclientdroid;

import android.app.Application;
import android.content.Context;

import java.util.logging.Logger;

import demo.simpleclientdroid.classes.AuthManager;


public class SimpleAndroidClient extends Application {

    private static SimpleAndroidClient sInstance;

    public static SimpleAndroidClient getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sInstance.initializeInstance();
    }

    protected void initializeInstance() {
        AuthManager.initInstance();
    }
}