package com.company;

import java.util.UUID;

/**
 * Created by vbaum on 21/09/14.
 */
public abstract class ReadModel {

    private static ReadModel _instance = new InMemoryReadModel();

    public static ReadModel instance() {
        return _instance;
    }

    public abstract void put(UUID id, TaskItem item);

    public abstract TaskItem get(UUID id);
}
