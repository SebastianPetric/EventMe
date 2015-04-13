package thesis.hfu.eventmy.database;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import thesis.hfu.eventmy.activities.AllEventsActivity;
import thesis.hfu.eventmy.activities.AllTasksOfEventActivity;
import thesis.hfu.eventmy.activities.LoginActivity;
import thesis.hfu.eventmy.activities.SearchActivity;
import thesis.hfu.eventmy.functions.BuildJSON;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.objects.*;

import java.sql.Date;
import java.util.ArrayList;

public class DBfunctions {

    private static DBfunctions instance;
    private static final String MESSAGE = "message";
    private static final String STATUS = "status";

    //Login
    private static final String USER_ID = "user_id";
    private static final String URL_LOGIN = "login_check.php";

    //Registration
    private static final String URL_REGISTRATION = "register_user.php";

    //All Events
    private static final String EVENTS = "events";
    private static final String URL_GET_ALL_EVENTS = "get_all_events.php";

    //Create Event
    private static final String URL_CREATE_EVENT = "create_event.php";

    //Create Task
    private static final String EVENT_ID = "event_id";
    private static final String URL_CREATE_TASK = "create_task.php";

    //Update all Tasks
    private static final String TASKS = "tasks";
    private static final String URL_GET_ALL_TASKS_OF_EVENT = "get_all_tasks_from_event.php";

    //Search
    private static final String USERS = "users";
    private static final String URL_SEARCH_USER = "search_user.php";

    //Remove Friend
    private static final String URL_REMOVE_FRIEND = "remove_friend.php";

    //Friend Request
    private static final String URL_FRIEND_REQUEST= "friend_request.php";

    //Update Percentage
    private static final String URL_UPDATE_PERCENTAGE_OF_TASK= "update_percentage_of_task.php";


    public static DBfunctions getInstance() {
        if (DBfunctions.instance == null) {
            DBfunctions.instance = new DBfunctions();
        }
        return DBfunctions.instance;
    }

    public void login(final Context context, String email, String password) {
        RequestParams params = BuildJSON.getInstance().loginJSON(email, password);
        DBconnection.post(URL_LOGIN, params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        CheckSharedPreferences.getInstance().setPreferances(context.getApplicationContext(), response.getString(USER_ID));
                        Intent intent = new Intent(context.getApplicationContext(), SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void registration(final Context context, String name, String prename, String email, String password) {

        RequestParams params = BuildJSON.getInstance().registrationJSON(name, prename, email, password);
        DBconnection.post(URL_REGISTRATION, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateAllEvents(final Context context, final RecyclerView recyclerView, String user_id) {

        RequestParams params = BuildJSON.getInstance().updateAllEventsJSON(user_id);
        DBconnection.post(URL_GET_ALL_EVENTS, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        ArrayList<Event> eventList = BuildJSON.getInstance().getAllEventsJSON(response.getJSONArray(EVENTS));
                        RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> recAdapter = new AllEventsListAdapter(context.getApplicationContext(), eventList);
                        recyclerView.setAdapter(recAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createEvent(final Context context, String eventName, String location, Date date, String user_id) {

        RequestParams params = BuildJSON.getInstance().createEventJSON(eventName, location, date, user_id);
        DBconnection.post(URL_CREATE_EVENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context.getApplicationContext(), AllEventsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void createTask(final Context context, final int event_id, int editor_id, String task, String description, String quantity) {

        RequestParams params = BuildJSON.getInstance().createTaskJSON(event_id, editor_id, task, description, quantity);
        DBconnection.post(URL_CREATE_TASK, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context.getApplicationContext(), AllTasksOfEventActivity.class);
                    intent.putExtra(EVENT_ID, event_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void updateAllTasks(final Context context, final RecyclerView recyclerView, int event_id) {

        RequestParams params = BuildJSON.getInstance().updateAllTasksJSON(event_id);
        DBconnection.post(URL_GET_ALL_TASKS_OF_EVENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {

                        ArrayList<Task> taskList = BuildJSON.getInstance().getAllTasksOfEventJSON(response.getJSONArray(TASKS));
                        RecyclerView.Adapter<AllTasksOfEventListAdapter.MyViewHolder> recAdapter = new AllTasksOfEventListAdapter(context.getApplicationContext(), taskList);
                        recyclerView.setAdapter(recAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void searchUser(final Context context, final RecyclerView recyclerView, String search, String user_id) {

        RequestParams params = BuildJSON.getInstance().searchUserJSON(search, user_id);
        DBconnection.post(URL_SEARCH_USER, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        ArrayList<User> userList = BuildJSON.getInstance().getAllUsersJSON(response.getJSONArray(USERS));
                        RecyclerView.Adapter<SearchListAdapter.MyViewHolder> recAdapter = new SearchListAdapter(context.getApplicationContext(), userList);
                        recyclerView.setAdapter(recAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void removeFriend(final Context context,String usera_id, String userb_id) {

        RequestParams params = BuildJSON.getInstance().addFriendJSON(usera_id, userb_id);
        DBconnection.post(URL_REMOVE_FRIEND, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void friendRequest(final Context context,String user1_id,String user2_id){

        RequestParams params = BuildJSON.getInstance().addFriendJSON(user1_id, user2_id);
        DBconnection.post(URL_FRIEND_REQUEST, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(),response.getString(MESSAGE),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
