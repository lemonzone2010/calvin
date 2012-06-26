<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>组织管理</title>
<script src="/resource/xia/js/org.js" type="text/javascript"></script>
</head>

<body>
	<div class="demo-info" style="margin-bottom: -10px">
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

	<table id="dg" class="easyui-treegrid"  url="${ctx}/organization/gridtree" idField="id" 
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
	<div id="addDialog" icon="icon-save" class="easyui-dialog" title="添加记录" style="width: 600px; height: 350px;" buttons="#dlg-buttons" resizable="true"
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
			        		       />须唯一，不能重复
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
