const baseUrl = 'http://codemata.club:8080/bysj/';

layui.use(['form', 'table', 'layer', 'laytpl'], function () {
	var table = layui.table;
	var form = layui.form;
	var layer = layui.layer;
	var laytpl = layui.laytpl;
	var $ = layui.jquery;
	var layerIndex;
	
	form.render();
	
	/**
	 * @author DengJie
	 * @date 2021-04-17
	 * @description 初始化表格信息
	 */
	table.render({
		elem: '#complaintInfos',
		title: '用户投诉建议总览',
		url: baseUrl + 'complaint/getAllComplaints.do',
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#headerToolList',
		defaultToolBar: ['filter', 'exports', 'print'],
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', align: 'center'},
			{field: 'complaintDate', title: '日期', align: 'center'},
			{field: 'messageContent', title: '详细内容', align: 'center'},
			{field: 'managerName', title: '处理人', align: 'center'},
			{field: 'managerResponse', title: '回复内容', align: 'center'},
			{field: 'result', title: '用户意见', align: 'center', templet: '#resultTpl'},
			{field: 'status', title: '处理状态', align: 'center', templet: '#statusTpl'},
			{field: 'category', title: '分类', align: 'center', templet: '#categoryTpl'},
			{title: '图片', toolbar: '#showPicture', align: 'center'},
			{title: '操作', toolbar: '#cellToolList', align: 'center', width: 80}
		]],	// 表头
		parseData: function (res) {
			return {
				"code": res.code,
				"count": res.count,
				"msg": res.msg,
				"data": res.complaints
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
	 * @date 2021-04-17
	 * @description 监听单元格事件(回复,点击显示处理人的详细信息,显示详细内容)
	 */
	table.on('tool(complaintInfos)', function (row) {
		var currentSelectedRow = row.data;
		var layEvent = row.event;
		// 判别事件
		if (layEvent === 'showReplyPanel') {
			if (currentSelectedRow.managerId != '') {
				layer.open({
					title: '提示',
					content: '已经处理过了!'
				});
				return;
			}
			$('#messageContent').val(currentSelectedRow.messageContent);
			$('#complaintId').val(currentSelectedRow.id);
			// 打开回复面板
			layerIndex = layer.open({
				type: 1,
				title: '回复用户' + currentSelectedRow.category,
				area: ['520px'],
				content: $('#replyContainer'),
				cancel: function() {
					$('#managerResponse').css('borderColor', '');
					// 复原表格内容
					$('#replyForm')[0].reset();
					form.render();
				}
			});
		} else if (layEvent === 'showPicture') {
			if (currentSelectedRow.complaintPicture == '') {
				layer.msg('该条信息没有图片', {time: 3000, icon: 2});
				return;
			}
			$.getJSON(baseUrl + 'complaint/getPictureJson.do?complaintId=' + currentSelectedRow.id + '&userId=' + localStorage.getItem("userId"), function (json) {
				console.log(json);
				layer.photos({
					photos: json,
					anim: 5
				});
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-17
	 * @description 回复用户
	 */
	$(document).on('click', '#reply', function () {
		var managerResponse = $('#managerResponse').val();
		var complaintId = $('#complaintId').val();
		var tips = '';
		var canSubmit = true;
		
		// 验证回复内容不允许为空
		if (managerResponse == '' || managerResponse == null) {
			tips += '回复内容不允许为空!!!'
			canSubmit = false;
			$('#managerResponse').css('borderColor', 'res');
		} else {
			$('#managerResponse').css('borderColor', '');
		}
		
		if (!canSubmit) {
			return;
			layer.open({
				title: '提示',
				content: tips
			});
		}
		
		// 提交管理员的回复内容
		$.ajax({
			url: baseUrl + 'complaint/managerReply.do',
			data: {
				managerResponse: managerResponse,
				managerId: localStorage.getItem("userId"),
				complaintId: complaintId,
				userId: localStorage.getItem("userId")
			},
			type: 'post',
			dataType: 'json',
			success: function (res) {
				if (res.msgId > 0) {
					// 关闭当前面板
					layer.close(layerIndex);
					// 展示提示信息
					layer.msg(res.msgContent, {time: 3000, icon: 1});
				} else {
					layer.msg(res.msgContent, {time: 3000, icon: 2});
				}
			},
			error: function (errorRes) {
				layer.msg('预期之外的错误', {time: 3000, icon: 2});
			}
		})
	});
	
	table.on('toolbar(complaintInfos)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch(obj.event) {
			// 导出选中记录到Excel
			case 'exportSelectedComplaints':
				if (checkStatus.data.length == 0) {
					layer.msg('当前未选中记录,无法执行导出操作!', {time: 3000, icon: 2});
				} else if (checkStatus.data.length > 0) {
					table.exportFile(obj.config.id, checkStatus.data, 'xls');
					layer.msg('成功导出' + checkStatus.data.length + '条记录!', {time: 3000, icon: 1});
				}
				break;
			// 导出所有记录
			case 'exportAllComplaints':
				$.ajax({
					url: baseUrl + 'complaint/exportAllComplaints.do',
					data: {
						userId: localStorage.getItem("userId")
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						if (res.count == 0) {
							layer.msg('当前没有数据,无法导出!', {time: 3000, icon: 0});
						} else if (res.count > 0) {
							table.exportFile(obj.config.id, res.complaints, 'xls');
							layer.msg(res.msg, {time: 3000, icon: 1});
						}
					},
					error: function (errorRes) {
						layer.msg('预期之外的错误', {time: 3000, icon: 2});
					}
				});
				break;
			// 仅显示未处理的消息
			case 'showUntreated': 
				table.reload('complaintInfos', {
					url: baseUrl + 'complaint/getUntreatedComplaints.do',
					where: {
						userId: localStorage.getItem("userId")
					},
					page: {
						curr: 1
					}
				});
				break;
			// 显示用户认为不满意的处理结果
			case 'showDissatisfaction':
				table.reload('complaintInfos', {
					url: baseUrl + 'complaint/getDissatisfactionComplaints.do',
					where: {
						userId: localStorage.getItem("userId")
					},
					page: {
						curr: 1
					}
				});
				break;
			// 刷新表格
			case 'refreshTable':
				table.reload('complaintInfos', {
					url: baseUrl + 'complaint/getAllComplaints.do',
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
	 * @date 2021-04-19
	 * @description 根据关键字搜索投诉建议
	 */
	$(document).on('click', '#searchComplaints', function () {
		var property = $('#keys').val();
		var value = $('#keyWords').val();
		// 发出请求 并且重载表格
		table.reload('complaintInfos', {
			url: baseUrl + 'complaint/managerSearchComplaints.do',
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
});