//jqGrid的配置信息

if($.jgrid){
	$.jgrid.defaults.width = 1000;
	$.jgrid.defaults.responsive = true;
	$.jgrid.defaults.styleUI = 'Bootstrap';
}

var baseURL0 = "";
var baseURL = "../../";
var baseURL2 = "/../";
var baseURL3 = "../../../";

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false
});
//重写alert
window.alert = function(msg, callback){//parent.
	layer.alert(msg, function(index){
		layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
};
/* 简化Ajax */
$.postJSON = function(url, data, callback,async) {
	if (async == null) {
		async = true;
	}
	return $.ajax({
		'type': 'POST',
		'url': url,
		'contentType': 'application/json',
		'data': JSON.stringify(data),
		'dataType': 'json',
		'success': callback,
		async: async,
	});
};
/* get请求同步 */
$.getSync = function(url, data, callback) {
 return  $.ajax({
        'type': 'GET',
        'url': url,
        'data': data,
        'dataType': 'json',
        'success': callback,
        async:false,
    });
};
/* 异步请求等待动画 */
$.postJSONAnimate = function(url, data) {
	var defer = $.Deferred();
	$.ajax({
		'type': 'GET',
		'url': url,
		'data': data,
		'dataType': 'json',
		success: function(data){
			defer.resolve(data)
		}
	});
	return defer
}
/* bootstrap select */
/*$('.selectpicker').selectpicker({
	noneSelectedText: '',
	noneResultsText: '没有找到噢(ง •_•)ง',
	liveSearch: true,
	size:5   //设置select高度，同时显示5个值
});*/

//重写confirm式样框
window.confirm = function(msg, callback){
	layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
};

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }

    return selectedIDs[0];
}

function getSelectedRownoalert() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	//alert("请选择一条记录");
    	return ;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }

    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }

    return grid.getGridParam("selarrrow");
}






//判断是否为空
function isBlank(value) {
	if(value===0){
		return false;
	}
    return !value || !/\S/.test(value)
}

//判断是否为空
function isBlankOrEmpty(value) {
	if(value===0){
		return false;
	}
    return !value || (""==value) || !/\S/.test(value);
}

//内嵌select2
Vue.directive('select2', {
    inserted: function (el, binding, vnode) {
        var options = binding.value || {};

        $(el).select2(options).on("select2:select", function(e){
            // v-model looks for
            //  - an event named "change"
            //  - a value with property path "$event.target.value"
            el.dispatchEvent(new Event('change', { target: e.target })); //说好的双向绑定，竟然不安套路
            // console.log("fire change in insert");
        });
    },
    update: function (el, binding, vnode) {
        for (var i = 0; i < vnode.data.directives.length; i++) {
            if (vnode.data.directives[i].name == "model") {
                $(el).val(vnode.data.directives[i].value);
                // console.log("new value in update:"+vnode.data.directives[i].value);
            }
        }
        $(el).trigger("change");
        // console.log("fire change in update");
    }
});

Vue.component('select2', {
	  props: ['options', 'value'],
	  template: '#select2-template',
	  mounted: function () {
	    var vm = this;
	    $(this.$el)
	      // init select2
	      .select2({ data: this.options })
	      .val(this.value)
	      .trigger('change')
	      // emit event on change.
	      .on('change', function () {
	        vm.$emit('input', this.value)
	      })
	  },
	  watch: {
	    value: function (value) {
	      // update value
	      $(this.$el)
	      	.val(value)
	      	.trigger('change')
	    },
	    options: function (options) {
	      // update options
	      $(this.$el).empty().select2({ data: options })
	    }
	  },
	  destroyed: function () {
	    $(this.$el).off().select2('destroy')
	  }
});


//add by xucg
//文件上传
function fileup(type){
	var baseurl = baseURL;
	if(type==3){// 三层路径页面
		baseurl = baseURL3;
	}

	layer.open({
		type : 2,
		offset : '50px',
		skin : 'layui-layer-molv',
		title : "选择附件",
		area : [ '700px', '400px'],
		shade : 0.2,
		shadeClose : false,
		content : baseurl+"modules/sys/fileup.html",
		btn : [ '确定', '取消' ],
		yes: function(index,layero){
			var body = layer.getChildFrame('body', index);
//		    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		    var mark = body.find('#mark').val();
		    var gid = body.find('#getidall').html();

		   console.log("获得的mark "+mark+"----- id "+gid);
		   if(gid==""||gid==null||gid=="undefined"){
			   alert("上传失败，请重新上传");
			   return false;
		   }

		   var befGid =  $('#getallfileid').html();// 这次上传之前的值，用于取消上传后回写
		   $('#getallfileid').append(gid);

		   $.ajax({
				type: "get",
			    url: baseurl + "ins/sysfiletable/updatemark",
			    async: false,
		        contentType: "application/json",
			    data:{"mark":mark,"ids":gid},
			    success: function(r){
			    	// console.log(r);
			    	if(r == "fail"){
			    		alert("上传失败");
			    	}
				}
			});
		   if (typeof(fileUploadCallback) == "function") {// 回调函数
				var callBack = fileUploadCallback(mark);
				if(!callBack && callBack!=undefined){
					$('#getallfileid').html(befGid);
					return;
				}
		   }

		   layer.close(index);
		 }
	});
}

