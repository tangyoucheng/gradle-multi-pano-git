// 老人名册管理
var treeNodeId = "";
var pId = "";
var level;
var list = {};
var formData = form2js($("#cisc0104FormSearch")[0]);
$(function() {

    // zTree 树形菜单初始化检索
    searchMenu();
    // var zTreeObj = $.fn.zTree.init($("#zTree"), setting, zNodes);
    // zTreeObj;
    var tableList = $('#table-list');

    // bootstrapTable初始化
    tableList.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableList.bootstrapTable('getSelections').length;
    $('#cisc0104Btn_delete').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableList.on(jqueryEvent, function() {
        var selectionsLength = tableList.bootstrapTable('getSelections').length;
        $('#cisc0104Btn_delete').prop('disabled', !selectionsLength);
    });

    // 删除
    $("#cisc0104Btn_delete").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#cisc0104FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map($("#table-list").bootstrapTable('getSelections'), function(row) {
                // 传入后台的唯一识别ID
                return row.listId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('cisc0104/doDelete'),
                success : function(result) {
                    if (result.success) {
                        window.top.layer.alert(result.msg, function(index) {
                            // 关闭当前层
                            window.top.layer.close(index);
                            // 重新检索画面
                            searchData();
                        });
                    }
                }
            // ,
            // error : function() {
            // }
            });
        }, function() {
            // 取消操作
        });

    });
    searchData();
    // 检索
    $("#cisc0104Btn_search").click(function() {
        // debugger; 检测
        // 控制高级查询区域隐藏
        $('.full-search').slideUp();
        formData['name'] = $("#txt_name").val();
        searchData();
    });

    // 共通 高级查询
    // $(".btn-search").click(function() {
    // $('.full-search').slideToggle();
    // $('.fa-angle-double-down').toggle();
    // $('.fa-angle-double-up').toggle();
    // });

    // 同时绑定多个 layui 日期控件
    lay('.date-item').each(function() {
        laydate.render({
            elem : this,
            trigger : 'click'
        });
    });
    // 导出 查询
    $("#cisc0104Btn_excel").click(function() {
        $("#name").val($("#txt_name").val());
        $("#listTypeId").val(treeNodeId);
        outputExcel();
    });

    // 新增
    $("#Platformz9801Btn_new").click(function() {
        var targetUrl = 'Platformz9802/';
        window.top.layer.open({
            title : '新建',
            type : 2,
            closeBtn : 1, // 显示关闭按钮
            area : [ '90%', '90%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });

    // 提交
    $("#Platformz9801Btn_submit").click(function() {
        var targetUrl = 'Platformz9802/';
        window.top.layer.open({
            title : '新建',
            type : 2,
            closeBtn : 1, // 显示关闭按钮
            area : [ '90%', '90%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });
});

// ztree树形菜单-检索
function searchMenu() {
    $.ajax({
        url : getMemberContextPath('/cisc0104/doSearchMenu'),
        type : "post",
        dataType : "json",
        data : {},
        success : function(result) {
            if (result.obj.length > 0) {
                $('#newBtn').hide();
            }
            if (result.success) {
            }
        }
    });
}
// 树形菜单ztree-新增保存
function doSave(str) {
    var targetUrl = 'cisc010401/' + '?listTypeId=' + treeNodeId + '&pId=' + pId;
    var width = '';
    var heigh = '';

    // 新建下级
    if (str == 1) {
        // 第二级
        targetUrl = targetUrl + '&flg=2';
        width = '40%';
        heigh = '40%';
    }
    // 新建同级
    if (str == 0 || str == 2) {
        // 判断新建同级是第一级还是第二级
        if (str == 0) {
            if ('0' == pId || '' == pId) {
                // 第一级
                targetUrl = targetUrl + '&flg=1';
            } else {
                // 第二级
                targetUrl = targetUrl + '&flg=3';
            }
            targetUrl
        }
        width = '40%';
        heigh = '30%';
    }

    // 隐藏 dropdown-menu
    $("#treeContextMenu").hide();
    window.top.layer.open({
        title : '新增名册',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ width, heigh ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            // debugger;
            searchMenu();
        }
    });
}
// 树形菜单ztree-编辑保存
function doUpdate() {
    var targetUrl = 'cisc010402/' + '?listTypeId=' + treeNodeId + '&pId=' + pId;
    if (0 == level) {
        // 父级画面
        targetUrl = targetUrl + '&flg=1';
        width = '40%';
        heigh = '30%';
    } else {
        // 下级画面
        targetUrl = targetUrl + '&flg=2';
        width = '40%';
        heigh = '40%';
    }

    // 隐藏 dropdown-menu
    $("#treeContextMenu").hide();
    window.top.layer.open({
        title : '编辑名册',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ width, heigh ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            searchMenu();
        }
    });
}
// 树形菜单ztree-删除节点
function doZtreeDelete() {

    // 隐藏 dropdown-menu
    $("#treeContextMenu").hide();
    // 询问框
    var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的数据，是否继续？', {
        icon : 3,
        title : '提示信息',
        btn : [ '确认', '取消' ]
    // 按钮
    }, function() { // 确认操作
        $("#treeContextMenu").hide();
        formData['listTypeId'] = treeNodeId;
        $.ajax({
            url : getMemberContextPath('/cisc0104/doZtreeDelete'),
            type : "post",
            dataType : "json",
            data : formData,
            success : function(result) {
                if (result.success) {
                    window.top.layer.alert(result.msg, function(index) {
                        // 关闭当前层
                        window.top.layer.close(index);
                        // 刷新树形菜单
                        searchMenu();
                        // 刷新表格
                        searchData();
                    });
                }
            }
        // ,
        // error : function() {
        // }
        });
    }, function() {
        // 取消操作
    });

}

// 导入添加人员
function doImport() {
    // 隐藏 dropdown-menu
    $("#treeContextMenu").hide();
    var targetUrl = 'cisc010403/?listTypeId=' + treeNodeId
    window.top.layer.open({
        title : '名册管理-添加人员',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ '90%', '90%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            searchMenu();
            // formData = form2js($("#cisc0202FormSearch")[0]);
            formData['listTypeId'] = treeNodeId;
            searchData();
        }
    });
}
// 老人名册管理导出
function outputExcel() {
    var msg = '本操作会导出老人名册管理，是否继续？';
    var formId = "cisc0104FormAjaxSubmit";
    var createFileurl = getMemberContextPath('cisc0104/exportExcel');
    var downloadUrl = getContextPath('/ajaxDownload');
    var downloadFileName = '老人名册管理' + window.top.CommonUtilJs.getCurrentTime() + '.xlsx';
    CommonUtilJs.doOutputExcelConfirm(msg, formId, createFileurl, downloadUrl, downloadFileName);
}
function searchData() {
    // 先销毁表格
    $('#table-list').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-list').bootstrapTable({
        url : getMemberContextPath('cisc0104/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'listId',// 初始化的时候排序的字段
        sortOrder : "desc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            // var formData = form2js($("#cisc0104FormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        uniqueId : "listId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'listId',
        }, {
            field : 'residentId'
        }, {
            field : 'listTypeId'
        }, {
            field : 'cardNo'
        }, {
            field : 'name'
        }, {
            field : 'sex'
        }, {
            field : 'age'
        }, {
            field : 'birthday'
        }, {
            field : 'presentAddress'
        }, {
            field : 'listName',
            cellStyle : function(value, row, index) {
                if (value == "孤寡老人") {
                    return {
                        css : {
                            "color" : "#f65e5e"
                        }
                    }
                } else
                    (value == "90-100岁老人")
                {
                    return {
                        css : {
                            "color" : "#7dc855"
                        }
                    }
                }
            }
        }, {
            field : 'rowOperate',
            events : {
                'click .row-visit' : function(e, value, tableRowInfo, index) {
                    doVisit(tableRowInfo);
                },
            },
            formatter : function(value, tableRowInfo, index) {
                var showData = '';
                // 走访记录
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="cis-text-primary font-14 p-2 row-visit"';
                showData = showData + '>';
                showData = showData + '走访记录';
                showData = showData + '</a>';

                // 走访记录
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="cis-text-primary font-14 p-2 row-visit"';
                showData = showData + '>';
                showData = showData + '走访记录';
                showData = showData + '</a>';

                // 走访记录
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="cis-text-primary font-14 p-2 row-visit"';
                showData = showData + '>';
                showData = showData + '走访记录';
                showData = showData + '</a>';
                return showData;
            }
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};

// 走访记录
function doVisit(tableRowInfo) {
    var targetUrl = 'cisc010404/';
    targetUrl = targetUrl + '?residentId=' + tableRowInfo.residentId + '&name=' + tableRowInfo.name;
    window.top.layer.open({
        title : '走访记录',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ '90%', '90%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            searchData();
        }
    });
}

// 树形ztree setting
var setting = {
    view : {
        addDiyDom : addDiyDom
    },
    check : {
        enable : false
    },
    edit : {
        enable : true
    },
    data : {
        simpleData : {
            enable : true
        }
    },
    callback : {
        // 右键点击
        onRightClick : zTreeOnRightClick,
        // 点击
        onClick : zTreeOnClick
    }
};
var IDMark_A = "_a";
function addDiyDom(treeId, treeNode) {
    if (treeNode.parentNode && treeNode.parentNode.id != 2)
        return;
    var aObj = $("#" + treeNode.tId + IDMark_A);
    var editStr = "<span class='ztree-char  text-primary' id='diyBtn_" + treeNode.id + "' title='" + treeNode.name
            + "'>（" + treeNode.count + "）</span>";
    aObj.after(editStr);
    var btn = $("#diyBtn_" + treeNode.id);
    if (btn)
        btn.bind("click", function() {
            // alert("diy Button for " + treeNode.name);
        });

}

/* 左键点击 */
function zTreeOnClick(event, treeId, treeNode) {
    formData['listTypeId'] = treeNode.id;
    treeNodeId = treeNode.id;
    // 第一节只具备查看
    if (treeNode.level != 0) {
        searchData();
    }
};
/* 树形tree右键编辑 */
function zTreeOnRightClick(event, treeId, treeNode) {
    // alert(treeNode.tId + ", " + treeNode.name);
    var zTreeObj = $.fn.zTree.getZTreeObj("zTree");
    if (treeNode) {
        zTreeObj.selectNode(treeNode);
        // 传入当前选中节点ID值
        treeNodeId = treeNode.id;
        pId = treeNode.pid;
        // showContextMenu(treeNode.organId, treeNode.leaf);
        // dropdown-menu位置
        showContextMenu(treeNode.organId, treeNode.leaf, event.clientX + 160, event.clientY + 10);
        if (1 == treeNode.level) {
            $(".sonlevel").hide();
        }
        if (0 == treeNode.level) {
            $(".importlevel ").hide();
        }
    }
};
/* 树形tree右键编辑 dropdown-menu位置 */
function showContextMenu(type, leaf, x, y) {
    if (type == -1) {
        $(".dropdown-menu").find("li:not(:first)").hide();
    } else if (leaf) {
        $(".dropdown-menu").find("li:first").hide();
    } else {
        $(".dropdown-menu").find("li").show();
    }
    $("#treeContextMenu").css({
        "top" : y + "px",
        "left" : x + "px"
    }).show();
    $("body").on("mousedown", onBodyMouseDown);
}
function hideContextMenu() {
    $("#treeContextMenu").hide();
    $("body").off("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event) {
    if (!(event.target.id == "treeContextMenu" || $(event.target).parents("#treeContextMenu").length > 0)) {
        hideContextMenu();
    }
}
