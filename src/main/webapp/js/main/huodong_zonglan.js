const baseUrl = 'http://localhost:8080/bysj/';

layui.use(['table', 'form', 'layer'], function () {
	var table = layui.table;
	var $ = layui.jquery;
	var layer = layui.layer;
	var form = layui.form;
	table.render({
		title: '活动信息总览表',	// 表格名称
		elem: '#activityInfos',	// 表格元素
		url: baseUrl,	// 数据接口
		method: 'POST',	// 请求方式
		toolbar: '',	// 头部工具栏
		defaultToolBar: ['filter', 'exports', 'print'],	// 表格右侧工具栏
		page: true,	// 开启分页
		limit: 10,	// 初始化每页记录条数
		limits: ['10', '20', '30', '40', '50'],	// 可选每页条数
		cols: [	// 表头
			[
				{
					type: 'checkbox',
					fix: 'left'
				},
				{
					field: 'applicationId',
					title: '申请记录ID',
					align: 'center'
				},
				{
					field: 'placeId',
					title: '场地ID',
					align: 'center'
				},
				{
					field: 'applicationUser',
					title: '申请人',
					align: 'center'
				},
				{
					field: 'applicationReason',
					title: '申请原因',
					align: 'center'
				},
				{
					field: 'applicationResult',
					title: '申请结果',
					align: 'center'
				},
				{
					field: 'applicationDate',
					title: '申请日期',
					align: 'center'
				},
				{
					field: 'beginDate',
					title: '开始使用时间',
					align: 'center'
				},
				{
					field: 'endDate',
					title: '结束使用时间',
					align: 'center'
				},
				{
					field: 'cost',
					title: '费用',
					align: 'center'
				}
			]
		],
		request: {
			pageName: 'page',
			limitName: 'limit'
		}
	});
});