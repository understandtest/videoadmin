$(function(){
//通用类
//获取项目路径
window.basePath = document.getElementById("base").href;
$('.viewer-img').viewer({"fullscreen":false});
/***
 * 双层图标的js
 */
$(".user-handle li").mouseover(function(){
    $(".user-bg",this).show();
})
$(".user-handle li").mouseout(function(){
	$(".user-bg").hide();
})
/**
 * 弹出框优化
 */
/*window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}*/

toastr.options ={
		closeButton: false,  //是否显示关闭按钮（提示框右上角关闭按钮）
        debug: false,  //是否为调试；
        progressBar: false,  //是否显示进度条（设置关闭的超时时间进度条）
        /**
         * toast-top-left  顶端左边
		   toast-top-right    顶端右边
		   toast-top-center  顶端中间
           toast-top-full-width 顶端，宽度铺满整个屏幕
           toast-botton-right  
           toast-bottom-left
           toast-bottom-center
           toast-bottom-full-width
         */
        positionClass: "toast-top-center",  //消息框在页面显示的位置
        onclick: null,  //点击消息框自定义事件 
        showDuration: "300",  //显示动作时间
        hideDuration: "2000",  //隐藏动作时间 
        timeOut: "2000",  //自动关闭超时时间 
        extendedTimeOut: "1000",  
        showEasing: "swing",  
        hideEasing: "linear",  
        showMethod: "fadeIn",  //显示的方式，和jquery相同
        hideMethod: "fadeOut"  //隐藏的方式，和jquery相同
}

//初始化化复选框样式
$('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
    checkboxClass: 'icheckbox_flat-blue',
    radioClass: 'iradio_flat-blue'
 });

//复选框全选

$("#checkAll").on('ifChecked ifUnchecked', function(event) {
	if (event.type == 'ifChecked') {
		$('input').iCheck('check');
	} else {
		$('input').iCheck('uncheck');
	}
});

//初始化select2
$('select').each(function(){
	if(!$(this).hasClass("noselect2")){
		$(this).select2({});
	}
})

window.commMethod = new Object();
//去掉所有的html标记
commMethod.delHtmlTag = function delHtmlTag(str){
	return str.replace(/<[^>]+>/g,"");
}
//全局配置
/**$.ajaxSetup({
	dataType: "json",
	contentType: "application/json",
	cache: false
});*/



//jqGrid的配置信息
/*$.jgrid.defaults.width = 300;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';


//选择一条记录
function getSelectedRow() {
    let grid = $("#jqGrid");
    let rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    let selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    
    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    let grid = $("#jqGrid");
    let rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    return grid.getGridParam("selarrrow");
}*/

    //创建websocket建立消息提醒
/*	let ws = new WebSocket("ws://"+window.location.host+"/websocket/webSocketServer")
	ws.onopen = function () {
		console.log("onpen");
		//ws.send("{}");
		
	}
	ws.onclose = function () {
		console.log("onclose");
	}

	ws.onmessage = function (msg) {
		console.log(msg.data);
		if(msg.data != null && msg.data !="" && msg.data !="0")
		{
			$("#msgNumber").text(msg.data);
		}
	}*/
	

	//验证当前字符是否为json格式
	function isJsonFormat(str) {  
	    try {  
	        $.parseJSON(str);  
	    } catch (e) {  
	        return false;  
	    }  
	    return true;  
	}  
	
});

let s;

