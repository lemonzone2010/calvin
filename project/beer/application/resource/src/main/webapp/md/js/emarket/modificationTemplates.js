function setBrandInfo(setBrandInfo) {
	var attributeStr = document.getElementById('attributeId').value;
	var semicolonStr="";
	if (attributeStr != "") {
		semicolonStr = attributeStr.split(";");
	}
	var array = eval(setBrandInfo);
	if (null != array && array.length != 0) {
		$("#brandDiv").show();
		$("#brandDiv").empty();
		for ( var i = 0; i < array.length; i++) {
			var setValue = array[i].setValue;
			var setValues = setValue.split("|");
			var optionDisplay = "";
			for ( var j = 0; j < setValues.length; j++) {
				if(semicolonStr!=""){
					var semicolonValue = semicolonStr[i].substring(semicolonStr[i]
							.indexOf(":") + 1, semicolonStr[i].length);
					if (semicolonValue == setValues[j]) {
						optionDisplay += '<option value="' + array[i].setName + ':'
								+ setValues[j] + '" selected=selected >'
								+ setValues[j] + '</option>';
					} else {
						optionDisplay += '<option value="' + array[i].setName + ':'
								+ setValues[j] + '" >' + setValues[j] + '</option>';
					}
				}else{
					optionDisplay +='<option value='+array[i].setName+':'+setValues[j]+'>'+setValues[j]+'</option>';
				}
			}
			var displayView = '<div class="item_goods"><span class=label_goods>' + array[i].setName + '：</span>';
			displayView += '<div class=fl><select id=brandIdSelect name=brandIdSelect>' + optionDisplay + '</select></div></div>';
			document.getElementById("brandDiv").innerHTML += displayView;
		}
	} else {
		$("#brandDiv").innerHTML = "";
		$("#brandDiv").hide();
	}
}
var semicolonValue;
function getCustomization() {
	var customization = document.getElementById('customizationId').value;
     if(customization!=":;"){
		if (customization.lastIndexOf(';') != -1) {
			customization = customization.substring(0, customization.length - 1);
		}
		var semicolonStr = customization.split(";");
		for ( var i = 0; i < semicolonStr.length; i++) {
			var semicolonName = semicolonStr[i].substring(0, semicolonStr[i]
					.indexOf(':'));// 名称
			semicolonValue = semicolonStr[i].substring(
					semicolonStr[i].indexOf(':') + 1, semicolonStr[i].length);// 值
			if (semicolonName != "") {
				document.getElementById('customizationName').value = semicolonName;
				document.getElementById('customizationValue').value = semicolonValue;
				addRowModiy();
			}
		}
		if (semicolonStr.length > 0) {
			$("#trDataRow1").remove();
			var id = semicolonStr.length;
			$("#trDataRow" + id).attr('id', '#trDataRow1');
		}
	}
}
function getLoadCategoryAttribute(id) {
	if (id == null) {
		id = $("#categoryId").val();
	}
	getBrandIdListView.addParam("categoryId", id);
	getBrandIdListView.submit();
}
function getCategoryAttribute() {
	getBrandIdListView.addParam("categoryId", $("#categoryId").val());
	getBrandIdListView.submit();
}
function addRowModiy() {
	var vTb = $("#TbData");
	var vNum = $("#TbData").find("tr").length-1;
	if (vNum > 5) {
		alert("不能大于5条数据");
		return;
	}
	var vTr = $("#trDataRow1");
	var vTrClone = vTr.clone(true);
	vTrClone[0].id = "trDataRow" + vNum;
	vTrClone.appendTo(vTb);
}
function addRowJModiy(obj) {
	var vTb = $("#TbData");
	var vNum = $("#TbData").find("tr").length;
	if (vNum > 5) {
		alert("不能大于5条数据");
		return;
	}
	var vbtnDel = $(obj);
	var vTr = vbtnDel.parent().parent("td").parent("tr");
	var vTrClone = vTr.clone(true);
	vTrClone[0].id = "trDataRow" + vNum;
	vTrClone.appendTo(vTb);
	$("#trDataRow" + vNum + " input").each(function() {
		$(this).attr("value", "");
	});
}
function deleteRowModiy(obj) {
	var vNum = $("#TbData").find("tr").length;
	if (vNum <= 2) {
		alert('请至少留一行');
		return;
	}
	var vbtnDel = $(obj);
	var vTr = vbtnDel.parent().parent("td").parent("tr");
	if (vTr.attr("id") == "trDataRow1") {
		alert('第一行不能删除!');
		return;
	} else {
		vTr.remove();
	}
}
function construstNodeView(output, level) {
	$("#selectCategory").html(level);
	$("#selected_brand").html(output);

}
var flg=true;
function submitFunction(obj){
		 $('.editor').val($('.editor').omEditor('getData'));
		 if(!ebiz_isAllPass()){
			 return false;
		 };
		var brandId=document.getElementById("brandId").value;
		var categoryId=document.getElementById("categoryId").value;
		if(0==categoryId){
			document.getElementById("tiShiCategoryId").innerHTML="<font color='red'>选择不能为空,请您选择的商品类别</font>";
			return false;
		}
		if(0==brandId){
			document.getElementById("tiShiBrandId").innerHTML="<font color='red'>品牌不能为空,请您选择的商品类别第三项</font>";
			return false;
		}
		var enableOpen=document.getElementById('enableOpenId');
		var retailPriceId=document.getElementById("retailPriceId").value;
		var wholesalePriceId=document.getElementById("wholesalePriceId").value;
		if(enableOpen.checked==false){
			if(retailPriceId==0){
				 if(confirm("该商品零售价为零(0),你是否提交！")){
					panDuanTijiao(obj);
				 }
			}else{
				panDuanTijiao(obj);
			}
		}
		if(enableOpen.checked==true){
			if(wholesalePriceId==0||retailPriceId==0){
				 if(confirm("该商品零售价与批发价为零(0),你是否提交！")){
					panDuanTijiao(obj);
				 }
			}else{
				panDuanTijiao(obj);
			}
		}
}
function panDuanTijiao(obj){
	 if(obj==6){
		 updateProductTemplate.submit();
	 }
	 if((obj==7)){
		 publishProductByTemplate.submit();
	 }
}