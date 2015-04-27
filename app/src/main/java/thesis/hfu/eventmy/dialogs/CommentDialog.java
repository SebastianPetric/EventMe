package thesis.hfu.eventmy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;

public class CommentDialog extends DialogFragment {

    private static CommentDialog instance;
    private int task_id,admin_id;
    private Context context;
    private TextView taskName, eventName, taskQuantity, costsField,percentageField,editorField, historyField;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static CommentDialog getInstance() {

        if (CommentDialog.instance == null) {
            CommentDialog.instance = new CommentDialog();
        }
        return CommentDialog.instance;
    }

    public void startCommentDialog(FragmentManager manager,final SwipeRefreshLayout swipeRefreshLayout,Context context, int task_id,String admin_id,final TextView eventName,final TextView taskField, final TextView quantity, final TextView cost, TextView percentage, final TextView editor, final TextView history) {
        setTask_id(task_id);
        setAdmin_id(Integer.valueOf(admin_id));
        setContext(context);
        setEventName(eventName);
        setTaskName(taskField);
        setTaskQuantity(quantity);
        setCostsField(cost);
        setPercentageField(percentage);
        setEditorField(editor);
        setHistoryField(history);
        setSwipeRefreshLayout(swipeRefreshLayout);
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
                        DBfunctions.getInstance().commentOnTask(getContext(),getTask_id(),getAdmin_id(),comment);
                        DBfunctions.getInstance().updateTaskDetails(getContext(),getSwipeRefreshLayout(),getEventNameTextView(),getTaskTextView(),getQuantityTextView(),getCostsTextView(),getPercentageTextView(),getEditorTextView(),getHistoryTextView(),getTask_id());
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
        return this.eventName;
    }
    public TextView getTaskTextView(){
        return this.taskName;
    }
    public TextView getQuantityTextView(){
        return this.taskQuantity;
    }
    public TextView getCostsTextView(){
        return this.costsField;
    }
    public TextView getPercentageTextView(){
        return this.percentageField;
    }
    public TextView getEditorTextView(){
        return this.editorField;
    }
    public TextView getHistoryTextView(){
        return this.historyField;
    }
    public void setTaskName(TextView taskName) {
        this.taskName = taskName;
    }
    public void setEventName(TextView eventName) {
        this.eventName = eventName;
    }
    public void setTaskQuantity(TextView taskQuantity) {
        this.taskQuantity = taskQuantity;
    }
    public void setCostsField(TextView costsField) {
        this.costsField = costsField;
    }
    public void setPercentageField(TextView percentageField) {
        this.percentageField = percentageField;
    }
    public void setEditorField(TextView editorField) {
        this.editorField = editorField;
    }
    public void setHistoryField(TextView historyField) {
        this.historyField = historyField;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }
    public SwipeRefreshLayout getSwipeRefreshLayout() {
       return this.swipeRefreshLayout;
    }
}