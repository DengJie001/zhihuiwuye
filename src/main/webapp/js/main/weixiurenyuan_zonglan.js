const baseUrl = 'http://codemata.club:8080/bysj/';

layui.use(['form', 'table', 'layer', 'laytpl'], function () {
	var table = layui.table;
	var form = layui.form;
	var layer = layui.layer;
	var laytpl = layui.laytpl;
	var $ = layui.jquery;
	var layerIndex;
	
	/**
	 * @author DengJie
	 * @date 2021-04-15
	 * @description 初始化表格
	 */
	table.render({
		elem: '#workerInfos',
		title: '物业维修人员总览表',
		url: baseUrl + 'worker/getAllWorkers.do',
		method: 'post',
		page: 'true',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#headerToolList',
		defaultToolBar: ['filter', 'exports', 'print'],
		initSort: {
			field: 'score',
			type: 'desc'
		},	// 默认按照评分降序排列
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'workerId', title: '人员编号', align: 'center'},
			{field: 'workerName', title: '姓名', align: 'center'},
			{field: 'gender', title: '性别', align: 'center', templet: '#workerGenderTpl'},
			{field: 'cost', title: '价格', align: 'center', sort: true},
			{field: 'workerTel', title: '联系方式', align: 'center'},
			{field: 'times', title: '服务次数', align: 'center', sort: true},
			{field: 'score', title: '评分', align: 'center', sort: true},
			{field: 'wait', title: '排队人数', align: 'center', sort: true},
			{field: 'workerDescription', title: '介绍', align: 'center'},
			{title: '操作', toolbar: '#cellToolList', align: 'center'}
		]],	// 表头
		parseData: function (res) {
			console.log(res);
			return {
				"code": res.code,
				"count": res.count,
				"msg": res.msg,
				"data": res.workers
			}
		},
		request: {
			pageName: 'page',
			limitName: 'limit',
		},
		where: {
			userId: localStorage.getItem("userId")
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-15
	 * @description 监听下拉框下拉
	 */
	form.on('select(workerPropertySelector)', function (selector) {
		var tips;
		if (selector.value == '评分' || selector.value == '排队人数' || selector.value == '服务次数') {
			layer.open({
				title: '搜索-提示',
				content: '搜索结果为小于您输入值的维修人员信息!'
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-15
	 * @description 监听头部工具栏事件(导出选中记录,导出所有记录,刷新表格)
	 */
	table.on('toolbar(workerInfos)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch(obj.event) {
			// 导出选中记录到Excel
			case 'exportSelectedWorkerInfos':
				if (checkStatus.data.length == 0) {
					layer.msg('当前未选中记录,无法执行导出操作', {time: 3000, icon: 0});
				} else if (checkStatus.data.length > 0) {
					table.exportFile(obj.config.id, checkStatus.data, 'xls');
					layer.msg('成功导出' + checkStatus.data.length + '条记录', {time: 3000, icon: 1});
				}
				break;
			case 'exportAllWorkerInfos':
				$.ajax({
					url: baseUrl + 'worker/exportAllWorkerInfos.do',
					data: {
						userId: localStorage.getItem("userId")
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						if (res.workers.length > 0) {
							table.exportFile(obj.config.id, res.workers, 'xls');
							layer.msg('成功导出' + res.count + '条记录', {time: 3000, icon: 1});
						} else {
							layer.msg(res.msg, {time: 3000, icon: 0});
						}
					},
					error: function (errorRes) {
						layer.msg('预期之外的错误', {time: 3000, icon: 2});
					}
				});
				break;
			case 'refreshTable':
				table.reload('workerInfos', {
					url: baseUrl + 'worker/getAllWorkers.do',
					where: {
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
	 * @date 2021-04-16
	 * @description 按照不同属性对维修人员信息进行搜索
	 */
	$(document).on('click', '#search', function () {
		var property = $('#keys').val();
		var value = $('#keyWords').val();
		// 发出请求 按照用户要求查询维修人员的信息
		// 并且重载表格
		table.reload('workerInfos', {
			url: baseUrl + 'worker/search.do',
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
	 * @date 2021-04-16
	 * @description 监听单元格工具事件(编辑维修人员信息,删除维修人员信息)
	 * TODO 考虑是否需要添加禁止接单功能
	 */
	table.on('tool(workerInfos)', function (row) {
		var currentSelectedWorker = row.data;
		var layEvent = row.event;
		// 判别事件 进行处理
		if (layEvent === 'modifyWorkerInfo') {	// 编辑维修人员信息
			$('#workerId').val(currentSelectedWorker.workerId);
			$('#workerTel').val(currentSelectedWorker.workerTel);
			$('#workerName').val(currentSelectedWorker.workerName);
			$('#workerDescription').val(currentSelectedWorker.workerDescription);
			$('#workerAvatar').attr('src', currentSelectedWorker.workerAvatar);
			$('#times').val(currentSelectedWorker.times);
			$('#wait').val(currentSelectedWorker.wait);
			$('#score').val(currentSelectedWorker.score);
			$('#cost').val(currentSelectedWorker.cost);
			console.log(currentSelectedWorker.gender);
			if (currentSelectedWorker.gender == '男') {
				$('#male').attr('checked', 'checked');
			} else {
				$('#female').attr('checked', 'checked');
			}
			form.render();	// 使表格元素生效
			// 打开编辑面板
			layerIndex = layer.open({
				type: 1,
				title: '修改维修人员信息:' + currentSelectedWorker.workerName,
				area: ['520px', '620px'],
				content: $('#modifyWorkerInfoForm'),
				cancel: function () {
					// 清除红色边框样式
					$('#workerName').css('borderColor', '');
					$('#workerTel').css('borderColor', '');
					$('#workerDescription').css('borderColor', '');
					$('#cost').css('borderColor', '');
					$('#male').removeAttr('checked');
					$('#female').removeAttr('checked');
					// 复原表格内容
					$('#m_workerForm')[0].reset();
					form.render();
				}
			});
		} else if (layEvent === 'deleteWorkerInfo') {
			// 删除该行的DOM结构
			row.del();
			// 向服务端发起删除该行的请求
			$.ajax({
				url: baseUrl + 'worker/deleteWorker.do',
				data: {
					workerId: currentSelectedWorker.workerId,
					userId: localStorage.getItem("userId")
				},
				type: 'post',
				dataType: 'json',
				success: function (res) {
					if (res.msgId > 0) {
						layer.msg(res.msgContent, {time: 3000, icon: 1});
						// 重载表格
						table.reload('workerInfos', {
							url: baseUrl + 'worker/getAllWorkers.do',
							where: {
								userId: localStorage.getItem("userId")
							},
							page: {
								curr: 1
							}
						});
					} else if (res.msgId == 0) {
						layer.msg(msgContent, {time: 3000, icon: 0});
					} else {
						layer.msg(msgContent, {time: 3000, icon: 2});
					}
				},
				error: function (errorRes) {
					layer.msg('预期之外的错误!', {time: 3000, icon: 2});
				}
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-16
	 * @description 提交修改后的维修人员信息
	 */
	$(document).on('click', '#modifyWorker', function () {
		var canSubmit = true;
		var tips = '';
		var workerId = $('#workerId').val();
		var workerName = $('#workerName').val();
		var workerTel = $('#workerTel').val();
		var workerDescription = $('#workerDescription').val();
		var cost = $('#cost').val();
		var gender = $("input[name='gender']:checked").val();
		
		// 验证修改后的字段合法性
		// 验证联系方式字段
		if (workerTel.length > 11 || workerTel == '') {
			tips += '联系方式不允许为空;<br>联系方式不允许超过11位;<br>';
			canSubmit = false;
			$('#workerTel').css('borderColor', 'red');
		} else {
			$('#workerTel').css('borderColor', '');
		}
		
		// 验证姓名字段
		if (workerName.length > 32 || workerName == '') {
			tips += '姓名不允许为空;<br>姓名不允许超过32位;<br>';
			canSubmit = false;
			$('#workerName').css('borderColor', 'red');
		} else {
			$('#workerName').css('borderColor', '');
		}
		
		// 验证介绍字段
		if (workerDescription == '') {
			tips += '简要介绍不允许为空;<br>';
			canSubmit = false;
			$('#workerDescription').css('borderColor', 'red');
		} else {
			$('#worerDescription').css('borderColor', '');
		}
		
		// 验证价格字段
		if (cost == '') {
			tips += '价格不允许为空;<br>'
			canSubmit = false;
			$('#cost').css('borderColor', 'red');
		} else {
			$('#cost').css('borderColor', '');
		}
		
		// 如果cabSubmit为false则表示有格式错误的字段 不允许提交
		if (!canSubmit) {
			layer.open({
				title: '出错了!',
				content: tips
			});
			return;
		}
		
		// 提交信息
		$.ajax({
			url: baseUrl + 'worker/modifyWorkerInfo.do',
			data: {
				workerId: workerId,
				workerTel: workerTel,
				workerName: workerName,
				workerDescription: workerDescription,
				gender: gender,
				cost: cost,
				userId: localStorage.getItem("userId")
			},
			type: 'post',
			dataType: 'json',
			success: function (res) {
				if (res.msgId > 0) {
					layer.msg(res.msgContent, {time: 3000, icon: 1});
					table.reload('workerInfos', {
						url: baseUrl + 'worker/getAllWorkers.do',
						where: {
							userId: localStorage.getItem("userId")
						},
						page: {
							curr: 1
						}
					});
					layer.close(layerIndex);
				} else if (res.msgId == 0) {
					layer.msg(res.msgContent, {time: 3000, icon: 0});
					layer.close(layerIndex);
				} else {
					layer.msg(res.msgContent, {time: 3000, icon: 2});
				}
			},
			error: function (errorRes) {
				layer.msg('预期之外的错误', {time: 3000, icon: 2});
			}
		});
	});
});