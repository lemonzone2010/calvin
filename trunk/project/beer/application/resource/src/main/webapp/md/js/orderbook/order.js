$(document).ready(function() {
	showpPayImageDisplayhide();
});
function showpPayImageDisplay(){
	$("#payRemark_4").show();
	$("#bankDispay").hide();
	$("#bankDispayColse").show();
}
function showpPayImageDisplayhide(){
	$("#payRemark_4").hide();
	$("#bankDispay").show();
	$("#bankDispayColse").hide();
}
$(function(){
	//地址
	//没有可用地址
	 var length = $("input[name='address']:radio").length;
	 $("ul#address-collection li").each(function(){
			$(this).removeClass("selected");
		});
	 if(length <= 1){
		 //不用使用其他地址
		 $("input[name='address']:radio").last().attr("checked","checked");
		 $("input[name='address']:radio").last().parent().parent().addClass("selected");
		 $("#other-address-li").hide();
		 $("#otherAddress").show()
	 }else{
	 var addressId = $("input[name='address']:radio:checked").length;
		if(addressId <= 0){
			//没有默认地址,就选第一个
			$("input[name='address']:radio").first().attr("checked","checked");
			$("input[name='address']:radio").first().parent().parent().addClass("selected");
			$("#otherAddress").show()
		}else{
			$("input[name='address']:radio:checked").parent().parent().addClass("selected");
		}
	 }
/*	var addressId = $("input[name='address']:radio:checked").length;
	if(addressId <= 0){
		$("input[name='address']:radio").last().attr("checked","checked");
		$("#otherAddress").show()
	}*/
	//得到支付方式
	var userPayMode = $("#orderForm\\:userPayMode").val();
	if(userPayMode ==''){
		userPayMode = 'ONLINE';	//默认线上
	}
	$("#payModeTable input:radio#"+userPayMode).attr("checked","checked");

	//得到配送方式
	var userDeliveryMode = $("#orderForm\\:userDeliveryMode").val();
	if(userDeliveryMode ==''){
		userDeliveryMode = 'MD_EXPRESS';	//默认 明大快递
	}
	$("#deliveryModeTable input:radio#"+userDeliveryMode).attr("checked","checked");

	//得到发票信息
	var invoiceType = $("#orderForm\\:userInvoiceType").val();
	if(invoiceType ==''){
		invoiceType = 'PERSONAL';	//默认  个人
	}
	$("input[name='invoiceType']:radio#"+invoiceType).attr("checked","checked");

	var invoiceContentType = $("#orderForm\\:userInvoiceContentType").val();
	if(invoiceContentType ==''){
		invoiceContentType = 'DETAIL';	//默认   明细
	}
	$("input[name='invoiceContentType']:radio#"+invoiceContentType).attr("checked","checked");

	if(invoiceType == 'PERSONAL'){
		$("#orderForm\\:userHeader").val("");
		$("#invoice_titleTr").hide();
	}
	var userHeader = $("#orderForm\\:userHeader").val();
	$("#invoice_Unit_TitName").val(userHeader);
});


