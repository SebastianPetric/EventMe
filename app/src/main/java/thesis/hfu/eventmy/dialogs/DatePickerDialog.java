package thesis.hfu.eventmy.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import thesis.hfu.eventmy.objects.Global;

import java.sql.Date;
import java.util.Calendar;


    public class DatePickerDialog extends DialogFragment
            implements android.app.DatePickerDialog.OnDateSetListener {

        private static DatePickerDialog instance;
        private TextView eventDateTextView;
        private Context context;

        public static DatePickerDialog getInstance() {
            if (DatePickerDialog.instance == null) {
                DatePickerDialog.instance = new DatePickerDialog();
            }
            return DatePickerDialog.instance;
        }

        public void startDatePickerDialog(FragmentManager manager, Context context, final TextView eventDateTextView) {
            setEventDateTextView(eventDateTextView);
            setContext(context);
            this.show(manager, "datePickerDialog");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new android.app.DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date=day+"."+(month+1)+"."+ (year);
            setEventDateField(date);
            Global global=((Global)getContext().getApplicationContext());
            global.setDate(new Date((year),month,day));
        }

        public void setEventDateTextView(TextView eventDateTextView){
            this.eventDateTextView= eventDateTextView;
        }
        public void setEventDateField(String date) {
            this.eventDateTextView.setText(date);
        }
        public Context getContext() {
            return context;
        }
        public void setContext(Context context) {
            this.context = context;
        }
    }