let opt0 = ["省份", "地级市", "市、县、区"];
let geo_province =  "";
let geo_city = "";
let geo_area = "";
function initGeo(values,ms){
	if((typeof ms)!="undefined"){
		s = ms;
	}else{
		s = ["s1","s2","s3"];
	}
	if((typeof values) != 'undefined'){
		geo_province =  values[0];
		geo_city = values[1];
		geo_area = values[2];
	}
	$("body").off("change","#"+s[0]);
	$("body").off("change","#"+s[1]);
	$("body").on("change","#"+s[0],function(){
		let code = $(this).find("option:selected").attr("code");
		initCity(code);
		/**if(code==""){
			$("#"+s[1]).html('<option code="" value="">'+opt0[1]+'</option>');
		}else{
			initCity(code,"");
		}**/
	})
	$("body").on("change","#"+s[1],function(){
		let code = $(this).find("option:selected").attr("code");
		initArea(code);
		/**if(code==""){
			$("#"+s[2]).html('<option code="" value="">'+opt0[2]+'</option>');
		}else{
			initArea(code);
		}**/
	})
	initProvince();
}
function initProvince(){
	$.ajax({
		type:"get",
		url:"/ajaxProvince",
		dataType:"json",
		success:function(data){
			let options = '<option code="" value="">'+opt0[0]+'</option>';
			let selected = "";
			for(let i=0;i<data.data.length;i++){
				if(data.data[i].name==geo_province){
					selected = "selected='selected'";
					geo_province = "";
				}
				options += '<option '+selected+' code="'+data.data[i].code+'" value="'+data.data[i].name+'">'+data.data[i].name+'</option>';
				selected = "";
			}
			$("#"+s[0]).html(options);
			$("#"+s[0]).trigger("change");
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			toastr.error("网络错误！");
		}
	});
}
function initCity(father){
	if((typeof father) != 'undefined'){
		$.ajax({
			type:"get",
			url:"/ajaxCity_"+father,
			dataType:"json",
			success:function(data){
				let options = '<option code="" value="">'+opt0[1]+'</option>';
				let selected = "";
				for(let i=0;i<data.data.length;i++){
					if(data.data[i].name==geo_city){
						selected = "selected='selected'";
						geo_city = "";
					}
					options += '<option '+selected+' code="'+data.data[i].code+'" value="'+data.data[i].name+'">'+data.data[i].name+'</option>';
					selected = "";
				}
				$("#"+s[1]).html(options);
				$("#"+s[1]).trigger("change");
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				toastr.error("网络错误！");
			}
		});
	}
}
function initArea(father){
	if((typeof father) != 'undefined'){
		$.ajax({
			type:"get",
			url:"/ajaxArea_"+father,
			dataType:"json",
			success:function(data){
				let options = '<option code="" value="">'+opt0[2]+'</option>';
				let selected = "";
				for(let i=0;i<data.data.length;i++){
					if(data.data[i].name==geo_area){
						selected = "selected='selected'";
						geo_area = "";
					}
					options += '<option '+selected+' code="'+data.data[i].code+'" value="'+data.data[i].name+'">'+data.data[i].name+'</option>';
					selected = "";
				}
				$("#"+s[2]).html(options);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				toastr.error("网络错误！");
			}
		});
	}
}
/**
 * 
 * bootstrap fileinput图片上传插件初始化  fileid为控件id  inpid为图片路径隐藏域id
 * 
 * **/
function initFileInput(fileId,inpId,uploadUrl){
	let oldVal="";
	if((typeof uploadUrl)=='undefined'||uploadUrl==""){
		uploadUrl = "/upload/image";
	}
	$("#"+fileId).fileinput('destroy');  
	$("#"+fileId).parents(".file_parent_div").find(".kv-file-remove").trigger("click");
	let fileInputUrl = $("#"+inpId).attr("remote_url");
	if((typeof fileInputUrl) != 'undefined'&&fileInputUrl!=""){
		$("#"+fileId).fileinput({
			maxFileCount: 1,
			language: 'zh',
			uploadUrl: uploadUrl,
			allowedFileExtensions : ['jpg','jpeg','png'],
			initialPreview:"<img src='"+fileInputUrl+"' calss='file-preview-image' style='width:100%;height:160px;'/>",
			uploadAsync: true,
			showUpload:false,
			showPreview :true,
			dropZoneEnabled: false,
			showClose:false,
			showCancel:false,
			showCaption: false,
			autoReplace:true,
			showRemove:false
		}).on("filebatchselected", function(event, files) {
			$(this).fileinput("upload");
			$("#"+fileId).parent().siblings(".kv-upload-progress").remove();
		}).on("fileuploaded", function (event, data, previewId, index) {
			oldVal=$("#"+inpId).val();
			if(data.response.httpCode=="200"){
				$("#"+inpId).val(data.response.data);
				$("#"+fileId).parent().hide();
			}else{
				toastr.error("网络错误!");
				$(".kv-file-remove").trigger("click");
			}
		}).on('fileerror', function(event, data, msg) {
			oldVal=$("#"+inpId).val();
			toastr.error("网络错误！");
		});
	}else{
		$("#"+fileId).fileinput({
			maxFileCount: 1,
			language: 'zh',
			uploadUrl: uploadUrl,
			allowedFileExtensions : ['jpg','jpeg','png'],
			uploadAsync: true,
			showUpload:false,
			showPreview :true,
			dropZoneEnabled: false,
			showClose:false,
			showCancel:false,
			showCaption: false,
			autoReplace:true,
			showRemove:false
		}).on("filebatchselected", function(event, files) {
			$(this).fileinput("upload");
			$("#"+fileId).parent().siblings(".kv-upload-progress").remove();
		}).on("fileuploaded", function (event, data, previewId, index) {
			oldVal=$("#"+inpId).val();
			if(data.response.httpCode=="200"){
				$("#"+inpId).val(data.response.data);
				$("#"+fileId).parent().hide();
			}else{
				toastr.error("网络错误!");
				$(".kv-file-remove").trigger("click");
			}
		}).on('fileerror', function(event, data, msg) {
			oldVal=$("#"+inpId).val();
			toastr.error("网络错误！");
		});
	}
	$("#"+fileId).parents(".file_parent_div").on("click",".kv-file-remove",function(){
		$("#"+inpId).val(oldVal	);
		$("#"+fileId).parent().show();
	})
}

