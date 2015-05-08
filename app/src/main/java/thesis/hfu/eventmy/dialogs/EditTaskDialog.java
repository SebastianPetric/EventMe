package thesis.hfu.eventmy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;

public class EditTaskDialog extends DialogFragment {

    private static EditTaskDialog instance;
    private Context context;
    private TextView taskNameTextView, quantityTextView, eventNameTextView, costsTextView,percentageTextView,editorTextView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout syncRefresh;
    private String editor_id;
    private int task_id;

    public static EditTaskDialog getInstance() {
        if (EditTaskDialog.instance == null) {
            EditTaskDialog.instance = new EditTaskDialog();
        }
        return EditTaskDialog.instance;
    }

    public void startEditTaskDialog(FragmentManager manager, Context context,SwipeRefreshLayout syncRefresh, RecyclerView recyclerView, TextView taskNameTextView, TextView quantityTextView,final TextView eventNameTextView, final TextView costsTextView, final TextView percentageTextView, final TextView editorTextView,int task_id,String editor_id) {
        setContext(context);
        setTaskNameTextView(taskNameTextView);
        setQuantityTextView(quantityTextView);
        setTask_id(task_id);
        setEditor_id(editor_id);
        setEventNameTextView(eventNameTextView);
        setCostsTextView(costsTextView);
        setPercentageTextView(percentageTextView);
        setEditorTextView(editorTextView);
        setRecyclerView(recyclerView);
        setSyncRefresh(syncRefresh);
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
                        DBfunctions.getInstance().updateTaskNameQuantity(getContext(),getSyncRefresh(),getRecyclerView(), getTaskNameTextView(), getQuantityTextView(), getEventNameTextView(),getCostsTextView(),getPercentageTextView(),getEditorTextView(),getTask_id(),getEditor_id(),quantity,name);
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
    public TextView getTaskNameTextView() {
        return taskNameTextView;
    }
    public void setTaskNameTextView(TextView taskNameTextView) {
        this.taskNameTextView = taskNameTextView;
    }
    public TextView getQuantityTextView() {
        return quantityTextView;
    }
    public void setQuantityTextView(TextView quantityTextView) {
        this.quantityTextView = quantityTextView;
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
    public TextView getEventNameTextView() {
        return eventNameTextView;
    }
    public void setEventNameTextView(TextView eventNameTextView) {
        this.eventNameTextView = eventNameTextView;
    }
    public TextView getCostsTextView() {
        return costsTextView;
    }
    public void setCostsTextView(TextView costsTextView) {
        this.costsTextView = costsTextView;
    }
    public TextView getPercentageTextView() {
        return percentageTextView;
    }
    public void setPercentageTextView(TextView percentageTextView) {
        this.percentageTextView = percentageTextView;
    }
    public TextView getEditorTextView() {
        return editorTextView;
    }
    public void setEditorTextView(TextView editorTextView) {
        this.editorTextView = editorTextView;
    }
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
    public SwipeRefreshLayout getSyncRefresh() {
        return syncRefresh;
    }
    public void setSyncRefresh(SwipeRefreshLayout syncRefresh) {
        this.syncRefresh = syncRefresh;
    }
}
