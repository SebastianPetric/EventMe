package thesis.hfu.eventmy.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBconnection;
import thesis.hfu.eventmy.functions.BuildJSON;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.objects.AllTasksOfEventListAdapter;
import thesis.hfu.eventmy.objects.Task;

import java.util.ArrayList;


public class AllTasksOfEventActivity extends Activity {

    private ImageButton addTaskButton;
    private RecyclerView allTasksOfEventRecycler;
    private ArrayList<Task> taskList;
    private RecyclerView.Adapter<AllTasksOfEventListAdapter.MyViewHolder> recAdapter;
    private int event_id;

    private static final String STATUS="status";
    private static final String MESSAGE="message";
    private static final String TASKS="tasks";
    private static final String EVENT_ID="event_id";

    private static final String URL_GET_ALL_TASKS_OF_EVENT= "get_all_tasks_from_event.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks_of_event);


        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setAddTaskButton(R.id.imageButtonAddNewTask);
            setAllTasksOfEventRecycler(R.id.recyclerViewAllTasksOfEvent);
            allTasksOfEventRecycler.setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            allTasksOfEventRecycler.setLayoutManager(layoutManager);
            updateAllTasks(getEvent_id());
            getAddTaskButton().setOnClickListener(new AddTaskCLickListener());
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }

    public class AddTaskCLickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.imageButtonAddNewTask){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){

                    createTask();
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }

    //----------------------------------------------------------------------
    //-----------------Functions-------------------------------------
    //----------------------------------------------------------------------

    public void updateAllTasks(int event_id){

        RequestParams params= BuildJSON.getInstance().updateAllTasksJSON(event_id);
        DBconnection.post(URL_GET_ALL_TASKS_OF_EVENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();

                    if (response.getInt(STATUS) == 200) {
                        setTaskList(BuildJSON.getInstance().getAllTasksOfEventJSON(response.getJSONArray(TASKS)));
                        recAdapter = new AllTasksOfEventListAdapter(getApplicationContext(), getTaskList());
                        allTasksOfEventRecycler.setAdapter(recAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    //----------------------------------------------------------------------
    //-----------------Functions-------------------------------------
    //----------------------------------------------------------------------


    public void createTask(){
        setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
        Intent intent= new Intent(getApplicationContext(),CreateTaskActivity.class);
        intent.putExtra(EVENT_ID,getEvent_id());
        startActivity(intent);
    }


    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public ImageButton getAddTaskButton() {
        return addTaskButton;
    }
    public void setAddTaskButton(int res) {
        this.addTaskButton = (ImageButton) findViewById(res);
    }
    public RecyclerView getAllTasksOfEventRecycler() {
        return allTasksOfEventRecycler;
    }
    public void setAllTasksOfEventRecycler(int res) {
        this.allTasksOfEventRecycler = (RecyclerView) findViewById(res);
    }
    public ArrayList<Task> getTaskList() {
        return taskList;
    }
    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public int getEvent_id(){
        return this.event_id;
    }
}
