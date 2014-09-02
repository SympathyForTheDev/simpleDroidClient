package demo.simpleclientdroid.data;

/**
 * Created by damien.bouclier on 02/09/2014.
 */
public abstract class Data {

    protected String _serverUrl;
    protected static String _dataName;

    public static String getDataSource()
    {
        return "http://localhost:8089/simplelaravelserver/public/api/"+_dataName;
    }

    public abstract void setDataName();
}
