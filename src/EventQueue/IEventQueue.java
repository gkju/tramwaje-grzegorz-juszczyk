package EventQueue;

import Events.Event;

public interface IEventQueue {
    public void insert(Event event);
    public Event getNext();
    public Event popNext();
}
