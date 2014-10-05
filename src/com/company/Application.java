package com.company;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class Application {
    private HashMap<Class, HandleCommand> _handlers;

    private ReadModel _db = ReadModel.instance();

    interface HandleCommand {
        void handle(Command cmd) throws Task.TaskAlreadyCreated, Task.TaskYetNotCreated;
    }

    interface HandleEvent<T> {

        void handle(T e);
    }


    public void send(Command command) throws Task.TaskYetNotCreated, Task.TaskAlreadyCreated {

        HandleCommand handler = _handlers.get(command.getClass());
        handler.handle(command);
    }

    public boolean isProcessed(UUID id, int version) {

        TaskItem item = read(id);

//        System.out.println("Expected " + version);

        return item != null && item.version > version;
    }

    public TaskItem read(UUID id) {

        return _db.get(id);
    }

    public Application bootstrap() {

        _handlers = new HashMap<Class, HandleCommand>();

        final TasksHandlers handlers = new TasksHandlers();

        _handlers.put(CreateTask.class, handlers.Create());

        _handlers.put(RenameTask.class, handlers.Rename());

        _handlers.put(CompleteTask.class, handlers.Complete());

        EventBus bus = EventBus.instance().refresh();

        bus.subscribe(new HandleEvent<TaskCreated>() {
            @Override
            public void handle(TaskCreated e) {

                TaskItem item = new TaskItem();
                item.title = e.title;
                item.version = e.version;
                _db.put(e.id, item);
            }
        });

        bus.subscribe(new HandleEvent<TaskRenamed>() {
            @Override
            public void handle(TaskRenamed e) {

                TaskItem item = read(e.id);
                item.title = e.title;
                item.version = e.version;
            }
        });

        bus.subscribe(new HandleEvent<TaskCompleted>() {
            @Override
            public void handle(TaskCompleted e) {

                TaskItem item = read(e.id);
                item.completed = true;
                item.version = e.version;
            }
        });

        return this;
    }
}
