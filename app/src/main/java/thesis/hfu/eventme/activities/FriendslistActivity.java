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

public class FriendslistActivity extends ActionBarActivity {

    private RecyclerView recyclerViewFriendslist;
    private SwipeRefreshLayout syncRefreshFriendslist;
    private EditText searchEditText;
    private Button searchButton;

    private String EMPTY_STRING="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setRecyclerViewFriendslist(R.id.recyclerViewFriendsList);
            setSyncRefreshFriendslist(R.id.swipe_refresh_friendslist);
            setSearchButton(R.id.buttonFriendsListSearchButton);
            setSearchEditText(R.id.editTextFriendsListSearchField);
            getRecyclerViewFriendslist().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getRecyclerViewFriendslist().setLayoutManager(layoutManager);
            getRecyclerViewFriendslist().addItemDecoration(new DividerItemDecoration(this));
            getSyncRefreshFriendslist().setOnRefreshListener(new CustomSwipeListener());
            getSearchButton().setOnClickListener(new CustomClickListener());
            DBfunctions.getInstance().searchFriendsList(getApplicationContext(), null, getRecyclerViewFriendslist(), EMPTY_STRING, CheckSharedPreferences.getInstance().getAdmin_id());
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
            if(v.getId()==R.id.buttonFriendsListSearchButton){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    DBfunctions.getInstance().searchFriendsList(getApplicationContext(), null, getRecyclerViewFriendslist(), getSearchFieldValue(), CheckSharedPreferences.getInstance().getAdmin_id());
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
                DBfunctions.getInstance().searchFriendsList(getApplicationContext(), getSyncRefreshFriendslist(), getRecyclerViewFriendslist(), EMPTY_STRING, CheckSharedPreferences.getInstance().getAdmin_id());
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
    public void onBackPressed() {
        StartActivity.getInstance().startAllEventsActivity(getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_search) {
            StartActivity.getInstance().startSearchActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_friends){
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

    public RecyclerView getRecyclerViewFriendslist() {
        return recyclerViewFriendslist;
    }
    public void setRecyclerViewFriendslist(int res) {
        this.recyclerViewFriendslist = (RecyclerView) findViewById(res);
    }
    public SwipeRefreshLayout getSyncRefreshFriendslist() {
        return syncRefreshFriendslist;
    }
    public void setSyncRefreshFriendslist(int res) {
        this.syncRefreshFriendslist = (SwipeRefreshLayout) findViewById(res);
    }
    public String getSearchFieldValue() {
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
}
