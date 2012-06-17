function showBrandDetail(obj,id) {
	if ($(obj).next().css('display') == 'none') {
		//异步提取 品牌关联信息
		$('#brandId').val(id);
		showCategorys.submit();

		$('.brandDetail').each(function() {
			$(this).hide();
		});
		$(obj).next().show();
	} else {
		$(obj).next().hide();
	}
}

function cancelBrandDetail(){
	$('.brandDetail').hide();
}

function updateBrandCategory(id,obj){
		var checkedtypes='';
	$('#category'+id).find('input:checked').each(function() {
		checkedtypes = checkedtypes+''+$(this).val()+',';
	});

	$('#brandCategories').val(checkedtypes);
	$('#brandId').val(id);

	//修改文本框
	var division = $(obj).parent().parent();
	$('#brandName').val($(division).find("input[name='name']").val());
	$('#brandUrl').val($(division).find("input[name='url']").val());
	updateBrand.submit();
}

function createBrand(){
	var checkedtypes='';
$('input:checked').each(function() {
	checkedtypes = checkedtypes+''+$(this).val()+',';
});
$('#brandCategories').val(checkedtypes);
$('#picturePath').val($('#picture').attr('src'));
addBrand.submit();
}

function update_brand(){
	var checkedtypes='';
$('input:checked').each(function() {
	checkedtypes = checkedtypes+''+$(this).val()+',';
});
$('#updateBrandForm\\:brandCategories').val(checkedtypes);
$('#updateBrandForm\\:picturePath').val($('#picture').attr('src'));
updateBrand.submit();
}


function refreshCategory(recommendTypes,id){
	$('#category'+id).find('input:checkbox').each(function() {
		 for( var key in recommendTypes ) {
			 var categeryId = key+''+id;
			 $('#' + categeryId).attr("checked", true);
	    }
	});
}

function update_refreshCategory(recommendTypes,id){
	$('input:checkbox').each(function() {
		 for( var key in recommendTypes ) {
			 $('#' + key).attr("checked", true);
	    }
	});
}

function uploadPicture(){
	if(document.getElementById('pictureUpload').value){
		ajaxFileUpload('form1',fileupload_callback,{});
	}else{
	 alert('请选择要上传的图片文件！');return false;
	 }
}

function delete_brand(id) {
	if(confirm('确定要删除品牌吗？')){
	$('#deleteBrandForm\\:brandId').val(id);
	deleteBrand.submit();
	}
}
