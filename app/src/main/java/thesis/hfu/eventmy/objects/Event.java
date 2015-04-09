package thesis.hfu.eventmy.objects;


import java.util.Date;

public class Event {
    private int event_id;
    private String name,location;
    private Date date=null;

    public Event(int event_id,String name,String location, Date date){
        this.event_id=event_id;
        this.name=name;
        this.date=date;
        this.location=location;
    }

    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
