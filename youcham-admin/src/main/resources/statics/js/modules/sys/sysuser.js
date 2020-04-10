$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [
            {label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true, hidden: true},
            {label: '用户账号', name: 'username', index: 'USERNAME', width: 80},
            {label: '邮箱', name: 'email', index: 'EMAIL', width: 80},
            {label: '姓名', name: 'name', index: 'NAME', width: 80},
            {label: '法人代表', name: 'legalPerson', index: 'LEGAL_PERSON', width: 50},
            {label: '电话', name: 'enterpriseTel', index: 'ENTERPRISE_TEL', width: 70},
            {
                label: '法人证件类型', name: 'mainType', index: 'MAIN_TYPE', width: 60,
                formatter: function (value, options, row) {
                    var result = row.mainType;
                    if (result === '01') {
                        return '身份证';
                    } else if (result === '02') {
                        return '护照';
                    } else if (result === '03') {
                        return '台湾同胞来往内地通行证';
                    } else if (result === '04') {
                        return '港澳居民来往内地通行证';
                    } else if (result === '05') {
                        return '外国人居留证';
                    } else {
                        return '';
                    }
                }
            },
            {label: '法人证件号', name: 'mainCard', index: 'MAIN_CARD', width: 80},
            {label: '注册时间', name: 'createTime', index: 'CREATE_TIME', width: 80},
            {
                label: '状态', name: 'status', width: 50,
                formatter: function (value, options, row) {
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                }
            },

        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
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
                    tips: [1, '#f3857c'],
                    time: 2000,
                    tipsMore: true
                });
            });
        },
        //失去焦点时验证 
        onfocusout: function (element) {
            $(element).valid();
        },
    });

});

function shipRecordInfor(xcCode) {
    layer.open({
        type: 2,
        offset: '20px',
        skin: 'layui-layer-molv',
        title: '船备案信息',
        area: ['1000px', '450px'],
        shade: 0.2,
        shadeClose: false,
        content: baseURL + "modules/st/stshiprecordinfo2.html?xcCode=" + xcCode
    });
}

function carRecordInfor(xcCode) {
    layer.open({
        type: 2,
        offset: '20px',
        skin: 'layui-layer-molv',
        title: '车备案信息',
        area: ['1000px', '450px'],
        shade: 0.2,
        shadeClose: false,
        content: baseURL + "modules/st/stcarrecordinfo2.html?xcCode=" + xcCode
    });
}


//加密的私钥
var key = '12345678';//自定义

// DES加密
function encryptByDES(message) {//传入加密的内容
    //把私钥转换成16进制的字符串
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    //模式为ECB padding为Pkcs7
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    //加密出来是一个16进制的字符串
    return encrypted.ciphertext.toString();
}

