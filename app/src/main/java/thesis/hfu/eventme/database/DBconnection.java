package thesis.hfu.eventme.database;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DBconnection {

    private static DBconnection instance;
    public AsyncHttpClient client;
    private static final String URL = "http://eventmy.besaba.com/";
    private static final String ACCEPT = "Accept";
    private static final String MIME_TYPE = "application/json";
    private int DEFAULT_TIMEOUT=20*1000;

    public DBconnection(){
        client = new AsyncHttpClient();
        this.client.addHeader(ACCEPT, MIME_TYPE);
        this.client.setTimeout(DEFAULT_TIMEOUT);
    }

    public static DBconnection getInstance () {
        if (DBconnection.instance == null) {
            DBconnection.instance = new DBconnection ();
        }
        return DBconnection.instance;
    }
    public static void post(String url,RequestParams params, JsonHttpResponseHandler handler){
        getInstance().client.post(DBconnection.URL +url, params,handler );
    }
}




//------------------------localhost-----------------------------
//private static final String URL ="http://10.0.3.2/eventmyDB/";
//--------------------------------------------------------------
