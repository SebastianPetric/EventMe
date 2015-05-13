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
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.dialogs.LogoutDialog;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;

public class ArchivedEventsActivity extends ActionBarActivity {

    private RecyclerView archievedEventsRecycler;
    private SwipeRefreshLayout syncRefreshArchivedEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_events);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
            setArchievedEventsRecycler(R.id.recyclerViewArchivedEvents);
            setSyncRefreshArchivedEvents(R.id.swipe_refresh_archived_events);
            getArchievedEventsRecycler().setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            getArchievedEventsRecycler().setLayoutManager(layoutManager);
            getSyncRefreshArchivedEvents().setOnRefreshListener(new CustomSwipeListener());
            getArchievedEventsRecycler().addItemDecoration(new DividerItemDecoration(this));
            DBfunctions.getInstance().getAllArchivEvents(ArchivedEventsActivity.this, null, getArchievedEventsRecycler(), CheckSharedPreferences.getInstance().getAdmin_id());
        } else {
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }

    //----------------------------------------------------------------------
    //-----------------CUSTOM LISTENER-------------------------------------
    //----------------------------------------------------------------------


    public class CustomSwipeListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            if (CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
                DBfunctions.getInstance().getAllArchivEvents(ArchivedEventsActivity.this, getSyncRefreshArchivedEvents(), getArchievedEventsRecycler(), CheckSharedPreferences.getInstance().getAdmin_id());
            } else {
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
        if (item.getItemId() == R.id.action_search) {
            StartActivityFunctions.getInstance().startSearchActivity(getApplicationContext());
            return true;
        } else if (item.getItemId() == R.id.action_friends) {
            StartActivityFunctions.getInstance().startFriendsListActivity(getApplicationContext());
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            StartActivityFunctions.getInstance().startAllEventsActivity(getApplicationContext());
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
            return true;
        } else if (item.getItemId() == R.id.action_archived_events) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public RecyclerView getArchievedEventsRecycler() {
        return archievedEventsRecycler;
    }
    public void setArchievedEventsRecycler(int res) {
        this.archievedEventsRecycler = (RecyclerView) findViewById(res);
    }
    public SwipeRefreshLayout getSyncRefreshArchivedEvents() {
        return syncRefreshArchivedEvents;
    }
    public void setSyncRefreshArchivedEvents(int res) {
        this.syncRefreshArchivedEvents = (SwipeRefreshLayout) findViewById(res);
    }
}
