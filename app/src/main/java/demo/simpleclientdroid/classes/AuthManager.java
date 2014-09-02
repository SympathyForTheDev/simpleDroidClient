package demo.simpleclientdroid.classes;

import android.content.Context;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import demo.simpleclientdroid.data.User;

/**
 * Created by damien.bouclier on 02/09/2014.
 */
public class AuthManager {

    private static AuthManager instance;

    private String _username;
    private String _password;
    private Boolean _isProxy;

    public static void initInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new AuthManager();
        }
    }

    public static AuthManager getInstance()
    {
        // Return the instance
        return instance;
    }

    private AuthManager()
    {

    }

    public void setAuthInfo(String username, String password)
    {
       _username = username;
       _password = password;
    }

    public String getUsername()
    {
        return _username;
    }
    public String getPassword()
    {
        return _password;
    }

    public Boolean IsProxy() {
        return _isProxy;
    }
}

