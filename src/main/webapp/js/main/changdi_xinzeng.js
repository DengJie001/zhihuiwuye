var imageFiles = [];
var baseUrl = 'http://codemata.club:8080/bysj/';
var imagesPath = '';

/**
 * @author DengJie
 * @param {Object} file
 * @date 2021-04-08
 * @description 选择图片并且预览
 */
function selectPlacePictures(file) {
	for (let i = 0; i < $(file)[0].files.length; ++i) {
		imageFiles.push($(file)[0].files[i]);
	}
	console.log(imageFiles);
	$('#pictureContainer').html('');
	for (let i = 0; i < imageFiles.length; ++i) {
		filePath = imageFiles[i].name;
		fileFormat = filePath.split('.')[1].toLowerCase();
		src = window.URL.createObjectURL(imageFiles[i]);
		if (!fileFormat.match(/png|jpg|jpeg|PNG|JPG|JPEG/)) {
			layui.layer.msg('图片格式必须为:png/jpg/jpeg', {time: 3000, icon: 2});
		}
		var img = document.createElement('img');
		img.length = 100;
		img.width = 100;
		img.src = src;
		img.style = 'margin-left:5px;';
		$('#pictureContainer').append(img);
	}
	for (let i = 0; i < imageFiles.length; ++i) {
		doPicturesSubmit(i);
	}
}

/**
 * @author DengJie
 * @param {Object} index
 * @date 2021-04-08
 * @description 上传图片到服务端,index为图片所在图片列表的下标
 */
function doPicturesSubmit(index) {
	var formData = new FormData();
	formData.append('pictures', imageFiles[index]);
	formData.append('userId', localStorage.getItem("userId"));
	$.ajax({
		url: baseUrl + 'PlaceInfo/uploadPlaceInfoPicture.do',
		data: formData,
		type: 'post',
		dataType: 'json',
		contentType: false,
		processData: false,
		cache: false,
		async: false,
		success: function (res) {
			if (res.msgId >= 0) {
				imagesPath = imagesPath + '+' + res.msgContent;
			} else {
				layui.layer.msg(res.msgContent, {time: 3000, icon: 2});
			}
		},
		error: function (errorRes) {
			layui.layer.msg('上传图片时发生了错误', {time: 3000, icon: 2});
		}
	});
}

/**
 * @author DengJie
 * @date 2021-04-08
 * @description 提交场地信息到后端保存
 */
function submitPlaceInfo() {
	var canSubmit = true;
	var tips = '';
	// 封装为后端所需要的PlaceInfo对象
	var placeInfo = {
		managerId: $('#managerIdInput').val(),
		placePrice: $('#placePriceInput').val(),
		placeArea: $('#placeAreaInput').val(),
		placeDescription: $('#placeDescriptionInput').val(),
		placeStatus: $("input[name='placeStatus']:checked").val(),
		placePicture: imagesPath
	};
	
	// 验证管理员ID不允许超过11位
	if (placeInfo.managerId.length > 11) {
		canSubmit = false;
		$('#managerIdInput').css('borderColor', 'red');
		tips += '管理员ID长度不允许超过11位;<br>'
	} else {
		$('#managerIdInput').css('borderColor', '');
	}
	
	// 验证管理员ID不允许为空
	if (placeInfo.managerId == '') {
		canSubmit = false;
		tips += '管理员ID不允许为空;<br>'
		$('#managerIdInput').css('borderColor', 'red');
	} else {
		$('#manangerIdInput').css('borderColor', '');
	}
	
	// 验证价格不允许为空
	if (placeInfo.placePrice == '') {
		canSubmit = false;
		tips += '租用价格不允许为空;<br>'
		$('#placePriceInput').css('borderColor', 'red');
	} else {
		$('#placePriceInput').css('borderColor', '');
	}
	
	// 验证面积不为空
	if (placeInfo.placeArea == '') {
		canSubmit = false;
		tips += '使用面积不允许为空;<br>';
		$('#placeAreaInput').css('borderColor', 'red');
	} else {
		$('#placeAreaInput').css('borderColor', '');
	}
	
	// 验证场地介绍不为空
	if (placeInfo.placeDescription == '') {
		canSubmit = false;
		tips += '场地介绍不允许为空;<br>';
		$('#placeDescriptionInput').css('borderColor', 'red');
	} else {
		$('#placeDescriptionInput').css('borderColor', '');
	}
	
	// 如果canSubmit为false则表示有字段格式不正确 阻止提交
	if (!canSubmit) {
		layer.open({
			title: '提示',
			content: tips
		});
		return;
	}
	
	// 先将所有的图片提交保存到服务端
	for (let i = 0; i < imageFiles.length; ++i) {
		doPicturesSubmit(i);
	}
	
	// 提交其他非图片信息到后端进行保存
	$.ajax({
		url: baseUrl + 'PlaceInfo/savePlaceInfo.do',
		data: {
			placeInfo: JSON.stringify(placeInfo),
			userId: localStorage.getItem("userId")
		},
		type: 'post',
		dataType: 'json',
		success: function (res) {
			if (res.msgId > 0) {
				layer.msg(res.msgContent, {time: 3000, icon: 1});
			} else {
				layer.msg(res.msgContent, {time: 3000, icon: 2});
			}
			$('#placeInfoForm')[0].reset();
		},
		error: function (errorRes) {
			layer.msg('发生了预期之外的异常!', {time: 3000, icon: 2});
		}
	});
}