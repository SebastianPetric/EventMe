package thesis.hfu.eventme.activities;

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
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.functions.CheckSharedPreferences;
import thesis.hfu.eventme.functions.StartActivity;
import thesis.hfu.eventme.list_decoration.DividerItemDecoration;
import thesis.hfu.eventme.dialogs.LogoutDialog;

public class SearchActivity extends ActionBarActivity {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerViewSearch;
    private SwipeRefreshLayout syncRefreshSearch;

    private static final String EMPTY_STRING = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setSearchEditText(R.id.editTextSearchField);
            setSearchButton(R.id.buttonSearchButton);
            setSyncRefreshSearch(R.id.swipe_refresh_search);
            getSearchButton().setOnClickListener(new CustomClickListener());
            setRecyclerViewSearch(R.id.recyclerViewSearch);
            getRecyclerViewSearch().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getRecyclerViewSearch().setLayoutManager(layoutManager);
            getRecyclerViewSearch().addItemDecoration(new DividerItemDecoration(this));
            getSyncRefreshSearch().setOnRefreshListener(new CustomSwipeListener());
            DBfunctions.getInstance().searchAllUsers(getApplicationContext(), null, getRecyclerViewSearch(), EMPTY_STRING, CheckSharedPreferences.getInstance().getAdmin_id());
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
            if(v.getId()==R.id.buttonSearchButton){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    DBfunctions.getInstance().searchAllUsers(getApplicationContext(), null, getRecyclerViewSearch(), getSearchEditText(), CheckSharedPreferences.getInstance().getAdmin_id());
                    }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }

    public class CustomSwipeListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            DBfunctions.getInstance().searchAllUsers(getApplicationContext(), getSyncRefreshSearch(), getRecyclerViewSearch(), EMPTY_STRING, CheckSharedPreferences.getInstance().getAdmin_id());
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
            return true;
        }else if(item.getItemId()==R.id.action_friends){
            StartActivity.getInstance().startFriendsListActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==android.R.id.home){
            StartActivity.getInstance().startAllEventsActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_logout){
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
            return true;
        }else if(item.getItemId()==R.id.action_archived_events){
            StartActivity.getInstance().startArchivedEventsActivity(getApplicationContext());
            return true;
        }else if (item.getItemId() == R.id.action_impressum) {
            StartActivity.getInstance().startImpressumActivity(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getSearchEditText() {
        return searchEditText.getText().toString().trim();
    }
    public void setSearchEditText(int res) {
        this.searchEditText = (EditText) findViewById(res);
    }
    public Button getSearchButton() {
        return searchButton;
    }
    public void setSearchButton(int res) {
        this.searchButton = (Button) findViewById(res);
    }
    public void setRecyclerViewSearch(int res) {
        this.recyclerViewSearch = (RecyclerView) findViewById(res);
    }
    public RecyclerView getRecyclerViewSearch(){
        return this.recyclerViewSearch;
    }
    public SwipeRefreshLayout getSyncRefreshSearch() {
        return syncRefreshSearch;
    }
    public void setSyncRefreshSearch(int res) {
        this.syncRefreshSearch = (SwipeRefreshLayout) findViewById(res);
    }
}
