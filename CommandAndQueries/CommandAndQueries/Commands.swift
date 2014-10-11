//
//  Commands.swift
//  CommandAndQueries
//
//  Created by Vitaly Baum on 11/10/14.
//  Copyright (c) 2014 Vitaly Baum. All rights reserved.
//

import Foundation

struct Commands {
    
    static func createTask(id: String, title: String, version: Int) -> CreateTask {
        
        return CreateTask(id: id, version: version, title: title);
    }
    
    static func renameTask(id: String, title: String, version: Int) -> RenameTask {
        
        return RenameTask(id: id, version: version, title: title);
    }
    
    static func completeTask(id: String, version: Int) -> CompleteTask {
        
        return CompleteTask(id: id, version: version);
    }
    
    
}

protocol Command {
    var id: String { get }
    var version: Int { get }
}

struct CreateTask : Command {
    
    let id: String
    let version: Int
    
    let title: String
}

struct RenameTask : Command {
    
    let id: String
    let version: Int
    let title: String
}

struct CompleteTask : Command {
    let id: String
    let version: Int
}
