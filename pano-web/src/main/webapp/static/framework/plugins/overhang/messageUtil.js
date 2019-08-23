/**
 * 指定的信息显示。 <br/> <br/>
 * 
 * @param {String|String[]}
 *            [detail] 显示的信息
 * @param {Boolean}
 *            [closable=true] 关闭按钮是否显示的标识。duration小于等于0的场合，强化设定为true。 (true:
 *            显示关闭按钮、false: 不显示关闭按钮）
 * @param {Number}
 *            [duration=10] 信息显示区域自动关闭的时间。0 的场合不自动关闭。 (s)
 * @param {Boolean}
 *            [escape=false] 信息是否被转义的标识。(true: 要转义、false: 不转义）
 * 
 * @query // 显示为字符串的场合 showSuccessMessage('处理成功了。');
 * @query // 显示为数组的场合 showSuccessMessage( ['处理成功了。', '邮箱：xxx@xxx.xxx.xxx']);
 * @query // 显示关闭按钮的场合 showSuccessMessage('处理成功了。', [], true, 0);
 * @query // 显示信息需要转义的场合 showSuccessMessage('&lt;sample.png&gt;的处理成功了', true,
 *          10, true);
 */
function showSuccessMessage(detail, closable, duration, escape) {
    var options = {};
    options.type = "success";
    if (typeof (detail) === 'string') {
        options.message = detail;
    }
    if (Object.prototype.toString.call(detail) === '[object Array]') {
        options.detail = detail;
    }
    
    if (typeof (closable) === 'boolean') {
        options.closeConfirm = closable;
    }
    if (typeof (duration) === 'number' && duration > 0) {
        options.duration = duration;
    }
    if (typeof (duration) === 'number' && duration <= 0) {
        options.closeConfirm = true;
    }
    if (typeof (escape) === 'boolean') {
        options.html = escape;
    }
    
    $(top.document.body).overhang(options);
}

/**
 * 指定的错误信息显示。 <br/> <br/> detail 为 String、或者 String 的数组。 <br/>
 * 
 * @param {String|String[]}
 *            [detail] 显示的信息
 * @param {Boolean}
 *            [closable=true] 关闭按钮是否显示的标识。duration小于等于0的场合，强化设定为true。 (true:
 *            显示关闭按钮、false: 不显示关闭按钮）
 * @param {Number}
 *            [duration=10] 信息显示区域自动关闭的时间。0 的场合不自动关闭。 (s)
 * @param {Boolean}
 *            [escape=false] 信息是否被转义的标识。(true: 要转义、false: 不转义）
 * 
 * @query // 显示为字符串的场合 showErrorMessage('处理失败了。');
 * @query // 显示为数组的场合 showErrorMessage( ['请和管理员联系', '邮箱：xxx@xxx.xxx.xxx']);
 * @query // 显示关闭按钮的场合 showErrorMessage('处理失败了。', [], true, 0);
 * @query // 显示信息需要转义的场合 showErrorMessage('&lt;sample.png&gt;的处理失败了', true,
 *          10, true);
 */
function showErrorMessage(detail, closable, duration, escape) {
    var options = {};
    options.type = "error";
    if (typeof (detail) === 'string') {
        options.message = detail;
    }
    if (Object.prototype.toString.call(detail) === '[object Array]') {
        options.detail = detail;
    }
    if (typeof (closable) === 'boolean') {
        options.closeConfirm = closable;
    }
    if (typeof (duration) === 'number' && duration > 0) {
        options.duration = duration;
        options.closeConfirm = closable;
    }
    if (typeof (duration) === 'number' && duration <= 0) {
        options.closeConfirm = true;
    }
    if (typeof (escape) === 'boolean') {
        options.html = escape;
    }
    $(top.document.body).overhang(options);
}

/**
 * 指定的警告信息显示。 <br/> <br/> detail 为 String、或者 String 的数组。 <br/>
 * 
 * @param {String|String[]}
 *            [detail] 显示的信息
 * @param {Boolean}
 *            [closable=true] 关闭按钮是否显示的标识。duration小于等于0的场合，强化设定为true。 (true:
 *            显示关闭按钮、false: 不显示关闭按钮）
 * @param {Number}
 *            [duration=10] 信息显示区域自动关闭的时间。0 的场合不自动关闭。 (s)
 * @param {Boolean}
 *            [escape=false] 信息是否被转义的标识。(true: 要转义、false: 不转义）
 * 
 * @query // 显示为字符串的场合 showWarningMessage('处理失败了。');
 * @query // 显示为数组的场合 showWarningMessage( ['请和管理员联系', '邮箱：xxx@xxx.xxx.xxx']);
 * @query // 显示关闭按钮的场合 showWarningMessage('处理失败了。', [], true, 0);
 * @query // 显示信息需要转义的场合 showWarningMessage('&lt;sample.png&gt;的处理失败了', true,
 *          10, true);
 */
function showWarningMessage(detail, closable, duration, escape) {
    var options = {};
    options.type = "warn";
    if (typeof (detail) === 'string') {
        options.message = detail;
    }
    if (Object.prototype.toString.call(detail) === '[object Array]') {
        options.detail = detail;
    }
    if (typeof (closable) === 'boolean') {
        options.closeConfirm = closable;
    }
    if (typeof (duration) === 'number' && duration > 0) {
        options.duration = duration;
    }
    if (typeof (duration) === 'number' && duration <= 0) {
        options.closeConfirm = true;
    }
    if (typeof (escape) === 'boolean') {
        options.html = escape;
    }

    $(top.document.body).overhang(options);

}