//点击查看附件触发
function ckfile(getid,type,nodel){
	var baseurl = baseURL;
	if(type==3){// 三层路径页面
		baseurl = baseURL3;
	}

	layer.open({
		type : 2,
		offset : '20px',
		skin : 'layui-layer-molv',
		title : "选择附件",
		area : [ '1000px', '460px' ],//590px
		shade :  [0.1, '#fff'],
		shadeClose : false,
		content : baseurl+"modules/sys/sysfiletable.html?getallid="+getid+"&&nodel="+nodel,
		btn : [ '关闭' ]
	});

}

//$(function(){ 
//	 setTimeout(function(){
//		 var hei = $(window).height();
//		 if(hei<768 && hei>400){
//			 $("#jqGrid").setGridHeight($(window).height() - 185);
//    	 }
//     },100);
//});

function resizeLayer(layerIndex,layerInitWidth,layerInitHeight) {
//		var docWidth = $("#myIframe" , parent.document).width()-10;
//		var docHeight =  $("#myIframe" , parent.document).height();
	    var docWidth = $(document).width()-20;
	    var docHeight = $(document.body)[0].clientHeight-30;

		if(layerInitWidth > docWidth || layerInitHeight > docHeight){
		    var minWidth = layerInitWidth > docWidth ? docWidth : layerInitWidth;
		    var minHeight = layerInitHeight > docHeight ? docHeight : layerInitHeight;
		    // console.log("doc:",docWidth,docHeight);
		    // console.log("lay:",layerInitWidth,layerInitHeight);
		    // console.log("min:",minWidth,minHeight);

		    layer.style(layerIndex, {
		        top:0,
		        width: minWidth,
		        height:minHeight
		    });
		}
}

// 如果宽高超出窗口重新设置layer宽高
function layerResize(layerIndex) {
	  var layerInitWidth  = $("#layui-layer"+layerIndex).width();
      var layerInitHeight = $("#layui-layer"+layerIndex).height();

      resizeLayer(layerIndex,layerInitWidth,layerInitHeight);
      $(window).resize()
}


function dictmanage(type){
	layer.open({
		type : 2,
		offset : '10px',
		skin : 'layui-layer-molv',
		title : "字典管理",
		area : [ '1100px','600px'],
		shade : 0.2,
		shadeClose : false,
		content : baseURL+"modules/sys/dictmanage.html?type="+type+"&parentId=0",
		btn : ['关闭'],
		success: function(layero, index) {
            //获取当前弹出窗口的索引及初始大小
            layerResize(index);
        },
		btn1: function(index,layero){
			 layer.close(index);
		}

	});

}

var numRegex = /^\d+$/;// 正整数
var intRegex = /^-?\d+$/;// 整数

function hideall(){
	 $("#pall").toggle();
}

// select2下拉加载模式反选 
function se2CheckValue(domid,value,text){
	 if(!isBlank(value)){
        $("#"+domid).append(new Option(text, value, false, true));
        $("#"+domid).trigger('change');
    }else{
    	$("#"+ domid +" option").remove()
    }
}

//select2下拉加载模式清空选项 
function se2RemoveCheck(domid){
	 $("#"+ domid +" option").remove()
}



/**
 * 下载文件 - 带进度监控
 * @param url: 文件请求路径
 * @param params: 请求参数
 * @param name: 保存的文件名
 * @param progress: 进度处理回调函数
 * @param success: 下载完成回调函数
 * eg: progressDownLoad({url:'http://loacalhost:8080/downLoad.action',name:'file.rar',progress:function(evt){
  *        console.log(evt);
  *     }});
 **/
