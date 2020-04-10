
$(function() {
    $(window).on('resize', function () {
        var $content = $('#larry-tab .layui-tab-content');
        $content.height($(this).height() - 130);
        $content.find('iframe').each(function () {
            $(this).height($content.height());
        });
    }).resize();
})

//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props: {item: {}},
    template: [
        '<li class="layui-nav-item" >',
        '<a v-if="item.type === 0" href="javascript:;">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}</span>',
        '<em class="layui-nav-more"></em>',
        '</a>',
        '<dl v-if="item.type === 0" class="layui-nav-child">',
        '<dd v-for="item in item.list" >',
        '<a v-if="item.type === 1" href="javascript:;" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i><span>{{item.name}}</span></a>',
        '</dd>',
        '</dl>',
        '<a v-if="item.type === 1" href="javascript:;" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i><span>{{item.name}}</span></a>',
        '</li>'
    ].join('')
});

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

//注册菜单组件
Vue.component('menuItem', menuItem);
isquery=true;
var vm = new Vue({
    el: '#layui_layout',
    data: {
    	user:{},
		dept:{},
        menuList: {},
        password: '',
        newPassword: '',
        navTitle: "首页"
    },
    methods: {
        getMenuList: function () {
            $.getJSON("sys/menu/nav", function (r) {
                vm.menuList = r.menuList;
            });
        },
        getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.token = r.token;
				vm.user = r.user;
				vm.dept = r.dept;
				
//				vm.getMenuList();
			});
		},
        updatePassword: function () {
            layer.open({
                type: 1,
//                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改', '取消'],
                btn1: function (index) {
                    var password = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[1-9])(?=.*[\W]).{6,}$/;
                    if(!password.test(vm.newPassword)){
                        layer.tips("密码须包含大小写字母、数字、符号", "#newPassword", {
                            tips: [1, '#f34239'],
                            time: 1500,
                            tipsMore: true
                        });

                        return;
                    }


                    var data = "password=" + encryptByDES(vm.password) + "&newPassword=" + encryptByDES(vm.newPassword);
                    $.ajax({
                        type: "POST",
                        url: "sys/user/password",
                        data: data,
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 0) {
                                layer.close(index);
                                layer.alert('修改成功', function (index) {
                                    location.reload();
                                });
                            } else {
                                layer.alert(result.msg);
                            }
                        }
                    });
                }
            });
        },
        reflashIfr: function(){
        	console.log($(".layui-tab-content .layui-show iframe").attr("src"));
        	$(".layui-tab-content .layui-show iframe")[0].contentWindow.location.reload();
        }
        
    },
    created: function () {
        this.getMenuList();
        this.getUser();
    },updated:function(){

        if($("#larry-side .layui-nav-item>a").length==0 || !isquery){
            return;
        }
        console.log("执行")
        isquery=false;
        layui.config({
            base: 'statics/js/',
        }).use(['navtab','layer'], function(){
            window.jQuery = window.$ = layui.jquery;
            window.layer = layui.layer;
            var element = layui.element();
            var  navtab = layui.navtab({
                elem: '.larry-tab-box',
                closed:false
            });
            $('#larry-nav-side').children('ul').find('li').each(function () {
                var $this = $(this);
                if ($this.find('dl').length > 0) {

                    var $dd = $this.find('dd').each(function () {
                        $(this).on('click', function () {
                            var $a = $(this).children('a');
                            var href = $a.data('url');
                            var icon = $a.children('i:first').data('icon');
                            var title = $a.children('span').text();
                            var data = {
                                href: href,
                                icon: icon,
                                title: title
                            }
                            navtab.tabAdd(data);
                        });
                    });
                } else {

                    $this.on('click', function () {
                        var $a = $(this).children('a');
                        var href = $a.data('url');
                        var icon = $a.children('i:first').data('icon');
                        var title = $a.children('span').text();
                        var data = {
                            href: href,
                            icon: icon,
                            title: title
                        }
                        navtab.tabAdd(data);
                    });
                }
            });
            $('#larry-side-menu').click(function () {
                var sideWidth = $('#larry-side').width();
                if (sideWidth === 230) {
                    $('#larry-body').animate({
                        left: '0'
                    });
                    $('#larry-footer').animate({
                        left: '0'
                    });
                    $('#larry-side').animate({
                        width: '0'
                    });
                } else {
                    $('#larry-body').animate({
                        left: '230px'
                    });
                    $('#larry-footer').animate({
                        left: '230px'
                    });
                    $('#larry-side').animate({
                        width: '230px'
                    });
                }
            });
        });
    }
});