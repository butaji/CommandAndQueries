//
//  Task.swift
//  CommandAndQueries
//
//  Created by Vitaly Baum on 11/10/14.
//  Copyright (c) 2014 Vitaly Baum. All rights reserved.
//

import Foundation

class Task {
    
    init () {
    }
    
    var id: String = ""

    func create(id: String, title: String) -> TaskCreated {
        return TaskCreated(id: id, title: title)
    }
    
    func rename(title: String) -> TaskRenamed {
        return TaskRenamed(id: self.id, title: title)
    }
    
    func complete() -> TaskCompleted {
        return TaskCompleted(id: self.id)
    }
    
    func apply<T: Event>(e: T) {
        
        if e is TaskCreated {
            self.id = e.id
        }
        
        if e is TaskRenamed {}
        if e is TaskCompleted {}
        
    }
}
