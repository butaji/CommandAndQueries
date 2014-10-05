package com.company;

import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class CreateTask implements Command {
    public UUID id;
    public String title;
    public int version;

    public CreateTask(UUID id, String title, int version) {
        this.id = id;
        this.title = title;
        this.version = version;
    }
}
