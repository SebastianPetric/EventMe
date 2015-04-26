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
import android.widget.Button;
import android.widget.EditText;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.dialogs.LogoutDialog;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;


public class TaskOrganizersActivity extends ActionBarActivity {

    private Button searchButton;
    private EditText searchField;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout syncRefresh;
    private int event_id;

    private static final String EVENT_ID="event_id";
    private static final String EMPTY_STRING="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_task_organizers);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setSyncRefresh(R.id.swipe_refresh_event_organizers);
            setSearchField(R.id.editTextEventOrganizersSearchField);
            setSearchButton(R.id.buttonEventOrganizersSearchButton);
            setRecyclerView(R.id.recyclerEventOrganizers);
            getSearchButton().setOnClickListener(new CustomClickListener());
            getRecyclerView().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getRecyclerView().setLayoutManager(layoutManager);
            getRecyclerView().addItemDecoration(new DividerItemDecoration(this));
            getSyncRefresh().setOnRefreshListener(new CustomSwipeListener());

            DBfunctions.getInstance().searchFriendsForTask(this,getApplicationContext(), null, getRecyclerView(), EMPTY_STRING, CheckSharedPreferences.getInstance().getAdmin_id(),getEvent_id());
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
            if(v.getId()==R.id.buttonEventOrganizersSearchButton){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    DBfunctions.getInstance().searchFriendsForTask(TaskOrganizersActivity.this,getApplicationContext(), null, getRecyclerView(), getSearchFieldValue(), CheckSharedPreferences.getInstance().getAdmin_id(),getEvent_id());
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
                DBfunctions.getInstance().searchFriendsForTask(TaskOrganizersActivity.this,getApplicationContext(), null, getRecyclerView(), EMPTY_STRING, CheckSharedPreferences.getInstance().getAdmin_id(),getEvent_id());
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
            this.finish();
            return true;
        }else if(item.getItemId()==R.id.action_logout){
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
            return true;
        }else if(item.getItemId()==R.id.action_events){
            StartActivityFunctions.getInstance().startAllEventsActivity(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public Button getSearchButton() {
        return searchButton;
    }
    public void setSearchButton(int res) {
        this.searchButton = (Button) findViewById(res);
    }
    public String getSearchFieldValue() {
        return searchField.getText().toString().trim();
    }
    public void setSearchField(int res) {
        this.searchField = (EditText) findViewById(res);
    }
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public void setRecyclerView(int res) {
        this.recyclerView = (RecyclerView) findViewById(res);
    }
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public SwipeRefreshLayout getSyncRefresh() {
        return syncRefresh;
    }
    public void setSyncRefresh(int res) {
        this.syncRefresh = (SwipeRefreshLayout) findViewById(res);
    }
}