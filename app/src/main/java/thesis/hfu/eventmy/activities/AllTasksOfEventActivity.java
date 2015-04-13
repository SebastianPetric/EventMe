package thesis.hfu.eventmy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;
import thesis.hfu.eventmy.objects.Task;

import java.util.ArrayList;


public class AllTasksOfEventActivity extends Activity {

    private ImageButton addTaskButton;
    private RecyclerView allTasksOfEventRecycler;
    private ArrayList<Task> taskList;
    private int event_id;

    private static final String EVENT_ID="event_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks_of_event);


        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setAddTaskButton(R.id.imageButtonAddNewTask);
            setAllTasksOfEventRecycler(R.id.recyclerViewAllTasksOfEvent);
            getAllTasksOfEventRecycler().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getAllTasksOfEventRecycler().setLayoutManager(layoutManager);
            getAllTasksOfEventRecycler().addItemDecoration(new DividerItemDecoration(this));
            getAddTaskButton().setOnClickListener(new CustomClickListener());
            DBfunctions.getInstance().updateAllTasks(getApplicationContext(),getAllTasksOfEventRecycler(),getEvent_id());
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }


    //----------------------------------------------------------------------
    //-----------------CUSTOM ONCLICKLISTENER-------------------------------------
    //----------------------------------------------------------------------

    public class CustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.imageButtonAddNewTask){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
                    StartActivityFunctions.getInstance().startCreateTaskActivity(getApplicationContext(),getEvent_id());
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
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
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public int getEvent_id(){
        return this.event_id;
    }
}
