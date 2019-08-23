$(function() {
	if($("#nodeExtendJson").val()){
		buttons.initButtons(eval("(" + $("#nodeExtendJson").val() + ")"));
	}
	buttons.setHeadText();
})
var buttons = {
		buttonBoxNode : $("#workFlowButtons"),
		nodeExtend: null,
		initButtons : function(nodeExtendJson){
			if(nodeExtendJson){
				buttons.nodeExtend = nodeExtendJson;
				for(var buttonInfo of buttons.nodeExtend.functions){
					buttons.getButtonHtml(buttonInfo);
				}
			}else{
				console.log('没有节点按钮信息');
			}
		},
		setHeadText: function(){
			if(buttons.nodeExtend){
				$("#headLi1").text(buttons.nodeExtend.nodeStateId);
				buttons.getNextNodeInfo();
			}else{
				$("#headLi1").text("已办结");
				$("#headLi1").addClass("cis-bwizard-finish");
				$("#headLi2").remove();
			}
		},
		getNextNodeInfo: function(){
			if($("#processInstanceId").val()){
				var formData = new FormData();
		        formData.append('processInstanceId',$("#processInstanceId").val());
		        formData.append('nodeStateOrder',buttons.nodeExtend.nodeStateOrder);
		        
		        $.ajax({
		            type : 'post',
		            processData: false,
		            contentType: false,
		            data : formData,
		            dataType : 'json',
			        url : '/cis/member/cisc040202/selectNextCiszWfNodeInit',
		            success : function(result) {
		                if( result.obj ){
		    				$("#headLi2").text(result.obj.nodeStateId);
		                }else{
		                	$("#headLi2").remove();
		                }	
		            }
		        });
			}
		},
		getButtonHtml: function(buttonInfo){
			var buttonHtml = "";
			if(buttonInfo.action.indexOf('apply') != -1 || buttonInfo.action.indexOf('commit') != -1 || buttonInfo.action.indexOf('approve') != -1 || buttonInfo.action == "over" || buttonInfo.action == "getFlag" ){
				buttonHtml += '<button type="button" id="'+ buttonInfo.functionValue +'" onclick="'+ buttonInfo.action +'()" class="btn cis-btn cis-btn-primary mr-2" style="margin-right: 5px;">';
			}else{
				buttonHtml += '<button type="button" id="'+ buttonInfo.functionValue +'" onclick="'+ buttonInfo.action +'()" class="btn cis-btn cis-btn-white mr-2" style="margin-right: 5px;">';
			}
			buttonHtml += '<span class="glyphicon"></span>';
			buttonHtml += '<span>'+ buttonInfo.functionName +'</span>';
			buttonHtml += '</button>'; 
			buttons.buttonBoxNode.append(buttonHtml);
		},
		setFunctionAttr: function(formData,actionValue){
			debugger;
			var button = buttons.nodeExtend.functions.find(bt => { return bt.action == actionValue });
			if(button){
				formData['functionName'] = button.functionName;
				formData['functionValue'] = button.functionValue;
				formData['action'] = button.action;
				formData['isBack'] = button.isBack;
				formData['nodeStateId'] = buttons.nodeExtend.nodeStateId;
				formData['nodeStateAuditGroup'] = buttons.nodeExtend.nodeStateAuditGroup;
				formData['nodeStateAt'] = buttons.nodeExtend.nodeStateAt;
			}
			return formData;
		},
}