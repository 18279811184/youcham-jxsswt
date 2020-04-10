var arrayNewList = new Array();
var userIds = T.p('userIds');
if (userIds != "" && userIds != null && userIds != "null"&&userIds!=undefined&&userIds!="undefined") {
	console.log("userIds不为空");
	console.log(userIds);
	var s = userIds;
	arrayNewList = s.split(",");
	$("#getallid").html(arrayNewList.toString());
}
console.log(arrayNewList);

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list3',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
			{ label: '用户名', name: 'username', width: 75 },
			{ label: '姓名', name: 'name', sortable: false, width: 75 },
			{ label: '所属部门', name: 'deptName', sortable: false, width: 75 },
			{ label: '用户类型', name: 'userTypeDesc', sortable: false, width: 75 },
			{ label: '手机号', name: 'mobile', width: 80 },		
			{ label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '单一窗口关联ID', name: 'wxuserId', width: 80 }
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
        },
        loadComplete : function(xhr) {
			console.log(xhr);
			var array = xhr.page.list;
			console.log(array);
			if (arrayNewList.length > 0) {
				$.each(array, function(i, item) {
					if (arrayNewList.indexOf(item.userId) > -1) {
						// 判断arrayNewList中存在item.code值时，选中前面的复选框，
						$("#jqGrid").jqGrid('setSelection',item.userId);
						//$("#jqg_jqGrid_" + item.userId).attr("checked", true);
					}
				});
			}
		},
		onSelectRow : function(aRowids, status) {
			if (status == true) {
				saveData(aRowids);
				console.log(arrayNewList.toString());
				$("#getallid").html(arrayNewList.toString());
			} else {
				deleteIndexData(aRowids);
				console.log(arrayNewList.toString());
				$("#getallid").html(arrayNewList.toString());
			}

		},
		onSelectAll : function(aRowids, status) {							
			if (userIds != "" && userIds != null && userIds != "null") {
				var str = userIds.split(",");		
				console.log("====================");
				console.log(str);	
				//两个集合的并集
				var newIds= array_union(aRowids,str);																							
				console.log(newIds);
				//把该数组的值清空
				arrayNewList = new Array();
				//重新添加该数组集合
				if (status == true) {
					$.each(newIds, function(i, item) {							
						saveData(item);									
					})
				} else {
					$.each(newIds, function(i, item) {
						deleteIndexData(item);
					})
				}
			}else{
				if (status == true) {
					$.each(aRowids, function(i, item) {
						saveData(item);
					})
				} else {
					$.each(aRowids, function(i, item) {
						deleteIndexData(item);
					})
				}
			}																			
			console.log(arrayNewList.toString());
			$("#getallid").html(arrayNewList.toString());
		},
        
    });
});

function saveData(obj) {
	arrayNewList.push(obj);
}
function deleteIndexData(obj) {
	// 获取obj在arrayNewList数组中的索引值
	for (i = 0; i < arrayNewList.length; i++) {
		if (arrayNewList[i] == obj) {
			// 根据索引值删除arrayNewList中的数据
			// arrayNewList.remove(i);
			arrayNewList.splice($.inArray(obj, arrayNewList), 1);
			i--;
		}
	}
}

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

var setting2 = {
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
    },
//    check: {
//    	chkStyle: "radio",
//    	enable:true,
//    	radioType:"all"
//    },
    view:{
    	showLine:false
    },  
    callback: {   
        onClick: function (e, treeId, treeNode, clickFlag) {   
//        	ztree2.checkNode(treeNode, !treeNode.checked, true);  
        	vmReload(e,treeId,treeNode);
        }   
    }, 
};

function vmReload(event, treeId, treeNode){
	vm.q.deptId = treeNode.deptId;
	vm.reload();
}

