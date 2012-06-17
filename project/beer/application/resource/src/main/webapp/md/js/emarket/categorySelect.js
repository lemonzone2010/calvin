$(function(){
	bindSelectedEven();
});

function bindSelectedEven(){
		var rootNodes = $("#category-cascading").find("ul").find("li");
		rootNodes.unbind();
		rootNodes.bind('mouseover',function(){
            if(!$(this).hasClass('selected')){
                $(this).addClass('hover');
            }
        });
		rootNodes.bind('mouseout',function(){
            if($(this).hasClass('hover')){
                $(this).removeClass('hover');
            }
        });
		rootNodes.bind("click",function(){
			var id = this.id;
			var parent = $(this).parent();
			parent.find("li[class='selected']").removeClass("selected");
			$(this).addClass("selected");
			if(parent.attr("id") == "root_node"){
				$("#children_node").html("");
				$("#leaf_node").html("");
				$("#brand_node").html("");
				$("#children_node").html("loading...");
				selectedNodeSubmit.addParam("categoryId",id);
				selectedNodeSubmit.submit();
			}else if(parent.attr("id") == "children_node"){
				$("#leaf_node").html("");
				$("#brand_node").html("");
				$("#leaf_node").html("loading...");
				selectedNodeSubmit.addParam("categoryId",id);
				selectedNodeSubmit.submit();
			}else if(parent.attr("id") == "leaf_node"){
				$("#brand_node").html("");
				$("#brand_node").html("loading...");
				selectedNodeSubmit.addParam("categoryId",id);
				selectedNodeSubmit.submit();
			}else{
				var categoryHtml = "";
				$("#category-cascading").find(".selected").each(function(index){
					if(index == 0){
						categoryHtml += $(this).find("span").text()+" > ";
					}
					if($(this).parent().attr("id") == "leaf_node"){
						$("#categoryId").val(this.id);
						categoryHtml += $(this).find("span").text();
					}
					if($(this).parent().attr("id") == "brand_node"){
						$("#brandId").val(this.id);
						$("#selected_brand").html($(this).find("span").text());
					}
				});
				$("#selectCategory").html('');
				$("#selectCategory").html(categoryHtml);
				getCategoryAttribute();
			}
		});
}

function construstNode(output,level){
	if(output == ""){
		output = "<font color='red'>该类别没有内容</font>";
	}
	if(level == 1){
		$("#children_node").html(output);
	}
	if(level == 2){
		$("#leaf_node").html(output);
	}
	if(level == 3){
		$("#brand_node").html(output);
	}
}