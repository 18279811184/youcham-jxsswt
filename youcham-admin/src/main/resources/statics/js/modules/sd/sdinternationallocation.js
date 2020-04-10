$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sd/sdinternationallocation/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'ID', width: 50, key: true },
			{ label: '父id/上级id', name: 'pid', index: 'PID', width: 80 }, 			
			{ label: '路径', name: 'path', index: 'PATH', width: 80 }, 			
			{ label: '层级', name: 'areaLevel', index: 'AREA_LEVEL', width: 80 }, 			
			{ label: '中文名称', name: 'name', index: 'NAME', width: 80 }, 			
			{ label: '英文名称', name: 'nameEn', index: 'NAME_EN', width: 80 }, 			
			{ label: '中文拼音', name: 'namePinyin', index: 'NAME_PINYIN', width: 80 }, 			
			{ label: '地区代码', name: 'code', index: 'CODE', width: 80 }, 			
			{ label: '状态值 0无效 1有效', name: 'status', index: 'STATUS', width: 80 }, 			
			{ label: '百度.纬度', name: 'lat', index: 'LAT', width: 80 }, 			
			{ label: '百度.经度', name: 'lng', index: 'LNG', width: 80 }, 			
			{ label: '创建人id', name: 'createId', index: 'CREATE_ID', width: 80 }, 			
			{ label: '创建人名称', name: 'createName', index: 'CREATE_NAME', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'CREATE_TIME', width: 80 }, 			
			{ label: '创建人id', name: 'updateId', index: 'UPDATE_ID', width: 80 }, 			
			{ label: '创建人名称', name: 'updateName', index: 'UPDATE_NAME', width: 80 }, 			
			{ label: '创建时间', name: 'updateTime', index: 'UPDATE_TIME', width: 80 }, 			
			{ label: '版本号', name: 'version', index: 'VERSION', width: 80 }, 			
			{ label: '逻辑删除字段 0：未删除 -1：已删除', name: 'beDelete', index: 'BE_DELETE', width: 80 }			
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
		sdInternationalLocation: {},
		q:{}
	},
	methods: {
		query: function () {
			vm.reload(true);
		},
		add: function(){
			vm.title = "新增";
			vm.sdInternationalLocation = {};
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
		
			var url = vm.sdInternationalLocation.id == null ? "sd/sdinternationallocation/save" : "sd/sdinternationallocation/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sdInternationalLocation),
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
				    url: baseURL + "sd/sdinternationallocation/delete",
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
			$.get(baseURL + "sd/sdinternationallocation/info/"+id, function(r){
                vm.sdInternationalLocation = r.sdInternationalLocation;
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
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['550px', '420px'],
                shade: 0.2,
                shadeClose: false,
                content: jQuery("#addLayer"),	
                btn: ['确定', '取消'],
                btn1: function (index) {
                	layer.load();
                	vm.saveOrUpdate();
                }
            });
        }
	}
});