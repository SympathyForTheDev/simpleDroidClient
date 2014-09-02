package demo.simpleclientdroid.classes;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import demo.simpleclientdroid.data.User;

public class UsersManager
{
    public static ArrayList<User> getUsersList()
    {
        final ArrayList<User> listUser = new ArrayList<User>();

        try {

            new HttpHandler() {
                @Override
                public HttpUriRequest getHttpRequestMethod() {

                    RequestManager RM = new RequestManager();
                    RM.prepareRequest(User.class.getSimpleName(), RequestManager.RequestMethod.GET, new ArrayList<NameValuePair>());
                    return RM.getRequest();
                }
                @Override
                public void onResponse(String result) {

                    Log.e("RESULT",result);

                    try {
                        JSONObject object = new JSONObject(result);
                        JSONArray arr = object.getJSONArray("users");

                        for (int i = 0; i < arr.length(); i++) {
                            listUser.add(convertUser(arr.getJSONObject(i)));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("RESULT",listUser.toString());

        return listUser;
    }

    public static User convertUser(JSONObject obj) throws JSONException {
        String username = obj.getString("username");
        return new User(username);
    }
}

