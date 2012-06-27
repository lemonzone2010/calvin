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
	var moveId = moveParentNode.find("input[name='navId']").val();
	var moveLevel = moveParentNode.find("input[name='level']").val();
	var moveParentId = moveParentNode.find("input[name='parentId']").val();
	
	var changeItemIds = new Array();
	var changeItemNumbers = new Array();
	var i = 0;
	$("#parent_node").find("div[id^='add_node_']").each(function(index){
		var this_level = $(this).find("input[name='level']").val();
		var this_id = $(this).find("input[name='parentId']").val();
	    if(this_level == moveLevel && this_id == moveParentId){
	        changeItemIds[i] = $(this).find("input[name='navId']").val();
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
	$("#add_node_"+id).find("input[name='navId']").val('0');
	$("#add_node_"+id).find("input[name='sequence']").val('999');
	$("#add_node_"+id).find("input[name='parentId']").val(id);
	$("#add_node_"+id).show();
}
function cancel(id){
	$("#add_node_"+id).hide();
}
function editRoot(id){
	$("#add_node_"+id).find("input[name='url']").parent().hide();
	$("div[id^='add_node_']").each(function(){
		$(this).hide();
		$(this).find("input[type='text']").val('');
	});
	var name = $("#add_node_"+id).prev().find("div[class^='margin-left']").find("a").text();
	$("#add_node_"+id).find("input[name='name']").val(name);
	$("#add_node_"+id).find("input[name='level']").val('1');
	$("#add_node_"+id).find("input[name='navId']").val(id);
	$("#add_node_"+id).find("input[name='parentId']").val('0');
	$("#add_node_"+id).show();
}
function editChild(id){
	$("div[id^='add_node_']").each(function(){
		$(this).hide();
		$(this).find("input[type='text']").val('');
	});
	var name = $("#add_node_"+id).prev().find("div[class^='margin-left']").find("a").text();
	var url = $("#add_node_"+id).prev().find("input[name='url']").val();
	$("#add_node_"+id).find("input[name='name']").val(name);
	$("#add_node_"+id).find("input[name='url']").val(url);
	$("#add_node_"+id).find("input[name='level']").val('2');
	$("#add_node_"+id).find("input[name='navId']").val(id);
	$("#add_node_"+id).show();
}
function saveOrUpdate(id){
	var name = $("#add_node_"+id).find("input[name='name']").val();
	var url = $("#add_node_"+id).find("input[name='url']").val();
	var status = $("#add_node_"+id).find("input[name='status']").val();
	var level = $("#add_node_"+id).find("input[name='level']").val();
	var parentId = $("#add_node_"+id).find("input[name='parentId']").val();
	var appId = $("#appId").val();
	var navId = $("#add_node_"+id).find("input[name='navId']").val();
	var sequence = $("#add_node_"+id).find("input[name='sequence']").val();
	var data = {
		name : name,
		url : url,
		status : status,
		level : level,
		parentId : parentId,
		appId : appId,
		navId : navId,
		sequence : sequence
	};
	var postURL='/navigation-web/navigation/update';
	$.postJSON(postURL, data, function(data) {
		alert('success')
	});
/*	jQuery.ajax({
        'type': 'POST',
        'url': postURL,
        'contentType': 'application/json',
        'data': data,
        'dataType': 'json',
        'success': function(){
        	alert('succ')
        }
    });*/
}
function del(id){
	var childrenLength = $("#child_node_"+id).find("div").length;
	var msg = "确认删除？";
	if(childrenLength > 0){
		msg = "删除该节点，其子节点也会一起删除，确认删除？";
	}
	if(confirm(msg)){
		deleteItem.addParam("navId",id);
		deleteItem.submit();
	}
}
function updateItemStatus(id){
	var status;
	if($("#add_node_"+id).prev().find(".show_icon").length > 0){
		status = 'N';
	}else{
		status = 'Y';
	}
	updaeStatus.addParam("navId",id);
	updaeStatus.addParam("status",status);
	updaeStatus.submit();
}
function updateCallback(id,name,url){
	$("#add_node_"+id).prev().find("div[class^='margin-left']").find("a").text(name);
	$("#add_node_"+id).prev().find("input[name='url']").val(url);
	cancel(id);
}
function deleteCallback(id){
	$("#add_node_"+id).prev().remove();
	$("#add_node_"+id).remove();
	$("#child_node_"+id).remove();
}
function updateStatusCallback(id){
	if($("#add_node_"+id).prev().find(".show_icon").length > 0){
		$("#add_node_"+id).prev().find(".show_icon").removeClass("show_icon").addClass("hide_icon");
		$("#add_node_"+id).find("input[name='status']").val('N');
		$("#add_node_"+id).next().find(".row").each(function(){
			$(this).find(".show_icon").removeClass("show_icon").addClass("hide_icon");
			$(this).find("input[name='status']").val('N');
		});
	}else{
		$("#add_node_"+id).prev().find(".hide_icon").removeClass('hide_icon').addClass("show_icon");
		$("#add_node_"+id).find("input[name='status']").val('Y');
		$("#add_node_"+id).next().find(".row").each(function(){
			$(this).find(".hide_icon").removeClass('hide_icon').addClass("show_icon");
			$(this).find("input[name='status']").val('Y');
		});
	}
}