function progressDownLoad2(url,filename,params,progress,success){
    var xhr = new XMLHttpRequest();
	xhr.open("POST", url, true);
    //监听进度事件
    xhr.addEventListener("progress", function (xhr) {
        if(progress) try{ progress.call(xhr); }catch(e){}
    }, false);

    xhr.responseType = "blob";
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    xhr.onreadystatechange = function () {

		if (xhr.readyState === 4 && xhr.status === 200) {
            if (typeof window.chrome !== 'undefined') {
                // Chrome version
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(xhr.response);
                link.download = filename;
                link.click();
            } else if (typeof window.navigator.msSaveBlob !== 'undefined') {
                // IE version
                var blob = new Blob([xhr.response], { type: 'application/force-download' });
                window.navigator.msSaveBlob(blob, filename);
            } else {
                // Firefox version
                var file = new File([xhr.response], filename, { type: 'application/force-download' });
                window.open(URL.createObjectURL(file));
            }
            if(success) try{ success.call(xhr); }catch(e){}
        }
    };
    // FormData
    //var formData = new FormData();
    var paramsStr = '';
    if(params) for (var key in params) paramsStr += '&'+key+'='+params[key];
    if(paramsStr) paramsStr = paramsStr.substring(1);

    xhr.send(paramsStr);
}
/**  主要使用
 * 下载文件 - 带进度监控
 * @param url: 文件请求路径
 * @param params: 请求参数
 * @param name: 保存的文件名
 * @param progress: 进度处理回调函数
 * @param success: 下载完成回调函数
 * eg: progressDownLoad({url:'http://loacalhost:8080/downLoad.action',name:'file.rar',progress:function(evt){
 *        console.log(evt);
 *     }});
 **/
function progressDownLoad1(url,filename,params,progress,success, listenerState){
	var xhr = new XMLHttpRequest();
	var defer = $.Deferred();
	//监听进度事件
	/*xhr.addEventListener("progress", function (event) {
		if(progress) try{
			progress.call(event);
			if(event.lengthComputable){
				var loaded = parseInt(event.loaded/event.total*100)+"%";
				$('#pros').width(loaded);
				$('#pros').text(loaded);
			}
		}catch(e){}
	}, false);*/
	xhr.open("POST", url, true);

	// xhr.addEventListener("progress", progress, false);

	xhr.responseType = "blob";
	xhr.setRequestHeader("Content-Type","application/json");
	xhr.onreadystatechange = function () {
		if (listenerState) {
			listenerState(xhr.readyState);
		}
		if (xhr.readyState === 4 && xhr.status === 200) {
			if (typeof window.chrome !== 'undefined') {
				// Chrome version
				var link = document.createElement('a');
				link.href = window.URL.createObjectURL(xhr.response);
				link.download = filename;
				link.click();
			} else if (typeof window.navigator.msSaveBlob !== 'undefined') {
				// IE version
				var blob = new Blob([xhr.response], { type: 'application/force-download' });
				window.navigator.msSaveBlob(blob, filename);
			} else {
				// Firefox version
				var file = new File([xhr.response], filename, { type: 'application/force-download' });
				window.open(URL.createObjectURL(file));
			}
			if(success) try{ success(xhr) ;defer.resolve(xhr);}catch(e){}
		} else if (xhr.status === 500) {
			defer.reject("导出失败");
		}
	};
	// FormData
	//var formData = new FormData();
	var paramsStr = '';
	//if(params) for (var key in params) paramsStr += '&'+key+'='+params[key];
	//if(paramsStr) paramsStr = paramsStr.substring(1);
	xhr.send(params);
	return defer;
}


