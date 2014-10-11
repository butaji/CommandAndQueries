var handlers = [];

module.exports = {

	publish : function (e) {
		
		handlers
			.filter(function (x) {
				return (e instanceof x["type"]);
			})
			.forEach(function (h) {
				
				setTimeout(function() {
					h["callback"](e);				
				}, 0);
			})
	},

	subscribe : function (etype, callback) {
		handlers.push({
			"type": etype,
			"callback": callback 
		});
	}
}