function initEditor(id){
	wangEditor.config.printLog = false;
	let editor = new wangEditor(id);
	 editor.config.menus = [
	     'source',
	     '|',
	     'bold',
	     'underline',
	     'italic',
	     'strikethrough',
	     'eraser',
	     'forecolor',
	     'bgcolor',
	     '|',
	     'alignleft',
	     'aligncenter',
	     'alignright',
	     '|',
	     'link',
	     'unlink',
	     'table',
	     'emotion',
	     '|',
	     'img',
	     'fullscreen'
	 ];
	//上传图片路径
	editor.config.uploadImgUrl = '/upload/layer/image';
	// 隐藏掉插入网络图片功能。该配置，只有在你正确配置了图片上传功能之后才可用。
    editor.config.hideLinkImg = true;
    editor.config.uploadImgFns.ontimeout = function (xhr) {
        toastr.error("上传图片超时，请检查您的网络！");
    };
    // 自定义error事件
    editor.config.uploadImgFns.onerror = function (xhr) {
        // xhr 是 xmlHttpRequest 对象，IE8、9中不支持
    	toastr.error("上传错误！");
    };
    // 定义文件上传的名称为file
    editor.config.uploadImgFileName = 'file';
    editor.create();
    return editor;
}

// 获取url参数
function getQueryString(name) {
	let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	let reg_rewrite = new RegExp("(^|/)" + name + "/([^/]*)(/|$)", "i");
	let r = window.location.search.substr(1).match(reg);
	let q = window.location.pathname.substr(1).match(reg_rewrite);
	if(r != null){
		return unescape(r[2]);
	}else if(q != null){
		return unescape(q[2]);
	}else{
		return null;
	}
}

/**
 * 初始化常规上传框
 */
function initUpload(fileid,imgid,inpid,url){
	$("body").on("change","#"+fileid,function(){
		let filepath = $("#"+fileid).val();
		if(filepath==""){
			toastr.warning("请选择文件！");
			return false;
		}
		let allowExt = ".jpg|.png|.gif|.jpeg";
		let ext = filepath.substring(filepath.lastIndexOf("."));
		if(allowExt.indexOf(ext.toLowerCase()) ==-1){
			toastr.warning("不支持的文件格式！");
			return false;
		}
		if(!url){
			url = "/upload/imageinp";
		}
		$.ajaxFileUpload({
			url,
			secureuri:false,
	        fileElementId:fileid,
	        dataType: "text",
	        success: function (result){
	        	if(result!=""){
	        		let res = result.split("|");
	        		$("#"+inpid).val(res[0]);
	        		$("#"+imgid).attr("src",res[1]);
	        		uploadSuccess(inpid,res[0]);
	        	}else{
	        		toastr.error("上传失败！");
	        	}
	        	$("#"+fileid).val("");
	        },
	        error: function (data, status, e){
	            toastr.error("网络错误！");
	            $("#"+fileid).val("");
	        }
		 });
	})
}
function initUpload2(fileid,imgid,inpid){
	$("body").on("change","#"+fileid,function(){
		let filepath = $("#"+fileid).val();
		if(filepath==""){
			toastr.warning("请选择文件！");
			return false;
		}
		let allowExt = ".jpg|.png|.gif|.jpeg";
		let ext = filepath.substring(filepath.lastIndexOf("."));
		if(allowExt.indexOf(ext.toLowerCase()) ==-1){
			toastr.warning("不支持的文件格式！");
			return false;
		}
		$.ajaxFileUpload({
			url:"/upload/imageinp",
			secureuri:false,
	        fileElementId:fileid,
	        dataType: "text",
	        success: function (result){
	        	if(result!=""){
	        		let res = result.split("|");
	        		$("#"+inpid).val(res[1]);
	        		$("#"+imgid).attr("src",res[1]);
	        		uploadSuccess(inpid,res[0]);
	        	}else{
	        		toastr.error("上传失败！");
	        	}
	        	$("#"+fileid).val("");
	        },
	        error: function (data, status, e){
	            toastr.error("网络错误！");
	            $("#"+fileid).val("");
	        }
		 });
	})
}
function uploadSuccess(id,url){
	
}

