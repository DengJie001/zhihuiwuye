const baseUrl = 'http://codemata.club:8080/bysj/';

layui.use(['form', 'table', 'layer', 'laytpl'], function () {
	var table = layui.table;
	var form = layui.form;
	var layer = layui.layer;
	var laytpl = layui.laytpl;
	var $ = layui.jquery;
	var layerIndex;
	
	table.render({
		elem: '#lostItemComplaintInfos',
		title: '失物招领举报处理',
		url: baseUrl + 'LostItemComplaint/getAllLostItemComplaints.do',
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#headerToolList',
		defaultToolBar: ['filter', 'exports', 'print'],
		cols: [[
			{field: 'complaintId', title: '举报编号', align: 'center'},
			{field: 'lostItemId', title: '失物招领编号', align: 'center'},
			{field: 'itemDescription', title: '内容', align: 'center'},
			{field: 'itemCategory', title: '分类', align: 'center'},
			{field: 'complaintCategory', title: '举报分类', align: 'center', templet: '#complaintCategoryTpl'},
			{field: 'date', title: '发布时间', align: 'center'},
			{title: '操作', toolbar: '#cellToolList', align: 'center'}
		]],
		parseData: function (res) {
			return {
				"code": res.info.code,
				"count": res.info.count,
				"msg": res.info.msg,
				"data": res.info.complaints
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
	
	table.on('tool(lostItemComplaintInfos)', function (row) {
		var currentSelectedRow = row.data;
		var layEvent = row.event;
		if (layEvent === 'delete') {
			$.ajax({
				url: baseUrl + 'LostItem/userDeleteLostItem.do',
				data: {
					id: currentSelectedRow.lostItemId,
					userId: localStorage.getItem("userId"),
					complaintCategory: currentSelectedRow.complaintCategory,
					itemCategory: currentSelectedRow.itemCategory,
					itemDate: currentSelectedRow.date
				},
				dataType: 'json',
				type: 'post',
				success: function (res) {
					if (res.status == 'success') {
						layer.msg('删除成功', {time: 3000, icon: 1});
						table.reload('lostItemComplaintInfos', {
							url: baseUrl + 'LostItemComplaint/getAllLostItemComplaints.do',
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
		} else if (layEvent === 'showPictures') {
			$.getJSON(baseUrl + 'LostItemComplaint/getPictureJson.do?lostItemId=' + currentSelectedRow.lostItemId + '&userId=' + localStorage.getItem("userId"), function (json) {
				layer.photos({
					photos: json.json,
					anim: 5
				});
			});
		}
	}) ;
});