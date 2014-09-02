package demo.simpleclientdroid.classes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import demo.simpleclientdroid.R;

/**
 * BaseActivity
 * Classe mere pour mes activity
 * Created by damien.bouclier on 02/09/2014.
 */
public abstract class BaseActivity extends Activity {


    private SharedPreferences _settings;

    protected void setDefaultSettings()
    {
        _settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    protected SharedPreferences getSettings()
    {
        return _settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultSettings();
        intialize();
    }

    protected abstract void intialize();

}
