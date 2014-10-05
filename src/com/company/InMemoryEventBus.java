package com.company;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by vbaum on 20/09/14.
 */
public class InMemoryEventBus extends EventBus {

    private final Thread _worker;
    Queue<Event> _queue;
    ArrayList<Application.HandleEvent> _handlers;

    protected InMemoryEventBus(){

        _worker = new Thread(){
            public void run(){

                while (true) {

                    if (_queue == null || _handlers == null)
                        continue;

                    Event e = _queue.poll();

                    if (e == null)
                        continue;

                    ArrayList<Application.HandleEvent> handlers = _handlers;

                    for (Application.HandleEvent h : handlers) {

                        Type generic = ((ParameterizedType)h.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];

                        if (e.getClass() == generic) {
                            h.handle(e);
                        }

                    }


                }

            }
        };

        _worker.start();

    }

    public void publish(Event e) {

        _queue.add(e);

        System.out.println("Published " + e);
    }

    @Override
    public void subscribe(Application.HandleEvent handleEvent) {
        _handlers.add(handleEvent);
    }

    @Override
    public EventBus refresh() {

        _handlers = new ArrayList<Application.HandleEvent>();
        _queue = new ConcurrentLinkedQueue<Event>();

        return this;
    }

}
