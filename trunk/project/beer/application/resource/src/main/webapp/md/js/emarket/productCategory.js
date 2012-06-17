$(function(){
	initSortable();
})

/**
 * 初始化拖拽事件，默认该事件禁用
 */
function initSortable(){
	$("#parent_node").sortable({revert:true,update:function(event,ui){sortCallback(ui.item);}});
	$("#parent_node").find("div[id^='child_node_']").sortable({revert:true,update:function(event,ui){sortCallback(ui.item);}});
	$("#parent_node").find("div[id^='leaf_node_']").sortable({revert:true,update:function(event,ui){sortCallback(ui.item);}});
	sortDisable();
}

/**
 * 激活拖拽排序事件
 * @return
 */
function sortEnable(){	
	$("#parent_node").sortable('enable');
	$("#parent_node").find("div[id^='child_node_']").sortable('enable');
	$("#parent_node").find("div[id^='leaf_node_']").sortable('enable');
}

/**
 * 禁用拖拽排序事件
 * @return
 */
function sortDisable(){
	$("#parent_node").sortable('disable');
	$("#parent_node").find("div[id^='child_node_']").sortable('disable');
	$("#parent_node").find("div[id^='leaf_node_']").sortable('disable');
}

/**
 * 拖拽结束并且发生排序变化后的回调方法，记录发生排序节点ID和序号
 * @param object
 * @return
 */
