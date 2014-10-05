defmodule Repo do
	
	def get(id) do
	    events = EventStore.load_history_for(id)
		
		IO.puts "loaded history"
		IO.inspect events
		
		Enum.reduce(
			events,
			%DomainTask{},
			fn(e, acc) ->
				IO.inspect acc
				DomainTask.apply(acc, e) end)

	end
	
	def save(e, id, v, app) do
	    EventStore.save(e, id, v) |> EventBus.publish(app)
	end
	
	
end