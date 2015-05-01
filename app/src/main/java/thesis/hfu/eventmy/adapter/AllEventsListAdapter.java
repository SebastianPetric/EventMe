package thesis.hfu.eventmy.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.objects.Event;

import java.util.ArrayList;

public class AllEventsListAdapter extends
        RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> {

    private ArrayList<Event> eventList;
    private Context context;

    public AllEventsListAdapter(Context context,ArrayList<Event> list) {
        this.eventList = list;
        this.context=context;
    }
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final Event event = this.eventList.get(position);

        viewHolder.name.setText(event.getName());
        viewHolder.location.setText(event.getLocation());
        viewHolder.date.setText(event.getDate().getDate()+"."+(event.getDate().getMonth()+1)+"."+event.getDate().getYear());
        viewHolder.costs.setText(String.valueOf(event.getCosts()));
        viewHolder.numberOrganizers.setText(String.valueOf(event.getNumOrganizers()));
        viewHolder.Percentage.setText(String.valueOf(event.getPercentage_of_event()));
        viewHolder.addOrganizersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityFunctions.getInstance().startEventOrganizersActivity(context.getApplicationContext(),event.getEvent_id());
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View itemView = LayoutInflater.from(arg0.getContext()).inflate(
                R.layout.list_event_row, arg0, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener,View.OnLongClickListener {

        private TextView name,location,date,numberOrganizers,costs,Percentage;
        private ImageButton addOrganizersButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textViewListRowEventName);
            location = (TextView) itemView.findViewById(R.id.textViewListRowEventLocation);
            date= (TextView) itemView.findViewById(R.id.textViewListRowEventDate);
            numberOrganizers= (TextView) itemView.findViewById(R.id.textViewListRowEventOrganizer);
            costs= (TextView) itemView.findViewById(R.id.textViewListRowEventCosts);
            Percentage= (TextView) itemView.findViewById(R.id.textViewlistRowEventPercentage);
            addOrganizersButton= (ImageButton) itemView.findViewById(R.id.imageButtonListRowEventOrganizer);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            StartActivityFunctions.getInstance().startAllTasksActivity(context.getApplicationContext(), eventList.get(getPosition()).getEvent_id());
        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage(R.string.dialog_delete_task_message)
                    .setNeutralButton(R.string.dialog_edit_event_archiv, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (CheckSharedPreferences.getInstance().isLoggedIn(context)) {
                                        DBfunctions.getInstance().archivEvent(context, getEventList(), AllEventsListAdapter.this, getPosition(), CheckSharedPreferences.getInstance().getAdmin_id(), eventList.get(getPosition()).getEvent_id());
                                    } else {
                                        CheckSharedPreferences.getInstance().endSession(context);
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
                                        if (CheckSharedPreferences.getInstance().isLoggedIn(context)) {
                                            DBfunctions.getInstance().deleteEvent(context, getEventList(), AllEventsListAdapter.this, getPosition(), CheckSharedPreferences.getInstance().getAdmin_id(), eventList.get(getPosition()).getEvent_id());
                                        } else {
                                            CheckSharedPreferences.getInstance().endSession(context);
                                        }
                                    }
                                }

                        );
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                        return false;
                    }
        }
    public ArrayList<Event> getEventList(){
        return this.eventList;
    }
}
