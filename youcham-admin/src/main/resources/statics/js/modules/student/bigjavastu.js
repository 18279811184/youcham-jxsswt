$(function () {

	console.log(T.p('allids')+"获得有ids");
	//var gn = $('#getallfileid', window.parent.document).html();
	var gn = T.p('allids');
	if(gn == ""|| gn == null){
		gn = null;
	}
    $("#jqGrid").jqGrid({
        url: baseURL + 'student/bigjavastu/list',
        datatype: "json",
		postData:{"allids":gn},
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '名字', name: 'stuName', index: 'stu_name', width: 80 }, 			
			{ label: '性别', name: 'stuSex', index: 'stu_sex', width: 80 }, 			
			{ label: '邮箱', name: 'stuEmali', index: 'stu_emali', width: 80 }, 			
			{ label: '手机号', name: 'stuPhone', index: 'stu_phone', width: 80 }, 			
			{ label: '学号', name: 'stuNumber', index: 'stu_number', width: 80 }, 			
			{ label: '所选课程', name: 'stuCourse', index: 'stu_course', width: 80 ,
				formatter: function (value, options, row) {
					return"<a href='javaScript:void(0)' onclick='vm.showCourse(\""+row.stuCourse+"\")'>查看详情</a>";
				}},
			{ label: '成绩', name: 'stuCredit', index: 'stu_credit', width: 80 },
			{ label:'老师名称',name:'stuTeaid', index:'stu_teaId'}
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
		teacherList:[],
		bigjavaStu: {},
		q:{}
	},
	methods: {
		query: function () {
			vm.reload(true);
		},
		add: function(){
			vm.title = "新增";
			vm.bigjavaStu = {};
			vm.addLayer();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
            vm.title = "修改";
            
            vm.getInfo(id);
            
            vm.addLayer();
		},
		saveOrUpdate: function (event) {
			// 进行校验
			if(!$("#ajaxForm").valid()){
				layer.closeAll("loading");
				return;
			}
		
			var url = vm.bigjavaStu.id == null ? "student/bigjavastu/save" : "student/bigjavastu/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.bigjavaStu),
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
				    url: baseURL + "student/bigjavastu/delete",
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
			$.get(baseURL + "student/bigjavastu/info/"+id, function(r){
                vm.bigjavaStu = r.bigjavaStu;
            });
		},
		getTeacher: function(){
			$.getJSON(baseURL+"student/bigjavatea/select",function(r){
				vm.teacherList = r;
			});
		},

		showCourse:function(ids){
			layer.open({
				type : 2,
				offset : '20px',
				skin : 'layui-layer-molv',
				title : "所选课程",
				area : [ '750px', '460px' ],//590px
				shade :  [0.1, '#fff'],
				shadeClose : false,
				content : baseURL+"modules/sys/sysexporttemplate.html?allids="+ids,
				btn : [ '关闭' ]
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
           		postData:{'stuName': vm.q.stuName,'stuCredit':vm.q.stuCredit}
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
        }
	},
	created:function(){
		this.getTeacher();
	}
});