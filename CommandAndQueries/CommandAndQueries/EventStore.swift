//
//  EventStore.swift
//  CommandAndQueries
//
//  Created by Vitaly Baum on 11/10/14.
//  Copyright (c) 2014 Vitaly Baum. All rights reserved.
//

import Foundation

struct EventStore {
    
    static let _instance = EventStore()
    
    static func instance() -> EventStore {
        return _instance;
    }
    
    func loadHistoryFor(id: String) -> [Event] {
        
        return JSONHelper.getList(id)
        
    }
  
    func save<T: Event>(id: String, e:T, v: Int) {
        
        var events:[Event] = loadHistoryFor(id)
        
        if events.count - 1 != v {
            NSException(
                name: "YAY!",
                reason: "Concurrent exception is happen for \(events.count)",
                userInfo: nil
                ).raise()
        }
        
        e.version = events.count
        e.time = Int64( NSDate.timeIntervalSinceReferenceDate());
        
        events.append(e)
        
        JSONHelper.saveList(id, items: events)
    }
    
    struct JSONHelper {
        
        static var data = Dictionary<String, [Event]> ();
        
        static func getList(id: String) -> [Event] {
            return data[id] ?? [Event]()
        }
        
        
        static func saveList(id: String, items: [Event]) {
            
            data[id] = items
        }
        
    }
    
}
