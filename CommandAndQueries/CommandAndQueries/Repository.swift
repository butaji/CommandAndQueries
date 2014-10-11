//
//  Repository.swift
//  CommandAndQueries
//
//  Created by Vitaly Baum on 11/10/14.
//  Copyright (c) 2014 Vitaly Baum. All rights reserved.
//

import Foundation

struct Repository {
    
    func get(id: String) -> Task {
        
        let task = Task()
        
        let events = EventStore.instance().loadHistoryFor(id);
        
        for e in events {
            task.apply(e)
        }
        
        return task
    }
    
    func save<T : Event>(id: String, e: T, v: Int) {
        
        EventStore.instance().save(id, e: e, v: v)
        EventBus.publish(e)
    }
}
