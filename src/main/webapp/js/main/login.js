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
	console.log('1111111');
	$.ajax({
		
	})
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