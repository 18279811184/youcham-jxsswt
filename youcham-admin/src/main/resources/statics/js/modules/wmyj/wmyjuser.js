$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'wmyj/wmyjuser/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'ID_', width: 50, key: true },
			{ label: '', name: 'rev', index: 'REV_', width: 80 }, 			
			{ label: '', name: 'first', index: 'FIRST_', width: 80 }, 			
			{ label: '', name: 'last', index: 'LAST_', width: 80 }, 			
			{ label: '', name: 'email', index: 'EMAIL_', width: 80 }, 			
			{ label: '', name: 'pwd', index: 'PWD_', width: 80 }, 			
			{ label: '', name: 'pictureId', index: 'PICTURE_ID_', width: 80 }			
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
		wmyjUser: {},
		q:{}
	},
	methods: {
		query: function () {
			vm.reload(true);
		},
		add: function(){
			vm.title = "新增";
			vm.wmyjUser = {};
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
		
			var url = vm.wmyjUser.id == null ? "wmyj/wmyjuser/save" : "wmyj/wmyjuser/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.wmyjUser),
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
				    url: baseURL + "wmyj/wmyjuser/delete",
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
			$.get(baseURL + "wmyj/wmyjuser/info/"+id, function(r){
                vm.wmyjUser = r.wmyjUser;
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