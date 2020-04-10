$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'wmyj/wmyjinfo/countData',
        datatype: "json",
		/*传递参数到后来*/
		postData: {
        	"queryDate":dateFmt("yyyy-MM",new Date())
		},
        colModel: [			
			{ label: '省市区', name: 'CITY_AREA', index: 'CITY_AREA', width: 80 },
			{ label: '本月预计出口额', name: 'MONTH_AMOUNT', index: 'MONTH_AMOUNT', width: 80 },

			{ label: '上年本月出口额', name: 'YEAR_AMOUNT', index: 'YEAR_AMOUNT', width: 80 },
			{ label: '本月同比%', name: 'MONTH_PRECENT', index: 'MONTH_PRECENT', width: 80 },
			{ label: '高新技术产品', name: 'NEWTEL_AMOUNT', index: 'NEWTEL_AMOUNT', width: 80 },
			{ label: '机电产品', name: 'ELEC_AMOUNT', index: 'ELEC_AMOUNT', width: 80 },
			{ label: '农产品及其深加工产品', name: 'FARM_AMOUNT', index: 'FARM_AMOUNT', width: 80 },
			{ label: '有色金属', name: 'METAL_AMOUNT', index: 'METAL_AMOUNT', width: 80 },
			{ label: '纺织服装', name: 'WEAVE_AMOUNT', index: 'WEAVE_AMOUNT', width: 80 },
			{ label: '轻工产品', name: 'LIGHTINDU_AMOUNT', index: 'LIGHTINDU_AMOUNT', width: 80 },
			{ label: '钢材和铁合金', name: 'STEEL_AMOUNT', index: 'STEEL_AMOUNT', width: 80 },
			{ label: '医药化工', name: 'MEDICINE_AMOUNT', index: 'MEDICINE_AMOUNT', width: 80 },
			{ label: '建材', name: 'MATERIALS_AMOUNT', index: 'MATERIALS_AMOUNT', width: 80 },
			{ label: '其他产品', name: 'OTHER_AMOUNT', index: 'OTHER_AMOUNT', width: 80 },
			{ label: '订单(增加)', name: 'ORDER_HIGH', index: 'ORDER_HIGH', width: 80 },
			{ label: '订单(持平)', name: 'ORDER_MID', index: 'ORDER_MID', width: 80 },
			{ label: '订单(减少)', name: 'ORDER_LOW', index: 'ORDER_LOW', width: 80 },
			{ label: '价格(上涨)', name: 'PRICE_HIGH', index: 'PRICE_HIGH', width: 80 },
			{ label: '价格(持平)', name: 'PRICE_MID', index: 'PRICE_MID', width: 80 },
			{ label: '价格(减少)', name: 'PRICE_LOW', index: 'PRICE_LOW', width: 80 }

        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
		shrinkToFit:false,   //填加底部滚动条
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
        	//$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
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
		wmyjTblInfoamountR: {},
		q:{
			queryDate:dateFmt("yyyy-MM",new Date())
		}
	},
	methids:{},
	mounted:function(){
		laydate.render({
			elem: '#queryDate',
			type: 'month',
			done: function(value, date, endDate){
				console.log(value); //得到日期生成的值，如：2017-08-18
				console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
				console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
				vm.q.queryDate = value;
			}
		});

	},
	methods: {
		//市级导出
		cityExcept:function(){
			// window.location.href = baseURL + "swzj/swzjmajorproject/exportExcel";



			layer.msg('导出中...', {
				icon: 16,
				shade: 0.01
			});
			progressDownLoad(baseURL + "wmyj/wmyjinfo/cityExcept","市级数据汇总.xls",
				JSON.stringify({
					'queryDate':vm.q.queryDate,
				}),
				function(xx){},
				function(){
					layer.closeAll('loading');
				}
			);
		},

		query: function () {
			vm.reload(true);
		},
		add: function(){
			vm.title = "新增";
			vm.wmyjTblInfoamountR = {};
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
		
			var url = vm.wmyjTblInfoamountR.id == null ? "wmyj/wmyjtblinfoamountr/save" : "wmyj/wmyjtblinfoamountr/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.wmyjTblInfoamountR),
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
				    url: baseURL + "wmyj/wmyjtblinfoamountr/delete",
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
			$.get(baseURL + "wmyj/wmyjtblinfoamountr/info/"+id, function(r){
                vm.wmyjTblInfoamountR = r.wmyjTblInfoamountR;
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
           		postData:{'queryDate': vm.q.queryDate}
            }).trigger("reloadGrid");
		},
		exportExcel:function(){
			layer.msg('导出中...', {
				icon: 16,
				shade: 0.01
			});
			var params = {
				'queryDate': vm.q.queryDate
			};
			progressDownLoad(baseURL + "wmyj/wmyjinfo/exportMoneyTotalExcel",vm.q.queryDate+"金额数据汇总.xls",params,
				null,
				function(){
					layer.closeAll('loading');
				}
			);
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
	}
});