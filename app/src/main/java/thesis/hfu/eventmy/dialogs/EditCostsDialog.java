package thesis.hfu.eventmy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.Calculation;
import thesis.hfu.eventmy.functions.CheckIf;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;

public class EditCostsDialog extends DialogFragment {

    private TextView taskCostField;
    private int task_id,typeOfUpdate;
    private double costValue;
    private static EditCostsDialog instance;

    private static final String ERROR_NUMERIC= "Sie haben keine Zahlen eingegeben!";

    public static EditCostsDialog getInstance(){
        if (EditCostsDialog.instance == null){
            EditCostsDialog.instance = new EditCostsDialog();
        }
        return EditCostsDialog.instance;
    }

    public void startEditTaskDialog(FragmentManager manager,TextView taskCostField,int task_id) {
        setTask_id(task_id);
        setTaskCostField(taskCostField);
        this.show(manager, "editCostsDialog");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.dialog_add_costs, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                                    if (CheckSharedPreferences.getInstance().isLoggedIn(getActivity())) {
                                        DBfunctions.getInstance().updateCosts(getActivity(),getTaskCostField(),getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getCostValue(), getTypeOfUpdate());
                                    }else{
                                        CheckSharedPreferences.getInstance().endSession(getActivity());
                                    }
                                } else {
                                    Toast.makeText(getActivity(), ERROR_NUMERIC, Toast.LENGTH_SHORT).show();
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
                            if (CheckSharedPreferences.getInstance().isLoggedIn(getActivity())) {
                                DBfunctions.getInstance().updateCosts(getActivity(), getTaskCostField(), getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getCostValue(), getTypeOfUpdate());
                            } else {
                                CheckSharedPreferences.getInstance().endSession(getActivity());
                            }
                        } else {
                            Toast.makeText(getActivity(), ERROR_NUMERIC, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return builder.create();
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public int getTask_id() {
        return task_id;
    }
    public void setTask_id(int task_id) {
        this.task_id = task_id;
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
    public TextView getTaskCostField() {
        return taskCostField;
    }
    public void setTaskCostField(TextView taskCostField) {
        this.taskCostField = taskCostField;
    }
}
