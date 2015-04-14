package thesis.hfu.eventmy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;

public class EventOrganizersActivity extends Activity {

    private Button searchButton;
    private EditText searchField;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_organizers);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){

        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }



    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(int res) {
        this.searchButton = (Button) findViewById(res);
    }

    public String getSearchFieldValue() {
        return searchField.getText().toString();
    }

    public void setSearchField(int res) {
        this.searchField = (EditText) findViewById(res);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(int res) {
        this.recyclerView = (RecyclerView) findViewById(res);
    }
}
