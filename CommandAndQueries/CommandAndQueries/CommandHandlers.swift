//
//  CommandHandlers.swift
//  CommandAndQueries
//
//  Created by Vitaly Baum on 11/10/14.
//  Copyright (c) 2014 Vitaly Baum. All rights reserved.
//

import Foundation

struct CommandHandlers {
    
    var repo = Repository()
    
    
    func createTask(cmd: CreateTask) {

        var task = self.repo.get(cmd.id)
        
        self.repo.save(cmd.id, e: task.create(cmd.id, title: cmd.title), v: cmd.version)
    }

    func renameTask(cmd: RenameTask) {

        var task = self.repo.get(cmd.id)
        
        self.repo.save(cmd.id, e: task.rename(cmd.title), v: cmd.version)
    }

    func completeTask(cmd: CompleteTask) {
        
        var task = self.repo.get(cmd.id)
        
        self.repo.save(cmd.id, e: task.complete(), v: cmd.version)
    }

}
