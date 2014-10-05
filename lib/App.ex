defmodule App do
	defstruct busId: ""

	def get_path(id) do
		"/Users/vbaum/Projects/ReadModel/#{id}.json"
	end

	def put_to_file(id, item) do
		{_,c} = JSON.encode([
				title: item.title,
				completed: item.completed,
				version: item.version
			])

		IO.puts "writing #{c}"

		path = get_path(id)
		File.write!(path, c)
	end
	
	def read_from_file(id) do
		
		path = get_path(id)
		
		if (File.exists? path) do
			{_,c} = File.read(path)
			
			{_, r} = JSON.decode(c)
			
			%TaskItem{
				title: r["title"],
				completed: r["completed"],
				version: r["version"]
			}
			
		else
			%TaskItem{
				version: -1
			}
		end
	end
	

	def handle_event do
	    receive do
			%Event{ type: :taskCreated, 
				id: id, title: title, version: version } ->
				
				put_to_file(id, %TaskItem{
					title: title,
					version: version
					})

				handle_event()

			%Event{ type: :taskRenamed, 
				id: id, title: title, version: version } ->
			
				t = read(id)
			
				put_to_file(id, %TaskItem{
					title: title,
					version: version,
					completed: t.completed
					})

				handle_event()
		
			%Event{ type: :taskCompleted, 
				id: id, completed: completed, version: version } ->
		
				t = read(id)
		
				put_to_file(id, %TaskItem{
					title: t.title,
					version: version,
					completed: completed
					})
			

				handle_event()


			x ->
				IO.puts "YAY! received"
				IO.inspect x
				handle_event()
	    end
	end

	def init do
		
		pid = spawn(fn -> handle_event end)
		
		%App {
			busId: pid
		}
	end

	def send(app, cmd) do
		CommandHadlers.handle(app, cmd)
	end
	
	def read(id) do
		
		read_from_file(id)
	end
	
end