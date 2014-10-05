defmodule Cc1 do

	require App
	
	def start do
		
		id = 123
		
		app = App.init
		
		App.send(
			app,
			%Command{
					id: id, 
					type: :createTask,
					title: "new item",
					version: -1
				})

		wait_while_cmd_applied(id, 0)
		
		App.send(
			app,
			%Command{
					id: id, 
					title: "new item 1",
					type: :renameTask,
					version: 0
				})

		wait_while_cmd_applied(id, 1)
		
		App.send(
			app,
			%Command{
					id: id, 
					type: :completeTask,
					version: 1
				})

		wait_while_cmd_applied(id, 2)
		
		task = App.read(id)
		
		IO.puts "Here it is #{task.title} + #{task.completed}"
		
	end
	
	def wait_while_cmd_applied(id,v) do
		IO.puts "Waiting for v#{v}"
		if (App.read(id).version != v) do
			wait_while_cmd_applied(id,v)
		end
	end
	

end

# Cc1.start