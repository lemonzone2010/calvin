// 导航栏配置文件
var outlookbar=new outlook();
var t;
$(function(){
	jQuery.ajax({
		"url":"/navigation/NavigationData.faces",
		"cache":false,
		"dataType":"json",
		"success":function(data){
			$.each(data.nav,function(idx,record){
				if(typeof(record.childrens) != 'undefined' && record.childrens.length > 0){
					t=outlookbar.addtitle(record.name,record.applicationId,1);
					$.each(record.childrens,function(index,children){
						outlookbar.additem(children.name,t,children.url);
					});
				}
			});
			if(data.isAdmin) {
				t=outlookbar.addtitle("应用管理","导航管理",1);
				outlookbar.additem("系统应用",t,"/navigation/menu/applicationManage.faces");
				t=outlookbar.addtitle("菜单管理","导航管理",1);
			}
			var output = "<ul>";
			$.each(data.app,function(idx,record){
				var istitle = false;
				for (i=0;i<outlookbar.titlelist.length;i++){
					if(outlookbar.titlelist[i].sortname == record.id){
						istitle = true;
						break;
					}
				}
				if(istitle){
					if(idx==0){
						output += "<li id=man_nav_"+(idx+1)+" onclick=list_sub_nav(id,'"+record.id+"') class=bg_image_onclick>"+record.applicationName+"</li>";
					}else{
						output += "<li id=man_nav_"+(idx+1)+" onclick=list_sub_nav(id,'"+record.id+"') class=bg_image>"+record.applicationName+"</li>";
					}
				}
				if(data.isAdmin){
					outlookbar.additem(record.applicationName,t,"/navigation/menu/navigationManage.faces?appId="+record.id);
				}
			});
			if(data.isAdmin){
				if(output == "<ul>"){
					output += "<li id=man_nav_"+(data.app.length+1)+" onclick=list_sub_nav(id,'导航管理') class=bg_image_onclick>导航管理</li>";
				}else{
					output += "<li id=man_nav_"+(data.app.length+1)+" onclick=list_sub_nav(id,'导航管理') class=bg_image>导航管理</li>";
				}
			}
			output += "</ul>";
			window.top.frames['mainFrame'].getObject("nav").innerHTML = output;
			if(typeof(data.app[0]) == "undefined"){
				initinav("导航管理");
			}else{
				initinav(data.app[0].id);
			}
		}
	});
});

/**
 * 去除数组重复值
 * @param arrayData
 * @return
 */
function distinctArray(arrayData){
	var newArray = new Array();
	for(idx in arrayData){
		var repeat = false;
		for(var i=0;i<newArray.length;i++){
			if(arrayData[idx] == newArray[i]){
				repeat = true;
				break;
			}
		}
		if(!repeat){
			newArray.push(arrayData[idx]);
		}
	}
	return newArray;
}






