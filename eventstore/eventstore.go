package eventstore

import (
    "fmt"
    "os"
    "encoding/json"
    "io/ioutil"
	"../domain"
	"time"
)

// here you can change implementation of EventStore from InMem to File
var ins = InMemEventStore { Events: map[string][]domain.Event {}}

func GetInstance() EventStore {
	return ins
}

type EventStore interface {
    SaveEvent(id string, e *domain.Event, v int)
	LoadHistoryFor(id string) []domain.Event
} 

type EventEnvelope struct {
	Id string `json:"id"`
	Time time.Time `json:"time"`
	Title string `json:"title"`
	Type string `json:"type"`
	Version int `json:"version"`
}

const Dir = "/Users/vbaum/Projects/EventStore/Storage/"

type InMemEventStore struct {
	Events map[string][]domain.Event
}

type FileEventStore struct {}

func (mes InMemEventStore) SaveEvent(id string, e *domain.Event, v int) {
    events := mes.LoadHistoryFor(id);

    fmt.Printf("save version %v\n", v)
    fmt.Printf("events %v\n", len(events))

    if len(events) - 1 != v {
        fmt.Printf("Concurrent exception is happen for %v\n", len(events))
        os.Exit(1)
	}

    e.Version = len(events);
    fmt.Printf("event version set to %v\n", e.Version)
	e.Time = time.Now()
	
	mes.Events[id] = append(events, *e)
	fmt.Printf("saved events %v\n", len(mes.Events[id]))
}

func (mes InMemEventStore) LoadHistoryFor(id string) []domain.Event {
	return mes.Events[id]
}

func (fes FileEventStore) SaveEvent(id string, e *domain.Event, v int) {
    events := fes.LoadHistoryFor(id);

    fmt.Printf("save version %v\n", v)
    fmt.Printf("events %v\n", len(events))

    if len(events) - 1 != v {
        fmt.Printf("Concurrent exception is happen for " + string(len(events)))
        os.Exit(1)
	}

    e.Version = len(events);
    fmt.Printf("event version set to %v\n", e.Version)
	e.Time = time.Now()

    put(id, *e);
}

func (fes FileEventStore) LoadHistoryFor(id string) []domain.Event {
	
	path := Dir + string(id) + "/"
	
	events := []domain.Event {}
	
	files, e := ioutil.ReadDir(path)
	
    if e != nil {
        fmt.Printf("Dir read error: %v\n", e)
        
		return events;
    }
	
    for _, f := range files {
		
		event := readFile(path + f.Name())
		events = append(events, event)
    }
	
  	return events;
}

func put(id string, e domain.Event) {
	
	data := EventEnvelope {}
	
	data.Id = string(e.Id)
    fmt.Printf("marshalling %s\n", data.Id)
	data.Time = e.Time
	data.Title = e.Params.(string)
	data.Type = e.Target
	data.Version = e.Version

	dataString, er := json.Marshal(data)
    fmt.Printf("marshalling %s\n", dataString)
	
	if er != nil {
		panic(er)
	}
	
	if os.MkdirAll(fmt.Sprintf("%v%s", Dir, id), 0764) != nil {
		panic("MkdirAll")
	}
	
	err := ioutil.WriteFile(
		fmt.Sprintf("%v%s/%v.json", Dir, id, data.Time.UnixNano() / int64(time.Millisecond)), 
		dataString, 0664)
		
	if err != nil {
        panic(err)
    }
	
    fmt.Printf("writen to flie\n")
	
}

func readFile(path string) domain.Event {
	
	file, e := ioutil.ReadFile(path)

    if e != nil {
		panic(e)
    }
	
	var data EventEnvelope
    json.Unmarshal(file, &data)

	event := domain.Event {}
	event.Id = data.Id
	event.Target = data.Type
	event.Version = data.Version
	
	if data.Title != "" {
		event.Params = data.Title
	}
	
	return event
}
