package thesis.hfu.eventmy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;

public class EventOrganizersActivity extends Activity {

    private Button searchButton;
    private EditText searchField;
    private RecyclerView recyclerView;
    private int event_id;

    private static final String EVENT_ID="event_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_organizers);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setSearchField(R.id.editTextEventOrganizersSearchField);
            setSearchButton(R.id.buttonEventOrganizersSearchButton);
            setRecyclerView(R.id.recyclerEventOrganizers);
            getSearchButton().setOnClickListener(new CustomClickListener());
            getRecyclerView().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getRecyclerView().setLayoutManager(layoutManager);
            getRecyclerView().addItemDecoration(new DividerItemDecoration(this));
            DBfunctions.getInstance().searchFriendsForEvent(getApplicationContext(), getRecyclerView(), CheckSharedPreferences.getInstance().getUser_id(),getEvent_id());
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
            if(v.getId()==R.id.buttonEventOrganizersSearchButton){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    DBfunctions.getInstance().searchFriendsForEvent(getApplicationContext(), getRecyclerView(), CheckSharedPreferences.getInstance().getUser_id(), getEvent_id());
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
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
        return searchField.getText().toString();
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
}
