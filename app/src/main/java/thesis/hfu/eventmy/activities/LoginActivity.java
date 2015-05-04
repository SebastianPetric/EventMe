package thesis.hfu.eventmy.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckIf;
import thesis.hfu.eventmy.functions.StartActivityFunctions;


public class LoginActivity extends ActionBarActivity {

    private EditText emailField,passwordField;
    private Button loginFinishButton;
    private TextView registrationTextButton;

    private static final String ERROR_EMAIL = "Email nicht korrekt!";
    private static final String ERROR_FIELD = "Bitte alle Felder korrekt ausfüllen!";
    private static final String EMPTY_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setEmailField(R.id.loginEmailEditText);
        setPasswordField(R.id.loginPasswortEditText);
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
                   StartActivityFunctions.getInstance().startRegistrationActivity(getApplicationContext());
               }else if(v.getId()==R.id.loginButton){
                   if(!getEmailField().equals(EMPTY_STRING)&&!getPasswordField().equals(EMPTY_STRING)) {
                       if (CheckIf.EmailIsValid(getEmailField())) {
                           DBfunctions.getInstance().login(getApplicationContext(), getEmailField(), getPasswordField());
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
