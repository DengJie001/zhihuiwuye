const baseUrl = 'http://codemata.club:8080/bysj/';

function doSubmit() {
	var visitorName = $('#visitorName').val();
	var visitorTel = $('#visitorTel').val();
	var visitorTemperature = $('#visitorTemperature').val();
	var visitorHomeAddress = $('#visitorHomeAddress').val();
	var visitorWorkAddress = $('#visitorWorkAddress').val();
	var canSubmit = true;
	var tips = '';
	if (visitorName == '') {
		canSubmit = false;
		tips += '姓名不能为空<br />';
	} 
	
	if (visitorTel.length > 11) {
		canSubmit = false;
		tips += '电话号码不允许超过11位<br />';
	}
	
	if (visitorTel == '') {
		canSubmit = false;
		tips += '联系方式不能为空<br />';
	}
	
	if (visitorTemperature == '') {
		canSubmit = false;
		tips += '体温不能为空<br />';
	}
	
	if (visitorTemperature < 35) {
		canSubmit = false;
		tips += '体温数值不能小于35<br />';
	}
	
	if (visitorHomeAddress == '') {
		canSubmit = false;
		tips += '家庭住址不能为空<br />';
	}
	
	if (visitorWorkAddress == '') {
		canSubmit = false;
		tips += '工作地址不能为空<br />';
	}
	
	if (!canSubmit) {
		layui.layer.open({
			title: '提示',
			content: tips
		});
		return;
	}
	
	$.ajax({
		url: baseUrl + 'visitor/addVisitor.do',
		data: {
			visitorName: visitorName,
			visitorTel: visitorTel,
			visitorTemperature: visitorTemperature,
			visitorHomeAddress: visitorHomeAddress,
			visitorWorkAddress: visitorWorkAddress
		},
		type: 'post',
		dataType: 'json',
		success: function (res) {
			if (res.status == 'success') {
				layui.layer.msg('登记成功,欢迎来访!', { time: 3000, icon: 1});
			} else {
				layui.layer.msg('服务端异常!', { time: 3000, icon: 2});
			}
		},
		error: function (res) {
			layui.layer.msg('预期之外的错误', { time: 3000, icon: 0});
		}
	});
}