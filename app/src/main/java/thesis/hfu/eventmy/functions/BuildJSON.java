package thesis.hfu.eventmy.functions;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import thesis.hfu.eventmy.objects.Event;
import thesis.hfu.eventmy.objects.User;

public class BuildJSON {

    private static BuildJSON instance;
    private static final String NAME="name";
    private static final String PRENAME="prename";
    private static final String EMAIL="email";
    private static final String PASSWORD="password";
    private static final String SEARCH="search";
    private static final String EVENT_ID="event_id";
    private static final String USER_ID="user_id";
    private static final String USER_A_ID="usera_id";
    private static final String USER_B_ID="userb_id";
    private static final String OWNER_ID="owner_id";
    private static final String LOCATION="location";
    private static final String DATE="date";
    private static final String STATUS_FRIEND="status_friend";

    public static BuildJSON getInstance () {
        if (BuildJSON.instance == null) {
            BuildJSON.instance = new BuildJSON();
        }
        return BuildJSON.instance;
    }

    public RequestParams registrationJSON(String name, String prename,String email, String password) {

        RequestParams params= new RequestParams();
        params.put(NAME, name);
        params.put(PRENAME, prename);
        params.put(EMAIL, email);
        params.put(PASSWORD, password);
        return params;
    }
    public RequestParams loginJSON(String email, String password) {

        RequestParams params= new RequestParams();
        params.put(EMAIL, email);
        params.put(PASSWORD, password);
        return params;
    }

    public RequestParams searchUserJSON(String search,String user_id) {

        RequestParams params= new RequestParams();
        params.put(SEARCH, search);
        params.put(USER_ID,user_id);
        return params;
    }

    public RequestParams addFriendJSON(String user1_id, String user2_id) {

        RequestParams params= new RequestParams();
        params.put(USER_A_ID, Integer.parseInt(user1_id));
        params.put(USER_B_ID, Integer.parseInt(user2_id));
        return params;
    }

    public RequestParams createEventJSON(String name,String location,Date date,String owner_id) {

        RequestParams params= new RequestParams();
        params.put(NAME, name);
        params.put(LOCATION,location);
        params.put(DATE,date);
        params.put(OWNER_ID,Integer.parseInt(owner_id));
        return params;
    }
    public RequestParams updateAllEventsJSON(String user_id) {

        RequestParams params= new RequestParams();
        params.put(USER_ID, Integer.parseInt(user_id));
        return params;
    }

    public ArrayList<User> getAllUsersJSON(JSONArray jsonArray) throws JSONException {
        ArrayList<User> userList= new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){
            int user_id = jsonArray.getJSONObject(i).getInt(USER_ID);
            String name= jsonArray.getJSONObject(i).getString(NAME);
            String prename= jsonArray.getJSONObject(i).getString(PRENAME);
            String email= jsonArray.getJSONObject(i).getString(EMAIL);
            int status= jsonArray.getJSONObject(i).getInt(STATUS_FRIEND);
            User user= new User(user_id,status,name,prename,email);
            userList.add(user);
        }
        return userList;
    }


    public ArrayList<Event> getAllEventsJSON(JSONArray jsonArray) throws JSONException {
        ArrayList<Event> eventList= new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){
            int event_id = jsonArray.getJSONObject(i).getInt(EVENT_ID);
            String name= jsonArray.getJSONObject(i).getString(NAME);
            String location= jsonArray.getJSONObject(i).getString(LOCATION);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date=null;
            try {
                date = (Date) df.parse(jsonArray.getJSONObject(i).getString(DATE));
                String newDateString = df.format(date);
                System.out.println(newDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Event event= new Event(event_id,name,location,date);
            eventList.add(event);
        }
        return eventList;
    }
}

