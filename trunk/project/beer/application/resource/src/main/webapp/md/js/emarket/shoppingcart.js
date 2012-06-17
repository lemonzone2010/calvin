
//初始化购物车
function init(){

	$("#wholesaleShoppingCartDiv").hide();   // 暂时不使用批发购物车

	var retailShoppingCartAmount=$('[name=retailPrice]');
	var wholesaleShoppingCartAmount = $('[name=wholesalePrice]');
	var wholesaleQuantity = $('[name=wholesaleQuantity]');
	var retailQuantity = $('[name=retailQuantity]');

	var retailAmount = 0;
	var wholesaleAmount = 0;
	var rQuantity = 0;
	var wQuantity = 0;
	var retailAmountMoney = 0;
	var wholesaleAmountMoney = 0;
	var retailShoppingCartDivHide = false;
	var wholesaleShoppingCartDivHide = false;
	
	if(retailShoppingCartAmount != null && retailShoppingCartAmount != 'undefined'){ //零售购物车
	    if(retailShoppingCartAmount.length == 0){
	    	$("#retailShoppingCartAmount").html('0.00');
	    	$("#retailShoppingCartDiv").hide();
	    	retailShoppingCartDivHide = true;
	    }else{
 			for(var i=0;i<retailShoppingCartAmount.length;i++){
 				retailAmount = parseFloat(retailShoppingCartAmount[i].value);
 				rQuantity =  parseFloat(retailQuantity[i].value);
 				retailAmountMoney += retailAmount * rQuantity;
 			}
 			retailAmountMoney+="";
 			if(retailAmountMoney.indexOf('.')==-1){
 				retailAmountMoney += ".00";
 			}else{
 				var len = retailAmountMoney.substring(retailAmountMoney.indexOf('.')+1).length;
 				if(len > 2){
 					retailAmountMoney = retailAmountMoney.substring(0,retailAmountMoney.indexOf('.')+3);
 				}
 			}
 			$("#retailShoppingCartAmount").html(retailAmountMoney);
		}
	}

	if(wholesaleShoppingCartAmount != null && wholesaleShoppingCartAmount != 'undefined'){ //批发购物车
		if(wholesaleShoppingCartAmount.length == 0){
	    	$("#wholesaleShoppingCartAmount").html('0.00');
	    	$("#wholesaleShoppingCartDiv").hide();
	    	wholesaleShoppingCartDivHide = true;
	    }else{
 			for(var i=0;i<wholesaleShoppingCartAmount.length;i++){
 				wholesaleAmount = parseFloat(wholesaleShoppingCartAmount[i].value);
 				wQuantity =  parseFloat(wholesaleQuantity[i].value);
 				wholesaleAmountMoney += wholesaleAmount * wQuantity;
 			}
 			wholesaleAmountMoney+="";
 			if(wholesaleAmountMoney.indexOf('.')==-1){
 				wholesaleAmountMoney += ".00";
 			}
 			$("#wholesaleShoppingCartAmount").html(wholesaleAmountMoney);
		}
	}
	if(retailShoppingCartDivHide){
		location.replace("emptyShoppingCart.xhtml");
	}
}

var obj;
function checkQuantity(quantity,shoppingCartType,index,productId,attribute){
	var retailQuantity = $('[name=retailQuantity]');
	var wholesaleQuantity = $('[name=wholesaleQuantity]');
	var actionType = 'minus';
	if(shoppingCartType == 1){
		if(onlyInputNum(1,retailQuantity[index].value,index,actionType)){
			if(retailQuantity[index].value<=1){
				if(confirm("确定不购买该商品吗?")){
					removeRetailShoppingCart.addParam('productId',productId);
					removeRetailShoppingCart.addParam('shoppingCartType',shoppingCartType);
					removeRetailShoppingCart.addParam('attribute',attribute);
					removeRetailShoppingCart.submit();
				}
			}else{
				retailQuantity[index].value = parseInt(retailQuantity[index].value) - 1;
				decreaseRetailProductQuantityToShoppingCart.addParam('addType','noRefurbish');
				decreaseRetailProductQuantityToShoppingCart.addParam('attribute',attribute);
				decreaseRetailProductQuantityToShoppingCart.submit();
				obj = retailQuantity[index];
				setTimeout("showMsgCue(1)",500);
			}
		}
	}else{
		if(onlyInputNum(0,retailQuantity[index].value,index,actionType)){
			if(wholesaleQuantity[index].value<=1){
				if(confirm("确定不购买该商品吗?")){
					removeWholesaleShoppingCart.addParam('productId',productId);
					removeWholesaleShoppingCart.addParam('shoppingCartType',shoppingCartType);
					removeWholesaleShoppingCart.submit();
				}
			}else{
				wholesaleQuantity[index].value = parseInt(wholesaleQuantity[index].value)-1;
				decreaseWholesaleProductQuantityToShoppingCart.addParam('addType','noRefurbish');
				decreaseWholesaleProductQuantityToShoppingCart.submit();
				obj = wholesaleQuantity[index];
				setTimeout("showMsgCue(0)",500);
			}
		}
	}
};

