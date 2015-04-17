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

public class SearchListAdapter extends
        RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {

    private ArrayList<User> users;
    private Context context;
    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private MyViewHolder viewHolder;
    private int position;

    //Remove Friend
    private static final String URL_REMOVE_FRIEND = "remove_friend.php";

    //Friend Request
    private static final String URL_FRIEND_REQUEST= "friend_request.php";

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
                    setViewHolder(viewHolder);

                    if(CheckSharedPreferences.getInstance().isLoggedIn(context)){
                        friendRequest(CheckSharedPreferences.getInstance().getAdmin_id(),user_b.getUser_id());
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
                    removeFriend(CheckSharedPreferences.getInstance().getAdmin_id(), user_b.getUser_id());
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
            /*Toast.makeText(v.getContext(),
                    "Klick " + users.get(getPosition()).getName(),
                    Toast.LENGTH_SHORT).show();*/
        }
    }


    //----------------------------------------------------------------------
    //-----------------Functions-------------------------------------
    //----------------------------------------------------------------------

    public void friendRequest(String user1_id,int user2_id){

        RequestParams params = BuildJSON.getInstance().addRemoveFriendJSON(user1_id, user2_id);
        DBconnection.post(URL_FRIEND_REQUEST, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(),response.getString(MESSAGE),Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200){
                        final User user_b = getUserList().get(getPosition());

                        //Users become friends
                        if(user_b.getStatus()==3){
                            user_b.setStatus(2);
                            getViewHolder().addButton.setVisibility(View.GONE);
                            getViewHolder().removeButton.setVisibility(View.VISIBLE);
                            getViewHolder().openRequest.setVisibility(View.GONE);

                            //Request is open and has to be answered by the other user
                        }else if(user_b.getStatus()==1){
                            user_b.setStatus(0);
                            getViewHolder().addButton.setVisibility(View.GONE);
                            getViewHolder().removeButton.setVisibility(View.GONE);
                            getViewHolder().openRequest.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void removeFriend(String usera_id, int userb_id) {

        RequestParams params = BuildJSON.getInstance().addRemoveFriendJSON(usera_id, userb_id);
        DBconnection.post(URL_REMOVE_FRIEND, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if(response.getInt(STATUS)==200) {

                        //Friend deleted
                        final User user_b = getUserList().get(getPosition());
                        user_b.setStatus(1);
                        getViewHolder().addButton.setVisibility(View.VISIBLE);
                        getViewHolder().removeButton.setVisibility(View.GONE);
                        getViewHolder().openRequest.setVisibility(View.GONE);
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
}