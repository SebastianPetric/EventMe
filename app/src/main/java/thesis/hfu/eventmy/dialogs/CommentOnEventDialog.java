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

public class CommentOnEventDialog extends DialogFragment {

    private static CommentOnEventDialog instance;
    private int admin_id,event_id;
    private TextView historyTextView;
    private SwipeRefreshLayout syncComments;
    private Context context;

    public static CommentOnEventDialog getInstance() {
        if (CommentOnEventDialog.instance == null) {
            CommentOnEventDialog.instance = new CommentOnEventDialog();
        }
        return CommentOnEventDialog.instance;
    }

    public void startCommentDialog(Context context,SwipeRefreshLayout syncComments,FragmentManager manager,int event_id,String admin_id,final TextView historyTextView) {
        setEvent_id(event_id);
        setAdmin_id(Integer.valueOf(admin_id));
        setHistoryTextView(historyTextView);
        setContext(context);
        setSyncComments(syncComments);
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
                        DBfunctions.getInstance().commentOnEvent(getContext(),getSyncComments(),getHistoryTextView(), getEvent_id(), getAdmin_id(), comment);
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


    public int getAdmin_id() {
        return admin_id;
    }
    public void setAdmin_id(int event_id) {
        this.admin_id = event_id;
    }
    public TextView getHistoryTextView(){
        return this.historyTextView;
    }
    public void setHistoryTextView(TextView historyTextView) {
        this.historyTextView = historyTextView;
    }
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public SwipeRefreshLayout getSyncComments() {
        return syncComments;
    }
    public void setSyncComments(SwipeRefreshLayout syncComments) {
        this.syncComments = syncComments;
    }
}