var vm = new Vue({
    /*components: {
        Multiselect: window.VueMultiselect.default
    },*/
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        roleList: {},
        sysUser: {
            status: 1,
            roleIdList: []
        },
        q: {username: null, creditCode: null},
        jiangxi: 0,
        cityList: [],
        countyList: [],
        userMenuTypes: [],
        userMenuType: [],
    },
    updated: function () {
        $(".selectpicker").selectpicker("refresh");
    },

    methods: {
        getUserMenuType: function () {
            $.get(baseURL + "sys/user/getUserMenuType", function (r) {
                vm.userMenuTypes = r.userMenuTypes;
                //console.log(vm.userMenuTypes);
            });
        },
        addUserMenuType: function () {
            var userIds = getSelectedRows();
            if (userIds == null) {
                return;
            }
            console.log(vm.userMenuType);
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "用户系统类型",
                area: ['600px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#userMenuTypes"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    if (vm.userMenuType.length < 1) {
                        alert("请至少勾选一项");
                        return;
                    }
                    layer.load();
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/user/addUserMenuType",
                        contentType: "application/json",
                        data: JSON.stringify({'ids': userIds, 'userMenuType': vm.userMenuType}),
                        success: function (r) {
                            layer.closeAll("loading");
                            if (r.code == 0) {
                                layer.closeAll();
                                alert('操作成功', function (index) {
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                }
            });
        },
        query: function () {
            vm.reload(true);
        },
        add: function () {
            vm.title = "新增";
            vm.sysUser = {
                status: 1,
                roleIdList: [],
                areaLevel: 0,
                province: vm.jiangxi,
                city: '',
                county: "",
            };
            vm.addLayer();
            vm.roleList = {};
            //获取角色信息
            this.getRoleList();
        },
        update: function (event) {
            var userId = getSelectedRow();
            if (userId == null) {
                return;
            }
            vm.title = "修改";

            vm.getInfo(userId);

            //获取角色信息
            this.getRoleList();

            vm.addLayer();
        },
        verifySubmit: function (event) {
            var userId = getSelectedRow();
            if (userId == null) {
                return;
            }
            // 获取用户信息
            vm.getInfo(userId);
            //获取角色信息
            this.getRoleList();

            this.setRoleLayer();
        },
        setRoleLayer: function () {
            layer.open({
                type: 1,
                offset: '20px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['430px', '220px'],
                shade: 0.2,
                shadeClose: false,
                content: jQuery("#setRole"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    layer.load();
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/user/verifyV",
                        contentType: "application/json",
                        data: JSON.stringify(vm.sysUser),
                        success: function (r) {
                            layer.closeAll("loading");
                            if (r.code == 0) {
                                layer.closeAll();
                                alert('操作成功', function (index) {
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                }
            });
        },
        getRoleList: function () {
            $.get(baseURL + "sys/role/select", function (r) {
                vm.roleList = r.list;
            });
        },
        saveOrUpdate: function (event) {
            if (vm.sysUser.password) {
                vm.sysUser.password = encryptByDES(vm.sysUser.password);
            }
            if (!vm.sysUser.city && (vm.sysUser.areaLevel == 1 || vm.sysUser.areaLevel == 2)) {
                alert("请选择市");
                layer.closeAll("loading");
                return;
            }
            if (!vm.sysUser.county && vm.sysUser.areaLevel == 2) {
                alert("请选择县");
                layer.closeAll("loading");
                return;
            }
            // 进行校验
            if (!$("#ajaxForm").valid()) {
                layer.closeAll("loading");
                return;
            }
            var url = vm.sysUser.userId == null ? "sys/user/save" : "sys/user/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.sysUser),
                success: function (r) {
                    layer.closeAll("loading");
                    if (r.code === 0) {
                        layer.closeAll();
                        alert('操作成功', function (index) {
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var userIds = getSelectedRows();
            if (userIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (userId) {
            $.ajaxSettings.async = false;
            $.get(baseURL + "sys/user/info/" + userId, function (r) {
                vm.sysUser = r.user;
                vm.getCountyList();
            });
            $.ajaxSettings.async = true;
        },
        reload: function (event) {
            //vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            if (event) {
                page = 1;
            }

            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: {
                    'username': vm.q.username,
                    'name': vm.q.name,
                    'areaLevel': vm.q.areaLevel
                }
            }).trigger("reloadGrid");
        },
        addLayer: function () {
            layer.open({
                type: 1,
                offset: '20px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['700px', '450px'],
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
        /* 获取江西省 */
        getJiangXi: function () {
            $.getJSON(baseURL + "swzj/swzjcounty/select", {"code": "360000"}, function (data) {
                if (data.list.length > 0) {
                    vm.jiangxi = data.list[0].id;
                } else {
                    vm.jiangxi = 0;
                }
            })
        },
        /* 查询本省市 */
        selectIndustryType: function () {
            /* 江西地市+省厅 */
            $.getJSON(baseURL + "swzj/swzjcounty/selectCityByJiangXi", function (data) {
                vm.cityList = data.list;
            })
        },
        /* 查询县 */
        getCountyList: function () {
            vm.countyList = [];
            // vm.sysUser.county = null;
            var id = vm.sysUser.city;
            if (id) {
                $.ajaxSettings.async = false;
                $.getJSON(baseURL + "swzj/swzjcounty/select", {"twoLevelCityId": id}, function (data) {
                    vm.countyList = data.list;
                });
                $.ajaxSettings.async = true;
            }
        },
        /* 切换区域等级清空 */
        getAreaLevel: function (e) {
            var value = e.target.value;
            vm.sysUser.province = vm.jiangxi;
            vm.sysUser.city = null;
            vm.sysUser.county = null;
        },
    },
    created: function () {
        this.getJiangXi();
        this.selectIndustryType();
        this.getUserMenuType();
    }
});
