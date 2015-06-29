package thesis.hfu.eventme.functions;

import android.content.Context;
import android.content.SharedPreferences;


public class CheckSharedPreferences {

    private static CheckSharedPreferences instance;
    private static final String SAVED_PROFILE = "SavedProfile";
    private static final int DEFAULT=-1;
    private static final String SAVED_UID = "savedUserID";
    private int admin_id;

    public static synchronized CheckSharedPreferences getInstance(){
        if (CheckSharedPreferences.instance == null){
            CheckSharedPreferences.instance = new CheckSharedPreferences();
        }
        return CheckSharedPreferences.instance;
    }

    public void setPreferences(Context context, int user_id){
        SharedPreferences.Editor editor = context.getSharedPreferences(SAVED_PROFILE, context.MODE_PRIVATE).edit();
        editor.putInt(SAVED_UID, user_id);
        editor.apply();
    }

    public boolean isLoggedIn(Context context){
        this.admin_id =-1;
        SharedPreferences sharedPreferences= context.getSharedPreferences(SAVED_PROFILE, context.MODE_PRIVATE);
        this.admin_id =sharedPreferences.getInt(SAVED_UID, DEFAULT);

        if(admin_id==DEFAULT){
            return false;
        }else {
            setAdmin_id(admin_id);
            return true;
        }
    }

    public void endSession(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(SAVED_PROFILE,context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        StartActivity.getInstance().startLoginActivity(context.getApplicationContext());
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public int getAdmin_id() {
        return admin_id;
    }
    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
}
