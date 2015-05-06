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

public class SearchListAdapter extends
        RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {

    private ArrayList<User> users;
    private Context context;
    private int position;

    public SearchListAdapter(Context context,ArrayList<User> list) {
        this.users = list;
        this.context=context;
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
            //Request is open
            viewHolder.addButton.setVisibility(View.GONE);
            viewHolder.removeButton.setVisibility(View.GONE);
            viewHolder.openRequest.setVisibility(View.VISIBLE);

        }else if(user_b.getStatus()==1){
            //No Request
            viewHolder.addButton.setVisibility(View.VISIBLE);
            viewHolder.removeButton.setVisibility(View.GONE);
            viewHolder.openRequest.setVisibility(View.GONE);

        }else if(user_b.getStatus()==2){
            //Friended
            viewHolder.addButton.setVisibility(View.GONE);
            viewHolder.removeButton.setVisibility(View.VISIBLE);
            viewHolder.openRequest.setVisibility(View.GONE);

        }else if(user_b.getStatus()==3) {
            //You have to answer request
            viewHolder.addButton.setVisibility(View.VISIBLE);
            viewHolder.removeButton.setVisibility(View.VISIBLE);
            viewHolder.openRequest.setVisibility(View.GONE);
        }

        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    setPosition(position);

                    if(CheckSharedPreferences.getInstance().isLoggedIn(context)){
                        DBfunctions.getInstance().friendRequest(context, SearchListAdapter.this, getUserList(), getPosition(), getUserList().get(getPosition()).getUser_id(), CheckSharedPreferences.getInstance().getAdmin_id());
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
                    DBfunctions.getInstance().removeFriend(context, SearchListAdapter.this, getUserList(), getPosition(), getUserList().get(getPosition()).getUser_id(), CheckSharedPreferences.getInstance().getAdmin_id());
                    }else {
                    CheckSharedPreferences.getInstance().endSession(context);
                }
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View itemView = LayoutInflater.from(arg0.getContext()).inflate(
                R.layout.list_search_row, arg0, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

         TextView name;
         TextView prename;
         TextView email;
         ImageButton addButton, removeButton, openRequest;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textViewListSearchRowName);
            prename = (TextView) itemView.findViewById(R.id.textViewListSearchRowPreName);
            email= (TextView) itemView.findViewById(R.id.textViewListSearchRowEmail);
            addButton= (ImageButton) itemView.findViewById(R.id.imageButtonAddFriend);
            removeButton= (ImageButton) itemView.findViewById(R.id.imageButtonDeleteFriend);
            openRequest= (ImageButton) itemView.findViewById(R.id.imageButtonOpenRequest);
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