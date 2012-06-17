function add() {
	$("#add_info").show();
}
function cancel() {
	$("#add_info").hide();
}
function save() {
	saveOrUpdate.submit();
}
function addBefore(id) {
	var name = $("#add_info").find("#new_name").val();
	$("#add_info").find("input[name='appId']").val(id);
	$("#add_info").find("input[name='applicationName']").val(name);
	$("#add_info").find("input[name='status']").val('Y');
	$("#add_info").find("input[name='sequence']").val('999');
}
function updateBefore(id) {
	var name = $("#edit_" + id).find("#name_" + id).val();
	var status = $("#edit_" + id).find("#status_" + id).val();
	var sequence = $("#data_info_"+id).find("input[name='appSortSequence']").val();

	$("#add_info").find("input[name='appId']").val(id);
	$("#add_info").find("input[name='applicationName']").val(name);
	$("#add_info").find("input[name='status']").val(status);
	$("#add_info").find("input[name='sequence']").val(sequence);
}
function recover(id) {
	$("#edit_" + id).remove();
	$("#data_info_" + id).show();
}
function edit(id) {
	var index = $("#data_info_" + id).find("#index_" + id).text();
	var name = $("#data_info_" + id).find("#name_" + id).text();
	var status = $("#data_info_" + id).find("#status_" + id).text();
	var output = "<div id='edit_" + id + "' class='row'>";
	output += "<div class='span-1'>" + index + "</div>";
	output += "<div class='span-4'><input id='name_" + id + "' value='" + name
			+ "' size='14' /></div>";
	if (status == '启用') {
		output += "<div class='span-3'><select id='status_"
				+ id
				+ "'><option value='Y' selected>启用</option><option value='N'>禁用</option></select></div>";
	} else {
		output += "<div class='span-3'><select id='status_"
				+ id
				+ "'><option value='Y'>启用</option><option value='N' selected>禁用</option></select></div>";
	}
	output += "<div class='span-7'>"
			+ $("#data_info_" + id).find("#status_" + id).next().clone(true)
					.html() + "</div>";
	output += "</div>";
	$("#data_info_" + id).after(output);
	$("#data_info_" + id).hide();
	$("#edit_" + id).find("input[value='修改']").hide();
	$("#edit_" + id).find("input[value='保存']").show();
	$("#edit_" + id).find("input[value='取消']").show();
}
function enable(id) {
	enable_b.addParam("enableId", id);
	enable_b.submit();
}
function disable(id) {
	disable_b.addParam("disableId", id);
	disable_b.submit();
}
function deleteItem(id) {
	if (confirm("确认删除？")) {
		delete_b.addParam("deleteId", id);
		delete_b.submit();
	}
}

$(function() {
	initSortable();
})

/**
 * 初始化拖拽事件，默认该事件禁用
 */
function initSortable() {
	$("#date_info").sortable( {
		revert : true,
		update : function() {
			sortCallback();
		}
	});
	sortDisable();
}

/**
 * 激活拖拽排序事件
 * 
 * @return
 */
function sortEnable() {
	$("#date_info").sortable('enable');
}

/**
 * 禁用拖拽排序事件
 * 
 * @return
 */
function sortDisable() {
	$("#date_info").sortable('disable');
}

/**
 * 拖拽结束并且发生排序变化后的回调方法，记录发生排序节点ID和序号
 * 
 * @param object
 * @return
 */
function sortCallback() {
	$("#date_info").find("div[id^='data_info_']").each(function(index) {
		$(this).find("input[name='appSortSequence']").val(index);
	});
	updateSerialNumber.submit();
}