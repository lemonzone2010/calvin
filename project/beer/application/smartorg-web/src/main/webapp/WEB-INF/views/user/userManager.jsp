<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<script src="/resource/xia/js/smartorg/user.js" type="text/javascript"></script>
</head>

<body>
	<div class="demo-info" style="margin-bottom: 10px">
		<div class="demo-tip icon-tip"></div>
		<div>用户管理.</div>
	</div>

	<div id="tb" style="padding: 5px; height: auto">
		<div id="toolbar">

			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:crudManager.addUser();return false;">新增</a>
			<a id="editButton" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="crudManager.editUser();return false;">编辑</a>
 			<a id="deleteButton" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="crudManager.deleteSelected();return false;">删除所选</a>
 			<a id="roleAddButton" href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="openRoleDialog('grant');return false;">授权</a>
 			<a id="roleCancelButton" href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="openRoleDialog('ungrant');return false;">取消授权</a>
 			
			<input id="ss" class="easyui-searchbox" searcher="search" prompt="请输入搜索条件" menu="#mm" style="width: 300px"></input>
			<div id="mm" style="width: 140px">
												<div name="name" iconCls="icon-ok">用户名</div>
											</div>

		</div>
	</div>


	<table id="dg" class="easyui-datagrid" style="width: 950px; height: 400px" url="${ctx}/user/grid" idField="id" 
			title="用户查看" iconCls="icon-save"
		toolbar="#toolbar" singleSelect="false" rownumbers="true" pagination="true">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
												<th field="name" width="150"  sortable="true" >用户名</th>
				 												<th field="roleForString" width="400" >角色</th>
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
			       <th><label for="name">用户名  <font color="red">*</font>  </label></th>
			       <td>
			        <input type="text" name="name" id="name" value=""   required="true" 			          validtype="length[4,50]" 			          class="easyui-validatebox text" 			         />
			        </td>
			     </tr>
												     <tr>
			       <th><label for="password">密码  <font color="red">*</font>  </label></th>
			       <td>
			        <input type="password" name="password" id="password" value=""   required="true" 			          validtype="length[6,50]" 			          class="easyui-validatebox text" 			         />
			        </td>
			     </tr>
					 <tr>
			       <th><label for="confirmpassword">密码  <font color="red">*</font>  </label></th>
			       <td>
			        <input type="password" cid="confirmpassword" value=""  	          class="easyui-validatebox text" 			         />
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
<%@ include file="/WEB-INF/views/role/roleDialog.jspf" %>
</body>
</html>
