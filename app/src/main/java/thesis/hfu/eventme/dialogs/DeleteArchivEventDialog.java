package thesis.hfu.eventme.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.functions.CheckSharedPreferences;

public class DeleteArchivEventDialog extends DialogFragment {

    private static DeleteArchivEventDialog instance;
    int event_id;
    private Context context;

    public static DeleteArchivEventDialog getInstance() {
        if (DeleteArchivEventDialog.instance == null) {
            DeleteArchivEventDialog.instance = new DeleteArchivEventDialog();
        }
        return DeleteArchivEventDialog.instance;
    }

    public void startDeleteArchivEventDialog(FragmentManager manager, Context context, final int event_id) {
        setEvent_id(event_id);
        setContext(context);
        this.show(manager, "deleteArchivEventDialog");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_edit_event_message)
                .setNeutralButton(R.string.dialog_edit_event_archiv, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (CheckSharedPreferences.getInstance().isLoggedIn(getActivity())) {
                                    DBfunctions.getInstance().archiveEvent(context, CheckSharedPreferences.getInstance().getAdmin_id(), getEvent_id());
                                } else {
                                    CheckSharedPreferences.getInstance().endSession(getActivity());
                                }
                            }
                        }
                )
                .setNegativeButton(R.string.dialog_edit_event_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                )
                .setPositiveButton(R.string.dialog_edit_event_delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (CheckSharedPreferences.getInstance().isLoggedIn(getActivity())) {
                                    DBfunctions.getInstance().deleteEvent(context,CheckSharedPreferences.getInstance().getAdmin_id(),getEvent_id());
                                     } else {
                                    CheckSharedPreferences.getInstance().endSession(getActivity());
                                }
                            }
                        }
                );
        return builder.create();
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public void setContext(Context context){
        this.context=context;
    }
    public Context getContext(){
        return this.context;
    }
}
