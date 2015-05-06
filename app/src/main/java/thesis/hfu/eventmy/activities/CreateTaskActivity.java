package thesis.hfu.eventmy.activities;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.dialogs.LogoutDialog;
import thesis.hfu.eventmy.objects.Global;

public class CreateTaskActivity extends ActionBarActivity {

    private EditText taskEditText, quantityEditText, descriptionEditText;
    private TextView editorTextView;
    private ImageView addEditorButton;
    private int event_id,editor_id;
    private String editorName;
    private FloatingActionButton createTaskButton;

    private static final String EMPTY_STRING= "";
    private static final String EVENT_ID= "event_id";
    private static final String ERROR_TASK= "Geben Sie eine Aufgabe an!";
    private final static String CREATE_TASK_BUTTON = "create_task_button";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setCreateTaskBttuon();
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setEditorTextView(R.id.textViewNewTaskEditorField);
            setTaskEditText(R.id.editTextNewTaskNameField);
            setQuantityEditText(R.id.editTextNewTaskQuantityField);
            setDescriptionEditText(R.id.editTextNewTaskNoteField);
            setAddEditorButton(R.id.imageButtonNewTaskAddButton);
            Global appState = ((Global)getApplicationContext());
            setEditor_id(appState.getEditor_id());
            setEditorName(appState.getEditorName());
            setEditorField(getEditorName());
            getCreateTaskButton().setOnClickListener(new FloatingButtonCustomClickListener());
            getAddEditorButton().setOnClickListener(new CustomClickListener());
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
            if(v.getId()==R.id.imageButtonNewTaskAddButton){
                StartActivityFunctions.getInstance().startFriendsForTaskActivity(getApplicationContext(),getEvent_id());
            }
        }
    }

    public class FloatingButtonCustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getTag().equals(CREATE_TASK_BUTTON)){
                if(!getTaskField().matches(EMPTY_STRING)){
                    if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                        DBfunctions.getInstance().createTask(getApplicationContext(),getEvent_id(),CheckSharedPreferences.getInstance().getAdmin_id(), getEditor_id(),getTaskField(),getDescriptionField(),getQuantityField());
                    }else{
                        CheckSharedPreferences.getInstance().endSession(getApplicationContext());
                    }
                }else{
                    Toast.makeText(getApplicationContext(), ERROR_TASK, Toast.LENGTH_SHORT).show();
                }
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
        if (item.getItemId()==R.id.action_search) {
            StartActivityFunctions.getInstance().startSearchActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==R.id.action_friends){
            StartActivityFunctions.getInstance().startFriendsListActivity(getApplicationContext());
            return true;
        }else if(item.getItemId()==android.R.id.home){
            StartActivityFunctions.getInstance().startAllTasksActivity(getApplicationContext(), getEvent_id());
            return true;
        }else if(item.getItemId()==R.id.action_logout){
            LogoutDialog.getInstance().startLogoutDialog(getFragmentManager());
            return true;
        }else if(item.getItemId()==R.id.action_events){
            StartActivityFunctions.getInstance().startAllEventsActivity(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getTaskField() {
        return taskEditText.getText().toString().trim();
    }
    public void setTaskEditText(int res) {
        this.taskEditText = (EditText) findViewById(res);
    }
    public String getQuantityField() {
        return quantityEditText.getText().toString().trim();
    }
    public void setQuantityEditText(int res) {
        this.quantityEditText = (EditText) findViewById(res);
    }
    public String getDescriptionField() {
        return descriptionEditText.getText().toString().trim();
    }
    public void setDescriptionEditText(int res) {
        this.descriptionEditText = (EditText) findViewById(res);
    }
    public void setEditorField(String value){
        this.editorTextView.setText(String.valueOf(value));
    }
    public void setEditorTextView(int res) {
        this.editorTextView = (TextView) findViewById(res);
    }
    public ImageView getAddEditorButton() {
        return addEditorButton;
    }
    public void setAddEditorButton(int res) {
        this.addEditorButton = (ImageView) findViewById(res);
    }
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public void setEditor_id(int editorValue) {
        this.editor_id = editorValue;
    }
    public int getEditor_id() {
        return editor_id;
    }public String getEditorName() {
        return editorName;
    }
    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }
    public void setCreateTaskBttuon(){
        ImageView icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.checkedicon));

        this.createTaskButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.add_button_shape)
                .build();
        createTaskButton.setTag(CREATE_TASK_BUTTON);
    }
    public FloatingActionButton getCreateTaskButton(){
        return this.createTaskButton;
    }
}
