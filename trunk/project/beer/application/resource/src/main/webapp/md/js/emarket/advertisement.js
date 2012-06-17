
$(function(){
	$('option').each(function(){
	    if($(this).val() == $("#positionId").val()){
	        $(this).attr('selected','selected')
	    }
	})
});

function saveOrUpdateAdvertisementPosition(id){
	
	var position = document.getElementById("newAdvertisementPositionForm:position").value;
	if(position.trim() == ''){
		alert("位置名称不能为空!");
		return ;
	}
	if(id!='' && id != 0){
		updateAdvertisementPosition.submit();
	}else{
		addAdvertisementPosition.submit();
	}
}

function removeAdvertisementPosition(id){
	if(confirm('确定要删除吗?')){
		deleteAdvertisementPosition.addParam('id',id);
		deleteAdvertisementPosition.submit();
	}
}

function removeAdvertisement(id){
	if(confirm('确定要删除吗?')){
		deleteAdvertisement.addParam('id',id);
		deleteAdvertisement.submit();
	}
}

function saveOrUpdateAdvertisementManagement(id){
	var name = $("#name").val();
	var url = $("#url").val();
	var position = $("#position").val();
	if(name.trim() == ''){
		alert("商户名称不能为空!");
		return;
	}
	if(url.trim() == ''){
		alert("URL不能为空!");
		return;
	}
	if(position == 0){
		alert("请选择投放位置!");
		return;
	}
	if(id!='' && id != 0){
		updateAdvertisement.submit();
	}else{
		addAdvertisement.submit();
	}
}

function advertisementPageManagementReload(){
	location.href='advertisementPageManagement.faces';
}

function advertisementManagementReload(){
	location.href='advertisementManagement.faces';
}

function uploadPicture(){
	if(document.getElementById('pictureUpload').value){
		ajaxFileUpload('form1',fileupload_callback,{});
	}else{
	    alert('请选择要上传的图片文件！');return false;
	}
}

