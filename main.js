var app = require("./application.js");
var commands = require("./commands.js");

var id = app.generateId();

app.send(new commands.CreateTask(id, "new title", -1));

setTimeout(function() {
	while (app.read(id).version != 0) {
		 console.log("v0");
	}

	app.send(new commands.RenameTask(id, "new title 1", 0));

	setTimeout(function() {
		while (app.read(id).version != 1) {
			console.log("v1");
		}

		app.send(new commands.CompleteTask(id, 1));

		setTimeout(function() {
			while (app.read(id).version != 2) {
				console.log("v2");
			}

			var item = app.read(id);

			console.log("Here it is " + item.title + " " + item.completed);
		}, 0);
	}, 0);
}, 0);


