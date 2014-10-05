package com.company;

import java.util.List;
import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class TasksRepository {

    private static TasksRepository _instance = new TasksRepository();

    public static TasksRepository instance() {
        return _instance;
    }

    public Task getById(UUID id) {

        Task task = new Task(id);

        List<Event> events = EventStore.instance().loadHistoryFor(id);

        for (Event e : events) {
            task.Apply(e);
        }

        return task;
    }

    public void Save(UUID id, Event e, int version) {

        EventStore.instance().saveEvent(id, e, version);
        EventBus.instance().publish(e);
    }
}
