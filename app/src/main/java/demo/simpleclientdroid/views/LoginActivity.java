package demo.simpleclientdroid.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import demo.simpleclientdroid.R;
import demo.simpleclientdroid.classes.AuthManager;
import demo.simpleclientdroid.classes.HttpHandler;
import demo.simpleclientdroid.classes.RequestManager;
import demo.simpleclientdroid.classes.UsersManager;
import demo.simpleclientdroid.data.User;


public class LoginActivity extends Activity {

    final String PARAM_LOGIN = "user_login";
    final String PARAM_PASSWORD = "user_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText login = (EditText) findViewById(R.id.login_username);
        final EditText password = (EditText) findViewById(R.id.login_password);
        Button loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   final String sLogin = login.getText().toString();
                   String sPassword = password.getText().toString();

                   if (sLogin.isEmpty() || sPassword.isEmpty())

                   {
                       Toast.makeText(LoginActivity.this,
                               R.string.username_password_empty,
                               Toast.LENGTH_SHORT).show();
                       return;
                   }

                   AuthManager.getInstance().setAuthInfo(sLogin, sPassword);

                   try {

                       new HttpHandler() {
                           @Override
                           public HttpUriRequest getHttpRequestMethod() {

                               RequestManager RM = new RequestManager();
                               ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                               params.add(new BasicNameValuePair("username", sLogin));

                               RM.prepareRequest(User.class.getSimpleName(), RequestManager.RequestMethod.GET, params);
                               return RM.getRequest();
                           }
                           @Override
                           public void onResponse(String result) {

                               try {
                                   JSONObject object = new JSONObject(result);
                                   JSONArray arr = object.getJSONArray("users");
                                   User user = UsersManager.convertUser(arr.getJSONObject(0));

                                   if (user.Username == sLogin )
                                   {

                                   }

                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                           }

                       }.execute();

                   } catch (Exception e) {
                       e.printStackTrace();
                   }

                   Intent intent = new Intent(LoginActivity.this, UrlActivity.class);
                   startActivity(intent);
               }
            }
        );
    }
}
