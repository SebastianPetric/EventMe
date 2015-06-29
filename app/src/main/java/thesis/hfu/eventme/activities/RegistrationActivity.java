package thesis.hfu.eventme.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.functions.CheckIf;
import thesis.hfu.eventme.functions.StartActivity;

public class RegistrationActivity extends ActionBarActivity {

    private EditText nameEditText, prenameEditText, emailEditText, passwordEditText, passwordRepeatEditText;
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

        setNameEditText(R.id.registrationNameField);
        setPrenameEditText(R.id.registrationPrenameField);
        setEmailEditText(R.id.registrationEmailField);
        setPasswordEditText(R.id.registrationPasswordField);
        setPasswordRepeatEditText(R.id.registrationPasswordRepeatField);
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
                if (!getNameEditText().equals(EMPTY_STRING) && !getPrenameEditText().equals(EMPTY_STRING) && !getEmailEditText().equals(EMPTY_STRING)
                        && !getPasswordEditText().equals(EMPTY_STRING) && !getPasswordRepeatEditText().equals(EMPTY_STRING)) {
                    if (CheckIf.emailIsValid(getEmailEditText())) {
                        if (getPasswordEditText().equals(getPasswordRepeatEditText())) {
                            DBfunctions.getInstance().registration(getApplicationContext(), getNameEditText(), getPrenameEditText(), getEmailEditText(), getPasswordEditText());
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
                StartActivity.getInstance().startLoginActivity(getApplicationContext());
            }
        }
    }

    //----------------------------------------------------------------------
    //-----------------ACTION BAR MENU-------------------------------------
    //----------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            StartActivity.getInstance().startLoginActivity(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getNameEditText() {
        return nameEditText.getText().toString().trim();
    }
    public void setNameEditText(int res) {
        this.nameEditText = (EditText) findViewById(res);
    }
    public String getPrenameEditText() {
        return prenameEditText.getText().toString().trim();
    }
    public void setPrenameEditText(int res) {
        this.prenameEditText = (EditText) findViewById(res);
    }
    public String getEmailEditText() {
        return emailEditText.getText().toString().trim();
    }
    public void setEmailEditText(int res) {
        this.emailEditText = (EditText) findViewById(res);
    }
    public String getPasswordEditText() {
        return passwordEditText.getText().toString().trim();
    }
    public void setPasswordEditText(int res) {
        this.passwordEditText = (EditText) findViewById(res);
    }
    public String getPasswordRepeatEditText() {
        return passwordRepeatEditText.getText().toString().trim();
    }
    public void setPasswordRepeatEditText(int res) {
        this.passwordRepeatEditText = (EditText) findViewById(res);
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
