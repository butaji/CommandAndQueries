function TaskCreatedEvent(id, title) {
		
	this.id = id;
	this.title = title;
}

function TaskRenamedEvent(id, title) {
	this.id = id;
	this.title = title;
}

function TaskCompletedEvent(id) {
	this.id = id;
}

module.exports = {
	
	GetTaskCreated : TaskCreatedEvent,
	GetTaskRenamed : TaskRenamedEvent,
	GetTaskCompleted : TaskCompletedEvent,
	
	TaskCreated : function(id, title) {
		return new TaskCreatedEvent(id, title);
	},

	TaskRenamed : function(id, title) {
		return new TaskRenamedEvent(id, title);
	},

	TaskCompleted : function(id) {
		return new TaskCompletedEvent(id);
	},
}