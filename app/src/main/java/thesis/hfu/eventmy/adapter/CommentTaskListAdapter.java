package thesis.hfu.eventmy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import thesis.hfu.eventmy.R;
import thesis.hfu.eventmy.objects.History;

import java.util.ArrayList;

public class CommentTaskListAdapter extends
        RecyclerView.Adapter<CommentTaskListAdapter.MyViewHolder> {

    private ArrayList<History> list;
    final Context context;

    public CommentTaskListAdapter(Context context, ArrayList<History> list) {
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
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View itemView = LayoutInflater.from(arg0.getContext()).inflate(
                R.layout.list_comments_task_row, arg0, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        TextView name, date, comment;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.listRowCommentsName);
            date = (TextView) itemView.findViewById(R.id.listRowCommentsDate);
            comment = (TextView) itemView.findViewById(R.id.listRowCommentsComment);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}