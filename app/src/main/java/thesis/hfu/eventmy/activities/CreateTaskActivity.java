package thesis.hfu.eventmy.activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;

public class CreateTaskActivity extends Activity{

    private EditText task,quantity,description;
    private TextView editor;
    private Button createTaskButton;
    private ImageView addEditorButton;
    private int event_id;
    private String editorValue;

    private static final String EMPTY_STRING= "";
    private static final String DEFAULT_EDITOR= "offen";
    private static final String EVENT_ID= "event_id";
    private static final String ERROR_TASK= "Geben Sie eine Aufgabe an!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
            setEvent_id(getIntent().getExtras().getInt(EVENT_ID));
            setEditor(R.id.textViewNewTaskEditorField);
            setTask(R.id.editTextNewTaskNameField);
            setQuantity(R.id.editTextNewTaskQuantityField);
            setDescription(R.id.editTextNewTaskNoteField);
            setAddEditorButton(R.id.imageButtonNewTaskAddButton);
            setCreateTaskButton(R.id.buttonNewTaskFinishButton);
            getCreateTaskButton().setOnClickListener(new CustomClickListener());
        }else{
            CheckSharedPreferences.getInstance().endSession(getApplicationContext());
        }
    }


    //----------------------------------------------------------------------
    //-----------------CUSTOM ONCLICKLISTENER-------------------------------------
    //----------------------------------------------------------------------

    public class CustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.buttonNewTaskFinishButton){

                if(!getTaskField().matches(EMPTY_STRING)){
                    if(getEditorField().matches(EMPTY_STRING)){
                        setEditorValue(DEFAULT_EDITOR);
                    }
                    if(CheckSharedPreferences.getInstance().isLoggedIn(getApplicationContext())){
                        DBfunctions.getInstance().createTask(getApplicationContext(),getEvent_id(),-1,getTaskField(),getDescriptionField(),getQuantityField());
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
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getTaskField() {
        return task.getText().toString();
    }
    public void setTask(int res) {
        this.task = (EditText) findViewById(res);
    }
    public String getQuantityField() {
        return quantity.getText().toString();
    }
    public void setQuantity(int res) {
        this.quantity = (EditText) findViewById(res);
    }
    public String getDescriptionField() {
        return description.getText().toString();
    }
    public void setDescription(int res) {
        this.description= (EditText) findViewById(res);
    }
    public String getEditorField() {
        return editor.getText().toString();
    }
    public void setEditorField(String value){
        this.editor.setText(String.valueOf(value));
    }
    public void setEditor(int res) {
        this.editor = (TextView) findViewById(res);
    }
    public Button getCreateTaskButton() {
        return createTaskButton;
    }
    public void setCreateTaskButton(int res) {
        this.createTaskButton = (Button) findViewById(res);
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
    public void setEditorValue(String editorValue) {
        this.editorValue = editorValue;
    }
}
