package thesis.hfu.eventmy.objects;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


public class AllTasksOfEventListAdapter extends
        RecyclerView.Adapter<AllTasksOfEventListAdapter.MyViewHolder> {

    private static final String URL_BECOME_EDITOR_OF_TASK= "become_editor_of_task.php";

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
        viewHolder.editorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                becomeEditorOfTask(Integer.parseInt(CheckSharedPreferences.getInstance().getUser_id()),task.getTask_id());
            }
        });
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


    public void becomeEditorOfTask(int editor_id,int task_id){

        RequestParams params= BuildJSON.getInstance().becomeEditorOfTaskJSON(editor_id,task_id);
        DBconnection.post(URL_BECOME_EDITOR_OF_TASK,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(context,response.getString("message"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("schlecht",statusCode+"");
                Log.d("schlecht",headers.toString());
                Log.d("schlecht",responseString);
                Log.d("schlecht",throwable.toString());
            }
        });

    }

}