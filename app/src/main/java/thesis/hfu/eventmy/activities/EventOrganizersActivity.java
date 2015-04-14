package thesis.hfu.eventmy.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBconnection;
import thesis.hfu.eventmy.functions.BuildJSON;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;
import thesis.hfu.eventmy.objects.EventOrganizersListAdapter;
import thesis.hfu.eventmy.objects.User;

import java.util.ArrayList;

public class EventOrganizersActivity extends Activity {

    private Button searchButton;
    private EditText searchField;
    private RecyclerView recyclerView;

    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private static final String USERS = "users";

    //Search EventOrganizer
    private static final String URL_SEARCH_FRIENDS_EVENT = "search_friends_for_event.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_organizers);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setSearchField(R.id.editTextEventOrganizersSearchField);
            setSearchButton(R.id.buttonEventOrganizersSearchButton);
            setRecyclerView(R.id.recyclerEventOrganizers);
            getSearchButton().setOnClickListener(new CustomClickListener());
            getRecyclerView().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getRecyclerView().setLayoutManager(layoutManager);
            getRecyclerView().addItemDecoration(new DividerItemDecoration(this));
            searchFriendsForEvent(getApplicationContext(), getRecyclerView(), CheckSharedPreferences.getInstance().getUser_id(),6);
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }

    public class CustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.buttonEventOrganizersSearchButton){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    searchFriendsForEvent(getApplicationContext(),getRecyclerView(),CheckSharedPreferences.getInstance().getUser_id(),6);
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }


    public void searchFriendsForEvent(final Context context, final RecyclerView recyclerView, String user_id, int event_id) {

        RequestParams params = BuildJSON.getInstance().searchFriendsEventJSON(user_id,event_id);
        DBconnection.post(URL_SEARCH_FRIENDS_EVENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        ArrayList<User> userList = BuildJSON.getInstance().getAllUsersJSON(response.getJSONArray(USERS));
                        RecyclerView.Adapter<EventOrganizersListAdapter.MyViewHolder> recAdapter = new EventOrganizersListAdapter(context.getApplicationContext(), userList);
                        recyclerView.setAdapter(recAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("schlecht",responseString);
                Log.d("schlecht",throwable.toString());
            }
        });
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
}
