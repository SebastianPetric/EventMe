package thesis.hfu.eventmy.functions;


import android.content.Context;
import android.content.Intent;
import thesis.hfu.eventmy.activities.*;
import thesis.hfu.eventmy.objects.Global;

public class StartActivityFunctions {

    private static StartActivityFunctions instance;
    private static final String EVENT_ID="event_id";
    private static final String TASK_ID="task_id";
    private static final String INACTIVE_TASK="";
    private static final int NO_EDITOR=-1;

    public static StartActivityFunctions getInstance(){
        if (StartActivityFunctions.instance == null){
            StartActivityFunctions.instance = new StartActivityFunctions();
        }
        return StartActivityFunctions.instance;
    }

    public void startLoginActivity(Context context){
        Intent intent = new Intent(context.getApplicationContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startRegistrationActivity(Context context){
        Intent intent = new Intent(context.getApplicationContext(),RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startCreateEventActivity(Context context){
        Intent intent= new Intent(context.getApplicationContext(),CreateEventActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startCreateTaskActivity(Context context, int event_id){
        Intent intent= new Intent(context.getApplicationContext(),CreateTaskActivity.class);
        intent.putExtra(EVENT_ID, event_id);
        Global global = ((Global)context.getApplicationContext());
        global.setEditor_id(NO_EDITOR);
        global.setEditorName(INACTIVE_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startCreateTaskFromFriendsActivity(Context context, int event_id){
        Intent intent= new Intent(context.getApplicationContext(),CreateTaskActivity.class);
        intent.putExtra(EVENT_ID, event_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startAllEventsActivity(Context context){
        Intent intent = new Intent(context.getApplicationContext(), AllEventsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startArchivedEventsActivity(Context context){
        Intent intent = new Intent(context.getApplicationContext(), ArchivedEventsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startAllTasksActivity(Context context,final int event_id){
        Intent intent = new Intent(context.getApplicationContext(), AllTasksOfEventActivity.class);
        intent.putExtra(EVENT_ID, event_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void startSearchActivity(Context context){
        Intent intent = new Intent(context.getApplicationContext(), SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startEventOrganizersActivity(Context context, int event_id){
        Intent intent = new Intent(context.getApplicationContext(), EventOrganizersActivity.class);
        intent.putExtra(EVENT_ID, event_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startFriendsListActivity(Context context){
        Intent intent = new Intent(context.getApplicationContext(), FriendslistActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startEditTaskActivity(Context context,int task_id,final int event_id){
        Intent intent = new Intent(context.getApplicationContext(), EditTaskActivity.class);
        intent.putExtra(TASK_ID, task_id);
        intent.putExtra(EVENT_ID, event_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startFriendsForTaskActivity(Context context,int event_id){
        Intent intent = new Intent(context.getApplicationContext(), TaskOrganizersActivity.class);
        intent.putExtra(EVENT_ID, event_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void startCommentOnEventActivity(Context context,int event_id){
        Intent intent = new Intent(context.getApplicationContext(), CommentOnEventActivity.class);
        intent.putExtra(EVENT_ID, event_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }
}
