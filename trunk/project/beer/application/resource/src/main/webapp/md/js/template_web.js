$(function(){
	$("#mydnew_break").append("");
//不需要获取顶部menu的名称     如果要则 在您现在位置的 锦绣商城 后面加上      <a href=''>"+modeName+"</a> &gt;
//	var modeName = $(".i_nav").find("span[class='actived']").text();

	var defChild = window.document.title;
	var defParent = null ;
	$(".mydnew_sidel").find("a").each(function(){
		if($(this).text() == defChild){
			$(this).removeClass("actived").addClass("actived");
			defParent = $(this).parent().parent().parent().prev().text();
		}
	});
	var secondChild = null;
	var secondChildHref = null;
	var secondChildLink = "";
	//查找本页面是否是二级子页面
	if(defParent == null){
		$(".mydnew_sidel").find(".hide").each(function(){
			if($(this).text() == defChild){
				$(this).prev().removeClass("actived").addClass("actived");
				secondChild = $(this).prev().text();
				if(secondChild!=null){
					secondChildHref = $(this).parent().find("a").attr("href");
					defParent = $(this).parent().parent().parent().prev().text();
				}

			}
		});
		if(secondChild != null){
			secondChildLink = "<a href='" + secondChildHref + "'>" + secondChild + "</a> &gt; "
		}
	}

	var output = "您现在的位置：<a href='/product/index.html'>锦绣商城</a> &gt; <a href=''>"+defParent+"</a> &gt; "+secondChildLink+" <a href=''>"+defChild+"</a>";
	$(".mydnew_break").html('');
	$(".mydnew_break").append(output);
	//顶部菜单栏
	ebiz_topMenuInit();
	ebiz_searchMenuInit();
});

function menuClick(){
	var child = $(this).find("a").text();
	var parent = $(this).parent().parent().prev().text();
	//var modeName = $(".i_nav").find("span[class='actived']").text();
	var output = "您现在的位置：<a href='/product/index.html'>锦绣商城</a> &gt; <a href=''>"+parent+"</a> &gt; <a href=''>"+child+"</a>";
	$(".mydnew_break").html('');
	$(".mydnew_break").append(output);
	$(".divborder").find("a[class='actived']").removeClass('actived');
	$(this).find("a").addClass("actived");
}

function ebiz_topMenuInit(){
	$(".navitem .nav .current").removeClass("current");
	$(".navitem .nav li").each(function(){
			var url=window.location+"";
			var href=$(this).children().first().attr("href");
			if(href==null){
				return;
			}
			//去掉前面的 http://域名:6888/
			url=url.substring(url.indexOf("/",7));
			if(url.indexOf("/search/category")!=-1 && url.indexOf("&type")!=-1){
				url = url.substring(0,url.indexOf("&type"));
			}
			if(url.indexOf("/search/wholesale")!=-1 && url.indexOf("?type")!=-1){
					url = url.substring(0,url.indexOf("?type"));
			}

			if(href==url){
				$(this).addClass("current");
			}else if(url.indexOf("/brand/")!=-1 && href.indexOf("/brand/")!=-1){
				$(this).addClass("current");
			}
	});
}

function ebiz_searchMenuInit(){
	var url=window.location+"";
	url=url.substring(url.indexOf("/",7));
	$("#search li a").each(function(index){
		var href=$(this).attr("href");
		var pvalue=ebiz_getParame(url,"type");
		var thisValue=ebiz_getParame(href,"type");

		if(url.indexOf('&type=')==-1){
			if(index==0){
				$(this).addClass("category_cur");
			}
		}else if(pvalue==thisValue){
			$(this).addClass("category_cur");
		}
	});
	var parem = "keyword";
	if(url.indexOf("type") != -1){
		parem = "type";
	}

	$("#wholesale li a").each(function(index){
		var href=$(this).attr("href");
		var pvalue=ebiz_getParame(url,parem);
		var thisValue=ebiz_getParame(href,parem);
		if(pvalue!=null ){
			if(pvalue ==  thisValue){
				$(this).addClass("category_cur");
			}else if(href==url){
				$(this).addClass("category_cur");
			}
		}else{
			if(index==0){
				$(this).addClass("category_cur");
			}
		}
	});


	ebiz_wholesaleMenuEvent();

}


function ebiz_getParame(url,pname)
{
    var paraStart=url.indexOf("?");//获取参数的起始位置
    if(paraStart!=-1)//如果有参数
    {
        var paraEnd=url.length;//获取参数的结束位置
        var para=url.substring(paraStart+1,paraEnd);//根据参数的起始位置和结束位置获取所有的参数
        var paraArray=para.split('&');//把参数分隔到数组中
        for ( var i = 0; i < paraArray.length; i++) {
        	if(paraArray[i].indexOf(pname)!=-1){
        		return paraArray[i].split("=")[1];
        	}
		}
    }
    return null;
}

function ebiz_wholesaleMenuEvent(){
	//绑定类型组的点击事件，展开和收缩子类形
	$("#wholesale h3").bind('click',function(){
		if($(this).next().css("display")=="none"){
			$(this).next().css("display","block");
			$(this).addClass("up_icon");
		}else{
			$(this).next().css("display","none");
			$(this).removeClass("up_icon");
		}
	});

	//显示变红的类型组
	$("#wholesale li .category_cur").each(function(){
		$(this).parent().parent().prev().click();
	});
}