/**
 * 初始化多图上传的  不指定上传图片数量 则默认最大3个
 */
function initMulitUpload(fileid,imgdivid,inpid,maximg){
	if((typeof maximg)=='undefined'||maximg==""){
		maximg = 3;
	}
	$("body").on("change","#"+fileid,function(){
		let filepath = $("#"+fileid).val();
		if(filepath==""){
			toastr.warning("请选择文件！");
			return false;
		}
		let len = $("#"+imgdivid).find(".price-img").length;
		if(len>=maximg){
			toastr.warning("最多上传"+maximg+"张图片！");
			return false;
		}
		let allowExt = ".jpg|.png|.gif|.jpeg";
		let ext = filepath.substring(filepath.lastIndexOf("."));
		if(allowExt.indexOf(ext.toLowerCase()) ==-1){
			toastr.warning("不支持的文件格式！");
			return false;
		}
		$.ajaxFileUpload({
			url:"/upload/imageinp",
			secureuri:false,
	        fileElementId:fileid,
	        dataType: "text",
	        success: function (result){
	        	if(result!=""){
	        		let res = result.split("|");
	        		let oldVal = $("#"+inpid).val();
	        		if(oldVal!=""){
	        			$("#"+inpid).val(oldVal+","+res[0]);
	        		}else{
	        			$("#"+inpid).val(res[0]);
	        		}
	        		$("#"+imgdivid).append("<div class='price-img'><img src='"+res[1]+"' save-url='"+res[0]+"'><i class='fa fa-times-circle img-mulit-i'></i></div>");
	        	}else{
	        		toastr.error("上传失败！");
	        	}
	        	$("#"+fileid).val("");
	        },
	        error: function (data, status, e){
	            toastr.error("网络错误！");
	            $("#"+fileid).val("");
	        }
		 });
	})
	$("#"+imgdivid).on("click",".img-mulit-i",function(){
		$(this).parent().remove();
		let urls = [];
		$("#"+imgdivid).find(".price-img").each(function(){
			urls.push($(this).find("img").attr("save-url"));
		})
		$("#"+inpid).val(urls.join(","));
	})
}
/**
 * excel上传
 */
