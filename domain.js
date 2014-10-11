var events = require("./events.js");

module.exports = {
	
	Task : function () {
		var _id;
		
		this.create = function (id, title) {
			
			return events.TaskCreated(id, title);
		}
	
		this.rename = function (title) {
		
			if (!_id)
				throw "Not created yet"
		
			return events.TaskRenamed(_id, title);
		}
	
		this.complete = function () {

			if (!_id)
				throw "Not created yet"

			return events.TaskCompleted(_id);
		}
	
		this.apply = function (e) {
			if (e instanceof events.GetTaskCreated) {
				_id = e.id;
				return;
			}

			if (e instanceof events.GetTaskRenamed) {
				return;
			}

			if (e instanceof events.GetTaskCompleted) {
				return;
			}

			throw "Unhandled event";
		}
	}
}