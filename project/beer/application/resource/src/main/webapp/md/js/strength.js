//命名空间
var ebiz_strength={};
//CharMode函数
//测试某个字符是属于哪一类.
ebiz_strength.CharMode=function(iN) {
	if (iN >= 48 && iN <= 57) // 数字
		return 1;
	if (iN >= 65 && iN <= 90) // 大写字母
		return 2;
	if (iN >= 97 && iN <= 122) // 小写
		return 4;
	else
		return 8; // 特殊字符
}

// bitTotal函数
// 计算出当前密码当中一共有多少种模式
ebiz_strength.bitTotal=function (num) {
	modes = 0;
	for (i = 0; i < 4; i++) {
		if (num & 1)
			modes++;
		num >>>= 1;
	}
	return modes;
}





// checkStrong函数
// 返回密码的强度级别

ebiz_strength.checkStrong =function (sPW) {
	if (sPW.length <= 4)
		return 0; // 密码太短
	Modes = 0;
	for (i = 0; i < sPW.length; i++) {
		// 测试每一个字符的类别并统计一共有多少种模式.
		Modes |= ebiz_strength.CharMode(sPW.charCodeAt(i));
	}

	return ebiz_strength.bitTotal(Modes);

}

// pwStrength函数
// 当用户放开键盘或密码输入框失去焦点时,根据不同的级别显示不同的颜色

ebiz_strength.pwStrength= function(pwd) {
	if (pwd == null || pwd == '') {
		return "A"
	} else {
		S_level = ebiz_strength.checkStrong(pwd);
		switch (S_level) {
		case 0:
			return "A";
			break;
		case 1:
			return "B";
			break;
		case 2:
			return "C";
			break;
		default:
			return "A";
		}
	}
}

function ebiz_strengthValidator(obj,strengthid){
	var pwdStrengthShow=document.getElementById(strengthid);
	pwdStrengthShow.className="strength"+ebiz_strength.pwStrength(obj.value);

}