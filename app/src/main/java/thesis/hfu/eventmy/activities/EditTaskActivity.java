package thesis.hfu.eventmy.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.dialogs.CommentDialog;
import thesis.hfu.eventmy.dialogs.DeleteTaskDialog;
import thesis.hfu.eventmy.dialogs.LogoutDialog;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;

public class EditTaskActivity extends ActionBarActivity {

    private TextView taskName, eventName, taskQuantity, costsField,percentageField,editorField, historyField;
    private EditText changeQuantityField, makeCommentField;
    private Button finishButton, deleteButton,commentButton;
    private ImageButton costsButton, percentageButton,editorButton;
    private int event_id,task_id;

    private static final String EVENT_ID="event_id";
    private static final String TASK_ID="task_id";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setTask_id(getIntent().getExtras().getInt(TASK_ID));

            setTaskNameField(R.id.textViewEditTaskTaskName);
            setEventNameField(R.id.textViewEditTaskEventName);
            setCostsField(R.id.textViewEditTaskCostsField);
            setChangeQuantityField(R.id.editTextEditTaskChangeQuantityField);
            setPercentageField(R.id.textViewEditTaskPercentageField);
            setEditorField(R.id.textViewEditTaskEditorField);
            setCommentButton(R.id.buttonEditTaskComment);
            setTaskQuantityField(R.id.textViewEditTaskQuantity);
            setCostsButton(R.id.imageButtonEditTaskCosts);
            setDeleteButton(R.id.buttonEditTaskDeleteTaskButton);
            setEditorButton(R.id.imageButtonEditTaskEditor);
            setFinishButton(R.id.buttonEditTaskFinishButton);
            setPercentageButton(R.id.imageButtonEditTaskPercentage);
            setMakeCommentField(R.id.editTextEditTaskNoteField);
            setHistoryField(R.id.textViewEditTaskRecentReview);
            DBfunctions.getInstance().updateTaskDetails(getEventNameTextView(), getTaskTextView(), getQuantityTextView(), getCostsTextView(), getPercentageTextView(), getEditorTextView(), getHistoryTextView(), getTask_id());
            getDeleteButton().setOnClickListener(new CustomClickListener());
            getCommentButton().setOnClickListener(new CustomClickListener());
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
            if(v.getId()==R.id.buttonEditTaskDeleteTaskButton){
                DeleteTaskDialog.getInstance().startDeleteTaskDialog(getFragmentManager(),getTask_id(),getEvent_id());
            }else if(v.getId()==R.id.buttonEditTaskComment){
                CommentDialog.getInstance().startCommentDialog(getFragmentManager(),getApplicationContext(),CheckSharedPreferences.getInstance().getAdmin_id(),getEventNameTextView(),getTaskTextView(),getQuantityTextView(),getCostsTextView(),getPercentageTextView(),getEditorTextView(),getHistoryTextView(),getTask_id());
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


    public void setTaskNameValue(String s) {
        this.taskName.setText(s);
    }
    public void setTaskNameField(int res) {
        this.taskName= (TextView) findViewById(res);
    }
    public String getEventNameValue() {
        return eventName.getText().toString();
    }
    public void setEventNameValue(String s) {
        this.eventName.setText(s);
    }
    public void setEventNameField(int res) {
        this.eventName= (TextView) findViewById(res);
    }
    public String getTaskQuantityValue() {
        return taskQuantity.getText().toString();
    }
    public void setTaskQuantityValue(String s) {
        this.taskQuantity.setText(s);
    }
    public void setTaskQuantityField(int res) {
        this.taskQuantity= (TextView) findViewById(res);
    }
    public String getCostsValue() {
        return costsField.getText().toString();
    }
    public void setCostsValue(String s) {
        this.costsField.setText(s);
    }
    public void setCostsField(int res) {
        this.costsField= (TextView) findViewById(res);
    }
    public String getPercentageValue() {
        return percentageField.getText().toString();
    }
    public void setPercentageValue(String s) {
        this.percentageField.setText(s);
    }
    public void setPercentageField(int res) {
        this.percentageField= (TextView) findViewById(res);
    }
    public String getEditorValue() {
        return editorField.getText().toString();
    }
    public void setEditorValue(String s) {
        this.editorField.setText(s);
    }
    public void setEditorField(int res) {
        this.editorField= (TextView) findViewById(res);
    }
    public String getHistoryValue() {
        return historyField.getText().toString();
    }
    public void setHistoryValue(String s) {
        this.historyField.setText(s);
    }
    public void setHistoryField(int res) {
        this.historyField= (TextView) findViewById(res);
    }
    public String getChangeQuantityField() {
        return changeQuantityField.getText().toString().trim();
    }
    public void setChangeQuantityField(int res) {
        this.changeQuantityField= (EditText) findViewById(res);
    }
    public String getMakeCommentField() {
        return makeCommentField.getText().toString().trim();
    }
    public void setMakeCommentValue(String s) {
        this.makeCommentField.setText(s);
    }
    public void setMakeCommentField(int res) {
        this.makeCommentField= (EditText) findViewById(res);
    }
    public Button getFinishButton() {
        return finishButton;
    }
    public void setFinishButton(int res) {
        this.finishButton = (Button) findViewById(res);
    }
    public Button getDeleteButton() {
        return deleteButton;
    }
    public void setDeleteButton(int res) {
        this.deleteButton = (Button) findViewById(res);
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
    public Button getCommentButton() {
        return commentButton;
    }
    public void setCommentButton(int res) {
        this.commentButton = (Button) findViewById(res);
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
}