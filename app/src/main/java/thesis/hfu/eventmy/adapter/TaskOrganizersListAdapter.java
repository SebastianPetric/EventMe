package thesis.hfu.eventmy.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.functions.StartActivityFunctions;
import thesis.hfu.eventmy.objects.Global;
import thesis.hfu.eventmy.objects.User;

import java.util.ArrayList;

public class TaskOrganizersListAdapter extends
        RecyclerView.Adapter<TaskOrganizersListAdapter.MyViewHolder> {

    private ArrayList<User> users;
    private Context context;
    private int position, event_id;
    private Activity activity;

    public TaskOrganizersListAdapter(Activity activity,Context context,ArrayList<User> list, int event_id) {
        this.users = list;
        this.context=context;
        this.activity=activity;
        this.event_id=event_id;
    }
    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final User user_b = this.users.get(position);

        viewHolder.name.setText(user_b.getName());
        viewHolder.prename.setText(user_b.getPrename());
        viewHolder.email.setText(user_b.getEmail());
        viewHolder.addButton.setVisibility(View.VISIBLE);
        viewHolder.removeButton.setVisibility(View.GONE);

        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPosition(position);

                if(CheckSharedPreferences.getInstance().isLoggedIn(context)){
                    addFriendToTask(getPosition());
                }else {
                    CheckSharedPreferences.getInstance().endSession(context);
                }
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View itemView = LayoutInflater.from(arg0.getContext()).inflate(
                R.layout.list_event_task_organizers_row, arg0, false);
        return new MyViewHolder(itemView);
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        TextView name;
        TextView prename;
        TextView email;
        ImageButton addButton, removeButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textViewListEventOrganizersRowName);
            prename = (TextView) itemView.findViewById(R.id.textViewListEventOrganizersRowPrename);
            email= (TextView) itemView.findViewById(R.id.textViewListEventOrganizersRowEmail);
            addButton= (ImageButton) itemView.findViewById(R.id.imageButtonAddToEvent);
            removeButton= (ImageButton) itemView.findViewById(R.id.imageButtonDeleteFromEvent);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

    //----------------------------------------------------------------------
    //-----------------Functions-------------------------------------
    //----------------------------------------------------------------------

    public void addFriendToTask(int position){

        final User user_b = getUserList().get(position);
        Global global = ((Global)context.getApplicationContext());
        global.setEditor_id(user_b.getUser_id());
        global.setEditorName(user_b.getName());
        StartActivityFunctions.getInstance().startCreateTaskFromFriendsActivity(context.getApplicationContext(), getEvent_id());
        activity.finish();
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public ArrayList<User> getUserList(){
        return this.users;
    }
}