function initExcelUpload(fileid,url){
	$("body").on("change","#"+fileid,function(){
		let filepath = $("#"+fileid).val();
		if(filepath==""){
			toastr.warning("请选择文件！");
			return false;
		}
		let allowExt = ".xls|.xlsx";
		let ext = filepath.substring(filepath.lastIndexOf("."));
		if(allowExt.indexOf(ext.toLowerCase()) ==-1){
			toastr.warning("仅支持xls3和xlsx格式文件！");
			return false;
		}
		shadeShow();
		$.ajaxFileUpload({
			url:url,
			secureuri:false,
	        fileElementId:fileid,
	        dataType: "text",
	        success: function (result){
				let jsonRes = jQuery.parseJSON(result);
	        	shadeHide();
	        	$("#"+fileid).val("");
	        	if(jsonRes.httpCode==200){
	        		swal({
	                    title: "导入成功!",
	                    text: jsonRes.data,
	                    type:"success",
	                    closeOnConfirm: false
	                }, function(){
	                	window.location.reload(true);
        			});
	        		//window.location.reload(true);
	        	}else{
	        		let html = jsonRes.data;
	        		html = "<p style='height: 300px;overflow: scroll;overflow-x: hidden;'>"+html +"</p>";
        			swal({
	                    title: "<h4>导入错误</h4>",
	                    text: html,
	                    html: true,
	                    closeOnConfirm: false
	                }, function(){
	                	window.location.reload(true);
        			});
	        	}
	        },
	        error: function (data, status, e){
	        	shadeHide();
	        	$("#"+fileid).val("");
	            toastr.error("请下载并使用导入模板进行导入！");
	        }
		 });
	})
}
	let shadeHtml = "<div class='modal fade my-shade' style='z-index:1099' aria-hidden='true' data-backdrop='static'>";
	shadeHtml += "<div class='loadingDiv'>"
	shadeHtml += "<svg viewBox='0 0 180 180' version='1.1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'>";
	shadeHtml += " <g id='circle' class='g-circles g-circles--v1'>";
	shadeHtml += "<circle id='12' transform='translate(35, 16.698730) rotate(-30) translate(-35, -16.698730) ' cx='35' cy='16.6987298' r='10'></circle>";
	shadeHtml += "<circle id='11' transform='translate(16.698730, 35) rotate(-60) translate(-16.698730, -35) ' cx='16.6987298' cy='35' r='10'></circle>";
	shadeHtml += "<circle id='10' transform='translate(10, 60) rotate(-90) translate(-10, -60) ' cx='10' cy='60' r='10'></circle>";
	shadeHtml += "<circle id='9' transform='translate(16.698730, 85) rotate(-120) translate(-16.698730, -85) ' cx='16.6987298' cy='85' r='10'></circle>";
	shadeHtml += "<circle id='8' transform='translate(35, 103.301270) rotate(-150) translate(-35, -103.301270) ' cx='35' cy='103.30127' r='10'></circle>";
	shadeHtml += "<circle id='7' cx='60' cy='110' r='10'></circle>";
	shadeHtml += "<circle id='6' transform='translate(85, 103.301270) rotate(-30) translate(-85, -103.301270) ' cx='85' cy='103.30127' r='10'></circle>";
	shadeHtml += "<circle id='5' transform='translate(103.301270, 85) rotate(-60) translate(-103.301270, -85) ' cx='103.30127' cy='85' r='10'></circle>";
	shadeHtml += "<circle id='4' transform='translate(110, 60) rotate(-90) translate(-110, -60) ' cx='110' cy='60' r='10'></circle>";
	shadeHtml += "<circle id='3' transform='translate(103.301270, 35) rotate(-120) translate(-103.301270, -35) ' cx='103.30127' cy='35' r='10'></circle>";
	shadeHtml += "<circle id='2' transform='translate(85, 16.698730) rotate(-150) translate(-85, -16.698730) ' cx='85' cy='16.6987298' r='10'></circle>";
	shadeHtml += "<circle id='1' cx='60' cy='10' r='10'></circle>";
	shadeHtml += "</g>";
	shadeHtml += "<use xlink:href='#circle' class='use'/>";
	shadeHtml += "</svg>";
	shadeHtml += "</div>";
	shadeHtml += "</div>";
	/**
	 * 操作提示遮罩show
	 */
	function shadeShow(){
		if($(".my-shade").length==0){
			$("body").append(shadeHtml);
		}
		$(".my-shade").modal("show");
	}
	/**
	 * 操作提示遮罩hide
	 */
	function shadeHide(){
		$(".my-shade").modal("hide");
	}
	
	$(".msg").click(function(){
		//传user.userType到后台
		$.ajax({
			type: "POST",
			url:"/admin/order/msgList",
			dataType:"html",
			success:function(data){
				$(".msgUl").html(data);
			},
			error:function(e){
				toastr.error("系统异常，请联系管理员!");
	    }
		});

	});

	function disBtn(id){
		$("#"+id).attr("disabled",true);
	}
	function unDisBtn(id){
		$("#"+id).removeAttr("disabled");
	}
