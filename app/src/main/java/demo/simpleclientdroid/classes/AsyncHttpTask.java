package demo.simpleclientdroid.classes;

/**
 * Created by damien.bouclier on 02/09/2014.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpTask extends AsyncTask<String, Void, String>{

    final private Integer CONNECTION_TIMEOUT = 30000;

    private HttpHandler httpHandler;
    public AsyncHttpTask(HttpHandler httpHandler){
        this.httpHandler = httpHandler;
    }

    @Override
    protected String doInBackground(String... arg0) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
            HttpResponse httpResponse = httpclient.execute(httpHandler.getHttpRequestMethod());

            int responseCode = httpResponse.getStatusLine().getStatusCode();

            if (responseCode == 200) {
                inputStream = httpResponse.getEntity().getContent();
                if (inputStream != null) result = convertInputStreamToString(inputStream);
            } else {
                throw new Exception("HTTP ERROR " + responseCode);
            }

            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }
        @Override
    protected void onPostExecute(String result) {
        httpHandler.onResponse(result);
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
