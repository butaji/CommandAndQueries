package com.company;

import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class TaskCompleted extends Event {
    public UUID id;

    public TaskCompleted(UUID id) {

        this.id = id;
    }
}
