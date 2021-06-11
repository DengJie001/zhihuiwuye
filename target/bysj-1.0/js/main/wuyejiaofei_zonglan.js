const baseUrl = 'http://codemata.club:8080/bysj/';

layui.use(['form', 'layer', 'laytpl', 'table'], function () {
	var form = layui.form;
	var layer = layui.layer;
	var laytpl = layui.laytpl;
	var table = layui.table;
	var $ = layui.jquery;
	var layerIndex;
	
	/**
	 * @author DengJie
	 * @date 2021-04-26
	 * @description 初始化表格
	 */
	table.render({
		elem: '#billInfos',
		title: '物业账单',
		url: baseUrl + 'bill/managerGetAllBills.do',
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#headerToolList',
		defaultToolBar: ['filter', 'exports', 'print'],
		initSort: {
			field: 'billId',
			type: 'desc'
		},
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'billId', title: '账单编号', align: 'center'},
			{field: 'billFigure', title: '金额', align: 'center', width: 80},
			{field: 'userName', title: '缴费人', align: 'center', width: 100},
			{field: 'areaId', title: '区', align: 'center', width: 80},
			{field: 'unitId', title: '单元', align: 'center', width: 80},
			{field: 'roomId', title: '门牌号', align: 'center', width: 100},
			{field: 'billStatus', title: '缴费状态', align: 'center', templet: '#billStatusTpl'},
			{field: 'billDate', title: '账单时间', align: 'center', sort: true},
			{field: 'billCategory', title: '账单类别', align: 'center', templet: '#billCategoryTpl'},
			{field: 'managerName', title: '管理员', align: 'center'},
			{title: '操作', toolbar: '#cellToolList', align: 'center'}
		]],
		parseData: function (res) {
			console.log(res);
			return {
				"code": res.info.code,
				"count": res.info.count,
				"msg": res.info.msg,
				"data": res.info.bills
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
	 * @date 2021-04-26
	 * @description 监听下拉框选择变化
	 */
	form.on('select(billPropertySelector)', function (selector) {
		var property = '';
		var value;
		if (selector.value == '已缴费' || selector.value == '未缴费') {
			property = '状态';
			value = selector.value;
		} else {
			property = '类别';
			value = selector.value;
		}
		// 重载表格
		if (property == '状态' || property == '类别') {
			table.reload('billInfos', {
				url: baseUrl + 'bill/managerGetBills.do',
				where: {
					property: property,
					value: value,
					userId: localStorage.getItem("userId")
				},
				page: {
					curr: 1
				}
			});
		} else {
			table.reload('billInfos', {
				url: baseUrl + 'bill/managerGetAllBills.do',
				where: {
					userId: localStorage.getItem("userId")
				},
				page: {
					curr: 1
				}
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-26
	 * @description 监听头部工具栏事件
	 */
	table.on('toolbar(billInfos)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch(obj.event) {
			// 导出选中记录到excel
			case 'exportSelectedBillInfos':
				if (checkStatus.data.length == 0) {
					layer.msg('当前未选中记录,无法执行导出', {time: 3000, icon: 0});
				} else if (checkStatus.data.length > 0) {
					table.exportFile(obj.config.id, checkStatus.data, 'xls');
					layer.msg('成功导出' + checkStatus.data.length + '条账单', {time: 3000, icon: 1});
				}
				break;
			case 'exportAllBillInfos':
				$.ajax({
					url: baseUrl + 'bill/exportAllBills.do',
					data: {
						userId: localStorage.getItem("userId")
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						if (res.status == 'success') {
							table.exportFile(obj.config.id, res.info.bills, 'xls');
							layer.msg('成功导出' + res.info.count + '条账单', {time: 3000, icon: 1});
						} else {
							layer.msg('服务端异常', {time: 3000, icon: 0});
						}
					},
					error: function (res) {
						layer.msg('预期之外的错误', {time: 3000, icon: 2});
					}
				});
				break;
			case 'refreshTable':
				table.reload('billInfos', {
					url: baseUrl + 'bill/managerGetAllBills.do',
					where: {
						userId: localStorage.getItem("userId")
					},
					page: {curr: 1}
				});
				break;
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-26
	 * @description 监听单元格事件(编辑账单,删除账单,催缴费)
	 */
	table.on('tool(billInfos)', function (row) {
		var currentSelectedBill = row.data;
		var layEvent = row.event;
		// 判别事件
		if (layEvent === 'editBillInfo') {	// 编辑账单
			$('#billId').val(currentSelectedBill.billId);
			$('#managerId').val(currentSelectedBill.managerId);
			$('#billFigure').val(currentSelectedBill.billFigure);
			$('#areaId').val(currentSelectedBill.areaId);
			$('#unitId').val(currentSelectedBill.unitId);
			$('#roomId').val(currentSelectedBill.roomId);
			if (currentSelectedBill.billCategory == '物业费') {
				$('#utilityFee').attr('checked', 'checked');
			} else if (currentSelectedBill.billCategory == '电费') {
				$('#electricityFee').attr('checked', 'checked');
			} else if (currentSelectedBill.billCategory == '水费') {
				$('#waterFee').attr('checked', 'checked');
			}
			// 使表格元素生效
			form.render();
			layerIndex = layer.open({
				type: 1,
				title: '物业账单修改',
				area: ['520px', '620px'],
				content: $('#editBillInfoLayer'),
				cancel: function (res) {
					// 清楚radio的选中状态
					$('#utilityFee').removeAttr('checked');
					$('#waterFee').removeAttr('checked');
					$('#electricityFee').removeAttr('checked');
				}
			})
		} else if (layEvent === 'urgePay') {	// 催缴费
			if (currentSelectedBill.billStatus == '已缴费') {
				layer.msg('该账单已经缴费成功,不能催缴!');
				return;
			}
			// 执行催缴费
			$.ajax({
				url: baseUrl + 'bill/urgePayBill.do',
				data: {
					userId: localStorage.getItem("userId"),
					date: currentSelectedBill.billDate,
					areaId: currentSelectedBill.areaId,
					unitId: currentSelectedBill.unitId,
					roomId: currentSelectedBill.roomId,
					billCategory: currentSelectedBill.billCategory,
				},
				type: 'post',
				dataType: 'json',
				success: function (res) {
					if (res.status == 'success') {
						layer.msg('催缴费成功!', { time: 3000, icon: 1});
					} else {
						layer.msg('催缴费失败!', { time: 3000, icon: 2});
					}
				},
				error: function (res) {
					layer.msg('预期之外的错误', { time: 3000, icon: 0});
				}
			})
		} else if (layEvent === 'deleteBillInfo') {	// 删除物业账单
			// 删除dom结构
			row.del();
			// 服务端删除数据
			$.ajax({
				url: baseUrl + 'bill/deleteBill.do',
				data: {
					userId: localStorage.getItem("userId"),
					billId: currentSelectedBill.billId
				},
				type: 'post',
				dataType: 'json',
				success: function (res) {
					if (res.status == 'success') {
						layer.msg('删除成功', {time: 3000, icon: 1});
						table.reload('billInfos', {
							url: baseUrl + 'bill/managerGetAllBills.do',
							where: {
								userId: localStorage.getItem("userId")
							},
							page: {
								curr: 1
							}
						});
					} else {
						layer.msg('删除失败', {time: 3000, icon: 2});
					}
				},
				error: function (res) {
					layer.msg('预期之外的错误', {time: 3000, icon: 2});
				}
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-26
	 * @description 提交物业账单修改信息
	 */
	$(document).on('click', '#modifyUtilityBill', function () {
		var billId = $('#billId').val();
		var billFigure = $('#billFigure').val();
		var billCategory = $("input[name='billCategory']:checked").val();
		var canSubmit = true;
		var tips = '';
		
		if (billFigure <= 0) {
			tips += '金额不允许小于等于0';
			canSubmit = false;
			$('#billFigure').css('borderColor', 'red');
		} else {
			$('#billFigure').css('borderColor', '');
		}
		
		if (!canSubmit) {
			layer.open({
				title: '提示',
				content: tips
			});
			return;
		}
		
		$.ajax({
			url: baseUrl + 'bill/modifyBill.do',
			data: {
				userId: localStorage.getItem("userId"),
				billId: billId,
				billFigure: billFigure,
				billCategory: billCategory
			},
			type: 'post',
			dataType: 'json',
			success: function (res) {
				if (res.status == 'success') {
					layer.msg('更新成功', {time: 3000, icon: 1});
					layer.close(layerIndex);
					table.reload('billInfos', {
						url: baseUrl + 'bill/managerGetAllBills.do',
						where: {
							userId: localStorage.getItem("userId")
						},
						page: {
							curr: 1
						}
					});
				} else if (res.status == 'exception') {
					layer.msg('服务端异常', {time: 3000, icon: 0});
				} else {
					layer.msg('修改失败', {time: 3000, icon: 2});
				}
			},
			error: function (res) {
				layer.msg('预期之外的错误', {time: 3000, icon: 2});
			}
		})
	});
});