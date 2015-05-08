package thesis.hfu.eventmy.database;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import thesis.hfu.eventmy.adapter.*;
import thesis.hfu.eventmy.functions.BuildJSON;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.objects.*;

import java.sql.Date;
import java.util.ArrayList;

public class DBfunctions {

    private static DBfunctions instance;
    private int overallYScroll = 0;
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

    //All ARCHIV EVENTS
    private static final String URL_GET_ALL_ARCHIV_EVENTS = "get_all_archiv_events.php";

    //Create Event
    private static final String URL_CREATE_EVENT = "create_event.php";

    //Create Task
    private static final String URL_CREATE_TASK = "create_task.php";

    //Update all Tasks
    private static final String TASKS = "tasks";
    private static final String URL_GET_ALL_TASKS_OF_EVENT = "get_all_tasks_from_event.php";

    //Search
    private static final String USERS = "users";
    private static final String URL_SEARCH_USER = "search_user.php";

    //Search Friend for Event
    private static final String URL_SEARCH_FRIENDS_EVENT = "search_friends_for_event.php";

    //Get FriendsList
    private static final String URL_GET_FRIENDSLIST = "get_friendlist.php";

    //Update Event Details
    private static final String URL_UPDATE_EVENT_DETAILS = "update_event_details.php";

    //Delete Task
    private static final String URL_DELETE_TASK = "delete_task.php";

    //Delete Event
    private static final String URL_DELETE_EVENT = "delete_event.php";

    //Archiv Event
    private static final String URL_ARCHIV_EVENT = "archiv_event.php";

    //Comment on task
    private static final String URL_COMMENT_ON_TASK = "comment_on_task.php";

    //Get task details
    private static final String URL_GET_TASK_DETAILS = "get_task_details.php";

    //Become editor of task
    private static final String URL_BECOME_EDITOR_OF_TASK= "become_editor_of_task.php";

    //Update percentage of task
    private static final String URL_UPDATE_PERCENTAGE_OF_TASK= "update_percentage_of_task.php";

    //Update costs of task
    private static final String URL_UPDATE_COSTS_OF_TASK = "update_costs_of_task.php";

    //Edit task details
    private static final String URL_UPDATE_TASK_QUANTITY_NAME = "update_task_quantity_name.php";
    private static final String EDITOR_NAME = "editor_name";
    private static final String EMPTY_STRING = "";

    //Search friend for task
    private static final String URL_SEARCH_FRIENDS_TASK="search_friends_for_task.php";

    //Comment on Event
    private static final String URL_COMMENT_ON_EVENT="comment_on_event.php";

    //Add Friend to Event
    private static final String URL_FRIEND_TO_EVENT = "add_friend_to_event.php";

    //Remove Friend from Event
    private static final String URL_REMOVE_FRIEND_FROM_EVENT = "remove_friend_from_event.php";

    //Remove Friend from Friendslist
    private static final String URL_REMOVE_FRIEND_FROM_FRIENDSLIST = "remove_friend.php";

    //Add Friend to Friendlist
    private static final String URL_ADD_FRIEND_TO_FRIENDSLIST = "friend_request.php";

    //Friend Request
    private static final String URL_FRIEND_REQUEST= "friend_request.php";

    //Remove Friend
    private static final String URL_REMOVE_FRIEND = "remove_friend.php";

    //Edit event details
    private static final String URL_EDIT_EVENT_DETAILS = "edit_event_details.php";

    //Get Task Comments
    private static final String URL_GET_TASK_COMMENTS = "get_task_comments.php";

    //Get Event Comments
    private static final String HISTORY = "history";
    private static final String URL_GET_EVENT_COMMENTS = "get_event_comments.php";

    public static DBfunctions getInstance() {
        if (DBfunctions.instance == null) {
            DBfunctions.instance = new DBfunctions();
        }
        return DBfunctions.instance;
    }

