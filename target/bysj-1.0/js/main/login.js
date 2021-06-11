const baseUrl = 'http://codemata.club:8080/bysj/';

/**
 * @author DengJie
 * @Date 2021-03-04
 * @description 管理员后台登录
 */
function managerLogin() {
	var username = $('#username').val();
	var password = $('#password').val();
	if (username == '' || password == '') {
		showLoginTip('密码或账号不允许为空');
		return;
	} else {
		hideLoginTip();
	}
	$.ajax({
		url: baseUrl + 'login/managerLogin.do',
		data: {
			id: username,
			password: password,
			userId: localStorage.getItem("userId")
		},
		type: 'post',
		dataType: 'json',
		success: function (res) {
			if (res.msgId == 1) {
				window.location.href = baseUrl + 'index.html';
				localStorage.setItem('userId', username);
			} else {
				layui.layer.msg('密码错误,登录失败!', {time: 3000, icon: 2});
			}
		},
		error: function (res) {
			layui.layer.msg('预期之外的错误!', {time: 3000, icon: 2});
		}
	});
}

/**
 * @author DengJie
 * @date 2021-03-04
 * @description 展示密码或用户名不能为空的提示
 */
function showLoginTip(msg) {
	$('#login_tip').html(msg);
	$('#login_tip').removeAttr('hidden');
}

/**
 * @author DengJie
 * @date 2021-03-04
 * @description 隐藏密码或用户名不能为空的提示
 */
function hideLoginTip() {
	$('#login_tip').attr('hidden', true);
}