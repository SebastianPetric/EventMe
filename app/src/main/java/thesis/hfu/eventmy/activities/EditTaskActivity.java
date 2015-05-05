package thesis.hfu.eventmy.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.dialogs.*;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;

public class EditTaskActivity extends ActionBarActivity {

    private TextView taskName, eventName, taskQuantity, costsField,percentageField,editorField, historyField;
    private ImageButton costsButton, percentageButton,editorButton;
    private SwipeRefreshLayout syncRefresh;
    private FloatingActionMenu actionMenu;
    private int event_id,task_id, percentageValue, typeOfUpdate;
    private double costValue;

    private static final String EVENT_ID="event_id";
    private static final String TASK_ID="task_id";
    private static final String ERROR_NUMERIC= "Sie haben keine Zahlen eingegeben!";
    private static final String DELETE_TASK="delete_task";
    private static final String EDIT_TASK="edit_task";
    private static final String COMMENT_TASK="comment_task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setTask_id(getIntent().getExtras().getInt(TASK_ID));

            setSyncRefresh(R.id.swipe_refresh_edit_task);
            setTaskNameField(R.id.textViewEditTaskTaskName);
            setEventNameField(R.id.textViewEditTaskEventName);
            setCostsField(R.id.textViewEditTaskCostsField);
            setPercentageField(R.id.textViewEditTaskPercentageField);
            setEditorField(R.id.textViewEditTaskEditorField);
            setTaskQuantityField(R.id.textViewEditTaskQuantity);
            setCostsButton(R.id.imageButtonEditTaskCosts);
            setEditorButton(R.id.imageButtonEditTaskEditor);
            setPercentageButton(R.id.imageButtonEditTaskPercentage);
            setHistoryField(R.id.textViewEditTaskRecentReview);
            DBfunctions.getInstance().updateTaskDetails(getApplicationContext(),getSyncRefresh(), getEventNameTextView(), getTaskTextView(), getQuantityTextView(), getCostsTextView(), getPercentageTextView(), getEditorTextView(), getHistoryTextView(), getTask_id());
            getSyncRefresh().setOnRefreshListener(new CustomSwipeListener());
            getCostsButton().setOnClickListener(new CustomClickListener());
            getPercentageButton().setOnClickListener(new CustomClickListener());
            getEditorButton().setOnClickListener(new CustomClickListener());
            setFloatingActionMenu();
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
                EditCostsDialog.getInstance().startEditTaskDialog(getFragmentManager(),getCostsTextView(),getTask_id());
            }else if(v.getId()==R.id.imageButtonEditTaskPercentage){
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String[] percentageValues={"0","25","50","75","100"};
                builder.setTitle(R.string.dialog_percentage_header)
                        .setItems(percentageValues, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                switch (position) {
                                    case 0:
                                        setPercentageValue(0);
                                        break;
                                    case 1:
                                        setPercentageValue(25);
                                        break;
                                    case 2:
                                        setPercentageValue(50);
                                        break;
                                    case 3:
                                        setPercentageValue(75);
                                        break;
                                    case 4:
                                        setPercentageValue(100);
                                        break;
                                }
                                if (CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
                                    DBfunctions.getInstance().updatePercentage(getApplicationContext(), getPercentageTextView(), getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getPercentageValue());
                                } else {
                                    CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                                }
                            }
                        });
                builder.show();

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
            if(v.getTag().equals(DELETE_TASK)){
                DeleteTaskDialog.getInstance().startDeleteTaskDialog(getFragmentManager(),getTask_id(),getEvent_id());
                actionMenu.close(true);
            }else if(v.getTag().equals(COMMENT_TASK)){
                actionMenu.close(true);
                CommentOnTaskDialog.getInstance().startCommentDialog(getFragmentManager(),getSyncRefresh(),getApplicationContext(),getTask_id(),CheckSharedPreferences.getInstance().getAdmin_id(),getEventNameTextView(),getTaskTextView(),getQuantityTextView(),getCostsTextView(),getPercentageTextView(),getEditorTextView(),getHistoryTextView());
            }else if(v.getTag().equals(EDIT_TASK)) {
                actionMenu.close(true);
                EditTaskDialog.getInstance().startEditTaskDialog(getFragmentManager(),getApplicationContext(),getTaskTextView(),getQuantityTextView(),getTask_id(),CheckSharedPreferences.getInstance().getAdmin_id());
            }
        }
    }

    public class CustomSwipeListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                DBfunctions.getInstance().updateTaskDetails(getApplicationContext(),getSyncRefresh(), getEventNameTextView(), getTaskTextView(), getQuantityTextView(), getCostsTextView(), getPercentageTextView(), getEditorTextView(), getHistoryTextView(), getTask_id());
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
        inflater.inflate(R.menu.menu, menu);
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
        } else if (item.getItemId() == R.id.action_events) {
            StartActivityFunctions.getInstance().startAllEventsActivity(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public void setTaskNameField(int res) {
        this.taskName= (TextView) findViewById(res);
    }
    public void setEventNameField(int res) {
        this.eventName= (TextView) findViewById(res);
    }
    public void setTaskQuantityField(int res) {
        this.taskQuantity= (TextView) findViewById(res);
    }
    public void setCostsField(int res) {
        this.costsField= (TextView) findViewById(res);
    }
    public void setPercentageField(int res) {
        this.percentageField= (TextView) findViewById(res);
    }
    public void setEditorField(int res) {
        this.editorField= (TextView) findViewById(res);
    }
    public void setHistoryField(int res) {
        this.historyField= (TextView) findViewById(res);
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
        return this.eventName;
    }
    public TextView getTaskTextView(){
        return this.taskName;
    }
    public TextView getQuantityTextView(){
        return this.taskQuantity;
    }
    public TextView getCostsTextView(){
        return this.costsField;
    }
    public TextView getPercentageTextView(){
        return this.percentageField;
    }
    public TextView getEditorTextView(){
        return this.editorField;
    }
    public TextView getHistoryTextView(){
        return this.historyField;
    }
    public int getPercentageValue() {
        return percentageValue;
    }
    public void setPercentageValue(int percentageValue) {
        this.percentageValue = percentageValue;
    }
    public SwipeRefreshLayout getSyncRefresh() {
        return syncRefresh;
    }
    public void setSyncRefresh(int res) {
        this.syncRefresh = (SwipeRefreshLayout) findViewById(res);
    }
    public void setFloatingActionMenu(){
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.editiconbig));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.add_button_shape)
                .build();

        ImageView deleteFloatButton= new ImageButton(this);
        deleteFloatButton.setImageResource(R.drawable.deleteicon);
        deleteFloatButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.icons_shape));
        ImageView commentFloatButton= new ImageButton(this);
        commentFloatButton.setImageResource(R.drawable.commenticon);
        commentFloatButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.icons_shape));
        ImageView editFloatButton= new ImageButton(this);
        editFloatButton.setImageResource(R.drawable.editicon);
        editFloatButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.icons_shape));

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton buttonEditText = itemBuilder.setContentView(editFloatButton).build();
        SubActionButton buttonDeleteTask = itemBuilder.setContentView(deleteFloatButton).build();
        SubActionButton buttonCommentTask = itemBuilder.setContentView(commentFloatButton).build();

        buttonEditText.setTag(EDIT_TASK);
        buttonDeleteTask.setTag(DELETE_TASK);
        buttonCommentTask.setTag(COMMENT_TASK);

        this.actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonDeleteTask)
                .addSubActionView(buttonEditText)
                .addSubActionView(buttonCommentTask)
                .attachTo(actionButton)
                .build();

        buttonEditText.setOnClickListener(new FloatingMenuCustomClickListener());
        buttonDeleteTask.setOnClickListener(new FloatingMenuCustomClickListener());
        buttonCommentTask.setOnClickListener(new FloatingMenuCustomClickListener());

    }
}