var repo;

module.exports = {
	create : function(r) {
		repo = r;
		
		return this;
	},
	
	createTask : function (cmd) {
		
		var task = repo.get(cmd.id);

		repo.save(cmd.id, task.create(cmd.id, cmd.title), cmd.version);
	},
		
	renameTask : function (cmd) {
		
		var task = repo.get(cmd.id);
	
		repo.save(cmd.id, task.rename(cmd.title), cmd.version);
	},
		
	completeTask : function (cmd) {
		
		var task = repo.get(cmd.id);
	
		repo.save(cmd.id, task.complete(), cmd.version);
	}
}