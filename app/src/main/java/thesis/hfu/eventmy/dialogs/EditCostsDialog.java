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

    private TextView taskCostField,toalCosts,totalPercentage,totalOrganizers,eventName,eventDate;
    private int event_id,task_id, typeOfCalculation, typeOfUpdate;
    private double costValue;
    private static EditCostsDialog instance;

    private static final String ERROR_NUMERIC= "Sie haben keine Zahlen eingegeben!";

    public static EditCostsDialog getInstance(){
        if (EditCostsDialog.instance == null){
            EditCostsDialog.instance = new EditCostsDialog();
        }
        return EditCostsDialog.instance;
    }

    public void startEditTaskDialog(FragmentManager manager,TextView taskCostField,TextView totalOrganizers,TextView totalCosts, TextView totalPercentage,TextView eventName, TextView eventDate,int event_id,int task_id,int typeOfUpdate) {
        setTask_id(task_id);
        setTaskCostField(taskCostField);
        setEvent_id(event_id);
        setTotalOrganizers(totalOrganizers);
        setToalCosts(totalCosts);
        setTotalPercentage(totalPercentage);
        setEventName(eventName);
        setEventDate(eventDate);
        setTypeOfUpdate(typeOfUpdate);
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
                                    setTypeOfCalculation(0);
                                    if (CheckSharedPreferences.getInstance().isLoggedIn(getActivity())) {
                                        DBfunctions.getInstance().updateCosts(getActivity(),getTaskCostField(),getTotalOrganizers(),getTotalPercentage(),getToalCosts(),getEventName(),getEventDate(),getEvent_id(),getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getCostValue(), getTypeOfCalculation(), getTypeOfUpdate());
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
                            setTypeOfCalculation(1);
                            if (CheckSharedPreferences.getInstance().isLoggedIn(getActivity())) {
                                DBfunctions.getInstance().updateCosts(getActivity(),getTaskCostField(),getTotalOrganizers(),getTotalPercentage(),getToalCosts(),getEventName(),getEventDate(),getEvent_id(),getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getCostValue(), getTypeOfCalculation(), getTypeOfUpdate());
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
    public int getTypeOfCalculation() {
        return typeOfCalculation;
    }
    public void setTypeOfCalculation(int typeOfCalculation) {
        this.typeOfCalculation = typeOfCalculation;
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
    public TextView getToalCosts() {
        return toalCosts;
    }
    public void setToalCosts(TextView toalCosts) {
        this.toalCosts = toalCosts;
    }
    public TextView getTotalPercentage() {
        return totalPercentage;
    }
    public void setTotalPercentage(TextView totalPercentage) {
        this.totalPercentage = totalPercentage;
    }
    public TextView getTotalOrganizers() {
        return totalOrganizers;
    }
    public void setTotalOrganizers(TextView totalOrganizers) {
        this.totalOrganizers = totalOrganizers;
    }
    public TextView getEventName() {
        return eventName;
    }
    public void setEventName(TextView eventName) {
        this.eventName = eventName;
    }
    public TextView getEventDate() {
        return eventDate;
    }
    public void setEventDate(TextView eventDate) {
        this.eventDate = eventDate;
    }
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public int getTypeOfUpdate() {
        return typeOfUpdate;
    }
    public void setTypeOfUpdate(int typeOfUpdate) {
        this.typeOfUpdate = typeOfUpdate;
    }
}
