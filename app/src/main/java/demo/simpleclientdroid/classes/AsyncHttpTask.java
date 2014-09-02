package demo.simpleclientdroid.classes;

/**
 * Created by damien.bouclier on 02/09/2014.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpTask extends AsyncTask<String, Void, String>{

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

            HttpResponse httpResponse = httpclient.execute(httpHandler.getHttpRequestMethod());

            int responseCode = httpResponse.getStatusLine().getStatusCode();

            if(responseCode == 200)
            {
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null) result = convertInputStreamToString(inputStream);
            } else {
                throw new Exception("HTTP ERROR " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
