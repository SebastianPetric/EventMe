package thesis.hfu.eventmy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;

public class EditTaskDialog extends DialogFragment {

    private static EditTaskDialog instance;
    private Context context;
    private TextView taskNameField,quantityField;
    private String editor_id;
    private int task_id;

    public static EditTaskDialog getInstance() {

        if (EditTaskDialog.instance == null) {
            EditTaskDialog.instance = new EditTaskDialog();
        }
        return EditTaskDialog.instance;
    }

    public void startEditTaskDialog(FragmentManager manager, Context context, TextView taskNameField, TextView quantityField,int task_id,String editor_id) {
        setContext(context);
        setTaskNameField(taskNameField);
        setQuantityField(quantityField);
        setTask_id(task_id);
        setEditor_id(editor_id);
        this.show(manager, "editTaskDialog");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.dialog_edit_task, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText changeName = (EditText) promptsView.findViewById(R.id.editTextEditTaskChangeTaskNameField);
        final EditText changeQuantity = (EditText) promptsView.findViewById(R.id.editTextEditTaskChangeQuantityField);

        builder.setView(promptsView);
        builder.setCancelable(false)
                .setPositiveButton(R.string.dialog_edit_task_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = changeName.getText().toString().trim();
                        String quantity = changeQuantity.getText().toString().trim();

                        DBfunctions.getInstance().updateTaskNameQuantity(getContext(),getTaskNameField(),getQuantityField(),getTask_id(),getEditor_id(),quantity,name);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.dialog_edit_task_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public TextView getTaskNameField() {
        return taskNameField;
    }
    public void setTaskNameField(TextView taskNameField) {
        this.taskNameField = taskNameField;
    }
    public TextView getQuantityField() {
        return quantityField;
    }
    public void setQuantityField(TextView quantityField) {
        this.quantityField = quantityField;
    }
    public String getEditor_id() {
        return editor_id;
    }
    public void setEditor_id(String editor_id) {
        this.editor_id = editor_id;
    }
    public int getTask_id() {
        return task_id;
    }
    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
}
