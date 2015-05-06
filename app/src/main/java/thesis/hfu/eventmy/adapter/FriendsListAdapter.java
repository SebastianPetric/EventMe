package thesis.hfu.eventmy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.database.DBfunctions;
import thesis.hfu.eventmy.functions.CheckSharedPreferences;
import thesis.hfu.eventmy.objects.User;

import java.util.ArrayList;

public class FriendsListAdapter extends
        RecyclerView.Adapter<FriendsListAdapter.MyViewHolder> {

    private ArrayList<User> users;
    private Context context;
    private int position;

    public FriendsListAdapter(Context context,ArrayList<User> list) {
        this.users = list;
        this.context = context;
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
            viewHolder.addButton.setVisibility(View.GONE);
            viewHolder.removeButton.setVisibility(View.GONE);
            viewHolder.openButton.setVisibility(View.VISIBLE);
        }else if(user_b.getStatus()==2){
            viewHolder.addButton.setVisibility(View.GONE);
            viewHolder.removeButton.setVisibility(View.VISIBLE);
            viewHolder.openButton.setVisibility(View.GONE);
        }else if(user_b.getStatus()==3){
            viewHolder.addButton.setVisibility(View.VISIBLE);
            viewHolder.removeButton.setVisibility(View.VISIBLE);
            viewHolder.openButton.setVisibility(View.GONE);
        }
        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPosition(position);
                if(CheckSharedPreferences.getInstance().isLoggedIn(context)){
                    DBfunctions.getInstance().addFriendToFriendslist(context, FriendsListAdapter.this, getUserList(), getPosition(), getUserList().get(getPosition()).getUser_id(), CheckSharedPreferences.getInstance().getAdmin_id());
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
                    DBfunctions.getInstance().removeFriendFromFriendsList(context, FriendsListAdapter.this, getUserList(), getPosition(), getUserList().get(getPosition()).getUser_id(), CheckSharedPreferences.getInstance().getAdmin_id());
                }else {
                    CheckSharedPreferences.getInstance().endSession(context);
                }
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View itemView = LayoutInflater.from(arg0.getContext()).inflate(
                R.layout.list_friendslist_row, arg0, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        TextView name;
        TextView prename;
        TextView email;
        ImageButton removeButton,addButton,openButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textViewFriendsListRowName);
            prename = (TextView) itemView.findViewById(R.id.textViewFriendsListRowPrename);
            email= (TextView) itemView.findViewById(R.id.textViewFriendsListRowEmail);
            removeButton= (ImageButton) itemView.findViewById(R.id.imageButtonDeleteFromFriendsList);
            addButton= (ImageButton) itemView.findViewById(R.id.imageButtonAddToFriendsList);
            openButton= (ImageButton) itemView.findViewById(R.id.imageButtonOpenRequestFriendsList);
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
}