
$(function() {
	
	// 文件上传
	// 随意单个或多个文件
	// 返回值中 result.msg
	// 表示整体请求是否完成
	// 返回值中 result.data
	// 此返回值为数组 其中是每个文件的返回路径 名称 以及上传是否成功的信息
    $('#filesInput').change(function() {

        // 选中的文件
        var filelength = $('#filesInput').get(0).files.length;

        // 做成FormData对象
        var formData = new FormData();
        formData.append('path',"img/img2/img3");
        var checkErrors = [];
        // 从选中的文件中过滤满足条件的图片
        if (filelength > 0) {
            // 图片类型正则表达式
            var fileTypeRegExp = new RegExp('(\\.)(jpeg|jpg|png)$',"i");
            jQuery.each($('#filesInput').get(0).files, function(i, file) {
//                // 图片类型正则表达式验证
//                var fileTypeRegExp = new RegExp('(\\.)(gif|jpeg|jpg|png)$',"i");
//                if (!fileTypeRegExp.test(file.name)) {
//                    checkErrors.push('照片[' + file.name + ']的格式不正确，请重新选择。');
//                } else {
//                    var fileSize = file.size/(1024 * 1024);
//                    // 单张图片文件不能超过2M
//                }
                formData.append('files',file);
            });
        }
        
        var targetUrl = '/cis/file/fileUpload';
        
        $.ajax({
            type : 'post',
            processData: false,
            contentType: false,
            data : formData,
            dataType : 'json',
            url : targetUrl,
            success : function(result) {
                // 成功或部分失败的场合，统一做下面的处理(success === true,保持前后台一致)
                if( result.msg == "file upload over"){
                	// 实际使用时 请自行扩展保存路径和文件名的操作
                	for(var file of result.data){
                		if(file.message == "success"){
							var filehtml = '<div class="layui-col-md2 layui-row" id="'+ file.fileTimeMillis +'">';
							filehtml += getFileImg(file.fileName,file.path);
//							filehtml += '<label class="layui-col-md10" style="margin-top: 5px;white-space: nowrap;text-overflow: ellipsis; overflow: hidden;"> '+file.fileName+'</label>';
							filehtml += '<a class="cis-text-primary font-14 p-2 row-visit" style="margin-top:40px;margin-left:205px;cursor: pointer;" onclick="deleteFile(this,null)" >删除</a>';
							filehtml += '<input hidden="true" value='+file.path+'>';
							filehtml += '</div>';
							$("#addPoint").before(filehtml);
							saveFile(file);
                		}
                	}
                }
            }
        });
    });
	
	
})

var files = [];

var initFiles = [];

var deleteFiles = [];

var userInfo = {};

var canEditFlag = true;

function fileUtilsAddFile(){
	$('#filesInput').click();
}

// 文件页初始化方法
function searchFile(tableName,tableKey,identifyFlag,user,canEdit){
    
	userInfo = user;
	canEditFlag = canEdit;
	// 没有编辑权限。隐藏添加按钮
	if(!canEditFlag){
		$("#addPoint").hide();
	}
	
	var formData = new FormData();
	formData.append("tableName",tableName);
	formData.append("tableKey",tableKey);
	formData.append("identifyFlag",identifyFlag);
	
	$.ajax({
        type : 'post',
        processData: false,
        contentType: false,
        data : formData,
        dataType : 'json',
        url : "/cis/file/query",
        success : function(result) {
        	if( result.data && result.data.length > 0){
        		initFiles = result.data;
            	for(var file of result.data){
        			var filehtml = '<div class="layui-col-md2 layui-row">';
        			filehtml += getFileImg(file.originalName,file.filePath);
//        			filehtml += '<label class="layui-col-md10" style="margin-top: 5px;white-space: nowrap;text-overflow: ellipsis; overflow: hidden;"> '+ file.originalName +'</label>';
        			// 此处判断是否能编辑此文件
        			if( file.createUserId == userInfo.userId && canEditFlag){
            			filehtml += '<a onclick="deleteFile(this,';
            			filehtml += "'"+ file.fileId +"'";
            			filehtml += ')" class="cis-text-primary font-14 p-2 row-visit" style="margin-top:40px;margin-left:205px;cursor: pointer;">删除</a>';
        			}
        			filehtml += '<input hidden="true" value='+file.filePath+'>';
        			filehtml += '</div>';
        			$("#addPoint").before(filehtml);
            	}
        	}
        }
	});
}


