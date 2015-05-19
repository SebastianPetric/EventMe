package thesis.hfu.eventme.objects;

import android.app.Application;

import java.sql.Date;

public class Global extends Application
{
    private int editor_id=-1;
    private String editor_name="";
    private Date date=null;

    //----------------------------------------------------------------------
    //-----------------Getter and Setter-------------------------------------
    //----------------------------------------------------------------------

    public String getEditorName()
    {
        return editor_name;
    }
    public void setEditorName(String s)
    {
        editor_name = s;
    }
    public int getEditor_id() {
        return editor_id;
    }
    public void setEditor_id(int editor_id) {
        this.editor_id = editor_id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
