//
//  TaskItem.swift
//  CommandAndQueries
//
//  Created by Vitaly Baum on 11/10/14.
//  Copyright (c) 2014 Vitaly Baum. All rights reserved.
//

import Foundation

class TaskItem {
    
    init(title: String, completed: Bool = false, version: Int) {
        self.title = title
        self.completed = completed
        self.version = version
    }
    
    let title: String
    let version: Int
    let completed: Bool
}
