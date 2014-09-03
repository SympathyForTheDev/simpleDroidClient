package demo.simpleclientdroid.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.ArrayList;

import demo.simpleclientdroid.R;
import demo.simpleclientdroid.classes.BaseActivity;
import demo.simpleclientdroid.classes.HttpHandler;
import demo.simpleclientdroid.classes.RequestManager;

/**
 * LoginActivity
 * Created by damien.bouclier on 01/09/2014.
 */
public class LoginActivity extends BaseActivity {

    final String PARAM_LOGIN = "user_login";
    final String PARAM_PASSWORD = "user_password";
    private EditText loginEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void intialize() {

        setContentView(R.layout.activity_login);

        loginEditText = (EditText) findViewById(R.id.login_username);
        passwordEditText = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        loginButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   String sLogin = loginEditText.getText().toString();
                   String sPassword = passwordEditText.getText().toString();

                   if (sLogin.isEmpty() || sPassword.isEmpty())
                   {
                       Toast.makeText(LoginActivity.this,
                               R.string.username_password_empty,
                               Toast.LENGTH_SHORT).show();
                       return;
                   }

                   SharedPreferences.Editor edit = getSettings().edit();
                   edit.putString("login", sLogin);
                   edit.putString("password", sPassword);
                   edit.apply();

                   try {

                       new HttpHandler() {
                           @Override
                           public HttpUriRequest getHttpRequestMethod() {
                               RequestManager RM = new RequestManager(getApplicationContext());
                               RM.prepareRequest("login", RequestManager.RequestMethod.GET, new ArrayList<NameValuePair>());
                               return RM.getRequest();
                           }
                           @Override
                           public void onResponse(String result) {
                               if(result.equals("AuthOK"))
                              {
                                  Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                  startActivity(intent);
                              } else if (result.isEmpty()) {
                                  Toast.makeText(LoginActivity.this,
                                          R.string.auth_failed,
                                          Toast.LENGTH_SHORT).show();
                              } else {
                                  Toast.makeText(LoginActivity.this,
                                          result,
                                          Toast.LENGTH_SHORT).show();
                              }
                           }

                       }.execute();

                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
            }
        );
    }
}
