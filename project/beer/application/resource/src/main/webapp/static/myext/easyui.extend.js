/**
 * CRUD操作的扩展，对easyui的扩展
 * @param config
 * @returns
 */
function CrudManager(config) {
	this.init(config || {}); 
}

CrudManager.prototype = {
		url:"",//定义CURD的相对路径，如/smartorg-web/role/
		dataGrid:"#dg",//定义easy ui data grid 的id
		pageSize:20,//定义data grid的page size
		editForm:"#addForm",//定义编辑框的id
		editFormId:"#addForm #id",//定义id字段的id
		editDialog:"#addDialog",//定义编辑dialog的id
		editButton:"#editButton",//定义datagrid tools上面的按钮id,变化状态用
		deleteButton:"#deleteButton",//定义datagrid tools上面的按钮id,变化状态用
		
		init:function(config){
			$.extend(this,config);
			this.initDataGrid();
			this.changeToolbarStatus();
			//$("#addForm").submit(this.edit());
		},
		
		initDataGrid:function(){
			var _self=this;
			$(this.dataGrid).datagrid({
				pageSize : _self.pageSize,
				onSelect : function() {
					_self.changeToolbarStatus();
				},
				onUnselect : function() {
					_self.changeToolbarStatus();
				},
			});
		},
		
		/**
		 * 添加或者修改表格
		 */
		edit:function(){

			if ($(this.editForm).form('validate') == false) {
				return false;
			}
			var formJson = $(this.editForm).serializeObject();
			var _url = this.url;
			if ($(this.editFormId).val()) {
				_url = this.url + "?_method=put"
			}
			var _self=this;
			$.postJSON(_url, formJson, function(data) {
				if (data.result.status) {
					$.messager.alert('信息', "操作成功", 'info');
					_self.reloadGridData();
					_self.closeDialog();					
				} else {
					$.messager.alert('信息', "数据操作失败，原因:" 	+ data.result.msg, 'info');
				}
				$(_self.dataGrid).datagrid('unselectAll');
			});
			return false;					
		},
		
		closeDialog:function(){
			$(this.editDialog).dialog("close");
		},
		
		reloadGridData:function(){
			$(this.dataGrid).datagrid("reload");
		},
		
		checkAvailability:function() {
			$.getJSON(this.url + "/check/name", {
				value : $('#name').val()
			}, function(result) {
				if (result.result.status == false) {
					fieldValidated("name", {
						valid : true
					});
				} else {
					fieldValidated("name", {
						valid : false,
						message : "Name:" + $('#name').val() + " is not available "
					});
				}
			});
		},
		
		deleteOne:function () {
			var row = $(this.dataGrid).datagrid('getSelected');

			if (row) {
				if (confirm('删除是不可恢复的操作，您确认删除?' + row.name)) {
					var _self=this;
					$.post(url + row.id, {
						_method : "delete"
					}, function(data) {
						$.messager.show({
							title : '信息',
							msg : '删除操作完成！',
							timeout : 2000,
							showType : 'fade'
						});

						_self.reloadGridData();
					});

				}
				return false;
			}
		},
		
		deleteSelected:function () {
			var ids = [];
			var rows = $(this.dataGrid).datagrid('getSelections');
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}

			if (rows.length > 0) {
				if (confirm('删除是不可恢复的操作，您确认删除选中的记录?')) {
					var _self=this;
					$.ajax({
						url : this.url,
						type : 'POST',
						data : {
							'items' : ids,
							_method : "delete"
						},
						dataType : 'JSON',
						success : function(data) {
							if (data.result.status) {
								$.messager.show({
									title : '信息',
									msg : '删除操作完成！',
									timeout : 2000,
									showType : 'fade'
								});
								_self.reloadGridData();
								$(_self.dataGrid).datagrid('unselectAll');
							} else {
								$.messager.show({
									title : '删除',
									msg : '您的删除操作失败:' + data.result.msg,
									timeout : 2000,
									showType : 'fade'
								});
							}
						}
					});

				}
			} else {
				$.messager.alert('提示', "请选择一行再继续操作.", 'info');
			}
		},
		getSelectedId:function () {
			var row = $(this.dataGrid).datagrid('getSelected');
			return row.id;
			/*if (row) {
				alert('Item ID:' + row.id + "\nPrice:" + row.name);
			}*/
		},

		getSelectedIds:function () {
			var ids = [];
			var rows = $(this.dataGrid).datagrid('getSelections');
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			//alert(ids.join('\n'));
			return ids;
		},
		search:function(value, name) {
			$(this.dataGrid).datagrid({
				pageNumber : 1,
				queryParams : {
					q : name + ":" + value
				}
			});
		},
		
		editUser:function () {
			var row = $(this.dataGrid).datagrid('getSelected');
			if (row) {
				$(this.editDialog).dialog('open').dialog('setTitle', '编辑');
				$(this.editForm).form('load', row);
			} else {
				$.messager.alert('提示', "请选择一行再操作", 'info');
			}
		},
		
		addUser:function () {
			$(this.editFormId).val('');
			$(this.editDialog).dialog('open').dialog('setTitle', '新增');
			$(this.editForm)[0].reset();
		},
		
		changeToolbarStatus:function () {
			var rows = $(this.dataGrid).datagrid('getSelections');
			if (rows.length == 1) {
				$(this.editButton).linkbutton('enable');
				$(this.deleteButton).linkbutton('enable');
				this.selectedOneStatus();
			} else if (rows.length > 1) {
				$(this.editButton).linkbutton('disable');
				$(this.deleteButton).linkbutton('enable');
				this.selectedMoreOneStatus();
			} else {
				$(this.editButton).linkbutton('disable');
				$(this.deleteButton).linkbutton('disable');
				this.selectedNoneStatus();
			}
		},
		selectedOneStatus:function(){
			
		},
		selectedMoreOneStatus:function(){
			
		},
		selectedNoneStatus:function(){
			
		}
}