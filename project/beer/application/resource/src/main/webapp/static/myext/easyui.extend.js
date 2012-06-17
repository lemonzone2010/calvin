var serverUrl = "demo1"
$(document).ready(function() {
    //define page size
    $('#dg').datagrid({
        pageSize: 20,
        onSelect: function() {
    	alert('onSelect');
            },
        onUnselect: function() {
    	alert('onUnselect');
            },
    });
function changeToolbarStatus(){
	var rows = $('#dg').datagrid('getSelections');
	if(rows.length==0){
		
	}
}
    var pg = $("#dg").datagrid("getPager");
/*    if (pg) {
        $(pg).pagination({
            onBeforeRefresh: function() {
            },
            onRefresh: function(pageNumber, pageSize) {
            	$('#dg').datagrid('unselectAll');
            },
            onChangePageSize: function() {
            	$('#dg').datagrid('unselectAll');
            },
            onSelectPage: function(pageNumber, pageSize) {
            	$('#dg').datagrid('unselectAll');
            }
        });
    }*/

    // check name availability on focus lost
   /* $('#add').click(function() {
        	$( "#addDialog" ).dialog("open");
        return false;
    });*/
    
    /*$('#name').blur(function() {
        if ($('#name').val()) {
            checkAvailability();
        }
    });*/
    //$('.searchbox-text').keydown(function() {
    //	if ($('.searchbox-text').val()) {	
    //		
    //		qq($('.searchbox-text').val(),$('.l-btn-text').text());
    //	}
    //});
    $("#addForm").submit(function() {
        var account = $(this).serializeObject();
        var _url = serverUrl;
        if ($('#id').val()) {
            _url = serverUrl+"?_method=put"
        }
        $.postJSON(_url, account,
        function(data) {
        	if(data.result.status){
           		 $.messager.alert('Info', "操作成功", 'info');
                 $('#dg').datagrid('reload');
	            $("#addDialog").dialog("close");
            }else{
           		 $.messager.alert('Info', "数据操作失败，原因:"+data.result.msg, 'info');
            }
        	$('#dg').datagrid('unselectAll');
        });
        return false;
    });
});

function checkAvailability() {
    $.getJSON(serverUrl+"/check/name", {
        value: $('#name').val()
    },
    function(result) {
        if (result.result.status==false) {
            fieldValidated("name", {
                valid: true
            });
        } else {
            fieldValidated("name", {
                valid: false,
                message: "Name:" + $('#name').val() + " is not available "
            });
        }
    });
}

function fieldValidated(field, result) {
    if (result.valid) {
        $("#" + field + "Label").removeClass("error");
        $("#" + field + "\\.errors").remove();
        $('#create').attr("disabled", false);
    } else {
        $("#" + field + "Label").addClass("error");
        if ($("#" + field + "\\.errors").length == 0) {
            $("#" + field).after("<span id='" + field + ".errors'>" + result.message + "</span>");
        } else {
            $("#" + field + "\\.errors").html("<span id='" + field + ".errors'>" + result.message + "</span>");
        }
        $('#create').attr("disabled", true);
    }
}

function deleteOne() {
    var row = $('#dg').datagrid('getSelected');

    if (row) {
        if (confirm('Sure delete?' + row.name)) {
            $.post("demo/" + row.id, {
                _method: "delete"
            },
            function(data) {
                $.messager.show({
                    title: 'My Title',
                    msg: 'Delete successed.',
                    timeout: 2000,
                    showType: 'fade'
                });

                // alert("Data Loaded: " + data);
                $('#dg').datagrid('reload');
            });

        }
        return false;
    }
}
function deleteSelected() {
    var ids = [];
    var rows = $('#dg').datagrid('getSelections');
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].id);
    }

    if (rows.length>0) {
        if (confirm('Sure delete?')) {
            $.ajax({
            	url:serverUrl,
            	type:'POST',
            	data:{
            		'items': ids,
                	_method: "delete"
                	},
            	dataType:'JSON',
            	success:function(data){
            		if (data.result.status) {
		                $.messager.show({
		                    title: '删除',
		                    msg: '您的删除操作已成功完成.',
		                    timeout: 2000,
		                    showType: 'fade'
		                });
		                $('#dg').datagrid('reload');
		                $('#dg').datagrid('unselectAll');
	                }else{
	                	 $.messager.show({
		                    title: '删除',
		                    msg: '您的删除操作失败:'+data.result.msg,
		                    timeout: 2000,
		                    showType: 'fade'
		                });
	                }
            	}
            });

        }
    }else{
			    	$.messager.alert('提示', "请选择一行再操作", 'info');
			    }
}
/*
function deleteit(){
	var row = $('#dg').datagrid('getSelected');

			if (row){
				if(confirm('Sure delete?'+row.name)){
					$.postJSON("demo", 'id='+row.id+'&_method=DELETE', function(data) {
						alert("delete success.")
						$('#dg').datagrid('reload');
					}
				}
			}
}*/
function getSelected() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        alert('Item ID:' + row.id + "\nPrice:" + row.name);
    }
}

function getSelections() {
    var ids = [];
    var rows = $('#dg').datagrid('getSelections');
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].itemid);
    }
    alert(ids.join('\n'));
}
function qq(value, name) {

    //	alert("You will search:"+value+":"+name)
    $('#dg').datagrid({
        pageNumber: 1,
        queryParams: {
            q: name + ":" + value
        }
    });
    $('#dg').datagrid('reload');
}
