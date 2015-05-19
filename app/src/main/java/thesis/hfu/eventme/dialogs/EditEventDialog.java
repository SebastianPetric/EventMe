package thesis.hfu.eventme.dialogs;

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
import android.widget.ImageButton;
import android.widget.TextView;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.functions.CheckSharedPreferences;
import thesis.hfu.eventme.objects.Global;

import java.sql.Date;

public class EditEventDialog extends DialogFragment {

    private static EditEventDialog instance;
    private Context context;
    private TextView eventDateDialogTextView,eventNameTextView,eventDateTextView,eventLocationTextView;
    private int event_id;

    public static EditEventDialog getInstance() {
        if (EditEventDialog.instance == null) {
            EditEventDialog.instance = new EditEventDialog();
        }
        return EditEventDialog.instance;
    }

    public void startEditEventDialog(FragmentManager manager, Context context, final TextView eventDateDialogTextView,final TextView eventNameTextView, final TextView eventDateTextView,final TextView eventLocationTextView, int event_id) {
        Global global= (Global) context.getApplicationContext();
        global.setDate(null);
        setContext(context);
        setEvent_id(event_id);
        setEventDateDialogTextView(eventDateDialogTextView);
        setEventNameTextView(eventNameTextView);
        setEventDateTextView(eventDateTextView);
        setEventLocationTextView(eventLocationTextView);
        this.show(manager, "editEventDialog");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.dialog_edit_event, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText changeName = (EditText) promptsView.findViewById(R.id.editTextEditEventChangeEventNameField);
        final EditText changeLocation = (EditText) promptsView.findViewById(R.id.editTextEditEventChangeLocationField);
        this.eventDateDialogTextView = (TextView) promptsView.findViewById(R.id.textViewNewEventDateField);
        final ImageButton dateButton= (ImageButton) promptsView.findViewById(R.id.imageButtonNewEventDate);
        dateButton.setOnClickListener(new CustomClickListener());
        builder.setView(promptsView);
        builder.setCancelable(false)
                .setPositiveButton(R.string.edit_event_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String name = changeName.getText().toString().trim();
                        String location = changeLocation.getText().toString().trim();
                        Global global= (Global) context.getApplicationContext();
                        Date date=global.getDate();
                        if(CheckSharedPreferences.getInstance().isLoggedIn(context.getApplicationContext())) {
                            DBfunctions.getInstance().editEventDetails(context.getApplicationContext(), getEventNameTextView(), getEventDateTextView(), getEventLocationTextView(), CheckSharedPreferences.getInstance().getAdmin_id(), getEvent_id(), name, location, date);
                        }else{
                         CheckSharedPreferences.getInstance().endSession(context.getApplicationContext());
                        }
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.edit_event_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    //----------------------------------------------------------------------
    //-----------------CUSTOM LISTENER-------------------------------------
    //----------------------------------------------------------------------

    public class CustomClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.imageButtonNewEventDate){
                DatePickerDialog.getInstance().startDatePickerDialog(getFragmentManager(),context.getApplicationContext(), getEventDateDialogTextView());
            }
        }
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
    public TextView getEventDateDialogTextView() {
        return eventDateDialogTextView;
    }
    public void setEventDateDialogTextView(TextView eventDateDialogTextView) {
        this.eventDateDialogTextView = eventDateDialogTextView;
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
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}
