//
//  Event.swift
//  CommandAndQueries
//
//  Created by Vitaly Baum on 11/10/14.
//  Copyright (c) 2014 Vitaly Baum. All rights reserved.
//

import Foundation

class Event : NSObject {
    
    init (id: String) {
        self.id = id
        self.version = 0
        self.time = 0
    }
    
    let id: String
    var version: Int
    var time: Int64
}

class TaskCreated : Event {
    
    init (id:String, title: String) {
        
        self.title = title

        super.init(id: id)
    }
    
    let title: String
}

class TaskRenamed : Event {
    
    init (id:String, title: String) {
        
        self.title = title
        
        super.init(id: id)
    }
    
    let title: String
}

class TaskCompleted : Event {
    

}
