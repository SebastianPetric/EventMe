package thesis.hfu.eventmy.activities;

import android.os.Bundle;
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
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;


public class SearchActivity extends ActionBarActivity {

    private EditText searchField;
    private Button searchButton;
    private RecyclerView recyclerView;
    private static final String EMPTY_STRING = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setSearchField(R.id.editTextSearchField);
            setSearchButton(R.id.buttonSearchButton);
            getSearchButton().setOnClickListener(new CustomClickListener());
            setRecyclerView(R.id.recyclerViewSearch);
            getRecyclerView().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getRecyclerView().setLayoutManager(layoutManager);
            getRecyclerView().addItemDecoration(new DividerItemDecoration(this));
            DBfunctions.getInstance().searchUser(getApplicationContext(),getRecyclerView(),EMPTY_STRING,CheckSharedPreferences.getInstance().getUser_id());
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
                    DBfunctions.getInstance().searchUser(getApplicationContext(),getRecyclerView(),getSearchField(),CheckSharedPreferences.getInstance().getUser_id());
                    }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId()==R.id.action_search) {
            return true;
        }else if(item.getItemId()==R.id.action_friends){

            return true;
        }return super.onOptionsItemSelected(item);
    }


    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getSearchField() {
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
    public void setRecyclerView(int res) {
        this.recyclerView = (RecyclerView) findViewById(res);
    }
    public RecyclerView getRecyclerView(){
        return this.recyclerView;
    }
}
