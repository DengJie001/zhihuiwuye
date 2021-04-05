/**
 * @author DengJie
 * @date 2021-03-04
 * @description 发布活动信息
 */
function submit() {
	var canSubmit = true;
	var title = $('#titleInput').val();
	var address = $('#addressInput').val();
	var managerId = $('#managerIdInput').val();
	var date = $('#dateInput').val();
	var description = $('#descriptionArea').val();
	
	// 验证标题格式是否有误
	if (title == '' || title == null || title == undefined) {
		showTitleInputTip('标题不能为空');
		canSubmit = false;
	} else if (title.length > 64) {
		showTitleInputTip('标题太长,最多64个字符');
		canSubmit = false;
	} else {
		hideTitleInputTip();
	}
	
	// 验证地址格式是否有误
	if (address == '' || address == null || address == undefined) {
		showAddressInputTip('地址不允许为空');
		canSubmit = false;
	} else if (address.length > 64) {
		showAddressInputTip('地址长度超限,最多64个字符');
		canSubmit = false;
	} else {
		hideAddressInputTip();
	}
	
	// 验证管理员电话号码是否正确
	if (managerId == '' || managerId == null || managerId == undefined) {
		showManagerIdInputTip('管理员联系方式不允许为空');
		canSubmit = false;
	} else if (managerId.length > 11) {
		showManagerIdInputTip('联系方式不允许超过11位数');
		canSubmit = false;
	} else {
		hideManagerIdInputTip();
	}
	
	// 验证起始时间是否为空
	if (date == '' || date == null || date == undefined) {
		$('#date-tip').html('起始时间不允许为空');
		$('#date-tip').removeAttr('hidden');
		canSubmit = false;
	} else {
		$('#date-tip').attr('hidden', true);
	}
	
	// 验证活动描述是否为空
	if (description == '' || description == null || description == undefined) {
		$('#description-tip').html('活动描述不允许为空');
		$('#description-tip').removeAttr('hidden');
		canSubmit = false;
	} else {
		$('#description-tip').attr('hidden', true);
	}
	
	// 如果任意一个输入不合法 则退出方法 不执行提交操作
	if (canSubmit == false) {
		return;
	}
	
	// TODO -- 执行提交
}

/**
 * @param {Object} msg
 * @author DengJie
 * @date 2021-03-04
 * @description 展示标题输入框提示
 */
function showTitleInputTip(msg) {
	$('#title-tip').html(msg);
	$('#title-tip').removeAttr('hidden');
}

/**
 * @author DengJie
 * @date 2021-03-04
 * @description 隐藏标题输入框提示
 */
function hideTitleInputTip() {
	$('#title-tip').attr('hidden', true);
}

/**
 * @param {Object} msg
 * @author DengJie
 * @date 2021-03-04
 * @description 展示地址输入框提示信息
 */
function showAddressInputTip(msg) {
	$('#address-tip').html(msg);
	$('#address-tip').removeAttr('hidden');
}

/**
 * @author DengJie
 * @date 2021-03-04
 * @description 隐藏地址输入框提示信息
 */
function hideAddressInputTip() {
	$('#address-tip').attr('hidden', true);
}

/**
 * @param {Object} msg
 * @date 2021-03-04
 * @description 展示管理员联系方式输入框提示信息
 */
function showManagerIdInputTip(msg) {
	$('#managerId-tip').html(msg);
	$('#managerId-tip').removeAttr('hidden');
}

/**
 * @author DengJie
 * @date 2021-03-04
 * @description 隐藏管理员联系方式输入框提示信息
 */
function hideManagerIdInputTip() {
	$('#managerId-tip').attr('hidden', true);
}