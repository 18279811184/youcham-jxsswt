$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'swzj/swzjobjectiveadd/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'ID', width: 50, key: true, hidden:true},
			{ label: '年份', name: 'year', index: 'YEAR', width: 80,
				formatter:function(value, options, row){
					return value+'年';
				} },
			{ label: '目标(亿元)', name: 'targetMillion', index: 'TARGET_MILLION', width: 80,
				formatter:function(value, options, row){
					return value+'亿元';
				} },
			{ label: '地市', name: 'map.NAME', index: 'REGION', width: 80 }
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
		sysObjectiveAdd: {},
		sysDictType:null,
		q:{},
		sysDictType : [],
        region:0,

	},
	methods: {
		query: function () {
			vm.reload(true);
		},
		add: function(){
			vm.title = "新增本年目标";
			vm.sysObjectiveAdd = {};
			vm.addLayer();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
            vm.title = "修改本年目标";
            
            vm.getInfo(id);
            
            vm.addLayer();
		},
		saveOrUpdate: function (event) {
			// 进行校验
			if(!$("#ajaxForm").valid()){
                layer.closeAll("loading");
				return false;
			}
            if (vm.sysObjectiveAdd.region==null||vm.sysObjectiveAdd.region==''){
                alert("请选择地市！");
                layer.closeAll("loading");
                return false;
            }
			if (vm.sysObjectiveAdd.targetMillion==null||vm.sysObjectiveAdd.targetMillion==""){
				alert("目标不能为空！");
                layer.closeAll("loading");
				return false;
			}

			$.get(baseURL+"swzj/swzjobjectiveadd/checkRegions", {idc: vm.sysObjectiveAdd.id, region: vm.sysObjectiveAdd.region},function (r) {
				console.log(r);
				if (r=="yes"){
					alert("相同年份的地市不能重复！");
                    layer.closeAll("loading");
					return;
				}else{
                    var url = vm.sysObjectiveAdd.id == null ? "swzj/swzjobjectiveadd/save" : "swzj/swzjobjectiveadd/update";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.sysObjectiveAdd),
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
				    url: baseURL + "swzj/swzjobjectiveadd/delete",
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
			$.get(baseURL + "swzj/swzjobjectiveadd/info/"+id, function(r){
                vm.sysObjectiveAdd = r.sysObjectiveAdd;
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
        },
		// getDictType:function () {
		// 	$.get(baseURL + "sys/dict/list", {"limit" : 9999999,"dictType":"city"},function(r){
		// 		vm.sysDictType = r.page.list;
		// 		console.log(vm.sysDictType)
		// 	});
		// },
		/* 查询本省市 */
		selectIndustryType: function () {
			/* 江西地市+省厅 */
			$.getJSON(baseURL + "swzj/swzjcounty/selectCityByJiangXi", function (data) {
				vm.sysDictType = data.list;
				console.log(vm.sysDictType)
			})
		},
	},
	mounted:function () {
		/*laydate.render({
			elem : '#years',
			theme : '#3C8DBC',
			type: 'year',
			//value: this.now,
			done : function(value) {
				vm.sysObjectiveAdd.year = value
			}
		});*/
		this.selectIndustryType();
	},
	beforeCreate: function() {
		$("body").css("margin-left", "100%");
		$("body").animate({marginLeft: "0px"}, 600);
	}
});