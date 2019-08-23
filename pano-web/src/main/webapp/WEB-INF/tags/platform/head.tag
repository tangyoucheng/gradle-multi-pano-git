
<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<title>房屋共享平台</title>
<!-- 移动设备 viewport -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--  360浏览器默认使用Webkit内核  -->
<meta name="renderer" content="webkit">
<!--  禁止搜索引擎抓取  -->
<meta name="robots" content="nofollow">
<!--  禁止百度SiteAPP转码  -->
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="<c:url value='/'/>" target="_self">
<!-- Jquery组件引用 -->
<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>
<!-- popper.js组件引用 -->
<script src="webjars/popper.js/1.15.0/umd/popper.min.js"></script>
<!-- bootstrap组件引用 -->
<link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
<!-- bootstrap.js的AttachmentMap属性,3039行:this.addAttachmentClass(this._getAttachment(data.placement.replace('-',''))); -->
<script src="static/framework/plugins/bootstrap/4.3.1/js/bootstrap.js"></script>
<link href="static/framework/plugins/bootstrap/4.3.1/css/glyphicons.css" rel="stylesheet">
<link href="static/framework/plugins/bootstrap/4.3.1/css/bootstrap_custom.css" rel="stylesheet">
<link href="static/framework/plugins/bootstrap/4.3.1/css/bootstrap_grid_custom.css" rel="stylesheet">
<!-- jquery.validate组件引用 -->
<!-- 修改并扩展jquery.validate.js的destroy方法 -->
<script src="static/framework/plugins/jquery-validation/1.19.0/jquery.validate.js"></script>
<script src="webjars/jquery-validation/1.19.0/dist/additional-methods.min.js"></script>
<script src="webjars/jquery-validation/1.19.0/dist/localization/messages_zh.js"></script>
<script src="static/framework/plugins/jquery-validation/custom-additional-methods.js"></script>
<!-- layui组件引用 -->
<link href="webjars/layui/2.4.5/css/modules/laydate/default/laydate.css" rel="stylesheet">
<link href="webjars/layui/2.4.5/css/layui.css" rel="stylesheet">
<script src="webjars/layui/2.4.5/layui.all.js"></script>
<!-- bootstrap-switch组件引用 -->
<link href="static/framework/plugins/bootstrap-switch/3.3.4/css/bootstrap3/bootstrap-switch.min.css"
    rel="stylesheet">
<script src="static/framework/plugins/bootstrap-switch/3.3.4/js/bootstrap-switch.min.js"></script>
<!-- bootstrap-select组件引用 -->
<link href="webjars/bootstrap-select/1.13.8/css/bootstrap-select.min.css" rel="stylesheet">
<script src="webjars/bootstrap-select/1.13.8/js/bootstrap-select.min.js"></script>
<script src="webjars/bootstrap-select/1.13.8/js/i18n/defaults-zh_CN.min.js"></script>

