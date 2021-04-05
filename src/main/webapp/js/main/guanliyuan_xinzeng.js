var baseUrl = 'http://localhost:8080/bysj';
var avatarFile = null;
var avatarPath = null;	// 头像上传服务器后返回的可以被访问的网络路径
/**
 * @param {Object} file
 * @author DengJie
 * @date 2021-03-08
 * @description 选择图片并小图预览
 */
function selectAvatar(file) {
	avatarFile = $(file)[0].files[0];
	filePath = avatarFile.name;
	fileFormat = filePath.split('.')[1].toLowerCase();
	src = window.URL.createObjectURL(avatarFile);
	if (!fileFormat.match(/png|jpg|jpeg|gif/)) {
		alert('图片格式必须为:png/jpg/jpeg/gif');
	}
	var img = document.createElement('img');
	img.length = 100;
	img.width = 100;
	img.src = src;
	$('#pictureContainer').html('');
	$('#pictureContainer').append(img);
}

/**
 * @param {Object} avatar
 * @param {Object} tel
 * @param {Object} name
 * @param {Object} password
 * @author DengJie
 */
function doSubmitPicture() {
	var formData = new FormData();
	formData.append('avatar', avatarFile);
	$.ajax({
		url: baseUrl + '/manager/uploadManagerAvatar.do',
		data: formData,
		type: 'post',
		cache: false,
		processData: false,
		contentType: false,
		async: false,
		dataType: 'text',
		success: function (res) {
			console.log(1);
			avatarPath = res;
		},
		error: function (res) {
			layui.layer.msg('上传头像时发生了错误', {time: 3000, icon: 2});
		}
	});
}

/**
 * @param {Object} avatar
 * @param {Object} tel
 * @param {Object} name
 * @param {Object} password
 * @author DengJie
 * @date 2021-03-09
 * @description 将管理员信息上传至服务端
 */
function doSubmitForm(avatar, tel, name, password) {
	$.ajax({
		url: baseUrl + '/manager/addManagerInfo.do',
		type: 'post',
		dataType: 'json',
		data: {
			managerTel: tel,
			managerName: name,
			managerAvatar: avatar,
			password: password
		},
		success: function (res) {
			console.log(2);
			if (res.msgId > 0) {
				layui.layer.msg(res.msgContent, {time: 3000, icon: 1});
				resetForm();
			} else {
				layui.layer.msg(res.msgContent, {time: 3000, icon: 2})
			}
		},
		error: function (res) {
			console.log(res);
			layui.layer.msg('发生了预期之外的错误', {time: 3000, icon: 2});
		}
	})
}

/**
 * @author DengJie
 * @date 2021-03-08
 * @description 提交新增的管理员信息 并对各个字段进行格式验证 若存在不合法的字段 讲阻止表单提交
 */
function submitManagerInfo() {
	var canSubmit = true;	// 能否提交的标志
	var managerTel = $('#managerTelInput').val();
	var managerName = $('#managerNameInput').val();
	var password = $('#passwordInput').val();
	var managerAvatar = avatarFile;
	
	// 电话号码输入合法性验证
	if (managerTel == null || managerTel == '' || managerTel == undefined) {
		showManagerTelTip('电话号码不允许为空');
		canSubmit = false;
	} else if (managerTel.length > 11) {
		showManagerTelTip('电话号码不允许超过11位');
		canSubmit = false;
	} else {
		hideManagerTelTip();
	}
	
	// 验证姓名格式是否正确
	if (managerName == null || managerName == '' || managerName == undefined) {
		showManagerNameTip('姓名不允许为空');
		canSubmit = false;
	} else if (managerName.length > 32) {
		showManagerNameTip('姓名长度不允许超过32位');
		canSubmit = false;
	} else {
		hideManagerNameTip();
	}
	
	// 验证密码格式是否正确
	if (password.length < 8) {
		showPasswordTip('密码过短,至少8位');
		canSubmit = false;
	} else if (password.length > 16) {
		showPasswordTip('密码过长,不允许超过16位');
		canSubmit = false;
	} else if (!regExp(password)) {
		showPasswordTip('密码格式不正确,数字、字母、符号组合(下划线,点号)');
		canSubmit = false;
	} else {
		hidePasswordTip();
	}
	
	// 验证是否已经选择了头像
	if (avatarFile == null) {
		alert('请选择头像');
		canSubmit = false;
	}
	
	// 如果含有不正确的字段 阻止提交
	if (!canSubmit) {
		return;
	}
	
	// 执行提交
	doSubmitPicture();
	// 提交其他文本信息,以及头像的网络路径
	doSubmitForm(avatarPath, managerTel, managerName, password)
}

function resetForm() {
	$('#managerTelInput').attr('value', '');
	$('#managerNameInput').attr('value', '');
	$('#passwordInput').attr('value', '');
	avatarFile = null;
	$('#pictureContainer').html('');
}

/**
 * @param {Object} msg
 * @author DengJie
 * @date 2021-03-08
 * @description 显示电话号码格式错误提示
 */
function showManagerTelTip(msg) {
	$('#managerTelTip').html(msg);
	$('#managerTelTip').removeAttr('hidden');
}

/**
 * @author DengJie
 * @date 2021-03-08
 * @description 隐藏电话号码格式的提示信息
 */
function hideManagerTelTip() {
	$('#managerTelTip').attr('hidden', true);
}

/**
 * @param {Object} msg
 * @author DengJie
 * @date 2021-03-08
 * @description 显示管理员姓名格式的错误提示
 */
function showManagerNameTip(msg) {
	$('#managerNameTip').html(msg);
	$('#managerNameTip').removeAttr('hidden');
}

/**
 * @author DengJie
 * @date 2021-03-08
 * @description 隐藏管理员姓名格式的错误提示
 */
function hideManagerNameTip() {
	$('#managerNameTip').attr('hidden', true);
}

/**
 * @param {Object} msg
 * @author DengJie
 * @date 2021-03-08
 * @description 显示管理员密码格式的错误提示
 */
function showPasswordTip(msg) {
	$('#passwordTip').html(msg);
	$('#passwordTip').removeAttr('hidden');
}

/**
 * @author DengJie
 * @date 2021-03-08
 * @description 隐藏管理员密码格式的错误提示
 */
function hidePasswordTip() {
	$('#passwordTip').attr('hidden', true);
}

/**
 * @author DengJie
 * @date 2021-03-08
 * @description 点击小眼睛展示或隐藏密码明文
 */
function showOrHidePassword() {
	var type = $('#passwordInput').attr('type');
	if (type == 'password') {
		$('#passwordInput').attr('type', 'text');
	} else if (type == 'text') {
		$('#passwordInput').attr('type', 'password');
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