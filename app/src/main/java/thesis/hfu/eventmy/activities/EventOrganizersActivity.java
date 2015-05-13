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
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;
import thesis.hfu.eventmy.dialogs.LogoutDialog;

public class EventOrganizersActivity extends ActionBarActivity {

    private Button searchButton;
    private EditText searchEditText;
    private RecyclerView recyclerViewEventOrganizers;
    private SwipeRefreshLayout syncRefreshEventOrganizers;
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
            setSyncRefreshEventOrganizers(R.id.swipe_refresh_event_organizers);
            setSearchEditText(R.id.editTextEventOrganizersSearchField);
            setSearchButton(R.id.buttonEventOrganizersSearchButton);
            setRecyclerViewEventOrganizers(R.id.recyclerEventOrganizers);
            getSearchButton().setOnClickListener(new CustomClickListener());
            getRecyclerViewEventOrganizers().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getRecyclerViewEventOrganizers().setLayoutManager(layoutManager);
            getRecyclerViewEventOrganizers().addItemDecoration(new DividerItemDecoration(this));
            getSyncRefreshEventOrganizers().setOnRefreshListener(new CustomSwipeListener());
            DBfunctions.getInstance().searchFriendsForEvent(getApplicationContext(),null, getRecyclerViewEventOrganizers(),EMPTY_STRING, CheckSharedPreferences.getInstance().getAdmin_id(),getEvent_id());
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
                    DBfunctions.getInstance().searchFriendsForEvent(getApplicationContext(),null, getRecyclerViewEventOrganizers(),getSearchFieldValue(), CheckSharedPreferences.getInstance().getAdmin_id(), getEvent_id());
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
                DBfunctions.getInstance().searchFriendsForEvent(getApplicationContext(), getSyncRefreshEventOrganizers(), getRecyclerViewEventOrganizers(),EMPTY_STRING, CheckSharedPreferences.getInstance().getAdmin_id(),getEvent_id());
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
            StartActivityFunctions.getInstance().startAllTasksActivity(getApplicationContext(), getEvent_id());
            return true;
        }else if(item.getItemId()==R.id.action_logout){
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
            return true;
        }else if(item.getItemId()==R.id.action_archived_events){
            StartActivityFunctions.getInstance().startArchivedEventsActivity(getApplicationContext());
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
        return searchEditText.getText().toString().trim();
    }
    public void setSearchEditText(int res) {
        this.searchEditText = (EditText) findViewById(res);
    }
    public RecyclerView getRecyclerViewEventOrganizers() {
        return recyclerViewEventOrganizers;
    }
    public void setRecyclerViewEventOrganizers(int res) {
        this.recyclerViewEventOrganizers = (RecyclerView) findViewById(res);
    }
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public SwipeRefreshLayout getSyncRefreshEventOrganizers() {
        return syncRefreshEventOrganizers;
    }
    public void setSyncRefreshEventOrganizers(int res) {
        this.syncRefreshEventOrganizers = (SwipeRefreshLayout) findViewById(res);
    }
}
