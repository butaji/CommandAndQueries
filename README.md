Simple CQRS example (polyglot edition)
=============

This project inspired by [Greg Young](https://twitter.com/gregyoung)'s project https://github.com/gregoryyoung/m-r where he's expaining simplest possible objects to keep in mind then you serve CQRS solution.

Implementations
-------

Currently this project implemented in:

* [Java](https://github.com/butaji/CommandAndQueries/tree/java)
* [Go lang](https://github.com/butaji/CommandAndQueries/tree/golang)
* [Elixir](https://github.com/butaji/CommandAndQueries/tree/elixir)
* [Swift](https://github.com/butaji/CommandAndQueries/tree/swift)
* [JavaScript (node.js)](https://github.com/butaji/CommandAndQueries/tree/JavaScript)

Every new language will be in another next branch and include very similar solution to problem .

Solution
-----------

In this section I will try to describe in pseudo-code how each solution is represented:


	app = Application.new #it's application (our working horse to serve every routine work)
	
	id = Id.new
	
	app.send createTask id: id, title: "new title", version: -1
	
	wait_while app.read_by_id(id).version == 0 #we need this because event publishing is asynchronous and everything happens in a while
	
	app.send renameTask id: id, title: "new title 1", version: 0
	
	wait_while app.read_by_id(id).version == 1
	
	app.send completeTask id: id, version: 1
	
	wait_while app.read_by_id(id).version == 2
	
	task = app.read_by_id id
	
	print "Check out that everything is ok with our task #{task.title} #{task.completed}"

