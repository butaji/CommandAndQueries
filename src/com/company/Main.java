package com.company;

import java.io.IOException;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        Application app = new Application().bootstrap();

        UUID id = UUID.fromString("85ade304-462d-4321-8d1b-3db4377c699d");

        try {


            CreateTask cmd1 = new CreateTask(id, "New task", -1);

            app.send(cmd1);

            while (!app.isProcessed(id, -1)) {
            }

            TaskItem task = app.read(id);

            RenameTask cmd2 = new RenameTask(id, "New task 1", task.version);

            app.send(cmd2);

            while (!app.isProcessed(id, task.version)) {
            }


            task = app.read(id);

            CompleteTask cmd3 = new CompleteTask(id, task.version);

            app.send(cmd3);

            while (!app.isProcessed(id, task.version)) {
            }



            task = app.read(id);

            System.out.println(task.title);
            System.out.println(task.completed);

        } catch (Task.TaskYetNotCreated taskYetNotCreated) {
            taskYetNotCreated.printStackTrace();
        } catch (Task.TaskAlreadyCreated taskAlreadyCreated) {
            taskAlreadyCreated.printStackTrace();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
