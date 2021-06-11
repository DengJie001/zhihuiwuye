const baseUrl = 'http://codemata.club:8080/bysj/';

function submitHouseInfo() {
	var canSubmit = true;
	var areaId = $('#areaIdInput').val();
	var unitId = $('#unitIdInput').val();
	var roomId = $('#roomIdInput').val();
	var tips = '';
	
	if (areaId == '') {
		$('#areaIdInput').css('borderColor', 'red');
		canSubmit = false;
		tips += '区域号不允许为空<br />';
	} else {
		$('#areaIdInput').css('borderColor', '');
	}
	
	if (unitId == '') {
		$('#unitIdInput').css('borderColor', 'red');
		canSubmit = false;
		tips += '单元号不允许为空<br />';
	} else {
		$('#unitIdInput').css('borderColor', '');
	}
	
	if (roomId == '') {
		$('#roomIdInput').css('borderColor', 'red');
		canSubmit = false;
		tips += '门牌号不允许为空<br />';
	} else {
		$('#roomIdInput').css('borderColor', '');
	}
	
	// 如果有任意一个输入不合法 则退出方法 不执行提交
	if (!canSubmit) {
		layui.layer.open({
			title: '提示',
			content: tips
		});
		return;
	}
	
	$.ajax({
		url: baseUrl + 'HouseInfo/addHouseInfo.do',
		data: {
			userId: localStorage.getItem("userId"),
			areaId: areaId,
			unitId: unitId,
			roomId: roomId
		},
		type: 'post',
		dataType: 'json',
		success: function (res) {
			if (res.status == 'success') {
				layer.msg('新增成功', {time: 3000, icon: 1});
			} else {
				layer.msg('服务端异常', {time: 3000, icon: 0});
			}
		},
		error: function (res) {
			console.log(res);
			layer.msg('预期之外的错误', {time: 3000, icon: 2});
		}
	});
}