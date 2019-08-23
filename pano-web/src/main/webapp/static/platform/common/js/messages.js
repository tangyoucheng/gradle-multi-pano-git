$(function() {
	
	$("#modalDismiss").click(function(){
		$("#controlTextarea").val("");
	});
	
	$("#modalClose").click(function(){
		$("#controlTextarea").val("");
	});
	
	// 新增信息
	$("#saveMessage").click(function(){
		var comment = $("#controlTextarea").val();
		$("#controlTextarea").val("");
		if(comment){
			// 修改留言
			if( editMessage ){
				// 修改List中的留言
				saveMessage[saveMessage.indexOf(editMessage)].comment = comment;
				// 生成显示信息
				generateLi(saveMessage[saveMessage.indexOf(editMessage)]);
			}else{
				var message = {
						leaveMessageId : null,
						tableName: tableName,
						tableKey: tableKey,
						identifyFlag: identifyFlag,
						comment:comment,
						createUserId : userInfo.userId,
						createDate : new Date(),
						deleteFlag : false,
					};
				generateLi(message);
				saveMessage.push(message);
			}
		}
	});
})


var userInfo = {};

var messageCanEditFlag = true;

var saveMessage = [];

var editMessage = null;

var tableName = "";

var tableKey = "";

var identifyFlag ="";

// 初始化留言页面
function initMessage(tn,tK,ifg,user,canEdit){
	tableName = tn;
	tableKey = tK;
	identifyFlag = ifg;
	userInfo = user;
	messageCanEditFlag = canEdit;
	// 非编辑页隐藏按钮
	if(!messageCanEditFlag){
		$("#addMessage").hide();
	}
	queryMessages();
}

// 初始化页面数据
function queryMessages(){
	$("#messagesBox").html('');
	saveMessage = [];
	editMessage = null;
	
    var formData = new FormData();
    formData.append('tableName',tableName);
    formData.append('tableKey',tableKey);
    formData.append('identifyFlag',identifyFlag);
	
    $.ajax({
        type : 'post',
        processData: false,
        contentType: false,
        data : formData,
        dataType : 'json',
        url : '/cis/message/queryMessages',
        success : function(result) {
        	// 返回结果
        	if(result.msg == "success"){
        		for(var message of result.data){
        			message.createDate = new Date(message.createDate);
        			message.comment = message.conment;
        			generateLi(message);
    				saveMessage.push(message);
        		}
        	}
        }
    });
	
}

// 信息保存
function saveAllMessageToDb(tableName,tableKey,identifyFlag){
	this.tableName = tableName;
	this.tableKey = tableKey;
	this.identifyFlag = identifyFlag;
    var formData = new FormData();
    for(var msg of saveMessage){
        formData.append('saveMessages['+ saveMessage.indexOf(msg) +'].leaveMessageId',msg.leaveMessageId);
        formData.append('saveMessages['+ saveMessage.indexOf(msg) +'].tableName',tableName);
        formData.append('saveMessages['+ saveMessage.indexOf(msg) +'].tableKey',tableKey);
        formData.append('saveMessages['+ saveMessage.indexOf(msg) +'].identifyFlag',identifyFlag);
        formData.append('saveMessages['+ saveMessage.indexOf(msg) +'].conment',msg.comment);
        formData.append('saveMessages['+ saveMessage.indexOf(msg) +'].createUserId',msg.createUserId);
        formData.append('saveMessages['+ saveMessage.indexOf(msg) +'].createDate',msg.createDate);
        formData.append('saveMessages['+ saveMessage.indexOf(msg) +'].deleteFlag',msg.deleteFlag);
    }
    formData.append('userId',userInfo.userId);
	
    $.ajax({
        type : 'post',
        processData: false,
        contentType: false,
        data : formData,
        dataType : 'json',
        url : '/cis/message/saveMessages',
        success : function(result) {
        	// 保存完成后重置页面li
//        	if(result.msg == "success"){
//            	queryMessages();
//        	}
			allMessageSaveDown();
        }
    });
}

function editMsg(time){
	// 找到修改的数据
	var dt = Number(time);
	editMessage = saveMessage.find(ms => {
		return ms.createDate.getTime() == dt;
	});
	$("#controlTextarea").val(editMessage.comment);
	$('#exampleModalCenter').modal('show')
}

function deleteMsg(time){
	// 找到修改的数据
	var dt = Number(time);
	editMessage = saveMessage.find(ms => {
		return ms.createDate.getTime() == dt;
	});
	// 修改List中的留言
	// 如果留言有ID，修改FLAG。如果没有ID删除SAVELIST中的数据
	if(editMessage.leaveMessageId){
		saveMessage[saveMessage.indexOf(editMessage)].deleteFlag = true;
		$('#'+dt).remove();
		editMessage = null;
	}else{
		saveMessage.splice(saveMessage.indexOf(editMessage),1);
		$('#'+dt).remove();
		editMessage = null;
	}
}

// 生成一行
function generateLi(message){
	var messageli = "";
	messageli += '<li id="'+ message.createDate.getTime() +'">';
	messageli += '<div class="layui-timeline-content layui-text">';
	messageli += '<div style="margin-top:20px;" class="layui-timeline-title">';
	messageli += '<span style="margin-left:-30px;margin-right: 20px;">';
	// 后台查询的消息使用后台名称
	messageli += '<span style="width:200px;display:inline-block;border-right:1px solid #1296db;">';
	if(message.leaveMessageId){
		messageli += message.createUserName;
	}else{
		messageli += userInfo.userName;
	}
	messageli += '</span>&nbsp;&nbsp;&nbsp;&nbsp;';
	messageli += formatDate(message.createDate);
	messageli +=  ' 填写了备注：' + message.comment;
	messageli += '</span>';
	// 通过登录者以及messageCanEditFlag判断是否能编辑
	if(messageCanEditFlag && message.createUserId == userInfo.userId){
		messageli += '<a class="cis-text-primary font-14 p-2 row-visit" style="cursor: pointer;" onclick="editMsg(';
		messageli += "'"+ message.createDate.getTime() +"'";
		messageli += ')">编辑</a>';
		messageli += '<a class="cis-text-primary font-14 p-2 row-visit" style="cursor: pointer;" onclick="deleteMsg('
		messageli += "'"+ message.createDate.getTime() +"'";
		messageli += ')">删除</a>';
	}
	messageli += '</div>';
	messageli += '</div>';
	messageli += '</li>'; 
	// 编辑状态时 删除之前标签，插入新标签
	if(editMessage){
		$("#"+message.createDate.getTime()).after(messageli);
		$("#"+message.createDate.getTime()).remove();
		editMessage = null;
	}else{
		// 新建时在后面插入
		$("#messagesBox").append(messageli);
	}
}



function formatDate(date){
	var strDate = "";
	if(date){
		strDate += date.getFullYear() + '-';
		strDate += (date.getMonth()+1) + '-';
		strDate += date.getDate() + ' ';
		strDate += date.getHours() + ':';
		strDate += date.getMinutes() + ':';
		strDate += date.getSeconds() + ' ';
	}
	return strDate;
}