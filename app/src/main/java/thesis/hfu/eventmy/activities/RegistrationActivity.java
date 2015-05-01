package thesis.hfu.eventmy.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckIf;
import thesis.hfu.eventmy.functions.StartActivityFunctions;

public class RegistrationActivity extends ActionBarActivity {

    private EditText nameField,prenameField,emailField,passwordField,passwordRepeatField;
    private Button finishRegistrationButton,cancelButton;

    private static final String EMPTY_STRING = "";
    private static final String ERROR_PASSWORD = "Passwort falsch wiederholt!";
    private static final String ERROR_EMAIL = "Email Adresse nicht korrekt!";
    private static final String ERROR_EMPTY_FIELDS = "Bitte alle Felder ausfüllen!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setNameField(R.id.registrationNameField);
        setPrenameField(R.id.registrationPrenameField);
        setEmailField(R.id.registrationEmailField);
        setPasswordField(R.id.registrationPasswordField);
        setPasswordRepeatField(R.id.registrationPasswordRepeatField);
        setFinishRegistrationButton(R.id.registrationButton);
        setCancelButton(R.id.registrationCancelButton);
        getFinishRegistrationButton().setOnClickListener(new CustomClickListener());
        getCancelButton().setOnClickListener(new CustomClickListener());
    }

    //----------------------------------------------------------------------
    //-----------------CUSTOM ONCLICKLISTENER-------------------------------------
    //----------------------------------------------------------------------

    public class CustomClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.registrationButton) {
                if (!getNameField().equals(EMPTY_STRING) && !getPrenameField().equals(EMPTY_STRING) && !getEmailField().equals(EMPTY_STRING)
                        && !getPasswordField().equals(EMPTY_STRING) && !getPasswordRepeatField().equals(EMPTY_STRING)) {
                    if (CheckIf.EmailIsValid(getEmailField())) {
                        if (getPasswordField().equals(getPasswordRepeatField())) {
                            DBfunctions.getInstance().registration(getApplicationContext(), getNameField(), getPrenameField(), getEmailField(), getPasswordField());
                        } else {
                            Toast.makeText(getApplicationContext(), ERROR_PASSWORD, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), ERROR_EMAIL, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), ERROR_EMPTY_FIELDS, Toast.LENGTH_SHORT).show();
                }
            }else if(v.getId()==R.id.registrationCancelButton){
                StartActivityFunctions.getInstance().startLoginActivity(getApplicationContext());
            }
        }
    }

    //----------------------------------------------------------------------
    //-----------------ACTION BAR MENU-------------------------------------
    //----------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            StartActivityFunctions.getInstance().startLoginActivity(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getNameField() {
        return nameField.getText().toString().trim();
    }
    public void setNameField(int res) {
        this.nameField= (EditText) findViewById(res);
    }
    public String getPrenameField() {
        return prenameField.getText().toString().trim();
    }
    public void setPrenameField(int res) {
        this.prenameField = (EditText) findViewById(res);
    }
    public String getEmailField() {
        return emailField.getText().toString().trim();
    }
    public void setEmailField(int res) {
        this.emailField = (EditText) findViewById(res);
    }
    public String getPasswordField() {
        return passwordField.getText().toString().trim();
    }
    public void setPasswordField(int res) {
        this.passwordField = (EditText) findViewById(res);
    }
    public String getPasswordRepeatField() {
        return passwordRepeatField.getText().toString().trim();
    }
    public void setPasswordRepeatField(int res) {
        this.passwordRepeatField = (EditText) findViewById(res);
    }
    public Button getFinishRegistrationButton() {
        return finishRegistrationButton;
    }
    public void setFinishRegistrationButton(int res) {
        this.finishRegistrationButton = (Button) findViewById(res);
    }
    public void setCancelButton(int res){
        this.cancelButton= (Button) findViewById(res);
    }
    public Button getCancelButton(){
        return this.cancelButton;
    }
}