function JclearWholesaleShoppingCart(){
	if(confirm("确定要清空批发购物车吗?")){
		clearWholesaleShoppingCart.submit();
	}
}

function JremoveWholesaleShoppingCart(){
	if(confirm("确定要删除该商品吗?")){
		removeWholesaleShoppingCart.submit();
	}
}

function JclearRetailShoppingCart(){
	if(confirm("确定要删除零售购物车吗?")){
		clearRetailShoppingCart.submit();
	}
}

function JremoveRetailShoppingCart(attribute){
	if(confirm("确定要删除该商品吗?")){
		removeRetailShoppingCart.addParam('attribute',attribute);
		removeRetailShoppingCart.submit();
		return true;
	}
	return false;
}

//零售  添加数量
function JaddRetailProductQuantityToShoppingCart(index,attribute){
	var retailQuantity = $('[name=retailQuantity]');
	var actionType = 'add';
	if(onlyInputNum(1,retailQuantity[index],index,actionType)){
		retailQuantity[index].value = parseInt(retailQuantity[index].value) + 1;
		addRetailProductQuantityToShoppingCart.addParam('addType','noRefurbish');
		addRetailProductQuantityToShoppingCart.addParam('attribute',attribute);
		addRetailProductQuantityToShoppingCart.addParam('attrType',"shoppingcart");
		addRetailProductQuantityToShoppingCart.submit();
		obj = retailQuantity[index];
		setTimeout("showMsgCue(1)",500);
	}
}

//批发  添加数量
function JaddWholesaleProductQuantityToShoppingCart(index){
	var wholesaleQuantity = $('[name=wholesaleQuantity]');
	if(onlyInputNum(0,wholesaleQuantity[index],index)){
		wholesaleQuantity[index].value = parseInt(wholesaleQuantity[index].value)+1;
		addWholesaleProductQuantityToShoppingCart.addParam('addType','noRefurbish');
		addWholesaleProductQuantityToShoppingCart.submit();
		obj = wholesaleQuantity[index];
		setTimeout("showMsgCue(0)",500);
	}
}

//零售 批量 修改商品数量
function JupdateRetailProductQuantityToShoppingCart(productId,quantity,index,attribute){
	var retailQuantity = $('[name=retailQuantity]');
	var actionType = 'update';
	if(onlyInputNum(1,quantity,index,actionType)){
		
		if(retailQuantity[index].value<=0){
			removeRetailShoppingCart.addParam('productId',productId);
			removeRetailShoppingCart.addParam('shoppingCartType','1');
			if(JremoveRetailShoppingCart(attribute)){
				return ;
			}else{
				retailQuantity[index].value = "1";
				//retailQuantity[index].onblur();
			}
					
		}
		
		addRetailProductQuantityToShoppingCart.addParam('quantity',parseInt(retailQuantity[index].value));
		addRetailProductQuantityToShoppingCart.addParam('productId',productId);
		addRetailProductQuantityToShoppingCart.addParam('shoppingCartType',1);
		addRetailProductQuantityToShoppingCart.addParam('attribute',attribute);
		addRetailProductQuantityToShoppingCart.addParam('attrType',"shoppingcart");
		addRetailProductQuantityToShoppingCart.addParam('addType','noRefurbish');
		addRetailProductQuantityToShoppingCart.addParam('isUpdate','true');
		addRetailProductQuantityToShoppingCart.submit();
		obj = retailQuantity[index];
		setTimeout("showMsgCue(1)",500);
	}
		
}

//批发 批量 修改商品数量
function JupdateWholesaleProductQuantityToShoppingCart(index){
	var retailQuantity = $('[name=retailQuantity]');
	if(onlyInputNum(1,retailQuantity[index],index)){
		retailQuantity[index].value = parseInt(retailQuantity[index].value) + 1;
		addRetailProductQuantityToShoppingCart.addParam('addType','noRefurbish');
		addRetailProductQuantityToShoppingCart.submit();
		obj = retailQuantity[index];
		setTimeout("showMsgCue(1)",500);
	}
}

//收藏商品
function JaddShoppingCartProductToFavorite(productId){
	window.location.href="/emarket/favorite/favorite.faces?productId=" + productId+"&addFavorite=yes";
}

