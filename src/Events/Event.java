package Events;

import java.util.Date;

public class Event implements Comparable<Event> {
    private Date date;

    public Event(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(Event o) {
        return date.compareTo(o.date);
    }
}
