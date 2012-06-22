
var crudManager
var contextPath="/smartorg-web";
$(document).ready(function() {
	
	  crudManager = new CrudManager({
		url : contextPath+"/resource/"
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
		$.post(contextPath+"/role/grid",{rows:99}, function(data) {
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
		return ret;
	}
	function grantRole(){
		var roles=$('#roleId:checked');
		var ids = [];
		$.each(roles,function(i,role){
			ids.push(role.value);
		});
		if(roles){
			$.post(contextPath+"/resource/grant",{roleids:ids,resourceIds:crudManager.getSelectedIds(),type:$("#type").val()}, function(data) {
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