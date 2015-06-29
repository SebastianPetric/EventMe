package thesis.hfu.eventme.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.functions.CheckSharedPreferences;

public class EditPercentageDialog extends DialogFragment {

    private TextView percentageTextView, totalOrganizersTextView, totalCostsTextView, totalPercentageTextView, eventNameTextView, eventDateTextView,eventLocationTextView;

    //If there are the fields totalOrganizers,totalPercentage,etc from the event in this activity then the value of status_of_update is 1, otherwise it is 0
    private int task_id,event_id, percentageValue,status_of_update;
    private static EditPercentageDialog instance;
    private SwipeRefreshLayout syncRefresh;
    private ProgressBar progressBarEvent,progressBarTask;


    public static EditPercentageDialog getInstance(){
        if (EditPercentageDialog.instance == null){
            EditPercentageDialog.instance = new EditPercentageDialog();
        }
        return EditPercentageDialog.instance;
    }

    public void startEditPercentageDialog(FragmentManager manager,ProgressBar progressBarEvent,ProgressBar progressBarTask, SwipeRefreshLayout syncRefresh,TextView percentageTextView,TextView totalOrganizersTextView, TextView totalCostsTextView, TextView totalPercentageTextView,TextView eventNameTextView,TextView eventDateTextView,TextView eventLocationTextView,int task_id,int event_id, int status_of_update) {
        setTask_id(task_id);
        setPercentageTextView(percentageTextView);
        setTotalCostsTextView(totalCostsTextView);
        setTotalOrganizersTextView(totalOrganizersTextView);
        setTotalPercentageTextView(totalPercentageTextView);
        setStatus_of_update(status_of_update);
        setEventDateTextView(eventDateTextView);
        setEventNameTextView(eventNameTextView);
        setEventLocationTextView(eventLocationTextView);
        setEvent_id(event_id);
        setSyncRefresh(syncRefresh);
        setProgressBarEvent(progressBarEvent);
        setProgressBarTask(progressBarTask);
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
                            DBfunctions.getInstance().updatePercentage(getActivity(), getProgressBarEvent(),getProgressBarTask(),getSyncRefresh(),getPercentageTextView(), getTotalCostsTextView(), getTotalPercentageTextView(), getTotalOrganizersTextView(), getEventNameTextView(), getEventDateTextView(),getEventLocationTextView(),getEvent_id(), getTask_id(), CheckSharedPreferences.getInstance().getAdmin_id(), getPercentageValue(),getStatus_of_update());
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
    public TextView getTotalOrganizersTextView() {
        return totalOrganizersTextView;
    }
    public void setTotalOrganizersTextView(TextView totalOrganizersTextView) {
        this.totalOrganizersTextView = totalOrganizersTextView;
    }
    public TextView getTotalCostsTextView() {
        return totalCostsTextView;
    }
    public void setTotalCostsTextView(TextView totalCostsTextView) {
        this.totalCostsTextView = totalCostsTextView;
    }
    public TextView getTotalPercentageTextView() {
        return totalPercentageTextView;
    }
    public void setTotalPercentageTextView(TextView totalPercentageTextView) {
        this.totalPercentageTextView = totalPercentageTextView;
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
    public ProgressBar getProgressBarEvent() {
        return progressBarEvent;
    }
    public void setProgressBarEvent(ProgressBar progressBarEvent) {
        this.progressBarEvent = progressBarEvent;
    }
    public ProgressBar getProgressBarTask() {
        return progressBarTask;
    }
    public void setProgressBarTask(ProgressBar progressBarTask) {
        this.progressBarTask = progressBarTask;
    }
}
