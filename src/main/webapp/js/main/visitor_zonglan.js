const baseUrl = 'http://codemata.club:8080/bysj/';

layui.use(['form', 'table', 'laytpl', 'layer'], function () {
	var form = layui.form;
	var table = layui.table;
	var laytpl = layui.laytpl;
	var layerIndex;
	var $ = layui.jquery;
	
	/**
	 * @author DengJie
	 * @date 2021-05-08
	 * @description 初始化表格
	 */
	table.render({
		elem: '#visitorInfos',
		title: '来访人员信息',
		url: baseUrl + 'visitor/getAllVisitors.do',
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#headerToolList',
		defaultToolBar: ['filter', 'exports', 'print'],
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'visitorId', title: '编号', align: 'center'},
			{field: 'visitorName', title: '姓名', align: 'center'},
			{field: 'visitorTel', title: '联系方式', align: 'center'},
			{field: 'visitorTemperature', title: '体温', align: 'center', templet: '#visitorTemperatureTpl'},
			{field: 'visitorHomeAddress', title: '家庭住址', align: 'center'},
			{field: 'visitorWorkAddress', title: '工作地址', align: 'center'},
			{title: '操作', toolbar: '#cellToolList', align: 'center'}
		]],
		parseData: function (res) {
			return {
				"code": res.data.code,
				"count": res.data.count,
				"msg": res.data.msg,
				"data": res.data.visitors
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
	
	table.on('tool(visitorInfos)', function (row) {
		var currentSelectedRow = row.data;
		var layEvent = row.event;
		if (layEvent === 'delete') {
			$.ajax({
				url: baseUrl + 'visitor/removeVisitor.do',
				data: {
					visitorId: currentSelectedRow.visitorId
				},
				type: 'post',
				dataType: 'json',
				success: function (res) {
					if (res.status == 'success') {
						layer.msg('删除成功', { time: 3000, icon: 1});
						table.reload('visitorInfos', {
							url: baseUrl + 'visitor/getAllVisitors.do',
							where: {
								userId: localStorage.getItem("userId")
							},
							page: {
								curr: 1
							}
						});
					} else {
						layer.msg('服务端异常', {time: 3000, icon: 2});
					}
				},
				error: function (res) {
					layer.msg('预期之外的错误', { time: 3000, icon: 0});
				}
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-05-08
	 * @description 监听头部工具栏事件
	 */
	table.on('toolbar(visitorInfos)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			case 'exportSelectedVisitors':
				if (checkStatus.data.length == 0) {
					layer.msg('当前没有选中记录,无法执行导出!', { time: 3000, icon: 2});
				} else if (checkStatus.data.length > 0) {
					table.exportFile(obj.config.id, checkStatus.data, 'xls');
					layer.msg('成功导出' + checkStatus.data.length + '条记录', { time: 3000, icon: 1});
				}
				break;
			case 'exportAllVisitors':
				$.ajax({
					url: baseUrl + 'visitor/exportAllVisitors.do',
					data: {
						userId: localStorage.getItem("userId")
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						if (res.data.count > 0) {
							table.exportFile(obj.config.id, res.data.visitors, 'xls');
							layer.msg('成功导出' + res.data.count + '条记录', { time: 3000, icon: 1});
						} else if (res.data.count == 0) {
							layer.msg('没有记录,无法导出', { time: 3000, icon: 0});
						} else {
							layer.msg('服务端异常', { time: 3000, icon: 2});
						}
					},
					error: function (res) {
						layer.msg('预期之外的错误', { time: 3000, icon: 2});
					}
				});
				break;
			case 'refreshTable':
				table.reload('visitorInfos', {
					url: baseUrl + 'visitor/getAllVisitors.do',
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
	
	form.on('select(selectorContainer)', function (selector) {
		if (selector.value == '日期') {
			layer.open({
				title: '提示',
				content: '日期格式应该遵照:xxxx-xx-xx'
			});
		}
	});
	
	$(document).on('click', '#searchVisitors', function () {
		var property = $('#keys').val();
		var value = $('#keyWords').val();
		if (property == '日期') {
			var strs = value.split('-');
			if (strs.length != 3) {
				layer.open({
					title: "提示",
					content: '日期格式不正确,日期格式应该遵照:xxxx-xx-xx'
				});
				return;
			}
			
			if (strs[0].length != 4 || strs[1].length != 2 || strs[2].length != 2) {
				layer.open({
					title: '提示',
					content: '日期格式不正确,日期格式应该遵照:xxxx-xx-xx'
				});
				return;
			}
			
			table.reload('visitorInfos', {
				url: baseUrl + 'visitor/getVisitorsByDate.do',
				where: {
					visitDate: value
				},
				page: {
					curr: 1
				}
			});
		} else if (property == '体温') {
			console.log(value);
			table.reload('visitorInfos', {
				url: baseUrl + 'visitor/getVisitorsByTemperature.do',
				where: {
					visitorTemperature: value
				},
				page: {
					curr: 1
				}
			});
		}
	});
});