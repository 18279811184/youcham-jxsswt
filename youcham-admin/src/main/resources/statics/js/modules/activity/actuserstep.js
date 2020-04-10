$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/actuserstep/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'ID', width: 50, key: true ,hidden:true},
			{ label: '指定人key', name: 'reserveOne', index: 'RESERVE_ONE', width: 80 },
			{ label: '流程定义名称(key)', name: 'lcKey', index: 'LC_KEY', width: 80 ,
				formatter: function(value, options, row){
					return ""+row.lcKeyName+"("+row.lcKey+")";
				}	
			}, 			
			{ label: '步骤说明', name: 'step', index: 'STEP', width: 80 }, 			
			{ label: '指定人的集合', name: 'appointname', index: 'APPOINT', width: 80 }, 	
			// { label: '公司名称', name: 'comAffname', index: 'comAff', width: 80 },
			{ label: '排序', name: 'reserveTwo', index: 'reserveTwo', width: 40 }, 
			// { label: '公司id', name: 'comAff', index: 'comAff', width: 80 ,hidden:true},
			/*{ label: '预留字段1', name: 'reserveOne', index: 'RESERVE_ONE', width: 80 }, 			
			{ label: '预留字段2', name: 'reserveTwo', index: 'RESERVE_TWO', width: 80 }*/			
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
		actUserStep: {},
		q:{},
		uid:null,
		levels:[],
		dept:{
            parentName:null,
            parentId:0,
            orderNum:0,
            level:"-1"
        },
	},
	methods: {
		query: function () {
			vm.reload(true);
		},
		add: function(){
			vm.title = "新增";
			vm.actUserStep = {appoint:null};
			vm.dept = {
	            parentName:null,
	            parentId:0,
	            orderNum:0,
	            level:"-1"
	        };
			  vm.getDept3();
			vm.addLayer();
		},
		getlckey:function(event){
			$.get(baseURL + "activity/getProcessDefinitions?limit=9999", function(r){
				console.log(r.page.list);
                vm.levels = r.page.list;
            });
		},
		 getDept3: function(){
	            //加载部门树
	            $.get(baseURL + "sys/dept/list", function(r){
	                ztree3 = $.fn.zTree.init($("#deptTree3"), setting, r);
	                var node = ztree3.getNodeByParam("deptId", vm.actUserStep.comAff);
	                console.log(r);
	                if(node != null){
	                    ztree3.selectNode(node);
	                    console.log("fuji--"+node.name);
	                    vm.dept.parentName = node.name;
	                }
	            })
	        },
		glgs:function(event){
			//公司树
			 layer.open({
	                type: 1,
	                offset: '50px',
	                skin: 'layui-layer-molv',
	                title: "选择部门",
	                area: ['300px', '450px'],
	                shade: 0,
	                shadeClose: false,
	                content: jQuery("#deptLayer2"),
	                btn: ['确定', '取消'],
	                btn1: function (index) {
	                    var node = ztree3.getSelectedNodes();
	                    //选择上级部门
	                    vm.dept.parentId = node[0].deptId;
	                    vm.dept.parentName = node[0].name;
                        vm.actUserStep.comAff = node[0].deptId;
	                    layer.close(index);
	                }
	            });
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
            vm.title = "修改";
           /* vm.dept = {
    	            parentName:null,
    	            parentId:0,
    	            orderNum:0,
    	            level:"-1"
    	        };*/
    	   
            
            vm.getInfo(id);
            
            vm.addLayer();
		},
		getuser: function (event) {
			layer.open({
                type: 2,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['850px', '420px'],
                shade: 0.2,
                shadeClose: false,
                content: baseURL+'modules/sys/user2.html?topic='+vm.actUserStep.appoint,	
                btn: ['确定', '取消'],
                btn1: function (index,layero) {
                	//var body = layer.getChildFrame('body', index);
                    //var iframeWin = window[layero.find('iframe')[0]['name']];
                    // var ids = iframeWin.getuid();
                	
                    // 获取弹出层选中的值
					var body = layer.getChildFrame('body',index);									
					var	myid = body.find('#getallid').html();							
					console.log("选中层的值为====="+myid);
                    
                   
                   // vm.uid = ids;
                    vm.actUserStep.appoint = myid;
                    console.log(vm.actUserStep);
                    layer.close(index);
                }
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.actUserStep.id == null ? "sys/actuserstep/save" : "sys/actuserstep/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.actUserStep),
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
				    url: baseURL + "sys/actuserstep/delete",
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
			$.get(baseURL + "sys/actuserstep/info/"+id, function(r){
                vm.actUserStep = r.actUserStep;
                vm.getDept3();
                if(vm.actUserStep.comAff == ""||vm.actUserStep.comAff == null){
                	vm.dept.parentName = "";
                }
              //选择上级部门
               //vm.dept.parentId =  vm.actUserStep.comAff;
              //  vm.dept.parentName = "test";
             //   vm.actUserStep.comAff = node[0].deptId;
                
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
           		postData:{'zdr': vm.q.zdr,'lcdy': vm.q.lcdy}
            }).trigger("reloadGrid");
		},
		addLayer: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['600px', '400px'],
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
	},created:function(){
		this.getlckey();
	}
});

var setting = {
	    data: {
	        simpleData: {
	            enable: true,
	            idKey: "deptId",
	            pIdKey: "parentId",
	            rootPId: -1
	        },
	        key: {
	            url:"nourl"
	        }
	    }
	};
	var ztree;

