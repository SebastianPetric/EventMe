package thesis.hfu.eventme.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class EditTaskActivity extends ActionBarActivity {

    private TextView taskNameTextView, eventNameTextView, taskQuantityTextView, costsTextView, percentageTextView, editorTextView;
    private ImageButton costsButton, percentageButton,editorButton;
    private SwipeRefreshLayout syncRefreshTaskDetails;
    private FloatingActionButton commentOnTaskButton;
    private RecyclerView recyclerCommentsOnTask;
    private ProgressBar progressBarTask;
    private int event_id,task_id;
    // No Event Updates necessary in this activity
    private final int typeOfUpdate=0;

    private static final String EVENT_ID="event_id";
    private static final String TASK_ID="task_id";
    private static final String COMMENT_TASK="comment_task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Actionbar Color green, because it's about a task
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1d9798")));

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setTask_id(getIntent().getExtras().getInt(TASK_ID));
            setCommentOnTaskButton();
            setProgressBarTask(R.id.progressBarTask);
            setSyncRefreshTaskDetails(R.id.swipe_refresh_all_tasks);
            setRecyclerCommentsOnTask(R.id.recyclerViewAllComments);
            setSyncRefreshTaskDetails(R.id.swipe_refresh_edit_task);
            setTaskNameTextView(R.id.textViewEditTaskTaskName);
            setEventNameTextView(R.id.textViewEditTaskEventName);
            setCostsTextView(R.id.textViewEditTaskCostsField);
            setPercentageTextView(R.id.textViewEditTaskPercentageField);
            setEditorTextView(R.id.textViewEditTaskEditorField);
            setTaskQuantityTextView(R.id.textViewEditTaskQuantity);
            setCostsButton(R.id.imageButtonEditTaskCosts);
            setEditorButton(R.id.imageButtonEditTaskEditor);
            setPercentageButton(R.id.imageButtonEditTaskPercentage);
            getRecyclerCommentsOnTask().setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            getRecyclerCommentsOnTask().setLayoutManager(layoutManager);
            getRecyclerCommentsOnTask().addItemDecoration(new DividerItemDecoration(this));
            getSyncRefreshTaskDetails().setOnRefreshListener(new CustomSwipeListener());
            getCostsButton().setOnClickListener(new CustomClickListener());
            getPercentageButton().setOnClickListener(new CustomClickListener());
            getEditorButton().setOnClickListener(new CustomClickListener());
            getCommentOnTaskButton().setOnClickListener(new FloatingMenuCustomClickListener());
            DBfunctions.getInstance().updateTaskDetails(getApplicationContext(),getProgressBarTask(), getSyncRefreshTaskDetails(), getRecyclerCommentsOnTask(), getEventNameTextView(), getTaskTextView(), getQuantityTextView(), getCostsTextView(), getPercentageTextView(), getEditorTextView(), getTask_id());
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }

    //----------------------------------------------------------------------
    //-----------------CUSTOM LISTENER-------------------------------------
    //----------------------------------------------------------------------

    public class CustomClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.imageButtonEditTaskCosts){
                EditCostsDialog.getInstance().startEditTaskDialog(getFragmentManager(), getSyncRefreshTaskDetails(), getRecyclerCommentsOnTask(), getCostsTextView(),null,null,null,null,null,null,getEvent_id(),getTask_id(),getTypeOfUpdate());
            }else if(v.getId()==R.id.imageButtonEditTaskPercentage){
                EditPercentageDialog.getInstance().startEditPercentageDialog(getFragmentManager(),null,getProgressBarTask(), getSyncRefreshTaskDetails(),getPercentageTextView(),null,null,null,null,null,null,getTask_id(),getEvent_id(),getTypeOfUpdate());
            }else if(v.getId()==R.id.imageButtonEditTaskEditor){
                if (CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
                    DBfunctions.getInstance().changeEditorOfTask(getApplicationContext(), getEditorTextView(), CheckSharedPreferences.getInstance().getAdmin_id(),getTask_id());
                } else {
                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                }
            }
        }
    }

    public class FloatingMenuCustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
           if(v.getTag().equals(COMMENT_TASK)) {
              CommentOnTaskDialog.getInstance().startCommentDialog(getFragmentManager(),getProgressBarTask(), getSyncRefreshTaskDetails(), getRecyclerCommentsOnTask(), getApplicationContext(), getTask_id(), CheckSharedPreferences.getInstance().getAdmin_id(), getEventNameTextView(), getTaskTextView(), getQuantityTextView(), getCostsTextView(),getPercentageTextView(),getEditorTextView());
            }
        }
    }

    public class CustomSwipeListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
               DBfunctions.getInstance().updateTaskDetails(getApplicationContext(),getProgressBarTask(), getSyncRefreshTaskDetails(), getRecyclerCommentsOnTask(), getEventNameTextView(), getTaskTextView(), getQuantityTextView(), getCostsTextView(), getPercentageTextView(), getEditorTextView(), getTask_id());
            }else{
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
    public void onBackPressed() {
        StartActivity.getInstance().startAllTasksActivity(getApplicationContext(),getEvent_id());
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
            EditTaskDialog.getInstance().startEditTaskDialog(getFragmentManager(),getProgressBarTask(),getApplicationContext(), getSyncRefreshTaskDetails(), getRecyclerCommentsOnTask(),getTaskTextView(),getQuantityTextView(),getEventNameTextView(),getCostsTextView(),getPercentageTextView(),getEditorTextView(),getTask_id(),CheckSharedPreferences.getInstance().getAdmin_id());
            return true;
        }else if (item.getItemId() == R.id.action_delete) {
            DeleteTaskDialog.getInstance().startDeleteTaskDialog(getFragmentManager(),getTask_id(),getEvent_id());
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

    public void setTaskNameTextView(int res) {
        this.taskNameTextView = (TextView) findViewById(res);
    }
    public void setEventNameTextView(int res) {
        this.eventNameTextView = (TextView) findViewById(res);
    }
    public void setTaskQuantityTextView(int res) {
        this.taskQuantityTextView = (TextView) findViewById(res);
    }
    public void setCostsTextView(int res) {
        this.costsTextView = (TextView) findViewById(res);
    }
    public void setPercentageTextView(int res) {
        this.percentageTextView = (TextView) findViewById(res);
    }
    public void setEditorTextView(int res) {
        this.editorTextView = (TextView) findViewById(res);
    }
    public ImageButton getCostsButton() {
        return costsButton;
    }
    public void setCostsButton(int res) {
        this.costsButton = (ImageButton) findViewById(res);
    }
    public ImageButton getPercentageButton() {
        return percentageButton;
    }
    public void setPercentageButton(int res) {
        this.percentageButton = (ImageButton) findViewById(res);
    }
    public ImageButton getEditorButton() {
        return editorButton;
    }
    public void setEditorButton(int res) {
        this.editorButton = (ImageButton) findViewById(res);
    }
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public int getTask_id() {
        return task_id;
    }
    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
    public TextView getEventNameTextView(){
        return this.eventNameTextView;
    }
    public TextView getTaskTextView(){
        return this.taskNameTextView;
    }
    public TextView getQuantityTextView(){
        return this.taskQuantityTextView;
    }
    public TextView getCostsTextView(){
        return this.costsTextView;
    }
    public TextView getPercentageTextView(){
        return this.percentageTextView;
    }
    public TextView getEditorTextView(){
        return this.editorTextView;
    }
    public SwipeRefreshLayout getSyncRefreshTaskDetails() {
        return syncRefreshTaskDetails;
    }
    public int getTypeOfUpdate() {
        return typeOfUpdate;
    }
    public void setSyncRefreshTaskDetails(int res) {
        this.syncRefreshTaskDetails = (SwipeRefreshLayout) findViewById(res);
    }
    public void setCommentOnTaskButton(){
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.comment_icon_button_task));
        this.commentOnTaskButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.icons_shape_green)
                .build();
        this.commentOnTaskButton.setTag(COMMENT_TASK);
    }
    public FloatingActionButton getCommentOnTaskButton(){
        return this.commentOnTaskButton;
    }
    public RecyclerView getRecyclerCommentsOnTask() {
        return recyclerCommentsOnTask;
    }
    public void setRecyclerCommentsOnTask(int res) {
        this.recyclerCommentsOnTask = (RecyclerView) findViewById(res);
    }
    public ProgressBar getProgressBarTask() {
        return progressBarTask;
    }
    public void setProgressBarTask(int res) {
        this.progressBarTask= (ProgressBar) findViewById(res);
    }
}