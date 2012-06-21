<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色管理</title>
<script type="text/javascript">
var crudManager
$(document).ready(function() {
	
	  crudManager = new CrudManager({
		url : "/smartorg-web/role/"
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
		<div>角色管理.</div>
	</div>

	<div id="tb" style="padding: 5px; height: auto">
		<div id="toolbar">

			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:crudManager.addUser();return false;">新增</a>
			<a id="editButton" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="crudManager.editUser();return false;">编辑</a>
 			<a id="deleteButton" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="crudManager.deleteSelected();return false;">删除所选</a>
 			
			<input id="ss" class="easyui-searchbox" searcher="search" prompt="请输入搜索条件" menu="#mm" style="width: 300px"></input>
			<div id="mm" style="width: 120px">
				<div name="name" iconCls="icon-ok">角色</div>
				<div name="alias" iconCls="icon-edit">角色名称</div>
			</div>

		</div>
	</div>

	<table id="dg" class="easyui-datagrid" style="width: 950px; height: 400px" url="/smartorg-web/role/grid" idField="id" title="角色查看" iconCls="icon-save"
		toolbar="#toolbar" singleSelect="false" rownumbers="true" pagination="true">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="name" width="220" sortable="true" editor="text">角色</th>
				<th field="alias" width="220" sortable="true" editor="text">角色名称</th>
				<th field="desc" width="400">描述</th>
			</tr>
		</thead>
	</table>
	


	<!-- start add -->
	<div id="addDialog" icon="icon-save" class="easyui-dialog" title="添加记录" style="width: 460px; height: 280px;" buttons="#dlg-buttons" resizable="true"
		closed="true">
		<br /> <br />
		<form:form modelAttribute="addForm" action="" method="post">
			<input type="hidden" name="id" id="id" />
			<table class="form-table">
				<tr>
					<th><label>角色* </label></th><td><input type="text" name="name" id="name" value="" class="easyui-validatebox text" required="true" validType="length[1,20]"/></td>
				</tr>
				<tr>
					<th><label>角色名称*</label> </th><td><input type="text" name="alias" id="alias" value="" class="easyui-validatebox text" required="true"  validType="length[1,30]"/></td>
				</tr>
				<tr>
					<th><label>描述</label> </th><td><input type="text" name="desc" id="desc" value="" class="text" /></td>
				</tr>
			</table>
		</form:form>
	</div>

	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" onclick="javascript:crudManager.edit();">保存</a> <a href="#" class="easyui-linkbutton"
			onclick="javascript:crudManager.closeDialog()">关闭</a>
	</div>
	<!-- end add -->
	
</body>
</html>
