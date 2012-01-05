<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>任务管理</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link href="${ctx}/static/mini-web.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/blueprint/1.0.1/print.css" type="text/css" rel="stylesheet" media="print" />
<link href="${ctx}/static/blueprint/src/grid.css" type="text/css" rel="stylesheet" media="screen, projection" />
<link href="${ctx}/static/blueprint/src/reset.css" type="text/css" rel="stylesheet" media="screen, projection" />
<link href="${ctx}/static/blueprint/src/typography.css" type="text/css" rel="stylesheet" media="screen, projection" />

<link href="${ctx}/static/jquery-jquery-1.8.6/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-jquery-1.8.6/themes/base/jquery.ui.dialog.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-jquery-1.8.6/themes/redmond/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui-1.2.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui-1.2.5/themes/icon.css">

<link href="${ctx}/static/jquery.json-2.3.js" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery-jquery-1.8.6/jquery-1.6.2.js" type="text/javascript"></script>

<script src="${ctx}/static/jquery-easyui-1.2.5/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-easyui-1.2.5/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>

<script src="${ctx}/static/myext/jquery.extend.js" type="text/javascript"></script>
<script src="${ctx}/static/myext/easyui.extend.js" type="text/javascript"></script>
<title>定时任务</title>
<script type="text/javascript">	

	serverUrl = "JobProcessServlet";
	
	function formatOperation(value,row){
			return '<a id="editButton" href="#" class="easyui-linkbutton" iconCls="icon-edit" disabled="true" plain="true" onclick="editUser();return false;">编辑</a>';
	}

</script>
</head>

<body>
<!-- start add -->
 <div id="addDialog"  icon="icon-save" class="easyui-dialog" title="添加记录" style="width:430px;height:400px;"
			 buttons="#dlg-buttons" resizable="true" closed="true">
			 <br/>
			 <br/>
			 
 <form:form  modelAttribute="addForm" action="jobEntity" method="post">
 <input type="hidden" name="id" id="id"/>
	<table id="addTable" align="center" cellpadding="0" cellspacing="7">
     <tr>
       <td class="addtd"><label for="jobName">任务名  </label></td>
       <td class="addtd">
        <input type="text" name="jobName" id="jobName" value=""            validtype="length[2,127]"           class="easyui-validatebox text"          />
        </td>
     </tr>
     <tr>
       <td class="addtd"><label for="jobClass">类/BEAN名  </label></td>
       <td class="addtd">
        <input type="text" name="jobClass" id="jobClass" value=""        validtype="length[2,127]"             class="easyui-validatebox text"          />
         <input type="checkbox" name="jobClassIsBeanName" id="jobClassIsBeanName" value="true" />是SpringBean?
        </td>
     </tr>
     <tr>
       <td class="addtd"><label for="jobMethod">方法名  </label></td>
       <td class="addtd">
        <input type="text" name="jobMethod" id="jobMethod" value=""       validtype="length[0,127]"              class="easyui-validatebox text"          />
        </td>
     </tr>
     <tr>
       <td class="addtd"><label for="jobCronExpress">表达式  </label></td>
       <td class="addtd">
        <input type="text" name="jobCronExpress" id="jobCronExpress" value=""       validtype="length[10,30]"              class="easyui-validatebox text"          />
        </td>
     </tr>
     <tr>
       <td class="addtd"><label for="jobDesc">任务描述  </label></td>
       <td class="addtd">
        <input type="text" name="jobDesc" id="jobDesc" value=""         validtype="length[0,200]"            class="easyui-validatebox text"          />
        </td>
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

		<div >定时任务管理.</div>

	</div>
	

 
<table id=dg class="easyui-datagrid" style="width:1200px;height:650px"
			url="JobProcessServlet?actionType=query" idField="id" 
			title="定时任务查看" iconCls="icon-save" toolbar="#toolbar"
			singleSelect="true" 
			rownumbers="true" pagination="true">
		<thead>
			<tr>
				<th field="jobName" width="150"  sortable="true" >任务名</th>
 				<th field="jobCronExpress" width="80" >表达式</th>
 				<th field="status" width="80" >状态</th>
 				<th field="jobDesc" width="200" >任务描述</th>
 				<th field="jobClass" width="180" >类/BEAN名</th>
 				<th field="jobMethod" width="60" >方法名</th>
 				<th field="jobClassIsBeanName" width="50" >类是BEAN</th>
 				<th field="nextExecTime" width="120" >下次执行时间</th>
 				<th field="lastExecTime" width="120" >上次执行时间</th>
 				<th field="jobUsedTime" width="50" >本次执行时长(MS)</th>
 				<th field="jobExecCount" width="50" >执行数</th>
 				<th field="jobExceptionCount" width="50" >异常数</th>
 				<th field="lastExeceptionTime" width="120" >上次异常时间</th>
 			</tr>
		</thead>
	</table>

<div id="tb" style="padding:5px;height:auto">

		<div id="toolbar">
		
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:addUser();return false;">新增</a>

		<a id="editButton" href="#" class="easyui-linkbutton" iconCls="icon-edit" disabled="true" plain="true" onclick="editUser();return false;">编辑</a>
		<a id="deleteButton" href="#" class="easyui-linkbutton" iconCls="icon-remove" disabled="true" plain="true" onclick="deleteSelected();return false;">删除所选</a>
		
		 <input id="ss" class="easyui-searchbox"
			searcher="qq"
			prompt="请输入查询条件,按回车开始查询" menu="#mm" style="width:400px"></input>
		<div id="mm" style="width:120px">
 				<div name="jobName" iconCls="icon-ok">任务名</div>
 				<div name="jobClass" iconCls="icon-ok">类/BEAN名</div>
	 				<div name="jobMethod" iconCls="icon-ok">方法名</div>
	 				<div name="jobDesc" iconCls="icon-ok">任务描述</div>
			</div>
	</div>

	</div>
</body>
</html>