function selectAddress(obj){
	$("ul#address-collection li").each(function(){
		$(this).removeClass("selected");
	});
	$(obj).parent().parent().addClass("selected");
	//$(obj).children().children("input").attr("checked","checked");
	if($("#modify_delivery_href").css('display')!='none'){
		if($(obj).parent().parent().attr("id")=='other-address-li'){
			//清空信息
			$("#orderForm\\:streetAddr").val('');
			$("#orderForm\\:postcode").val('');
			$("#orderForm\\:consignee").val('');
			$("#orderForm\\:mobil").val('');
			$("#orderForm\\:phone").val('');
			$("#otherAddress select:eq(0) option:eq(0)").attr("selected","selected")
			$("#otherAddress select:eq(0)").change();
			$("#otherAddress").show();
			$("#show_address_info").hide();
			$("#addTodelivery_href a").text('[添加到地址簿]');
		}else{
			$("#otherAddress").hide();
			//得到详情
			modifySelectedDeliveryAddr($(obj).parent().parent().children().children("input").attr("id"),'showDeliveryAddrDetail');
			$("#show_address_info").show();
		}

	}else{
		modifySelectedDeliveryAddr($(obj).parent().parent().children().children("input").attr("id"),'showDeliveryAddrInfo');
	}
}
function addOrder() {
	//得到地址簿
	var addrId = $("#address-collection input:radio:checked").attr("id");
	if(addrId>0){
		$("#otherAddress").remove();
	}
	$("#orderForm\\:deliveryAddrId").val(addrId);
	//得到支付方式
	var payModeId = $("#payModeTable input:radio:checked").attr("id");
	$("#orderForm\\:payMode").val(payModeId);
	//得到配送方式
	var deliveryModeId = $("#deliveryModeTable input:radio:checked").attr("id");
	$("#orderForm\\:deliveryMode").val(deliveryModeId);


	//发票信息

	var value = $("input[name='needInvoice']:radio:checked").val();
	$("#orderForm\\:needInvoice").val(value);
	if(value =='true'){
		var invoiceTypeId = $("input[name='invoiceType']:radio:checked").attr("id");
		var invoiceContentTypeId = $("input[name='invoiceContentType']:radio:checked").attr("id");
		if(invoiceTypeId=='PERSONAL'){
			$("#invoice_Unit_TitName").val("");
		}
		var header = $("#invoice_Unit_TitName").val();
		$("#orderForm\\:invoiceType").val(invoiceTypeId);
		$("#orderForm\\:invoiceContentType").val(invoiceContentTypeId);
		$("#orderForm\\:header").val(header);
	}else{
		$("#orderForm\\:needInvoice").val('false');
	}


	//备注
	$("#orderForm\\:orderRemark").val($("#textfield").val());
	var invoiceTypeValeu=$("input[name='invoiceType']:radio:checked").val();
	if(invoiceTypeValeu == 'COMPANY'){
		if(header == ''){
			alert('单位名称不能为空');
			return ;
		}
	}
	submitOrder.submit();
}

//修改地址簿
function modify_deliveryAddrs() {

	$("ul#address-collection li").each(function(){
		$(this).removeClass("selected");
	});
	var address =$("#address-collection input:radio:checked");
	address.parent().parent().addClass("selected");
	$("#other-address-li").show();
	$("#address-collection").show();
	$("#otherAddress").show();
	$("#show_address_info").hide();

	$("#close_delivery_href").show();
	$("#modify_delivery_href").hide();
	var addressId = $("input[name='address']:radio:checked").attr("id");
	if(addressId > 0){
		$("#addTodelivery_href a").text('[更新到地址簿]');
		$("#saveAddressForm\\:save_deliveryAddrId").val(addressId);
		getDeliveryAddrInfo.addParam('action','showDeliveryAddrInfo');
		getDeliveryAddrInfo.submit();
	}else{
		$("#addTodelivery_href a").text('[添加到地址簿]');
	}
}

function modifySelectedDeliveryAddr(addressId,action){
	if(addressId > 0){
	$("#addTodelivery_href a").text('[更新到地址簿]');
	$("#saveAddressForm\\:save_deliveryAddrId").val(addressId);
	getDeliveryAddrInfo.addParam('action',action);
	getDeliveryAddrInfo.submit();
	}else{
		$("#addTodelivery_href a").text('[添加到地址簿]');
		//清空信息
		$("#orderForm\\:streetAddr").val('');
		$("#orderForm\\:postcode").val('');
		$("#orderForm\\:consignee").val('');
		$("#orderForm\\:mobil").val('');
		$("#orderForm\\:phone").val('');
		$("#otherAddress select:eq(0) option:eq(0)").attr("selected","selected")
		$("#otherAddress select:eq(0)").change();
	}
}

