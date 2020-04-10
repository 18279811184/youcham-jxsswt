$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ins/inssuggestions/list',
        datatype: "json",
        colModel: [			
			{ label: 'suggestId', name: 'suggestId', index: 'suggest_id', width: 50, key: true,hidden:true },
			//{ label: '日常工作', name: 'rouTine', index: 'rou_tine', width: 80 }, 			
			{ label: '姓名', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '电话', name: 'userPhone', index: 'user_phone', width: 80 }, 			
			{ label: '建议标题', name: 'sugTitle', index: 'sug_title', width: 80 }, 			
			{ label: '建议内容', name: 'sugContent', index: 'sug_content', width: 80 }, 
			{ label: '状态', name: 'statu', index: 'statu', width: 80 ,
				formatter: function(value, options, row){
					if(value=='0'){
						return '不通过'
					}else if(value=='1'){
						return '通过'
					}else{
						return '待审核'
					}
				}	
			}, 	
			{ label: '审核意见', name: 'shyj', index: 'shyj', width: 80 }, 	
			{ label: '附件', name: 'sugEnclosure', index: 'sug_enclosure', width: 80, formatter(value,option,row){
				return '<a href="javascript:;" onclick="loadImg(\''+value+'\');">查看</a>';
			}}, 			
			//{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			//{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			//{ label: '更新人', name: 'updateUserid', index: 'update_userid', width: 80 }, 			
			//{ label: '意见建议', name: 'sugTions', index: 'sug_tions', width: 80 }, 			
			//{ label: '版本号', name: 'dataVersion', index: 'data_version', width: 80 }, 			
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
            order: "order"
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
		insSuggestions: {},
		q:{
			sugtitle: null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			//vm.showList = false;
			vm.title = "新增";
			vm.insSuggestions = {};
			showadd("新增");
		},
		shenhe: function(){
			var suggestId = getSelectedRow();
			if(suggestId == null){
				return ;
			}
			 vm.getInfo(suggestId);
			layer.open({
				type : 1,
				offset : '50px',
				skin : 'layui-layer-molv',
				title : "审核",
				area : [ '700px', '420px' ],
				shade : 0.2,
				shadeClose : false,
				content : jQuery("#showyj"),
				btn : [ '确定', '取消' ],
				success:function(layero, index){
					//layer.iframeAuto(index)
				},
				yes: function(index,layero){
		             //vm.saveOrUpdate();
					vm.saveOrUpdate();
		             layer.close(index);
		             
				},
				end:function(layero, index){
				}   
				
			});
		},
		update: function (event) {
			var suggestId = getSelectedRow();
			if(suggestId == null){
				return ;
			}
		//	vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(suggestId);
            showadd(vm.title);
		},
		saveOrUpdate: function (event) {
			var url = vm.insSuggestions.suggestId == null ? "ins/inssuggestions/save" : "ins/inssuggestions/update";
			console.log(vm.insSuggestions);
			if(vm.insSuggestions.sugEnclosure = ""){
				vm.insSuggestions.sugEnclosure = $("#fujian").val();
			}
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.insSuggestions),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							layer.closeAll();
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var suggestIds = getSelectedRows();
			if(suggestIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ins/inssuggestions/delete",
                    contentType: "application/json",
				    data: JSON.stringify(suggestIds),
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
		getInfo: function(suggestId){
			$.get(baseURL + "ins/inssuggestions/info/"+suggestId, function(r){
                vm.insSuggestions = r.insSuggestions;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'sugtitle': vm.q.sugtitle},
                page:page
            }).trigger("reloadGrid");
		}
	}
});


//新增修改  弹出
function showadd(title){
	layer.open({
		type : 1,
		offset : '50px',
		skin : 'layui-layer-molv',
		title : vm.title,
		area : [ '700px', '400px' ],
		shade : 0.2,
		shadeClose : false,
		content : jQuery("#layshow"),
		btn : [ '确定', '取消' ],
		success:function(layero, index){
			//layer.iframeAuto(index)
		},
		yes: function(index,layero){
             vm.saveOrUpdate();
            // layer.close(index);
             
		},
		end:function(layero, index){
		}   
		
	});
}

//加载附件
function loadImg(src){
	if(src==""||src==null){
		alert("暂无附件");
		return false;
	}
	layer.open({
		type : 2,
		offset : '50px',
		maxmin:true,
		skin : 'layui-layer-molv',
		title : '查看附件',
		area : [ '700px', '400px' ],
		shade : 0.2,
		shadeClose : false,
		content : src,
		btn : [ '关闭']//,
//		yes: function(index,layero){
//            vm.saveOrUpdate();
//		}
	});
}
