<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>组织管理</title>
<!-- <script src="/resource/xia/js/easyui.treegrid.extend.js" type="text/javascript"></script> -->
<script type="text/javascript">
var crudManager
$(document).ready(function() {
	
	  crudManager = new CrudManager({
		url : "${ctx}/organization/",
			unselectAll : function() {
				$(this.dataGrid).treegrid('unselectAll');
			},
			
			getSelectedRow : function() {
				return $(this.dataGrid).treegrid('getSelected');
			},
			
			getSelectedRows : function() {
				return $(this.dataGrid).treegrid('getSelections');
			},
			addUser:function () {
				var row = this.getSelectedRow();
				if(!row){
					$.messager.alert('提示', "请选择一行再操作", 'info');
					return;
				}
				$(this.editFormId).val('');
				$(this.editDialog).dialog('open').dialog('setTitle', '新增');
				$(this.editForm)[0].reset();
				var id=row.id;
				if(id!=null){
					$('#parentId').val(id);
		  		}else{
		  			$('#parentId').val('');
		  		}
			},
			changeToolbarStatus:function () {
				
			},reloadGridData:function(){
				$(this.dataGrid).treegrid("reload");
			},
			editUser_:function () {
				var row = this.getSelectedRow();
				if(!row){
					$.messager.alert('提示', "请选择一行再操作", 'info');
					return;
				}
				if(row.id==1){
					$.messager.alert('提示', "此条记录不请允许操作", 'info');
					return;
				}
				this.editUser();
			},
			deleteSelected_:function () {
				var row = this.getSelectedRow();
				if(!row){
					$.messager.alert('提示', "请选择一行再操作", 'info');
					return;
				}
				if(row.id==1){
					$.messager.alert('提示', "此条记录不请允许操作", 'info');
					return;
				}
				this.deleteSelected();
			}
	});
});
	/**
	定义搜索用的查询函数
	*/
	function search(value, name){
		crudManager.search(value, name);
	}
</script>
</head>

<body>
	<div class="demo-info" style="margin-bottom: 10px">
		<div class="demo-tip icon-tip"></div>
		<div>组织管理.</div>
	</div>

	<div id="tb" style="padding: 5px; height: auto">
		<div id="toolbar">

			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:crudManager.addUser();return false;">新增</a>
			<a id="editButton" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="crudManager.editUser_();return false;">编辑</a>
 			<a id="deleteButton" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="crudManager.deleteSelected_();return false;">删除所选</a>
 			
		</div>
	</div>

	<table id="dg" class="easyui-treegrid" style="width: 950px; height: 400px" url="${ctx}/organization/gridtree" idField="id" 
			title="组织查看" iconCls="icon-save"  treeField="name" fitColumns="true"
		toolbar="#toolbar" singleSelect="true" rownumbers="true" pagination="false">
		<thead>
			<tr>
				<th field="name" width="150" >组织简称</th>
				<th field="orgCode" width="150" >编码</th>
				<th field="fullName" width="150" >组织全称</th>
				<th field="description" width="150" >组织描述</th>
				 							</tr>
		</thead>
	</table>

	<!-- start add -->
	<div id="addDialog" icon="icon-save" class="easyui-dialog" title="添加记录" style="width: 560px; height: 380px;" buttons="#dlg-buttons" resizable="true"
		closed="true">
		<br /> <br />
		<form:form modelAttribute="addForm" action="" method="post">
			<input type="hidden" name="id" id="id" />
			<input type="hidden" name="parentId" id="parentId" />
			<table class="form-table">
									     <tr>
			       <th><label for="orgCode">编码  <font color="red">*</font>  </label></th>
			       <td>
			        <input type="text" name="orgCode" id="orgCode" value=""   required="true" 
			        	  validType="length[2,5]" 
			        	    
			        	      missingMessage="编码不能为空"          class="easyui-validatebox text" 
			        		       />
			        </td>
			     </tr>
												     <tr>
			       <th><label for="name">组织简称  <font color="red">*</font>  </label></th>
			       <td>
			        <input type="text" name="name" id="name" value=""   required="true" 			          validtype="length[2,20]" 			          class="easyui-validatebox text" 			         />
			        </td>
			     </tr>
												     <tr>
			       <th><label for="fullName">组织全称  </label></th>
			       <td>
			        <input type="text" name="fullName" id="fullName" value=""  			          validtype="length[0,40]" 			          class="easyui-validatebox text" 			         />
			        </td>
			     </tr>
												     <tr>
			       <th><label for="description">组织描述  </label></th>
			       <td>
			        <input type="text" name="description" id="description" value=""  			          validtype="length[0,40]" 			          class="easyui-validatebox text" 			         />
			        </td>
			     </tr>
									</table>
		</form:form>
	</div>

	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" onclick="javascript:crudManager.edit();">保存</a> <a href="#" class="easyui-linkbutton"
			onclick="javascript:crudManager.closeDialog()">关闭</a>
	</div>
	<!-- end add -->
<table id="test"></table>
</body>
</html>
