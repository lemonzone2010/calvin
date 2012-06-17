function deleteNewById(id) {
	if(confirm('确定要删除公告吗？')){
	$("#newsId").val(id);
	deleteNews.submit();
	}
}
function updateNewsById(id){
	$("#newsId").val(id);
	updateNews.submit();
}

function modifyNews(){
	//判断 content值是否为空
	if($('.editor').omEditor('getData')==''){
		alert('内容不能为空');
		return;
	}
	$('#content').val($('.editor').omEditor('getData'));
	updateNews.submit();
}

function saveNews(){
	//判断 content值是否为空
	if($('.editor').omEditor('getData')==''){
		alert('内容不能为空');
		return;
	}
	$('#content').val($('.editor').omEditor('getData'));
	addNews.submit();
}

$(document).ready(function() {
	if($('.editor').length > 0){
		$('.editor').omEditor({
			 onKeyUp : count,
			 filebrowserImageUploadUrl : '/emarket/omEditorFileUploadServlet?type=Images',
			 allowImageType : ['jpg','gif','png','bmp']
		});
	}
});

function count(event) {
	$('#viewCount').css("display","block");
    $('#countText').text($('.editor').omEditor('getPlainText').length);
}

function newsList(){
  window.location="newsList.faces";
}

