const baseUrl = 'http://codemata.club:8080/bysj/';

function addUtilityBill() {
	var managerId = localStorage.getItem("userId");
	var billFigure = $('#billFigureInput').val();
	var areaId = $('#areaIdInput').val();
	var unitId = $('#unitIdInput').val();
	var roomId = $('#roomIdInput').val();
	var billCategory = $("input[name='billCategory']:checked").val();
	var tips = '';
	var canSubmit = true;
	
	// 验证数据合法性
	// 账单金额不小等于0
	if (billFigure <= 0) {
		tips += '金额不允许小于等于0<br>';
		canSubmit = false;
		$('#billFigureInput').css('borderColor', 'red');
	} else {
		$('#billFigureInput').css('borderColor', '');
	}
	
	// 区号不为空
	if (areaId == '') {
		tips += '区号不能为空<br>';
		canSubmit = false;
		$('#areaIdInput').css('borderColor', 'red');
	} else {
		$('#areaIdInput').css('borderColor', '');
	}
	
	// 单元号不为空
	if (unitId == '') {
		tips += '单元号不为空<br>';
		canSubmit = false;
		$('#unitIdInput').css('borderColor', 'red');
	} else {
		$('#unitIdInput').css('borderColor', '');
	}
	
	// 房间号不为空
	if (roomId == '') {
		tips += '房间号不为空<br>';
		canSubmit = false;
		$('#roomIdInput').css('borderColor', 'red');
	} else {
		$('#roomIdInput').css('borderColor', '');
	}
	
	// 含有格式不正确字段 打断提交
	if (!canSubmit) {
		layer.open({
			title: '提示',
			content: tips
		});
		return;
	}
	
	// 提交
	$.ajax({
		url: baseUrl + 'bill/addBill.do',
		data: {
			managerId: managerId,
			billFigure: billFigure,
			areaId: areaId,
			unitId: unitId,
			roomId: roomId,
			billCategory: billCategory,
			userId: localStorage.getItem('userId')
		},
		type: 'post',
		dataType: 'json',
		success: function (res) {
			console.log(res);
			if (res.status == 'success') {
				layer.msg('新增成功', {time: 3000, icon: 1});
				$('#billInfoForm')[0].reset();
			} else if (res.status == 'error') {
				layer.msg('新增失败', {time: 3000, icon: 2});
			}
		},
		error: function (res) {
			layer.msg('预期之外的错误', {time: 3000, icon: 0});
		}
	});
}