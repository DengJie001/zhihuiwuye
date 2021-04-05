var baseUrl = 'http://localhost:8080/bysj/manager/';

layui.use(['table', 'layer', 'form'], function() {
	var table = layui.table;
	var layer = layui.layer;
	var form = layui.form;
	var $ = layui.jquery;
	var editLayerIndex;
	table.render({
		title: '管理员身份信息',
		elem: '#managerInfos',
		url: baseUrl + 'getAllManagers.do', //数据接口
		method: 'post',
		toolbar: '#headerToolList',
		defaultToolBar: ['filter', 'exports', 'print'],
		page: true, //开启分页
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		cols: [
			[ //表头
				{
					type: 'checkbox',
					fixed: 'left'
				}, {
					fixed: 'left',
					field: 'managerId',
					align: 'center',
					title: '管理员id',
				}, {
					field: 'managerAvatar',
					title: '头像',
					align: 'center'
				}, {
					field: 'managerName',
					title: '姓名',
					align: 'center'
				}, {
					field: 'managerTel',
					title: '联系方式',
					align: 'center'
				}, {
					title: '操作',
					fixed: 'right',
					align: 'center',
					toolbar: '#cellToolList',
				}
			]
		],
		parseData: function(res) {
			return {
				"code": res.code,
				"count": res.count,
				"msg": res.msg,
				"data": res.managers
			}
		},
		request: {
			pageName: 'page',
			limitName: 'limit'
		}
	});

	/**
	 * @author DengJie
	 * @date 2021-03-08
	 * @description 监听头部工具栏事件(导出选中记录到excel,导出所有记录到excel,刷新)
	 */
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			// 监听导出选中行到excel事件
			case 'exportSelectedInfos':
				table.exportFile(obj.config.id, checkStatus.data, 'xls');
				break;
			// 监听导出所有信息到excel
			case 'exportAllInfos':
				$.ajax({
					url: baseUrl + 'exportAllInfos.do',
					type: 'post',
					success: function (res) {
						table.exportFile(obj.config.id, res, 'xls');
					},
					error: function (res) {
						layer.msg('发生了预期之外的错误', {time: 3000, icon: 0});
					}
				});
				break;
			// 监听刷新事件
			case 'refreshTable':
				// 刷新表格信息
				table.reload('managerInfos', {
					url: baseUrl + 'getAllManagers.do',
					page: {
						curr: 1
					},
					where: {}
				});
				break;
		}
	})

	/**
	 * @author DengJie
	 * @date 2021-03-08
	 * @description 监听单元格工具条事件(查新详细信息,删除对应记录,编辑信息)
	 */
	table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var currentSelectedManager = obj.data; //获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

		// 监听查看详细信息事件
		if (layEvent === 'detail') {
			//TODO
			console.log('查看信息');
		} else if (layEvent === 'del') { //删除
			layer.confirm('真的删除行么', function(index) {
				obj.del(); //删除对应行（tr）的DOM结构
				layer.close(index);
				//向服务端发送删除指令
				$.ajax({
					url: baseUrl + 'removeManagerInfo.do',
					data: {
						managerId: currentSelectedManager.managerId
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						if (res.msgId > 0) {
							// 弹出提示框
							layer.msg(res.msgContent, {time: 3000, icon: 1});
							// 重载表格
							table.reload('managerInfos', {
								page: {
									curr:1
								},
								where: {}
							});
						} else {
							// 弹出提示信息
							layer.msg(res.msgContent, {time: 3000, icon: 2});
						}
					},
					error: function (res) {
						// 弹出提示信息
						layer.msg('发生了预期之外的错误', {time: 3000, icon: 0});
					}
				});
			});
		} else if (layEvent === 'edit') {	// 监听编辑事件
			editLayerIndex = layer.open({
				type: 1, // 弹出层类型
				title: '修改管理员信息',
				area: ['420px', '330px'],
				content: $('#editForm')
			});
			$('#managerId').attr('value', currentSelectedManager.managerId);
			$('#managerName').attr('value', currentSelectedManager.managerName);
			$('#managerTel').attr('value', currentSelectedManager.managerTel);
			$('#avatarContainer > img').attr('src', currentSelectedManager.managerAvatar);
			$('#avatarContainer > img').attr('layer-src', currentSelectedManager.managerAvatar);
		}
	});
	
	// 提交修改
	$(document).on('click', '#submit', function () {
		var managerId = $('#managerId').val();
		var managerTel = $('#managerTel').val();
		var managerName = $('#managerName').val();
		if (managerId == '' || managerTel == '' || managerName == '') {
			layer.msg('不允许有空字段', {time: 3000, icon: 2});
			return;
		}
		// 向服务端发起修改信息指令
		$.ajax({
			url: baseUrl + 'modifyManagerInfo.do',
			data: {
				managerId: managerId,
				managerTel: managerTel,
				managerName: managerName
			},
			type: 'post',
			dataType: 'json',
			success: function (res) {
				// 大于0说明修改管理员信息成功 需要重载表格
				if (res.msgId > 0) {
					// 给出操作成功提示
					layer.msg(res.msgContent, {time: 3000, icon: 1});
					// 重载表格
					table.reload('managerInfos', {
						where: {},
						page: {
							curr: 1
						}
					});
					layer.close(editLayerIndex);
				} else {
					// 给出操作失败提示
					layer.msg(res.msgContent, {time: 3000, icon : 2});
					layer.close(editLayerIndex);
				}
			},
			error: function (res) {
				// 给出操作异常提示
				layer.msg('发生预期之外的错误', {time: 3000, icon: 0});
				layer.close(editLayerIndex);
			}
		})
	});
	
	// 重置表格可修改部分
	$(document).on('click', '#reset', function () {
		$('#managerName').attr('value', '');
		$('#managerTel').attr('value', '');
	});

	// 搜索
	$(document).on('click', '#search', function () {
		var property = $('#keys').val();
		var keyWords = $('#keyWords').val();
		table.reload('managerInfos', {
			url: baseUrl + 'search.do',
			where: {
				property: property,
				keyWords: keyWords
			},
			page: {
				curr: 1
			}
		});
	});
});