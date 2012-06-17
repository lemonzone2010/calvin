$(document).ready(function(){
	var count = getShoppingCartCookieCount();
	document.getElementById("shoppingCartCookieCount").innerHTML="（"+count+"）";
});