package thesis.hfu.eventmy.objects;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBconnection;
import thesis.hfu.eventmy.functions.BuildJSON;
import thesis.hfu.eventmy.functions.Calculation;
import thesis.hfu.eventmy.functions.CheckIf;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;

import java.util.ArrayList;


public class AllTasksOfEventListAdapter extends
        RecyclerView.Adapter<AllTasksOfEventListAdapter.MyViewHolder> {

    private static final String MESSAGE= "message";
    private static final String STATUS= "status";
    private static final String EDITOR_NAME= "editor_name";
    private static final String ERROR_NUMERIC= "Sie haben keine Zahlen eingegeben!";

    private static final String URL_BECOME_EDITOR_OF_TASK= "become_editor_of_task.php";
    private static final String URL_UPDATE_PERCENTAGE_OF_TASK= "update_percentage_of_task.php";
    private static final String URL_UPDATE_COSTS_OF_TASK = "update_costs_of_task.php";


    private ArrayList<Task> tasks;
    private Context context;
    private MyViewHolder viewHolder;
    private int position,percentageValue;
    private double costsValue;
    private int type_of_update;

    public AllTasksOfEventListAdapter(Context context,ArrayList<Task> list) {
        this.tasks = list;
        this.context=context;
    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final Task task = this.tasks.get(position);

        viewHolder.task.setText(task.getTask());
        viewHolder.quantity.setText(task.getQuantity());
        viewHolder.costField.setText(String.valueOf(task.getCostOfTask()));
        viewHolder.percentageField.setText(String.valueOf(task.getPercentage()));
        viewHolder.editorField.setText(task.getEditor_name());
        viewHolder.editorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewHolder(viewHolder);
                setPosition(position);
                if (CheckSharedPreferences.getInstance().isLoggedIn(context.getApplicationContext())) {
                    changeEditorOfTask(Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), task.getTask_id());
                } else {
                    CheckSharedPreferences.getInstance().endSession(context.getApplicationContext());
                }
            }
        });
        viewHolder.percentageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewHolder(viewHolder);
                setPosition(position);
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
                                if (CheckSharedPreferences.getInstance().isLoggedIn(context.getApplicationContext())) {
                                    updatePercentage(task.getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getPercentageValue());
                                } else {
                                    CheckSharedPreferences.getInstance().endSession(context.getApplicationContext());
                                }
                            }
                        });
                builder.show();
            }
        });
        viewHolder.costButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewHolder(viewHolder);
                setPosition(position);

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
                                            setCostsValue(Calculation.getInstance().round(Double.parseDouble(userInput.getText().toString())));
                                            setType_of_update(0);
                                            if (CheckSharedPreferences.getInstance().isLoggedIn(context)) {
                                                updateCosts(task.getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getCostsValue(), getType_of_update());
                                            }else{
                                                CheckSharedPreferences.getInstance().endSession(context);
                                            }
                                            } else {
                                            Toast.makeText(context.getApplicationContext(),ERROR_NUMERIC, Toast.LENGTH_SHORT).show();
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
                                    setCostsValue(Calculation.getInstance().round(Double.parseDouble(userInput.getText().toString())));
                                    setType_of_update(1);
                                    if(CheckSharedPreferences.getInstance().isLoggedIn(context)) {
                                        updateCosts(task.getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getCostsValue(), getType_of_update());
                                    }else{
                                        CheckSharedPreferences.getInstance().endSession(context);
                                    }
                                    } else {
                                    Toast.makeText(context.getApplicationContext(),ERROR_NUMERIC, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View itemView = LayoutInflater.from(arg0.getContext()).inflate(
                R.layout.list_task_of_event_row, arg0, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        ImageButton costButton,percentageButton,editorButton;
        TextView costField,percentageField,editorField,task,quantity;

        public MyViewHolder(View itemView) {
            super(itemView);

            costButton = (ImageButton) itemView.findViewById(R.id.imageButtonListRowTaskOfEventCosts);
            percentageButton = (ImageButton) itemView.findViewById(R.id.imageButtonListRowTaskOfEventPercentage);
            editorButton = (ImageButton) itemView.findViewById(R.id.imageButtonlistRowTaskOfEventPercentage);
            costField = (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventCosts);
            percentageField= (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventPercentage);
            editorField= (TextView) itemView.findViewById(R.id.textViewlistRowTaskOfEventOrganizer);
            task= (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventName);
            quantity= (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           // Intent intent= new Intent(v.getContext(), CreateTaskActivity.class);
           // intent.putExtra(EVENT_ID,events.get(getPosition()).getEvent_id());
           // v.getContext().startActivity(intent);
        }
    }

    //----------------------------------------------------------------------
    //-----------------Functions-------------------------------------
    //----------------------------------------------------------------------

    public void updatePercentage(int task_id, int editor_id, final int percentage){

        RequestParams params = BuildJSON.getInstance().updatePercentageOfTaskJSON(task_id, editor_id, percentage);
        DBconnection.post(URL_UPDATE_PERCENTAGE_OF_TASK,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(),response.getString(MESSAGE),Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        Task task = getTasks().get(getPosition());
                        task.setPercentage(percentage);
                        getViewHolder().percentageField.setText(String.valueOf(task.getPercentage()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateCosts(int task_id, int editor_id, final double costs, final int status){

        RequestParams params = BuildJSON.getInstance().updateCostsOfTaskJSON(task_id, editor_id, costs, status);
        DBconnection.post(URL_UPDATE_COSTS_OF_TASK,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(),response.getString(MESSAGE),Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        Task task = getTasks().get(getPosition());
                        if(status==0) {
                            task.setCostOfTask(costs);
                            getViewHolder().costField.setText((String.valueOf(task.getCostOfTask())));
                        }else if(status==1){
                            task.setCostOfTask(task.getCostOfTask()+costs);
                            getViewHolder().costField.setText(String.valueOf(task.getCostOfTask()));
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void changeEditorOfTask(final int editor_id, int task_id){

        RequestParams params= BuildJSON.getInstance().becomeEditorOfTaskJSON(editor_id, task_id);
        DBconnection.post(URL_BECOME_EDITOR_OF_TASK, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context, response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        Task task = getTasks().get(getPosition());
                        task.setEditor_id(editor_id);
                        task.setEditor_name(response.getString(EDITOR_NAME));
                        getViewHolder().editorField.setText(task.getEditor_name());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public void setViewHolder(MyViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public ArrayList<Task> getTasks(){
        return this.tasks;
    }
    public int getPercentageValue() {
        return percentageValue;
    }
    public void setPercentageValue(int percentageValue) {
        this.percentageValue = percentageValue;
    }
    public double getCostsValue() {
        return costsValue;
    }
    public void setCostsValue(Double costsValue) {
        this.costsValue = costsValue;
    }
    public int getType_of_update() {
        return type_of_update;
    }
    public void setType_of_update(int type_of_update) {
        this.type_of_update = type_of_update;
    }
    public MyViewHolder getViewHolder() {
        return viewHolder;
    }
}