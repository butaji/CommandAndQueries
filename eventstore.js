var data = {};

module.exports = {
	
	loadHistoryForId : function (id) {
		
		return data[id] || [];
	},
	
	save : function (id, e, v) {
		
		if (!e)
			throw "Event is undefined"
		
		var events = module.exports.loadHistoryForId(id);
		
		if (events.length - 1 > v) {
			throw "YAY";
		}
		
		e.version = events.length;
		e.time = new Date().getTime();
		
		data[id] = events.concat([e]);
	}

}