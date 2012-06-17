function showProductDetail(obj,id) {
	if ($(obj).next().css('display') == 'none') {
		//异步提取 商品的扩展信息
		$('#productId').val(id);
		showProductRecommend.submit();

		$('.productDetail').each(function() {
			$(this).hide();
		});
		$(obj).next().show();
	} else {
		$(obj).next().hide();
	}

}

function cancelProductDetail(){
	$('.productDetail').hide();
}

function updateProduct(id){
		var checkedtypes='';;
	if($('#NEW'+id).attr('checked')=='checked'){
		checkedtypes = checkedtypes+'NEW,';
	}else{
	}

	if($('#RECOMMEND'+id).attr('checked')=='checked'){
		checkedtypes = checkedtypes+'RECOMMEND,';
	}else{
	}
	if($('#HOT'+id).attr('checked')=='checked'){
		checkedtypes = checkedtypes+'HOT,';
	}else{
	}
	$('#productRecommendTypes').val(checkedtypes);
	$('#productId').val(id);
	updateProductRecommend.submit();
}

function refresh(recommendTypes,productId){
	if(recommendTypes.NEW == 'checked'){
		var id = 'NEW' + productId;
		$('#' + id).attr("checked", true);
	}

	if(recommendTypes.RECOMMEND == 'checked'){
		var id = 'RECOMMEND' + productId;
		$('#' + id).attr("checked", true);
	}

	if(recommendTypes.HOT == 'checked'){
		var id = 'HOT' + productId;
		$('#' + id).attr("checked", true);
	}
}

function cancelNewProduct(id){
	if(confirm('确定要取消新品吗？')){
	$("#productRecommendId").val(id);
	cancelNewProductShelves.submit();
	}
}

function cancelRecommendProduct(id){
	if(confirm('确定要取消推荐吗？')){
	$("#productRecommendId").val(id);
	cancelRecommend.submit();
	}
}
function cancelHot(id){
	if(confirm('确定要取消畅销排行吗？')){
	$("#productRecommendId").val(id);
	cancelHotProduct.submit();
	}
}
