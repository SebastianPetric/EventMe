package thesis.hfu.eventmy.activities;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;

import java.sql.Date;
import java.util.Calendar;

public class CreateEventActivity extends ActionBarActivity {

    private EditText eventNameField,eventLocationField;
    private TextView eventDateField;
    private ImageButton addDateButton;
    private Button finishButton;
    private Date eventDate;

    private static final String EMPTY_STRING = "";
    private static final String DATE_PICKER = "datepicker";
    private static final String ERROR_EMPTY_FIELD = "Bitte füllen Sie alle Felder aus!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEventLocationField(R.id.editTextNewEventLocationField);
            setButtonDate(R.id.imageButtonNewEventDate);
            setEditTextName(R.id.editTextNewEventNameField);
            setTextViewDate(R.id.textViewNewEventDateField);
            setFinishButton(R.id.buttonNewEventFinishButton);
            getFinishButton().setOnClickListener(new CustomClickListener());
            getAddDateButton().setOnClickListener(new CustomClickListener());
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }

    public class CustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.buttonNewEventFinishButton){
                if(!getEventNameField().equals(EMPTY_STRING)&&!getEventLocationField().equals(EMPTY_STRING)&&!getEventDateField().equals(EMPTY_STRING)){
                    if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
                        DBfunctions.getInstance().createEvent(getApplicationContext(), getEventNameField(), getEventLocationField(), getEventDate(), CheckSharedPreferences.getInstance().getAdmin_id());
                    }else CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }else{
                    Toast.makeText(getApplicationContext(),ERROR_EMPTY_FIELD,Toast.LENGTH_SHORT).show();
                }
            }else if(v.getId()==R.id.imageButtonNewEventDate){
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), DATE_PICKER);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId()==R.id.action_search) {
            StartActivityFunctions.getInstance().startSearchActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_friends){
            StartActivityFunctions.getInstance().startFriendsListActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==android.R.id.home){
            StartActivityFunctions.getInstance().startAllEventsActivity(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //----------------------------------------------------------------------
    //-----------------DATEPICKER-------------------------------------
    //----------------------------------------------------------------------


    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date=day+"."+(month+1)+"."+ (year);
            setEventDateField(date);
            setEventDate(new Date((year),month,day));
        }
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getEventNameField() {
        return eventNameField.getText().toString();
    }
    public String getEventDateField() {
        return eventDateField.getText().toString();
    }
    public void setEventDateField(String eventDateField) {
        this.eventDateField.setText(eventDateField);
    }
    public ImageButton getAddDateButton() {
        return addDateButton;
    }
    public void setEditTextName(int res){
        this.eventNameField= (EditText) findViewById(res);
    }
    public void setTextViewDate(int res){
        this.eventDateField= (TextView) findViewById(res);
    }
    public void setButtonDate(int res){
        this.addDateButton=  (ImageButton)findViewById(res);
    }
    public Button getFinishButton() {
        return finishButton;
    }
    public void setFinishButton(int res) {
        this.finishButton= (Button) findViewById(res);
    }
    public String getEventLocationField() {
        return eventLocationField.getText().toString();
    }
    public void setEventLocationField(int res) {
        this.eventLocationField = (EditText) findViewById(res);
    }
    public Date getEventDate() {
        return eventDate;
    }
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

}
