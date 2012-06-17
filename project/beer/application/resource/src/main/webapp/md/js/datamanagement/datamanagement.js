$(document).ready(function() {
	$('#editorAdd').omEditor({
		filebrowserImageUploadUrl : '/emarket/omEditorFileUploadServlet?type=Images',
		allowImageType : ['jpg','gif','png','bmp']
	});
	$('#editorModfiy').omEditor({
		filebrowserImageUploadUrl : '/emarket/omEditorFileUploadServlet?type=Images',
		allowImageType : ['jpg','gif','png','bmp']
	});
});
function updateDataList(id){
	getDatasByCategoryId.addParam("categoryId",id);
	getDatasByCategoryId.submit();
}

function showUpdateData(checkName){
	var length = $("input:checkbox[name='" + checkName + "']:checked").length;
	if (length == 0) {
		alert("请先选中一行进行修改");
		return false;
	}
	if (length != 1) {
		alert("您好，一次只能修改一条记录");
		return false;
	}
	var id = getCheckBoxId(checkName);
	showUpdateWindow.addParam("dataId",id);
	return true;
}

function deleteDatas(checkName){
	var length = $("input:checkbox[name='" + checkName + "']:checked").length;
	if (length == 0) {
		alert("请先选中一行");
		return false;
	}
	if (window.confirm("确定要删除？")) {
		var id = getCheckBoxId(checkName);
		deleteDatasAction.addParam("dataIds",id);
		return true;
	}
}
function loadingModifySelectDataType(){
	var values = document.getElementsByName("updateDataForm:selectModifyDataTypeId");
	if(values[0].checked==false){//富文本
		$("#editorIdModModifyValue").show();  
		$("#editorIdTextModifyValue").hide(); 
	}else{
		$("#editorIdModModifyValue").hide();  
		$("#editorIdTextModifyValue").show();
	}
	var data = $('#updateDataForm\\:editModifyTextarea').val();
	$('#editorModfiy').omEditor('setData',data);
}
function loadingSelectDataType(){
	var values = document.getElementsByName("addDatemangementId:selectDataTypeId");
	if(values[0].checked==false){//富文本
		$("#editorIdValue").show();  
		$("#editorIdTextValue").hide(); 
	}else{
		$("#editorIdValue").hide();  
		$("#editorIdTextValue").show();
	}
	$('#editorAdd').omEditor('setData',"");
}
function selectDataType(obj){
	var values =obj.value;
	if(values=="RICHTEXT"){//富文本
		$("#editorIdValue").show();  
		$("#editorIdTextValue").hide(); 
	}else{
		$("#editorIdValue").hide();  
		$("#editorIdTextValue").show();
	}
}
function selectModifyDataType(obj){
	var values =obj.value;
	if(values=="RICHTEXT"){//富文本
		$("#editorIdModModifyValue").show();  
		$("#editorIdTextModifyValue").hide(); 
	}else{
		$("#editorIdModModifyValue").hide();  
		$("#editorIdTextModifyValue").show();
	}
}

