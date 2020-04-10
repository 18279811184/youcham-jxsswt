$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'st/stjjtossshipline/list2',
        datatype: "json",
        colModel: [
            { label: '类型', name: 'TYPE', index: "TYPE",
            	formatter: function(value, options, row){
     				if(value==1){
     					return '九江上海班轮航线';
     				}else if(value==2){
     					return '小支线班轮航线';
     				}else if(value==3){
     					return '长江内河港口';
     				}else if(value==4){
     					return '九江港卡车高速公路通行费';
     				}else if(value==5){
     					return '各铁路站点到发集装箱重箱扶持';
     				}else if(value==6){
     					return '外贸直航业务扶持';
     				}else if(value==7){
     					return '检验检疫';
     				}else{
     					return '总和';
     				}
     			}
            },
            { label: '业务量', name: 'ZONGSHU', index: "ZHONGSHU", 
            	formatter: function(value, options, row){
     				if(isBlank(value)){
     					return '0';
     				}else{
     					return value;
     				}
     			}
            },
            { label: '总金额', name: 'JINE', index: "JINE", 
            	formatter: function(value, options, row){
     				if(isBlank(value)){
     					return '0';
     				}else{
     					return value;
     				}
     			}
            },
            { label: '平均金额', name: 'PINGJUN', index: "PINGJUN", 
            	formatter: function(value, options, row){
     				if(isBlank(value)){
     					return '0';
     				}else{
     					return value;
     				}
     			}
            },
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: false,
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

//菜单树
var menu_ztree;
var menu_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "menuId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    },
    check:{
        enable:true,
        nocheckInherit:true,
        chkboxType: { "Y": "s", "N": "ps" }
    }
};

//部门结构树
var dept_ztree;
var dept_setting = {
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

//数据树
var data_ztree;
var data_setting = {
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
    check:{
        enable:true,
        nocheckInherit:true,
        chkboxType:{ "Y" : "", "N" : "" }
    }
};

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            roleName: null
        },
        showList: true,
        title:null,
        role:{
            deptId:null,
            deptName:null
        },
    },
    methods: {
        query: function () {
            vm.reload();
        },                                 
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'status': vm.q.status,'declareTime':vm.q.declareTime},
                page:page
            }).trigger("reloadGrid");
        },             
    },
    mounted:function(){
    	laydate.render({
			elem : '#declareTimeSearch',
			theme : '#3C8DBC',
			type: 'month',
			value: this.now,
			done : function(value) {
				 vm.q.declareTime = value
			}
		});
    }
});