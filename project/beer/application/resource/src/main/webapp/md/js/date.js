function ebiz_validationDate(dateInput){
	if(dateInput.value == ""){
		return;
	}
	var date = $.calendar.parseDate(dateInput.value,"yy-mm-dd");
	if(date==null){
		alert("请输入正确的日期");
		dateInput.value = "";
		return false;
	}
	return true;
	
}

function ebiz_validationTime(dateInput){
	if(dateInput.value == ""){
		return;
	}
	var date = $.calendar.parseDate(dateInput.value,"yy-mm-dd H:i:s");
	if(date==null){
		alert("请输入正确的时间");
		dateInput.value = "";
		return false;
	}
	return true;
	
}