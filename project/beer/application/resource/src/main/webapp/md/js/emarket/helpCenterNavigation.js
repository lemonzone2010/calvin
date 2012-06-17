$(function(){
	initSortable();
})

/**
 * 初始化拖拽事件，默认该事件禁用
 */
function initSortable(){
	$("#parent_node").sortable({revert:true,update:function(event,ui){sortCallback(ui.item);}});
	$("#parent_node").find("div[id^='child_node_']").sortable({revert:true,update:function(event,ui){sortCallback(ui.item);}});
	sortDisable();
}

/**
 * 激活拖拽排序事件
 * @return
 */
function sortEnable(){	
	$("#parent_node").sortable('enable');
	$("#parent_node").find("div[id^='child_node_']").sortable('enable');
}

/**
 * 禁用拖拽排序事件
 * @return
 */
function sortDisable(){
	$("#parent_node").sortable('disable');
	$("#parent_node").find("div[id^='child_node_']").sortable('disable');
}

/**
 * 拖拽结束并且发生排序变化后的回调方法，记录发生排序节点ID和序号
 * @param object
 * @return
 */
function sortCallback(object){
	var moveParentNode = object.find("div[id^='add_node_']");
	var moveId = moveParentNode.find("input[name='hcnId']").val();
	var moveLevel = moveParentNode.find("input[name='level']").val();
	var moveParentId = moveParentNode.find("input[name='parentId']").val();
	
	var changeItemIds = new Array();
	var changeItemNumbers = new Array();
	var i = 0;
	$("#parent_node").find("div[id^='add_node_']").each(function(index){
		var this_level = $(this).find("input[name='level']").val();
		var this_id = $(this).find("input[name='parentId']").val();
	    if(this_level == moveLevel && this_id == moveParentId){
	        changeItemIds[i] = $(this).find("input[name='hcnId']").val();
	        changeItemNumbers[i] = index;
	        i++;
	    }
	});
	sortNewSerialNumber(changeItemIds,changeItemNumbers);
}

/**
 * 更新发生序号改变的节点
 * @param changeItemIds
 * @param changeItemNumbers
 * @return
 */
function sortNewSerialNumber(changeItemIds,changeItemNumbers){
	updateSerialNumber.addParam("changeIds",changeItemIds);
	updateSerialNumber.addParam("changeNumbers",changeItemNumbers);
	updateSerialNumber.submit();
}



function openOrCloseNode(id){
	if($("#child_node_"+id).css("display")=="none"){
		$("#child_node_"+id).show();
		$("#child_node_"+id).prev().prev().find(".openFolder").removeClass('openFolder').addClass('closeFolder');
		$("#child_node_"+id).prev().prev().find(".folder").removeClass('folder').addClass('folderopen');
	}else{
		$("#child_node_"+id).hide();
		$("#child_node_"+id).prev().prev().find(".closeFolder").removeClass('closeFolder').addClass('openFolder');
		$("#child_node_"+id).prev().prev().find(".folderopen").removeClass('folderopen').addClass('folder');
	}
}

function openOrCloseAll(oper){
	if(oper == 'close'){
		$(".finder-list").find("div[id^='child_node_']").each(function(){
			$(this).hide();
			$(this).prev().prev().find(".closeFolder").removeClass('closeFolder').addClass('openFolder');
		});
	}else{
		$(".finder-list").find("div[id^='child_node_']").each(function(){
			$(this).show();
			$(this).prev().prev().find(".openFolder").removeClass('openFolder').addClass('closeFolder');
		});
	}
}
function addNew(id){
	$("div[id^='add_node_']").each(function(){
		$(this).hide();
		$(this).find("input[type='text']").val('');
	});
	if(id > 0){
		$("#add_node_"+id).find("input[name='level']").val('2');
	}
	$("#add_node_"+id).find("input[name='hcnId']").val('0');
	$("#add_node_"+id).find("input[name='serialNumber']").val('999');
	$("#add_node_"+id).find("input[name='parentId']").val(id);
	$("#add_node_"+id).show();
}
function cancel(id){
	$("#add_node_"+id).hide();
}
function editRoot(id){
	$("#add_node_"+id).find("input[name='cssName']").parent().show();
	$("div[id^='add_node_']").each(function(){
		$(this).hide();
		$(this).find("input[type='text']").val('');
	});
	var name = $("#add_node_"+id).prev().find("div[class^='margin-left']").find("a").text();
	var cssName = $("#add_node_"+id).prev().find("input[name='cssName']").val();
	$("#add_node_"+id).find("input[name='cssName']").val(cssName);
	$("#add_node_"+id).find("input[name='name']").val(name);
	$("#add_node_"+id).find("input[name='level']").val('1');
	$("#add_node_"+id).find("input[name='hcnId']").val(id);
	$("#add_node_"+id).find("input[name='parentId']").val('0');
	$("#add_node_"+id).show();
}
function editChild(id){
	$("#add_node_"+id).find("input[name='cssName']").parent().hide();
	$("div[id^='add_node_']").each(function(){
		$(this).hide();
		$(this).find("input[type='text']").val('');
	});
	var name = $("#add_node_"+id).prev().find("div[class^='margin-left']").find("a").text();
	$("#add_node_"+id).find("input[name='name']").val(name);
	$("#add_node_"+id).find("input[name='level']").val('2');
	$("#add_node_"+id).find("input[name='hcnId']").val(id);
	$("#add_node_"+id).show();
}
function saveOrUpdate(id){
	var name = $("#add_node_"+id).find("input[name='name']").val();
	var level = $("#add_node_"+id).find("input[name='level']").val();
	var parentId = $("#add_node_"+id).find("input[name='parentId']").val();
	var hcnId = $("#add_node_"+id).find("input[name='hcnId']").val();
	var serialNumber = $("#add_node_"+id).find("input[name='serialNumber']").val();
	var cssName = $("#add_node_"+id).find("input[name='cssName']").val();
	saveItem.addParam("name",name);
	saveItem.addParam("level",level);
	saveItem.addParam("parentId",parentId);
	saveItem.addParam("hcnId",hcnId);
	saveItem.addParam("serialNumber",serialNumber);
	saveItem.addParam("cssName",cssName);
	saveItem.submit();
}
function del(id){
	var childrenLength = $("#child_node_"+id).find("div").length;
	var msg = "确认删除？";
	if(childrenLength > 0){
		msg = "删除该节点，其子节点也会一起删除，确认删除？";
	}
	if(confirm(msg)){
		deleteItem.addParam("hcnId",id);
		deleteItem.submit();
	}
}
function updateCallback(id,name,cssName){
	$("#add_node_"+id).prev().find("div[class^='margin-left']").find("a").text(name);
	$("#add_node_"+id).prev().find("input[name='cssName']").val(cssName);
	cancel(id);
}
function deleteCallback(id){
	$("#add_node_"+id).prev().remove();
	$("#add_node_"+id).remove();
	$("#child_node_"+id).remove();
}