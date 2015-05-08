package thesis.hfu.eventmy.objects;

public class History {

    private String comment,editor_name,date;


    public History(String date, String editor_name, String comment){
        this.comment=comment;
        this.editor_name=editor_name;
        this.date=date;
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

}
