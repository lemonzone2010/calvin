//document.write("<script language=javascript src='namespace.js'><\/script>");
//namespace.register("ebiz");
function ebiz_tipshow(tipid,type){
    var obj=document.getElementById(tipid);
    obj.style.display="block";
    switch(type){
        case 1:
            obj.style.visibility="visible";
            break;
        case 0:
           	obj.style.visibility="hidden";
			break;
    }
}

function ebiz_highlight(inputObj,type){

    switch(type){
        case 1:
        	//取消所有高亮
        	$('.highlight1').each(function() {
        		$(this).removeClass('highlight1');
        	});
        	inputObj.className="text highlight1";
            break;
        case 0:
        	inputObj.className="text";
			break;
    }
}
