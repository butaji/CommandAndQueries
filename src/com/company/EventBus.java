package com.company;

/**
 * Created by vbaum on 20/09/14.
 */
public abstract class EventBus {

    private static EventBus _instance = new InMemoryEventBus();

    public static EventBus instance() {
        return _instance;
    }

    public abstract void publish(Event e);

    public abstract void subscribe(Application.HandleEvent handleEvent);

    public abstract EventBus refresh();
}
