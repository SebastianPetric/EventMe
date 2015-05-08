package thesis.hfu.eventmy.objects;


public class Task {

    private int task_id, event_id,percentage,editor_id;
    private double costOfTask;
    private String task,quantity,editor_name,event_name;

    public Task(int task_id,int event_id,int percentage,int editor_id,double costOfTask, String task, String quantity,String editor_name,String event_name){
        this.task_id=task_id;
        this.event_id=event_id;
        this.percentage=percentage;
        this.editor_id=editor_id;
        this.task=task;
        this.costOfTask=costOfTask;
        this.quantity=quantity;
        this.editor_name=editor_name;
        this.event_name=event_name;
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public int getTask_id() {
        return task_id;
    }
    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public int getPercentage() {
        return percentage;
    }
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
    public int getEditor_id() {
        return editor_id;
    }
    public void setEditor_id(int editor_id) {
        this.editor_id = editor_id;
    }
    public double getCostOfTask() {
        return costOfTask;
    }
    public void setCostOfTask(double costOfTask) {
        this.costOfTask = costOfTask;
    }
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getEditor_name() {
        return editor_name;
    }
    public void setEditor_name(String editor_name) {
        this.editor_name = editor_name;
    }
    public String getEvent_name() {
        return event_name;
    }
    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }
}
