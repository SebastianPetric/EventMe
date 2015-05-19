package thesis.hfu.eventme.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.dialogs.*;
import thesis.hfu.eventme.functions.CheckSharedPreferences;
import thesis.hfu.eventme.functions.StartActivity;
import thesis.hfu.eventme.list_decoration.DividerItemDecoration;

public class CommentOnEventActivity extends ActionBarActivity {

    private TextView totalOrganizersTextView, totalCostsTextView, totalPercentageTextView, eventNameTextView, eventDateTextView,eventLocationTextView;
    private ImageButton addOrganizersButton;
    private int event_id;
    private FloatingActionButton commentOnEventButton;
    private SwipeRefreshLayout syncRefreshCommentsOnEvent;
    private RecyclerView recyclerComments;
    private ProgressBar progressBarEvent;

    private static final String EVENT_ID="event_id";
    private final static String COMMENT_ON_EVENT = "comment_on_event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_on_event);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setRecyclerComments(R.id.recyclerViewAllComments);
            setSyncRefreshCommentsOnEvent(R.id.swipe_refresh_event_comments);
            setTotalOrganizersTextView(R.id.textViewTaskOfEventTotalOrganizers);
            setTotalCostsTextView(R.id.textViewTaskOfEventTotalCosts);
            setTotalPercentageTextView(R.id.textViewTaskOfEventTotalPercentage);
            setEventNameTextView(R.id.textViewTaskOfEventEventName);
            setEventDateTextView(R.id.textViewTaskOfEventDate);
            setEventLocationTextView(R.id.textViewTaskOfEventLocation);
            setAddOrganizersButton(R.id.imageButtonTasksOfEventOrganizers);
            setProgressBarEvent(R.id.progressBarAllTasks);
            setCommentOnEventBttuon();
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            getRecyclerComments().setLayoutManager(layoutManager);
            getRecyclerComments().addItemDecoration(new DividerItemDecoration(this));
            getAddOrganizersButton().setOnClickListener(new CustomClickListener());
            getSyncRefreshCommentsOnEvent().setOnRefreshListener(new CustomSwipeListener());
            getCommentOnEventButton().setOnClickListener(new FloatingButtonCustomClickListener());
            DBfunctions.getInstance().getEventComments(getApplicationContext(), getSyncRefreshCommentsOnEvent(),getRecyclerComments(), event_id);
            DBfunctions.getInstance().updateEventDetails( getSyncRefreshCommentsOnEvent(),getProgressBarEvent(), CheckSharedPreferences.getInstance().getAdmin_id(), getEventTotalOrganizersTextView(), getEventTotalPercentageTextView(), getEventTotalCostsTextView(), getEventNameTextView(), getEventDateTextView(), getEventLocationTextView(), getEvent_id());
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }

    //----------------------------------------------------------------------
    //-----------------CUSTOM LISTENER-------------------------------------
    //----------------------------------------------------------------------

    public class CustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.imageButtonTasksOfEventOrganizers){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    StartActivity.getInstance().startEventOrganizersActivity(getApplicationContext(), getEvent_id());
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }

    public class FloatingButtonCustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getTag().equals(COMMENT_ON_EVENT)){
                if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                    CommentOnEventDialog.getInstance().startCommentDialog(getApplicationContext(),getRecyclerComments(), getSyncRefreshCommentsOnEvent(),getFragmentManager(), getEvent_id(), CheckSharedPreferences.getInstance().getAdmin_id());
                }else{
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }

    public class CustomSwipeListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            if (CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
                DBfunctions.getInstance().getEventComments(getApplicationContext(), getSyncRefreshCommentsOnEvent(),getRecyclerComments(), event_id);
                DBfunctions.getInstance().updateEventDetails( getSyncRefreshCommentsOnEvent(),getProgressBarEvent(), CheckSharedPreferences.getInstance().getAdmin_id(), getEventTotalOrganizersTextView(), getEventTotalPercentageTextView(), getEventTotalCostsTextView(), getEventNameTextView(), getEventDateTextView(), getEventLocationTextView(), getEvent_id());
            } else {
                CheckSharedPreferences.getInstance().endSession(getApplicationContext());
            }
        }
    }

    //----------------------------------------------------------------------
    //-----------------ACTION BAR MENU-------------------------------------
    //----------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_task_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            StartActivity.getInstance().startSearchActivity(getApplicationContext());
            return true;
        } else if (item.getItemId() == R.id.action_friends) {
            StartActivity.getInstance().startFriendsListActivity(getApplicationContext());
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            StartActivity.getInstance().startAllTasksActivity(getApplicationContext(),getEvent_id());
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
            return true;
        } else if (item.getItemId() == R.id.action_archived_events) {
            StartActivity.getInstance().startArchivedEventsActivity(getApplicationContext());
            return true;
        }else if (item.getItemId() == R.id.action_edit) {
            EditEventDialog.getInstance().startEditEventDialog(getFragmentManager(),getApplicationContext(),getEventDateTextView(),getEventNameTextView(),getEventDateTextView(),getEventLocationTextView(),getEvent_id());
            return true;
        }else if (item.getItemId() == R.id.action_delete) {
            DeleteArchivEventDialog.getInstance().startDeleteArchivEventDialog(getFragmentManager(),getApplicationContext(),getEvent_id());
            return true;
        }else if (item.getItemId() == R.id.action_impressum) {
            StartActivity.getInstance().startImpressumActivity(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public void setCommentOnEventBttuon(){
        ImageView icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.comments_icon_big_event));
        this.commentOnEventButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.icons_shape_red)
                .build();
        commentOnEventButton.setTag(COMMENT_ON_EVENT);
    }
    public FloatingActionButton getCommentOnEventButton(){
        return this.commentOnEventButton;
    }
    public SwipeRefreshLayout getSyncRefreshCommentsOnEvent() {
        return syncRefreshCommentsOnEvent;
    }
    public void setSyncRefreshCommentsOnEvent(int res) {
        this.syncRefreshCommentsOnEvent = (SwipeRefreshLayout) findViewById(res);
    }
    public void setAddOrganizersButton(int res) {
        this.addOrganizersButton = (ImageButton) findViewById(res);
    }
    public void setTotalOrganizersTextView(int res) {
        this.totalOrganizersTextView = (TextView) findViewById(res);
    }
    public void setTotalCostsTextView(int res) {
        this.totalCostsTextView = (TextView) findViewById(res);
    }
    public void setTotalPercentageTextView(int res) {
        this.totalPercentageTextView = (TextView) findViewById(res);
    }
    public void setEventNameTextView(int res) {
        this.eventNameTextView = (TextView) findViewById(res);
    }
    public void setEventDateTextView(int res) {
        this.eventDateTextView = (TextView) findViewById(res);
    }
    public TextView getEventNameTextView(){
        return this.eventNameTextView;
    }
    public TextView getEventDateTextView(){
        return this.eventDateTextView;
    }
    public TextView getEventTotalOrganizersTextView(){
        return this.totalOrganizersTextView;
    }
    public TextView getEventTotalCostsTextView(){
        return this.totalCostsTextView;
    }
    public TextView getEventTotalPercentageTextView(){
        return this.totalPercentageTextView;
    }
    public ImageButton getAddOrganizersButton() {
        return addOrganizersButton;
    }
    public TextView getEventLocationTextView() {
        return eventLocationTextView;
    }
    public void setEventLocationTextView(int eventLocationTextView) {
        this.eventLocationTextView= (TextView) findViewById(eventLocationTextView);
    }
    public RecyclerView getRecyclerComments() {
        return recyclerComments;
    }
    public void setRecyclerComments(int res) {
        this.recyclerComments = (RecyclerView) findViewById(res);
    }
    public ProgressBar getProgressBarEvent() {
        return progressBarEvent;
    }
    public void setProgressBarEvent(int res) {
        this.progressBarEvent = (ProgressBar) findViewById(res);
    }
}
