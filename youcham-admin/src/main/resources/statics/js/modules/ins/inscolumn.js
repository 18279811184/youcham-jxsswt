$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ins/inscolumn/list',
        datatype: "json",
        colModel: [			
			{ label: 'columnId', name: 'columnId', index: 'column_id', width: 50, key: true,hidden:true },
			//{ label: '图标路径', name: 'columnIcon', index: 'column_icon', width: 80 }, 			
			{ label: '栏目名称', name: 'columnName', index: 'column_name', width: 80 }, 			
			{ label: '栏目状态', name: 'columnStatus', index: 'column_status', width: 80 ,
				formatter: function(value, options, row){
					return value === 1 ? 
							'<span class="label label-success">启用</span>' : 
							'<span class="label label-danger ">停用</span>';
				}	
			}, 			
			{ label: '栏目备注', name: 'columnDesc', index: 'column_desc', width: 80 }, 			
			//{ label: '所属上级栏目', name: 'columnParent', index: 'column_parent', width: 80 }, 			
			
			//{ label: '是否显示在首页，0为否，1为是', name: 'beHome', index: 'be_home', width: 80 }, 			
			//{ label: '创建人部门ID', name: 'departId', index: 'depart_id', width: 80 }, 			
			//{ label: '创建用户ID', name: 'createId', index: 'create_id', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			//{ label: '更新用户ID', name: 'updateId', index: 'update_id', width: 80 }, 			
			//{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			//{ label: '数据是否已删除，0为否，1为是（在回收站），2为从回收站清空', name: 'beDelete', index: 'be_delete', width: 80 }, 			
			//{ label: '版本号', name: 'dataVersion', index: 'data_version', width: 80 }	
			{ label: '排序编号', name: 'columnOrder', index: 'column_order', width: 80 }		
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
		insColumn: {},
		q:{
			columnname: null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			//vm.showList = false;
			vm.title = "新增";
			vm.insColumn = {};
			showadd();
		},
		update: function (event) {
			var columnId = getSelectedRow();
			if(columnId == null){
				return ;
			}
			//vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(columnId);
            showadd();
		},
		saveOrUpdate: function (event) {
			var url = vm.insColumn.columnId == null ? "ins/inscolumn/save" : "ins/inscolumn/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.insColumn),
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
			var columnIds = getSelectedRows();
			if(columnIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ins/inscolumn/delete",
                    contentType: "application/json",
				    data: JSON.stringify(columnIds),
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
		getInfo: function(columnId){
			$.get(baseURL + "ins/inscolumn/info/"+columnId, function(r){
                vm.insColumn = r.insColumn;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'columnname': vm.q.columnname},
                page:page
            }).trigger("reloadGrid");
		}
	}
});
//新增修改  弹出
function showadd(){
	layer.open({
		type : 1,
		offset : '50px',
		skin : 'layui-layer-molv',
		title : vm.title,
		area : [ '800px', '400px' ],
		shade : 0.2,
		shadeClose : false,
		content : jQuery("#layshow"),
		btn : [ '确定', '取消' ],
		yes: function(index,layero){
             vm.saveOrUpdate();
           //  layer.close(index);
             
		},
		end:function(layero, index){
		}   
		
	});



}