package thesis.hfu.eventmy.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.dialogs.LogoutDialog;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;


public class AllTasksOfEventActivity extends ActionBarActivity {

    private ImageButton addTaskButton, addOrganizersButton;
    private TextView totalOrganizers,totalCosts,totalPercentage,eventName,eventDate;
    private RecyclerView allTasksOfEventRecycler;
    private SwipeRefreshLayout syncRefresh;
    private int event_id;
    private String eventNameValue,eventDateValue,totalOrganizersValue,totalCostsValue,totalPercentageValue;

    private static final String EVENT_ID="event_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_of_event);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));

            setAddTaskButton(R.id.imageButtonAddNewTask);
            setTotalOrganizers(R.id.textViewTaskOfEventTotalOrganizers);
            setTotalCosts(R.id.textViewTaskOfEventTotalCosts);
            setTotalPercentage(R.id.textViewTaskOfEventTotalPercentage);
            setEventName(R.id.textViewTaskOfEventEventName);
            setEventDate(R.id.textViewTaskOfEventDate);
            setAddOrganizersButton(R.id.imageButtonTasksOfEventOrganizers);
            setSyncRefresh(R.id.swipe_refresh_all_tasks);
            setAllTasksOfEventRecycler(R.id.recyclerViewAllTasksOfEvent);
            getAllTasksOfEventRecycler().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getAllTasksOfEventRecycler().setLayoutManager(layoutManager);
            getAllTasksOfEventRecycler().addItemDecoration(new DividerItemDecoration(this));
            getAddTaskButton().setOnClickListener(new CustomClickListener());
            getSyncRefresh().setOnRefreshListener(new CustomSwipeListener());
            getAddOrganizersButton().setOnClickListener(new CustomClickListener());
            DBfunctions.getInstance().updateEventDetails(null, CheckSharedPreferences.getInstance().getAdmin_id(), getEvent_id(), getEventTotalOrganizersTextView(), getEventTotalPercentageTextView(), getEventTotalCostsTextView(), getEventNameTextView(), getEventDateTextView());
            DBfunctions.getInstance().updateAllTasks(getApplicationContext(),null,getAllTasksOfEventRecycler(),getEvent_id(),getEventTotalOrganizersTextView(), getEventTotalPercentageTextView(), getEventTotalCostsTextView(),getEventNameTextView(),getEventDateTextView());
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }

    //----------------------------------------------------------------------
    //-----------------CUSTOM LISTENER-------------------------------------
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
            }else if(v.getId()==R.id.imageButtonTasksOfEventOrganizers){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
                    StartActivityFunctions.getInstance().startEventOrganizersActivity(getApplicationContext(), getEvent_id());
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }

    public class CustomSwipeListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            DBfunctions.getInstance().updateEventDetails(null, CheckSharedPreferences.getInstance().getAdmin_id(), getEvent_id(),getEventTotalOrganizersTextView(), getEventTotalPercentageTextView(), getEventTotalCostsTextView(),getEventNameTextView(),getEventDateTextView());
            DBfunctions.getInstance().updateAllTasks(getApplicationContext(),getSyncRefresh(), getAllTasksOfEventRecycler(), getEvent_id(),getEventTotalOrganizersTextView(), getEventTotalPercentageTextView(), getEventTotalCostsTextView(),getEventNameTextView(),getEventDateTextView());
        }
    }

    //----------------------------------------------------------------------
    //-----------------ACTION BAR MENU-------------------------------------
    //----------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_search) {
            StartActivityFunctions.getInstance().startSearchActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_friends){
            StartActivityFunctions.getInstance().startFriendsListActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==android.R.id.home){
            StartActivityFunctions.getInstance().startAllEventsActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_logout){
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
        }
        return super.onOptionsItemSelected(item);
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
    public SwipeRefreshLayout getSyncRefresh() {
        return syncRefresh;
    }
    public void setSyncRefresh(int res) {
        this.syncRefresh = (SwipeRefreshLayout) findViewById(res);
    }
    public ImageButton getAddOrganizersButton() {
        return addOrganizersButton;
    }

    public void setAddOrganizersButton(int res) {
        this.addOrganizersButton = (ImageButton) findViewById(res);
    }
    public void setTotalOrganizers(int res) {
        this.totalOrganizers = (TextView) findViewById(res);
    }
    public void setTotalCosts(int res) {
        this.totalCosts = (TextView) findViewById(res);
    }
    public void setTotalPercentage(int res) {
        this.totalPercentage = (TextView) findViewById(res);
    }
    public void setEventName(int res) {
        this.eventName = (TextView) findViewById(res);
    }
    public void setEventDate(int res) {
        this.eventDate = (TextView) findViewById(res);
    }
    public TextView getEventNameTextView(){
        return this.eventName;
    }
    public TextView getEventDateTextView(){
        return this.eventDate;
    }
    public TextView getEventTotalOrganizersTextView(){
        return this.totalOrganizers;
    }
    public TextView getEventTotalCostsTextView(){
        return this.totalCosts;
    }
    public TextView getEventTotalPercentageTextView(){
        return this.totalPercentage;
    }


}
