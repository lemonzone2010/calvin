

function deliveryAddr_modifyAddrSubmit(id) {
	var obj = document.getElementById("modifyAddrId");
	obj.value = id;
	obj.form.submit();
}

function deliveryAddr_deleteAddrSubmit(id) {
	if(confirm('确定要删除吗？')){
		deleteAddr.addParam("deliveryAddrId", id);
		deleteAddr.submit();
		}
}
function deliveryAddr_changeDefaultAddrId(id) {
	changeDefaultAddr.addParam("deliveryAddrId", id);
	changeDefaultAddr.submit();
}

function deliveryAddr_ResetInputs() {
	var formId = "addrForm";
	deliveryAddr_reset("#" + formId + "\\:consignee");
	deliveryAddr_reset("#" + formId + "\\:streetAddr");
	deliveryAddr_reset("#" + formId + "\\:postcode");
	deliveryAddr_reset("#" + formId + "\\:mobil");
	deliveryAddr_reset("#" + formId + "\\:phone");
	$("#"+formId+"\\:isAdd").attr("disabled","true");
	$("#"+formId+"\\:isAdd").attr("checked","true");
}
function deliveryAddr_reset(id) {
	$(id).val('');
	$(id).next().children().each(function() {
		$(this).css("display", "none");
	});
	$(id).next().val('');
}