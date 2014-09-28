package eventbus

import (
	"fmt"
	"../domain"
)

var subs = map[string][]func(domain.Event){}

func Publish(e domain.Event) {
    
	handlers := subs[e.Target]

    fmt.Printf("Published %v\n handlers: %v\n", e, len(handlers))

	for _, h := range handlers {

	    fmt.Printf("hanlder %v\n", h)
		go h(e)
	}
}

func Subscribe(name string, callback func(domain.Event)) {
    subs[name] = append(subs[name], callback)
}