//单文件上传
function uploadFile(fileId,type) {
		let _extensions;
		let _mimeTypes;
		let allMaxSize = 300;
		if(type=='myFile'){
			_extensions ='word,xls,xlsx,ppt';  
			_mimeTypes ='video/*,audio/*,application/*'; 
			}else{
			_extensions ='mp4,mov';  
			_mimeTypes ='video/mp4,video/mov'; 
			}
	    let $ = jQuery,
	        $list = $('#thelist'),
	        $btn = $('#ctlBtn'),
	        state = 'pending',
	        uploader;
	    uploader = WebUploader.create({
	        // 不压缩image
	        resize: false,	
	        // swf文件路径，需要用到flash的时候BASE_URL自己根据需要定义 也可写成绝对路径
	        swf: '${base}/static/plugins/webuploader/Uploader.swf',
	        // 文件接收服务端。此处根据自己的框架写，本人用的是SpringMVC
	        server: '/upload/videoUp',   
	        // 选择文件的按钮。可选。
	        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	        fileSizeLimit: allMaxSize*1024*1024,//限制大小，所有被选文件，超出选择不上
	        pick: fileId,
	        accept: {
	            title: 'file',
	            extensions: _extensions,
	            mimeTypes: _mimeTypes
	        }
	    });
	    
	    //  验证大小
	    uploader.on("error",function (type){ 
	         if(type == "F_DUPLICATE"){
	              toastr.error("不能重复选择文件！");
	         }else if(type == "Q_EXCEED_SIZE_LIMIT"){
	              toastr.error("所选文件总大小不可超过" + allMaxSize+"M");
	         }
	     });

	    // 当有文件添加进来的时候
	    uploader.on( 'fileQueued', function( file ) {
	        $list.html( '<div id="' + file.id + '" class="item">' +
	            '<h4 class="info">' + file.name + '</h4>' +
	            '<p class="state">等待上传...</p>' +
	        '</div>' );
	    });

	    // 文件上传过程中创建进度条实时显示。
	    uploader.on( 'uploadProgress', function( file, percentage ) {
	        let $li = $( '#'+file.id ),
	            $percent = $li.find('.progress .progress-bar');

	        // 避免重复创建
	        if ( !$percent.length ) {
	            $percent = $('<div class="progress progress-striped active">' +
	              '<div class="progress-bar" role="progressbar" style="width: 0%">' +
	              '</div>' +
	            '</div>').appendTo( $li ).find('.progress-bar');
	        }

	        $li.find('p.state').text('上传中');

	        $percent.css( 'width', percentage * 100 + '%' );
	    });

	    uploader.on( 'uploadSuccess', function( file,data) {
	    	console.log(data);
	    	let url = data.fileUrl;  //上传视频路径
	    	$("#videoUrl").val(url);
//	    	$("#videoName").val(file.name);
	    	$("#videoSourceName").val(file.name);
	        $( '#'+file.id ).find('p.state').text('已上传');
	    });

	    uploader.on( 'uploadError', function( file ) {
	        $( '#'+file.id ).find('p.state').text('上传出错');
	    });

	    uploader.on( 'uploadComplete', function( file ) {
	        $( '#'+file.id ).find('.progress').fadeOut();
	    });

	    uploader.on( 'all', function( type ) {
	        if ( type === 'startUpload' ) {
	            state = 'uploading';
	        } else if ( type === 'stopUpload' ) {
	            state = 'paused';
	        } else if ( type === 'uploadFinished' ) {
	            state = 'done';
	        }

	        if ( state === 'uploading' ) {
	           // $btn.text('暂停上传');
	        } else {
	            $btn.text('开始上传');
	        }
	    });

	    $btn.on( 'click', function() {
	    	
	        if ( state === 'uploading' ) {
	          //  uploader.stop(true);
	        } else {
	            uploader.upload();
	        }
	    });
	}

	//key  selectid  初始option    默认选中的选项
function ajaxBaseDataSelect(key,sid,firstOption,defaultValue){
	$.ajax({ 
    type:"POST", 
    url:"/webajax/ajaxBaseData", 
    data:{key:key},
    async:false,
    dataType:"json",      
    success:function(data){
		$("#"+sid).html("<option value=''>"+firstOption+"</option>");
    	jQuery.each(data.data, function(i,d){ 
    		if((typeof defaultValue) != 'undefined'){
    			if(defaultValue==d.dataKey){
    				$("#"+sid).append("<option selected='selected' value='"+d.dataKey+"'>"+d.dataValue+"</option>");
    			}else{
    				$("#"+sid).append("<option value='"+d.dataKey+"'>"+d.dataValue+"</option>");
    			}
    		}else{
				$("#"+sid).append("<option value='"+d.dataKey+"'>"+d.dataValue+"</option>");
			}
        });  
    }
 })
}
function formatDate(time,format){
    let date = new Date(time);

    let year = date.getFullYear(),
        month = date.getMonth()+1,//月份是从0开始的
        day = date.getDate(),
        hour = date.getHours(),
        min = date.getMinutes(),
        sec = date.getSeconds();
    let preArr = Array.apply(null,Array(10)).map(function(elem, index) {
        return '0'+index;
    });////开个长度为10的数组 格式为 00 01 02 03

    let newTime = format.replace(/YY/g,year)
                        .replace(/MM/g,preArr[month]||month)
                        .replace(/DD/g,preArr[day]||day)
                        .replace(/hh/g,preArr[hour]||hour)
                        .replace(/mm/g,preArr[min]||min)
                        .replace(/ss/g,preArr[sec]||sec);

    return newTime;         
}

