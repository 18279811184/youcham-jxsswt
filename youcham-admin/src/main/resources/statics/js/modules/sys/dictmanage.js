var type= T.p('type');
var id= T.p('id');
console.log(type+"======"+id);
var dicname = "";
var dictype = "";
/*vm.q.type = type;*/
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/dict/list',
        postData:{'type': type,"id": id},
        datatype: "json",
        colModel: [			
			{ label: '字典名称', name: 'name', index: 'name', width: 80 ,
				formatter: function(value, options, row){
					dicname = value;
					return value; 
			}		
			},
			{ label: '字典类型', name: 'type', index: 'type', width: 80 ,
				formatter: function(value, options, row){
					dictype = value;
					return value; 
			}		
			}, 			
			{ label: '字典码', name: 'code', index: 'code', width: 80 }, 			
			{ label: '字典值', name: 'value', index: 'value', width: 80 ,
				formatter: function(value, options, row){
						return '<a  href="javaScript:void(0)" onclick="second('+row.id+')">'+value+'</a>' ; 
				}		
			}, 			
//			{ label: '备注', name: 'remark', index: 'remark', width: 80 },	
			{ label: '排序', name: 'orderNum', index: 'order_num', width: 30 }, 		
			//{ label: 'aprentid', name: 'parentId', index: 'parent_id', width: 30 } 		
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
        q:{
            name: null,
            parentId: null,
            type:null,
        },
		showList: true,
		title: null,
		dict: {},
		dicts:[],
		parentId:0
		
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
//			vm.showList = false;
			
			vm.title = "新增";
			vm.addLayer();
			vm.dict = {parentId: "0",orderNum:0,name:dicname,type:dictype};
			vm.dict.parentId =  vm.parentId;
			 if(vm.dict.parentId == "0"){
	             	if(vm.dict.type != null){
	             		$("#dictype").attr("disabled","disabled");
	             	}
	         }else{
	        	 $("#dictype").removeAttr("disabled");
	         }
			this.getMaxOrderNum();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
//			vm.showList = false;
            vm.title = "修改";
            vm.addLayer();
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			console.log(vm.dict.parentId);
			if(vm.dict.parentId === "0"){
				if(vm.dict.type == null||vm.dict.type == ""){
					alert("字典类型不能为空");
					return false;
				}
			}
			
			var url = vm.dict.id == null ? "sys/dict/save" : "sys/dict/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dict),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							 vm.q.type = type;
							 vm.q.parentId = null;
							vm.reload();
							vm.getDicts();
							layer.closeAll();
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
				    url: baseURL + "sys/dict/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								 vm.q.type = type;
								 vm.q.parentId = null;
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		back: function(){
			 vm.q.type = type;
			 vm.q.parentId = null;
			 vm.reload();
			
		},
		getInfo: function(id){
			$.get(baseURL + "sys/dict/info/"+id, function(r){
                vm.dict = r.dict;
                console.log(r.dict.parentId +"========="+r.dict.type);
               
            });
		},
		getDicts: function(){
			$.get(baseURL + "sys/dict/select", function(r){
                vm.dicts = r.list;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'nvalue': vm.q.name,"parentId": vm.q.parentId,'type': vm.q.type,"id": id},
                page:page
            }).trigger("reloadGrid");
		},
		changeParent: function(){
			if(vm.dict.parentId==0){
				vm.dict.name = "";
				vm.dict.id = "";
			}else{
				var item = $("#dictParent").find("option:selected").data("item");
				vm.dict.name = "【"+item.name+"】"+item.value;
				vm.dict.type = item.type + "_" + item.id ;
			}
			
			this.getMaxOrderNum();
		},
		getMaxOrderNum: function(){
        	$.get(baseURL+"sys/dict/getMaxOrderNum",{parentId:vm.dict.parentId},function(r){
        		vm.dict.orderNum = r.maxOrderNum*1 + 1;
        	});
        },
        addLayer: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['650px', '350px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#addLayer"),	
                btn: ['确定', '取消'],
                btn1: function (index) {
                	vm.saveOrUpdate();
                },
                end: function (layero, index) {
                	//vm.reload();
                }
            });
        }
        
	},
	created: function(){
		this.getDicts();
	}
});


function second(id){
	 vm.q.type = null;
	 vm.q.parentId = id;
	 //給父级id赋值
	 vm.parentId = id;

	  dicname = "";
	  dictype = "";
	 
	vm.reload();
	
}