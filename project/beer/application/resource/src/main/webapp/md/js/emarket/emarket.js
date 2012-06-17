function emarket_selectCustomization(obj, name, item) {
	$(obj).parent().parent().children().each(function() {
		$(this).children().each(function() {
			$(this).removeClass("selected");
		});
	});

	$(obj).addClass("selected");
	$(".selectedAttr").each(function() {
		if ($(this).attr("alt") == name) {
			$(this).css("display", "block");
			$(this).text(item);
		}
	})
	$(".selectedAttrs").parent().show();
}

function emarket_detailSelect(obj, tagid) {
	$(".current2").removeClass("current2");
	$(obj).parent().addClass("current2");
	$(".item_pd").children().each(function() {
		$(this).hide();
	});
	$("#" + tagid).show();
}

function emarket_addShopCart(shoppingCartType, wholeSaleThrehold) {
	var productIdObj = document.getElementById("productId");

	var isCancelObj=document.getElementById("isCancel");
	if(isCancelObj!=null&&isCancelObj.value=="true"){
		alert("该商品已下架，无法购买此商品");
		return;
	}

	var inStockObj=document.getElementById("inStock");
	var stockObj=document.getElementById("stock");
	if(inStockObj!=null&&inStockObj.value=="false"){
		alert("该商品" + stockObj.value + "，无法购买此商品");
		return;
	}

	if (shoppingCartType == "WHOLESALE") {
		// 批发
		var buynum = document.getElementById("quantity").value;
		buynum = parseInt(buynum);
		if (isNaN(buynum)) {
			alert("请输入正确的购买数量");
			return;
		}
		if (wholeSaleThrehold == null) {
			alert("起批量为空");
			return;
		}
		if (buynum < wholeSaleThrehold) {
			alert("购买数量未达到起批量");
			return;
		}
	}
	document.getElementById("shoppingCartType").value = shoppingCartType;
	var hasEmpty = false;
	var attrs = "";
	$(".selectedAttr").each(function() {
		if (hasEmpty) {
			return;
		}
		var name = $(this).attr("alt");
		var text = $(this).text();
		if (text == null || text == "") {
			alert("请选择:" + name);
			hasEmpty = true;
		}
		attrs = attrs + name + ":" + text + ";";
	});
	if (hasEmpty) {
		return;
	}
	if (attrs.indexOf(";",attrs.length-1)!=-1) {
		attrs = attrs.substring(0,attrs.length-1);
	}
	document.getElementById("attribute").value = attrs;
	if (productIdObj.value == null || productIdObj.value == ""
			|| productIdObj.value == "0") {
		alert("商品ID为空，可能是预览，无法购买此商品");
		return;
	}

	var productId = productIdObj.value;
	var attribute = attrs;
	var shoppingCartType = shoppingCartType;
	var quantity = document.getElementById("quantity").value;
	var addFrom = "productPage";
	window.location = "/emarket/shoppingcart/shoppingCart.faces?productId="
			+ productId + "&attribute=" + encodeURIComponent(attribute) + "&shoppingCartType="
			+ shoppingCartType + "&quantity=" + quantity + "&addFrom="
			+ addFrom;
}


function emarket_addShopOrder(shoppingCartType, wholeSaleThrehold) {
	var productIdObj = document.getElementById("productId");

	var isCancelObj=document.getElementById("isCancel");
	if(isCancelObj!=null&&isCancelObj.value=="true"){
		alert("该商品已下架，无法购买此商品");
		return;
	}

	var inStockObj=document.getElementById("inStock");
	var stockObj=document.getElementById("stock");
	if(inStockObj!=null&&inStockObj.value=="false"){
		alert("该商品" + stockObj.value + "，无法购买此商品");
		return;
	}

	if (shoppingCartType == "WHOLESALE") {
		// 批发
		var buynum = document.getElementById("quantity").value;
		buynum = parseInt(buynum);
		if (isNaN(buynum)) {
			alert("请输入正确的购买数量");
			return;
		}
		if (wholeSaleThrehold == null) {
			alert("起批量为空");
			return;
		}
		if (buynum < wholeSaleThrehold) {
			alert("购买数量未达到起批量");
			return;
		}
	}
	document.getElementById("favoriteType").value = shoppingCartType;
	var hasEmpty = false;
	var attrs = "";
	$(".selectedAttr").each(function() {
		if (hasEmpty) {
			return;
		}
		var name = $(this).attr("alt");
		var text = $(this).text();
		if (text == null || text == "") {
			alert("请选择:" + name);
			hasEmpty = true;
		}
		attrs = attrs + name + ":" + text + ";";
	});
	if (hasEmpty) {
		return;
	}
	document.getElementById("attribute").value = attrs;
	if (productIdObj.value == null || productIdObj.value == ""
			|| productIdObj.value == "0") {
		alert("商品ID为空，可能是预览，无法购买此商品");
		return;
	}
	var productId = productIdObj.value;
	var attribute = attrs;
	var shoppingCartType = shoppingCartType;
	var quantity = document.getElementById("quantity").value;
    window.location=("/orderbook/order/fillInWholesaleOrderInfo.faces?productId="+productId+"&quantity="+quantity+"&attribute="+encodeURIComponent(attribute)+"");
}
function emarket_addShopCartById(productId, attributes) {
	var attrs = attributes.split(";");
	var attribute = "";
	for ( var i = 0; i < attrs.length; i++) {
		var items = attrs[i].split(":");
		for ( var j = 0; j < items.length; j++) {
			if( items[0] == null || items[0] == ""){
				continue;
			}
			var name = items[0]!=null?items[0]:'' ;
			var value = items[1]!=null? items[1].split("|")[0]:'';
			attribute += name + ":" + value + ";";
		}
	}
	var shoppingCartType = 'RETAIL';
	var quantity = 1;
	var addFrom = "productPage";
	window.location = "/emarket/shoppingcart/shoppingCart.faces?productId="
			+ productId + "&attribute=" + encodeURIComponent(attribute) + "&shoppingCartType="
			+ shoppingCartType + "&quantity=" + quantity + "&addFrom="
			+ addFrom;
}

