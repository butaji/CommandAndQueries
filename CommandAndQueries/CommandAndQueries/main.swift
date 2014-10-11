//
//  main.swift
//  CommandAndQueries
//
//  Created by Vitaly Baum on 10/10/14.
//  Copyright (c) 2014 Vitaly Baum. All rights reserved.
//

import Foundation

let app = Application.create();

let id = "123";

app.send(Commands.createTask(id, title: "new title", version: -1))

var item = app.read(id)

while (item.version != 0) {
    item = app.read(id)
    println("v0")
}

app.send(Commands.renameTask(id, title: "new title 1", version: 0))

while (item.version != 1) {
    item = app.read(id)
    println("v1")
}

app.send(Commands.completeTask(id, version: 1))

while (item.version != 2) {
    item = app.read(id)
    println("v2")
}

item = app.read(id)

println("Check out that everything is ok with our task \(item.title) \(item.completed)")




