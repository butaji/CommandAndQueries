package com.company;

/**
 * Created by vbaum on 20/09/14.
 */
public class TasksHandlers {

    void handle(CreateTask cmd) throws Task.TaskAlreadyCreated {

        TasksRepository repo = TasksRepository.instance();

        Task task = repo.getById(cmd.id);

        repo.Save(cmd.id, task.Create(cmd.title), -1);
    }

    void handle(RenameTask cmd) throws Task.TaskYetNotCreated {

        TasksRepository repo = TasksRepository.instance();

        Task task = repo.getById(cmd.id);

        repo.Save(cmd.id, task.Rename(cmd.title), cmd.version);
    }

    void handle(CompleteTask cmd) throws Task.TaskYetNotCreated {

        TasksRepository repo = TasksRepository.instance();

        Task task = repo.getById(cmd.id);

        repo.Save(cmd.id, task.Complete(), cmd.version);
    }

    public Application.HandleCommand Create() {

        final TasksHandlers h = this;

        return new Application.HandleCommand() {
            @Override
            public void handle(Command cmd) throws Task.TaskAlreadyCreated {
                h.handle((CreateTask)cmd);
            }
        };
    }

    public Application.HandleCommand Rename() {

        final TasksHandlers h = this;

        return new Application.HandleCommand() {
            @Override
            public void handle(Command cmd) throws Task.TaskYetNotCreated {
                h.handle((RenameTask)cmd);
            }
        };
    }

    public Application.HandleCommand Complete() {

        final TasksHandlers h = this;

        return new Application.HandleCommand() {
            @Override
            public void handle(Command cmd) throws Task.TaskYetNotCreated {
                h.handle((CompleteTask)cmd);
            }
        };
    }
}
