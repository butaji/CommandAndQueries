defmodule EventBus do
	
	def publish(e, app) do
		IO.puts "publishing event"
		IO.inspect e
		send app.busId, e
	end
	
end