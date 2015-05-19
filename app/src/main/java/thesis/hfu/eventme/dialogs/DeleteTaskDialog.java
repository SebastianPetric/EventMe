package thesis.hfu.eventme.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.functions.CheckSharedPreferences;

public class DeleteTaskDialog extends DialogFragment {

    private static DeleteTaskDialog instance;
    int task_id,event_id;

    public static DeleteTaskDialog getInstance() {
        if (DeleteTaskDialog.instance == null) {
            DeleteTaskDialog.instance = new DeleteTaskDialog();
        }
        return DeleteTaskDialog.instance;
    }

    public void startDeleteTaskDialog(FragmentManager manager, int task_id,int event_id) {
        setTask_id(task_id);
        setEvent_id(event_id);
        this.show(manager, "deleteTaskDialog");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_task_message)
                .setPositiveButton(R.string.dialog_delete_task_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(CheckSharedPreferences.getInstance().isLoggedIn(getActivity())) {
                            DBfunctions.getInstance().deleteTask(getActivity(),getTask_id(),CheckSharedPreferences.getInstance().getAdmin_id(),getEvent_id());
                        }else{
                            CheckSharedPreferences.getInstance().endSession(getActivity());
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_delete_task_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
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
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}