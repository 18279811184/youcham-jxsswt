//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li class="nav-item has-treeview">',
        '	<a v-if="item.type === 0" href="javascript:;" class="nav-link">',
        '		<i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i>',
        '		<p>' ,
		'			{{item.name}}<i class="right fas fa-angle-left"></i>',
		'		</p>',
        '	</a>',
        '	<ul v-if="item.type === 0"  class="nav nav-treeview">',
        '		<menu-item :item="item" v-for="item in item.list"></menu-item>',
        '	</ul>',

        '	<a v-if="item.type === 1 && item.parentId == 0" :href="\'#\'+item.url" class="nav-link">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
		'		<i v-else class="fa fa-circle-o"></i>',
        '		<p>{{item.name}}</p>',
        '	</a>',

        '	<a v-if="item.type === 1 && item.parentId != 0" :href="\'#\'+item.url" class="nav-link"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
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

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 145);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
	el:'#rrapp',
	data:{
		token:"",
		user:{},
		dept:{},
		menuList:{},
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:"控制台"
	},
	methods: {
		getMenuList: function (event) {
//			$.getJSON(userCenterApi+"api/menu/nav/",{"token":vm.token,"systemId":systemId,"userId":vm.user.userId}, function(r){
		/*	$.getJSON("sys/menu/nav?_"+$.now(),function(r){
				vm.menuList = r.menuList;
			});*/
			var defer = $.Deferred();
			$.getJSON("sys/menu/nav?_"+$.now(),function(r){
				vm.menuList = r.menuList;
				defer.resolve(r);
			});
			return defer;
			/* return  new Promise(function (resolve, reject)  {
				 $.getJSON("sys/menu/nav?_"+$.now(),function(r){
					 vm.menuList = r.menuList;
					 resolve(r);
				 });
			})*/
		},
		getUser: function(){
		/*	return  new Promise(function (resolve, reject) {
				$.getJSON("sys/user/info?_"+$.now(), function(r){
					vm.token = r.token;
					vm.user = r.user;
					vm.dept = r.dept;
					resolve(r);
				});
			})*/
			var defer = $.Deferred();
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.token = r.token;
				vm.user = r.user;
				vm.dept = r.dept;
				defer.resolve(r);
			});
			return defer;
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '330px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					if(!vm.password){
						layer.tips("原密码不能为空", "#password", {
							tips: [1, '#f34239'],
							time: 1500,
							tipsMore: true
						});
						return;
					}
					if(!vm.newPassword){
						layer.tips("新密码不能为空", "#newPassword", {
							tips: [1, '#f34239'],
							time: 1500,
							tipsMore: true
						});
						return;
					}

                    /*var password = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[1-9])(?=.*[\W]).{6,}$/;
                    if(!password.test(vm.newPassword)){
                        layer.tips("密码须包含大小写字母、数字、符号", "#newPassword", {
                            tips: [1, '#f34239'],
                            time: 1500,
                            tipsMore: true
                        });

                        return;
                    }*/


                    var data = "password=" + encryptByDES(vm.password) + "&newPassword=" + encryptByDES(vm.newPassword);
					$.ajax({
						type: "POST",
					    url: "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		},
		logout: function(){
			location.href = 'logout';
		}
	},
	created: function(){
		var p = this.getUser();
		var p1 = this.getMenuList();
		/* 请求全部完成则删除动画 */
	/*	Promise.all([p, p1]).then(function () {
			$('#loader-wrapper .load_title').remove();
			$('body').addClass('loaded');
		});*/
		$.when(p, p1).then(function () {
			$('#loader-wrapper .load_title').remove();
			$('body').addClass('loaded');
		})
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	}
});



function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			routerList(router, menu.list);
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				
				//替换iframe的url
			    vm.main = url.replace('#', '');
			    
			    //导航菜单展开
			    $("#menuTree li").removeClass("active");
			    $("#menuTree li a").removeClass("active");
			    $("a[href='"+url+"']").parents("li").parents("ul").prev("a").addClass("active");
			    $("a[href='"+url+"']").addClass("active");

			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}
