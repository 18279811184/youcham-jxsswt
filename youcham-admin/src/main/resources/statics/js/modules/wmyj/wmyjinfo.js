$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'wmyj/wmyjinfo/list',
        datatype: "json",
        colModel: [
			{ label: '审核状态', name: 'showFlag', index: 'SHOW_FLAG', width: 80,
				formatter: function (value, option, row) {
					if (value == 0) {
						return "未审核";
					} else if(value == 1){
						return "市级已通过";
					}else if(value == 2){
						return "省级已通过";
					}else if(value == 3){
						return "市级未通过";
					}else if(value == 4){
						return "省级未通过";
					}
				}
			},
			{ label: '所属市', name: 'cityCode', index: 'CITY_CODE', width: 80 },
			{ label: '所属区', name: 'areaCode', index: 'AREA_CODE', width: 80 },
			/*{ label: '用户名', name: 'userid', index: 'USERID', width: 80 },*/
			{ label: '企业代码', name: 'enterpriseCode', index: 'ENTERPRISE_CODE', width: 80 }, 			
			{ label: '企业名称', name: 'enterpriseName', index: 'ENTERPRISE_NAME', width: 80 }, 			
			{ label: '所属单位代码', name: 'unitCode', index: 'UNIT_CODE', width: 100 },
			{ label: '报出日期', name: 'dateIn', index: 'DATE_IN', width: 80 }, 			
			{ label: '本月预计出口额', name: 'monthAmount', index: 'MONTH_AMOUNT', width: 100 },
			{ label: '上年本月出口额', name: 'yearAmount', index: 'YEAR_AMOUNT', width: 100 },
			{ label: '高新技术产品', name: 'newtelAmount', index: 'NEWTEL_AMOUNT', width: 80 }, 			
			{ label: '机电产品', name: 'elecAmount', index: 'ELEC_AMOUNT', width: 80 }, 			
			{ label: '农产品及其深加工产品', name: 'farmAmount', index: 'FARM_AMOUNT', width: 100 },
			{ label: '有色金属', name: 'metalAmount', index: 'METAL_AMOUNT', width: 80 }, 			
			{ label: '纺织服装', name: 'weaveAmount', index: 'WEAVE_AMOUNT', width: 80 }, 			
			{ label: '轻工产品', name: 'lightinduAmount', index: 'LIGHTINDU_AMOUNT', width: 80 }, 			
			{ label: '钢材和铁合金', name: 'steelAmount', index: 'STEEL_AMOUNT', width: 80 }, 			
			{ label: '医药化工', name: 'medicineAmount', index: 'MEDICINE_AMOUNT', width: 80 }, 			
			{ label: '建材', name: 'materialsAmount', index: 'MATERIALS_AMOUNT', width: 80 }, 			
			{ label: '其他产品', name: 'otherAmount', index: 'OTHER_AMOUNT', width: 80 },
			{ label: '订单情况', name: 'orderFlag', index: 'ORDER_FLAG', width: 80,
				formatter: function (value, option, row) {
					if (value == 0) {
						return "订单持平";
					} else if(value == 1){
						return "订单增加";
					}else if(value == -1){
						return "订单减少";
					}
				}},
			{ label: '价格走势', name: 'priceFlag', index: 'PRICE_FLAG', width: 80,
				formatter: function (value, option, row) {
					if (value == 0) {
						return "价格持平";
					} else if(value == 1){
						return "价格上涨";
					}else if(value == -1){
						return "价格减少";
					}
				}},
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25,
		shrinkToFit:false,   //填加底部滚动条
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
		showImport:false,
		title: null,
		wmyjInfo: {

		},

		citys: [],
		areas: {
			"南昌市":["东湖区","西湖区","青云谱区","湾里区","青山湖区","南昌县","新建县","安义县","进贤县","高新技术开发区","南昌经济开发区","桑海经济技术开发区","英雄开发区","南昌市属企业"],
			"景德镇市":["昌江区","珠山区","浮梁县","乐平市","景德镇市高新区","陶瓷工业园","景德镇市属企业"],
			"萍乡市":["安源区","湘东区","莲花县","上栗县","芦溪县","萍乡市开发区","萍乡市属企业"],
			"九江市":["庐山区","浔阳区","九江县","武宁县","修水县","永修县","德安县","星子县","都昌县","湖口县","彭泽县","瑞昌市","九江市开发区","共青开发区","九江市属企业"],
			"新余市":["渝水区","分宜县","新余市开发区","新余市属企业"],
			"鹰潭市":["月湖区","余江县","贵溪市","鹰潭市工业园","鹰潭市属企业"],
			"赣州市":["章贡区","赣县区","信丰县","大余县","上犹县","崇义县","安远县","龙南县","定南县","全南县","宁都县","于都县","兴国县","会昌县","寻乌县","石城县","瑞金市","南康市","赣州市开发区","赣州市属企业"],
			"宜春市":["袁州区","奉新县","万载县","上高县","宜丰县","靖安县","铜鼓县","丰城市","樟树市","高安市","宜春市开发区","宜春市属企业"],
			"上饶市":["信州区","上饶县","广丰县","玉山县","铅山县","横峰县","戈阳县","余干县","鄱阳县","万年县","婺源县","德兴市","上饶市经济开发区","上饶市属企业"],
			"吉安市":["吉州区","青原区","吉安县","吉水县","峡江县","新干县","永丰县","泰和县","遂川县","万安县","安福县","永新县","井冈山市","吉安市高新区","吉安市属企业"],
			"抚州市":["临川区","南城县","黎川县","南丰县","崇仁县","乐安县","宜黄县","金溪县","资溪县","东乡县","广昌县","金巢开发区","抚州市属企业"]

		},
		firstLevelCitys: ["南昌市","景德镇市","萍乡市","九江市","新余市","鹰潭市","赣州市","宜春市","上饶市","吉安市","抚州市"],
		twoLevelCitys: [],
		q:{
			queryDate:dateFmt("yyyy-MM",new Date()),
			auditFlag:"",
			enterpriseName:"",
			city:"",
			area:""
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
		//导入确认
		saveOrUpdate2: function (event) {
			var url = "wmyj/wmyjinfo/importExcel";
			var formData = new FormData();
			formData.append('file', $('#importExcelfile')[0].files[0]);
			/*formData.append('dtDate', vm.stjjtossshipline.declareTime);*/
			/*console.log("vm.stjjtossshipline.declareTime="+vm.stjjtossshipline.declareTime);*/

			// 进行校验
			if(!$("#ajaxFormimportLayer").valid()){
				layer.closeAll("loading");
				return;
			}

			$.ajax({
				type: "POST",
				url: baseURL + url,
				data: formData,
				processData:false,
				contentType : false,
				success: function(r){
					layer.closeAll("loading");
					if(r.code === 0){
						layer.closeAll();
						alert(r.msg, function(index){
							vm.reload();
							$('#importExcelfile').val('');
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},


		//打开导入界面
		importExcel: function(){
			vm.showImport = true;
			vm.wmyjInfo = {};
			$('#importExcelfile')[0].value="";
			layer.open({
				type: 1,
				offset: '50px',
				skin: 'layui-layer-molv',
				title: '导入',
				area: ['600px', '300px'],
				shade: 0.2,
				shadeClose: false,
				content: jQuery("#importLayer"),
				btn: ['确定', '取消'],
				btn1: function (index,layero) {
					layer.load();
					vm.saveOrUpdate2();
				},
				end: function (layero, index) {
					vm.reload();
				}
			});
		},

		//导出
		exportExcel: function(){
			// window.location.href = baseURL + "swzj/swzjmajorproject/exportExcel";

			layer.msg('导出中...', {
				icon: 16,
				shade: 0.01
			});
			//设置参数
			var params = {
				queryDate : vm.q.queryDate,
				auditFlag:vm.q.auditFlag,
				enterpriseName:vm.q.enterpriseName,
				city:vm.q.city,
				area:vm.q.area,
			}

			progressDownLoad(baseURL + "wmyj/wmyjinfo/exportExcel","出口信息汇总.xls",params,
				null,
				function(){
					layer.closeAll('loading');
				}
			);
		},
		/*
		市区二级下拉列表
		 */
        getTwoLevelCitys: function (id) {

			vm.twoLevelCitys = [];
			id = vm.q.city;
			this.$nextTick(function () {
				vm.twoLevelCitys = vm.areas[id];
			})
		},


		/*mounted:function(){
			laydate.render({
				elem: '#queryDate',
				type: 'month'
			});
		},*/
		query: function () {    //查询

			vm.reload(true);
		},
		autoFill:function(){   //填匹配，自动填充

			var url = "wmyj/wmyjinfo/autoFill";
			layer.load();   //显示加载动画，防止多次点击
			$.ajax({
				type: "POST",
				url: baseURL + url,
				contentType: "application/json",
				data: JSON.stringify(vm.wmyjInfo),

				success: function(r){
					/*r.wmyjInfo;*/
					if(r.wmyjInfo){
						vm.wmyjInfo.enterpriseName = r.wmyjInfo.enterpriseName;
						vm.wmyjInfo.unitCode = r.wmyjInfo.unitCode;
						vm.wmyjInfo.contact = r.wmyjInfo.contact;
						vm.wmyjInfo.telephone = r.wmyjInfo.telephone;
						layer.closeAll("loading");
						alert('匹配成功');
					}else{
						layer.closeAll("loading");  //打转的
						alert('匹配失败');
					}


					/*if(r.code === 0){
						layer.closeAll();
						alert('操作成功', function(index){
							$("#jqGrid").trigger("reloadGrid");
						});
					}else{
						alert(r.msg);
					}*/
				}
			});

		},
		passCheck:function(){   //审核通过方法
			var ids = getSelectedRows();

			if(ids == null){
				return ;
			}
			var url = "wmyj/wmyjinfo/audit";
			layer.load();   //显示加载动画，防止多次点击
			$.ajax({
				type: "POST",
				url: baseURL + url,
				/*contentType: "application/json",*/
				/*data: JSON.stringify(vm.wmyjInfo),*/
				data:{
					/*ids:ids,*/
					"ids":ids,
					"auditFlag":1
				},
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
		noPassCheck:function(){   //审核不通过方法
			var ids = getSelectedRows();

			if(ids == null){
				return ;
			}
			var url = "wmyj/wmyjinfo/audit";
			layer.load();
			$.ajax({
				type: "POST",
				url: baseURL + url,
				/*contentType: "application/json",*/
				/*data: JSON.stringify(vm.wmyjInfo),*/
				data:{
					/*ids:ids,*/
					"ids":ids,
					"auditFlag":0
				},
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
		add: function(){    //新增方法的初始化值
			vm.title = "新增";
			vm.showImport = false;
			vm.wmyjInfo = {
				orderFlag:1,
				priceFlag:1,
				dateIn:dateFmt("yyyy-MM-dd",new Date()),
				showFlag:0,
				statisFlag:0,
				enterpriseName:"",
				unitCode:"",
				contact:"",
				telephone:""
			};

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
		
			var url = vm.wmyjInfo.id == null ? "wmyj/wmyjinfo/save" : "wmyj/wmyjinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.wmyjInfo),
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
				    url: baseURL + "wmyj/wmyjinfo/delete",
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
			$.get(baseURL + "wmyj/wmyjinfo/info/"+id, function(r){
                vm.wmyjInfo = r.wmyjInfo;
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
           		postData:{'queryDate': vm.q.queryDate,'auditFlag': vm.q.auditFlag,'enterpriseName': vm.q.enterpriseName,
                'city':vm.q.city,'area':vm.q.area}
            }).trigger("reloadGrid");
		},

		addLayer: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: vm.title,
                area: ['900px', '520px'],
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