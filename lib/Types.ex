defmodule Command do
	defstruct id: 0, title: "", version: 0, type: :atom
end

defmodule Event do
	defstruct id: 0, version: 0, type: :atom, raised: 0, title: "", completed: false
end

defmodule TaskItem do
	defstruct version: 0, title: "", completed: false
end