var crudManager;
var contextPath="/foundation-web";
$(document).ready(function() {
	crudManager = new CrudManager({
		url : contextPath+"/organization/",
		unselectAll : function() {
			$(this.dataGrid).treegrid('unselectAll');
		},

		getSelectedRow : function() {
			return $(this.dataGrid).treegrid('getSelected');
		},

		getSelectedRows : function() {
			return $(this.dataGrid).treegrid('getSelections');
		},
		addUser : function() {
			var row = this.getSelectedRow();
			if (!row) {
				$.messager.alert('提示', "请选择一行再操作", 'info');
				return;
			}
			$(this.editFormId).val('');
			$(this.editDialog).dialog('open').dialog('setTitle', '新增');
			$(this.editForm)[0].reset();
			var id = row.id;
			if (id != null) {
				$('#parentId').val(id);
			} else {
				$('#parentId').val('');
			}
		},
		changeToolbarStatus : function() {

		},
		reloadGridData : function() {
			$(this.dataGrid).treegrid("reload");
		},
		editUser_ : function() {
			var row = this.getSelectedRow();
			if (!row) {
				$.messager.alert('提示', "请选择一行再操作", 'info');
				return;
			}
			if (row.id == 1) {
				$.messager.alert('提示', "此条记录不请允许操作", 'info');
				return;
			}
			this.editUser();
		},
		deleteSelected_ : function() {
			var row = this.getSelectedRow();
			if (!row) {
				$.messager.alert('提示', "请选择一行再操作", 'info');
				return;
			}
			if (row.id == 1) {
				$.messager.alert('提示', "此条记录不请允许操作", 'info');
				return;
			}
			this.deleteSelected();
		},
		collapseAll : function() {
			$(this.dataGrid).treegrid("collapseAll");
		},
		expandAll : function() {
			$(this.dataGrid).treegrid("expandAll");
		}
	});
});
