package thesis.hfu.eventmy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBconnection;
import thesis.hfu.eventmy.functions.BuildJSON;
import thesis.hfu.eventmy.functions.CheckIf;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;


public class LoginActivity extends ActionBarActivity {

    private EditText emailField,passwordField;
    private Button loginFinishButton;
    private TextView registrationTextButton;

    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private static final String USER_ID = "user_id";

    private static final String ERROR_EMAIL = "Email nicht korrekt!";
    private static final String ERROR_FIELD = "Bitte alle Felder korrekt ausfüllen!";
    private static final String EMPTY_STRING = "";

    private static final String URL_LOGIN= "login_check.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setEmailField(R.id.loginEmailEditText);
        setPasswordField(R.id.loginPasswortEditText);
        setLoginFinishButton(R.id.loginButton);
        setRegistrationTextButton(R.id.loginRegistrationButton);
        getLoginFinishButton().setOnClickListener(new CustomButtonListener());
        getRegistrationTextButton().setOnClickListener(new CustomButtonListener());
    }

    public class CustomButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
               if(v.getId()==R.id.loginRegistrationButton){
                   Intent intent= new Intent(getApplicationContext(),RegistrationActivity.class);
                   startActivity(intent);
               }
               if(v.getId()==R.id.loginButton){

                   if(!getEmailField().equals(EMPTY_STRING)&&!getPasswordField().equals(EMPTY_STRING)) {
                       if (CheckIf.EmailIsValid(getEmailField())) {
                            login(getEmailField(),getPasswordField());
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
    //-----------------Functions-------------------------------------
    //----------------------------------------------------------------------

    public void login(String email,String password){
        RequestParams params= BuildJSON.getInstance().loginJSON(email,password);
        DBconnection.post(URL_LOGIN, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        CheckSharedPreferences.getInstance().setPreferances(getApplicationContext(),response.getString(USER_ID));
                        Intent intent= new Intent(getApplicationContext(),AllEventsActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("schlecht",statusCode+"");

                try {
                    Toast.makeText(getApplicationContext(), errorResponse.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

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
