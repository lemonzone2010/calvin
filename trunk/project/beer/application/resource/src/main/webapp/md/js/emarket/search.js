/**
 * 搜索页面初始化
 */
$(function(){
	//初始化关键字
	$("#index_keyword").val($("#tmp_keyword").val());
	$("#index_keyword").css({'color':'#ccc'}).focus(function(){
		$(this).css({'color':'#333'});
	}).blur(function(){
		$(this).css({'color':'#ccc'});
	});

	//tab初始化
	$(".tsearch ul li").bind('click',function(){
		$(".tsearch ul .current").removeClass("current");
		$(this).addClass("current");
		$("#searchType").val($(this).attr("alt"));
	});

	var url=location.href+"";
	if(url.indexOf("?")!=-1){
		url=url.substring(0,url.indexOf("?"));
	}

	if (url.indexOf("wholesale")!=-1) {
		$(".tsearch ul li").each(function(){
			if($(this).attr("alt")=="wholesale"){
				$(this).click();
			}
		});
	}
})

/**
 * 全文搜索
 * @return
 */
function fulltextQuerySearch(){
	var keyword = $("#index_keyword").val();
	keyword = $.trim(keyword)//忽略空格
	if(keyword==''){
		return;
	}else{
		var type = $("#searchType").val();
		if (type == "retail") {//如果是零售
			location.href="/emarket/search?keyword=" +encodeURIComponent(encodeURIComponent(keyword));
		}else{
			location.href="/emarket/search/wholesale.faces?type=" +encodeURIComponent(encodeURIComponent(keyword));
		}
	}
}

/**
 * 全文搜索添加回车事件
 * @return
 */
function enterSearch(){
	var e = document.all ? event:arguments.callee.caller.arguments[0];
	var key = e.keyCode || e.which;
	if(key==13){
		if(typeof(fulltextQuerySearch)=='function')fulltextQuerySearch.call();
		if ($.browser.ie) {
			e.returnValue = false;
		} else {
			e.preventDefault();
		}
	}
}
