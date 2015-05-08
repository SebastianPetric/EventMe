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
import android.widget.ImageView;
import android.widget.TextView;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.dialogs.EditEventDialog;
import thesis.hfu.eventmy.dialogs.LogoutDialog;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;


public class AllTasksOfEventActivity extends ActionBarActivity {

    private ImageButton addOrganizersButton;
    private TextView totalOrganizersTextView, totalCostsTextView, totalPercentageTextView, eventNameTextView, eventDateTextView, eventLocationTextView;
    private RecyclerView allTasksOfEventRecycler;
    private SwipeRefreshLayout syncRefresh;
    private FloatingActionButton createTaskButton;
    private int event_id;

    private final static String ADD_TASK_BUTTON ="add_task_button";
    private static final String EVENT_ID="event_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_of_event);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setFloatingActionButton();
            setEventLocationTextView(R.id.textViewTaskOfEventLocation);
            setTotalOrganizersTextView(R.id.textViewTaskOfEventTotalOrganizers);
            setTotalCostsTextView(R.id.textViewTaskOfEventTotalCosts);
            setTotalPercentageTextView(R.id.textViewTaskOfEventTotalPercentage);
            setEventNameTextView(R.id.textViewTaskOfEventEventName);
            setEventDateTextView(R.id.textViewTaskOfEventDate);
            setAddOrganizersButton(R.id.imageButtonTasksOfEventOrganizers);
            setSyncRefresh(R.id.swipe_refresh_all_tasks);
            setAllTasksOfEventRecycler(R.id.recyclerViewAllTasksOfEvent);
            getAllTasksOfEventRecycler().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getAllTasksOfEventRecycler().setLayoutManager(layoutManager);
            getAllTasksOfEventRecycler().addItemDecoration(new DividerItemDecoration(this));
            getSyncRefresh().setOnRefreshListener(new CustomSwipeListener());
            getAddOrganizersButton().setOnClickListener(new CustomClickListener());
            getFloatingActionButton().setOnClickListener(new FloatingButtonCustomClickListener());
            DBfunctions.getInstance().updateEventDetails(null, CheckSharedPreferences.getInstance().getAdmin_id(), getEvent_id(),getEventTotalOrganizersTextView(), getEventTotalPercentageTextView(), getEventTotalCostsTextView(), getEventNameTextView(), getEventDateTextView(),getEventLocationTextView());
            DBfunctions.getInstance().getAllTasks(this, getSyncRefresh(), getAllTasksOfEventRecycler(), getEvent_id(), getEventTotalOrganizersTextView(), getEventTotalPercentageTextView(), getEventTotalCostsTextView(), getEventNameTextView(), getEventDateTextView(),getEventLocationTextView());
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
            if(v.getId()==R.id.imageButtonTasksOfEventOrganizers){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
                    StartActivityFunctions.getInstance().startEventOrganizersActivity(getApplicationContext(), getEvent_id());
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }
    public class FloatingButtonCustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getTag().equals(ADD_TASK_BUTTON)){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
                    StartActivityFunctions.getInstance().startCreateTaskActivity(getApplicationContext(),getEvent_id());
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }

    public class CustomSwipeListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                DBfunctions.getInstance().updateEventDetails(null, CheckSharedPreferences.getInstance().getAdmin_id(), getEvent_id(), getEventTotalOrganizersTextView(), getEventTotalPercentageTextView(), getEventTotalCostsTextView(), getEventNameTextView(), getEventDateTextView(),getEventLocationTextView());
                DBfunctions.getInstance().getAllTasks(AllTasksOfEventActivity.this, getSyncRefresh(), getAllTasksOfEventRecycler(), getEvent_id(), getEventTotalOrganizersTextView(), getEventTotalPercentageTextView(), getEventTotalCostsTextView(), getEventNameTextView(), getEventDateTextView(),getEventLocationTextView());
            }else{
                CheckSharedPreferences.getInstance().endSession(getApplicationContext());
            }
        }
    }

    //----------------------------------------------------------------------
    //-----------------ACTION BAR MENU-------------------------------------
    //----------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_all_tasks, menu);
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
            return true;
        }else if(item.getItemId()==R.id.action_archived_events){
            StartActivityFunctions.getInstance().startArchivedEventsActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_comment){
            if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
                StartActivityFunctions.getInstance().startCommentOnEventActivity(getApplicationContext(),getEvent_id());
            }else{
                CheckSharedPreferences.getInstance().endSession(getApplicationContext());
            }
            return true;
        }else if(item.getItemId()==R.id.action_edit){
            EditEventDialog.getInstance().startEditEventDialog(getFragmentManager(),getApplicationContext(),getEventDateTextView(),getEventNameTextView(),getEventDateTextView(),getEventLocationTextView(),getEvent_id());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

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
    public void setTotalOrganizersTextView(int res) {
        this.totalOrganizersTextView = (TextView) findViewById(res);
    }
    public void setTotalCostsTextView(int res) {
        this.totalCostsTextView = (TextView) findViewById(res);
    }
    public void setTotalPercentageTextView(int res) {
        this.totalPercentageTextView = (TextView) findViewById(res);
    }
    public void setEventNameTextView(int res) {
        this.eventNameTextView = (TextView) findViewById(res);
    }
    public void setEventDateTextView(int res) {
        this.eventDateTextView = (TextView) findViewById(res);
    }
    public TextView getEventNameTextView(){
        return this.eventNameTextView;
    }
    public TextView getEventDateTextView(){
        return this.eventDateTextView;
    }
    public TextView getEventTotalOrganizersTextView(){
        return this.totalOrganizersTextView;
    }
    public TextView getEventTotalCostsTextView(){
        return this.totalCostsTextView;
    }
    public TextView getEventTotalPercentageTextView(){
        return this.totalPercentageTextView;
    }
    public void setFloatingActionButton(){
        ImageView icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.add_button));

        this.createTaskButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.add_button_shape)
                .build();
        this.createTaskButton.setTag(ADD_TASK_BUTTON);
    }
    public FloatingActionButton getFloatingActionButton(){
        return this.createTaskButton;
    }
    public TextView getEventLocationTextView() {
        return eventLocationTextView;
    }
    public void setEventLocationTextView(int res) {
        this.eventLocationTextView = (TextView) findViewById(res);
    }

}
