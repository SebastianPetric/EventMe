package thesis.hfu.eventmy.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import thesis.hfu.eventmy.functions.CheckIf;
import thesis.hfu.eventmy.functions.BuildJSON;
import thesis.hfu.eventmy.database.DBconnection;

public class RegistrationActivity extends Activity {

    private EditText nameField,prenameField,emailField,passwordField,passwordRepeatField;
    private Button finishRegistrationButton;

    private static final String MESSAGE = "message";
    private static final String EMPTY_STRING = "";
    private static final String ERROR_PASSWORD = "Passwort falsch wiederholt!";
    private static final String ERROR_EMAIL = "Email Adresse nicht korrekt!";
    private static final String ERROR_EMPTY_FIELDS = "Bitte alle Felder ausfüllen!";

    private static final String URL_REGISTRATION= "register_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setNameField(R.id.registrationNameField);
        setPrenameField(R.id.registrationPrenameField);
        setEmailField(R.id.registrationEmailField);
        setPasswordField(R.id.registrationPasswordField);
        setPasswordRepeatField(R.id.registrationPasswordRepeatField);
        setFinishRegistrationButton(R.id.registrationButton);
        getFinishRegistrationButton().setOnClickListener(new RegistrationButton());
    }


    public class RegistrationButton implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(!getNameField().equals(EMPTY_STRING)&&!getPrenameField().equals(EMPTY_STRING)&&!getEmailField().equals(EMPTY_STRING)
                    &&!getPasswordField().equals(EMPTY_STRING)&&!getPasswordRepeatField().equals(EMPTY_STRING)){
            if(CheckIf.EmailIsValid(getEmailField())){
                if(getPasswordField().equals(getPasswordRepeatField())){
                        registration(getNameField(),getPrenameField(),getEmailField(),getPasswordField());
                    }else {
                    Toast.makeText(getApplicationContext(), ERROR_PASSWORD, Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), ERROR_EMAIL, Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(getApplicationContext(),ERROR_EMPTY_FIELDS,Toast.LENGTH_SHORT).show();
            }
        }
    }
    //----------------------------------------------------------------------
    //-----------------Functions-------------------------------------
    //----------------------------------------------------------------------

    public void registration(String name, String prename,String email,String password){

        RequestParams params= BuildJSON.getInstance().registrationJSON(name,prename,email,password);
        DBconnection.post(URL_REGISTRATION, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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

    public String getNameField() {
        return nameField.getText().toString();
    }
    public void setNameField(int res) {
        this.nameField= (EditText) findViewById(res);
    }
    public String getPrenameField() {
        return prenameField.getText().toString();
    }
    public void setPrenameField(int res) {
        this.prenameField = (EditText) findViewById(res);
    }
    public String getEmailField() {
        return emailField.getText().toString();
    }
    public void setEmailField(int res) {
        this.emailField = (EditText) findViewById(res);
    }
    public String getPasswordField() {
        return passwordField.getText().toString();
    }
    public void setPasswordField(int res) {
        this.passwordField = (EditText) findViewById(res);
    }
    public String getPasswordRepeatField() {
        return passwordRepeatField.getText().toString();
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
}