//---------------------------------------------------
//日期格式化
//dateFmt("yyyy-MM-dd hh:mm:ss",new Date())
//---------------------------------------------------
function dateFmt(fmt,date)
{ //author: meizz
	var o = {
		"M+" : date.getMonth()+1,                 //月份
		"d+" : date.getDate(),                    //日
		"h+" : date.getHours(),                   //小时
		"m+" : date.getMinutes(),                 //分
		"s+" : date.getSeconds(),                 //秒
		"q+" : Math.floor((date.getMonth()+3)/3), //季度
		"S"  : date.getMilliseconds()             //毫秒
	};
	if(/(y+)/.test(fmt))
		fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("("+ k +")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	return fmt;
}

/**
 * 下载文件 - 带进度监控
 * @param url: 文件请求路径
 * @param params: 请求参数
 * @param name: 保存的文件名
 * @param progress: 进度处理回调函数
 * @param success: 下载完成回调函数
 * eg: progressDownLoad({url:'http://loacalhost:8080/downLoad.action',name:'file.rar',progress:function(evt){
 *        console.log(evt);
 *     }});
 **/
function progressDownLoad(url,filename,params,progress,success){
	var defer = $.Deferred();
	var xhr = new XMLHttpRequest();

	xhr.open("POST", url, true);

	//监听进度事件
	xhr.addEventListener("progress", function (evt) {
		// console.warn("公用文件:" );
		// console.warn( evt);
		if(progress) try{ progress.call(evt); }catch(e){}
	}, false);

	xhr.responseType = "blob";
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
	xhr.onreadystatechange = function () {
		if (xhr.readyState === 4 && xhr.status === 200) {
			if (typeof window.chrome !== 'undefined') {
				// Chrome version
				var link = document.createElement('a');
				link.href = window.URL.createObjectURL(xhr.response);
				link.download = filename;
				link.click();
			} else if (typeof window.navigator.msSaveBlob !== 'undefined') {
				// IE version
				var blob = new Blob([xhr.response], { type: 'application/force-download' });
				window.navigator.msSaveBlob(blob, filename);
			} else {
				// Firefox version
				var file = new File([xhr.response], filename, { type: 'application/force-download' });
				window.open(URL.createObjectURL(file));
			}
			if(success) try{ success.call(xhr);	defer.resolve(xhr);}catch(e){
			}
		} else if (xhr.status == 404 || xhr.status == 500) {
			defer.reject("啊哦, 没有当月的文件(′⌒`) ");
		}

	};
	// FormData
	//var formData = new FormData();
	var paramsStr = '';
	if(params) for (var key in params) paramsStr += '&'+key+'='+params[key];
	if(paramsStr) paramsStr = paramsStr.substring(1);

	xhr.send(paramsStr);
	return defer;
}
$(function () {
	/* 放弃自动查找 */
	Dropzone.autoDiscover = false;
});
/* 配置上传文件 */
function initDropzone(params) {

	/* jquery方式会提示错误消息*/
	var size = 0;
	$("#"+params.id+"").dropzone({
		url: baseURL + 'fileAction/webuploadfile',
		//设置缩略图的缩放比例
		// thumbnailHeight: 120,
		// thumbnailWidth: 120,
		paramName: "file",
		maxFilesize: params.fileSize, // 上传的文件大小  MB
		timeout: 9999999999999999,
		maxFiles: params.fileNum,//  文件数量上限
		parallelUploads: params.fileNum, //  一次上传的文件数量
		acceptedFiles: params.fileType,
		addRemoveLinks: "dictRemoveFile",//添加移除文件
		autoProcessQueue: false,//不自动上传
		dictCancelUploadConfirmation: '你确定要取消上传吗？',
		dictFallbackMessage: '不好意思，您的浏览器不支持！',
		dictMaxFilesExceeded: "只能上传{{maxFiles}}个文件",
		dictFileTooBig: "文件过大({{filesize}}MB). 上传文件单个最大支持: {{maxFilesize}}MB.",
		dictDefaultMessage:
			'<span class="bigger-150 bolder" style="font-weight: 400;"><i class="icon-caret-right red"></i> 拖动文件至该处</span> \
            <span class="smaller-80 grey"></span> <br /> \
            <i class="upload-icon icon-cloud-upload blue icon-3x"></i>',
		// previewTemplate: $('#preview-template').html(),
		dictResponseError: '文件上传失败!',
		dictInvalidFileType: "你不能上传该类型文件,文件类型只能是"+params.fileType,
		dictCancelUpload: "取消上传",
		dictRemoveFile: "移除文件",
		uploadMultiple: false,
		init: function () {
			//上传文件时触发的事件
			this.on("addedfile", function (file) {
				// console.warn("文件" + JSON.stringify(file));
				size += file.upload.total;
				// console.warn("总大小" + size);
				var total = ((size / (1024 * 1024)).toFixed(1));
				var cul = (((size - file.upload.total) / (1024 * 1024)).toFixed(2)) + "M";
				// console.warn(params.totalSize < total)
				if (params.totalSize < total) {
					alert("文件总大小超出限制,总大小: " + params.totalSize + "M," + "当前" + cul);
					this.removeFile(file);
				}
			});
			//上传文件成功时触发的事件
			this.on("success", function (file, res) {
				console.warn(JSON.parse(res));``
				var data = JSON.parse(res);
				if (data.error == 500) {
					alert("上传文件失败");
				} else {
					$("#"+"file"+params.id+"").append(data.gid + ",");
				}
			});
			//上传完成时触发的事件
			this.on("complete", function (file) {
				size = 0;
			});
			//移除文件触发的事件
			this.on("removedfile", function (file) {
				size = size - file.size;
			});
			this.on("maxfilesexceeded", function(file){
				//当文件数量超过限制时发生
				//删除超过限制的文件
				//this.removeFile(file);
				//console.log(file.name);
			});
		}
	});
}
