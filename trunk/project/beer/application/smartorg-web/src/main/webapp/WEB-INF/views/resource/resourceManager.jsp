<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源</title>
<script type="text/javascript">
var crudManager
$(document).ready(function() {
	
	  crudManager = new CrudManager({
		url : "${ctx}/resource/"
	});
	
	  //设置状态
	  crudManager.selectedOneStatus=function(){
		 	 $("#roleAddButton").linkbutton('enable');
		 	 $("#roleCancelButton").linkbutton('enable');			
		};
		
		crudManager.selectedMoreOneStatus=function(){
		  	$("#roleAddButton").linkbutton('disable');
		  	$("#roleCancelButton").linkbutton('disable');	
		};
		
		crudManager.selectedNoneStatus=function(){
		 	$("#roleAddButton").linkbutton('disable');
		  	$("#roleCancelButton").linkbutton('disable');	
		};
		
		crudManager.changeToolbarStatus();
	
});
	/**
	定义搜索用的查询函数
	*/
	function search(value, name){
		crudManager.search(value, name);
	}
	function openRoleDialog(type){
		$("#ids").val(crudManager.getSelectedIds().join(','));
		$("#type").val(type);
		$('#grant_a .l-btn-text').text(type=='grant'?"授权":"取消授权");
		var roles="";
		$.post("${ctx}/role/grid",{rows:99}, function(data) {
			//alert(data.rows)
			$.each(data.rows,function(key,obj){ 
				roles+=('<div><label><input type="checkbox" id="roleId" name="roleId" value="'+obj.id+'">'+obj.alias+'</label></div><br/>');
				});
			$('#roleForm td').html(roles);
		});
		$('#roleDialog').dialog("open");
		
		//与open有冲突，要加上0.1秒的缓冲
		setTimeout(function(){
		$('input#roleId').each(function(){
			var needchecked=isSelectedRole($(this).parent().text());
			if(needchecked){
				$(this).attr("checked",true);
			}
		})
		},100);
	}
	function isSelectedRole(roleName){
		var ret=false;
		var roleNames=crudManager.getSelectedRow().roleForString.split(",");
		$.each(roleNames, function(index, value) { 
			  if(value==roleName){
				  ret = true;
			  }
			});
			//  alert(ret);
		return ret;
	}
	function grantRole(){
		var roles=$('#roleId:checked');
		var ids = [];
		$.each(roles,function(i,role){
			ids.push(role.value);
		});
		if(roles){
			$.post("${ctx}/resource/grant",{roleids:ids,resourceIds:crudManager.getSelectedIds(),type:$("#type").val()}, function(data) {
				if (data.result.status) {
					$.messager.show({
						title : '信息',
						msg : '授权操作完成！',
						timeout : 2000,
						showType : 'fade'
					});
					crudManager.reloadGridData();
					$(crudManager.dataGrid).datagrid('unselectAll');
					$('#roleDialog').dialog("close");
				} else {
					$.messager.show({
						title : '信息',
						msg : '授权操作失败:' + data.result.msg,
						timeout : 2000,
						showType : 'fade'
					});
				}
			});
		}
		return false;
	}
</script>
</head>

<body>
	<div class="demo-info" style="margin-bottom: 10px">
		<div class="demo-tip icon-tip"></div>
		<div>资源管理.</div>
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
												<div name="resName" iconCls="icon-ok">资源名</div>
																<div name="resType" iconCls="icon-ok">资源类型</div>
																<div name="resValue" iconCls="icon-ok">资源</div>
																<div name="resContext" iconCls="icon-ok">所属工程</div>
											</div>

		</div>
	</div>


	<table id="dg" class="easyui-datagrid" style="width: 950px; height: 400px" url="${ctx}/resource/grid" idField="id" 
			title="资源查看" iconCls="icon-save"
		toolbar="#toolbar" singleSelect="false" rownumbers="true" pagination="true">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
												<th field="resName" width="150"  sortable="true" >资源名</th>
				 												<th field="resType" width="50" >资源类型</th>
				 												<th field="resValue" width="150" >资源</th>
				 												<th field="resContext" width="150" >所属工程</th>
				 												<th field="roleForString" width="150" >角色</th>
				 												<th field="desc" width="150" >描述</th>
				 							</tr>
		</thead>
	</table>

	<!-- start add -->
	<div id="addDialog" icon="icon-save" class="easyui-dialog" title="添加记录" style="width: 490px; height: 430px;" buttons="#dlg-buttons" resizable="true"
		closed="true">
		<br /> <br />
		<form:form modelAttribute="addForm" action="" method="post">
			<input type="hidden" name="id" id="id" />
			<table class="form-table">
								     <tr>
			       <th><label for="resName">资源名  <font color="red">*</font>  </label></th>
			       <td>
			        <input type="text" name="resName" id="resName" value=""   required="true" 			          validtype="length[2,50]" 			          class="easyui-validatebox text" 			         />
			        </td>
			     </tr>
												     <tr>
			       <th><label for="resType">资源类型  <font color="red">*</font>  </label></th>
			       <td>
			        <input type="text" name="resType" id="resType" value=""   required="true" 			          validtype="length[0,127]" 			          class="easyui-validatebox text" 			         />
			        </td>
			     </tr>
												     <tr>
			       <th><label for="resValue">资源  <font color="red">*</font>  </label></th>
			       <td>
			        <input type="text" name="resValue" id="resValue" value=""   required="true" 			          validtype="length[0,250]" 			          class="easyui-validatebox text" 			         />
			        </td>
			     </tr>
												     <tr>
			       <th><label for="resContext">所属工程  <font color="red">*</font>  </label></th>
			       <td>
			        <input type="text" name="resContext" id="resContext" value=""   required="true" 			          validtype="length[0,127]" 			          class="easyui-validatebox text" 			         />
			        </td>
			    <!--  </tr>
												     <tr>
			       <th><label for="priority">级别  </label></th>
			       <td>
			        <input type="text" name="priority" id="priority" value=""  			          validtype="length[0,127]" 			          class="easyui-validatebox text" 			         />
			        </td>
			     </tr> -->
												     <tr>
			       <th><label for="desc">描述  </label></th>
			       <td>
			        <input type="text" name="desc" id="desc" value=""  			          validtype="length[0,250]" 			          class="easyui-validatebox text" 			         />
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
	
	<div id="roleDialog" icon="icon-save" class="easyui-dialog" title="角色授权" style="width: 490px; height: 430px;" buttons="#role-buttons" resizable="true"
		closed="true">
			<div class="demo-info" style="margin-bottom: 10px">
				<div class="demo-tip icon-tip"></div>
				<div>将所选资源授权给以下角色.</div>
		</div>
		<form:form modelAttribute="roleForm" action="" method="post">
			<input type="hidden" name="ids" id="ids" />
			<input type="hidden" name="type" id="type" />
			<table class="form-table">
			     <tr>
			     <th>&nbsp;</th><td></td>
			     </tr>
			 </table>
	   </form:form>
	  </div>
	  <div id="role-buttons">
		<a href="#" id="grant_a" class="easyui-linkbutton" onclick="javascript:grantRole();">授权</a> 
		<a href="#" class="easyui-linkbutton"	onclick="javascript:$('#roleDialog').dialog('close');">关闭</a>
	</div>

</body>
</html>
