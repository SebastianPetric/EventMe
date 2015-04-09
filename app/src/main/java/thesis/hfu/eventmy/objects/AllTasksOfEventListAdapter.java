package thesis.hfu.eventmy.objects;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import thesis.hfu.eventmy.R;

import java.util.ArrayList;


public class AllTasksOfEventListAdapter extends
        RecyclerView.Adapter<AllTasksOfEventListAdapter.MyViewHolder> {

    private ArrayList<Task> tasks;
    private Context context;

    public AllTasksOfEventListAdapter(Context context,ArrayList<Task> list) {
        this.tasks = list;
        this.context=context;
    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final Task task = this.tasks.get(position);

        viewHolder.task.setText(task.getTask());
        viewHolder.quantity.setText(task.getQuantity());
        viewHolder.costField.setText(String.valueOf(task.getCostOfTask()));
        viewHolder.percentageField.setText(String.valueOf(task.getPercentage()));
        viewHolder.editorField.setText(task.getEditor_name());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View itemView = LayoutInflater.from(arg0.getContext()).inflate(
                R.layout.list_task_of_event_row, arg0, false);
        return new MyViewHolder(itemView);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        ImageButton costButton,percentageButton,editorButton;
        TextView costField,percentageField,editorField,task,quantity;

        public MyViewHolder(View itemView) {
            super(itemView);

            costButton = (ImageButton) itemView.findViewById(R.id.imageButtonListRowTaskOfEventCosts);
            percentageButton = (ImageButton) itemView.findViewById(R.id.imageButtonListRowTaskOfEventPercentage);
            editorButton = (ImageButton) itemView.findViewById(R.id.imageButtonlistRowTaskOfEventPercentage);
            costField = (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventOrganizer);
            percentageField= (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventCosts);
            editorField= (TextView) itemView.findViewById(R.id.textViewlistRowTaskOfEventPercentage);
            task= (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventName);
            quantity= (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

           // Intent intent= new Intent(v.getContext(), CreateTaskActivity.class);
           // intent.putExtra(EVENT_ID,events.get(getPosition()).getEvent_id());
           // v.getContext().startActivity(intent);
        }
    }
}