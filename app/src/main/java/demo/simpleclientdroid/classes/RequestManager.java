package demo.simpleclientdroid.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.net.URLEncoder;
import java.util.ArrayList;

import demo.simpleclientdroid.SimpleAndroidClient;

/**
 * Created by damien.bouclier on 02/09/2014.
 */
public class RequestManager {

    private SharedPreferences _settings;
    public RequestManager(Context applicationContext) {

        _settings = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    public enum RequestMethod{
        GET,
        POST,
        PUT,
        DELETE;
    }

    public void AddHeader(String name, String value)
    {
        uriRequest.addHeader(name, value);
    }

    public void AddAuthHeader(String username, String password, Boolean isProxy)
    {
        uriRequest.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(username, password), HTTP.UTF_8, isProxy));
    }

    private HttpUriRequest uriRequest;

    public HttpUriRequest getRequest() {
        return uriRequest;
    }

    public void prepareRequest(String dataName, RequestMethod method, ArrayList<NameValuePair> params) {

        setUrl(dataName, params);
        setRequest(method);
        AddAuthHeader( _settings.getString("login",""),   _settings.getString("password",""),  false);
    }

    private String _url;
    private void setUrl(String dataName, ArrayList<NameValuePair> params) {

        _url = getDataSource(dataName);

        if (params.size() > 0)
        {
            _url = _url+"?";

            for(NameValuePair p : params)
            {
                _url = _url+p.getName()+"="+p.getValue()+"&";
            }
        }
    }

    private String getDataSource(String dataName)
    {
        String serverUrl = _settings.getString("serverURL", "127.0.0.1");

        return "http://"+serverUrl+"/simplelaravelserver/public/api/"+dataName.toLowerCase();
    }

    private void setRequest(RequestMethod method) {
        HttpUriRequest request = null;

        try {
            switch(method) {
                case GET:
                {
                    request = new HttpGet(_url);
                    break;
                }
                case POST:
                {
                    request = new HttpPost(_url);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        uriRequest = request;
    }
}
