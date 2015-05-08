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
import android.widget.ProgressBar;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;

public class CommentOnTaskDialog extends DialogFragment {

    private static CommentOnTaskDialog instance;
    private int task_id,admin_id;
    private Context context;
    private TextView taskNameTextView, eventNameTextView, taskQuantityTextView, costsTextView, percentageTextView, editorTextView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout syncRefresh;
    private ProgressBar progressBarTask;

    public static CommentOnTaskDialog getInstance() {
        if (CommentOnTaskDialog.instance == null) {
            CommentOnTaskDialog.instance = new CommentOnTaskDialog();
        }
        return CommentOnTaskDialog.instance;
    }

    public void startCommentDialog(FragmentManager manager,ProgressBar progressBarTask,SwipeRefreshLayout syncRefresh,final RecyclerView recyclerView,Context context, int task_id,String admin_id,final TextView eventNameTextView,final TextView taskFieldTextView, final TextView quantityTextView, final TextView costTextView, TextView percentageTextView, final TextView editorTextView) {
        setTask_id(task_id);
        setAdmin_id(Integer.valueOf(admin_id));
        setContext(context);
        setEventNameTextView(eventNameTextView);
        setTaskNameTextView(taskFieldTextView);
        setTaskQuantityTextView(quantityTextView);
        setCostsTextView(costTextView);
        setPercentageTextView(percentageTextView);
        setEditorTextView(editorTextView);
        setRecyclerView(recyclerView);
        setSyncRefresh(syncRefresh);
        setProgressBarTask(progressBarTask);
        this.show(manager, "commentDialog");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.dialog_comment, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextCommentUserInput);

        builder.setView(promptsView);
        builder.setCancelable(false)
                .setPositiveButton(R.string.dialog_comment_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String comment = userInput.getText().toString().trim();
                        DBfunctions.getInstance().commentOnTask(getContext(),getProgressBarTask(), getSyncRefresh(),getRecyclerView(),getEventNameTextView(), getTaskTextView(),getQuantityTextView(),getCostsTextView(),getPercentageTextView(),getEditorTextView(),getTask_id(),getAdmin_id(),comment);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.dialog_comment_cancel, new DialogInterface.OnClickListener() {
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
    public int getAdmin_id() {
        return admin_id;
    }
    public void setAdmin_id(int event_id) {
        this.admin_id = event_id;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public TextView getEventNameTextView(){
        return this.eventNameTextView;
    }
    public TextView getTaskTextView(){
        return this.taskNameTextView;
    }
    public TextView getQuantityTextView(){
        return this.taskQuantityTextView;
    }
    public TextView getCostsTextView(){
        return this.costsTextView;
    }
    public TextView getPercentageTextView(){
        return this.percentageTextView;
    }
    public TextView getEditorTextView(){
        return this.editorTextView;
    }
    public void setTaskNameTextView(TextView taskNameTextView) {
        this.taskNameTextView = taskNameTextView;
    }
    public void setEventNameTextView(TextView eventNameTextView) {
        this.eventNameTextView = eventNameTextView;
    }
    public void setTaskQuantityTextView(TextView taskQuantityTextView) {
        this.taskQuantityTextView = taskQuantityTextView;
    }
    public void setCostsTextView(TextView costsTextView) {
        this.costsTextView = costsTextView;
    }
    public void setPercentageTextView(TextView percentageTextView) {
        this.percentageTextView = percentageTextView;
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
    public ProgressBar getProgressBarTask() {
        return progressBarTask;
    }
    public void setProgressBarTask(ProgressBar progressBarTask) {
        this.progressBarTask = progressBarTask;
    }
}