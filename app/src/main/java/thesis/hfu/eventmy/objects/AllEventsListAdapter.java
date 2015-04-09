package thesis.hfu.eventmy.objects;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thesis.hfu.eventmy.R;

public class AllEventsListAdapter extends
        RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> {

    private ArrayList<Event> events;
    private Context context;
    private static final String MESSAGE = "message";

    private static final String URL_GET_ALL_EVENTS= "get_all_events.php";

    public AllEventsListAdapter(Context context,ArrayList<Event> list) {
        this.events = list;
        this.context=context;
    }
    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final Event event = this.events.get(position);

        viewHolder.name.setText(event.getName());
        viewHolder.location.setText(event.getLocation());
        viewHolder.date.setText(event.getDate().getDay()+"."+event.getDate().getMonth()+"."+event.getDate().getYear());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View itemView = LayoutInflater.from(arg0.getContext()).inflate(
                R.layout.list_event_row, arg0, false);
        return new MyViewHolder(itemView);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private TextView name,location,date,numberOrganizers,Costs,Percentage;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textViewListRowEventName);
            location = (TextView) itemView.findViewById(R.id.textViewListRowEventLocation);
            date= (TextView) itemView.findViewById(R.id.textViewListRowEventDate);
            numberOrganizers= (TextView) itemView.findViewById(R.id.textViewListRowEventOrganizer);
            Costs= (TextView) itemView.findViewById(R.id.textViewListRowEventCosts);
            Percentage= (TextView) itemView.findViewById(R.id.textViewlistRowEventPercentage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),
                    "Klick " + events.get(getPosition()).getName(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