//关闭地址簿
function close_deliveryAddrs() {
	var addrId = $("#address-collection input:radio:checked").attr("id")

		if($("#otherAddress select:eq(0) option:selected").val()==''){
			alert('请填写地区');
			return;
		}
		if($("#orderForm\\:streetAddr").val()==''){
			alert('请填写详细地址');
			return;
		}
		if($("#orderForm\\:postcode").val()==''){
			alert('请填写邮政编码');
			return;
		}
		if($("#orderForm\\:consignee").val()==''){
			alert('请填写收货人');
			return;
		}
		if($("#orderForm\\:mobil").val()==''){
			alert('请填写手机');
			return;
		}
		if($("#orderForm\\:phone").val()==''){
			alert('请填写固定电话');
			return;
		}

	if(addrId == 0){
		var province = $("#otherAddress select:eq(0) option:selected").val();
		var city = $("#otherAddress select:eq(1) option:selected").val();
		var area = $("#otherAddress select:eq(2) option:selected").val();
		if(address==undefined){
			area = '';
		}
		var address = province+ city+area;
		$("#show_address").text(address);
		$("#show_streetAddr").text($("#orderForm\\:streetAddr").val());
		$("#show_postcode").text($("#orderForm\\:postcode").val());
		$("#show_consignee").text($("#orderForm\\:consignee").val());
		$("#show_mobile").text($("#orderForm\\:mobil").val());
		$("#show_phone").text($("#orderForm\\:phone").val());
	}else{
		var province = $("#saveAddressForm\\:save_province").val();
		var city = $("#saveAddressForm\\:save_city").val();
		var area = $("#saveAddressForm\\:save_area").val();
		$("#show_address").text(province+city+area);
		$("#show_streetAddr").text($("#saveAddressForm\\:save_streetAddr").val());
		$("#show_postcode").text($("#saveAddressForm\\:save_postcode").val());
		$("#show_consignee").text($("#saveAddressForm\\:save_consignee").val());
		$("#show_mobile").text($("#saveAddressForm\\:save_mobile").val());
		$("#show_phone").text($("#saveAddressForm\\:save_phone").val());
		}

	$("#show_address_info").show();
	$("#otherAddress").hide();
	$("#address-collection").show();
	$("#modify_delivery_href").show();
	$("#close_delivery_href").hide();
}

//添加到地址簿
function add_deliveryAddr() {
	var addrId = $("#address-collection input:radio:checked").attr("id")
	$("#orderForm\\:deliveryAddrId").val(addrId);
	addToDeliveryAddr.submit();
}

// 修改地址簿
function showDeliveryAddrInfo() {
	var province = $("#saveAddressForm\\:save_province").val();
	var city = $("#saveAddressForm\\:save_city").val();
	var area = $("#saveAddressForm\\:save_area").val();
	$("#otherAddress select:eq(0) option").each(function() {
		if($(this).val() == province){
			$(this).attr("selected","selected");
		}
	});
	$("#otherAddress select:eq(0)").change();
	$("#otherAddress select:eq(1) option").each(function() {
		if($(this).val() == city){
			$(this).attr("selected","selected");
		}
	});
	$("#otherAddress select:eq(1)").change();
	$("#otherAddress select:eq(2) option").each(function() {
		if($(this).val() == area){
			$(this).attr("selected","selected");
		}
	});
	$("#orderForm\\:streetAddr").val($("#saveAddressForm\\:save_streetAddr").val());
	$("#orderForm\\:postcode").val($("#saveAddressForm\\:save_postcode").val());
	$("#orderForm\\:consignee").val($("#saveAddressForm\\:save_consignee").val());
	$("#orderForm\\:mobil").val($("#saveAddressForm\\:save_mobile").val());
	$("#orderForm\\:phone").val($("#saveAddressForm\\:save_phone").val());
}

function showDeliveryAddrDetail() {
	var province = $("#saveAddressForm\\:save_province").val();
	var city = $("#saveAddressForm\\:save_city").val();
	var area = $("#saveAddressForm\\:save_area").val();
	$("#show_address").text(province+city+area);
	$("#show_streetAddr").text($("#saveAddressForm\\:save_streetAddr").val());
	$("#show_postcode").text($("#saveAddressForm\\:save_postcode").val());
	$("#show_consignee").text($("#saveAddressForm\\:save_consignee").val());
	$("#show_mobile").text($("#saveAddressForm\\:save_mobile").val());
	$("#show_phone").text($("#saveAddressForm\\:save_phone").val());
}

//保存地址
function save_address() {
	var address = $("input[name='address']:radio").last();
	address.attr("checked","checked");
	$("ul#address-collection li").each(function(){
		$(this).removeClass("selected");
	});
	$("input[name='address']:radio").last().parent().parent().addClass("selected");
	saveAddress.submit();
}
//显示地址详情
function showAddressInfo() {
	$("#show_address_info").show();
	$("#otherAddress").hide();
	$("#address-collection").show();
	$("#modify_delivery_href").show();
	$("#close_delivery_href").hide();
}

//保存发票信息
function savePart_invoice() {

	//发票信息
	var invoiceTypeId = $("input[name='invoiceType']:radio:checked").attr("id");
	var invoiceContentTypeId = $("input[name='invoiceContentType']:radio:checked").attr("id");
	if(invoiceTypeId=='PERSONAL'){
	$("#invoice_Unit_TitName").val("");
	}
	var header = $("#invoice_Unit_TitName").val();
	/*//判断是否为空
	if(header == ''){
		alert('单位名称不能为空');
		return ;
	}*/
	$("#saveInvoiceForm\\:saveInvoice_invoiceType").val(invoiceTypeId);
	$("#saveInvoiceForm\\:saveInvoice_invoiceContentType").val(invoiceContentTypeId);
	$("#saveInvoiceForm\\:saveInvoice_header").val(header);
	saveInvoice.submit();
}

