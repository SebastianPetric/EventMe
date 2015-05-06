package thesis.hfu.eventmy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;

public class EditPercentageDialog extends DialogFragment {

    private TextView percentageTextView,totalOrganizers,totalCosts,totalPercentage,eventName,eventDate;
    private int task_id,event_id, percentageValue,status_of_update;
    private static EditPercentageDialog instance;

    public static EditPercentageDialog getInstance(){
        if (EditPercentageDialog.instance == null){
            EditPercentageDialog.instance = new EditPercentageDialog();
        }
        return EditPercentageDialog.instance;
    }

    public void startEditPercentageDialog(FragmentManager manager,TextView percentageTextView,TextView totalOrganizers, TextView totalCosts, TextView totalPercentage,TextView eventName,TextView eventDate,int task_id,int event_id, int status_of_update) {
        setTask_id(task_id);
        setPercentageTextView(percentageTextView);
        setTotalCosts(totalCosts);
        setTotalOrganizers(totalOrganizers);
        setTotalPercentage(totalPercentage);
        setStatus_of_update(status_of_update);
        setEventDate(eventDate);
        setEventName(eventName);
        setEvent_id(event_id);
        this.show(manager, "editPercentageDialog");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        if (CheckSharedPreferences.getInstance().isLoggedIn(getActivity())) {
                            DBfunctions.getInstance().updatePercentage(getActivity(), getPercentageTextView(),getTotalCosts(),getTotalPercentage(),getTotalOrganizers(),getEventName(),getEventDate(),getEvent_id(), getTask_id(), Integer.parseInt(CheckSharedPreferences.getInstance().getAdmin_id()), getPercentageValue(),getStatus_of_update());
                        } else {
                            CheckSharedPreferences.getInstance().endSession(getActivity());
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
    public int getPercentageValue() {
        return percentageValue;
    }
    public void setPercentageValue(int percentageValue) {
        this.percentageValue = percentageValue;
    }
    public TextView getPercentageTextView() {
        return percentageTextView;
    }
    public void setPercentageTextView(TextView percentageTextView) {
        this.percentageTextView = percentageTextView;
    }
    public TextView getTotalOrganizers() {
        return totalOrganizers;
    }
    public void setTotalOrganizers(TextView totalOrganizers) {
        this.totalOrganizers = totalOrganizers;
    }
    public TextView getTotalCosts() {
        return totalCosts;
    }
    public void setTotalCosts(TextView totalCosts) {
        this.totalCosts = totalCosts;
    }
    public TextView getTotalPercentage() {
        return totalPercentage;
    }
    public void setTotalPercentage(TextView totalPercentage) {
        this.totalPercentage = totalPercentage;
    }
    public int getStatus_of_update() {
        return status_of_update;
    }
    public void setStatus_of_update(int status_of_update) {
        this.status_of_update = status_of_update;
    }
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
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

}
