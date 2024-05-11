package EventQueue;

import Events.Event;

public class EventQueue implements IEventQueue {
    private RandomHeap<Event> heap = new RandomHeap<Event>();

    public EventQueue() {
    }

    public void insert(Event event) {
        heap.insert(new RandomHeapNode<Event>(event));
    }

    public Event getNext() {
        return heap.findMin().getValue();
    }

    public Event popNext() {
        return heap.popMin().getValue();
    }
}
