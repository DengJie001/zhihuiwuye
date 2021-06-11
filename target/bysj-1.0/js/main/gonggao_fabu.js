const baseUrl = 'http://codemata.club:8080/bysj/';	// 请求路径前缀

function submitAnnouncement() {
	var canSubmit = true;	// 是否可以提交公告信息
	var title = $('#titleInput').val();
	var oldContent = $('#contentInput').val();
	var authorId = $('#authorIdInput').val();
	var top = $("input[name='top']:checked").val();
	var tips = '';
	if (title == '') {
		tips = '标题不允许为空;';
		canSubmit = false;
	}
	if (title.length > 32) {
		tips += '标题长度不允许超过32位;';
		canSubmit = false;
	}
	if (oldContent == '') {
		tips += '内容不允许为空;'
		canSubmit = false;
	}
	if (authorId == '') {
		tips += '发布人ID不允许为空;'
		canSubmit = false;
	}
	if (authorId.length > 11) {
		tips += '发布人ID长度不允许超过11位;'
		canSubmit = false;
	}
	var content = oldContent.replace(/\r\n/g, '<br/>'); //.replace(/\n/g, '<br/>');
	
	// 如果canSubmit == false则表示有字段错误,则不允许提交公告信息
	if (!canSubmit) {
		alert(tips);
		return;
	}
	
	// 提交公告信息
	$.ajax({
		url: baseUrl + 'announcement/saveAnnouncement.do',
		data: {
			title: title,
			content: content,
			author: authorId,
			top: top,
			userId: localStorage.getItem("userId")
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
}