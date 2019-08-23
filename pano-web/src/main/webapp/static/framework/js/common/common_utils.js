function CommonUtilJs() {
}

//
// 数値カンマ編集（削除）
//
CommonUtilJs.delComma = function(value) {
    var patern = /,/g;
    var val = "";
    if (value != undefined && value != null) {
        val = value.replace(patern, "");
    }
    return val;
};

//
// 数値カンマ編集（追加）
//
CommonUtilJs.addComma = function(value) {
    var patern = /^([\+\-]?\d+)(\d{3})([\,\d+]*)(\.\d+)?$/;
    var rep = "";
    var val = "";
    if (value != undefined && value != null) {
        val = String(value);
        while (rep != val) {
            rep = val;
            val = rep.replace(patern, "$1,$2$3$4");
        }
    }
    return val;
};

// =========================================================================
// 日付フォーマット編集
// 【 入力 】文字列
// 【返却値】スラッシュ編集した日付文字列
// =========================================================================
CommonUtilJs.addDateFormat = function(value) {
    var before = value;
    var after = "";

    if (before.match("^\\d{8}$")) {
        after += before.substr(0, 4);
        after += "/";
        after += before.substr(4, 2);
        after += "/";
        after += before.substr(6);
    } else if (before.match("^\\d{6}$")) {
        after += before.substr(0, 4);
        after += "/";
        after += before.substr(4);
    } else {
        after = before;
    }
    return after;
};

// =========================================================================
// 日付フォーマット編集（スラッシュ除去）
// 【 入力 】文字列
// 【返却値】スラッシュ除去した日付文字列
// =========================================================================
CommonUtilJs.delDateFormat = function(value) {

    var before = value;
    var after = "";
    var parts = before.split("/");

    if (parts.length == 3) {
        var tmp1 = "00".concat(parts[1]);
        var tmp2 = "00".concat(parts[2]);
        after = parts[0].concat(tmp1.substr(tmp1.length - 2)).concat(tmp2.substr(tmp2.length - 2));
    } else if (parts.length == 2) {
        var tmp1 = "00".concat(parts[1]);
        after = parts[0].concat(tmp1.substr(tmp1.length - 2));
    } else {
        after = before;
    }

    return after;
};

// =========================================================================
// 指定された文字列は整数であるかどうかをチェックする
// 【 入力 】文字列
// 【返却値】判定結果
// =========================================================================
CommonUtilJs.isInteger = function(value) {
    if (value.match(/[^0-9]/g) || parseInt(value, 10) + "" != value) {
        return false;
    } else {
        return true;
    }
};

// =========================================================================
// 指定された文字列は数字であるかどうかをチェックする
// 【 入力 】文字列
// 【返却値】判定結果
// =========================================================================
CommonUtilJs.isNumber = function(input) {
    // return typeof input === 'number' || Object.prototype.toString.call(input)
    // ===
    // '[object Number]';
    return parseFloat(input) === +parseFloat(input);
};
// =========================================================================
// 指定された文字列の前の0を削除する
// 【 入力 】文字列
// 【返却値】結果返却
// =========================================================================
CommonUtilJs.trimLeft0 = function(value) {
    var result = "";
    if (value.length > 1) {
        result = value.replace(/^0*/, "");
    } else {
        result = value;
    }
    if (result == "") {
        result = "0";
    }
    return result;
};

// =========================================================================
// check json type
// 【 入力 】文字列
// 【返却値】結果返却
// =========================================================================
CommonUtilJs.isJson = function(value) {
    var result = false;
    if (value.match("^\{(.+:.+,*){1,}\}$")) {
        result = true;
    }
    return result;
};

