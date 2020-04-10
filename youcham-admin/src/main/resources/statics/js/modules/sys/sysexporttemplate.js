
$(function () {

	console.log(T.p('allids')+"获得有ids");
	//var gn = $('#getallfileid', window.parent.document).html();
	var gn = T.p('allids');
	if(gn == ""|| gn == null){
		gn = null;
	}

    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysexporttemplate/list',
        datatype: "json",
		postData:{"exportModuleId":gn},
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden:true },
			{ label: '所属课程', name: 'exportModule.name', index: 'export_module_id', width: 80 },
			{ label: '学分', name: 'name', index: 'name', width: 80 }	,
			{ label: '文件信息名称', name: 'filename', index: 'filename', width: 80 },
			{ label: '文件信息路径', name: 'filepath', index: 'filepath', width: 80 ,formatter:function(value, options, row){
				return '<a href="../../fileAction/downloadFileByPath?filepath='+encodeURIComponent(value)+'">'+value+'</a>';
			}}, 
			{ label: '文件类型', name: 'type', index: 'Type', width: 80 ,formatter:function(value, options, row){
				return value==0?'Excel':'Word';
			}}, 
			// { label: '模板类型', name: 'mbtype', index: 'mbType', width: 80 ,formatter:function(value, options, row){
			// 	return value==0?'公共':'私有';
			// }},
			{ label: '课程创建人', name: 'createUser', index: 'create_id', width: 50 },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			// { label: '状态 ', name: 'status', index: 'status', width: 50 ,formatter:function(value, options, row){
			// 	return value === 0 ?
			// 			'<span class="label label-danger">待审核</span>' :
			// 			'<span class="label label-success">已审核</span>';
			// }},
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
		sysExportTemplate: {},
		exportModules:[],
		q:{},
		showStatus:false,
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showStatus=false;
			vm.title = "新增";
			vm.sysExportTemplate = {exportModuleId:"",status:0,mbtype:1};
			$('#inputfile')[0].value="";
			vm.addLayer();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showStatus=true;
            vm.title = "修改";
            vm.getInfo(id);
            $('#inputfile')[0].value="";
            
            vm.addLayer();
		},
		saveOrUpdate: function (event) {
			var url = vm.sysExportTemplate.id == null ? "sys/sysexporttemplate/save" : "sys/sysexporttemplate/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysExportTemplate),
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
		saveOrUpdate2: function (event) {			
				var url = vm.sysExportTemplate.id == null ? "sys/sysexporttemplate/saveFileAndData" : "sys/sysexporttemplate/updateFileAndData";
				var formData = new FormData();
				formData.append('file', $('#inputfile')[0].files[0]);
				
				if(vm.sysExportTemplate.id != null){
					formData.append('id', vm.sysExportTemplate.id);
				} 
				formData.append('name', isBlank(vm.sysExportTemplate.name)?"":vm.sysExportTemplate.name);
				formData.append('remark', isBlank(vm.sysExportTemplate.remark)?"":vm.sysExportTemplate.remark);
				formData.append('status', vm.sysExportTemplate.status);
				formData.append('exportModuleId', vm.sysExportTemplate.exportModuleId);
				formData.append('type', vm.sysExportTemplate.type);
				formData.append('mbtype', vm.sysExportTemplate.mbtype);
				$.ajax({
					type: "POST",
				    url: baseURL + url,
				    data: formData,
	                processData:false,
	                contentType : false,
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
				    url: baseURL + "sys/sysexporttemplate/delete",
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
		},choose: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要选中该记录？', function(){
				$.ajax({
					type: "POST",
					url: baseURL + "sys/sysexporttemplate/choose",
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
			$.get(baseURL + "sys/sysexporttemplate/info/"+id, function(r){
                vm.sysExportTemplate = r.sysExportTemplate;
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
		getExportModules: function(){
			$.getJSON(baseURL+"sys/sysexportmodule/select",function(r){
				vm.exportModules = r;
			});
		},
		addLayer: function(){			
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['570px', '420px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#addLayer"),	
                btn: ['确定', '取消'],
                btn1: function (index) {
            		if(isBlank(vm.sysExportTemplate.type)&&vm.sysExportTemplate.type!=0){
        				alert("请选择文件类型！！！");
        				return
        			}
                	layer.load();
                	vm.saveOrUpdate2();
                },
                end: function (layero, index) {
                	vm.reload();
                }
            });
        }
		
	},
	created:function(){
		this.getExportModules();
	}
});