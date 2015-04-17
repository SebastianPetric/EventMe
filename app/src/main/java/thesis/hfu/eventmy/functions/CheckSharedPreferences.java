package thesis.hfu.eventmy.functions;

import android.content.Context;
import android.content.SharedPreferences;


public class CheckSharedPreferences {

    private static CheckSharedPreferences instance;
    private static final String SAVED_PROFILE = "SavedProfile";
    private static final String DEFAULT="N/A";
    private static final String SAVED_UID = "savedUserID";
    private String admin_id;

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

        this.admin_id =null;
        SharedPreferences sharedPreferences= context.getSharedPreferences(SAVED_PROFILE, context.MODE_PRIVATE);
        this.admin_id =sharedPreferences.getString(SAVED_UID, DEFAULT);

        if(admin_id.equals(DEFAULT)){
            return false;
        }else {
            setAdmin_id(admin_id);
            return true;
        }
    }

    public void endSession(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SAVED_PROFILE,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        StartActivityFunctions.getInstance().startLoginActivity(context.getApplicationContext());
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getAdmin_id() {
        return admin_id;
    }
    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }
}
