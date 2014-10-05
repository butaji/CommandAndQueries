package com.company;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public abstract class EventStore {
    private static EventStore _instance = new FileEventStore();

    public static EventStore instance() {

        return _instance;
    }


    public List<Event> loadHistoryFor(UUID id) {
        List<Event> events = get(id);

        if (events == null)
            events = new ArrayList<Event>();

        return events;
    }


    public void saveEvent(UUID id, Event e, int version) {
        List<Event> events = loadHistoryFor(id);

        if (events.size() - 1 != version)
            throw new ConcurrentModificationException();

        e.version = events.size();

        put(id, e);
    }

    protected abstract List<Event> get(UUID id);
    protected abstract void put(UUID id, Event event);
}
