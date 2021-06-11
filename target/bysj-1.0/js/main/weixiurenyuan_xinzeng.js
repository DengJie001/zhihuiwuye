const baseUrl = 'http://codemata.club:8080/bysj/';
var avatarFile = null;
var avatarPath = null;

/**
 * @author DengJie
 * @param {Object} file
 * @date 2021-04-15
 * @description  选择员工头像并且小图预览
 */
function selectAvatar(file) {
	avatarFile = $(file)[0].files[0];
	filePath = avatarFile.name;
	fileFormat = filePath.split('.')[1].toLowerCase();
	src = window.URL.createObjectURL(avatarFile);
	if (!fileFormat.match(/png|jpg|jpeg/)) {
		layui.layer.msg('图片格式必须为:png/jpg/jpeg', {time: 3000, icon: 2});
	}
	var img = document.createElement('img');
	img.length = 100;
	img.width = 100;
	img.src = src;
	$('#pictureContainer').html('');
	$('#pictureContainer').append(img);
}

/**
 * @author DengJie
 * @date 2021-04-15
 * @description 上传员工头像
 */
function doSubmitPicture() {
	var formData = new FormData();
	formData.append('avatar', avatarFile);
	formData.append('userId', localStorage.getItem("userId"));
	$.ajax({
		url: baseUrl + 'worker/uploadWorkerAvatar.do',
		data: formData,
		type: 'post',
		cache: false,
		processData: false,
		contentType: false,
		async: false,
		dataType: 'json',
		success: function (res) {
			console.log(res);
			if (res.msgId > 0) {
				avatarPath = res.msgContent;
				console.log("1:" + avatarPath);
			} else {
				layui.layer.msg('上传图片失败', {time: 3000, icon: 2});
			}
		},
		error: function (errorRes) {
			layui.layer.msg('预期之外的错误', {time: 3000, icon: 2});
		}
	});
}


/**
 * @author DengJie
 * @date 2021-04-15
 * @description 提交物业维修员的基本信息
 */
function submitWorkerInfo() {
	var that = this;
	// 在提交其他信息之前先要提交员工的头像到服务器
	doSubmitPicture();
	var canSubmit = true;
	var tips = '';
	var workerTel = $('#workerTel').val();
	var workerName = $('#workerName').val();
	var workerDescription = $('#workerDescription').val();
	var gender = $("input[name='gender']:checked").val();
	var cost = $('#cost').val();
	// 验证数据合法性
	// 员工电话号码不允许超过11位
	if (workerTel.length > 11) {
		tips += '员工电话不允许超过11位;<br>';
		canSubmit = false;
		$('#workerTel').css('borderColor', 'red');
	} else {
		$('#workerTel').css('borderColor');
	}
	
	// 员工电话号码不允许为空
	if (workerTel == '') {
		tips += '员工电话不允许为空;<br>';
		canSubmit = false;
		$('#workerTel').css('borderColor', 'red');
	} else {
		$('#workerTel').css('borderColor', '');
	}
	
	// 验证员工姓名不允许为空
	if (workerName == '') {
		tips += '员工姓名不允许为空;<br>';
		canSubmit = false;
		$('#workerName').css('borderColor', 'red');
	} else {
		$('#workerName').css('borderColor', '');
	}
	
	// 验证员工姓名不允许超过32位
	if (workerName.length > 32) {
		tips += '员工姓名不允许超过32位;<be>';
		canSubmit = false;
		$('#workerName').css('borderColor', 'red');
	} else {
		$('#workerName').css('borderColor', '');
	}
	
	// 验证员工介绍不允许为空
	if (workerDescription == '') {
		tips += '员工介绍不允许为空,至少填入该员工的擅长业务!<br>';
		canSubmit = false;
		$('#workerDescription').css('borderColor', 'red');
	} else {
		$('#workerDescription').css('borderColor', '');
	}
	
	// 验证员工头像不允许为空
	if (that.avatarPath == '' || that.avatarPath == null) {
		tips += '员工头像不允许不选择;<br>';
		canSubmit = false;
	}
	
	// 验证维修价格不为空
	if (cost == '') {
		tips += '价格不允许为空;<br>';
		canSubmit = false;
		$('#cost').css('borderColor', 'red');
	} else {
		$('cost').css('borderColor', '');
	}
	
	// 如果canSubmit是false 那么就是有非法字段 阻止提交信息
	if (!canSubmit) {
		return;
	}
	// 提交信息
	$.ajax({
		url: baseUrl + 'worker/addWorker.do',
		data: {
			workerTel: workerTel,
			workerName: workerName,
			workerDescription: workerDescription,
			workerAvatar: that.avatarPath,
			gender: gender,
			cost: cost,
			userId: localStorage.getItem("userId")
		},
		type: 'post',
		dataType: 'json',
		success: function (res) {
			if (res.msgId > 0) {
				layui.layer.msg(res.msgContent + '3秒后自动刷新……', {time: 3000, icon: 1});
				setTimeout(function () {
					location.reload();
				}, 3000);
			} else {
				layui.layer.msg('发生了异常', {time: 3000, icon: 0});
			}
		},
		errro: function (errorRes) {
			layui.layer.msg('预期之外的cuowu', {time: 3000, icon: 2});
		}
	})
}