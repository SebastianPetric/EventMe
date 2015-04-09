package thesis.hfu.eventmy.activities;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBconnection;
import thesis.hfu.eventmy.functions.BuildJSON;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import java.sql.Date;
import java.util.Calendar;

public class CreateEventActivity extends Activity {

    private EditText eventNameField,eventLocationField;
    private TextView eventDateField;
    private ImageButton addDateButton;
    private Button finishButton;
    private Date eventDate;
    private static final String MESSAGE= "message";
    private static final String EMPTY_STRING = "";
    private static final String DEFAULT_DATE = "";
    private static final String DATE_PICKER = "datepicker";
    private static final String ERROR_EMPTY_FIELD = "Bitte füllen Sie alle Felder aus!";

    private static final String URL_CREATE_EVENT= "create_event.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEventLocationField(R.id.editTextNewEventLocationField);
            setButtonDate(R.id.imageButtonNewEventDate);
            setEditTextName(R.id.editTextNewEventNameField);
            setTextViewDate(R.id.textViewNewEventDateField);
            setFinishButton(R.id.buttonNewEventFinishButton);
            getFinishButton().setOnClickListener(new NewEventFinishButton());
            getAddDateButton().setOnClickListener(new NewEventFinishButton());
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }


    public class NewEventFinishButton implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.buttonNewEventFinishButton){
                if(!getEventNameField().equals(EMPTY_STRING)&&!getEventLocationField().equals(EMPTY_STRING)&&!getEventDateField().equals(DEFAULT_DATE)){
                    if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
                        createEvent();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),ERROR_EMPTY_FIELD,Toast.LENGTH_SHORT).show();
                }
            }else if(v.getId()==R.id.imageButtonNewEventDate){
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), DATE_PICKER);
            }
        }
    }

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

    public void createEvent(){

        RequestParams params = BuildJSON.getInstance().createEventJSON(getEventNameField(),getEventLocationField(), getEventDate(),CheckSharedPreferences.getInstance().getUser_id());
        DBconnection.post(URL_CREATE_EVENT,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(),response.getString(MESSAGE).toString(),Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),AllEventsActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText(getApplicationContext(),errorResponse.getString(MESSAGE).toString(),Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),AllEventsActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
