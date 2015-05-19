package thesis.hfu.eventme.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.dialogs.LongClickEventDialog;
import thesis.hfu.eventme.functions.StartActivity;
import thesis.hfu.eventme.objects.Event;

import java.util.ArrayList;

public class AllEventsListAdapter extends
        RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> {

    private ArrayList<Event> eventList;
    private Context context;
    private FragmentManager fragmentManager;

    public AllEventsListAdapter(Activity context,ArrayList<Event> list) {
        this.eventList = list;
        this.context=context;
        this.fragmentManager=context.getFragmentManager();
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final Event event = this.eventList.get(position);

        viewHolder.nameTextView.setText(event.getName());
        viewHolder.locationTextView.setText(event.getLocation());
        viewHolder.dateTextView.setText(event.getDate().getDate() + "." + (event.getDate().getMonth() + 1) + "." + event.getDate().getYear());
        viewHolder.costsTextView.setText(String.valueOf(event.getCosts()));
        viewHolder.numberOrganizersTextView.setText(String.valueOf(event.getNumOrganizers()));
        viewHolder.percentageTextView.setText(String.valueOf(event.getPercentage_of_event()));
        viewHolder.progressBarEvent.setProgress((int)event.getPercentage_of_event());
        viewHolder.addOrganizersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivity.getInstance().startEventOrganizersActivity(context.getApplicationContext(),event.getEvent_id());
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

        private TextView nameTextView, locationTextView, dateTextView, numberOrganizersTextView, costsTextView, percentageTextView;
        private ImageButton addOrganizersButton;
        private ProgressBar progressBarEvent;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.textViewListRowEventName);
            locationTextView = (TextView) itemView.findViewById(R.id.textViewListRowEventLocation);
            dateTextView = (TextView) itemView.findViewById(R.id.textViewListRowEventDate);
            numberOrganizersTextView = (TextView) itemView.findViewById(R.id.textViewListRowEventOrganizer);
            costsTextView = (TextView) itemView.findViewById(R.id.textViewListRowEventCosts);
            percentageTextView = (TextView) itemView.findViewById(R.id.textViewlistRowEventPercentage);
            addOrganizersButton= (ImageButton) itemView.findViewById(R.id.imageButtonListRowEventOrganizer);
            progressBarEvent= (ProgressBar) itemView.findViewById(R.id.progressBarEvent);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            StartActivity.getInstance().startAllTasksActivity(context.getApplicationContext(), eventList.get(getPosition()).getEvent_id());
        }

        @Override
        public boolean onLongClick(View v) {
            LongClickEventDialog.getInstance().startLongClickEventDialog(getFragmentManager(),getEventList(),AllEventsListAdapter.this,getEventList().get(getPosition()).getEvent_id(),getPosition());
            return false;
            }
        }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public ArrayList<Event> getEventList(){
        return this.eventList;
    }
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
}
