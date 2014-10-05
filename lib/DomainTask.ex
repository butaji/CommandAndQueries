defmodule DomainTask do
	defstruct id: 0, title: "", completed: false
	
	def create(task, title) do
		%Event{
			id: task.id,
			title: title,
			type: :taskCreated
		}
	end

	def rename(task, title) do
		%Event{
			id: task.id,
			title: title,
			type: :taskRenamed
		}
	end

	def complete(task) do
		%Event{
			id: task.id,
			completed: true,
			type: :taskCompleted
		}
	end
	
	
	def apply(task, e) do
		
		apply_change(task, e)
	end
	
	def apply_change(_, %Event { type: :taskCreated, id: id, title: title }) do
		
		%DomainTask {
			id: id,
			title: title
		}
	end

	def apply_change(t, %Event { type: :taskRenamed, title: title}) do
		
		%DomainTask {
			id: t.id,
			title: title,
			completed: t.completed
		}
	end

	def apply_change(t, %Event { type: :taskCompleted }) do
		
		%DomainTask {
			id: t.id,
			title: t.title,
			completed: true
		}
	end
	
	
end