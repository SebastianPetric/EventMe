package thesis.hfu.eventme.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.functions.CheckIf;
import thesis.hfu.eventme.functions.CheckSharedPreferences;
import thesis.hfu.eventme.functions.StartActivity;

public class LoginActivity extends ActionBarActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginFinishButton;
    private TextView registrationTextButton;

    private static final String ERROR_EMAIL = "Email nicht korrekt!";
    private static final String ERROR_FIELD = "Bitte alle Felder korrekt ausfüllen!";
    private static final String EMPTY_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(CheckSharedPreferences.getInstance().isLoggedIn(this)){
            StartActivity.getInstance().startAllEventsActivity(this);
        }
        setEmailEditText(R.id.loginEmailEditText);
        setPasswordEditText(R.id.loginPasswortEditText);
        setLoginFinishButton(R.id.loginButton);
        setRegistrationTextButton(R.id.loginRegistrationButton);
        getLoginFinishButton().setOnClickListener(new CustomClickListener());
        getRegistrationTextButton().setOnClickListener(new CustomClickListener());
    }

    //----------------------------------------------------------------------
    //-----------------CUSTOM ONCLICKLISTENER-------------------------------------
    //----------------------------------------------------------------------

    public class CustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
               if(v.getId()==R.id.loginRegistrationButton){
                   StartActivity.getInstance().startRegistrationActivity(getApplicationContext());
               }else if(v.getId()==R.id.loginButton){
                   if(!getEmailEditText().equals(EMPTY_STRING)&&!getPasswordEditText().equals(EMPTY_STRING)) {
                       if (CheckIf.emailIsValid(getEmailEditText())) {
                           DBfunctions.getInstance().login(getApplicationContext(), getEmailEditText(), getPasswordEditText());
                       }else{
                           Toast.makeText(getApplicationContext(),ERROR_EMAIL,Toast.LENGTH_SHORT).show();
                       }
                   }else{
                       Toast.makeText(getApplicationContext(),ERROR_FIELD,Toast.LENGTH_SHORT).show();
                   }
               }
        }
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

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
    public void setLoginFinishButton(int res) {
        this.loginFinishButton = (Button) findViewById(res);
    }
    public TextView getRegistrationTextButton() {
        return registrationTextButton;
    }
    public Button getLoginFinishButton() {
        return loginFinishButton;
    }
    public void setRegistrationTextButton(int res) {
        this.registrationTextButton = (TextView) findViewById(res);
    }
}
