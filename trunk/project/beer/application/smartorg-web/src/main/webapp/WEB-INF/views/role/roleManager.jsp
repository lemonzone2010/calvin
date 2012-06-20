<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色管理</title>
<script type="text/javascript">	
		serverUrl="/smartorg-web/role/";
		function editUser() {
		    var row = $('#dg').datagrid('getSelected');
		    if (row) {
		        $('#addDialog').dialog('open').dialog('setTitle', '编辑');
		        $('#addForm').form('load', row);
			    }else{
			    	$.messager.alert('提示', "请选择一行再操作", 'info');
			    }
		}
		function addUser(){
			$('#id').val('');
			$('#addDialog').dialog('open').dialog('setTitle', '新增');
			$('#addForm')[0].reset();
		}
	</script>
</head>

<body>
<!-- start add -->
 <div id="addDialog"  icon="icon-save" class="easyui-dialog" title="添加记录" style="width:380px;height:200px;"
			 buttons="#dlg-buttons" resizable="true" closed="true">
			 <br/>
			 <br/>
 <form:form  modelAttribute="addForm" action="" method="post">
 <input type="hidden" name="id" id="id"/>
	<table style="float:center">
     <tr>
       <td><label>角色
        <input type="text" name="name" id="name" value="" class="easyui-validatebox text" required="true"/></td></label>
     </tr>
     <tr>
       <td><label>角色名称
        <input type="text" name="alias" id="alias" value="" class="easyui-validatebox text" required="true"/></td></label>
     </tr>
     <tr>
       <td><label>描述
        <input type="text" name="desc" id="desc" value="" class="text"/></td></label>
     </tr>
   </table>
</form:form>
 </div>
 
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" onclick="javascript:$('#addForm').submit();">保存</a>
		<a href="#" class="easyui-linkbutton" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	</div>
 
 <!-- end add -->

	<div class="demo-info" style="margin-bottom:10px">

		<div class="demo-tip icon-tip"></div>

		<div >角色管理.</div>

	</div>
	

 
<table id=dg class="easyui-datagrid" style="width:950px;height:400px"
			url="/smartorg-web/role/grid" idField="id" 
			title="角色查看" iconCls="icon-save" toolbar="#toolbar"
			singleSelect="false" 
			rownumbers="true" pagination="true">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="name" width="220"  sortable="true" editor="text">角色</th>
				<th field="alias" width="220"  sortable="true" editor="text">角色名称</th>
				<th field="desc" width="400">描述</th>
			</tr>
		</thead>
	</table>
<div id="tb" style="padding:5px;height:auto">

		<div id="toolbar">
		
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:addUser();return false;">新增</a>

		<a id="editButton" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser();return false;">编辑</a>
		<a id="deleteButton" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteSelected();return false;">删除所选</a>
		
		 <input id="ss" class="easyui-searchbox"
			searcher="qq"
			prompt="Please Input Value" menu="#mm" style="width:300px"></input>
	<div id="mm" style="width:120px">
		<div name="name" iconCls="icon-ok">角色</div>
		<div name="alias" iconCls="icon-edit">角色名称</div>
	</div>
	</div>

	</div>

</body>
</html>
