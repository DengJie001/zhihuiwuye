const baseUrl = 'http://localhost:8080/bysj/';

layui.use(['form', 'table', 'layer'], function () {
	var table = layui.table;
	var form = layui.form;
	var layer = layui.layer;
	var $ = layui.jquery;
	var editFormIndex;
	
	/**
	 * @author DengJie
	 * @date 2021-04-04
	 * @description  获取公告信息并在表格中展示
	 */
	table.render({
		title: '公告信息总览',
		elem: '#announcementInfos',
		url: baseUrl + 'announcement/getAnnouncements.do',
		method: 'post',
		toolbar: '#headerToolList',
		defaultToolBar: ['exports', 'print', 'filter'],
		page: true,	// 开启分页
		limit: 10,	// 初始化每一条公告条数
		limits: [10, 20, 30, 40, 50],
		initSort: {
			field: 'top',
			type: 'desc'
		},	// 默认降序排列
		cols: [
			[
				{
					type: 'checkbox',
					fixed: 'left'
				},
				{
					field: 'id',
					align: 'center',
					title: '公告ID',
					sort: true
				},
				{
					field: 'title',
					align: 'center',
					title: '公告标题'
				},
				{
					field: 'authorName',
					align: 'center',
					title: '发布人'
				},
				{
					field: 'authorId',
					align: 'center',
					title: '发布人ID'
				},
				{
					field: 'authorTel',
					align: 'center',
					title: '联系方式'
				},
				{
					field: 'content',
					align: 'center',
					title: '内容'
				},
				{
					field: 'date',
					align: 'center',
					title: '发布日期',
					sort: true
				},
				{
					field: 'top',
					align: 'center',
					title: '置顶',
					sort: true
				},
				{
					title: '操作',
					toolbar: '#cellToolList',
					align: 'center'
				}
			]
		],
		parseData: function(res) {
			return {
				"code": res.code,
				"count": res.count,
				"msg": res.msg,
				"data": res.announcements
			}
		},
		request: {
			pageName: 'page',
			limitName: 'limit'
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-04
	 * @description 监听头部工具栏(导出选中记录,导出所有记录,刷新表格)
	 */
	table.on('toolbar(announcementInfos)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch(obj.event) {
			// 导出选中记录到excel文件
			case 'exportSelectedAnnouncements':
				if (checkStatus.data.length == 0) {
					layer.msg('当前未选中记录,不可知性导出操作', {time: 3000, icon: 0});
				} else if (checkStatus.data.length > 0) {
					table.exportFile(obj.config.id, checkStatus.data, 'xls');
					layer.msg('成功导出' + checkStatus.data.length + '条记录', {time: 3000, icon: 1});
				}
				break;
			// 导出所有记录
			case 'exportAllAnnouncements':
				$.ajax({
					url: baseUrl + 'announcement/exportAllAnnouncements.do',
					type: 'post',
					dataType: 'json',
					success: function (res) {
						table.exportFile(obj.config.id, res.announcements, 'xls');
						layer.msg('导出成功,共' + res.count + '条记录', {time: 3000, icon: 1});
					},
					error: function (errorRes) {
						layer.msg('遇到了预期之外的错误', {time: 3000, icon: 2});
					}
				});
				break;
			// 刷新表格
			case 'refreshTable':
				table.reload('announcementInfos', {
					url: baseUrl + 'announcement/getAnnouncements.do',
					where: {
						property: null,
						value: null
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
	 * @date 2021-04-04
	 * @description 根据属性(标题,发布人,日期)搜索公告信息
	 */
	$(document).on('click', '#search', function () {
		var property = $('#keys').val();
		var value = $('#keyWords').val();
		// 如果关键字为空 不执行搜索
		if (value == '') {
			return;
		}
		// 执行搜索并且重置表格
		table.reload('announcementInfos', {
			url: baseUrl + 'announcement/getAnnouncements.do',
			where: {
				property: property,
				value: value
			},
			page: {
				curr: 1
			}
		});
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-04
	 * @description 监听下拉框选择
	 */
	form.on('select(announcementPropertySelector)', function (selector) {
		var year = new Date().getFullYear();
		var month = new Date().getMonth();
		var day = new Date().getDate();
		var now = '';
		// 将当前日期拼接为正确格式(xxxx-xx-xx),在提示中显示
		if (month < 9) {
			month = '0' + (month + 1)
		}
		if (day < 10) {
			day = '0' + day;
		}
		now = year + '-' + month + '-' + day;
		if (selector.value == '日期') {
			layer.open({
				title: '提示',
				content: '日期格式:xxxx-xx-xx,例如:' + now
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-04
	 * @description 监听单元格工具条事件(删除某行,查看详细公告,编辑某行)
	 */
	table.on('tool(announcementInfos)', function (row) {
		var currentSelectedAnnouncement = row.data;
		var layEvent = row.event;
		// 判别事件
		if (layEvent === 'edit') {	// 监听编辑按钮
			// 将获取到的数据展示在修改面板中
			$('#modifyId').attr('value', currentSelectedAnnouncement.id);
			$('#modifyTitle').attr('value', currentSelectedAnnouncement.title);
			$('#modifyContent').val(currentSelectedAnnouncement.content);
			$('#modifyAuthor').attr('value', currentSelectedAnnouncement.authorId);
			$('#modifyTel').attr('value', currentSelectedAnnouncement.authorTel);
			$('#modifyName').attr('value', currentSelectedAnnouncement.authorName);
			$('#modifyDate').attr('value', currentSelectedAnnouncement.date);
			// 动态对radio进行赋值
			if (currentSelectedAnnouncement.top == '是') {
				$('#yes').attr('checked', 'checked');
			} else {
				$('#no').attr('checked', 'checked');
			}
			form.render();
			editFormIndex = layer.open({
				type: 1,
				title: '修改公告信息',
				area: ['520px', '620px'],
				content: $('#editAnnouncement'),
				cancel: function () {
					$('#editAnnouncement').css('display', 'none');
					$('#modifyAnnouncement')[0].reset();
					form.render();
				}
			});
		} else if (layEvent === 'preview') {	// 监听查看按钮
			$('#announcementContent').text(currentSelectedAnnouncement.content);
			layer.open({
				type: 1,
				title: '公告内容-' + currentSelectedAnnouncement.title,
				area: ['520px', '620px'],
				content: $('#announcementDetail'),
				cancel: function () {
					$('#announcementDetail').css('display', 'none');
				}
			});
		} else if (layEvent === 'delete') {	// 监听删除按钮
			layer.confirm('是否确认删除该行,此操作不可逆!', function (index) {
				// 删除对应行的DOM结构
				row.del();
				layer.close(index);
				// 向服务端发起真正的删除请求
				$.ajax({
					url: baseUrl + 'announcement/removeAnnouncement.do',
					data: {
						id: currentSelectedAnnouncement.id
					},
					type: 'post',
					dataType: 'json',
					success: function (res) {
						console.log(res);
					},
					error: function (errorRes) {
						console.log(errorRes);
					}
				});
			});
		}
	});
	
	/**
	 * @author DengJie
	 * @date 2021-04-05
	 * @description 监听提交按钮触发
	 */
	$(document).on('click', '#submit', function () {
		var canSubmit = true;	// 能否提交更新信息的标志
		var tips = '';	// 字段内容格式错误提示
		var announcement = {
			id: $('#modifyId').val(),
			title: $('#modifyTitle').val(),
			author: $('#modifyAuthor').val(),
			content: $('#modifyContent').val(),
			top: $('#modifyTop input[name="top"]:checked').val()
		};
		// 验证字段内容是否有格式错误
		// 验证标题不为空
		if (announcement.title == '') {
			tips += '公告标题不允许为空';
			$('#modifyTitle').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#modifyTitle').removeAttr('style');
		}
		// 验证标题长度不超过32位
		if (announcement.title.length > 32) {
			tips += '公告标题长度不允许超过32位;';
			$('#modifyTitle').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#modifyTitle').removeAttr('style');
		}
		// 验证发布人ID字段不为空
		if (announcement.author == '') {
			tips += '发布人ID不允许为空';
			$('modifyAuthor').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#modifyAuthor').removeAttr('style');
		}
		// 验证发布人ID长度不超过11位
		if (announcement.author.length > 11) {
			tips += '发布人ID长度不允许超过11位;';
			$('#modifyAuthor').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#modifyAuthor').removeAttr('style');
		}
		// 验证公告内容不为空
		if (announcement.content == '') {
			tips += '公告内容不允许为空;'
			$('#modifyContent').attr('style', 'border-color:red;');
			canSubmit = false;
		} else {
			$('#modifyContent').removeAttr('style');
		}
		// 如果提交标志为false 则表示有格式错误的字段 阻止提交
		if (!canSubmit) {
			layer.open({
				title: '提示',
				content: tips
			});
			return;
		}
		// 提交修改请求
		console.log(announcement);
		$.ajax({
			url: baseUrl + 'announcement/modifyAnnouncement.do',
			data: {
				announcement: JSON.stringify(announcement)
			},
			type: 'post',
			dataType: 'json',
			success: function (res) {
				if (res.msgId > 0) {
					layer.msg(res.msgContent, {time: 3000, icon: 1});
					table.reload('announcementInfos', {
						url: baseUrl + 'announcement/getAnnouncements.do',
						where: {
							property: null,
							value: null
						},
						page: {
							curr: 1
						}
					});
					layer.close(editFormIndex);
					$('#editAnnouncement').css('display', 'none');
				} else {
					layer.msg(res.msgContent, {time: 3000, icon: 0});
				}
			},
			error: function (errorRes) {
				layer.msg(errorRes.msgContent, {time: 3000, icon: 2});
			}
		})
	});
});