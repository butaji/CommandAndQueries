var data = {};
var bus = require("./eventbus.js");
var store = require("./eventstore.js");
var commands = require("./commands.js");
var domain = require("./domain.js");
var events = require("./events.js");
var repository = require("./repository.js");
var commandhandlers = require("./commandhandlers.js");

var repo = repository.create(bus, store);
var handlers = commandhandlers.create(repo);

bus.subscribe(events.GetTaskCreated, taskCreated);
bus.subscribe(events.GetTaskRenamed, taskRenamed);
bus.subscribe(events.GetTaskCompleted, taskCompleted);

function TaskItem(title, completed, version) {
	this.title = title;
	this.completed = completed;
	this.version = version;
}

function taskCreated(e) {
	
	console.log("taskCReated");
	
	data[e.id] = new TaskItem(e.title, false, e.version);
}

function taskRenamed(e) {
	
	data[e.id].title = e.title;
	data[e.id].version = e.version;
}

function taskCompleted(e) {
	data[e.id].completed = true;
	data[e.id].version = e.version;
}

module.exports = {
	
	send : function (cmd) {
		
		if (cmd instanceof commands.CreateTask) {
			handlers.createTask(cmd);
			return;
		}

		if (cmd instanceof commands.RenameTask) {
			handlers.renameTask(cmd);
			return;
		}

		if (cmd instanceof commands.CompleteTask) {
			handlers.completeTask(cmd);
			return;
		}
		
		throw "Unhandled command";
	},
	
	read : function (id) {
		
		return data[id] || new TaskItem("", false, -1);
	},
	
	generateId : function () {
		return 123;
	}
}