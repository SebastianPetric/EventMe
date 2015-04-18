package thesis.hfu.eventmy.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

public class FriendslistActivity extends ActionBarActivity {

    private RecyclerView FriendsListRecycler;
    private SwipeRefreshLayout syncRefresh;
    private EditText searchField;
    private Button searchButton;

    private String EMPTY_STRING="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setFriendsListRecycler(R.id.recyclerViewFriendsList);
            setSyncRefresh(R.id.swipe_refresh_friendslist);
            setSearchButton(R.id.buttonFriendsListSearchButton);
            setSearchField(R.id.editTextFriendsListSearchField);
            getFriendsListRecycler().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getFriendsListRecycler().setLayoutManager(layoutManager);
            getFriendsListRecycler().addItemDecoration(new DividerItemDecoration(this));
            getSyncRefresh().setOnRefreshListener(new CustomSwipeListener());
            getSearchButton().setOnClickListener(new CustomClickListener());
            DBfunctions.getInstance().getFriendsList(getApplicationContext(),null,getFriendsListRecycler(),EMPTY_STRING,CheckSharedPreferences.getInstance().getAdmin_id());
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
                    DBfunctions.getInstance().getFriendsList(getApplicationContext(),null,getFriendsListRecycler(),getSearchFieldValue(),CheckSharedPreferences.getInstance().getAdmin_id());
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }

    public class CustomSwipeListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            DBfunctions.getInstance().getFriendsList(getApplicationContext(),getSyncRefresh(), getFriendsListRecycler(),EMPTY_STRING,CheckSharedPreferences.getInstance().getAdmin_id());
            getSyncRefresh().setRefreshing(false);
        }
    }

    //----------------------------------------------------------------------
    //-----------------ACTION BAR MENU-------------------------------------
    //----------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friendslist, menu);
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
        }else if(item.getItemId()==R.id.action_friendslist_logout){
            LogoutDialog dialog= new LogoutDialog();
            dialog.show(getFragmentManager(),"logoutDialog");
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //-----------------LOGOUT DIALOG-------------------------------------
    //----------------------------------------------------------------------

    public class LogoutDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_logout_message)
                    .setPositiveButton(R.string.dialog_logout_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                        }
                    })
                    .setNegativeButton(R.string.dialog_logout_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            return builder.create();
        }
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public RecyclerView getFriendsListRecycler() {
        return FriendsListRecycler;
    }
    public void setFriendsListRecycler(int res) {
        this.FriendsListRecycler = (RecyclerView) findViewById(res);
    }
    public SwipeRefreshLayout getSyncRefresh() {
        return syncRefresh;
    }
    public void setSyncRefresh(int res) {
        this.syncRefresh = (SwipeRefreshLayout) findViewById(res);
    }
    public String getSearchFieldValue() {
        return searchField.getText().toString();
    }
    public void setSearchField(int res) {
        this.searchField = (EditText) findViewById(res);
    }
    public Button getSearchButton() {
        return searchButton;
    }
    public void setSearchButton(int res) {
        this.searchButton = (Button) findViewById(res);
    }
}