function emarket_justNumber(obj) {
	obj.value = obj.value.replace(/[^\d]/g, "");
	if (obj.value == "") {
		obj.value = 1;
	}
}

function emarket_addFavorite() {
	var productIdObj = document.getElementById("productId");

	var isCancelObj=document.getElementById("isCancel");
	if(isCancelObj!=null&&isCancelObj.value=="true"){
		alert("该商品已下架，无法收藏此商品");
		return;
	}
	var attrs = "";
	$(".selectedAttr").each(function() {
		var name = $(this).attr("alt");
		var text = $(this).text();
		attrs = attrs + name + ":" + text + ";";
	});
	document.getElementById("attribute").value = attrs;
	if (productIdObj.value == null || productIdObj.value == ""
			|| productIdObj.value == "0") {
		alert("商品ID为空，可能是预览，无法收藏此商品");
		return;
	}
	var favoriteType = document.getElementById("favoriteType");
	if(favoriteType.value == "RETAIL"){
		window.location = "/emarket/favorite/favorite.faces?productId="
				+ productIdObj.value + "&addFavorite=yes&saleType=RETAIL";
	}else{
		window.location = "/emarket/favorite/favoriteWholesale.faces?productId="
			+ productIdObj.value + "&addFavorite=yes&saleType=WHOLESALE";
	}
}

function emarket_quantityAdd(type, id) {
	var num = document.getElementById(id).value;
	num = parseInt(num);
	if (isNaN(num)) {
		num = 1;
	}
	if (type == 1) {
		// 则为加
		num += 1;
	} else {
		num -= 1;
		if (num <= 0) {
			num = 1;
		}
	}
	document.getElementById(id).value = num;
}

function emarket_imagezoomInit() {
	KISSY.use("dom,event,imagezoom", function(S, DOM, Event, ImageZoom) {
		var m = new ImageZoom( {
			imageNode : "#multi",
			align : {
				node : "#multi",
				points : [ "tr", "tl" ],
				offset : [ 0, 0 ]
			}
		});
		imgReset(m);
		// 切换时, 动态设置大图路径
			Event.on(".smallimg", 'click', function() {
				$(".current").removeClass("current");
				$(this).parent().parent().addClass("current");
				var data = DOM.attr(this, 'data-ks-imagezoom');
				DOM.attr('#multi', 'src', data);
				m.set('hasZoom', DOM.attr(this, 'data-has-zoom'));
				if (data) {
					m.set('bigImageSrc', data);
					imgReset(m);
				}
			});
		});
}

function imgReset(m){
	m.set('bigImageWidth', 900);
	m.set('bigImageHeight', 900);
	m.set('imageWidth', 400);
	m.set('imageHeight', 400);
	m._setLensSize();
}

function imageUploadInit(){
	//商品图片预览滚动栏
	$(".productImageArea .scrollable").scrollable({
		speed: 600
	});

	// 显示商品图片预览操作层
	$(".productImageArea li").livequery("mouseover", function() {
		$(this).find(".productImageOperate").show();
	});

	// 隐藏商品图片预览操作层
	$(".productImageArea li").livequery("mouseout", function() {
		$(this).find(".productImageOperate").hide();
	});

	// 商品图片左移
	$(".left").livequery("click", function() {
		var $productImageLi = $(this).parent().parent().parent();
		var $productImagePrevLi = $productImageLi.prev("li");
		if ($productImagePrevLi.length > 0) {
			$productImagePrevLi.insertAfter($productImageLi);
		}
	});

	// 商品图片右移
	$(".right").livequery("click", function() {
		var $productImageLi = $(this).parent().parent().parent();
		var $productImageNextLi = $productImageLi.next("li");
		if ($productImageNextLi.length > 0) {
			$productImageNextLi.insertBefore($productImageLi);
		}
	});

	//商品图片删除
	$(".delete").livequery("click", function() {
		var $productImageLi = $(this).parent().parent().parent();
		var $productImagePreview = $productImageLi.find(".productImagePreview");
		var $productImageIds = $productImageLi.find("input[name='productImageIds']");
		var $productImageFiles = $productImageLi.find("input[name='productImages']");
		var $productImageParameterTypes = $productImageLi.find("input[name='productImageParameterTypes']");
		$productImageIds.remove();
		$productImageFiles.after('<input type="file" name="productImages" hidefocus="true" />');
		$productImageFiles.remove();
		$productImageParameterTypes.remove();
		$productImagePreview.html("暂无图片");
		$productImagePreview.removeAttr("title");
	});
}
