package thesis.hfu.eventme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.functions.CheckSharedPreferences;
import thesis.hfu.eventme.objects.User;

import java.util.ArrayList;

    public class EventOrganizersListAdapter extends
            RecyclerView.Adapter<EventOrganizersListAdapter.MyViewHolder> {

        private ArrayList<User> users;
        private Context context;
        private int position,event_id;

        public EventOrganizersListAdapter(Context context,ArrayList<User> list,int event_id) {
            this.users = list;
            this.context=context;
            this.event_id=event_id;
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
            final User user_b = this.users.get(position);

            viewHolder.nameTextView.setText(user_b.getName());
            viewHolder.prenameTextView.setText(user_b.getPrename());
            viewHolder.emailTextView.setText(user_b.getEmail());

            if(user_b.getStatus()==0){
                //User Not in Event
                viewHolder.addButton.setVisibility(View.VISIBLE);
                viewHolder.removeButton.setVisibility(View.GONE);

            }else if(user_b.getStatus()==1){
                //User already in Event
                viewHolder.addButton.setVisibility(View.GONE);
                viewHolder.removeButton.setVisibility(View.VISIBLE);

            }
            viewHolder.addButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setPosition(position);
                    if(CheckSharedPreferences.getInstance().isLoggedIn(context)){
                        DBfunctions.getInstance().addFriendToEvent(context, EventOrganizersListAdapter.this, getUserList(),getPosition(),getUserList().get(getPosition()).getUser_id(), getEvent_id());
                    }else {
                        CheckSharedPreferences.getInstance().endSession(context);
                    }
                }
            });

            viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setPosition(position);
                    if(CheckSharedPreferences.getInstance().isLoggedIn(context)){
                        DBfunctions.getInstance().removeFriendFromEvent(context, EventOrganizersListAdapter.this, getUserList(), getPosition(), getUserList().get(getPosition()).getUser_id(), getEvent_id());
                    }else {
                        CheckSharedPreferences.getInstance().endSession(context);
                    }
                }
            });
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            View itemView = LayoutInflater.from(arg0.getContext()).inflate(
                    R.layout.list_event_organizers_row, arg0, false);
            return new MyViewHolder(itemView);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements
                View.OnClickListener {

            TextView nameTextView, prenameTextView, emailTextView;
            ImageButton addButton, removeButton;

            public MyViewHolder(View itemView) {
                super(itemView);
                nameTextView = (TextView) itemView.findViewById(R.id.textViewListEventOrganizersRowName);
                prenameTextView = (TextView) itemView.findViewById(R.id.textViewListEventOrganizersRowPrename);
                emailTextView = (TextView) itemView.findViewById(R.id.textViewListEventOrganizersRowEmail);
                addButton= (ImageButton) itemView.findViewById(R.id.imageButtonAddToEvent);
                removeButton= (ImageButton) itemView.findViewById(R.id.imageButtonDeleteFromEvent);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
            }
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
        public int getEvent_id() {
            return event_id;
        }
    }