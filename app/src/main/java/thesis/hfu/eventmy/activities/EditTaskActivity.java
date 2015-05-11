package thesis.hfu.eventmy.activities;

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
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.dialogs.*;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.list_decoration.DividerItemDecoration;

public class EditTaskActivity extends ActionBarActivity {

    private TextView taskNameTextView, eventNameTextView, taskQuantityTextView, costsTextView, percentageTextView, editorTextView;
    private ImageButton costsButton, percentageButton,editorButton;
    private SwipeRefreshLayout syncRefresh;
    private FloatingActionButton commentTaskButton;
    private RecyclerView recyclerComments;
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1d9798")));

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setTask_id(getIntent().getExtras().getInt(TASK_ID));
            setFloatingActionButton();
            setProgressBarTask(R.id.progressBarTask);
            setSyncRefresh(R.id.swipe_refresh_all_tasks);
            setRecyclerComments(R.id.recyclerViewAllComments);
            setSyncRefresh(R.id.swipe_refresh_edit_task);
            setTaskNameTextView(R.id.textViewEditTaskTaskName);
            setEventNameTextView(R.id.textViewEditTaskEventName);
            setCostsTextView(R.id.textViewEditTaskCostsField);
            setPercentageTextView(R.id.textViewEditTaskPercentageField);
            setEditorTextView(R.id.textViewEditTaskEditorField);
            setTaskQuantityTextView(R.id.textViewEditTaskQuantity);
            setCostsButton(R.id.imageButtonEditTaskCosts);
            setEditorButton(R.id.imageButtonEditTaskEditor);
            setPercentageButton(R.id.imageButtonEditTaskPercentage);
            getRecyclerComments().setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            getRecyclerComments().setLayoutManager(layoutManager);
            getRecyclerComments().addItemDecoration(new DividerItemDecoration(this));
            getSyncRefresh().setOnRefreshListener(new CustomSwipeListener());
            getCostsButton().setOnClickListener(new CustomClickListener());
            getPercentageButton().setOnClickListener(new CustomClickListener());
            getEditorButton().setOnClickListener(new CustomClickListener());
            getCommentTaskButton().setOnClickListener(new FloatingMenuCustomClickListener());
            DBfunctions.getInstance().updateTaskDetails(getApplicationContext(),getProgressBarTask(), getSyncRefresh(),getRecyclerComments(), getEventNameTextView(), getTaskTextView(), getQuantityTextView(), getCostsTextView(), getPercentageTextView(), getEditorTextView(), getTask_id());
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
                EditCostsDialog.getInstance().startEditTaskDialog(getFragmentManager(),getSyncRefresh(),getRecyclerComments(), getCostsTextView(),null,null,null,null,null,null,getEvent_id(),getTask_id(),getTypeOfUpdate());
            }else if(v.getId()==R.id.imageButtonEditTaskPercentage){
                EditPercentageDialog.getInstance().startEditPercentageDialog(getFragmentManager(),null,getProgressBarTask(),getSyncRefresh(),getPercentageTextView(),null,null,null,null,null,null,getTask_id(),getEvent_id(),getTypeOfUpdate());
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
              CommentOnTaskDialog.getInstance().startCommentDialog(getFragmentManager(),getProgressBarTask(),getSyncRefresh(), getRecyclerComments(), getApplicationContext(), getTask_id(), CheckSharedPreferences.getInstance().getAdmin_id(), getEventNameTextView(), getTaskTextView(), getQuantityTextView(), getCostsTextView(),getPercentageTextView(),getEditorTextView());
            }
        }
    }

    public class CustomSwipeListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
               DBfunctions.getInstance().updateTaskDetails(getApplicationContext(),getProgressBarTask(),getSyncRefresh(),getRecyclerComments(), getEventNameTextView(), getTaskTextView(), getQuantityTextView(), getCostsTextView(), getPercentageTextView(), getEditorTextView(), getTask_id());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            StartActivityFunctions.getInstance().startSearchActivity(getApplicationContext());
            return true;
        } else if (item.getItemId() == R.id.action_friends) {
            StartActivityFunctions.getInstance().startFriendsListActivity(getApplicationContext());
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            StartActivityFunctions.getInstance().startAllTasksActivity(getApplicationContext(),getEvent_id());
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
            return true;
        } else if (item.getItemId() == R.id.action_archived_events) {
            StartActivityFunctions.getInstance().startArchivedEventsActivity(getApplicationContext());
            return true;
        }else if (item.getItemId() == R.id.action_edit) {
            EditTaskDialog.getInstance().startEditTaskDialog(getFragmentManager(),getProgressBarTask(),getApplicationContext(),getSyncRefresh(),getRecyclerComments(),getTaskTextView(),getQuantityTextView(),getEventNameTextView(),getCostsTextView(),getPercentageTextView(),getEditorTextView(),getTask_id(),CheckSharedPreferences.getInstance().getAdmin_id());
            return true;
        }else if (item.getItemId() == R.id.action_delete) {
            DeleteTaskDialog.getInstance().startDeleteTaskDialog(getFragmentManager(),getTask_id(),getEvent_id());
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
    public SwipeRefreshLayout getSyncRefresh() {
        return syncRefresh;
    }
    public int getTypeOfUpdate() {
        return typeOfUpdate;
    }
    public void setSyncRefresh(int res) {
        this.syncRefresh = (SwipeRefreshLayout) findViewById(res);
    }
    public void setFloatingActionButton(){
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.comment_icon_button_task));

        this.commentTaskButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.icons_shape_green)
                .build();
        this.commentTaskButton.setTag(COMMENT_TASK);
    }
    public FloatingActionButton getCommentTaskButton(){
        return this.commentTaskButton;
    }
    public RecyclerView getRecyclerComments() {
        return recyclerComments;
    }
    public void setRecyclerComments(int res) {
        this.recyclerComments = (RecyclerView) findViewById(res);
    }
    public ProgressBar getProgressBarTask() {
        return progressBarTask;
    }
    public void setProgressBarTask(int res) {
        this.progressBarTask= (ProgressBar) findViewById(res);
    }
}