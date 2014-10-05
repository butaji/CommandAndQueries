package com.company;

import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class RenameTask implements Command {


    public UUID id;
    public String title;
    public int version;

    public RenameTask(UUID id, String title, int version) {

        this.id = id;
        this.title = title;
        this.version = version;
    }
}
