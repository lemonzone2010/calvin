<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>物料类型</title>
<script type="text/javascript">	
		serverUrl="materialType";
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
 <form:form  modelAttribute="addForm" action="materialType" method="post">
 <input type="hidden" name="id" id="id"/>
	<table style="float:center">
     <tr>
       <td><label for="name">名称</label>
        <input type="text" name="name" id="name" value="6666" class="easyui-validatebox text" required="true"/></td>
     </tr>
     <tr>
       <td><label for="desc">描述</label>
        <input type="text" name="desc" id="desc" value="33333" class="text"/></td>
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

		<div >物料类型管理.</div>

	</div>
	

 
<table id=dg class="easyui-datagrid" style="width:950px;height:400px"
			url="materialType/grid" idField="id" 
			title="物料类型数据查看" iconCls="icon-save" toolbar="#toolbar"
			singleSelect="false" 
			rownumbers="true" pagination="true">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="name" width="220"  sortable="true" editor="text">名称</th>
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
		<div name="name" iconCls="icon-ok">名称</div>
		<div name="desc" iconCls="icon-edit">描述</div>
	</div>
	</div>

	</div>

</body>
</html>