// =========================================================================
// 解析ajax验证信息
// 【 入力 】文字列
// 【返却値】結果返却
// =========================================================================
CommonUtilJs.processAjaxSuccessAfter = function(data) {

    // if (!data.match("^\{(.+:.+,*){1,}\}$")) {
    // return true;
    // }
    // var isjson = typeof(data) == "object" &&
    // Object.prototype.toString.call(data).toLowerCase() == "[object object]"
    // && !data.length;
    // if(!isjson){
    // return true;
    // }

    // alert("Data Loaded: " + data);
    var result = eval(data);
    if (typeof (result) === 'string') {
        result = jQuery.parseJSON(result);
    }
    if (result.success == true) {
        // if (result.update) {
        // jQuery.each(result.update, function(index, value) {
        // $('#' + value.id).val(value.value);
        // });
        // }
        if (result.msg && result.msg.length > 0) {
            window.top.showSuccessMessage(result.msg, false, 3);
        }
        return true;
    }
    if (result.success == false) {
        var errorMsgList = [];
        jQuery.each(result.data, function(index, value) {
            var name = value.recordCode;
            var errorMsgInfo = value.recordValue;
            errorMsgList.push(errorMsgInfo);
            if (name || name.length == 0) {
                return true; // false时相当于break, 如果return true 就相当于continure。
            }

            $.each(errorList, function(index, value) {
                var _popover = $(value.element).popover(
                    {
                        trigger : 'manual',
                        offset : '0,0',
                        placement : 'bottom', // 修改popper.js的computeStyle.order属性
                        content : value.message,
                        template : '<div class="popover" role="tooltip">' + '<div class="arrow"></div>'
                                + '<h3 class="popover-header"></h3>' + '<div class="popover-body"></div></div>'
                    });

                _popover.data('bs.popover').config.content = value.message;

                $(value.element).parent("div").addClass('was-validated');
                $(value.element).popover('show');

            });

            // 3 秒后关闭验证提示信息
            popoverTimeout = setTimeout(function() {
                $.each(errorList, function(index, value) {
                    $(value.element).popover('hide');
                });
            }, 3000);

            for ( var eachMsg in errorMsgList) {
                if ($(':input[name="' + name + '"]') != null && $(':input[name="' + name + '"]') != undefined
                        && $(':input[name="' + name + '"]').length > 0) {
                    $(':input[name="' + name + '"]').parent().append(
                        '<div class="imui-validation-error">' + msgsInfo[eachMsg] + '</div>');
                } else {
                    $('#' + name + '').parent().append(
                        '<div class="imui-validation-error">' + msgsInfo[eachMsg] + '</div>');
                }
            }
        });

        // jQuery.merge(errorMsgList, result.msg);
        errorMsgList.push(result.msg);
        if (errorMsgList.length > 0) {
            window.top.showErrorMessage(errorMsgList, false, 3);
        }
        return false;
    }
    // if (result.result == 'WARNING') {
    // window.top.imuiShowWarningMessage(result.errors, [], true, 3000);
    // return false;
    // }
    return true;
};

// =========================================================================
// load mask
// 【 入力 】
// 【返却値】結果返却
// =========================================================================
CommonUtilJs.loadMask = function() {
    $("#imui-container").mask(CdicConstants.VAL_WAITING);
};

// =========================================================================
// remove mask
// 【 入力 】
// 【返却値】結果返却
// =========================================================================
CommonUtilJs.removeMask = function() {
    $("#imui-container").unmask();
};

// =========================================================================
// 現在時刻取得（yyyy/mm/dd hh:mm:ss）
// 【 入力 】
// 【返却値】結果返却
// =========================================================================
CommonUtilJs.getCurrentTime = function() {
    var now = new Date();
    var res = now.getFullYear() + "/";
    res = res + CommonUtilJs.padZero(now.getMonth() + 1) + "/";
    res = res + CommonUtilJs.padZero(now.getDate()) + " ";
    res = res + CommonUtilJs.padZero(now.getHours()) + ":";
    res = res + CommonUtilJs.padZero(now.getMinutes()) + ":";
    res = res + CommonUtilJs.padZero(now.getSeconds());
    return res;
}

// =========================================================================
// 現在時刻取得（yyyy年mm月dd日 hh:mm:ss）
// 【 入力 】
// 【返却値】結果返却
// =========================================================================
CommonUtilJs.getCurrentTimeDATE = function() {
    var now = new Date();
    var res = now.getFullYear() + "年";
    res = res + CommonUtilJs.padZero(now.getMonth() + 1) + "月";
    res = res + CommonUtilJs.padZero(now.getDate()) + "日";
    res = res + CommonUtilJs.padZero(now.getHours()) + "时";
    res = res + CommonUtilJs.padZero(now.getMinutes()) + "分";
    res = res + CommonUtilJs.padZero(now.getSeconds()) + "秒";
    return res;
}

// =========================================================================
// 先頭ゼロ付加
// 【 入力 】
// 【返却値】結果返却
// =========================================================================
CommonUtilJs.padZero = function(num) {
    var result;
    if (num < 10) {
        result = "0" + num;
    } else {
        result = "" + num;
    }
    return result;
}
// 判断下拉框中是否已经存在指定的值
CommonUtilJs.isExistOption = function(id, value) {
    var isExist = false;
    var count = $('#' + id).find('option').length;
    for (var i = 0; i < count; i++) {
        if ($('#' + id).get(0).options[i].value == value) {
            isExist = true;
            break;
        }
    }
    return isExist;
}

