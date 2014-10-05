defmodule EventStore do
	
	def get_path(id) do
		"/Users/vbaum/Projects/EventStore/Storage/#{id}/"
	end
	
	def load_history_for(id) do
		
		path = get_path(id) 
		
		if (! File.exists?(path)) do
			[]
		else
			files = File.ls!(path) |> Enum.filter(fn x -> x != ".DS_Store" end)
			
			Enum.map(files, fn x -> 
			
				{_, c} = File.read("#{path}#{x}")
				IO.puts "file is read #{x}"
				{_, r} = JSON.decode(c)

				%Event{
					id: r["id"],
					raised: r["time"],
					title: r["title"],
					type: String.to_atom(r["type"]),
					version: r["version"]
				}
				
			end)
		end

	end
	
	def to_ticks({mg, s, mc}) do
	    mg*1000*1000*1000 + s * 1000 + mc
	end
	
	def save(e, id, v) do

		events = load_history_for(id)

		if length(events) - 1 != v do
			raise ArgumentError, message: "Concurrent access exception len:#{length(events)} and v:#{v}"
		end
		
		time = to_ticks(:os.timestamp)
		
		event_to_save = %Event{
			id: id, 
			version: length(events), 
			type: e.type, 
			raised: time, 
			title: e.title, 
			completed: e.completed
		}

		{_, content} = JSON.encode([
			id: id,
			time: time,
			title: e.title,
			type: e.type,
			version: length(events)
			])
		
		IO.puts content
		
		dir_path = get_path(id)
		path = "#{dir_path}#{time}.json"
		if File.write!(path, content) do
			IO.puts "file written to #{path}"
		end
		
		
		event_to_save
		
	end
	
	
end