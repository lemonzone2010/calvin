function ebiz_topinit(){
	$.getJSON('http://172.20.1.68:7001/iam/GeneralData?jsoncallback=?',function(data){
		if(data.username!=null){
			$("#hadlogin").children().first().text(data.username);
			$("#hadlogin").show();
			$("#nologin").hide();
		}
	});
}

$(document).ready(function(){
	ebiz_topinit();
	ebiz_footinit();
});

function ebiz_footinit(){
	if($("#footer").find(".flinks").length > 0){
		jQuery.ajax({
			"url":"http://172.20.1.68:6888/emarket/PageFootInit",
			"cache":false,
			"dataType":"json",
			"success":function(data){
						pageFoot(data);
					}
		});
	}
}
function pageFoot(data){
	var helpOut="";
	var footOut="";
	$.each(data.helpDate,function(idx,record){
		if(record.name == '页脚导航'){
			$.each(record.childrens,function(index,children){
				if(index>0){
					footOut +="|";
				}
				footOut += "<a target='_blank' href='/product/help/help"+children.id+".html'>"+children.name+"</a>"
			});
		}else{
			helpOut +="<dl class='"+record.cssName+"'>";
			helpOut +="<dt><i></i><a href=''>"+record.name+"</a></dt>";
			$.each(record.childrens,function(index,children){
				helpOut += "<dd>·<a target='_blank' rel='nofollow' href='/product/help/help"+children.id+".html'>"+children.name+"</a></dd>";
			});
			helpOut +="</dl>";
		}
	});
	helpOut +="</dl><div class='clear'></div>";
	$("#footer").find(".flinks").html(footOut);
	$("#helpNavigation").html(helpOut);
}