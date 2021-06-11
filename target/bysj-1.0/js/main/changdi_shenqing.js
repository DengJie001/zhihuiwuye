const baseUrl = 'http://codemata.club:8080/bysj/';

layui.use(['layer', 'table', 'form'], function () {
	var table = layui.table;
	var layer = layui.layer;
	var form = layui.form;
	var $ = layui.jquery;
	var leyerindex;
	console.log(localStorage.getItem('userId'));
	/**
	 * @author DengJie
	 * @date 2021-04-14
	 * @description 载入所有的场地申请信息
	 */
	table.render({
		elem: '#placeApplications',
		title: '场地申请表',
		url: baseUrl + 'PlaceApplication/getPlaceApplications.do',
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#headerToolList',
		defaultToolBar: ['filter', 'exports', 'print'],
		initSort: {
			field: 'result',
			type: 'asc'
		},	// 按照申请结果升序排列
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'applicationId', title: '申请编号', align: 'center'},
			{field: 'placeId', title: '场地编号', align: 'center', width: 90},
			{field: 'userName', title: '用户姓名', align: 'center', width: 100, event: 'showUser'},
			{field: 'applicationReason', title: '申请原因', align: 'center'},
			{field: 'result', title: '申请结果', align: 'center'},
			{field: 'resultDescription', title: '备注', align: 'center'},
			{field: 'managerName', title: '处理人', align: 'center', event: 'showManager', width: 100},
			{field: 'resultDate', title: '处理时间', align: 'center', sort: true},
			{field: 'applicationDate', title: '申请时间', align: 'center', sort: true},
			{field: 'beginDate', title: '开始使用', align: 'center', sort: true},
			{field: 'endDate', title: '结束使用', align: 'center', sort: true},
			{field: 'cost', title: '费用', align: 'center', sort: true, width: 80},
			{title: '操作', toolbar: '#cellToolList', align: 'center', width: 100}
		]],	// 表头
		parseData: function (res) {
			return {
				"code": res.code,
				"count": res.count,
				"msg": res.msg,
				"data": res.placeApplications
			}
		},
		request: {
			pageName: 'page',
			limitName: 'limit',
		},
		where: {
			userId: localStorage.getItem('userId')
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-14
	 * @description 监听单元格时间,即点击审核,点击用户姓名显示用户详细信息,点击处理人姓名显示处理人详细信息
	 */
	table.on('tool(placeApplications)', function (row) {
		var currentSelectedPlaceApplication = row.data;
		var layEvent = row.event;
		if (layEvent === 'showUser') {	// 显示用户姓名和电话号码
			$('#t_name').html(currentSelectedPlaceApplication.userName);
			$('#t_tel').html(currentSelectedPlaceApplication.userTel);
			layer.open({
				type: 1,
				title: '用户详细信息',
				content: $('#NameAndTel'),
				cancel: function () {
					$('#t_name').html('');
					$('#t_tel').html('');
				}
			});
		} else if (layEvent === 'showManager') {	// 处理人的姓名和电话号码
			$('#t_name').html(currentSelectedPlaceApplication.managerName);
			$('#t_tel').html(currentSelectedPlaceApplication.managerTel);
			layer.open({
				type: 1,
				title: '处理人详细信息',
				content: $('#NameAndTel'),
				cancel: function () {
					$('#t_name').html();
					$('#t_tel').html();
				}
			});
		} else if (layEvent === 'judgePlaceApplication') {	// 审核该条场地申请是否通过
			// 单选框赋值
			if (currentSelectedPlaceApplication.result == '待审核') {
				$("input[name='j_result']")[0].checked = 'checked';
			} else if (currentSelectedPlaceApplication.result == '通过') {
				$("input[name='j_result']")[1].checked = 'checked';
			} else if (currentSelectedPlaceApplication.result == '拒绝') {
				$("input[name='j_result']")[2].checked = 'checked';
			}
			// 其他信息赋值
			$('#j_applicationId').val(currentSelectedPlaceApplication.applicationId);
			$('#j_placeId').val(currentSelectedPlaceApplication.placeId);
			$('#j_userName').val(currentSelectedPlaceApplication.userName);
			$('#j_userTel').val(currentSelectedPlaceApplication.userTel);
			$('#j_applicationReason').val(currentSelectedPlaceApplication.applicationReason);
			$('#j_resultDescription').val(currentSelectedPlaceApplication.resultDescription);
			$('#j_managerId').val(currentSelectedPlaceApplication.managerTel);
			$('#j_applicationDate').val(currentSelectedPlaceApplication.applicationDate);
			$('#j_beginDate').val(currentSelectedPlaceApplication.beginDate);
			$('#j_endDate').val(currentSelectedPlaceApplication.endDate);
			$('#j_cost').val(currentSelectedPlaceApplication.cost);
			form.render();
			layerIndex = layer.open({
				type: 1,
				title: '审核申请:' + currentSelectedPlaceApplication.applicationId,
				content: $('#placeApplicationDetail'),
				area: ['520px', '700px']
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-14
	 * @description 监听下拉框选择
	 */
	form.on('select(applicationPropertySelector)', function (selector) {
		if (selector.value == '申请结果') {
			layerIndex = layer.open({
				type: 1,
				title: '选择申请结果进行查询',
				area: ['400px'],
				content: $('#resultContainer')
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-14
	 * @description 按照申请结果查询
	 */
	$(document).on('click', '#searchByResult', function () {
		var property = '申请结果';
		var value = $("input[name='placeStatus']:checked").val();
		// 重载表格
		table.reload('placeApplications', {
			url: baseUrl + 'PlaceApplication/getPlaceApplicationsByResult.do',
			where: {
				property: property,
				value: value,
				userId: localStorage.getItem("userId")
			},
			page: {
				curr: 1
			}
		});
		// 关闭该页面
		layer.close(layerIndex);
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-14
	 * @description 关闭结果选择面板 取消按照结果搜索
	 */
	$(document).on('click', '#closeResultLayer', function () {
		layer.close(layerIndex);
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-14
	 * @description 提交场地申请审核结果
	 */
	$(document).on('click', '#j_judge', function () {
		var result = $("input[name='j_result']:checked").val();
		var resultDescription = $('#j_resultDescription').val();
		var managerId = $('#j_managerId').val();
		var applicationId = $('#j_applicationId').val();
		$.ajax({
			url: baseUrl + 'PlaceApplication/modifyApplicationResult.do',
			data: {
				result: result,
				resultDescription: resultDescription,
				managerId: managerId,
				applicationId: applicationId,
				userId: localStorage.getItem("userId")
			},
			type: 'post',
			dataType: 'json',
			success: function (res) {
				if (res.msgId == 0) {	// 操作成功 但未引起数据变化
					layer.msg(res.msgContent, {time: 3000, icon: 0});
				} else if (res.msgId > 0) {	// 修改审核结果成功
					layer.msg(res.msgContent, {time: 3000, icon: 1});
				} else if (res.msgId == -1) {	// 已经没有该条申请记录 用户可能已经删除
					layer.msg(res.msgContent, {time: 3000, icon: 0});
					// 关闭审核页面
					layer.close(layerIndex);
					// 重载表格
					table.reload('placeApplications', {
						url: baseUrl + 'PlaceApplication/getPlaceApplications.do',
						where: {
							userId: localStorage.getItem("userId")
						},
						page: {
							curr: 1
						}
					});
				} else {	// 发生了异常
					layer.msg(res.msgContent, {time: 3000, icon: 2});
				}
			},
			error: function (errorRes) {
				layer.msg('预期之外的错误', {time: 3000, icon: 2});
			}
		})
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-14
	 * @description 监听头部工具栏事件(导出选中记录,导出所有记录,刷新表格)
	 */
	table.on('toolbar(placeApplications)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch(obj.event) {
			// 导出选中记录到excel
			case 'exportSelectedPlaceApplications':
				if (checkStatus.data.length == 0) {
					layer.msg('当前未选中记录,无法执行导出操作', {time: 3000, icon: 0});
				} else if (checkStatus.data.length > 0) {
					table.exportFile(obj.config.id, checkStatus.data, 'xls');
					layer.msg('成功导出' + checkStatus.data.length + '条记录!', {time: 3000, icon: 1});
				}
				break;
			// 导出所有记录
			case 'exportAllPlaceApplications':
				$.ajax({
					url: baseUrl + 'PlaceApplication/exportAllPlaceApplications.do',
					data: {
						userId: localStorage.getItem("userId")
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						if (res.placeApplications.length > 0) {
							table.exportFile(obj.config.id, res.placeApplications, 'xls');
							layer.msg('成功导出' + res.placeApplications.length + '条记录', {time: 3000, icon: 1});
						} else if (res.placeApplications.length == 0) {
							layer.msg('暂无数据', {time: 3000, icon: 0});
						}
					},
					error: function (errorRes) {
						layer.msg('预期之外的错误', {time: 3000, icon: 2});
					}
				});
				break;
			// 刷新表格
			case 'refreshTable':
				table.reload('placeApplications', {
					url: baseUrl + 'PlaceApplication/getPlaceApplications.do',
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
	 * @date 2021-04-15
	 * @description 监听普通搜索按钮,按照管理员,用户场地编号进行搜索
	 */
	$(document).on('click', '#search', function () {
		var property = $('#keys').val();
		var value = $('#keyWords').val();
		// 重载表格
		table.reload('placeApplications', {
			url: baseUrl + 'PlaceApplication/getPlaceApplications.do',
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