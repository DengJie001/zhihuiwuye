var imageFiles;
function submitHouseInfo() {
	var canSubmit = true;
	var address = $('#addressInput').val();
	var managerId = $('#managerIdInput').val();
	var price = $('#priceInput').val();
	var category = $('input:radio[name="house_category"]:checked').val();
	var description = $('#descriptionArea').val();
	
	// 验证输入的地址信息是否合法
	if (address == '' || address == null || address == undefined) {
		showAddressInputTip('地址不允许为空');
		canSubmit = false;
	} else if (address.length > 64) {
		showAddressInputTip('输入的地址长度过长,不允许超过64位');
		canSubmit = false;
	} else {
		hideAddressInputTip();
	}
	
	// 验证输入的管理员id是否合法
	if(managerId == '' || managerId == null || managerId == undefined) {
		showManagerIdInputTip('管理员id不允许为空');
		canSubmit = false;
	} else if(managerId.length > 11) {
		showManagerIdInputTip('管理员id不允许超过11位');
		canSubmit = false;
	} else {
		hideManagerIdInputTip();
	}
	
	// 验证价格输入是否合法
	if (price == '' || price == null || price == undefined) {
		showPriceInputTip('价格不允许为空');
		canSubmit = false;
	} else {
		hidePriceInputTip();
	}
	
	// 验证房产描述输入是否合法
	if (description == '' || description == null || description == undefined) {
		$('#descriptiom-tip').html('房产描述不允许为空');
		$('#descriptiom-tip').removeAttr('hidden');
		canSubmit = false;
	} else {
		$('#descriptiom-tip').attr('hidden', true);
	}
	
	// 如果有任意一个输入不合法 则退出方法 不执行提交
	if (!canSubmit) {
		return;
	}
	
	// 执行提交图片
	for (let i = 0; i < imageFiles.length; ++i) {
		doSubmitPicture(i);
	}
}

/**
 * @param {Object} num
 * @author DengJie
 * @date 2021-03-08
 * @description 上传照片到服务器,num为需要提交照片列表的下标
 */
function doSubmitPicture(num) {
	var formData = new FormData();
	formData.append("pictures", imageFiles[num]);
	$.ajax({
		url: 'http://localhost:8080/bysj/manager/uploadManagerAvatar.do',
		type: 'post',
		cache: false,
		data: formData,
		processData: false,
		contentType: false,
		dataType: 'text',
		success: function (res) {
			console.log(res);
		},
		error: function (res) {
			console.log(res);
			console.log("失败");
		}
	});
}

/**
 * @param {Object} msg
 * @author DengJie
 * @date 2021-03-05
 * @description 显示地址输入框的错误提示
 */
function showAddressInputTip(msg) {
	$('#address-tip').html(msg);
	$('#address-tip').removeAttr('hidden');
}

/**
 * @author DengJie
 * @date 2021-03-05
 * @description 隐藏地址输入框的错误提示
 */
function hideAddressInputTip() {
	$('#address-tip').attr('hidden', true);
}

/**
 * @param {Object} msg
 * @date 2021-03-05
 * @author DengJie
 * @description 显示管理员id输入框的错误提示
 */
function showManagerIdInputTip(msg) {
	$('#managerId-tip').html(msg);
	$('#managerId-tip').removeAttr('hidden');
}

/**
 * @author DengJie
 * @date 2021-03-05
 * @description 隐藏管理员id输入框的错误提示
 */
function hideManagerIdInputTip() {
	$('#managerId-tip').attr('hidden', true);
}

/**
 * @param {Object} msg
 * @author DengJie
 * @date 2021-03-05
 * @description 显示价格输入框的错误提示
 */
function showPriceInputTip(msg) {
	$('#price-tip').html(msg);
	$('#price-tip').removeAttr('hidden')
}

/**
 * @author DengJie
 * @date 2021-03-05
 * @description 隐藏价格输入框的错误提示
 */
function hidePriceInputTip() {
	$('#price-tip').attr('hidden', true);
}

function selectImage(file) {
	imageFiles = $(file)[0].files;
	console.log(imageFiles);
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
		$('#pictureContainer').append(img);
	}
}