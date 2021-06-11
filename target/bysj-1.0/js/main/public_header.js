const baseUrl = 'http://codemata.club:8080/bysj/';

function logout() {
	$.ajax({
		url: baseUrl + 'login/managerLogout.do',
		data: {
			userId: localStorage.getItem("userId"),
			managerId: localStorage.getItem("userId")
		},
		dataType: 'json',
		type: 'post',
		success: function (res) {
			localStorage.removeItem("userId");
		},
		error: function (res) {
			console.log(res);
		}
	})
}

function getManagerInfo() {
	$.ajax({
		url: baseUrl + 'manager/getManagerInfo.do',
		data: {
			managerId: localStorage.getItem("userId"),
			userId: localStorage.getItem("userId")
		},
		type: 'post',
		dataType: 'json',
		success: function (res) {
			if (res.status == 'success') {
				$('#managerName').html(res.data.managerName)
			}
		},
		error: function (res) {
			console.log(res);
			alert('预期之外的错误!');
		}
	});
}