function queryData(){
	queryAction.submit();
}

function initActions(orderId,actionStr,type){
	var actions = actionStr.split(",");
	var output="";
	for(var i = 0;i<actions.length;i++){
		if(actions[i] != ''){
			output += "<a href='javascript:' onClick=\"orderbookProcess(this,'"+orderId+"','"+type+"')\">"+actions[i]+"</a><br/>";
		}
	}
	output += "<a href=\"/orderbook/order/buyerOrderDetail.faces?orderId="+orderId+"\" target='_blank'>订单详情</a>";
	$("#order_action_"+orderId).html(output);
}

function orderbookProcess(object,orderId,type){
	var actionName = $(object).text();
	if(type == "sell" && actionName == "取消订单"){
		$("#cancelOrderID").val(orderId);
		$("#cancelOrderReason").val('');
		$("#otherReason").hide();
		cancelReasonWin.show();
		return;
	}else if(!confirm(actionName+"?")){
		return;
	}
	processAction.addParam("action",actionName);
	processAction.addParam("orderId",orderId);
	processAction.submit();
}

function cancelOrder(){
	processAction.addParam("action","取消订单");
	processAction.addParam("orderId",$("#cancelOrderID").val());
	processAction.submit();
}

function statisticsClick(stateValue){
	$("#order_query_time").val('');
	$("#order_query_state").val(stateValue);
	queryAction.submit();
}

function queryAppraise(){
	$("#order_query_time").val('');
	$("#order_query_state").val('');
	queryAppraiseAction.submit();
}
function queryConfirmPayment(){
	$("#order_query_time").val('');
	$("#order_query_state").val('');
	queryConfirmPaymentAction.submit();
}
function changeSellType(obj,saleType){
	if($(obj).parents('li').attr("class") == 'curr'){
		return;
	}
	$("#selltypeTab").find("li").each(function(){
		$(this).removeClass("curr");
	});
	$(obj).parents('li').addClass("curr");
	$("#orderSaleType").val(saleType);
	$("#order_query_time").val('MONTH');
	$("#order_query_state").val('');
	changeTabAction.addParam("saleType",saleType);
	changeTabAction.submit();
	
}
function changeStatText(saleType){
	if("RETAIL" == saleType){
		$("#orderStatType").text("零售订单提醒");
	}else{
		$("#orderStatType").text("批发订单提醒");
	}
}
function showChangePriceWin(orderId){
	changePriceWinFn.addParam("orderId",orderId);
	changePriceWinFn.submit();
	priceChangeWin.show();
}

function changePrice(){
	var currentPrice=$("#allTotalPrice").text();
	if(confirm("您修改后的订单总价为 "+currentPrice+"元，确认提交？")){
	  changePriceFn.submit();
	}
}
function changeReason(){
	if($("#cancelOrderReason").val() == "OTHER"){
		$("#otherReason").show();
	}else{
		$("#otherReason").hide();
	}
}
function changePriceInput(id) {
	var productTotal = mul($("#currentPrice_"+id).val() , $("#productQuantity_"+id).text());
	$("#productTotal_"+id).text(numberFormat(productTotal));
	var allTotal = Number(0);
	$("#changePriceWindow").find("td[id^='productTotal_']").each(function(){
		allTotal += Number($(this).text());
	});
	$("#allTotalPrice").text(numberFormat(allTotal));
}

/**
 * 格式话金额
 * @param num
 * @return
 */
function numberFormat(num){
	var len = 0;
	try{len+=num.toString().split(".")[1].length}catch(e){}
	if(len < 2){
		len = 2;
	}
	var num = mul(num , Math.pow(10,len)) + "";
	var numStart = num.substr(0,num.length-len);
	var numEnd = num.substr(num.length-len,num.length);
	return numStart + "." + numEnd;
}

/**
 * 两个数字相乘
 * @param s1
 * @param s2
 * @return
 */
function mul(s1,s2){
	 var m=0,s1=s1.toString(),s2=s2.toString();
	 try{m+=s1.split(".")[1].length}catch(e){}
	 try{m+=s2.split(".")[1].length}catch(e){}
	 return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}