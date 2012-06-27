/**
 * 须依赖CrudManager,jquery
 * 
 * @param config
 */
function TreeGridCrudManager(config) {
	CrudManager.apply(this, arguments);
}
$.extend(TreeGridCrudManager.prototype, CrudManager.prototype, {
	init : function(config) {
		CrudManager.prototype["init"].apply(this, arguments);
	},
	
	unselectAll : function() {
		$(this.dataGrid).treegrid('unselectAll');
	},
	
	getSelectedId : function() {
		var row = this.getSelectedRow();
		if (row == null)
			return null;
		return row.id;
	},
	
	getSelectedRow : function() {
		return $(this.dataGrid).treegrid('getSelected');
	},
	
	getSelectedRows : function() {
		return $(this.dataGrid).treegrid('getSelections');
	},
});