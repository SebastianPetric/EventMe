package thesis.hfu.eventmy.objects;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBconnection;
import thesis.hfu.eventmy.functions.BuildJSON;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;

import java.util.ArrayList;


    public class EventOrganizersListAdapter extends
            RecyclerView.Adapter<EventOrganizersListAdapter.MyViewHolder> {

        private ArrayList<User> users;
        private Context context;
        private static final String MESSAGE = "message";
        private static final String STATUS = "status";
        private MyViewHolder viewHolder;
        private int position,event_id;

        //Remove Friend
        private static final String URL_REMOVE_FRIEND_FROM_EVENT = "remove_friend_from_event.php";

        //Friend Request
        private static final String URL_FRIEND_TO_EVENT = "add_friend_to_event.php";

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

            viewHolder.name.setText(user_b.getName());
            viewHolder.prename.setText(user_b.getPrename());
            viewHolder.email.setText(user_b.getEmail());

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
                    setViewHolder(viewHolder);

                    if(CheckSharedPreferences.getInstance().isLoggedIn(context)){
                        addFriendToEvent(getUserList().get(getPosition()).getUser_id(),getEvent_id());
                    }else {
                        CheckSharedPreferences.getInstance().endSession(context);
                    }
                }
            });

            viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setPosition(position);
                    setViewHolder(viewHolder);

                    if(CheckSharedPreferences.getInstance().isLoggedIn(context)){
                       removeFriendFromEvent(getUserList().get(getPosition()).getUser_id(),getEvent_id());
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
            /*Toast.makeText(v.getContext(),
                    "Klick " + users.get(getPosition()).getName(),
                    Toast.LENGTH_SHORT).show();*/
            }
        }

        //----------------------------------------------------------------------
        //-----------------Functions-------------------------------------
        //----------------------------------------------------------------------

        public void addFriendToEvent(int user_id,int event_id){

            RequestParams params = BuildJSON.getInstance().addRemoveFriendToEventJSON(user_id, event_id);
            DBconnection.post(URL_FRIEND_TO_EVENT, params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                        if (response.getInt(STATUS) == 200) {
                            final User user_b = getUserList().get(getPosition());

                                // User wird hinzugefügt
                                user_b.setStatus(1);
                                getViewHolder().addButton.setVisibility(View.GONE);
                                getViewHolder().removeButton.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void removeFriendFromEvent(int user_id,int event_id) {

            RequestParams params = BuildJSON.getInstance().addRemoveFriendToEventJSON(user_id, event_id);
            DBconnection.post(URL_REMOVE_FRIEND_FROM_EVENT, params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                        if (response.getInt(STATUS) == 200) {
                            final User user_b = getUserList().get(getPosition());

                                //User wird entfernt
                                user_b.setStatus(0);
                                getViewHolder().addButton.setVisibility(View.VISIBLE);
                                getViewHolder().removeButton.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        //----------------------------------------------------------------------
        //-----------------Getter and Setter-------------------------------------
        //----------------------------------------------------------------------

        public MyViewHolder getViewHolder() {
            return viewHolder;
        }
        public void setViewHolder(MyViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }
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