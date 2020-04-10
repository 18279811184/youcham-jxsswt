var arrayNewList = new Array();
var topic = T.p('topic');
if (topic != "" && topic != null && topic != "null") {
	console.log("topic不为空");
	console.log(topic);
	var s = topic;
	arrayNewList = s.split(",");
	$("#getallid").html(arrayNewList.toString());
}
console.log(arrayNewList);

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/userlist',
        datatype: "json",
        colModel: [
            { label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true ,hidden:true},
            { label: '用户账号', name: 'username', index: 'USERNAME', width: 80 },
            // { label: '邮箱', name: 'email', index: 'EMAIL', width: 80 },
            { label: '统一社会信用代码', name: 'creditCode', index: 'XY_CODE', width: 80 },
            { label: '企业名称', name: 'enterpriseName', index: 'ENTERPRISE_NAME', width: 80 },
            { label: '法人代表', name: 'legalPerson', index: 'LEGAL_PERSON', width: 80 },
            { label: '企业电话', name: 'enterpriseTel', index: 'ENTERPRISE_TEL', width: 80 },
            // { label: '法人证件类型', name: 'mainType', index: 'MAIN_TYPE', width: 80,
            //     formatter: function(value, options, row){
            //         var result=row.mainType;
            //         if(result==='01'){
            //             return '身份证';
            //         }else if(result==='02'){
            //             return '护照';
            //         }else if(result==='03'){
            //             return '台湾同胞来往内地通行证';
            //         }else if(result==='04'){
            //             return '港澳居民来往内地通行证';
            //         }else if(result==='05'){
            //             return '外国人居留证';
            //         } else {
            //             return '';
            //         }
            //     }
            // },
            // { label: '法人证件号', name: 'mainCard', index: 'MAIN_CARD', width: 80 },
            { label: '状态', name: 'status', width: 60,
                formatter: function(value, options, row){
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                }
            },
            { label: '审核状态', name: 'auditStatus', index: 'AUDIT_STATUS', width: 60,
                formatter: function(value, options, row){
                    if(value===0){
                        return '<font color="red">未备案</font>';
                    } else if(value===1){
                        return '<font color="blue">已备案</font>';
                    } else if(value===2){
                        return '<font color="orange">备案未完全</font>';
                    } else if(value===3){
                        return '<font color="green">已审核</font>';
                    } else {
                        return '无效';
                    }
                }
            }
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
						$("#jqg_jqGrid_" + item.userId).attr(
								"checked", true);
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
			if (topic != "" && topic != null && topic != "null") {
				var str = topic.split(",");		
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
			$.get(baseURL + "sys/dept/list",function(r){
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

	