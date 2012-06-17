function showDetail(obj) {
	$('.userProfile_edit').each(function() {
		$(this).hide();
	});
	$('.userProfile_detail').each(function() {
		$(this).show();
	});
	$('.edit_button').each(function() {
		$(this).hide();
	});
	$('.cancel_button').each(function() {
		$(this).hide();
	});
	$('.back_button').show();

	if ($(obj).next().css('display') == 'none') {
		$('.finder').each(function() {
			$(this).hide();
		});
		$(obj).next().show();
	} else {
		$(obj).next().hide();
	}
}

function modifyUserProfile() {
	$('.userProfile_edit').show();
	$('.userProfile_detail').hide();

	$('.edit_button').show();
	$('.cancel_button').show();
	$('.back_button').hide();
}

function cancelDetail() {
	$('.finder').hide();
}

function approval(id) {
	var userProfileId = document.getElementById('approvalForm:userProfileId');
	userProfileId.value = id;
	approvalShopUser.submit();
}

function cancel(id) {
	var userProfileId = document.getElementById('approvalForm:userProfileId');
	userProfileId.value = id;
	cancelShopUser.submit();
}

function update(id) {
	var email = document.getElementById('email' + id);
	var fullName = document.getElementById('fullName' + id);
	var nickname = document.getElementById('nickname' + id);
	var address = document.getElementById('address' + id);
	var mobilePhone = document.getElementById('mobilePhone' + id);
	var phone = document.getElementById('phone' + id);
	var postCode = document.getElementById('postCode' + id);

	var newEmail = document.getElementById('approvalForm:newEmail');
	var newFullName = document.getElementById('approvalForm:newFullName');
	var newNickname = document.getElementById('approvalForm:newNickname');
	var newAddress = document.getElementById('approvalForm:newAddress');
	var newMobilePhone = document.getElementById('approvalForm:newMobilePhone');
	var newPhone = document.getElementById('approvalForm:newPhone');
	var newPostCode = document.getElementById('approvalForm:newPostCode');

	newEmail.value = email.value;
	newFullName.value = fullName.value;
	newNickname.value = nickname.value;
	newAddress.value = address.value;
	newMobilePhone.value = mobilePhone.value;
	newPhone.value = phone.value;
	newPostCode.value = postCode.value;

	var validateMsg = "";
	if(!validateEmail(email.value)){
		validateMsg +=  "E-mail格式有误!\n";
	}

	if(!validateMobile(mobilePhone.value)){
		validateMsg +=  "手机号码格式有误!\n";
	}

	if(!validatePhone(phone.value)){
		validateMsg +=  "固定电话格式有误!\n";
	}

	if(!validatePostCode(postCode.value)){
		validateMsg +=  "邮编格式有误!\n";
	}

	if(validateMsg != ""){
		alert(validateMsg);
		return;
	}

	var userProfileId = document.getElementById('approvalForm:userProfileId');
	userProfileId.value = id;
	updateUserProfile.submit();
}

/*validate email*/
function validateEmail(vValue){
	var _r = true;
	if(vValue.Trim().length<=0)
		return _r;
	if(!(/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/).test(vValue.Trim())){
		_r = false;
	}
	return _r;
}

/*validate telphone*/
function validatePhone(vValue){
	var _r = true;
	var phone=vValue.Trim();
	if(phone.length<=0)
		return _r;
	strTemp="0123456789-()# ";
	for (i=0;i<phone.length;i++){
		j=strTemp.indexOf(phone.charAt(i));
		if (j==-1){
			_r = false;
		}
	}
	return _r;
}

/*validate mobile phone*/
function validateMobile(vValue){
	var _r = true;
	var phone=vValue.Trim();
	if(phone.length<=0)
		return _r;
	if(!(/^0{0,1}(1)[0-9]{10}$/.test(vValue.Trim())))
		_r = false;
	return _r;
}

/*validate postcode*/
function validatePostCode(vValue){
	var _r = true;
	if(vValue.Trim().length<=0)
		return _r;
	if(!(/^[0-9]\d{5}(?!\d)$/).test(vValue.Trim())){
		_r = false;
	}
	return _r;
}