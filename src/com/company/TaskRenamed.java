package com.company;

import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class TaskRenamed extends Event {
    public UUID id;
    public String title;

    public TaskRenamed(UUID id, String title) {

        this.id = id;
        this.title = title;
    }


}
