// 日期前后验证
// $("#txt_endDate").rules('add', { greaterEqual :
// $("#txt_startDate").val() });
//数值前后验证
//$("#txt_endNumber").rules('add', { greaterEqual :
//$("#txt_startNumber").val() });

// 大于等于验证
jQuery.validator.addMethod("greaterEqual", function(value, element, params) {
	var dateIsoRegExp=new RegExp('^\\d{4}[\\/\\-](0?[1-9]|1[012])[\\/\\-](0?[1-9]|[12][0-9]|3[01])$');
    if (dateIsoRegExp.test(value)) {
        return params.length == 0 || new Date(value) >= new Date(params);
    }
    return isNaN(value) && isNaN(params) || (Number(value) >= Number(params));
}, '此数据必须大于或等于 {0}');

// 大于验证
jQuery.validator.addMethod("greaterThan", function(value, element, params) {
    var dateIsoRegExp=new RegExp('^\\d{4}[\\/\\-](0?[1-9]|1[012])[\\/\\-](0?[1-9]|[12][0-9]|3[01])$');
    if (dateIsoRegExp.test(value)) {
        return params.length == 0 || new Date(value) > new Date(params);
    }
    return isNaN(value) && isNaN(params) || (Number(value) > Number(params));
}, '此数据必须大于 {0}');

// 身份证验证
jQuery.validator.addMethod("validateIdCard", function(idCard, element) {
    var result = CheckMismatch(element);
    if (result === "dependency-mismatch") {
        return result;
    }
    var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
    // 如果通过该验证，说明身份证格式正确，但准确性还需计算
    if (regIdCard.test(idCard)) {
        if (idCard.length == 18) {
            var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
                    5, 8, 4, 2); // 将前17位加权因子保存在数组里
            var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); // 这是除以11后，可能产生的11位余数、验证码，也保存成数组
            var idCardWiSum = 0; // 用来保存前17位各自乖以加权因子后的总和
            for (var i = 0; i < 17; i++) {
                idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
            }

            var idCardMod = idCardWiSum % 11; // 计算出校验码所在数组的位置
            var idCardLast = idCard.substring(17); // 得到最后一位身份证号码

            // 如果等于2，则说明校验码是10，身份证号码最后一位应该是X
            if (idCardMod == 2) {
                if (idCardLast == "X" || idCardLast == "x") {
                    return true;
                } else {
                    return false;
                }
            } else {
                // 用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if (idCardLast == idCardY[idCardMod]) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
    return false;
}, '身份证号不符合要求,请重新输入');

//社保卡验证
jQuery.validator.addMethod("validateSocialSecurityNo", function(idCard, element) {
    var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
    return CheckMismatch(element) || regIdCard.test(idCard);
}, '社保卡号不符合要求,请重新输入');

// 手机号验证
jQuery.validator.addMethod("validatePhone", function(phone, element) {
    var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
    return CheckMismatch(element) || reg.test(phone);
}, '手机号不符合要求,请重新输入');

// 手机号和固定电话验证
jQuery.validator.addMethod("validateTel", function(tel, element) {
    var reg = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$|^[1][3,4,5,7,8][0-9]{9}$/;
    return  CheckMismatch(element) || reg.test(tel);
}, '电话号码或手机号不符合要求,请重新输入');

// 邮箱验证
jQuery.validator.addMethod("validateEmail", function(email, element) {
    var reg = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
    return CheckMismatch(element) || reg.test(email);
}, '邮箱地址不符合要求,请重新输入');

// 残疾证号
jQuery.validator.addMethod("disabilityNo", function(disabilityNo, element) {
    var reg = /^[0-9]{17}[0-9X][1-7][1-4]$/;
    return CheckMismatch(element) || reg.test(disabilityNo);
}, '残疾证号不符合要求,请重新输入');

// 邮编
jQuery.validator.addMethod("postcode", function(postcode, element) {
    var reg = /^[1-9]\d{5}$/;
    return CheckMismatch(element) || reg.test(postcode);
}, '邮编不符合要求,请重新输入');

// 经纬、维度验证
jQuery.validator.addMethod("validateTotal", function(phone, element) {
    var reg = /^[1-9]{1}\d{2}\.\d{7}$/;
    return CheckMismatch(element) || reg.test(phone);
}, '数据不符合要求,请重新输入');

 // 检证元素是否需要验证
function CheckMismatch(element) {
    var result = jQuery.validator.prototype.optional(element);
    if (result === "dependency-mismatch") {
        $(element).removeClass('is-valid');
        $(element).removeClass('is-invalid');
    }
    return result;
};