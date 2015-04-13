package thesis.hfu.eventmy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;

public class AllEventsActivity extends Activity{

    private ImageButton addEventButton;
    private RecyclerView allEventsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setAddEvent(R.id.imageButtonAddNewEvent);
            setAllEventsRecycler(R.id.recyclerViewAllEvents);
            getAllEventsRecycler().setHasFixedSize(true);
            LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            getAllEventsRecycler().setLayoutManager(layoutManager);
            getAllEventsRecycler().addItemDecoration(new DividerItemDecoration(this));
            getAddEventButton().setOnClickListener(new CustomClickListener());
            DBfunctions.getInstance().updateAllEvents(getApplicationContext(), getAllEventsRecycler(), CheckSharedPreferences.getInstance().getUser_id());
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
            if(v.getId()==R.id.imageButtonAddNewEvent){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    StartActivityFunctions.getInstance().startCreateEventActivity(getApplicationContext());
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
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
}
