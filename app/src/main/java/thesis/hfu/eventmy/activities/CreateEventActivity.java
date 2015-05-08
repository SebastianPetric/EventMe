package thesis.hfu.eventmy.activities;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.dialogs.DatePickerDialog;
import thesis.hfu.eventmy.dialogs.LogoutDialog;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.objects.Global;

import java.sql.Date;

public class CreateEventActivity extends ActionBarActivity {

    private EditText eventNameEditText, eventLocationEditText;
    private TextView eventDateTextView;
    private ImageButton addDateButton;
    private Date eventDate;
    private FloatingActionButton createEventButton;

    private static final String EMPTY_STRING = "";
    private static final String DATE_PICKER = "datepicker";
    private static final String ERROR_EMPTY_FIELD = "Bitte füllen Sie alle Felder aus!";
    private final static String CREATE_EVENT_BUTTON = "create_event_button";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setCreateEventBttuon();
            setEventLocationEditText(R.id.editTextNewEventLocationField);
            setButtonDate(R.id.imageButtonNewEventDate);
            setEditTextName(R.id.editTextNewEventNameField);
            setTextViewDate(R.id.textViewNewEventDateField);
            getCreateEventButton().setOnClickListener(new FloatingButtonCustomClickListener());
            getAddDateButton().setOnClickListener(new CustomClickListener());
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
           if(v.getId()==R.id.imageButtonNewEventDate){
               DatePickerDialog.getInstance().startDatePickerDialog(getFragmentManager(),getApplicationContext(),getEventDateTextView());
            }
        }
    }

    public class FloatingButtonCustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getTag().equals(CREATE_EVENT_BUTTON)){
                if(!getEventNameEditText().equals(EMPTY_STRING)&&!getEventLocationEditText().equals(EMPTY_STRING)&&!getEventDateField().equals(EMPTY_STRING)){
                    if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
                        Global global= (Global) getApplicationContext();
                        setEventDate(global.getDate());

                        DBfunctions.getInstance().createEvent(getApplicationContext(), getEventNameEditText(), getEventLocationEditText(), getEventDate(), CheckSharedPreferences.getInstance().getAdmin_id());
                    }else CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }else{
                    Toast.makeText(getApplicationContext(), ERROR_EMPTY_FIELD, Toast.LENGTH_SHORT).show();
                }
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
        if (item.getItemId()==R.id.action_search) {
            StartActivityFunctions.getInstance().startSearchActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_friends){
            StartActivityFunctions.getInstance().startFriendsListActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==android.R.id.home){
            StartActivityFunctions.getInstance().startAllEventsActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_logout){
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
            return true;
        }else if(item.getItemId()==R.id.action_archived_events){
            StartActivityFunctions.getInstance().startArchivedEventsActivity(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getEventNameEditText() {
        return eventNameEditText.getText().toString().trim();
    }
    public String getEventDateField() {
        return eventDateTextView.getText().toString();
    }
    public TextView getEventDateTextView() {
        return eventDateTextView;
    }
    public ImageButton getAddDateButton() {
        return addDateButton;
    }
    public void setEditTextName(int res){
        this.eventNameEditText = (EditText) findViewById(res);
    }
    public void setTextViewDate(int res){
        this.eventDateTextView = (TextView) findViewById(res);
    }
    public void setButtonDate(int res){
        this.addDateButton=  (ImageButton)findViewById(res);
    }
    public String getEventLocationEditText() {
        return eventLocationEditText.getText().toString().trim();
    }
    public void setEventLocationEditText(int res) {
        this.eventLocationEditText = (EditText) findViewById(res);
    }
    public Date getEventDate() {
        return eventDate;
    }
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
    public void setCreateEventBttuon(){
        ImageView icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.checkedicon));

        this.createEventButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.icons_shape_blue)
                .build();
        createEventButton.setTag(CREATE_EVENT_BUTTON);
    }
    public FloatingActionButton getCreateEventButton(){
        return this.createEventButton;
    }
}
