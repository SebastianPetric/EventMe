package thesis.hfu.eventmy.functions;

import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import thesis.hfu.eventmy.objects.Event;
import thesis.hfu.eventmy.objects.Task;
import thesis.hfu.eventmy.objects.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private static final String COSTS_OF_EVENT="costs_of_event";
    private static final String NUM_ORGANIZERS_EVENT="num_organizers_event";
    private static final String EDITOR_ID="editor_id";
    private static final String TASK="task";
    private static final String DESCRIPTION="description";
    private static final String QUANTITY="quantity";
    private static final String TASK_ID="task_id";
    private static final String COSTS_OF_TASK="costs_of_task";
    private static final String PERCENTAGE_OF_TASK="percentage_of_task";
    private static final String PERCENTAGE_OF_EVENT="percentage_of_event";
    private static final String EDITOR_NAME="editor_name";
    private static final String TYPE_OF_UPDATE="type_of_update";



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


    public RequestParams createTaskJSON(int event_id,int editor_id,String task,String description,String quantity) {

        RequestParams params= new RequestParams();
        params.put(EVENT_ID, event_id);
        params.put(EDITOR_ID,editor_id);
        params.put(TASK,task);
        params.put(DESCRIPTION,description);
        params.put(QUANTITY,quantity);
        return params;
    }
    public RequestParams updateAllEventsJSON(String user_id) {

        RequestParams params= new RequestParams();
        params.put(USER_ID, Integer.parseInt(user_id));
        return params;
    }

    public RequestParams updateAllTasksJSON(int event_id) {

        RequestParams params= new RequestParams();
        params.put(EVENT_ID, event_id);
        return params;
    }

    public RequestParams updatePercentageOfTaskJSON(int task_id,int editor_id,int percentage) {

        RequestParams params= new RequestParams();
        params.put(TASK_ID,task_id);
        params.put(EDITOR_ID,editor_id);
        params.put(PERCENTAGE_OF_TASK,percentage);
        return params;
    }
    public RequestParams updateCostsOfTaskJSON(int task_id, int editor_id, double costs, int type_of_update) {

        RequestParams params= new RequestParams();
        params.put(TASK_ID,task_id);
        params.put(EDITOR_ID,editor_id);
        params.put(COSTS_OF_TASK,costs);
        params.put(TYPE_OF_UPDATE,type_of_update);
        return params;
    }
    public RequestParams becomeEditorOfTaskJSON(int editor_id,int task_id) {

        RequestParams params= new RequestParams();
        params.put(EDITOR_ID, editor_id);
        params.put(TASK_ID, task_id);
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

        for(int i=0;i<jsonArray.length();i++) {
            int event_id = jsonArray.getJSONObject(i).getInt(EVENT_ID);
            String name = jsonArray.getJSONObject(i).getString(NAME);
            String location = jsonArray.getJSONObject(i).getString(LOCATION);
            double costsOfEvent = Double.parseDouble(jsonArray.getJSONObject(i).getString(COSTS_OF_EVENT));
            int numOrganizersEvent = Integer.parseInt(jsonArray.getJSONObject(i).getString(NUM_ORGANIZERS_EVENT));
            double percentageOfEvent=jsonArray.getJSONObject(i).getDouble(PERCENTAGE_OF_EVENT);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = (Date) df.parse(jsonArray.getJSONObject(i).getString(DATE));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Event event = new Event(event_id, costsOfEvent,percentageOfEvent,numOrganizersEvent, name, location, date);
            eventList.add(event);
        }
        return eventList;
    }

    public ArrayList<Task>  getAllTasksOfEventJSON(JSONArray jsonArray) throws JSONException {
        ArrayList<Task> taskList= new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++) {
            int task_id=jsonArray.getJSONObject(i).getInt(TASK_ID);
            int event_id=jsonArray.getJSONObject(i).getInt(EVENT_ID);
            String task=jsonArray.getJSONObject(i).getString(TASK);
            String quantity=jsonArray.getJSONObject(i).getString(QUANTITY);
            String description=jsonArray.getJSONObject(i).getString(DESCRIPTION);
            Double costs_of_task=jsonArray.getJSONObject(i).getDouble(COSTS_OF_TASK);
            int percentage_of_task=jsonArray.getJSONObject(i).getInt(PERCENTAGE_OF_TASK);
            String editor_name=jsonArray.getJSONObject(i).getString(EDITOR_NAME);
            int editor_id=jsonArray.getJSONObject(i).getInt(EDITOR_ID);

            Task newTask= new Task(task_id,event_id,percentage_of_task,editor_id,costs_of_task,task, quantity,description,editor_name);
            taskList.add(newTask);
        }
        return taskList;
    }
}

