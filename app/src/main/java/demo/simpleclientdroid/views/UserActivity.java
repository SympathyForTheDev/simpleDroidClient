package demo.simpleclientdroid.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import demo.simpleclientdroid.R;
import demo.simpleclientdroid.classes.BaseActivity;
import demo.simpleclientdroid.classes.HttpHandler;
import demo.simpleclientdroid.classes.RequestManager;
import demo.simpleclientdroid.data.User;

/**
 * UserActivity
 * Affiche une liste d'utilisateur
 * Created by damien.bouclier on 01/09/2014.
 */
public class UserActivity extends BaseActivity implements OnRefreshListener {

    private ListView listview;
    private List<String> listUsername;
    private ArrayAdapter<String> listUserNameAdapter;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void intialize()
    {
        setContentView(R.layout.activity_url);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // recuperation des users
        getUserList();
    }

    private void getUserList()
    {
        listview  = (ListView) findViewById(R.id.url_listview);
        listUsername = new ArrayList<String>();
        listUserNameAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listUsername );

        listview.setAdapter(listUserNameAdapter);

        try {

            new HttpHandler() {
                @Override
                public HttpUriRequest getHttpRequestMethod() {

                    swipeLayout.setRefreshing(true);
                    RequestManager RM = new RequestManager(getApplicationContext());
                    RM.prepareRequest("user", RequestManager.RequestMethod.GET, new ArrayList<NameValuePair>());
                    return RM.getRequest();
                }
                @Override
                public void onResponse(String result) {

                    try {
                        JSONObject object = new JSONObject(result);
                        JSONArray arr = object.getJSONArray("users");

                        for (int i = 0; i < arr.length(); i++) {
                            User user = convertUser(arr.getJSONObject(i));
                            listUsername.add(user.Username);
                            listUserNameAdapter.notifyDataSetChanged();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    swipeLayout.setRefreshing(false);
                }

            }.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User convertUser(JSONObject obj) throws JSONException {
        String username = obj.getString("username");
        return new User(username);
    }

    @Override
    public void onRefresh() {
        getUserList();
    }
/*
    TODO : ADD USER
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_user_button:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addUser()
    {
        try {

            new HttpHandler() {
                @Override
                public HttpUriRequest getHttpRequestMethod() {

                    RequestManager RM = new RequestManager(getApplicationContext());
                    RM.prepareRequest("user", RequestManager.RequestMethod.POST, new ArrayList<NameValuePair>());
                    return RM.getRequest();
                }
                @Override
                public void onResponse(String result) {

                    try {

                        if(result.equals()){
                            listUserNameAdapter.notifyDataSetChanged();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}