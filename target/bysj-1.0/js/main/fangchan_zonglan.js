const baseUrl = 'http://codemata.club:8080/bysj/';

layui.use(['table', 'laytpl'], function () {
	var table = layui.table;
	var laytpl = layui.laytpl;
	var $ = layui.jquery;
	var layerIndex;
	
	table.render({
		elem: '#houseInfos',
		title: '房产信息表',
		url: baseUrl + 'HouseInfo/getAllHouseInfos.do',
		method: 'post',
		toolbar: '#headerToolList',
		defaultToolBar: ['filter', 'export', 'print'],
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'houseId', title: '房产编号', align: 'center'},
			{field: 'areaId', title: '区号', align: 'center', templet: '#areaIdTpl'},
			{field: 'unitId', title: '单元号', align: 'center', templet: '#unitIdTpl'},
			{field: 'roomId', title: '门牌号', align: 'center'},
			{field: 'userName', title: '业主姓名', align: 'center'},
			{field: 'userTel', title: '业主电话', align: 'center'},
			{title: '操作', toolbar: '#cellToolList', align: 'center'}
		]],
		parseData: function (res) {
			return {
				"code": res.data.code,
				"count": res.data.count,
				"msg": res.data.msg,
				"data": res.data.houseInfos
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
	 * @date 2021-05-07
	 * @description 监听单元格时间(删除某条房产信息)
	 */
	table.on('tool(houseInfos)', function (row) {
		var currentSelectedRow = row.data;
		var layEvent = row.event;
		if (layEvent === 'delete') {
			if (currentSelectedRow.userName != '暂无业主') {
				layer.msg('已经有人入住,无法删除!', {time: 3000, icon: 2});
				return;
			}
			$.ajax({
				url: baseUrl + 'HouseInfo/deleteHouseInfo.do',
				data: {
					userId: localStorage.getItem("userId"),
					houseId: currentSelectedRow.houseId,
				},
				type: 'post',
				dataType: 'json',
				success: function (res) {
					if (res.status == 'success') {
						layer.msg('删除成功', {time: 3000, icon: 1});
						table.reload('houseInfos', {
							url: baseUrl + 'HouseInfo/getAllHouseInfos.do',
							where: {
								userId: localStorage.getItem("userId")
							},
							page: {
								curr: 1
							}
						});
					} else {
						layer.msg('删除失败,请重试!', {time: 3000, icon: 2});
					}
				},
				error: function (res) {
					layer.msg('预期之外的错误', {time: 3000, icon: 0});
				}
			});
		}
	});
	
	table.on('toolbar(houseInfos)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			case 'exportSelectedHouseInfos':
				if (checkStatus.data.length == 0) {
					layer.msg('当前未选中记录,无法导出!', {time: 3000, icon: 2});
				} else if (checkStatus.data.length > 0) {
					table.exportFile(obj.config.id, checkStatus.data, 'xls');
					layer.msg('成功导出' + checkStatus.data.length + '条记录', {time: 3000, icon: 1});
				}
				break;
			case 'exportAllHouseInfos':
				$.ajax({
					url: baseUrl + 'HouseInfo/exportAllHouseInfos.do',
					data: {
						userId: localStorage.getItem("userId")
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						if (res.status == 'success') {
							if (res.data.count == 0) {
								layer.msg('当前没有数据,无法导出!', {time: 3000, icon: 0});
							} else {
								table.exportFile(obj.config.id, res.data.houseInfos, 'xls');
								layer.msg('导出成功,共' + res.data.count + '条记录', {time: 3000, icon: 1});
							}
						} else {
							layer.msg('导出失败,服务端异常!', {time: 3000, icon: 2});
						}
					},
					error: function (res) {
						layer.msg('预期之外的错误', {time: 3000, icon: 2});
					}
				});
				break;
			case 'refreshTable':
				table.reload('houseInfos', {
					url: baseUrl + 'HouseInfo/getAllHouseInfos.do',
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
});