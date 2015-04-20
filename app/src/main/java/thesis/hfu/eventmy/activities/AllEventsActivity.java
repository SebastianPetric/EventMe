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
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.dialogs.LogoutDialog;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;

public class AllEventsActivity extends ActionBarActivity{

    private ImageButton addEventButton;
    private RecyclerView allEventsRecycler;
    private SwipeRefreshLayout syncRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);


        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setAddEvent(R.id.imageButtonAddNewEvent);
            setSyncRefresh(R.id.swipe_refresh_all_events);
            setAllEventsRecycler(R.id.recyclerViewAllEvents);
            getAllEventsRecycler().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getAllEventsRecycler().setLayoutManager(layoutManager);
            getAllEventsRecycler().addItemDecoration(new DividerItemDecoration(this));
            getAddEventButton().setOnClickListener(new CustomClickListener());
            getSyncRefresh().setOnRefreshListener(new CustomSwipeListener());
            DBfunctions.getInstance().getAllEvents(getApplicationContext(), null, getAllEventsRecycler(), CheckSharedPreferences.getInstance().getAdmin_id());
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
            if(v.getId()==R.id.imageButtonAddNewEvent){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    StartActivityFunctions.getInstance().startCreateEventActivity(getApplicationContext());
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
                DBfunctions.getInstance().getAllEvents(getApplicationContext(), getSyncRefresh(), getAllEventsRecycler(), CheckSharedPreferences.getInstance().getAdmin_id());
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
                LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
                return true;
        }else if(item.getItemId()==R.id.action_logout){
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
            return true;
        }else if(item.getItemId()==R.id.action_events){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public ImageButton getAddEventButton() {
        return addEventButton;
    }
    public void setAddEvent(int res) {
        this.addEventButton= (ImageButton) findViewById(res);
    }
    public RecyclerView getAllEventsRecycler() {
        return allEventsRecycler;
    }
    public void setAllEventsRecycler(int res) {
        this.allEventsRecycler = (RecyclerView) findViewById(res);
    }
    public SwipeRefreshLayout getSyncRefresh() {
        return syncRefresh;
    }
    public void setSyncRefresh(int res) {
        this.syncRefresh = (SwipeRefreshLayout) findViewById(res);
    }
}
