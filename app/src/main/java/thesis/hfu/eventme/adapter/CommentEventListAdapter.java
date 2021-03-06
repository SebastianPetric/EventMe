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
import thesis.hfu.eventme.objects.History;
import java.util.ArrayList;

public class CommentEventListAdapter extends
        RecyclerView.Adapter<CommentEventListAdapter.MyViewHolder> {

    private ArrayList<History> list;
    final Context context;

    public CommentEventListAdapter(Context context, ArrayList<History> list) {
        this.list = list;
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final History history_temp = this.list.get(position);

        viewHolder.name.setText(history_temp.getEditor_name());
        viewHolder.date.setText(history_temp.getDate());
        viewHolder.comment.setText(history_temp.getComment());
        if(history_temp.isEditor()){
            viewHolder.deleteTask.setVisibility(View.VISIBLE);
            viewHolder.deleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBfunctions.getInstance().deleteCommentEvent(context, CommentEventListAdapter.this, getList(), position, history_temp.getHistory_id());
                }
            });
        }else{
            viewHolder.deleteTask.setVisibility(View.GONE);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View itemView = LayoutInflater.from(arg0.getContext()).inflate(
                R.layout.list_comments_event_row, arg0, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        TextView name, date, comment;
        ImageButton deleteTask;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.listRowCommentsName);
            date = (TextView) itemView.findViewById(R.id.listRowCommentsDate);
            comment = (TextView) itemView.findViewById(R.id.listRowCommentsComment);
            deleteTask = (ImageButton) itemView.findViewById(R.id.imageButtonDeleteCommentEvent);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public ArrayList<History> getList(){
        return this.list;
    }
}