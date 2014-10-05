defmodule CommandHadlers do
	
	def handle(app, cmd) do
		Repo.get(cmd.id) |>
			domain_event(cmd) |>
			Repo.save(cmd.id, cmd.version, app)
	end
	
	def domain_event(task, %Command{ type: :createTask, title: title }) do
		IO.puts "========= ::createTask"
		DomainTask.create(task, title)
	end
	
	def domain_event(task, %Command{ type: :renameTask, title: title }) do
		IO.puts "========= ::renameTask"
		DomainTask.rename(task, title)	
	end
	
	def domain_event(task, %Command{ type: :completeTask }) do
		IO.puts "========= ::completeTask"
		DomainTask.complete(task)
	end
	
end