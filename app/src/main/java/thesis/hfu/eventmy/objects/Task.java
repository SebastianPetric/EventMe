package thesis.hfu.eventmy.objects;


public class Task {

    private int task_id, event_id,percentage,editor_id;
    private double costOfTask;
    private String task,quantity,description;


    public Task(int task_id,int event_id,int percentage,int editor_id,double costOfTask, String task, String quantity,String description){

        this.task_id=task_id;
        this.event_id=event_id;
        this.percentage=percentage;
        this.editor_id=editor_id;
        this.task=task;
        this.costOfTask=costOfTask;
        this.quantity=quantity;
        this.description=description;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
