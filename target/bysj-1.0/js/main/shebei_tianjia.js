var imageFiles = [];
var baseUrl = 'http://codemata.club:8080/bysj/equipment/'
var imagesPath = ''; // 图片上传后返回的可访问的网络路径
/**
 * @param {Object} file
 * @author DengJie
 * @date 2021-03-10
 * @description 选择图片并且预览
 */
function selectPictures(file) {
	// imageFiles = $(file)[0].files;
	for (let i = 0; i < $(file)[0].files.length; ++i) {
		imageFiles.push($(file)[0].files[i]);
	}
	$('#pictureContainer').html('');
	for (let i = 0; i < imageFiles.length; ++i) {
		filePath = imageFiles[i].name;
		fileFormat = filePath.split('.')[1].toLowerCase();
		src = window.URL.createObjectURL(imageFiles[i]);
		if (!fileFormat.match(/png|jpg|jpeg|gif/)) {
			alert('图片格式必须为:png/jpg/jpeg/gif');
		}
		var img = document.createElement('img');
		img.length = 100;
		img.width = 100;
		img.src = src;
		img.style = 'margin-left:5px;'
		$('#pictureContainer').append(img);
	}
}

/**
 * @param {Object} num
 * @author DengJie
 * @date 2021-03-10
 * @description 上传图片到服务端,num为需要上传图片列表的下标
 */
function doPicturesSubmit(num) {
	var formData = new FormData();
	formData.append('pictures', imageFiles[num]);
	formData.append('userId', localStorage.getItem("userId"));
	$.ajax({
		url: baseUrl + 'uploadEquipmentPictures.do',
		data: formData,
		type: 'post',
		dataType: 'json',
		contentType: false,
		processData: false,
		cache: false,
		async: false,
		success: function(res) {
			console.log(res);
			if (res.msgId >= 0) {
				imagesPath = imagesPath + '+' + res.msgContent;
			} else {
				layui.layer.msg(res.msgContent, {time: 3000, icon: 2});
			}
		},
		error: function(res) {
			layui.layer.msg('上传图片时发生了错误', {time: 3000, icon: 2});
		}
	})
}

/**
 * @author DengJie
 * @date 2021-03-10
 * @description 提交设备信息
 */
function submitEquipmentInfo() {
	var canSubmit = true;
	var equipmentName = $('#nameInput').val();
	var managerId = $('#managerIdInput').val();
	var equipmentPrice = $('#priceInput').val();
	var equipmentBrand = $('#brandInput').val();
	var equipmentType = $('#typeInput').val();
	var equipmentQuantity = $('#quantityInput').val();
	var buyDate = $('#dateSelector').val();

	// 验证各项字段格式是否合法
	// 验证设备名称字段是否有错误
	if (equipmentName == '' || equipmentName == null) {
		$('#nameTip').html('设备名称不允许为空');
		$('#nameTip').removeAttr('hidden');
		canSubmit = false;
	} else if (equipmentName.length > 32) {
		$('#nameTip').html('设备名称字数不允许超过32');
		$('#nameTip').removeAttr('hidden');
		canSubmit = false;
	} else {
		$('#nameTip').attr('hidden', true);
	}

	// 验证管理员id格式是否有错误
	if (managerId == '' || managerId == null) {
		$('#managerIdTip').html('负责人id不允许为空');
		$('#managerIdTip').removeAttr('hidden');
		canSubmit = false;
	} else if (managerId.length > 11) {
		$('#managerIdTip').html('管理员id不允许超过11位');
		$('#managerIdTip').removeAttr('hidden');
		canSubmit = false;
	} else {
		$('#managerIdTip').attr('hidden', true);
	}

	// 验证价格字段格式是否有误
	if (equipmentPrice == '' || equipmentPrice == null) {
		$('#priceTip').html('价格不允许为空');
		$('#priceTip').removeAttr('hiddem');
		canSubmit = false;
	} else if (equipmentPrice < 0) {
		$('#priceTip').html('价格不允许小于0');
		$('#priceTip').removeAttr('hidden');
		canSubmit = false;
	} else {
		$('#priceTip').attr('hidden', true);
	}

	// 验证设备品牌字段是否有错误
	if (equipmentBrand == null || equipmentBrand == '') {
		$('#brandTip').html('设备品牌不允许为空');
		$('#brandTip').removeAttr('hidden');
		canSubmit = false;
	} else if (equipmentBrand.length > 32) {
		$('#brandTip').html('设备品牌字数不允许超过32');
		$('#brandTip').removeAttr('hidden');
		canSubmit = false;
	} else {
		$('#brandTip').attr('hidden', true);
	}

	// 验证设备型号字段是否有错误
	if (equipmentType == '' || equipmentType == null) {
		$('#typeTip').html('设备型号不允许为空');
		$('#typeTip').removeAttr('hidden');
		canSubmit = false;
	} else if (equipmentType.length > 32) {
		$('#typeTip').html('设备型号不允许超过32位');
		$('#typeTip').removeAttr('hidden');
		canSubmit = false;
	} else {
		$('#typeTip').attr('hidden', true);
	}

	// 验证设备数量字段格式是否有错误
	if (equipmentQuantity == null || equipmentQuantity == '') {
		$('#quantityTip').html('设备数量不允许为空');
		$('#quantityTip').removeAttr('hidden');
		canSubmit = false;
	} else if (equipmentQuantity < 0) {
		$('#quantityTip').html('数量值不允许小于0');
		$('#quantityTip').removeAttr('hidden');
		canSubmit = false;
	} else if (Math.floor(equipmentQuantity) != equipmentQuantity) {
		$('#quantityTip').html('设备数量必须为整数');
		$('#quantityTip').removeAttr('hidden');
		canSubmit = false;
	} else {
		$('#quantityTip').attr('hidden', true);
	}

	// 如果存在格式不正确的字段 阻止提交表单
	if (!canSubmit) {
		return;
	}

	for (let i = 0; i < imageFiles.length; ++i) {
		doPicturesSubmit(i);
	}
	
	// 数据封装
	// 如果用户没有选择设备的购买时间,就默认为当天并且将日期格式化为yyyy-MM-dd的形式
	if (buyDate == '' || buyDate == null) {
		var date = new Date();
		var realYear = date.getFullYear();
		var realDay = date.getDate();
		var realMonth;
		if (date.getMonth() <= 8) {
			realMonth = '0' + (parseInt(date.getMonth()) + 1)
		} else {
			realMonth = '' + (parseInt(date.getMonth()) + 1);
		}
		buyDate = realYear + '-' + realMonth + '-' + realDay;
	}
	var equipment = {
		equipmentName: equipmentName,
		equipmentPrice: equipmentPrice,
		equipmentBrand: equipmentBrand,
		equipmentType: equipmentType,
		equipmentQuantity: equipmentQuantity,
		equipmentPicture: imagesPath,
		managerId: managerId,
		buyDate: buyDate
	};
	console.log(equipment);
	
	// 执行提交
	$.ajax({
		url: baseUrl + 'addEquipment.do',
		data: {
			equipment:JSON.stringify(equipment),
			userId: localStorage.getItem("userId")
		},
		type: 'post',
		dataType: 'json',
		success: function (res) {
			if (res.msgId > 0) {
				layui.layer.msg(res.msgContent, {time: 3000, icon: 1});
				// TODO 刷新页面
			} else {
				layui.layer.msg(res.msgContent, {time: 3000, icon: 2});
			}
		},
		error: function (res) {
			layui.layer.msg('发生了预期之外的错误', {time: 3000, icon: 2});
		}
	});
}


function test() {
	var buyDate = '';
	console.log(buyDate);
}