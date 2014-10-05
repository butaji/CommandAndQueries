package com.company;

import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class TaskCreated extends Event {
    public UUID id;
    public String title;

    public TaskCreated(UUID id, String title) {

        this.id = id;
        this.title = title;
    }
}
