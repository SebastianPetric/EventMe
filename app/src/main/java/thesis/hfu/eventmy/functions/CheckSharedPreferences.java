package thesis.hfu.eventmy.functions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import thesis.hfu.eventmy.activities.LoginActivity;


public class CheckSharedPreferences {

    private static CheckSharedPreferences instance;
    private static final String SAVED_PROFILE = "SavedProfile";
    private static final String DEFAULT="N/A";
    private static final String SAVED_UID = "savedUserID";
    private String user_id;

    public static CheckSharedPreferences getInstance(){

        if (CheckSharedPreferences.instance == null){
            CheckSharedPreferences.instance = new CheckSharedPreferences();
        }
        return CheckSharedPreferences.instance;
    }

    public void setPreferances(Context context, String user_id){
        SharedPreferences.Editor editor = context.getSharedPreferences(SAVED_PROFILE, context.MODE_PRIVATE).edit();
        editor.putString(SAVED_UID, user_id);
        editor.apply();
    }

    public boolean isLoggedIn(Context context){

        this.user_id=null;
        SharedPreferences sharedPreferences= context.getSharedPreferences(SAVED_PROFILE, context.MODE_PRIVATE);
        this.user_id=sharedPreferences.getString(SAVED_UID, DEFAULT);

        if(user_id.equals(DEFAULT)){
            return false;
        }else {
            setUser_id(user_id);
            return true;
        }
    }

    public void endSession(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SAVED_PROFILE,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent= new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