//输入验证
function onlyInputNum(type,quantity,index,actionType){
	var retailQuantity = $('[name=retailQuantity]');
	var wholesaleQuantity = $('[name=wholesaleQuantity]');

	var wholesaleProductId = $('[name=wholesaleProductId]');
	var retailProductId = $('[name=retailProductId]');

	if(type == 0){
		 if(isNaN(wholesaleQuantity[index].value) || wholesaleQuantity[index].value.trim()==''){
		 	alert("输入格式不正确!");
		 	return false;
		 }
		 if(parseInt(wholesaleQuantity[index].value) > 100){
			 alert("商品数量超过最大限制");
			 wholesaleQuantity[index].value = quantity;
			 return false;
		 }
		 return true;

	}else{
		if(isNaN(retailQuantity[index].value) || retailQuantity[index].value.trim()==''){
			alert("输入格式不正确!");
			return false;
		}
		
		 if(actionType == 'minus'){
			 if(parseInt(retailQuantity[index].value) > 101){
				 alert("商品数量修改失败");
				 retailQuantity[index].value -= 1;
				 return false;
			 }
		 }else if(actionType == 'add'){
			 if(parseInt(retailQuantity[index].value) >= 100){
				 alert("商品数量修改失败");
				// retailQuantity[index].value = parseInt(retailQuantity[index].value) + 1;
				 return false;
			 }
		 }else{
			 if(parseInt(retailQuantity[index].value) > 100){
				 alert("商品数量超过最大限制");
				 retailQuantity[index].value="100";
				 retailQuantity[index].onblur();
				 return false;
			 }
		 }
		 return true;
	}
}

//提示框
var msgLeft,msgTop;
function showMsgCue(type){
	getAbsolutePos();
	var money = "0.00";
	if(type == 0){
		money =  $("#wholesaleShoppingCartAmount").html();
	}else{
		money =  $("#retailShoppingCartAmount").html();
	}
	var objs = document.createElement("div");
	objs.id = "objsDiv";
	var objDiv = document.createElement("div");
	objDiv.id = "msgDiv";
	objs.style.cssText="position:absolute;width:"+130+"px"+";height:"+95+"px"+";left:"+msgLeft+"px;"+"top:"+msgTop+
						 "px"+";background:#fdfce0;border:1px solid;border-color:#f8991a";
	objDiv.style.border = "1px";
    objDiv.innerHTML = "<div style='line-height:25px;margin-left:10px;'>商品数量修改成功!<br/> 商品金额为:" +
    		"<font style='color:red'>￥"+money+"</font><br/>" + "<div style='margin-left:40px;'><a onclick='closeDiv()'>关闭</a></div><div>";

    var objDivSmall = document.createElement("div");
    objDivSmall.id = "smallDiv";
    //objDivSmall.style.cssText="<img src='../../emarket/float_arrows.gif'/>";
    //objDivSmall.innerHTML= "<div><span><img src='../../emarket/float_arrows.gif'/></span></div>";
    objs.appendChild( objDiv );
    objs.appendChild(objDivSmall);
    document.body.appendChild(objs);
    setTimeout("closeDiv()",2000);
}

function closeDiv(){
	$("#objsDiv").remove();
}

function getAbsolutePos()
{
   var t=obj.offsetTop;
   var l=obj.offsetLeft;
   while(obj=obj.offsetParent)
   {
       t+=obj.offsetTop;
       l+=obj.offsetLeft;
   }
   msgTop = t - 100;
   msgLeft = l - 50;
}

function showFavoriteAddSuccessMsg(){
	alert("已成功加入收藏夹");
	window.location.href= "/emarket/favorite/favorite.faces";
	return;
}

function showFavoriteWholesaleAddSuccessMsg(){
	alert("已成功加入收藏夹");
	window.location.href= "/emarket/favorite/favoriteWholesale.faces";
	return;
}

function showShoppingCartAddSuccessMsg(){
	window.location.href= "/emarket/shoppingcart/shoppingCart.faces";
	return;
}

function showSameProductInFavoriteMsg(){
	alert("该商品已被收藏，价格重新更新");
	window.location.href= "/emarket/favorite/favorite.faces";
}

function showSameProductInFavoriteWholesaleMsg(){
	alert("该商品已被收藏，价格重新更新");
	window.location.href= "/emarket/favorite/favoriteWholesale.faces";
}

function sumFavorite(sum){
	$("#sumFavorite").text('('+sum+')');
}

function removeFavorite(){
	if(confirm("确定要删除吗?")){
	  deleteProductAtFavorite.submit();
	}
}

//取得cookie购物车数量
function getShoppingCartCookieCount(){
	var i = 0;
	var j = 0;
	while(document.cookie.split(";").length > i){
		if(document.cookie.split(";")[i].indexOf("RETAIL")!=-1){
			j++;
		}
		i++;
	}
	return j;
}

