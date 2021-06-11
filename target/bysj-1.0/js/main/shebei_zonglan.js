var baseUrl = 'http://codemata.club:8080/bysj/equipment/'

layui.use(['form', 'table', 'layer'], function () {
	var table = layui.table;
	var layer = layui.layer;
	var form = layui.form;
	var $ = layui.jquery;
	var editLayerIndex;
	
	/**
	 * @author DengJie
	 * @date 2021-03-11
	 * @description 获取信息并在表格中展示
	 */
	table.render({
		elem: '#equipmentInfos',
		title: '设备信息表',
		url: baseUrl + 'getEquipmentInfosByPage.do',
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#headerToolList',
		defaultToolBar: ['filter', 'exports', 'print'],
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'equipmentId', title: '设备ID', fixed: 'left', align: 'center'},
			{field: 'equipmentName', title: '设备名称', align: 'center'},
			{field: 'equipmentPrice', title: '单价', align: 'center', sort: true, width: 80},
			{field: 'equipmentBrand', title: '设备品牌', align: 'center'},
			{field: 'equipmentType', title: '设备型号', align: 'center'},
			{field: 'equipmentQuantity', title: '数量', align: 'center', sort: true, width: 80},
			{field: 'managerTel', title: '管理员电话', align: 'center', width: 120},
			{field: 'managerName', title: '管理员姓名', align: 'center', sort: true, width: 120},
			{field: 'buyDate', title: '购买日期', align: 'center', sort: true, width: 120},
			{title: '操作', toolbar: '#cellToolList', align: 'center'}
		]],
		request: {
			pageName: 'page',
			limitName: 'limit'
		},
		parseData: function (res) {
			return {
				"code": res.code,
				"count": res.count,
				"msg": res.msg,
				"data": res.equipments
			}
		},
		where: {
			userId: localStorage.getItem("userId")
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-03-11
	 * @description 监听头部工具栏事件(导出选中记录,导出所有记录,刷新表格)
	 */
	table.on('toolbar(equipmentInfos)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch(obj.event){
			// 导出选中的记录到excel文件
			case 'exportSelectedInfos':
				// table.exportFile(obj.config.id, checkStatus.data, 'xls');
				if (checkStatus.data.length == 0) {
					layer.msg('当前未选中记录,不可执行导出操作', {time: 3000, icon: 0});
				} else if (checkStatus.data.length > 0) {
					table.exportFile(obj.config.id, checkStatus.data, 'xls');
					layer.msg('成功导出' + checkStatus.data.length + '条记录', {time: 3000, icon: 1});
				}
				break;
			// 导出数据库中所有记录到excel文件
			case 'exportAllInfos':
				$.ajax({
					url: baseUrl + 'exportAllEquipmentInfos.do',
					data: {
						userId: localStorage.getItem("userId")
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						table.exportFile(obj.config.id, res, 'xls');
						layer.msg('导出成功,共' + res.length + '条记录', {time: 3000, icon: 1});
					},
					error: function (res) {
						layer.msg('遇到了预期之外的错误', {time: 3000, icon: 2});
					}
				});
				break;
			// 刷新表格
			case 'refreshTable':
				table.reload('equipmentInfos', {
					url: baseUrl + 'getEquipmentInfosByPage.do',
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
	 * @date 2021-03-11
	 * @description 监听单元格工具条事件(删除某行,查看设备图片,编辑某行)
	 */
	table.on('tool(equipmentInfos)', function (row) {
		var currentSelectedEquipment = row.data;
		var layEvent = row.event;
		
		// 判别事件
		if (layEvent === 'edit') {	// 监听编辑按钮
			// 将获取到数据展示在修改面板中
			$('#equipmentId').attr('value', currentSelectedEquipment.equipmentId);
			$('#equipmentName').attr('value', currentSelectedEquipment.equipmentName);
			$('#equipmentBrand').attr('value', currentSelectedEquipment.equipmentBrand);
			$('#equipmentType').attr('value', currentSelectedEquipment.equipmentType);
			$('#equipmentPrice').attr('value', currentSelectedEquipment.equipmentPrice);
			$('#equipmentQuantity').attr('value', currentSelectedEquipment.equipmentQuantity);
			$('#managerId').attr('value', currentSelectedEquipment.managerId);
			$('#managerName').attr('value', currentSelectedEquipment.managerName);
			$('#managerTel').attr('value', currentSelectedEquipment.managerTel);
			// 打开弹出层
			editLayerIndex = layer.open({
				type: 1,
				title: '修改设备信息',
				area: ['520px', '620px'],
				content: $('#editForm'),
				cancel: function () {
					// 点击取消后 因为可能发生过表单修改但是未执行提交 需要将表单恢复未最初的样子 需要执行重置
					$('#modifyForm')[0].reset();
					form.render();
				}
			});
		} else if (layEvent === 'preview') {	// 监听查看图片事件
		// TODO 有待完成
		$('#pictureContainer').html('');
			var pictures = currentSelectedEquipment.equipmentPicture.split('+');
			for (let i = 0; i < pictures.length; ++i) {
				var img = '<img src=">' + pictures[i] + '" alt="图片不见了" />'
				$('#pictureContainer').append()
			}
			layer.photos({
				content: '#pictureContainer',
				anim: 5
			});
		} else if (layEvent === 'delete') {	// 监听删除按钮
			layer.confirm('是否确认删除该行', function (index) {
				// 删除对应行的DOM结构
				row.del();
				layer.close(index);
				// 向服务端发起真正的删除
				$.ajax({
					url: baseUrl + 'removeEquipmentInfo.do',
					data: {
						equipmentId: currentSelectedEquipment.equipmentId,
						userId: localStorage.getItem("userId")
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						if (res.msgId > 0) {	// 删除成功
							// 加载提示信息
							layer.msg(res.msgContent, {time: 3000, icon: 1});
							// 重载表格
							table.reload('equipmentInfos', {
								url: baseUrl + 'getEquipmentInfosByPage.do',
								page: {
									curr: 1
								},
								where: {
									userId: localStorage.getItem("userId")
								}
							});
						} else {
							layer.msg(res.msgContent, {time: 3000, icon: 2});
						}
					},
					error: function (res) {
						layer.msg('发生了预期之外的错误', {time: 3000, icon: 2});
					}
				});
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-03-17
	 * @description 修改设备信息
	 */
	$(document).on('click', '#submit', function () {
		var canSubmit = true;
		// 获取用户修改之后的设备信息字段内容
		var equipment = {
			equipmentId: $('#equipmentId').val(),
			equipmentName: $('#equipmentName').val(),
			equipmentBrand: $('#equipmentBrand').val(),
			equipmentType: $('#equipmentType').val(),
			equipmentPrice: $('#equipmentPrice').val(),
			equipmentQuantity: $('#equipmentQuantity').val(),
			managerId: $('#managerId').val()
		};
		// 验证字段内容格式是否有错误
		// 验证设备名称
		if (equipment.equipmentName == '' || equipment.equipmentName.length > 32) {
			$('#equipmentName').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#equipmentName').removeAttr('style');
		}
		
		// 验证设备品牌
		if (equipment.equipmentBrand == '' || equipment.equipmentBrand.length > 32) {
			$('#equipmentBrand').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#equipmentBrand').removeAttr('style');
		}
		
		// 验证设备型号
		if (equipment.equipmentType == '' || equipment.equipmentType.length > 32) {
			$('#equipmentType').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#equipmentType').removeAttr('style');
		}
		
		// 验证设备单价
		if (equipment.equipmentPrice < 0 || equipment.equipmentPrice == '') { 
			$('#equipmentPrice').attr('style', 'border-color: red;');
			canSubmit = false;
		} else {
			$('#equipmentPrice').removeAttr('style');
		}
		
		// 验证设备数量
		if (equipment.equipmentQuantity < 0 || Math.floor(equipment.equipmentQuantity) != equipment.equipmentQuantity || equipment.equipmentQuantity == '') {
			$('#equipmentQuantity').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#equipmentQuantity').removeAttr('style');
		}
		
		// 验证设备管理员
		if (equipment.managerId == '' || equipment.managerId.length > 11) {
			$('#managerId').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#managerId').removeAttr('style');
		}
		
		if (!canSubmit) {
			layer.msg('含有格式不正确的字段!', {time: 3000, icon: 2});
			return;
		}
		
		// 向服务端发起修改指令
		$.ajax({
			url: baseUrl + 'modifyEquipmentInfo.do',
			data: {
				equipment: JSON.stringify(equipment),
				userId: localStorage.getItem("userId")
			},
			type: 'post',
			dataType: 'json',
			success: function (res) {
				if (res.msgId > 0) {
					layer.msg(res.msgContent, {time: 3000, icon: 1});
					table.reload('equipmentInfos', {
						url: baseUrl + 'getEquipmentInfosByPage.do',
						where: {
							userId: localStorage.getItem("userId")
						},
						page: {
							curr: 1
						}
					});
					layer.close(editLayerIndex);
				} else {
					layer.msg(res.msgContent, {time: 3000, icon: 2});
				}
			},
			error: function (res) {
				layer.msg('发生了预期之外的错误', {time: 3000, icon: 2});
			}
		});
	});
	
	/**
	 * @author DengJie
	 * @date 2021-03-17
	 * @description 根据属性(设备ID,设备名称,设备品牌,负责人ID)搜索设备信息
	 */
	$(document).on('click', '#search', function () {
		var property = $('#keys').val();
		var keyWords = $('#keyWords').val();
		// 执行搜索并且重载表格
		table.reload('equipmentInfos', {
			url: baseUrl + 'search.do',
			where: {
				property: property,
				keyWords: keyWords,
				userId: localStorage.getItem("userId")
			},
			page: {
				curr: 1
			}
		});
	});
});