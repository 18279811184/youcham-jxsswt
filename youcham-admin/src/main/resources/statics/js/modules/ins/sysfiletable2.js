	var nodel = T.p('nodel');
	console.log("nodel--"+nodel);
	if(nodel == ""|| nodel == null|| nodel == "undefined"){
		nodel = false;
	}else{
		nodel = true;
	}
	console.log("nodel--"+nodel);
	
	var disTrue=T.p("disTrue");
	console.log("禁用input==========="+disTrue);
	if(disTrue==1){
		$("input").attr("disabled","true");
		$("a").hide();
		$("#aaaa").show();		
		$("select").attr("disabled","true");
	}
	
	var hdGuanJianId = T.p('hdGuanJianId');
	console.log("父页面传过来的关检id==========="+hdGuanJianId);
$(function () {
	console.log(T.p('getallid')+"获得有ids");
	//var gn = $('#getallfileid', window.parent.document).html();
	var gn = T.p('getallid');
	if(gn == ""|| gn == null){
		gn = "0";
	}

    $("#jqGrid").jqGrid({
        url: baseURL + 'ins/sysfiletable/list',
    	postData:{"getallid":gn,"hdGuanJianId":hdGuanJianId},
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true,hidden:true },
			//{ label: '文件序号', name: 'fileXh', index: 'file_xh', width: 80 }, 			
			{ label: '文件名称', name: 'fileName', index: 'file_name', width: 80 ,
				formatter: function(value, options, row){
					//console.log(row);
					return "<a href='javaScript:void(0)' onclick='downloadFile(\""+row.id+"\")'>"+value+"</a>";
				}
			}, 			
			{ label: '文件真实名称', name: 'fileRealname', index: 'file_realname', width: 80 }, 		
			{ label: '文件类型', name: 'fileType', index: 'file_type', width: 80 }, 			
			{ label: '文件备注', name: 'fileRemark', index: 'file_remark', width: 80 }, 			
			{ label: '创建人', name: 'createUser.name', index: 'createUser.name', width: 80 }, 			
			{ label: '创建时间', name: 'fileCreatedate', index: 'file_createdate', width: 80 }, 			
			//{ label: '删除标识', name: 'beDelete', index: 'be_delete', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order",
           
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysFiletable: {},
		q:{
			filebz: null
		},
		nodel:nodel,
		hdGuanJian:{},
		showScdz:false,
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysFiletable = {};
			//console.log("点击了新增---");
			//console.log($('#getallfileid', window.parent.document).html());
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysFiletable.id == null ? "ins/sysfiletable/save" : "ins/sysfiletable/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysFiletable),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}	
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ins/sysfiletable/delete?hdGuanJianId="+hdGuanJianId,
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								parent.vm.getInfo(hdGuanJianId);
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "ins/sysfiletable/info/"+id, function(r){
                vm.sysFiletable = r.sysFiletable;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'fileRemark': vm.q.filebz},
                page:page
            }).trigger("reloadGrid");
		},
		getFileIds: function(){
			if(isBlank(hdGuanJianId)){
				alert("请先添加一条报关报检数据！！！");
				return;
			}
			$.get(baseURL + "hd/hdguanjian/info/"+hdGuanJianId, function(r){
				vm.hdGuanJian=r.hdGuanJian;
				layer.open({
					type : 2,
					skin : 'layui-layer-molv',
					title : "审核单证列表",
					area : [ '800px', '400px' ],
					shadeClose : false,
					content : baseURL + 'modules/sys/sysfiletable.html?getallid='+vm.hdGuanJian.fileIds,
					btn : [ '确定', '取消' ],
					btn1 : function(index) {
						// 获取弹出层选中的值
						var res = window["layui-layer-iframe" + index].getSelectedRows();
						if(res == null){
							return ;
						}
						//拼接成ids
						var sfdzIds="";
						$.each(res, function(index,value){      
							sfdzIds+=value+","
						}); 
						if(isBlank(vm.hdGuanJian.sfDanzhengIds)){
							vm.hdGuanJian.sfDanzhengIds=sfdzIds;
						}else{
							vm.hdGuanJian.sfDanzhengIds=vm.hdGuanJian.sfDanzhengIds+sfdzIds;
						}						
						$.ajax({
							type: "POST",
						    url: baseURL + "hd/hdguanjian/update",
			                contentType: "application/json",
						    data: JSON.stringify(vm.hdGuanJian),
						    success: function(r){
						    	layer.closeAll("loading");
						    	if(r.code === 0){
						    		layer.closeAll();					    	
									alert('操作成功', function(index){
										parent.vm.getInfo(hdGuanJianId);								
									});
								}else{
									alert(r.msg);
								}
							}
						});
						
					},
					btn2 : function(index, layero) {
							layer.close(index);
					}
				});
			})
			
		},
	},
	mounted: function(){
		if(isBlank(hdGuanJianId)){
			this.showScdz=false;
		}else{
			this.showScdz=true;
		}
	}
});


//下载文件

function downloadFile(bytearrayId){


	if(bytearrayId == "" || bytearrayId == null){

		//$("#"+aid).dblclick();
	}else{
		
	window.location.href=baseURL +'fileAction/downloadFile?bytearrayId='+bytearrayId;	
		/*$.get(baseURL +'fileAction/downloadFile?bytearrayId='+bytearrayId,{contentType: false},function(r){
            console.log("下载成功");
        });*/
	}
	
}

function fileUploadCallback(mark) {
	console.log("拿到的gid"+$("#getallfileid").html());
	
	// 指标为必须上传附件且未传
	if(isBlank($("#getallfileid").html())||$("#getallfileid").html()=='undefined'){
		layer.alert("还未上传附件，请选择文件后点击开始上传");
		return false;
	}
	
	/*if(isBlank(mark)){
		layer.alert("备注不得为空，应描述清晰 例如：2018-01");
		return false;
	}*/
	var ids = $("#getallfileid").html();
	$.get(baseURL + "hd/hdguanjian/info/"+hdGuanJianId, function(r){
		if(isBlank(r.hdGuanJian.sfDanzhengIds)){
			r.hdGuanJian.sfDanzhengIds=ids;
		}else{
			r.hdGuanJian.sfDanzhengIds=r.hdGuanJian.sfDanzhengIds+ids;
		}
		$.ajax({
			type: "POST",
		    url: baseURL + "hd/hdguanjian/update",
            contentType: "application/json",
		    data: JSON.stringify(r.hdGuanJian),
		    success: function(r){
		    	layer.closeAll("loading");
		    	if(r.code === 0){
		    		layer.closeAll();					    	
					alert('操作成功', function(index){
						parent.vm.getInfo(r.hdGuanJianId);								
					});
				}else{
					alert(r.msg);
				}
			}
		});
	})
	$("#getallfileid").html("");
	
	return true;
};
