package thesis.hfu.eventmy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import thesis.hfu.eventmy.objects.SearchListAdapter;
import thesis.hfu.eventmy.objects.User;

import java.util.ArrayList;


public class SearchActivity extends Activity {

    private EditText searchField;
    private Button searchButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<SearchListAdapter.MyViewHolder> recAdapter;

    private ArrayList<User> userList;

    private static final String STATUS="status";
    private static final String MESSAGE="message";
    private static final String USERS="users";
    private static final String EMPTY_STRING = "";

    private static final String URL_SEARCH_USER= "search_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setSearchField(R.id.editTextSearchField);
            setSearchButton(R.id.buttonSearchButton);
            getSearchButton().setOnClickListener(new CustomClickListener());
            setRecyclerView(R.id.recyclerViewSearch);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            searchUser(EMPTY_STRING,CheckSharedPreferences.getInstance().getUser_id());
            recyclerView.addItemDecoration(new DividerItemDecoration(this));
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }

    public class CustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.buttonSearchButton){

                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    searchUser(getSearchField(),CheckSharedPreferences.getInstance().getUser_id());
                    }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }

    //----------------------------------------------------------------------
    //-----------------Functions-------------------------------------
    //----------------------------------------------------------------------

    public void searchUser(String search,String user_id){

        RequestParams params= BuildJSON.getInstance().searchUserJSON(search,user_id);
        DBconnection.post(URL_SEARCH_USER,params,new JsonHttpResponseHandler(){

             @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Toast.makeText(getApplicationContext(),response.getString(MESSAGE),Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200) {
                        setUserList(BuildJSON.getInstance().getAllUsersJSON(response.getJSONArray(USERS)));
                        recAdapter = new SearchListAdapter(getApplicationContext(),getUserList());
                        recyclerView.setAdapter(recAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText(getApplicationContext(),errorResponse.getString(MESSAGE),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
    public ArrayList<User> getUserList() {
        return userList;
    }
    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}