var ztree2;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            username: null,
            name: null,
            deptId: null,
            userType: null
        },
        showList: true,
        showListdept: true,
        title:null,
        roleList:{},
        dept:{
            parentName:null,
            parentId:0,
            orderNum:0,
            level:"-1"
        },
        user:{
            status:1,
            deptId:null,
            deptName:null,
            roleIdList:[]
        },
        levels:[]
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
//            vm.showList = false;
            
            vm.title = "新增";
            vm.addLayer();
            vm.roleList = {};
            vm.user = {deptName:null, deptId:null, status:1, roleIdList:[]};

            //获取角色信息
            this.getRoleList();

            vm.getDept();
        },
        downloadServiceExcel: function(){
        	var params = "1=" + "1";
        	
        	window.location.href = baseURL + "ins/sysuserdown/downuser?username="+vm.q.username+"&&name="+vm.q.name+"&&userType="+vm.q.userType+"";
        },
        //添加部门
        adddept: function(){
        	 vm.dept = {parentName:null,parentId:0,orderNum:0,level:"-1"};
             vm.getDept3();
             vm.getDeptLevel();
        	 layer.open({
                 type: 1,
                 offset: '50px',
                 skin: 'layui-layer-molv',
                 title: "选择部门",
                 area: ['600px', '450px'],
                 shade: 0,
                 shadeClose: false,
                 content: jQuery("#deptshow"),
                 btn: ['确定', '取消'],
                 btn1: function (index) {
                     var node = ztree3.getSelectedNodes();
                     //选择上级部门
                     if(node.length > 0){
                    	 vm.user.deptId = node[0].deptId;
                         vm.user.deptName = node[0].name;
                     }
                    
                     vm.saveOrUpdatedept();
                     layer.close(index);
                 }
             });
        	
        	
        },
        updatedept: function () {
        	//var deptId = getDeptId();
        	var deptId=vm.q.deptId;
        	//console.log("选中的部门id=================="+deptId);
        	console.log("选中的部门id=================="+vm.q.deptId);
            if(deptId == null){
                return ;
            }

            $.get(baseURL + "sys/dept/info/"+deptId, function(r){
                vm.title = "修改";
                console.log(r.dept);
                vm.dept = r.dept;
                
                vm.getDept3();
               
              //加载部门等级
                /*$.get(baseURL + "sys/dept/getDepartLevel", function(r){
                	vm.levels = r.list;
                })*/
            });
        	 layer.open({
                 type: 1,
                 offset: '50px',
                 skin: 'layui-layer-molv',
                 title: "选择部门",
                 area: ['600px', '450px'],
                 shade: 0,
                 shadeClose: false,
                 content: jQuery("#deptshow"),
                 btn: ['确定', '取消'],
                 btn1: function (index) {
                     var node = ztree3.getSelectedNodes();
                     //选择上级部门
                    /* vm.user.deptId = node[0].deptId;
                     vm.user.deptName = node[0].name;*/
                     
                     vm.saveOrUpdatedept();
                     
                     layer.close(index);
                 }
             });
        	
        	
        	
        	
        },
        getDeptLevel: function(){
            //加载部门等级
            $.get(baseURL + "sys/dept/getDepartLevel", function(r){
            	vm.levels = r.list;
            })
        },
        deldept: function () {
          /*  var deptId = getDeptId();*/
        	var deptId=vm.q.deptId;
            if(deptId == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/dept/delete",
                    data: "deptId=" + deptId,
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(){
                        
                            	 vm.getDept2();
                            //	ztree2.reAsyncChildNodes(null, "refresh"); //是推荐方式  
                            });

                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        
        getDept: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if(node != null){
                    ztree.selectNode(node);

                    vm.user.deptName = node.name;
                }
            })
        },
        getDept3: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree3 = $.fn.zTree.init($("#deptTree3"), setting, r);
                var node = ztree3.getNodeByParam("deptId", vm.dept.parentId);
                console.log(r);
                if(node != null){
                    ztree3.selectNode(node);
                    console.log("fuji--"+node.name);
                    vm.dept.parentName = node.name;
                }
            })
        },
        update: function () {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }

//            vm.showList = false;
            vm.title = "修改";
            vm.addLayer();
            vm.getUser(userId);
            //获取角色信息
            this.getRoleList();
        },
        del: function () {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                            layer.closeAll();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        
        saveOrUpdatedept: function (event) {
            var url = vm.dept.deptId == null ? "sys/dept/save" : "sys/dept/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.dept),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                            vm.getDept2();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        
        getUser: function(userId){
            $.get(baseURL + "sys/user/info/"+userId, function(r){
                vm.user = r.user;
                vm.user.password = null;

                vm.getDept();
            });
        },
        getRoleList: function(){
            $.get(baseURL + "sys/role/select", function(r){
                vm.roleList = r.list;
            });
        },
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        deptTree2: function(){
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

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'username': vm.q.username,'name': vm.q.name,'deptId':vm.q.deptId,'userType':vm.q.userType},
                page:page
            }).trigger("reloadGrid");
        },
        getDept2: function () {// 列表左侧查询指标树
			$.get(baseURL + "sys/user/getDeptlist",function(r){
				ztree2 = $.fn.zTree.init($("#deptTree2"), setting2, r);
				var nodes = ztree2.getNodes();
		        for (var i = 0; i < nodes.length; i++) { //设置节点展开
		        	ztree2.expandNode(nodes[i], true, false, true);
		        }
			});
		},
		addLayer: function(){
            layer.open({
                type: 1,
                offset: '20px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['650px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#addLayer"),	
                btn: ['确定', '取消'],
                btn1: function (index) {
                	vm.saveOrUpdate();
                },
                end: function (layero, index) {
                	vm.reload();
                }
            });
        }
    },created: function(){
    	this.getDept2();
    	 this.getDeptLevel();
    }
});



function getDeptId () {

	var nodes = ztree2.getCheckedNodes(true);
	console.log(nodes);
	if (nodes.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return nodes[0].deptId;
    }
   /* var selected = $('#deptTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }*/
}


/** 
 * 刷新当前选择节点的父节点 
 */  
function refreshParentNode() {  
    var zTree = $.fn.zTree.getZTreeObj("scriptTree"),  
    type = "refresh",  
    silent = false,  
    nodes = zTree.getSelectedNodes();  
    /*根据 zTree 的唯一标识 tId 快速获取节点 JSON 数据对象*/  
    var parentNode = zTree.getNodeByTId(nodes[0].parentTId);  
    /*选中指定节点*/  
    zTree.selectNode(parentNode);  
    zTree.reAsyncChildNodes(parentNode, type, silent);  
}  


//去除数组的重复项
function array_remove_repeat(a) { // 去重
    var r = [];
    for(var i = 0; i < a.length; i ++) {
        var flag = true;
        var temp = a[i];
        for(var j = 0; j < r.length; j ++) {
            if(temp === r[j]) {
                flag = false;
                break;
            }
        }
        if(flag) {
            r.push(temp);
        }
    }
    return r;
}
//两数组差集 a - b
function array_difference(a, b) { 
    //clone = a
    var clone = a.slice(0);
    for(var i = 0; i < b.length; i ++) {
        var temp = b[i];
        for(var j = 0; j < clone.length; j ++) {
            if(temp === clone[j]) {
                //remove clone[j]
                clone.splice(j,1);
            }
        }
    }
    return array_remove_repeat(clone);
}
//两数组并集
function array_union(a, b) { 
    return array_remove_repeat(a.concat(b));
}


	