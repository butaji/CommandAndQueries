package com.company;

import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class CompleteTask implements Command {
    public UUID id;
    public int version;

    public CompleteTask(UUID id, int version) {

        this.id = id;
        this.version = version;
    }
}
