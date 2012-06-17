/**
 * 全选
 * @param name
 * @param flag
 * @return
 */
function checkBoxAll(name, flag) {
	$("input[name='" + name + "']").each(function() {
		this.checked = flag;
	});
}
/**
 * 当全部选中时修改选中按钮
 * @param name
 * @param checkBoxName
 * @param flag
 * @return
 */
function checkBoxChange(name, checkBoxName, flag) {
	if (!flag) {
		checkBoxAll(name, false);
	} else {
		if ($("input[name='" + checkBoxName + "']").length == $("input[name='"
				+ checkBoxName + "']:checked").length) {
			checkBoxAll(name, true);
		}
	}
}
/**
 * 获取选中的记录结果以,分隔
 * @param name
 * @return
 */
function getCheckBoxId(name) {
	var s = '';
	$("input[name='" + name + "']:checked").each(function() {
		s += $(this).val() + ',';
	});
	return s.substring(0, s.length - 1);
}
/**
 * 获取选中的记录数
 * @param name
 * @return
 */
function getCheckedCount(name){
	return $("input[name='"+name+"']:checked").length;
}
/**
 * 获得所有的记录数
 * @param name
 * @return
 */
function getAllCount(name){
	return $("input[name='"+name+"']").length;
}