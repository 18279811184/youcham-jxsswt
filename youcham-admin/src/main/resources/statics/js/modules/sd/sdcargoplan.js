$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sd/sdcargoplan/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'ID', width: 50, key: true ,hidden:true},
			{ label: '计划开始时间', name: 'planStartDate', index: 'PLAN_START_DATE', width: 80 ,hidden:true}, 			
			{ label: '计划结束时间', name: 'planEndDate', index: 'PLAN_END_DATE', width: 80 ,hidden:true}, 		
			{ label: '计划时间', name:'plandate', width: 80,
				formatter:function(value, options, row){
					return row.planStartDate +" 至 "+row.planEndDate;
				}
			}, 	
			{ label: '运输方式', name: 'transTypeDesc', index: 'TRANS_TYPE', width: 40 }, 			
			{ label: '港口名称', name: 'portName', index: 'PORT_NAME', width: 40 }, 			
			{ label: '运行线路-起点', name: 'transLineOrigin', index: 'TRANS_LINE_ORIGIN', width: 80 ,hidden:true}, 			
			{ label: '运行线路-终点', name: 'transLineDestination', index: 'TRANS_LINE_DESTINATION', width: 80 ,hidden:true}, 	
			{ label: '运行线路', name:'transLine', width: 80,
				formatter:function(value, options, row){
					return row.transLineOrigin +" - "+row.transLineDestination;
				}
			},
			{ label: '班期', name: 'schedule', index: 'SCHEDULE', width: 80 }, 			
			{ label: '运营单位', name: 'operateUnit', index: 'OPERATE_UNIT', width: 90 }, 			
			{ label: '联系人', name: 'contectPserson', index: 'CONTECT_PSERSON', width: 30 }, 			
			{ label: '电话', name: 'phoneNumber', index: 'PHONE_NUMBER', width: 50 }, 			
			{ label: '备注', name: 'remark', index: 'REMARK', width: 100 }, 			
			/*{ label: '创建人id', name: 'createId', index: 'CREATE_ID', width: 80 }, 			
			{ label: '创建人名称', name: 'createName', index: 'CREATE_NAME', width: 80 },*/ 			
			{ label: '创建时间', name: 'createTime', index: 'CREATE_TIME', width: 60 }, 			
			/*{ label: '更新人id', name: 'updateId', index: 'UPDATE_ID', width: 80 }, 			
			{ label: '更新人名称', name: 'updateName', index: 'UPDATE_NAME', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'UPDATE_TIME', width: 80 }, 			
			{ label: '版本号', name: 'version', index: 'VERSION', width: 80 }, 			
			{ label: '逻辑删除字段 0：未删除 -1：已删除', name: 'beDelete', index: 'BE_DELETE', width: 80 }		*/	
        ],
		viewrecords: true,
        height: 800,
        rowNum: 20,
		rowList : [20,40,60],
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
            order: "createTime"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    
    // jq validate 校验 (需要校验的字段必须加上name属性/扩充校验写在validate-methods.js中)
    $("#ajaxForm").validate({
        //重写showErrors
        showErrors: function (errorMap, errorList) {
            var msg = "";
            $.each(errorList, function (i, v) {
                //在此处用了layer的方法,显示效果更美观
            	console.log(v.element);
                layer.tips(v.message, v.element, {
                	tips:[1,'#f3857c'], 
                	time: 2000, 
                	tipsMore: true
            	});
            });
        },
        //失去焦点时验证 
        onfocusout:  function (element) { $(element).valid(); }, 
    });
    
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sdCargoPlan: {},
		q:{}
	},
	methods: {
		query: function () {
			vm.reload(true);
		},
		add: function(){
			vm.title = "新增";
			vm.sdCargoPlan = {};
			vm.addLayer();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
            vm.title = "修改";
            
            vm.getInfo(id);
            
            vm.addLayer();
		},
		saveOrUpdate: function (event) {
			// 进行校验
			if(!$("#ajaxForm").valid()){
				layer.closeAll("loading");
				return;
			}
		
			var url = vm.sdCargoPlan.id == null ? "sd/sdcargoplan/save" : "sd/sdcargoplan/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sdCargoPlan),
			    success: function(r){
			    	layer.closeAll("loading");
			    	if(r.code === 0){
			    		layer.closeAll();
						alert('操作成功', function(index){
							$("#jqGrid").trigger("reloadGrid");
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
				    url: baseURL + "sd/sdcargoplan/delete",
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
			$.get(baseURL + "sd/sdcargoplan/info/"+id, function(r){
                vm.sdCargoPlan = r.sdCargoPlan;
            });
		},
		reload: function (event) {
			//vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			if(event){
				page = 1;
			}
			
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
           		postData:{'name': vm.q.name}
            }).trigger("reloadGrid");
		},
		addLayer: function(){
            layer.open({
                type: 2,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['550px', '800px'],
                shade: 0.2,
                shadeClose: false,
                content: baseURL + "modules/mobile/sdcargoplan.html",
            });
        }
	}
});