<!-- bootstrap table组件以及中文包的引用 -->
<link href="static/framework/plugins/bootstrap-table/1.15.4/bootstrap-table.min.css" rel="stylesheet">
<script src="static/framework/plugins/bootstrap-table/1.15.4/bootstrap-table.js"></script>
<link href="static/framework/plugins/bootstrap-table/1.15.4/extensions/page-jump-to/bootstrap-table-page-jump-to.css" rel="stylesheet">
<script src="static/framework/plugins/bootstrap-table/1.15.4/extensions/page-jump-to/bootstrap-table-page-jump-to.min.js"></script>
<script src="static/framework/plugins/bootstrap-table/1.15.4/locale/bootstrap-table-zh-CN.min.js"></script>
<link href="static/framework/plugins/bootstrap-table/bootstrap-table-custom.css" rel="stylesheet">
<!-- bignumber组件的引用 -->
<script src="webjars/bignumber.js/6.0.0/bignumber.min.js"></script>
<!-- accounting组件的引用 -->
<script src="static/framework/plugins/accounting/accounting.min.js"></script>
<!-- font-awesome组件的引用 -->
<link href="webjars/font-awesome/5.8.1/css/all.min.css" rel="stylesheet">
<link href="webjars/font-awesome/5.8.1/css/v4-shims.min.css" rel="stylesheet">
<!-- font-shweb组件的引用 -->
<link href="static/framework/plugins/font-shweb/font-shweb.css" rel="stylesheet">
<link href="static/framework/plugins/font-shweb/font-shweb-custom.css" rel="stylesheet">
<!-- font-cisweb组件的引用 -->
<!-- form2js组件的引用 -->
<script src="static/framework/plugins/form2js/form2js.js"></script>
<script src="static/framework/plugins/form2js/js2form.js"></script>
<!-- bwizard 向导式插件 -->
<link href="static/framework/plugins/bwizard/css/bwizard.min.css" rel="stylesheet">
<!-- 顔色樣式 -->
<link href="static/platform/platform/js/ps99/ps99theme-blue.css">
<!-- 自定义共通组件的引用 -->
<script src="static/framework/js/common/common_utils.js"></script>
<script>
  var laypage = layui.laypage, layForm = layui.form, layer = layui.layer;
  var laydate = layui.laydate;
  //  清除popover处理
  var popoverTimeout;

  $(function() {

    $.ajaxSetup({
      beforeSend : function(jqXHR) {
        window.top.layuiLoading();
      },
      complete : function(jqXHR,textStatus) {
        window.top.layuiRemoveLoading();
      },
      error : function(jqXHR, textStatus, errorThrown) {
        window.top.ajaxCommonError(jqXHR.status);
      }
    });
    // bootstrapTable缺省值设定
    $.extend($.fn.bootstrapTable.defaults, {
        striped : true, // 是否显示行间隔色
        cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination : true, // 是否显示分页（*）
        showJumpto: true, // 跳转到指定页数
        sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
        pageNumber : 1, // 初始化加载第一页，默认第一页
        pageSize : 10, // 每页的记录行数（*）
        pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
        search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch : true,
        showColumns : false, // 是否显示所有的列
        showRefresh : false, // 是否显示刷新按钮
        minimumCountColumns : 2, // 最少允许的列数
        clickToSelect : true, // 是否启用点击选中行
        // height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        showToggle : false, // 是否显示详细视图和列表视图的切换按钮
        cardView : false, // 是否显示详细视图
        detailView : false, // 是否显示父子表,
        onLoadError : function(status, jqXHR) {
            window.top.ajaxCommonError(status);
        }
    });

    // Set jQuery.validate settings for bootstrap integration
    jQuery.validator.setDefaults({
        debug : true,// 防止校验成功后表单自动提交
        showErrors : function(errorMap, errorList) {
          var currentThis = this; 
          $.each(this.successList, function(index, value) {
            $(value).popover('hide');
            $(value).removeClass('is-invalid');
            if(currentThis.optional(value) != "dependency-mismatch"){
                $(value).addClass('is-valid');
            }
            // $(value).parent("div").removeClass('was-validated');
          });

          $.each(errorList, function(index, value) {
            var _popover = $(value.element).popover(
                {
                  trigger : 'manual',
                  offset : '0,0',
                  placement : 'bottomStart', // 修改bootstrap.js的AttachmentMap属性,3039行:this.addAttachmentClass(this._getAttachment(data.placement.replace('-','')));
                  content : value.message,
                  template : '<div class="popover" role="tooltip">' + '<div class="arrow bs-popover-arrow-left"></div>'
                      + '<h3 class="popover-header"></h3>' + '<div class="popover-body"></div></div>'
                });

            _popover.data('bs.popover').config.content = value.message;
            $(value.element).removeClass('is-invalid');
            $(value.element).addClass('is-invalid');

            // $(value.element).parent("div").addClass('was-validated');
            value.element.validity.valid = false;
            $(value.element).popover('show');

          });

          // 3 秒后关闭验证提示信息
          popoverTimeout = setTimeout(function() {
            $.each(errorList, function(index, value) {
              $(value.element).popover('hide');
            });
          }, 3000);

        },
        destroy: function(errorList) { // 修改并扩展jquery.validate.js的destroy方法
          if(popoverTimeout){
            clearTimeout(popoverTimeout);
          }
          $.each(errorList, function(index, value) {
            $(value.element).popover('hide');
            $(value.element).removeClass('is-invalid');
            // $(value.element).parent("div").removeClass('was-validated');
          });
        }
    });
    //必填项的星号设定
    $.each($('input[required],textarea[required],select[required]'), function(index, value) {
      if ($(value).prev().length == 1 && $(value).prev()[0].nodeName == 'SPAN') {
          var spanObject = $(value).prev();
          spanObject.html(spanObject.html() + '<span style="color:#dc3545;font-weight: 700;">*</span>');
      } else {
          var labelObject = $(value).parent().prev();
          labelObject.html(labelObject.html() + '<span style="color:#dc3545;font-weight: 700;">*</span>');
      }
    });　
  });
</script>
<!-- 自定义组件的引用 -->
<link href="static/platform/common/css/common.css" rel="stylesheet">
