$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysexportparameter/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true ,hidden:true},
			{ label: '所属导出模块', name: 'exportModule.name', index: 'export_module_id', width: 80 }, 			
			{ label: '参数名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '参数值', name: 'value', index: 'value', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }			
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
		sysExportParameter: {},
		exportModules:[],
		q:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.title = "新增";
			vm.sysExportParameter = {exportModuleId:""};
			vm.addLayer();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
            vm.title = "修改";
            
            vm.getInfo(id)
            
            vm.addLayer();
		},
		saveOrUpdate: function (event) {
			var url = vm.sysExportParameter.id == null ? "sys/sysexportparameter/save" : "sys/sysexportparameter/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysExportParameter),
			    success: function(r){
			    	layer.closeAll("loading");
			    	if(r.code === 0){
			    		layer.closeAll();
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
				    url: baseURL + "sys/sysexportparameter/delete",
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
			$.get(baseURL + "sys/sysexportparameter/info/"+id, function(r){
                vm.sysExportParameter = r.sysExportParameter;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{"name":vm.q.name,"exportModuleId":vm.q.exportModuleId}
            }).trigger("reloadGrid");
		},
		addLayer: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['550px', '420px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#addLayer"),	
                btn: ['确定', '取消'],
                btn1: function (index) {
                	layer.load();
                	vm.saveOrUpdate();
                },
                end: function (layero, index) {
                	vm.reload();
                }
            });
        },
        getExportModules: function(){
			$.getJSON(baseURL+"sys/sysexportmodule/select",function(r){
				vm.exportModules = r;
			});
		},

	},
	created:function(){
		this.getExportModules();
	}
});