    public void login(final Context context, String email, String password) {
        RequestParams params = BuildJSON.getInstance().loginJSON(email, password);
        DBconnection.post(URL_LOGIN, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        CheckSharedPreferences.getInstance().setPreferances(context.getApplicationContext(), response.getString(USER_ID));
                        StartActivityFunctions.getInstance().startAllEventsActivity(context.getApplicationContext());
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
                    StartActivityFunctions.getInstance().startLoginActivity(context.getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getAllArchivEvents(final Activity context,  final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView, String admin_id) {

        RequestParams params = BuildJSON.getInstance().getAllEventsJSON(admin_id);
        DBconnection.post(URL_GET_ALL_ARCHIV_EVENTS, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        final ArrayList<Event> eventList = BuildJSON.getInstance().getAllEventsJSON(response.getJSONArray(EVENTS));
                        RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> recAdapter = new AllEventsListAdapter(context, eventList);
                        recyclerView.setAdapter(recAdapter);
                    }
                    if(swipeRefreshLayout!=null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void getAllTasks(final Activity context, final ProgressBar progressBarEvent,final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView, final int event_id, final TextView totalOrganizersTextView, final TextView totalPercentageTextView, final TextView totalCostsTextView, final TextView nameTextView, final TextView dateTextView, final TextView locationTextView) {

        RequestParams params = BuildJSON.getInstance().getAllTasksJSON(event_id);
        DBconnection.post(URL_GET_ALL_TASKS_OF_EVENT, params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                            if (response.getInt(STATUS) == 200) {
                                ArrayList<Task> taskList = BuildJSON.getInstance().getAllTasksOfEventJSON(response.getJSONArray(TASKS));
                                RecyclerView.Adapter<AllTasksOfEventListAdapter.MyViewHolder> recAdapter = new AllTasksOfEventListAdapter(context,progressBarEvent,recyclerView,swipeRefreshLayout, taskList, nameTextView, dateTextView, totalOrganizersTextView, totalCostsTextView, totalPercentageTextView,locationTextView,event_id);
                                recyclerView.setAdapter(recAdapter);
                                recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                        super.onScrolled(recyclerView, dx, dy);
                                        overallYScroll = overallYScroll + dy;
                                        if (overallYScroll <= 0) {
                                            swipeRefreshLayout.setEnabled(true);
                                        } else {
                                            swipeRefreshLayout.setEnabled(false);
                                        }
                                    }
                                });
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    public void createEvent(final Context context, String eventName, String location, Date date, String admin_id) {

        RequestParams params = BuildJSON.getInstance().createEventJSON(eventName, location, date, admin_id);
        DBconnection.post(URL_CREATE_EVENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    StartActivityFunctions.getInstance().startAllEventsActivity(context.getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createTask(final Context context, final int event_id, String admin_id, int editor_id, String task, String description, String quantity) {

        RequestParams params = BuildJSON.getInstance().createTaskJSON(event_id, admin_id, editor_id, task, description, quantity);
        DBconnection.post(URL_CREATE_TASK, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    StartActivityFunctions.getInstance().startAllTasksActivity(context.getApplicationContext(), event_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void searchAllUsers(final Context context, final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView, String search, String admin_id) {

        RequestParams params = BuildJSON.getInstance().searchAllUsersJSON(search, admin_id);
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
                    if(swipeRefreshLayout!=null){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void searchFriendsForEvent(final Context context,final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView, String search, String admin_id, final int event_id) {

        RequestParams params = BuildJSON.getInstance().searchFriendsForEventJSON(admin_id, event_id, search);
        DBconnection.post(URL_SEARCH_FRIENDS_EVENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        ArrayList<User> userList = BuildJSON.getInstance().getAllUsersJSON(response.getJSONArray(USERS));
                        RecyclerView.Adapter<EventOrganizersListAdapter.MyViewHolder> recAdapter = new EventOrganizersListAdapter(context.getApplicationContext(), userList,event_id);
                        recyclerView.setAdapter(recAdapter);
                    }
                    if(swipeRefreshLayout!=null){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void searchFriendsForTask(final Activity activity,final Context context,final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView, final String search, String admin_id, final int event_id) {

        RequestParams params = BuildJSON.getInstance().searchFriendsForTaskJSON(admin_id, search);
        DBconnection.post(URL_SEARCH_FRIENDS_TASK, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        ArrayList<User> userList = BuildJSON.getInstance().getAllUsersJSON(response.getJSONArray(USERS));
                        RecyclerView.Adapter<TaskOrganizersListAdapter.MyViewHolder> recAdapter = new TaskOrganizersListAdapter(activity,userList,event_id);
                        recyclerView.setAdapter(recAdapter);
                    }
                    if(swipeRefreshLayout!=null){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void searchFriendsList(final Context context, final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView, String search, String admin_id) {

        RequestParams params = BuildJSON.getInstance().getFriendsListJSON(search, admin_id);
        DBconnection.post(URL_GET_FRIENDSLIST, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        ArrayList<User> userList = BuildJSON.getInstance().getAllUsersJSON(response.getJSONArray(USERS));
                        RecyclerView.Adapter<FriendsListAdapter.MyViewHolder> recAdapter = new FriendsListAdapter(context.getApplicationContext(), userList);
                        recyclerView.setAdapter(recAdapter);
                    }
                    if(swipeRefreshLayout!=null){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteTask(final Context context, int task_id, String admin_id, final int event_id){

        RequestParams params = BuildJSON.getInstance().deleteTaskJSON(task_id, admin_id);
        DBconnection.post(URL_DELETE_TASK,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        StartActivityFunctions.getInstance().startAllTasksActivity(context, event_id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteEvent(final Context context, final ArrayList<Event> eventList, final RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> adapter, final int position,String admin_id, final int event_id){

        RequestParams params = BuildJSON.getInstance().deleteArchivEventJSON(event_id, admin_id);
        DBconnection.post(URL_DELETE_EVENT,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        eventList.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteEvent(final Context context, String admin_id, final int event_id){

        RequestParams params = BuildJSON.getInstance().deleteArchivEventJSON(event_id, admin_id);
        DBconnection.post(URL_DELETE_EVENT,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        StartActivityFunctions.getInstance().startAllEventsActivity(context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("schlecht",responseString);
            }
        });
    }

    public void archiveEvent(final Context context, String admin_id, final int event_id){

        RequestParams params = BuildJSON.getInstance().deleteArchivEventJSON(event_id, admin_id);
        DBconnection.post(URL_ARCHIV_EVENT,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        StartActivityFunctions.getInstance().startAllEventsActivity(context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void archivEvent(final Context context, final ArrayList<Event> eventList, final RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> adapter, final int position,String admin_id, final int event_id){

        RequestParams params = BuildJSON.getInstance().deleteArchivEventJSON(event_id, admin_id);
        DBconnection.post(URL_ARCHIV_EVENT,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        eventList.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void editEventDetails(final Context context,final TextView eventNameTextView, final TextView eventDateTextView,final TextView eventLocationTextView,String admin_id,int event_id, final String name, final String location, final Date date){

        RequestParams params = BuildJSON.getInstance().editEventDetailsJSON(event_id, admin_id, name, location, date);
        DBconnection.post(URL_EDIT_EVENT_DETAILS,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(),response.getString(MESSAGE),Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        if(!name.equals(EMPTY_STRING)){
                            eventNameTextView.setText(name);
                        }
                        if(!location.equals(EMPTY_STRING)){
                            eventLocationTextView.setText(location);
                        }
                        if(date!=null){
                            eventDateTextView.setText(date.getDate() + "." + (date.getMonth() + 1) + "." + date.getYear());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updatePercentage(final Context context,final ProgressBar progressBarEvent ,final ProgressBar progressBarTask,final SwipeRefreshLayout syncRefresh,final TextView percentageTextView,final TextView totalCostsTextView,final TextView totalPercentageTextView,final TextView totalOrganizersTextView, final TextView eventNameTextView, final TextView eventDateTextView,final TextView eventLocationTextView,final int event_id,int task_id, int editor_id, final int percentage,final int status_of_update){

        RequestParams params = BuildJSON.getInstance().editPercentageOfTaskJSON(task_id, editor_id, percentage);
        DBconnection.post(URL_UPDATE_PERCENTAGE_OF_TASK, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        percentageTextView.setText(String.valueOf(percentage));
                        if(progressBarTask!=null) {
                            progressBarTask.setProgress(percentage);
                        }
                        //If there aren't the fields totalOrganizers etc than this won't happen
                        if(status_of_update==1){
                            if(CheckSharedPreferences.getInstance().isLoggedIn(context.getApplicationContext())){
                               updateEventDetails(syncRefresh,progressBarEvent,CheckSharedPreferences.getInstance().getAdmin_id(), totalOrganizersTextView, totalPercentageTextView, totalCostsTextView,eventNameTextView,eventDateTextView,eventLocationTextView, event_id);
                            }else{
                                CheckSharedPreferences.getInstance().endSession(context.getApplicationContext());
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateCosts(final Context context, final ProgressBar progressBar, final SwipeRefreshLayout syncRefresh,final TextView costsFieldTextView, final TextView totalOrganizersTextView, final TextView totalPercentageTextView, final TextView totalCostsTextView, final TextView eventNameTextView, final TextView eventDateTextView,final TextView eventLocationTextView, final int event_id,int task_id, int editor_id, final double costs, final int type_of_calculation, final int status_of_update){

        RequestParams params = BuildJSON.getInstance().getCostsOfTaskJSON(task_id, editor_id, costs, type_of_calculation);
        DBconnection.post(URL_UPDATE_COSTS_OF_TASK,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(),response.getString(MESSAGE),Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        if(type_of_calculation==0) {
                            costsFieldTextView.setText(String.valueOf(costs));
                        }else if(type_of_calculation==1){
                            costsFieldTextView.setText(String.valueOf((Double.parseDouble(costsFieldTextView.getText().toString()) + costs)));
                        }
                        //If there aren't the fields totalOrganizers etc than this won't happen
                        if(status_of_update==1){
                            if(CheckSharedPreferences.getInstance().isLoggedIn(context.getApplicationContext())){
                                updateEventDetails(syncRefresh,progressBar,CheckSharedPreferences.getInstance().getAdmin_id(), totalOrganizersTextView, totalPercentageTextView, totalCostsTextView,eventNameTextView,eventDateTextView,eventLocationTextView, event_id);
                            }else{
                                CheckSharedPreferences.getInstance().endSession(context.getApplicationContext());
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void changeEditorOfTask(final Context context,final TextView editorTextView,final String editor_id, int task_id){

        RequestParams params= BuildJSON.getInstance().becomeEditorOfTaskJSON(Integer.parseInt(editor_id), task_id);
        DBconnection.post(URL_BECOME_EDITOR_OF_TASK, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context, response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                       editorTextView.setText(response.getString(EDITOR_NAME));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addFriendToEvent(final Context context, final RecyclerView.Adapter<EventOrganizersListAdapter.MyViewHolder> adapter, final ArrayList<User> userList, final int position,int user_id,int event_id){

        RequestParams params = BuildJSON.getInstance().addRemoveFriendEventJSON(user_id, event_id);
        DBconnection.post(URL_FRIEND_TO_EVENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        final User user_b = userList.get(position);

                        // User wird hinzugefügt
                        user_b.setStatus(1);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void removeFriendFromEvent(final Context context, final RecyclerView.Adapter<EventOrganizersListAdapter.MyViewHolder> adapter, final ArrayList<User> userList, final int position,int user_id,int event_id) {

        RequestParams params = BuildJSON.getInstance().addRemoveFriendEventJSON(user_id, event_id);
        DBconnection.post(URL_REMOVE_FRIEND_FROM_EVENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        final User user_b = userList.get(position);

                        //User wird entfernt
                        user_b.setStatus(0);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void removeFriendFromFriendsList(final Context context, final RecyclerView.Adapter<FriendsListAdapter.MyViewHolder> adapter, final ArrayList<User> userList, final int position,int user_id,String admin_id) {

        RequestParams params = BuildJSON.getInstance().addRemoveFriendJSON(admin_id, user_id);
        DBconnection.post(URL_REMOVE_FRIEND_FROM_FRIENDSLIST, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        userList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addFriendToFriendslist(final Context context, final RecyclerView.Adapter<FriendsListAdapter.MyViewHolder> adapter, final ArrayList<User> userList, final int position,int user_id,String admin_id) {

        RequestParams params = BuildJSON.getInstance().addRemoveFriendJSON(admin_id, user_id);
        DBconnection.post(URL_ADD_FRIEND_TO_FRIENDSLIST, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(),response.getString(MESSAGE),Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        final User user_b = userList.get(position);

                        //Users become friends
                        if(user_b.getStatus()==3){
                            user_b.setStatus(2);
                            adapter.notifyDataSetChanged();

                            //Request is open and has to be answered by the other user
                        }else if(user_b.getStatus()==1){
                            user_b.setStatus(0);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void friendRequest(final Context context, final RecyclerView.Adapter<SearchListAdapter.MyViewHolder> adapter, final ArrayList<User> userList, final int position,int user_id,String admin_id){

        RequestParams params = BuildJSON.getInstance().addRemoveFriendJSON(admin_id, user_id);
        DBconnection.post(URL_FRIEND_REQUEST, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        final User user_b = userList.get(position);

                        //Users become friends
                        if (user_b.getStatus() == 3) {
                            user_b.setStatus(2);
                            adapter.notifyDataSetChanged();
                            //Request is open and has to be answered by the other user
                        } else if (user_b.getStatus() == 1) {
                            user_b.setStatus(0);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void removeFriend(final Context context, final RecyclerView.Adapter<SearchListAdapter.MyViewHolder> adapter, final ArrayList<User> userList, final int position,int user_id,String admin_id) {

        RequestParams params = BuildJSON.getInstance().addRemoveFriendJSON(admin_id, user_id);
        DBconnection.post(URL_REMOVE_FRIEND, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {

                        //Friend deleted
                        final User user_b = userList.get(position);
                        user_b.setStatus(1);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addFriendToTask(Activity context, ArrayList<User> userList,int position, int event_id){

        final User user_b = userList.get(position);
        Global global = ((Global)context.getApplicationContext());
        global.setEditor_id(user_b.getUser_id());
        global.setEditorName(user_b.getName());
        StartActivityFunctions.getInstance().startCreateTaskFromFriendsActivity(context.getApplicationContext(), event_id);
        context.finish();
    }

    public void getAllEvents(final Activity context,  final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView, String admin_id) {

        RequestParams params = BuildJSON.getInstance().getAllEventsJSON(admin_id);
        DBconnection.post(URL_GET_ALL_EVENTS, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        final ArrayList<Event> eventList = BuildJSON.getInstance().getAllEventsJSON(response.getJSONArray(EVENTS));
                        RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> recAdapter = new AllEventsListAdapter(context, eventList);
                        recyclerView.setAdapter(recAdapter);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void getTaskComments(final Context context, final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView, int task_id){
        final RequestParams params= BuildJSON.getInstance().getCommentsJSON(task_id);
        DBconnection.post(URL_GET_TASK_COMMENTS,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200) {
                        final ArrayList<History> history=BuildJSON.getInstance().getComments(response.getJSONArray(HISTORY));
                        RecyclerView.Adapter<CommentListAdapter.MyViewHolder> recAdapter = new CommentListAdapter(context, history);
                        recyclerView.setAdapter(recAdapter);
                        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                overallYScroll = overallYScroll + dy;
                                if (overallYScroll <= 0) {
                                    swipeRefreshLayout.setEnabled(true);
                                } else {
                                    swipeRefreshLayout.setEnabled(false);
                                }
                                }
                        });
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getEventComments(final Context context, final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView, int event_id){
        final RequestParams params= BuildJSON.getInstance().getCommentsJSON(event_id);
        DBconnection.post(URL_GET_EVENT_COMMENTS,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200) {
                        final ArrayList<History> history=BuildJSON.getInstance().getComments(response.getJSONArray(HISTORY));
                        RecyclerView.Adapter<CommentListAdapter.MyViewHolder> recAdapter = new CommentListAdapter(context, history);
                        recyclerView.setAdapter(recAdapter);
                        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                overallYScroll = overallYScroll + dy;
                                if (overallYScroll <= 0) {
                                    swipeRefreshLayout.setEnabled(true);
                                } else {
                                    swipeRefreshLayout.setEnabled(false);
                                }
                            }
                        });
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void commentOnEvent(final Context context, final RecyclerView recyclerView, final SwipeRefreshLayout synRefresh, final int event_id,int admin_id,String comment){

        RequestParams params= BuildJSON.getInstance().commentEventJSON(event_id, admin_id, comment);
        DBconnection.post(URL_COMMENT_ON_EVENT,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        getEventComments(context, synRefresh, recyclerView, event_id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void updateEventDetails(final SwipeRefreshLayout swipeRefreshLayout,final ProgressBar progressBar,String admin_id, final TextView totalOrganizersTextView, final TextView totalPercentageTextView, final TextView totalCostsTextView, final TextView eventNameTextView, final TextView eventDateTextView, final TextView eventLocationTextView,final int event_id) {

        RequestParams params = BuildJSON.getInstance().getEventDetailsJSON(admin_id, event_id);
        DBconnection.post(URL_UPDATE_EVENT_DETAILS, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt(STATUS) == 200) {
                        Event event = BuildJSON.getInstance().getEventJSON(response.getJSONArray(EVENTS));
                        eventNameTextView.setText(event.getName());
                        eventDateTextView.setText(event.getDate().getDate() + "." + (event.getDate().getMonth() + 1) + "." + event.getDate().getYear());
                        totalOrganizersTextView.setText(String.valueOf(event.getNumOrganizers()));
                        totalCostsTextView.setText(String.valueOf(event.getCosts()));
                        totalPercentageTextView.setText(String.valueOf(event.getPercentage_of_event()));
                        eventLocationTextView.setText(String.valueOf(event.getLocation()));
                        if(progressBar!=null) {
                            progressBar.setProgress((int) event.getPercentage_of_event());
                        }
                    }
                    if(swipeRefreshLayout!=null){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void updateTaskDetails(final Context context, final ProgressBar progressBarTask,final SwipeRefreshLayout swipeRefreshLayout,final RecyclerView recyclerView,final TextView eventNameTextView, final TextView taskFieldTextView, final TextView quantityTextView, final TextView costsTextView, final TextView percentageFieldTextView, final TextView editorTextView,final int task_id){

        RequestParams params= BuildJSON.getInstance().getTaskDetailsJSON(task_id);
        DBconnection.post(URL_GET_TASK_DETAILS,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200) {
                        Task task = BuildJSON.getInstance().getTaskDetailsJSON(response, task_id);
                        percentageFieldTextView.setText(String.valueOf(task.getPercentage()));
                        eventNameTextView.setText(task.getEvent_name());
                        taskFieldTextView.setText(task.getTask());
                        quantityTextView.setText(task.getQuantity());
                        costsTextView.setText(String.valueOf(task.getCostOfTask()));
                        editorTextView.setText(task.getEditor_name());
                        progressBarTask.setProgress(task.getPercentage());
                        getTaskComments(context, swipeRefreshLayout, recyclerView, task_id);
                    }
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void updateTaskNameQuantity(final Context context, final ProgressBar progressBarTask,final SwipeRefreshLayout syncRefresh,final RecyclerView recyclerView,final TextView taskNameTextView, final TextView quantityTextView,final TextView eventNameTextView, final TextView costsTextView, final TextView percentageTextView, final TextView editorTextView, final int task_id, String editor_id, final String quantity, final String task_name){

        RequestParams params = BuildJSON.getInstance().updateTaskNameQuantityJSON(editor_id, task_id, quantity, task_name);
        DBconnection.post(URL_UPDATE_TASK_QUANTITY_NAME,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(),response.getString(MESSAGE),Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        if(!quantity.equals(EMPTY_STRING)){
                            quantityTextView.setText(quantity);
                        }
                        if(!task_name.equals(EMPTY_STRING)){
                            taskNameTextView.setText(task_name);
                        }
                        DBfunctions.getInstance().updateTaskDetails(context, progressBarTask, syncRefresh, recyclerView, eventNameTextView, taskNameTextView, quantityTextView, costsTextView, percentageTextView, editorTextView, task_id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void commentOnTask(final Context context, final ProgressBar progressBarTask, final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView, final TextView eventNameTextView, final TextView taskNameTextView, final TextView quantityTextView, final TextView costsTextView, final TextView percentageTextView, final TextView editorTextView, final int task_id, int admin_id, String comment){

        RequestParams params = BuildJSON.getInstance().commentTaskJSON(task_id, admin_id, comment);
        DBconnection.post(URL_COMMENT_ON_TASK,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        updateTaskDetails(context, progressBarTask, swipeRefreshLayout, recyclerView, eventNameTextView, taskNameTextView, quantityTextView, costsTextView, percentageTextView, editorTextView, task_id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}