// 导出文件确认对话框处理
CommonUtilJs.doOutputExcelConfirm = function(msg, formId, createFileurl, downloadUrl, downloadFileName) {
    // 询问框
    var currentConfirmIndex = window.top.layer.confirm(msg, {
        icon : 3,
        title : '提示信息',
        btn : [ '确认', '取消' ]
    // 按钮
    }, function() {
        window.top.layer.close(currentConfirmIndex);

        // 做成FormData对象
        var formData = new FormData(document.getElementById(formId));

        if (createFileurl != null) {

            var xhr = new XMLHttpRequest();
            xhr.open("POST", createFileurl);
            xhr.responseType = "json";
            xhr.onloadstart = function() {
                window.top.layuiLoading();
            }
            xhr.onload = function() {
                if (this.status == 200) {
                    var result = '';
                    if (typeof this.response === 'string') {
                        result = JSON.parse(this.responseText);
                    } else {
                        result = this.response;
                    }
                    if (result.success == true) {
                        formData.append("ajaxDownloadFileName", result.msg);
                        // 确认操作
                        CommonUtilJs.outputExcelFile(formData, createFileurl, downloadUrl, downloadFileName);
                    } else {
                        window.top.layuiRemoveLoading();
                    }

                } else {
                    window.top.ajaxCommonError(this.status);
                    // window.top.layuiRemoveLoading();
                    // window.top.layer.alert(this.status);
                }
            };
            // 发送请求
            xhr.send(formData);

        } else {
            // 确认操作
            CommonUtilJs.outputExcelFile(formData, createFileurl, downloadUrl, downloadFileName);
        }
    }, function() {
        // 取消操作
    });
}

// 导出excel文件
CommonUtilJs.outputExcelFile = function(formData, createFileurl, downloadUrl, downloadFileName) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", downloadUrl);
    xhr.responseType = "blob";
    xhr.onloadstart = function() {
        if (createFileurl == null) {
            window.top.layuiLoading();
        }
    }
    xhr.onload = function() {
        if (this.status == 200) {
            var blob = this.response;
            if (window.navigator.msSaveOrOpenBlob) {
                // for ie 10 and later
                try {
                    var blobObject = new Blob([ blob ]);
                    window.navigator.msSaveOrOpenBlob(blobObject, downloadFileName);
                } catch (e) {
                    console.log(e);
                }
            } else {

                // 创建Blob对象
                var blob = new Blob([ blob ]);
                var url = window.URL || window.webkitURL;

                // 做成BlobURL
                var blobUrl = url.createObjectURL(blob);

                // A标签做成
                var alink = document.createElement("a");
                // alink.innerHTML = '';
                alink.download = downloadFileName;
                alink.href = blobUrl;

                document.body.appendChild(alink);
                alink.click();
                alink.remove();
            }
        } else {
            window.top.ajaxCommonError(this.status);
            // window.top.layer.alert(this.status, {
            // icon : 6
            // });
        }
    };
    xhr.onloadend = function() {
        window.top.layuiRemoveLoading();
    }

    // 发送请求
    xhr.send(formData);
}

Array.prototype.serializeObject = function(lName) {
    var o = {};
    $t = this;
    for (var i = 0; i < $t.length; i++) {
        for ( var item in $t[i]) {
            if ($t[i][item]) {
                // o[lName + '[' + i + '].' + item.toString()] =
                // $t[i][item].toString();
                 o[lName + '[' + i + '].' + item.toString()] = $t[i][item];
            }
        }
    }
    return o;
};

// 是否是指定的开始字符
String.prototype.startWith = function(str) {
    var reg = new RegExp("^" + str);
    return reg.test(this);
}
// 是否是指定的结束字符
String.prototype.endWith = function(str) {
    var reg = new RegExp(str + "$");
    return reg.test(this);
}

// =========================================================================
// 一览数据操作的下拉式菜单
// 【 入力 】
// 【返却値】結果返却
// =========================================================================
CommonUtilJs.createDropMenu = function(dropMenuItems) {
    var dropMenu = '';
    dropMenu = dropMenu + '<div class="btn-group">';
    dropMenu = dropMenu + '<button type="button" aria-haspopup="true" aria-expanded="false" ';
    dropMenu = dropMenu + 'class="dropdown-toggle btn pano-btn-danger font-12 p-1" ';
    dropMenu = dropMenu + 'data-toggle="dropdown">操作';
    dropMenu = dropMenu + '</button>';
    dropMenu = dropMenu + '<div class="dropdown-menu dropdown-menu-right" style="min-width: auto">';
    dropMenu = dropMenu + dropMenuItems;
    dropMenu = dropMenu + '</div>';
    return dropMenu;
}
