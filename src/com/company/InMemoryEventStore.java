package com.company;

import java.util.*;

/**
 * Created by vbaum on 20/09/14.
 */
public class InMemoryEventStore extends EventStore {

    HashMap<UUID, List<Event>> _db = new HashMap<UUID, List<Event>>();

    @Override
    protected List<Event> get(UUID id) {
        return _db.get(id);
    }

    @Override
    protected void put(UUID id, Event e) {

        List<Event> events = loadHistoryFor(id);

        events.add(e);

        _db.put(id, events);
    }
}
