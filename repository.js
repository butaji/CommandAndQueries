var domain = require("./domain.js");
var eventStore;
var eventBus;

module.exports = { 
	
	create : function(bus, store) {
		eventStore = store;
		eventBus = bus;
		
		return this;
	},
		
	get : function (id) {
		var task = new domain.Task();
	
		var events = eventStore.loadHistoryForId(id);

		events.forEach(task.apply);
		
		return task;
	},
		
	save : function (id, e, v) {
	
		eventStore.save(id, e, v);
		eventBus.publish(e);
	}
}