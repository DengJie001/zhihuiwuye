const baseUrl = 'http://codemata.club:8080/bysj/';

layui.use(['form', 'layer', 'table'], function () {
	var form = layui.form;
	var table = layui.table;
	var layer = layui.layer;
	var $ = layui.jquery;
	var layerIndex;
	
	/**
	 * @author DengJie
	 * @date 2021-04-06
	 * @description 载入活动场地信息
	 */
	table.render({
		elem: '#placeInfos',
		title: '活动场地信息表',
		url: baseUrl + 'PlaceInfo/getPlaceInfos.do',
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#headerToolList',
		defaultToolBar: ['filter', 'exports', 'print'],
		initSort: {
			field: 'placeId',
			type: 'asc'
		},	// 默认按照场地编号升序排列
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'placeId', title: '场地编号', align: 'center', width: 80},
			{field: 'managerName', title: '管理员', align: 'center', width: 80},
			{field: 'managerTel', title: '联系方式', align: 'center'},
			{field: 'placeArea', title: '场地面积', align: 'center', sort: true},
			{field: 'placePrice', title: '租用价格', align: 'center', sort: true},
			{field: 'placeDescription', title: '场地介绍', align: 'center'},
			{field: 'placeStatus', title: '使用状态', align: 'center'},
			{title: '操作', toolbar: '#cellToolList', align: 'center'}
		]],	// 表头
		parseData: function (res) {
			return {
				"code": res.code,
				"count": res.count,
				"msg": res.msg,
				"data": res.placeInfos
			}
		},
		request: {
			pageName: 'page',
			limitName: 'limit'
		},
		where: {
			userId: localStorage.getItem("userId")
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-07
	 * @description 监听下拉框选择
	 */
	form.on('select(placePropertySelector)', function (selector) {
		if (selector.value == '价格') {
			$('#lowerValue').attr('placeholder', '价格最小值');
			$('#higherValue').attr('placeholder', '价格最大值');
			layerIndex = layer.open({
				type: 1,
				title: '范围查询--价格',
				area: ['320px'],
				content: $('#range')
			});
		} else if (selector.value == '面积') {
			$('#lowerValue').attr('placeholder', '面积最小值');
			$('#higherValue').attr('placeholder', '面积最大值');
			layerIndex = layer.open({
				type: 1,
				title: '范围查询--面积',
				area: ['320px'],
				content: $('#range')
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-07
	 * @description 监听头部工具栏(导出选中记录,导出所有记录,刷新表格)
	 */
	table.on('toolbar(placeInfos)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch(obj.event) {
			// 导出选中记录到excel
			case 'exportSelectedPlaceInfos':
				if (checkStatus.data.length == 0) {
					layer.msg('当前未选中记录,无法执行导出操作', {time: 3000, icon: 2});
				} else if (checkStatus.data.length > 0) {
					table.exportFile(obj.config.id, checkStatus.data, 'xls');
					layer.msg('成功导出' + checkStatus.data.length + '条记录', {time: 1, icon: 2});
				}
				break;
			// 导出所有记录到excel
			case 'exportAllPlaceInfos':
				$.ajax({
					url: baseUrl + 'PlaceInfo/exportAllPlaceInfos.do',
					data: {
						userId: localStorage.getItem("userId")
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						console.log(res);
						if (res.placeInfos.length > 0) {
							table.exportFile(obj.config.id, res.placeInfos, 'xls');
							layer.msg('成功导出' + res.placeInfos.length + '条记录', {time: 3000, icon: 1});
						} else if (res.placeInfos.length == 0) {
							layer.msg('暂无数据', {time: 3000, icon: 0});
						}
					},
					error: function (errorRes) {
						console.log(errorRes);
						layer.msg('发生了预期之外的错误', {time: 3000, icon: 2});
					}
				});
				break;
			// 刷新表格
			case 'refreshTable':
				table.reload('placeInfos', {
					url: baseUrl + 'PlaceInfo/getPlaceInfos.do',
					where: {
						property: null,
						value: null,
						userId: localStorage.getItem("userId")
					},
					page: {
						curr: 1
					}
				});
				break;
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-07
	 * @description 监听单元格事件(删除某行,编辑某行,查看场地信息信息不包括图片)
	 */
	table.on('tool(placeInfos)', function (row) {
		var currentSelectedPlaceInfo = row.data;
		var layEvent = row.event;
		// 判别事件
		if (layEvent === 'editPlaceInfo') {	// 编辑某一行
			// 将获取到的数据展示在表格中
			$('#placeId').attr('value', currentSelectedPlaceInfo.placeId);	// 场地编号
			$('#managerId').attr('value', currentSelectedPlaceInfo.managerId);	// 管理员ID
			$('#managerName').attr('value', currentSelectedPlaceInfo.managerName);	// 管理员姓名
			$('#managerTel').attr('value', currentSelectedPlaceInfo.managerTel);	// 管理员联系方式
			$('#placePrice').attr('value', currentSelectedPlaceInfo.placePrice);	// 租用价格
			$('#placeArea').attr('value', currentSelectedPlaceInfo.placeArea);	// 使用面积
			$('#placeDescription').val(currentSelectedPlaceInfo.placeDescription);	// 场地介绍
			// 设置场地信息的使用状态
			if (currentSelectedPlaceInfo.placeStatus == '使用中') {
				$('#using').attr('checked', 'checked');
			} else if (currentSelectedPlaceInfo.placeStatus == '未使用') {
				$('#unused').attr('checked', 'checked');
			} else if (currentSelectedPlaceInfo.placeStatus == '维护中') {
				$('#repairing').attr('checked', 'checked');
			} else if (currentSelectedPlaceInfo.placeStatus == '审核中') {
				$('#judging').attr('checked', 'checked');
			}
			form.render();	// 使layui表格元素生效
			// 打开编辑面板
			layerIndex = layer.open({
				type: 1,
				title: '修改场地信息:' + currentSelectedPlaceInfo.placeId + '号场地',
				area: ['520px', '620px'],
				content: $('#editPlaceInfoLayer'),
				cancel: function () {
					// 消除动态提示时加上的红色边框
					$('#managerId').css('borderColor', '');
					$('#placePrice').css('borderColor', '');
					$('#placeArea').css('borderColor', '');
					$('#placeDescription').css('borderColor', '');
					// 复原表格内容
					$('#modifyPlaceInfoForm')[0].reset();
					form.render();
				}
			});
		} else if (layEvent === 'previewPlaceInfo') {	// 查看当前行的场地信息
			// TODO 考虑是否需要查看当前行的详细信息 因为在编辑功能中 可以查看到详细信息
		} else if (layEvent === 'deletePlaceInfo') {
			layer.confirm('确认删除该行?此操作不可逆!', function (index) {
				// 删除该行的DOM结构
				row.del();
				layer.close(index);
				// 向服务端发起删除该行场地信息的请求
				$.ajax({
					url: baseUrl + 'PlaceInfo/removePlaceInfo.do',
					data: {
						placeId: currentSelectedPlaceInfo.placeId,
						userId: localStorage.getItem("userId")
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						if (res.msgId > 0) {	// msgId大于0 成功删除了msgId条记录
							layer.msg(res.msgContent, {time: 3000, icon: 1});
						} else if (res.msgId == 0) {	// msgId等于0 仅仅使请求成功了 但是没有删除场地信息 并没有对数据造成实质性影响
							layer.msg(res.msgContent, {time: 3000, icon: 2});
						}
					},
					error: function (errorRes) {
						layer.msg('删除时发生了异常', {time: 3000, icon: 2});
					}
				})
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-07
	 * @description 监听搜索按钮点击 进行场地信息查询
	 */
	$(document).on('click', '#search', function () {
		var canSubmit = true;
		var property = $('#keys').val();	// 用户选择的属性
		var value = $('#keyWords').val();	// 属性的值
		console.log(value);
		console.log(canSubmit);
		// 验证数据不为空
		if (value == '') {
			canSubmit = false;
			$('#keyWords').attr('style', 'border-color:red;')
		} else {
			$('#keyWords').attr('style');
		}
		
		// 如果canSubmit为false 表示value为空 则不进行查询
		if (!canSubmit) {
			return;
		}
		
		// 提交查询
		table.reload('placeInfos', {
			url: baseUrl + 'PlaceInfo/getPlaceInfos.do',
			where: {
				property: property,
				value: value,
				userId: localStorage.getItem("userId")
			},
			page: {
				curr: 1
			}
		});
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-08
	 * @description 监听区间搜索 按照价格区间或者面积区间查询场地信息
	 */
	$(document).on('click', '#rangeSearch', function () {
		var canSubmit = true;	// 能否进行请求的标志
		var property = $('#keys').val();
		var lowerValue = $('#lowerValue').val();
		var higherValue = $('#higherValue').val();
		
		// 验证数据合法性
		// 验证最小值不为空
		if (lowerValue == '') {	
			$('#lowerValue').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#lowerValue').removeAttr('style');
		}
		
		// 验证最大值不为空
		if (higherValue == '') {
			$('#higherValue').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#higherValue').removeAttr('style');
		}
		
		// 如果canSubmit为false 表明有非法字符 则阻止请求
		if (!canSubmit) {
			return;
		}
		
		// 提交请求 按照价格区间/面积区间查询场地信息 并且重载表格
		table.reload('placeInfos', {
			url: baseUrl + 'PlaceInfo/getPlaceInfosByRange.do',
			where: {
				property: property,
				lowerValue: lowerValue,
				higherValue: higherValue,
				userId: localStorage.getItem("userId")
			},
			page: {
				curr: 1
			}
		});
		layer.close(layerIndex);
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-08
	 * @description 监听用户点击最大最小值面板中的取消按钮--关闭该面板
	 */
	$(document).on('click', '#closeRangeLayer', function () {
		layer.close(layerIndex);
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-14
	 * @description 提交修改后的场地信息
	 */
	$(document).on('click', '#modifyPlaceInfo', function () {
		var canSubmit = true;
		var tips = '';
		var placeInfo = {
			placeId: $('#placeId').val(),
			managerId: $('#managerId').val(),
			placePrice: $('#placePrice').val(),
			placeArea: $('#placeArea').val(),
			placeDescription: $('#placeDescription').val(),
			placeStatus: $("input[name='placeStatus']:checked").val()
		};
		// 对个字段进行格式验证 如果有不符合要求的字段 给出提示 并且阻止提交
		// 验证管理员ID字段
		if (placeInfo.managerId.length > 11 || placeInfo.managerId.length == 0) {
			canSubmit = false;
			tips += '管理员ID不允许超过11位且不能位空;<br>';
			$('#managerId').css('borderColor', 'red');
		} else {
			$('#managerId').css('borderColor', '');
		}
		
		// 验证场地价格字段
		if (placeInfo.placePrice == '') {
			canSubmit = false;
			tips += '场地使用价格不允许为空;<br>';
			$('#placePrice').css('borderColor', 'red');
		} else {
			$('#placePrice').css('borderColor', '');
		}
		
		// 验证场地面积字段
		if (placeInfo.placeArea == '') {
			canSubmit = false;
			tips += '使用面积不允许为空;<br>';
			$('#placeArea').css('borderColor', 'red');
		} else {
			$('#placeArea').css('borderColor', '');
		}
		
		// 验证场地介绍字段
		if (placeInfo.placeDescription == '') {
			canSubmit = false;
			tips += '场地介绍不允许为空;<br>';
			$('#placeDescription').css('borderColor', 'red');
		} else {
			$('#placeDescription').css('borderColor', '');
		}
		
		// 验证canSubmit
		if (!canSubmit) {
			layer.open({
				title: '提示',
				content: tips
			});
			return;
		}
		
		// 提交
		$.ajax({
			url: baseUrl + 'PlaceInfo/updatePlaceInfo.do',
			data: {
				placeInfo: JSON.stringify(placeInfo),
				userId: localStorage.getItem("userId")
			},
			type: 'post',
			dataType: 'json',
			success: function (res) {
				console.log(res);
				if (res.msgId > 0) {
					layer.close(layerIndex);
					layer.msg(res.msgContent, {time: 2000, icon: 1});
					table.reload('placeInfos', {
						url: baseUrl + 'PlaceInfo/getPlaceInfos.do',
						where: {
							userId: localStorage.getItem("userId")
						},
						page: {
							curr: 1
						}
					});
				} else {
					layer.close(layerIndex);
					layer.msg(res.msgContent, {time: 2000, icon: 2});
				}
			},
			error: function (errorRes) {
				layer.msg('预期之外的错误', {time: 3000, icon: 2});
			}
		});
	});
});