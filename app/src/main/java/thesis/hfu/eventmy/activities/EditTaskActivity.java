package thesis.hfu.eventmy.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.*;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.dialogs.CommentDialog;
import thesis.hfu.eventmy.dialogs.DeleteTaskDialog;
import thesis.hfu.eventmy.dialogs.EditTaskDialog;
import thesis.hfu.eventmy.dialogs.LogoutDialog;
import thesis.hfu.eventmy.functions.Calculation;
import thesis.hfu.eventmy.functions.CheckIf;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;

public class EditTaskActivity extends ActionBarActivity {

    private TextView taskName, eventName, taskQuantity, costsField,percentageField,editorField, historyField;
    private Button finishButton, deleteButton,commentButton;
    private ImageButton costsButton, percentageButton,editorButton;
    private SwipeRefreshLayout syncRefresh;
    private int event_id,task_id, percentageValue, typeOfUpdate;
    private double costValue;

    private static final String EVENT_ID="event_id";
    private static final String TASK_ID="task_id";
    private static final String ERROR_NUMERIC= "Sie haben keine Zahlen eingegeben!";

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
            setCommentButton(R.id.buttonEditTaskComment);
            setTaskQuantityField(R.id.textViewEditTaskQuantity);
            setCostsButton(R.id.imageButtonEditTaskCosts);
            setDeleteButton(R.id.buttonEditTaskDeleteTaskButton);
            setEditorButton(R.id.imageButtonEditTaskEditor);
            setFinishButton(R.id.buttonEditTaskFinishButton);
            setPercentageButton(R.id.imageButtonEditTaskPercentage);
            setHistoryField(R.id.textViewEditTaskRecentReview);
            DBfunctions.getInstance().updateTaskDetails(getApplicationContext(),getSyncRefresh(), getEventNameTextView(), getTaskTextView(), getQuantityTextView(), getCostsTextView(), getPercentageTextView(), getEditorTextView(), getHistoryTextView(), getTask_id());
            getDeleteButton().setOnClickListener(new CustomClickListener());
            getCommentButton().setOnClickListener(new CustomClickListener());
            getSyncRefresh().setOnRefreshListener(new CustomSwipeListener());
            getCostsButton().setOnClickListener(new CustomClickListener());
            getPercentageButton().setOnClickListener(new CustomClickListener());
            getEditorButton().setOnClickListener(new CustomClickListener());
            getFinishButton().setOnClickListener(new CustomClickListener());
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
                CommentDialog.getInstance().startCommentDialog(getFragmentManager(),getSyncRefresh(),getApplicationContext(),getTask_id(),CheckSharedPreferences.getInstance().getAdmin_id(),getEventNameTextView(),getTaskTextView(),getQuantityTextView(),getCostsTextView(),getPercentageTextView(),getEditorTextView(),getHistoryTextView());
            }else if(v.getId()==R.id.imageButtonEditTaskCosts){
                LayoutInflater li = LayoutInflater.from(v.getContext());
                View promptsView = li.inflate(R.layout.dialog_add_costs, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                builder.setView(promptsView);
                builder.setCancelable(false)
                        .setNeutralButton(R.string.dialog_costs_new,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (CheckIf.isNumeric(userInput.getText().toString())) {
                                            dialog.cancel();
                                            setCostValue(Calculation.getInstance().round(Double.parseDouble(userInput.getText().toString())));
                                            setTypeOfUpdate(0);
                                            if (CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
                                                DBfunctions.getInstance().updateCosts(getApplicationContext(),getCostsTextView(),getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getCostValue(), getTypeOfUpdate());
                                            }else{
                                                CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), ERROR_NUMERIC, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                        .setNegativeButton(R.string.dialog_costs_cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton(R.string.dialog_costs_add, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (CheckIf.isNumeric(userInput.getText().toString())) {
                                    dialog.cancel();
                                    setCostValue(Calculation.getInstance().round(Double.parseDouble(userInput.getText().toString())));
                                    setTypeOfUpdate(1);
                                    if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())) {
                                        DBfunctions.getInstance().updateCosts(getApplicationContext(), getCostsTextView(), getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getCostValue(), getTypeOfUpdate());
                                    }else{
                                        CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(),ERROR_NUMERIC, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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
                                    DBfunctions.getInstance().updatePercentage(getApplicationContext(),getPercentageTextView(),getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getPercentageValue());
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
            }else if(v.getId()==R.id.buttonEditTaskFinishButton) {
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
    public int getPercentageValue() {
        return percentageValue;
    }
    public void setPercentageValue(int percentageValue) {
        this.percentageValue = percentageValue;
    }
    public int getTypeOfUpdate() {
        return typeOfUpdate;
    }
    public void setTypeOfUpdate(int typeOfUpdate) {
        this.typeOfUpdate = typeOfUpdate;
    }
    public double getCostValue() {
        return costValue;
    }
    public void setCostValue(double costValue) {
        this.costValue = costValue;
    }
    public SwipeRefreshLayout getSyncRefresh() {
        return syncRefresh;
    }
    public void setSyncRefresh(int res) {
        this.syncRefresh = (SwipeRefreshLayout) findViewById(res);
    }



}