package thesis.hfu.eventmy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;


public class LogoutDialog extends DialogFragment {

    private static LogoutDialog instance;

    public static LogoutDialog getInstance() {

        if (LogoutDialog.instance == null) {
            LogoutDialog.instance = new LogoutDialog();
        }
        return LogoutDialog.instance;
    }

    public void startLogoutDialog(FragmentManager manager) {
        LogoutDialog dialog = new LogoutDialog();
        dialog.show(manager, "logoutDialog");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_logout_message)
                .setPositiveButton(R.string.dialog_logout_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        CheckSharedPreferences.getInstance().endSession(getActivity());
                    }
                })
                .setNegativeButton(R.string.dialog_logout_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}
