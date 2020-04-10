$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysdeclarationdate/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'id', width: 50, key: true ,hidden:true},
            { label: '申报月份', name: 'month', index: 'month', width: 80,
                formatter: function(value, options, row){
                    for (var i = 1; i <= 12 ; i++) {
                        if (value === i)
                        {
                            return i + "月"
                        }
                    }
                }
            },
            { label: '截止时间', name: 'theDate', index: 'theDate', width: 80 },
            { label: '操作', name: 'caoZuo', width: 80,frozen: true ,
                formatter: function(value, options, row){
                    return "<a href='javaScript:void(0)' onclick='vm.update(\""+row.id+"\")'>修改</a>&nbsp;"
                }}
        ],
        //viewrecords: true,
        height: 462,
        //rowNum: 12,
        //rowList : [12,30,50],
        //rownumbers: true,
        rownumWidth: 25,
        //autowidth:true,
        //multiselect: true,
        //pager: "#jqGridPager",
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
    el:"#rrapp",
    data:{
        showList: true,
        title: null,
        sysDeclarationDate:{},
        months:{},
        q:{}
    },
    methods:{
        query: function () {
            vm.reload();
        },
        /*add:function(){
            vm.title = "";
            vm.sysDeclarationDate={};
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['550px', '420px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#addLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    layer.load();
                    var url = "sys/sysdeclarationdate/save";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.sysDeclarationDate),
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
                end: function (layero, index) {
                    vm.reload();
                }
            });
        },*/
        update: function (id) {
            //var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.title = "修改";

            vm.getInfo(id);

            vm.addLayer();
        },
        addLayer:function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['550px', '420px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#addLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    layer.load();
                    vm.saveOrUpdate();
                },
                end: function (layero, index) {
                    vm.reload();
                }
            });
        },

        saveOrUpdate:function(id){
            //vm.sysDeclarationDate.id = id;
            var url = "sys/sysdeclarationdate/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.sysDeclarationDate),
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
       /* del: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/sysdeclarationdate/delete",
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
        },*/
        getInfo: function(id){
            $.get(baseURL + "sys/sysdeclarationdate/info/"+id, function(r){
                vm.sysDeclarationDate = r.sysDeclarationDate;
            });
        },
        reload:function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:page,
                postData:{"month":vm.q.month,"theDate":vm.q.theDate}
            }).trigger("reloadGrid");
        },


        /*getlist:function() {
            $.get(baseURL + "sys/sysdeclarationdate/list", function (r) {
                //vm.months = r.list;
                //console.log(vm.months);
                for(i = 0;i < r.page.list.length;i++)
                {
                    //console.log(r.list[i].month);
                    r.page.list[i].month = r.page.list[i].month + "月份";
                    console.log(r.page.list[i].month);
                    console.log(r);
                }
            });
        }*/
    },

    mounted:function () {
        laydate.render({
            elem : '#theDate',
            theme : '#3C8DBC',
            type: 'date',
            value: this.now,
            done : function(value) {
                vm.sysDeclarationDate.theDate = value
            }
        });
        //this.getlist();
    }
});