// 保存所有数据
function saveAllFileToDb(tableName,tableKey,identifyFlag){
	// 待保存文件保存
	if(files && files.length > 0){
		for(var file of files){
		 	var formData = new FormData();
		 	formData.append("tableName",tableName);
		 	formData.append("tableKey",tableKey);
		 	formData.append("identifyFlag",identifyFlag);
		 	formData.append("fileName",file.fileName);
		 	formData.append("newName",file.newName);
		 	formData.append("fileSize",file.fileSize);
		 	formData.append("path",file.path);
		 	formData.append("userId",userInfo.userId);
		 	saveFileToDb(formData,file);
		}	
	}// 待删除文件删除
	else if(deleteFiles && deleteFiles.length > 0){
		for(var file of deleteFiles){
			deleteFileInDb(file);
		}
	}else{
		allFileSaveDown();
	}
};


// 保存文件到数据 请自行修改success方法
function saveFileToDb(formData,file){
	
    var targetUrl = '/cis/file/fileSaveToDb';
    
	$.ajax({
        type : 'post',
        processData: false,
        contentType: false,
        data : formData,
        dataType : 'json',
        url : targetUrl,
        success : function(result) {
        	// 文件保存后删除files中的数据
        	files.splice(files.indexOf(file),1);
        	
        	if(files.length == 0){
        		// 调用文件删除方法
        		if(deleteFiles && deleteFiles.length > 0){
        			for(var file of deleteFiles){
        				deleteFileInDb(file);
        			}
        		}else{
        			allFileSaveDown();
        		}
        	}
        }
	})
}



// 文件下载 需要路径以及想要的名字
function downloadFile(path,fileName){
	if( fileName && path){
		// 手动生成form提交并删除
		var url = '/cis/file/download?path='+path+'&fileName='+fileName;
		
		var form = $("<form></form>").attr("action", url).attr("method", "post");
		
		form.appendTo('body').submit().remove();
	}
}



// 判断文件类型生成图标 有更多类型请自行扩展
function getFileImg(fileName,filePath){
	extension = fileName.split(".")[1];
	switch(extension){
		case "png": case "jpg": case "jpeg": case "bmp": case "tif": case "gif": 
		case "pcx": case "tga": case "exif": case "svg": case "psd": case "cdr":
			img = "";
			img += '<img  class="layui-col-md12" style="height:300px" ';
			img += 'onclick="downloadFile(';
			img += "'"+filePath+"',";
			img += "'"+fileName+"'";
			img += ')"src="/cis/file/download?path='+filePath+'&fileName='+fileName+'"/>';
			return img;
		default:
			img = "";
			img += '<img  class="layui-col-md12" style="height:300px" ';
			img += 'onclick="downloadFile(';
			img += "'"+filePath+"',";
			img += "'"+fileName+"'";
			img += ')" src="/cis/static/cis/images/common/docFile.png"/>';
			return img;
	}
}

function deleteFileInDb(file){
	var formData = new FormData();
    formData.append('fileId',file.fileId);
    formData.append('userId',userInfo.userId);
	$.ajax({
        type : 'post',
        processData: false,
        contentType: false,
        data : formData,
        dataType : 'json',
        url : "/cis/file/fileDelete",
        success : function(result) {
            // 此返回值表示成功
        	deleteFiles.splice(deleteFiles.indexOf(file),1);
        	if(deleteFiles.length == 0){
        		// 调用保存完成方法
        		allFileSaveDown();
        		
        	}
        }
    });
}

// 文件删除，没有fileId时。直接删除标签。有ID走后台删表数据
function deleteFile(button,fileId){
	if(fileId){
		var file = initFiles.find( fl => {
			return fl.fileId == fileId;
		})

		initFiles.splice(files.indexOf(file),1)
		// 放入待删除中，表单保存时一起删除
		deleteFiles.push(file);
		
    	$(button).parent().remove();
	}else{
		removeFile($(button).parent().attr('id'));
		$(button).parent().remove();
	}
}

// 当临时保存文件完成后会调用此方法
// 请在此方法中保存临时文件的file
// 参照本业面的方法
// 在具体保存所有文件到数据库时会用到
function saveFile(file){
	files.push(file);
}

// 删除临时文件时 将变量中的文件去掉
function removeFile(fileTimeMillis){
	var file = files.find( fl => {
		return fl.fileTimeMillis == fileTimeMillis;
	})
	files.splice(files.indexOf(file),1)
}