package com.company;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by vbaum on 21/09/14.
 */
public class InMemoryReadModel extends ReadModel {

    private HashMap<UUID, TaskItem> _db = new HashMap<UUID, TaskItem>();

    @Override
    public void put(UUID id, TaskItem item) {
        _db.put(id, item);
    }

    @Override
    public TaskItem get(UUID id) {
        return _db.get(id);
    }
}
