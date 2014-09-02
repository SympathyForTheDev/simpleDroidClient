package demo.simpleclientdroid.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import demo.simpleclientdroid.R;
import demo.simpleclientdroid.classes.BaseActivity;


/**
 * SettingsActivity
 * View pour gerer les settings du serveur
 * Created by damien.bouclier on 01/09/2014.
 */
public class SettingsActivity extends BaseActivity {

    private EditText serverUrl;
    private Button settingsButton;

    @Override
    protected void intialize() {
        setContentView(R.layout.activity_settings);

        serverUrl= (EditText) findViewById(R.id.settings_serverurl);
        serverUrl.setText(getSettings().getString("serverURL","127.0.0.1"));
        settingsButton = (Button) findViewById(R.id.settings_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor edit = getSettings().edit();
                edit.putString("serverURL", serverUrl.getText().toString());
                edit.apply();

                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
