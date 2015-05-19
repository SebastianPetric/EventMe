package thesis.hfu.eventme.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.dialogs.EditCostsDialog;
import thesis.hfu.eventme.dialogs.EditPercentageDialog;
import thesis.hfu.eventme.functions.CheckSharedPreferences;
import thesis.hfu.eventme.functions.StartActivity;
import thesis.hfu.eventme.objects.Task;

import java.util.ArrayList;

public class AllTasksOfEventListAdapter extends
        RecyclerView.Adapter<AllTasksOfEventListAdapter.MyViewHolder>  {

    private ArrayList<Task> taskList;
    private Context context;
    private MyViewHolder viewHolder;
    private TextView eventNameTextView, eventDateTextView, eventTotalOrganizersTextView, eventTotalCostsTextView, eventTotalPercentageTextView,eventLocationTextView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout syncRefresh;
    private ProgressBar progressBarEvent;
    private int event_id;

    //If there are the fields totalOrganizers,totalPercentage,etc from the event in this activity then the value is 1, otherwise it is 0
    private final int typeOfUpdate=1;
    private FragmentManager fragmentManager;

    public AllTasksOfEventListAdapter(Activity context,ProgressBar progressBarEvent,RecyclerView recyclerView,SwipeRefreshLayout syncRefresh,ArrayList<Task> taskList,TextView eventNameTextView,TextView eventDateTextView,TextView eventTotalOrganizersTextView,TextView eventTotalCostsTextView,TextView eventTotalPercentageTextView, TextView eventLocationTextView,int event_id) {
        setTaskList(taskList);
        setContext(context);
        setEventNameTextView(eventNameTextView);
        setEvent_id(event_id);
        setEventDateTextView(eventDateTextView);
        setEventTotalCostsTextView(eventTotalCostsTextView);
        setEventTotalOrganizersTextView(eventTotalOrganizersTextView);
        setEventTotalPercentageTextView(eventTotalPercentageTextView);
        setEventLocationTextView(eventLocationTextView);
        setRecyclerView(recyclerView);
        setSyncRefresh(syncRefresh);
        setFragmentManager(context.getFragmentManager());
        setProgressBarEvent(progressBarEvent);
    }
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final Task task = this.taskList.get(position);

        viewHolder.taskTextView.setText(task.getTask());
        viewHolder.quantityTextView.setText(task.getQuantity());
        viewHolder.costTextView.setText(String.valueOf(task.getCostOfTask()));
        viewHolder.percentageTextView.setText(String.valueOf(task.getPercentage()));
        viewHolder.editorTextView.setText(task.getEditor_name());
        viewHolder.progressBar.setProgress(task.getPercentage());

        viewHolder.editorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewHolder(viewHolder);
                if (CheckSharedPreferences.getInstance().isLoggedIn(context.getApplicationContext())) {
                    DBfunctions.getInstance().changeEditorOfTask(context.getApplicationContext(),getViewHolder().editorTextView,CheckSharedPreferences.getInstance().getAdmin_id(), task.getTask_id());
                } else {
                    CheckSharedPreferences.getInstance().endSession(context.getApplicationContext());
                }
            }
        });

        viewHolder.percentageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewHolder(viewHolder);
                EditPercentageDialog.getInstance().startEditPercentageDialog(getFragmentManager(),getProgressBarEvent(),viewHolder.progressBar,getSyncRefresh(), viewHolder.percentageTextView, getEventTotalOrganizersTextView(), getEventTotalCostsTextView(), getEventTotalPercentageTextView(), getEventNameTextView(), getEventDateTextView(),getEventLocationTextView(), task.getTask_id(),getEvent_id(),getTypeOfUpdate());
            }
        });

        viewHolder.costButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewHolder(viewHolder);
                EditCostsDialog.getInstance().startEditTaskDialog(getFragmentManager(),getSyncRefresh(),getRecyclerView(), viewHolder.costTextView, getEventTotalOrganizersTextView(), getEventTotalCostsTextView(), getEventTotalPercentageTextView(), getEventNameTextView(), getEventDateTextView(),getEventLocationTextView(),getEvent_id(),task.getTask_id(),getTypeOfUpdate());
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
            View.OnClickListener{

        ImageButton costButton,percentageButton,editorButton;
        TextView costTextView, percentageTextView, editorTextView, taskTextView, quantityTextView;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            costButton = (ImageButton) itemView.findViewById(R.id.imageButtonListRowTaskOfEventCosts);
            percentageButton = (ImageButton) itemView.findViewById(R.id.imageButtonListRowTaskOfEventPercentage);
            editorButton = (ImageButton) itemView.findViewById(R.id.imageButtonlistRowTaskOfEventPercentage);
            costTextView = (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventCosts);
            percentageTextView = (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventPercentage);
            editorTextView = (TextView) itemView.findViewById(R.id.textViewlistRowTaskOfEventOrganizer);
            taskTextView = (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventName);
            quantityTextView = (TextView) itemView.findViewById(R.id.textViewListRowTaskOfEventDate);
            progressBar=(ProgressBar) itemView.findViewById(R.id.progressBarTask);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(CheckSharedPreferences.getInstance().isLoggedIn(context.getApplicationContext())){
            StartActivity.getInstance().startEditTaskActivity(context.getApplicationContext(), getTaskList().get(getPosition()).getTask_id(),getEvent_id());
            }else{
                CheckSharedPreferences.getInstance().endSession(context.getApplicationContext());
            }
        }
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public void setViewHolder(MyViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }
    public ArrayList<Task> getTaskList(){
        return this.taskList;
    }
    public void setTaskList(ArrayList<Task> tasks){
        this.taskList =tasks;
    }
    public void setContext(Context context){
        this.context=context;
    }
    public void setEventNameTextView(TextView eventName){
        this.eventNameTextView=eventName;
    }
    public void setEventDateTextView(TextView eventDate){
        this.eventDateTextView=eventDate;
    }
    public void setEventTotalOrganizersTextView(TextView totalOrganizers){
        this.eventTotalOrganizersTextView=totalOrganizers;
    }
    public void setEventTotalCostsTextView(TextView totalCosts){
        this.eventTotalCostsTextView=totalCosts;
    }
    public void setEventTotalPercentageTextView(TextView totalPercentage){
        this.eventTotalPercentageTextView=totalPercentage;
    }
    public void setEvent_id(int event_id){
        this.event_id=event_id;
    }
    public void setEventLocationTextView(TextView eventLocation){
        this.eventLocationTextView=eventLocation;
    }
    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager=fragmentManager;
    }
    public MyViewHolder getViewHolder() {
        return viewHolder;
    }
    public TextView getEventNameTextView() {
        return eventNameTextView;
    }
    public TextView getEventDateTextView() {
        return eventDateTextView;
    }
    public TextView getEventTotalOrganizersTextView() {
        return eventTotalOrganizersTextView;
    }
    public TextView getEventTotalCostsTextView() {
        return eventTotalCostsTextView;
    }
    public TextView getEventTotalPercentageTextView() {
        return eventTotalPercentageTextView;
    }
    public int getEvent_id() {
        return event_id;
    }
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
    public int getTypeOfUpdate() {
        return typeOfUpdate;
    }
    public TextView getEventLocationTextView() {
        return eventLocationTextView;
    }
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
    public SwipeRefreshLayout getSyncRefresh() {
        return syncRefresh;
    }
    public void setSyncRefresh(SwipeRefreshLayout syncRefresh) {
        this.syncRefresh = syncRefresh;
    }
    public ProgressBar getProgressBarEvent() {
        return progressBarEvent;
    }
    public void setProgressBarEvent(ProgressBar progressBar) {
        this.progressBarEvent = progressBar;
    }
}