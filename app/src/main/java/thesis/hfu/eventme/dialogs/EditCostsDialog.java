package thesis.hfu.eventme.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.functions.Calculation;
import thesis.hfu.eventme.functions.CheckIf;
import thesis.hfu.eventme.functions.CheckSharedPreferences;

public class EditCostsDialog extends DialogFragment {

    private TextView taskCostTextView, toalCostsTextView, totalPercentageTextView, totalOrganizersTextView, eventNameTextView, eventDateTextView,eventLocationTextView;
    private int event_id,task_id, typeOfCalculation, typeOfUpdate;
    private double costValue;
    private SwipeRefreshLayout syncRefresh;
    private RecyclerView recyclerView;
    private static EditCostsDialog instance;

    private static final String ERROR_NUMERIC= "Sie haben keine Zahlen eingegeben!";

    public static EditCostsDialog getInstance(){
        if (EditCostsDialog.instance == null){
            EditCostsDialog.instance = new EditCostsDialog();
        }
        return EditCostsDialog.instance;
    }

    public void startEditTaskDialog(FragmentManager manager,SwipeRefreshLayout syncRefresh,RecyclerView recyclerView,TextView taskCostTextView,TextView totalOrganizersTextView,TextView totalCostsTextView, TextView totalPercentageTextView,TextView eventNameTextView, TextView eventDateTextView,TextView eventLocationTextView,int event_id,int task_id,int typeOfUpdate) {
        setTask_id(task_id);
        setTaskCostTextView(taskCostTextView);
        setEvent_id(event_id);
        setTotalOrganizersTextView(totalOrganizersTextView);
        setToalCostsTextView(totalCostsTextView);
        setTotalPercentageTextView(totalPercentageTextView);
        setEventNameTextView(eventNameTextView);
        setEventDateTextView(eventDateTextView);
        setTypeOfUpdate(typeOfUpdate);
        setRecyclerView(recyclerView);
        setSyncRefresh(syncRefresh);
        setEventLocationTextView(eventLocationTextView);
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
                                        DBfunctions.getInstance().updateCosts(getActivity(),null,getSyncRefresh(), getTaskCostTextView(), getTotalOrganizersTextView(), getTotalPercentageTextView(), getToalCostsTextView(), getEventNameTextView(), getEventDateTextView(),getEventLocationTextView(),getEvent_id(),getTask_id(), CheckSharedPreferences.getInstance().getAdmin_id(), getCostValue(), getTypeOfCalculation(), getTypeOfUpdate());
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
                                DBfunctions.getInstance().updateCosts(getActivity(),null,getSyncRefresh(), getTaskCostTextView(), getTotalOrganizersTextView(), getTotalPercentageTextView(), getToalCostsTextView(), getEventNameTextView(), getEventDateTextView(),getEventLocationTextView(),getEvent_id(),getTask_id(), CheckSharedPreferences.getInstance().getAdmin_id(), getCostValue(), getTypeOfCalculation(), getTypeOfUpdate());
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
    public TextView getTaskCostTextView() {
        return taskCostTextView;
    }
    public void setTaskCostTextView(TextView taskCostTextView) {
        this.taskCostTextView = taskCostTextView;
    }
    public TextView getToalCostsTextView() {
        return toalCostsTextView;
    }
    public void setToalCostsTextView(TextView toalCostsTextView) {
        this.toalCostsTextView = toalCostsTextView;
    }
    public TextView getTotalPercentageTextView() {
        return totalPercentageTextView;
    }
    public void setTotalPercentageTextView(TextView totalPercentageTextView) {
        this.totalPercentageTextView = totalPercentageTextView;
    }
    public TextView getTotalOrganizersTextView() {
        return totalOrganizersTextView;
    }
    public void setTotalOrganizersTextView(TextView totalOrganizersTextView) {
        this.totalOrganizersTextView = totalOrganizersTextView;
    }
    public TextView getEventNameTextView() {
        return eventNameTextView;
    }
    public void setEventNameTextView(TextView eventNameTextView) {
        this.eventNameTextView = eventNameTextView;
    }
    public TextView getEventDateTextView() {
        return eventDateTextView;
    }
    public void setEventDateTextView(TextView eventDateTextView) {
        this.eventDateTextView = eventDateTextView;
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
    public TextView getEventLocationTextView() {
        return eventLocationTextView;
    }
    public void setEventLocationTextView(TextView eventLocationTextView) {
        this.eventLocationTextView = eventLocationTextView;
    }
    public SwipeRefreshLayout getSyncRefresh() {
        return syncRefresh;
    }
    public void setSyncRefresh(SwipeRefreshLayout syncRefresh) {
        this.syncRefresh = syncRefresh;
    }
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
