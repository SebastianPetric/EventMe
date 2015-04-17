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

public class FriendsListAdapter extends
        RecyclerView.Adapter<FriendsListAdapter.MyViewHolder> {

    private ArrayList<User> users;
    private Context context;
    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private MyViewHolder viewHolder;
    private int position;

    //Remove Friend
    private static final String URL_REMOVE_FRIEND_FROM_FRIENDSLIST = "remove_friend_from_friendslist.php";


    public FriendsListAdapter(Context context,ArrayList<User> list) {
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

        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPosition(position);
                setViewHolder(viewHolder);

                if(CheckSharedPreferences.getInstance().isLoggedIn(context)){
                    removeFriendFromFriendsList(CheckSharedPreferences.getInstance().getAdmin_id(),getUserList().get(getPosition()).getUser_id());
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
        ImageButton removeButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textViewFriendsListRowName);
            prename = (TextView) itemView.findViewById(R.id.textViewFriendsListRowPrename);
            email= (TextView) itemView.findViewById(R.id.textViewFriendsListRowEmail);
            removeButton= (ImageButton) itemView.findViewById(R.id.imageButtonDeleteFromFriendsList);
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

    public void removeFriendFromFriendsList(String admin_id,int user_id) {

        RequestParams params = BuildJSON.getInstance().addRemoveFriendJSON(admin_id, user_id);
        DBconnection.post(URL_REMOVE_FRIEND_FROM_FRIENDSLIST, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context.getApplicationContext(), response.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    if (response.getInt(STATUS) == 200) {
                        getUserList().remove(getPosition());
                        //final User user_b = getUserList().get(getPosition());

                        //User wird entfernt
                        //user_b.setStatus(0);
                        //getViewHolder().removeButton.setVisibility(View.GONE);

                        /*FriendsListAdapter adp = (FriendsListAdapter) this.getAdapter();
                        adp.UpdateDataList(EfficientAdapter.MY_DATA);
                        adp.notifyDataSetChanged();
                        QuickList.invalidateViews();
                        QuickList.scrollBy(0, 0);*/
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