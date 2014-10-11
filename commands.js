module.exports = {

	CreateTask : function(id, title, version) {
		this.id = id;
		this.title = title;
		this.version = version;
	},

	RenameTask : function (id, title, version) {
		this.id = id;
		this.title = title;
		this.version = version;
	},

	CompleteTask : function(id, version) {
		this.id = id;
		this.version = version;
	}
}
