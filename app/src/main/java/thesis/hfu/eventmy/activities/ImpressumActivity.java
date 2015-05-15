package thesis.hfu.eventmy.activities;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.dialogs.LogoutDialog;
import thesis.hfu.eventmy.functions.StartActivityFunctions;

public class ImpressumActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impressum);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    //----------------------------------------------------------------------
    //-----------------ACTION BAR MENU-------------------------------------
    //----------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_search) {
            StartActivityFunctions.getInstance().startSearchActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_friends){
            StartActivityFunctions.getInstance().startFriendsListActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==android.R.id.home){
            StartActivityFunctions.getInstance().startAllEventsActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_logout){
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
            return true;
        }else if(item.getItemId()==R.id.action_archived_events){
            StartActivityFunctions.getInstance().startArchivedEventsActivity(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
