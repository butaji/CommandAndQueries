package com.company;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class Task implements Root {

    private boolean _created;
    private UUID _id;

    private Task(){}

    public Task(UUID id) {

        _id = id;
    }

    public TaskCreated Create(String title) throws TaskAlreadyCreated {

        if (_created)
            throw new Task.TaskAlreadyCreated();

        _created = true;

        return new TaskCreated(_id, title);
    }

    public TaskRenamed Rename(String title) throws TaskYetNotCreated {

        if (!_created)
            throw new Task.TaskYetNotCreated();

        return new TaskRenamed(_id, title);
    }

    public TaskCompleted Complete() throws TaskYetNotCreated {

        if (!_created)
            throw new Task.TaskYetNotCreated();

        return new TaskCompleted(_id);
    }

    @Override
    public void Apply(Event e) {
        if (e.getClass() == TaskCreated.class)
            Apply((TaskCreated)e);

        if (e.getClass() == TaskRenamed.class)
            Apply((TaskRenamed)e);

        if (e.getClass() == TaskCompleted.class)
            Apply((TaskCompleted)e);
    }

    void Apply(TaskCreated e){
        _created = true;
    }
    void Apply(TaskRenamed e){

    }

    void Apply(TaskCompleted e){

    }

    protected class TaskYetNotCreated extends Throwable {
    }

    protected class TaskAlreadyCreated extends Throwable {
    }
}