function sortCallback(object){
	var moveParentNode = object.find(".row").first();
	var moveId = moveParentNode.find("input[name='categoryId']").val();
	var moveLevel = moveParentNode.find("input[name='level']").val();
	var moveParentId = moveParentNode.find("input[name='parentId']").val();
	var changeItemIds = new Array();
	var changeItemNumbers = new Array();
	var i = 0;
	$("#parent_node").find(".row").each(function(index){
		var this_level = $(this).find("input[name='level']").val();
		var this_id = $(this).find("input[name='parentId']").val();
	    if(this_level == moveLevel && this_id == moveParentId){
	        changeItemIds[i] = $(this).find("input[name='categoryId']").val();
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

/**
 * 解决ajaxUpdate reload后标签移位问题
 * @return
 */
function initUpdate(){
	var output = $("#attrUpdate").clone(true);
	$("#attrUpdate").remove();
	$("#category_add_property").find(".finder-head").after(output);
}

/**
 * 展开或收起节点
 * @param id
 * @return
 */
function openOrCloseNode(id){
	if($("#"+id).css("display")=="none"){
		$("#"+id).show();
		$("#"+id).prev().find(".openFolder").removeClass('openFolder').addClass('closeFolder');
		$("#"+id).prev().find(".folder").removeClass('folder').addClass('folderopen');
	}else{
		$("#"+id).hide();
		$("#"+id).prev().find(".closeFolder").removeClass('closeFolder').addClass('openFolder');
		$("#"+id).prev().find(".folderopen").removeClass('folderopen').addClass('folder');
	}
}

/**
 * 页面刷新后，展开该ID下面所有节点
 * @param id
 * @return
 */
function openHistory(id){
	if($("#category_node_"+id).parents("div[id^='child_node_']").css("display")=="none"){
		$("#category_node_"+id).parents("div[id^='child_node_']").show();
		$("#category_node_"+id).parents("div[id^='child_node_']").prev().find(".openFolder").removeClass('openFolder').addClass('closeFolder');
		$("#category_node_"+id).parents("div[id^='child_node_']").prev().find(".folder").removeClass('folder').addClass('folderopen');
		$("#category_node_"+id).parents("div[id^='leaf_node_']").show();
		$("#category_node_"+id).parents("div[id^='leaf_node_']").prev().find(".openFolder").removeClass('openFolder').addClass('closeFolder');
		$("#category_node_"+id).parents("div[id^='leaf_node_']").prev().find(".folder").removeClass('folder').addClass('folderopen');
	}else{
		$("#category_node_"+id).parents("div[id^='child_node_']").hide();
		$("#category_node_"+id).parents("div[id^='child_node_']").prev().find(".closeFolder").removeClass('closeFolder').addClass('openFolder');
		$("#category_node_"+id).parents("div[id^='child_node_']").prev().find(".folderopen").removeClass('folderopen').addClass('folder');
		$("#category_node_"+id).parents("div[id^='leaf_node_']").hide();
		$("#category_node_"+id).parents("div[id^='leaf_node_']").prev().find(".closeFolder").removeClass('closeFolder').addClass('openFolder');
		$("#category_node_"+id).parents("div[id^='leaf_node_']").prev().find(".folderopen").removeClass('folderopen').addClass('folder');
	}
}

/**
 * 全部展开或者全部收起
 * @param oper
 * @return
 */
function openOrCloseAll(oper){
	if(oper == 'close'){
		$(".finder-list").find("div[id^='child_node_']").each(function(){
			$(this).hide();
			$(this).prev().find(".closeFolder").removeClass('closeFolder').addClass('openFolder');
		});
		$(".finder-list").find("div[id^='leaf_node_']").each(function(){
			$(this).hide();
			$(this).prev().find(".closeFolder").removeClass('closeFolder').addClass('openFolder');
		});
	}else{
		$(".finder-list").find("div[id^='child_node_']").each(function(){
			$(this).show();
			$(this).prev().find(".openFolder").removeClass('openFolder').addClass('closeFolder');
		});
		$(".finder-list").find("div[id^='leaf_node_']").each(function(){
			$(this).show();
			$(this).prev().find(".openFolder").removeClass('openFolder').addClass('closeFolder');
		});
	}
}

/**
 * 新增一个节点，并初始化数据
 * @param id
 * @return
 */
function addNew(id){
	$("#category_add_node").each(function(){
		$(this).find("input[type!='button']").val('');
	});
	var level = $("#category_node_"+id).find("input[name='level']").val();
	var state;
	if(id==0){
		level = 1;
		state = "ENABLE";
	}else{
		level = Number(level)+1;
		state = $("#category_node_"+id).find("input[name='state']").val();
	}
	$("#category_add_node").find("input[name='categoryId']").val(0);
	$("#category_add_node").find("input[name='level']").val(level);
	$("#category_add_node").find("input[name='parentId']").val(id);
	$("#category_add_node").find("input[name='state']").val(state);
	$("#category_add_node").find("input[name='serialNumber']").val(999);
	var output = $("#category_add_node").clone(true);
	$("#category_add_node").remove();
	$("#category_node_"+id).after(output);
	$("#category_add_node").show();
}

function cancel(){
	$("#category_add_node").hide();
}

/**
 * 编辑节点
 * @param id
 * @return
 */
function editRoot(id){
	$("category_add_node").each(function(){
		$(this).find("input[type!='button']").val('');
	});
	var name = $("#category_node_"+id).find("div[class^='margin-left']").find("a").text();
	var key = $("#category_node_"+id).find("input[name='key']").val();
	var level = $("#category_node_"+id).find("input[name='level']").val();
	var parentId = $("#category_node_"+id).find("input[name='parentId']").val();
	var state = $("#category_node_"+id).find("input[name='state']").val();
	var serialNumber = $("#category_node_"+id).find("input[name='serialNumber']").val();
	$("#category_add_node").find("input[name='name']").val(name);
	$("#category_add_node").find("input[name='key']").val(key);
	$("#category_add_node").find("input[name='categoryId']").val(id);
	$("#category_add_node").find("input[name='level']").val(level);
	$("#category_add_node").find("input[name='parentId']").val(parentId);
	$("#category_add_node").find("input[name='state']").val(state);
	$("#category_add_node").find("input[name='serialNumber']").val(serialNumber);
	var output = $("#category_add_node").clone(true);
	$("#category_add_node").remove();
	$("#category_node_"+id).after(output);
	$("#category_add_node").show();
}

/**
 * 保存或者更新节点
 * @return
 */
function saveOrUpdate(){
	var name = $("#category_add_node").find("input[name='name']").val();
	var key = $("#category_add_node").find("input[name='key']").val();
	if(name == "" || key == ""){
		alert("内容不能为空");
		return false;
	}
	var state = $("#category_add_node").find("input[name='state']").val();
	var level = $("#category_add_node").find("input[name='level']").val();
	var parentId = $("#category_add_node").find("input[name='parentId']").val();
	var categoryId = $("#category_add_node").find("input[name='categoryId']").val();
	var serialNumber = $("#category_add_node").find("input[name='serialNumber']").val();
	saveItem.addParam("name",name);
	saveItem.addParam("key",key);
	saveItem.addParam("state",state);
	saveItem.addParam("level",level);
	saveItem.addParam("parentId",parentId);
	saveItem.addParam("categoryId",categoryId);
	saveItem.addParam("serialNumber",serialNumber);
	saveItem.submit();
}

/**
 * 删除节点，并且删除该节点下所有子节点
 * @param id
 * @return
 */
function del(id){
	var childrenLength = $("#child_node_"+id).find("div").length;
	var leafLength = $("#leaf_node_"+id).find("div").length;
	var msg = "确认删除？";
	if(childrenLength > 0 || leafLength > 0){
		msg = "删除该节点，其子节点也会一起删除，确认删除？";
	}
	if(confirm(msg)){
		deleteItem.addParam("categoryId",id);
		deleteItem.submit();
	}
}

/**
 * 更新节点状态
 * @param id
 * @return
 */
function updateItemState(id){
	var state = $("#category_node_"+id).find("input[name='state']").val();
	if(state == "ENABLED"){
		state = "DISABLED";
	}else{
		state = "ENABLED";
	}
	updaeState.addParam("categoryId",id);
	updaeState.addParam("state",state);
	updaeState.submit();
}

/**
 * managedbean回调方法
 * @param id
 * @param name
 * @param key
 * @return
 */
function updateCallback(id,name,key){
	$("#category_node_"+id).find("div[class^='margin-left']").find("a").text(name);
	$("#category_node_"+id).find("input[name='key']").val(key);
	cancel();
}

/**
 * managedbean回调方法
 * @param id
 * @return
 */
function deleteCallback(id){
	if($("#category_node_"+id).find("#category_add_node").length > 0
			|| $("#child_node_"+id).find("#category_add_node").length > 0
			|| $("#leaf_node_"+id).find("#category_add_node").length > 0){
		var output = $("#category_add_node").clone(true);
		$("#category_add_node").remove();
		$("#man_zone").after(output);
		$("#category_add_node").hide();
	}
	if($("#category_node_"+id).find("#category_add_property").length > 0
			|| $("#child_node_"+id).find("#category_add_property").length > 0
			|| $("#leaf_node_"+id).find("#category_add_property").length > 0){
		var outputProperty = $("#category_add_property").clone(true);
		$("#category_add_property").remove();
		$("#man_zone").after(outputProperty);
		$("#category_add_property").hide();
	}
	if($("#category_node_"+id).next().attr("id") == 'category_add_node'){
		$("#category_add_node").hide();
	}
	if($("#category_node_"+id).next().attr("id") == 'category_add_property'){
		$("#category_add_property").hide();
	}
	$("#category_node_"+id).remove();
	$("#child_node_"+id).remove();
	$("#leaf_node_"+id).remove();
}

/**
 * managedbean回调方法
 * @param id
 * @return
 */
function updateStateCallback(id){
	var state = $("#category_node_"+id).find("input[name='state']").val();
	if(state == "ENABLED"){
		$("#category_node_"+id).find("a[class='show_icon']").removeClass("show_icon").addClass("hide_icon");
		$("#category_node_"+id).find("a[class='show_icon']").attr("title","点击启用此栏目");
		$("#category_node_"+id).find("a[class='show_icon']").attr("alt","点击启用此栏目");
		$("#category_node_"+id).find("input[name='state']").val('DISABLED');
		$("#category_node_"+id).next().find(".row").each(function(){
			$(this).find("a[class='show_icon']").removeClass("show_icon").addClass("hide_icon");
			$(this).find("a[class='show_icon']").attr("title","点击启用此栏目");
			$(this).find("a[class='show_icon']").attr("alt","点击启用此栏目");
			$(this).find("input[name='state']").val('DISABLED');
		});
	}else{
		$("#category_node_"+id).find("a[class='hide_icon']").removeClass('hide_icon').addClass("show_icon");
		$("#category_node_"+id).find("a[class='show_icon']").attr("title","点击禁用此栏目");
		$("#category_node_"+id).find("a[class='show_icon']").attr("alt","点击禁用此栏目");
		$("#category_node_"+id).find("input[name='state']").val('ENABLED');
		$("#category_node_"+id).next().find(".row").each(function(){
			$(this).find("a[class='hide_icon']").removeClass('hide_icon').addClass("show_icon");
			$(this).find("a[class='show_icon']").attr("title","点击禁用此栏目");
			$(this).find("a[class='show_icon']").attr("alt","点击禁用此栏目");
			$(this).find("input[name='state']").val('ENABLED');
		});
	}
}

/**
 * 查询叶子节点属性
 * @param id
 * @return
 */
function queryProperty(id){
	if($("#category_node_"+id).next().attr("id") != "category_add_property" || $("#category_add_property").css("display")=="none"){
		queryProperty_fn.addParam("propertyCategoryId",id);
		queryProperty_fn.submit();
	}else{
		$("#category_add_property").hide();
	}
}

/**
 * 添加叶子节点属性
 * @param id
 * @return
 */
function addProperty(id){
	var output = $("#category_add_property").clone(true);
	$("#category_add_property").remove();
	$("#category_node_"+id).after(output);
	$("#category_add_property").find("input[name='propertyCategoryId']").val(id);
	$("#category_add_property").show();
}

/**
 * 保存叶子节点属性
 * @return
 */
function saveProperty(){
	var length = $("#category_add_property").find(".row").length;
	if(length > 0){
		var isNullValue = true;
		$("#category_add_property").find("input[name='propertyName']").each(function(){
			if($(this).val() == ""){
				isNullValue = false;
			}
		});
		$("#category_add_property").find("input[name='propertyValue']").each(function(){
			if($(this).val() == ""){
				isNullValue = false;
			}
		});
		if(isNullValue){
			saveProperty_fn.submit();
		}else{
			alert("属性值不能为空");
		}
	}
}

/**
 * 保存叶子属性后 managedbean回调方法
 * @return
 */
function disabledProperty(){
	$("#category_add_property").fadeOut(1000);
	$("#category_add_property").fadeIn(1000);
}

function cancelProperty(){
	$('#category_add_property').hide();
}

/**
 * 添加叶子节点属性item
 * @return
 */
function addNewPropertyItem(){
	var output = "<div class='row'>"+
				"<div class='row-line'>"+
				"<div class='span-3'><input type='text' name='propertyName' size='12'/></div>"+
				"<div class='span-8'><input type='text' name='propertyValue' style='width:95%;'/></div>"+
				"<input name='propertyId' type='hidden' value='0'/>"+
				"<div style='text-align:center;color:red;' class='span-3'>"+
				"<a title='删除' alt='删除' class='detel_icon' onclick='deletePropertyItem(this)'/>"+
				"</div>"+
				"</div>"+
				"<div style='clear:both'></div>"+
        		"</div>";
	$(".info_property").append(output);
}

/**
 * 删除叶子节点属性Iitem
 * @param object
 * @return
 */
function deletePropertyItem(object){
	if(confirm("确认删除？")){
		$(object).parents(".row").remove();
	}
}

function editProperty(){
	$(".info_property").find(".row").each(function(){
		$(this).find("input[type='text']").removeAttr("disabled");
	});
	$(".actionBar").find("span[onclick='saveProperty()']").show();
}