function save_payAndDelivery() {
	//得到支付方式
	var payModeId = $("#payModeTable input:radio:checked").attr("id");
	$("#savePayAndDeliveryForm\\:savePayAndDelivery_payMode").val(payModeId);
	//得到配送方式
	var deliveryModeId = $("#deliveryModeTable input:radio:checked").attr("id");
	$("#savePayAndDeliveryForm\\:savePayAndDelivery_deliveryMode").val(deliveryModeId);
	savePayAndDelivery.submit();
}

function showInvoiceInfo() {
	var orderInvoice_type = $("input[name='invoiceType']:radio:checked").next().text();
	$("#orderInvoice_type").text(orderInvoice_type);

	var invoiceContentType = $("input[name='invoiceContentType']:radio:checked").next().text();
	$("#orderInvoice_content").text(invoiceContentType);

	var header = $("#invoice_Unit_TitName").val();
	$("#orderInvoice_head").text(header);
	if($("input[name='invoiceType']:radio:checked").val()=='PERSONAL'){
		$("#orderInvoice_head").parent().hide();
	}else{
		$("#orderInvoice_head").parent().show();
	}
	/*//判断是否为空
	if(header == ''){
		alert('单位名称不能为空');
		return ;
	}*/
	$("#part_invoice_form").hide();
	$("#savePartinvoicebn").hide();
	$("#closeinvoicehref").hide();
	$("#invoiceMessage").show();
	$("#modifyinvoicehref").show();
	//orderInvoice_type orderInvoice_head orderInvoice_content


}

function modifyInvoice() {
	$("#part_invoice_form").show();
	$("#savePartinvoicebn").show();
	$("#closeinvoicehref").show();
	$("#invoiceMessage").hide();
	$("#modifyinvoicehref").hide();
}
function showpayAndDeliveryInfo() {
	$("#payAndDeliveryEditInfo").hide();
	$("#savepayAndDeliverybn").hide();
	$("#closeypayhref").hide();
	$("#payAndDeliveryMessage").show();
	$("#modifypayhref").show();
	//orderInvoice_type orderInvoice_head orderInvoice_content
	var paymentInfo_paymode = $("#payModeTable input:radio:checked").next().text();
	$("#paymentInfo_paymode").text(paymentInfo_paymode);
	var paymentInfo_deliveryinfo = $("#deliveryModeTable input:radio:checked").next().text();
	$("#paymentInfo_deliveryinfo").text(paymentInfo_deliveryinfo);
}
function modifypayAndDelivery() {
	$("#payAndDeliveryEditInfo").show();
	$("#savepayAndDeliverybn").show();
	$("#closeypayhref").show();
	$("#payAndDeliveryMessage").hide();
	$("#modifypayhref").hide();
}

function query_Order() {
	var state = $("#stateOrderSelect option:selected");
	if($(state).attr('id')!= 'all'){
	$("#queryOrderForm\\:orderstate").val($(state).val());
	}else{
		$("#queryOrderForm\\:orderstate").val('');
	}
	var timeValue = $("#orderTimeSelect option:selected").attr("value");;
	$("#queryOrderForm\\:timeSelect").val(timeValue);
	queryOrder.submit();
}

function deleteProduct(id,attribute) {
	if(confirm("该商品您确定删除吗？")){
	  $("#deleteCookieForm\\:prodcutId").val(id);
	  $("#deleteCookieForm\\:attribute").val(attribute);
	  removeProductCookie.submit();
	}
}

function need_invoice(obj){
	var value = $(obj).val();
    if(value=='true'){
    	$("#invoiceHeaderTable").show();
    	$("#invoiceContentTable").show();
    	$("#invoicebtn").show();
    	$("#invoice_operator").show();
    	return ;
    }
    $("#invoiceHeaderTable").hide();
	$("#invoiceContentTable").hide();
	$("#invoicebtn").hide();
	$("#invoice_operator").hide();
}

/**
 * 选择发票抬头
 * @return
 */
function select_invoiceType(obj) {
	var value = $(obj).val();
	if(value == 'COMPANY'){
	 $("#invoice_titleTr").show();
	 return ;
	}
	$("#invoice_titleTr").hide();
}