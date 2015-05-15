package thesis.hfu.eventmy.functions;

import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import thesis.hfu.eventmy.objects.Event;
import thesis.hfu.eventmy.objects.History;
import thesis.hfu.eventmy.objects.Task;
import thesis.hfu.eventmy.objects.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BuildJSON {

    private static BuildJSON instance;
    private static final String ID="id";
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
    private static final String STATUS ="status";
    private static final String COSTS_OF_EVENT="costs_of_event";
    private static final String NUM_ORGANIZERS_EVENT="num_organizers_event";
    private static final String EDITOR_ID="editor_id";
    private static final String TASK="task";
    private static final String DESCRIPTION="description";
    private static final String HISTORY="history";
    private static final String QUANTITY="quantity";
    private static final String TASK_ID="task_id";
    private static final String COSTS_OF_TASK="costs_of_task";
    private static final String PERCENTAGE_OF_TASK="percentage_of_task";
    private static final String PERCENTAGE_OF_EVENT="percentage_of_event";
    private static final String EDITOR_NAME="editor_name";
    private static final String TYPE_OF_UPDATE="type_of_update";
    private static final String ADMIN_ID="admin_id";
    private static final String EVENT_NAME="event_name";
    private static final String EMPTY_STRING="";
    private static final String COMMENT="comment";


    public static BuildJSON getInstance(){
        if (BuildJSON.instance == null){
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

    public RequestParams searchAllUsersJSON(String search, String admin_id) {

        RequestParams params= new RequestParams();
        params.put(SEARCH, search);
        params.put(ADMIN_ID,admin_id);
        return params;
    }

    public RequestParams getFriendsListJSON(String search,String admin_id) {

        RequestParams params= new RequestParams();
        params.put(ADMIN_ID,Integer.parseInt(admin_id));
        params.put(SEARCH,search);
        return params;
    }

    public RequestParams searchFriendsForEventJSON(String admin_id, int event_id, String search) {

        RequestParams params= new RequestParams();
        params.put(ADMIN_ID, Integer.parseInt(admin_id));
        params.put(EVENT_ID,event_id);
        params.put(SEARCH,search);
        return params;
    }

    public RequestParams searchFriendsForTaskJSON(String admin_id,String search) {

        RequestParams params= new RequestParams();
        params.put(ADMIN_ID, Integer.parseInt(admin_id));
        params.put(SEARCH,search);
        return params;
    }

    public RequestParams addRemoveFriendEventJSON(int user_id, int event_id) {

        RequestParams params= new RequestParams();
        params.put(USER_ID, user_id);
        params.put(EVENT_ID,event_id);
        return params;
    }


    public RequestParams addRemoveFriendJSON(String admin_id, int user_id) {

        RequestParams params= new RequestParams();
        params.put(USER_A_ID, Integer.parseInt(admin_id));
        params.put(USER_B_ID, user_id);
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

    public RequestParams createTaskJSON(int event_id,String admin_id,int editor_id,String task,String description,String quantity) {

        RequestParams params= new RequestParams();
        params.put(EVENT_ID, event_id);
        params.put(ADMIN_ID,admin_id);
        params.put(EDITOR_ID,editor_id);
        params.put(TASK,task);
        params.put(DESCRIPTION,description);
        params.put(QUANTITY,quantity);
        return params;
    }

    public RequestParams getAllEventsJSON(String admin_id) {

        RequestParams params= new RequestParams();
        params.put(ADMIN_ID, Integer.parseInt(admin_id));
        return params;
    }

    public RequestParams getAllTasksJSON(int event_id) {

        RequestParams params= new RequestParams();
        params.put(EVENT_ID, event_id);
        return params;
    }

    public RequestParams getEventDetailsJSON(String admin_id, int event_id) {

        RequestParams params= new RequestParams();
        params.put(ADMIN_ID, Integer.parseInt(admin_id));
        params.put(EVENT_ID, event_id);
        return params;
    }

    public RequestParams editPercentageOfTaskJSON(int task_id, int admin_id, int percentage) {

        RequestParams params= new RequestParams();
        params.put(TASK_ID,task_id);
        params.put(ADMIN_ID,admin_id);
        params.put(PERCENTAGE_OF_TASK,percentage);
        return params;
    }

    public RequestParams editEventDetailsJSON(int event_id,String admin_id, String name, String location, Date date) {

        RequestParams params= new RequestParams();
        params.put(EVENT_ID,event_id);
        params.put(ADMIN_ID,Integer.parseInt(admin_id));
        params.put(NAME,name);
        params.put(LOCATION,location);
        int no_date_change;
        if(date==null){
            no_date_change=-1;
            params.put(DATE,no_date_change);
        }else{
            params.put(DATE,date);
        }
        return params;
    }

    public RequestParams getCostsOfTaskJSON(int task_id, int editor_id, double costs, int type_of_update) {

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
            int status= jsonArray.getJSONObject(i).getInt(STATUS);
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
                date = df.parse(jsonArray.getJSONObject(i).getString(DATE));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Event event = new Event(event_id, costsOfEvent,percentageOfEvent,numOrganizersEvent, name, location, date);
            eventList.add(event);
        }
        return eventList;
    }

    public Event getEventJSON(JSONArray jsonArray) throws JSONException {

        Event event=null;

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
                date = df.parse(jsonArray.getJSONObject(i).getString(DATE));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            event = new Event(event_id, costsOfEvent,percentageOfEvent,numOrganizersEvent, name, location, date);
        }
        return event;
    }

    public ArrayList<Task>  getAllTasksOfEventJSON(JSONArray jsonArray) throws JSONException {

        ArrayList<Task> taskList= new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++) {
            int task_id=jsonArray.getJSONObject(i).getInt(TASK_ID);
            int event_id=jsonArray.getJSONObject(i).getInt(EVENT_ID);
            String task=jsonArray.getJSONObject(i).getString(TASK);
            String quantity=jsonArray.getJSONObject(i).getString(QUANTITY);
            Double costs_of_task=jsonArray.getJSONObject(i).getDouble(COSTS_OF_TASK);
            int percentage_of_task=jsonArray.getJSONObject(i).getInt(PERCENTAGE_OF_TASK);
            String editor_name=jsonArray.getJSONObject(i).getString(EDITOR_NAME);
            int editor_id=jsonArray.getJSONObject(i).getInt(EDITOR_ID);

            Task newTask= new Task(task_id,event_id,percentage_of_task,editor_id,costs_of_task,task, quantity,editor_name,EMPTY_STRING);
            taskList.add(newTask);
        }
        return taskList;
    }

    public RequestParams getTaskDetailsJSON(int task_id) {

        RequestParams params= new RequestParams();
        params.put(TASK_ID, task_id);
        return params;
    }

    public Task getTaskDetailsJSON(JSONObject jsonObject,int taskID) throws JSONException {

            int task_id=taskID;
            int event_id=jsonObject.getInt(EVENT_ID);
            String task=jsonObject.getString(TASK);
            String quantity=jsonObject.getString(QUANTITY);
            Double costs_of_task=jsonObject.getDouble(COSTS_OF_TASK);
            int percentage_of_task=jsonObject.getInt(PERCENTAGE_OF_TASK);
            String editor_name=jsonObject.getString(EDITOR_NAME);
            int editor_id=jsonObject.getInt(EDITOR_ID);
            String event_name=jsonObject.getString(EVENT_NAME);
            Task newTask= new Task(task_id,event_id,percentage_of_task,editor_id,costs_of_task,task, quantity,editor_name,event_name);

        return newTask;
    }

    public RequestParams deleteTaskJSON(int task_id, String admin_id) {

        RequestParams params= new RequestParams();
        params.put(TASK_ID, task_id);
        params.put(ADMIN_ID,Integer.parseInt(admin_id));
        return params;
    }

    public RequestParams deleteArchivEventJSON(int event_id, String admin_id) {

        RequestParams params= new RequestParams();
        params.put(EVENT_ID, event_id);
        params.put(ADMIN_ID,Integer.parseInt(admin_id));
        return params;
    }

    public RequestParams commentTaskJSON(int task_id, int admin_id, String comment) {

        RequestParams params= new RequestParams();
        params.put(TASK_ID, task_id);
        params.put(ADMIN_ID,admin_id);
        params.put(COMMENT,comment);
        return params;
    }

    public RequestParams commentEventJSON(int event_id, int admin_id, String comment) {

        RequestParams params= new RequestParams();
        params.put(EVENT_ID, event_id);
        params.put(ADMIN_ID,admin_id);
        params.put(COMMENT,comment);
        return params;
    }

    public RequestParams getCommentsJSON(int id) {

        RequestParams params= new RequestParams();
        params.put(ID, id);
        return params;
    }


    public RequestParams deleteCommentsJSON(int history_id) {

        RequestParams params= new RequestParams();
        params.put(ID, history_id);
        return params;
    }

    public ArrayList<History> getComments(JSONArray jsonArray) throws JSONException{

        ArrayList<History> commentList = new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++) {
            String name=jsonArray.getJSONObject(i).getString(NAME);
            String date=jsonArray.getJSONObject(i).getString(DATE);
            String comment=jsonArray.getJSONObject(i).getString(COMMENT);
            int history_id=jsonArray.getJSONObject(i).getInt(ID);
            int editor_id=jsonArray.getJSONObject(i).getInt(EDITOR_ID);
            History history= new History(date,name,comment,history_id,editor_id);
            commentList.add(history);
        }
        return commentList;
    }

    public RequestParams updateTaskNameQuantityJSON(String admin_id,int task_id,String quantity, String task) {

        RequestParams params= new RequestParams();
        params.put(ADMIN_ID, Integer.parseInt(admin_id));
        params.put(TASK_ID, task_id);
        params.put(QUANTITY, quantity);
        params.put(TASK, task);
        return params;
    }
}

