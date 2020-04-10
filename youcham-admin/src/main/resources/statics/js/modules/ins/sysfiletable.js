
var nodel = T.p('nodel');

$(function () {
	if(nodel == ""|| nodel == null|| nodel == "undefined"){
		vm.nodel = false;
	}else{
		vm.nodel = true;
	}
	console.log("nodel--"+nodel);
	
	//console.log(T.p('getallid')+"获得有idd");
	//var gn = $('#getallfileid', window.parent.document).html();
	var gn = T.p('getallid');
	if(gn == ""|| gn == null){
		gn = "0";
	}
    $("#jqGrid").jqGrid({
        url: baseURL + 'ins/sysfiletable/list',
    	postData:{"getallid":gn},
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true,hidden:true},
			//{ label: '文件序号', name: 'fileXh', index: 'file_xh', width: 80 }, 			
			{ label: '文件名称', name: 'fileName', index: 'file_name', width: 120 ,
				formatter: function(value, options, row){
					//console.log(row);
					return "<a href='javaScript:void(0)' onclick='loadFile(\""+row.id+"\")'>"+value+"</a>";
				}
			}, 			
			{ label: '文件原名称', name: 'fileRealname', index: 'file_realname', width: 120 },
			{ label: '文件类型', name: 'fileType', index: 'file_type', width: 80 }, 			
			// { label: '文件备注', name: 'fileRemark', index: 'file_remark', width: 80 },
			{ label: '上传人', name: 'createUser.name', index: 'createUser.name', width: 80 }, 			
			{ label: '上传时间', name: 'fileCreatedate', index: 'file_createdate', width: 80 }, 
			{ label: '下载',  width: 80,
				formatter: function(value, options, row){
					//console.log(row);
					return "<a href='javaScript:void(0)' onclick='downloadFile(\""+row.id+"\")'>下载</a>";
				}
			}, 
			//{ label: '删除标识', name: 'beDelete', index: 'be_delete', width: 80 }			
        ],
		viewrecords: true,
        height: 205,
        rowNum: 7,
		rowList : [7,10,30,50],
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
		nodel:nodel
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
				    url: baseURL + "ins/sysfiletable/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
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
		}
	}
});


//下载文件
function downloadFile(bytearrayId){
	
	if(bytearrayId == "" || bytearrayId == null){
	}else{
		window.location.href=baseURL +'fileAction/downloadFile?bytearrayId='+bytearrayId;
	}
}

//预览文件
function loadFile(bytearrayId){
	
	parent.parent.layer.open({
		type : 2,
		offset: '50px',
		skin : 'layui-layer-molv',
		title : "文件预览",
		maxmin:true,
		area : [ '1050px', '550px' ],
		shadeClose : false,
		content : 'fileAction/loadFile?bytearrayId='+bytearrayId,
		btn : ['关闭'],
	/*	btn1 : function(index, layero) {	
			var iframeWin = parent.parent.window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
			iframeWin.before();
		},
		btn2 : function(index, layero) {
			var iframeWin = parent.parent.window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
			iframeWin.after();
			return false;
		},*/
		btn1 : function(index, layero) {										
			parent.parent.layer.close(index);
		}
	});
}
