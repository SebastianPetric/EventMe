package thesis.hfu.eventme.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import thesis.hfu.eventme.R;
import thesis.hfu.eventme.adapter.AllEventsListAdapter;
import thesis.hfu.eventme.database.DBfunctions;
import thesis.hfu.eventme.functions.CheckSharedPreferences;
import thesis.hfu.eventme.objects.Event;
import java.util.ArrayList;

public class LongClickEventDialog extends DialogFragment{

    private int position,event_id;
    private ArrayList<Event> eventList;
    RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> adapter;
    FragmentManager fragmentManager;
    private static LongClickEventDialog instance;

    public static LongClickEventDialog getInstance(){
        if (LongClickEventDialog.instance == null){
            LongClickEventDialog.instance = new LongClickEventDialog();
        }
        return LongClickEventDialog.instance;
    }

    public void startLongClickEventDialog(FragmentManager manager,ArrayList<Event> eventList, final RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> adapter,int event_id,int position) {
        this.adapter=adapter;
        this.position=position;
        this.event_id=event_id;
        this.eventList=eventList;
        this.fragmentManager=manager;
        this.show(manager, "longClickEventDialog");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_edit_event_message)
                .setNeutralButton(R.string.dialog_edit_event_archiv, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (CheckSharedPreferences.getInstance().isLoggedIn(getActivity())) {
                                    DBfunctions.getInstance().archivEvent(getActivity(),getEventList(),getAdapter(), getPosition(), CheckSharedPreferences.getInstance().getAdmin_id(), eventList.get(getPosition()).getEvent_id());
                                } else {
                                    CheckSharedPreferences.getInstance().endSession(getActivity());
                                }
                            }
                        }
                )
                .setNegativeButton(R.string.dialog_edit_event_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                )
                .setPositiveButton(R.string.dialog_edit_event_delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (CheckSharedPreferences.getInstance().isLoggedIn(getActivity())) {
                                    DBfunctions.getInstance().deleteEvent(getActivity(), getEventList(), getAdapter(), getPosition(), CheckSharedPreferences.getInstance().getAdmin_id(), eventList.get(getPosition()).getEvent_id());
                                } else {
                                    CheckSharedPreferences.getInstance().endSession(getActivity());
                                }
                            }
                        }

                );
        return builder.create();
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public ArrayList<Event> getEventList(){
        return this.eventList;
    }
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public RecyclerView.Adapter<AllEventsListAdapter.MyViewHolder> getAdapter(){
        return this.adapter;
    }
    public int getPosition() {
        return position;
    }
}
