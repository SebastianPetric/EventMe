package thesis.hfu.eventmy.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBconnection;
import thesis.hfu.eventmy.functions.BuildJSON;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;
import thesis.hfu.eventmy.objects.AllEventsListAdapter;
import thesis.hfu.eventmy.objects.Event;


public class AllEvents extends Activity{

    private ImageButton addEventButton;
    private RecyclerView allEventsRecycler;
    private ArrayList<Event> eventList;
    private RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> recAdapter;

    private static final String STATUS="status";
    private static final String MESSAGE="message";
    private static final String EVENTS="events";

    private static final String URL_GET_ALL_EVENTS= "get_all_events.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setAddEvent(R.id.imageButtonAddNewEvent);
            updateAllEvents(CheckSharedPreferences.getInstance().getUser_id());
            setAllEventsRecycler(R.id.recyclerViewAllEvents);
            allEventsRecycler.setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            allEventsRecycler.setLayoutManager(layoutManager);
            allEventsRecycler.addItemDecoration(new DividerItemDecoration(this));
            getAddEventButton().setOnClickListener(new AddEventCLickListener());
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }



    public class AddEventCLickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.imageButtonAddNewEvent){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    createEvent();
                }
            }
        }
    }

    //----------------------------------------------------------------------
    //-----------------Functions-------------------------------------
    //----------------------------------------------------------------------

    public void updateAllEvents(String user_id){
        RequestParams params= BuildJSON.getInstance().updateAllEventsJSON(user_id);
        DBconnection.post(URL_GET_ALL_EVENTS,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200) {
                        setEventList(BuildJSON.getInstance().getAllEventsJSON(response.getJSONArray(EVENTS)));
                        recAdapter = new AllEventsListAdapter(getApplicationContext(),getEventList());
                        allEventsRecycler.setAdapter(recAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    public void createEvent(){
        Intent intent= new Intent(getApplicationContext(),CreateEventActivity.class);
        startActivity(intent);
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
    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }
}
