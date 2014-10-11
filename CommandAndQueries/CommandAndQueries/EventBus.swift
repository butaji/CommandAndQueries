//
//  File.swift
//  CommandAndQueries
//
//  Created by Vitaly Baum on 11/10/14.
//  Copyright (c) 2014 Vitaly Baum. All rights reserved.
//

import Foundation

struct EventBus {
    
    static var subscribtions:[(name: String, value: AnyObject -> ())] = []
    
    static func publish<T : Event>(e: T) {

        let y:String = NSStringFromClass(T.self)
        
        let handlers = subscribtions.filter({ x in x.name == y})
        
        for h in handlers {
            
            dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)) {
                h.value(e)
            }
            
        }
    }
    
    static func subscribe<T : Event>(fun: T -> ()) {
    
        let y:String = NSStringFromClass(T.self)
        
        let fun2:(AnyObject)->() = {arg in fun(arg as T)}
        
        subscribtions.append(name: y, value: fun2)
    }
}
