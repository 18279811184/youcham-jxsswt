$(function () {
    $("#jqGrid").jqGrid({
        url: youchamapi + '/ins/inssuggestions/list',
        datatype: "json",
        colModel: [			
			{ label: 'suggestId', name: 'suggestId', index: 'suggest_id', width: 50, key: true },
			{ label: '日常工作', name: 'rouTine', index: 'rou_tine', width: 80 }, 			
			{ label: '姓名', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '电话', name: 'userPhone', index: 'user_phone', width: 80 }, 			
			{ label: '建议标题', name: 'sugTitle', index: 'sug_title', width: 80 }, 			
			{ label: '建议内容', name: 'sugContent', index: 'sug_content', width: 80 }, 			
			{ label: '附件', name: 'sugEnclosure', index: 'sug_enclosure', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			{ label: '更新人', name: 'updateUserid', index: 'update_userid', width: 80 }, 			
			{ label: '意见建议', name: 'sugTions', index: 'sug_tions', width: 80 }, 			
			{ label: '版本号', name: 'dataVersion', index: 'data_version', width: 80 }, 			
			{ label: '删除标识', name: 'beDelete', index: 'be_delete', width: 80 }			
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
		insSuggestions: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.insSuggestions = {};
		},
		update: function (event) {
			var suggestId = getSelectedRow();
			if(suggestId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(suggestId)
		},
		saveOrUpdate: function (event) {
			var url = vm.insSuggestions.suggestId == null ? "/ins/inssuggestions/save" : "/ins/inssuggestions/update";
			console.log(vm.insSuggestions);
			
			vm.insSuggestions.sugEnclosure = $("#fujian").val();
			$.ajax({
				type: "POST",
			    url: youchamapi + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.insSuggestions),
			    success: function(r){
			    	if(r.code === 0){
						alert('提交成功,请等待审核', function(index){
//							vm.reload();
							location.reload();
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
				    url: youchamapi + "/ins/inssuggestions/delete",
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
			$.get(youchamapi + "/ins/inssuggestions/info/"+suggestId, function(r){
                vm.insSuggestions = r.insSuggestions;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});