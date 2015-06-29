package thesis.hfu.eventme.objects;

import thesis.hfu.eventme.functions.CheckSharedPreferences;

public class History {

    private String comment,editor_name,date;
    private int history_id,editor_id;
    private boolean isEditor;
    private int admin_id= CheckSharedPreferences.getInstance().getAdmin_id();

    public History(String date, String editor_name, String comment, int history_id,int editor_id){
        this.comment=comment;
        this.editor_name=editor_name;
        this.date=date;
        this.history_id=history_id;
        this.editor_id= editor_id;
        if(admin_id==editor_id){
            setIsEditor(true);
        }else{
            setIsEditor(false);
        }
    }

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getEditor_name() {
        return editor_name;
    }
    public void setEditor_name(String editor_name) {
        this.editor_name = editor_name;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getHistory_id() {
        return history_id;
    }
    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }
    public int getEditor_id() {
        return editor_id;
    }
    public void setEditor_id(int editor_id) {
        this.editor_id = editor_id;
    }
    public boolean isEditor() {
        return isEditor;
    }
    public void setIsEditor(boolean isEditor) {
        this.isEditor = isEditor;
    }
}
