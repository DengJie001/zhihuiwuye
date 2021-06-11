const baseUrl = 'http://codemata.club:8080/bysj/';

function checkPasswordForm(input) {
	var password = '';
	var surePassword = '';
	password = $('#newPasswordInput').val();
	surePassword = $('#surePasswordInput').val();
	// 判定密码格式是否正确 格式不正确就给出相应提示
	if (password.length > 16 || password.length < 8) {
		$('#newPassword').removeAttr('hidden');
		banSubmitButton();
	} else if (!regExp(password)) {
		$('#newPassword').removeAttr('hidden');
		banSubmitButton();
	} else {
		$('#newPassword').attr('hidden', true);
		allowSubmitButton();
	}
	
	// 判定新密码和确认密码两个输入是否相同
	if (password == surePassword) {
		$('#surePassword').attr('hidden', true);
		allowSubmitButton();
	} else{
		$('#surePassword').removeAttr('hidden');
		banSubmitButton();
	}
}

/**
 * @param {Object} str
 * @author Dengjie
 * @date 2021/2/15 21:27
 * @description 模拟正则表达式 验证密码字符是否合法
 */
function regExp(str) {
	for (let i = 0; i < str.length; ++i) {
		if (str[i] != '_' && str[i] != '.' && !isNumber(str[i]) && !isAlpabet(str[i])) {
			return false;
		}
	}
	return true;
}

/**
 * @param {Object} ch
 * @author Dengjie
 * @date 2021/2/15 21:21
 * @description 验证是否为数字字符
 */
function isNumber(ch) {
	if (ch >= '0' && ch <= '9') {
		return true;
	} else {
		return false;
	}
}

/**
 * @param {Object} ch
 * @author Dengjie
 * @date 2021/2/15 21:19
 * @description 验证字符是否为大写或者小写字母
 */
function isAlpabet(ch) {
	// 验证是否是小写字母
	if (ch >= 'a' && ch <= 'z') {
		return true;
	}
	// 验证是否为大写字母
	if (ch >= 'A' && ch <= 'Z') {
		return true;
	}
	// 非字母字符返回false
	return false;
}

/**
 * @author Dengjie
 * @date 2021/2/15 22:40
 * @description 禁用提交按钮
 */
function banSubmitButton() {
	$('#submitButton').addClass('layui-btn-disabled');
	$('#submitButton').attr('disabled', 'disabled');
}

/**
 * @author Dengjie
 * @date 2021/2/15 22:40
 * @description 取消提交按钮禁用
 */
function allowSubmitButton() {
	$('#submitButton').removeClass('layui-btn-disabled');
	$('#submitButton').removeAttr('disabled');
}

/**
 * @author Dengjie
 * @date 2021/2/16 10:30
 * @description 提交密码修改
 */
function doSubmitForm() {
	var password = $('#surePasswordInput').val();
	$.ajax({
		url: baseUrl + 'manager/modifyManagerPassword.do',
		data: {
			managerId: localStorage.getItem("userId"),
			password:password,
			userId: localStorage.getItem("userId")
		},
		type: 'post',
		dataType:'json',
		success: function (res) {
			if (res.status == 'success') {
				alert('修改成功');
			} else {
				alert('修改失败');
			}
		},
		error: function (res) {
			alter('预期之外的错误');
		}
	});
}