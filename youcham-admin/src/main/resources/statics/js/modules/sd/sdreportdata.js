$(function () {
	$("#jqGrid").jqGrid({
		url: baseURL + 'sd/sdreportdata/list',
		datatype: "json",
		colModel: [
			{ label: 'id', name: 'id', index: 'ID', width: 50, key: true, hidden: true},
			{ label: '共需方', name: 'reportType', index: 'REPORT_TYPE', width: 80 , formatter(value, row, option) {
					if (value == 0) {
						return "需求方"
					} else {
						return "提供方"
					}
				} },
			{ label: '名称/公司名称', name: 'name', index: 'NAME', width: 80 },
			{ label: '所在地（国家）', name: 'mapDesc.countryDesc', index: 'PROVINCE', width: 80 },
			{ label: '所在地（省）', name: 'mapDesc.provinceDesc', index: 'PROVINCE', width: 80 },
			{ label: '所在地（市）', name: 'mapDesc.cityDesc', index: 'CITY', width: 80 },
			{ label: '详细地址（门牌号）', name: 'address', index: 'ADDRESS', width: 80 },
			{ label: '客户类型(字典编码)', name: 'mapDesc.typeDesc', index: 'CUSTOMER_TYPE', width: 80 },
			// { label: '物资对接方式', name: 'acceptTypes', index: 'ACCEPT_TYPES', width: 80 },
			{ label: '联系人', name: 'contacts', index: 'CONTACTS', width: 80 },
			{ label: '联系人手机号', name: 'contactsTel', index: 'CONTACTS_TEL', width: 80 },
			{ label: '需求来源（字典编码）', name: 'mapDesc.sourceDesc', index: 'SOURCE_TYPE', width: 80 },
			{ label: '物资清单', name: "id", index: 'SOURCE_TYPE', width: 80, formatter(value, row, option) {
					return "<a href='#' onclick='vm.view(\"" + row.rowId +"\")'>查看</a>"
				}},
			/*{ label: '密码', name: 'password', index: 'PASSWORD', width: 80 },
			{ label: '定位经纬度', name: 'latLng', index: 'LAT_LNG', width: 80 }, 			
			{ label: '定位地址', name: 'locationAddress', index: 'LOCATION_ADDRESS', width: 80 }, 		*/

		],
		viewrecords: true,
		height: 385,
		rowNum: 10,
		rowList : [10,30,50],
		rownumbers: true,
		rownumWidth: 25,
		autowidth:true,
		multiselect: true,
		sortname: "createTime",
		sortorder: "desc",
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

var id = T.p("id");
var vm = new Vue({
	el: '#rrapp',
	data() {
		var validcodePhone=(rule,value,callback)=>{
			let reg=/^([1][3,4,5,6,7,8,9])\d{9}$/
			if(!reg.test(value)){
				callback(new Error('手机号错误')
				)
			}else{
				callback()
			}
		};
		return {
			showList: false,
			q: {},
			sdReportData: {
				reportType: 0,
				acceptTypes: [],
				city: null,
				goodsList: [
					{goodsName: "", goodsNum: ""}
				],
				sourceType: null,
				needLogistics: null,
				province: null,
			},
			reportTypeList:
				[
					{ value: 0, label: '我是需求方'},
					{value: 1, label: '我是供应方' }
				],
			customerTypeList: [],
			needLogisticsList: [
				{value: 0, label: "否"},
				{value: 1, label: "是"},
			],
			fileList: [], // 文件上传绑定的list
			fileList1: [], // 中介作用
			sourceTypeList: [], // 需求来源
			resourceNameList: [], // 物资名称
			acceptTypesNeedList: [], // 物资对接方式 (需求方)
			acceptTypesProvideList: [], // 物资对接方式 (提供方)
			url: "",
			isShowImg: false,
			goodsList: [
				{goodsName: "", goodsNum: ""}
			],
			provinceList: [],
			cityList: [],
			rules: {
				name: [
					{required: true, message: "名称不能为空", trigger: 'blur'}
				],
				city: [
					{required: true, message: "地市不能为空", trigger: 'blur'}
				],
				country: [
					{required: true, message: "国家不能为空", trigger: 'blur'}
				],
				province: [
					{required: true, message: "省份不能为空", trigger: 'blur'}
				],
				address: [
					{required: true, message: "地址不能为空", trigger: 'blur'}
				],
				customerType: [
					{required: true, message: "类型不能为空", trigger: 'blur'}
				],
				acceptTypes: [
					{required: true, message: "物资对接不能为空", trigger: 'blur'}
				],
				contacts: [
					{required: true, message: "联系人不能为空", trigger: 'blur'}
				],
				contactsTel: [
					{required: true, message: "联系人电话不能为空", trigger: 'blur'},
					{validator:validcodePhone,trigger:'blur'}
				],
				sourceType: [
					{required: true, message: "需求来源不能为空", trigger: 'blur'}
				],
				password: [
					{required: true, message: "密码不能为空", trigger: 'blur'}
				]
			},
			countryList: [], // 国家
			provinceList: [], // 省
			fileIsExist: false,  // 是否存在文件
			isShowForm: false,
			deleteIds: [], // 物资删除ids
			typeLoading: true, // 加载动画
			resourceLoading: true, // 加载动画
			demandLoading: true, // 加载动画
			acceptNeedLoading: true, // 加载动画
			acceptProvideLoading: true, // 加载动画
			cityLoading: true, // 加载动画
			countryLoading: true, // 加载动画
			provinceLoading: false, // 加载动画
		}
	},
	created() {

		this.getType();
		this.getDemand();
		this.getCountry();
		this.getCity();

		this.getResource();
		this.getAcceptTypeNeed();
		this.getAcceptTypeProvide();

	},
	watch: {
		/*isShowForm(v1, v2) {
			if (v1 == true) {
				this.$nextTick(()=>{
					this.$refs.form.resetFields();

				});
			}

		}*/
	},
	methods: {
		/* 修改确认密码 */
		confirmPas(id) {
			this.$prompt('请输入修改密码', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				closeOnClickModal:false,   //点击model背景层不关闭MessageBox
				inputErrorMessage: '请输入修改密码'
			}).then((value, action) => {
				this.isUpdate(id, value.value);
			})
				.catch(() => {
					location.href = "report_list.html";
				})
		},
		/* 是否可以修改 */
		isUpdate(id, password) {
			$.get(baseURL + "api/sd/reportData/isuUpdate",  {id : id, password: password}).then((data) => {
				if (data.code == 0) {
					vm.isShowForm = true;
				} else {
					this.$alert("密码错误", "提示").then(() => {
						this.confirmPas(id);
					}) ;
				}
			})
		},
		/* 添加 */
		add() {
			vm.isShowForm = true;
			Vue.nextTick(() => {
				this.$refs.form.clearValidate();
			})
			vm.sdReportData = {
				reportType: 0,
				acceptTypes: [],
				city: null,
				goodsList: [
					{goodsName: "", goodsNum: ""}
				],
				sourceType: null,
				needLogistics: null,
				province: null,
			};
			vm.fileList = [];
			vm.fileList1 = [];
			vm.deleteIds = [];
		},
		/* 修改 */
		update() {
			vm.sdReportData = {
				fileIds: null,
			};
			vm.fileList = [];
			vm.fileList1 = [];
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			layer.load();
			vm.getInfo(id);
		},
		/* 删除 */
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
					url: baseURL + "sd/sdreportdata/delete",
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
		/* 查询 */
		query() {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			if(event){
				page = 1;
			}

			$("#jqGrid").jqGrid('setGridParam',{
				page:page,
				postData:{'needProvide': vm.q.needProvide}
			}).trigger("reloadGrid");
		},
		/* 查看物资 */
		view(id) {
			layer.open({
				type : 2,
				offset : '10px',
				skin : 'layui-layer-molv',
				title : "物资清单",
				area : [ '985px','478px'],
				shade : 0.2,
				shadeClose : false,
				content : baseURL+"modules/sd/sdreportgoods.html?reportDataId="+id,
				btn : ['关闭'],
				success: function(layero, index) {
					//获取当前弹出窗口的索引及初始大小
					layerResize(index);
				},
				btn1: function(index,layero){
					layer.close(index);
				}

			});
		},
		getInfo(id) {

			$.get(baseURL + "api/sd/reportData/info/" + id, (data) => {
				vm.sdReportData = data.sdReportData;
			}).then(() => {
				vm.sdReportData.acceptTypes = (vm.sdReportData.acceptTypes).split(",");
				$.get(baseURL + "api/sd/reportData/getCountryOrProvince", {"pid" : vm.sdReportData.country}, (data) => {
					vm.provinceList = data.list;
				}).then(() => {
					this.provinceLoading = false;
				});

				/* 回显图片 */
				if (vm.sdReportData.fileIds) {
					var fileIds = (vm.sdReportData.fileIds).split(",");
					$.each(fileIds, (i, v) => {
						vm.fileList.push(
							{ name: "", url:  baseURL + "fileAction/loadFile?bytearrayId="+ encodeURI(v),
								fileId: v
							}
						);
						vm.fileList1.push(v);
					})
				}
				vm.isShowForm = true;
				layer.closeAll();
				this.$nextTick(()=>{
					this.$refs['form'].clearValidate();
				});
			})
		},
		onSuccess(data, file, fileList) {
			/* 声明第三方, 否则无法加id */
			vm.fileList1.push(data.gid);
			vm.sdReportData.fileIds  = (vm.fileList1).join(",");
			if (vm.fileList1.length == fileList.length) {
				vm.sdReportData.acceptTypes = (vm.sdReportData.acceptTypes).join(",");
				vm.saveOrUpdate();
			}
		},
		/* 提交表单 */
		onSubmit() {
			this.$refs.form.validate((valid) => {
				if (valid) {
					/* 没有文件 */
					if (!vm.fileIsExist) {
						vm.sdReportData.acceptTypes = (vm.sdReportData.acceptTypes).join(",");
						vm.saveOrUpdate();
					} else {
						this.$refs.upload.submit();
					}
				} else {
					this.$alert("你有必填项未填..", "提示");
				}
			});
		},
		/* 跳转 */
		hrefHtml() {
			if (id) {
				location.href = "report_list.html";
			} else {
				location.href = "report_list.html";
			}
		},
		/* 保存或修改 */
		saveOrUpdate() {
			var url = vm.sdReportData.id == null ? baseURL + "api/sd/reportData/save" : baseURL + "api/sd/reportData/update";
			$.postJSON(url,
				{
					"sdReportData" : JSON.stringify(vm.sdReportData),
					"goodsList" : JSON.stringify(vm.sdReportData.goodsList),
					"deleteIds" : JSON.stringify(vm.deleteIds),
				},
			).then((data) => {
				this.$alert(data.msg, "提示",
					{type: "success"}).then(() => {
					vm.isShowForm = false;
					$("#jqGrid").trigger("reloadGrid");
				}).catch(() => {
					vm.isShowForm = false;
					$("#jqGrid").trigger("reloadGrid");
				})
				vm.fileIsExist = false;
			}, () => {
				this.$alert("错误", "提示", {type: "error"});
			});
		},
		handlePictureCardPreview(file) {
			this.url = file.url;
			this.isShowImg = true;
		},
		/* 图片超过限制钩子 */
		onExceed(file, fileList) {
			this.$alert("最多只能上传五个", "提示").catch(() => {});
		},
		/* 文件改变限制钩子 */
		onChange(file, fileList) {
			var isJPG = (file.raw.type === 'image/jpeg' || file.raw.type === 'image/png');
			var isLt2M = file.size / 1024 / 1024 < 2;

			if (!isJPG) {
				this.$alert('只能上传jpg/png图片!');
				vm.fileList.splice(fileList.length-1, 1);
			}
			if (!isLt2M) {
				this.$alert('上传头像图片大小不能超过 2MB!');
				vm.fileList.splice(fileList.length-1, 1);
			}
			if (isJPG && isLt2M) {
				/* true */
				vm.fileIsExist = fileList.length > 0;
			}
			return isJPG && isLt2M;
		},
		/* 删除文件钩子 */
		onRemove(file, fileList) {
			console.warn( file);
			console.warn( fileList);
			$.each(vm.fileList1, (i, v) => {
				if (file.fileId == v) {
					vm.fileList1.splice(i, 1);
				}
			})
			vm.sdReportData.fileIds  = (vm.fileList1).join(",");
			if (fileList.length == 0) {
				vm.fileIsExist = false;
			}
		},
		/* 删除文件之前钩子 */
		beforeRemove(file, fileList) {
			return  this.$confirm('是否删除该文件?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '我再想想',
				type: 'warning'
			});
		},
		/* 动态添加物资清单 */
		addLogistics() {
			vm.sdReportData.goodsList.push({goodsName : "", goodsNum: "" })
			console.warn(vm.sdReportData.goodsList);
		},
		/* 删除物资清单 */
		remove(index, id) {
			vm.deleteIds.push(id);
			if (vm.sdReportData.goodsList.length == 1) {
				alert("至少保留一项");
				return
			}
			vm.sdReportData.goodsList.splice(index, 1);
		},
		/* 获取类型 */
		getType() {
			$.get(baseURL + "api/sd/reportData/finddic", {"type" : "type"}, (data) => {
				vm.customerTypeList = data.templateList;
			}).then(() => {
					this.typeLoading = false;
				})
			;
		},
		/* 获取需求来源 */
		getDemand() {
			$.get(baseURL + "api/sd/reportData/finddic", {"type": "demand"}, (data) => {
				vm.sourceTypeList = data.templateList;
			}).then(() => {
				this.demandLoading = false;
			});
		},
		/* 获取物资清单 */
		getResource() {
			$.get(baseURL + "api/sd/reportData/finddic", {"type" : "resource"}, (data) => {
				vm.resourceNameList = data.templateList;
			}).then(() => {
				this.resourceLoading = false;
			});
		},
		/* 获取物资对接方式 (需求方) */
		getAcceptTypeNeed() {
			$.get(baseURL + "api/sd/reportData/finddic", {"type" : "acceptTypeNeed"}, (data) => {
				vm.acceptTypesNeedList = data.templateList;
			}).then(() => {
				this.acceptNeedLoading = false;
			});
		},
		/* 获取物资对接方式 (提供方) */
		getAcceptTypeProvide() {
			$.get(baseURL + "api/sd/reportData/finddic", {"type" : "acceptTypeProvide"}, (data) => {
				vm.acceptTypesProvideList = data.templateList;
			}).then(() => {
				this.acceptProvideLoading = false;
			});
		},
		/* 获取国家 */
		getCountry() {
			$.get(baseURL + "api/sd/reportData/getCountryOrProvince", {"level" : "2"}, (data) => {
				vm.countryList = data.list;
			}).then(() => {
				this.countryLoading = false;
			});
		},
		/* 获取省 */
		getProvince(id) {
			vm.provinceList = [];
			vm.sdReportData.province = null;
			$.get(baseURL + "api/sd/reportData/getCountryOrProvince", {"pid" : id}, (data) => {
				vm.provinceList = data.list;
			}).then(() => {
				this.provinceLoading = false;
			});
		},
		/* 获取市 */
		getCity(id) {
			$.get(baseURL + "api/sd/reportData/selectCityByJiangXi", (data) => {
				vm.cityList = data.list;
			}).then(() => {
				this.cityLoading = false;
			});
		},
		/* 清除一些字段 */
		clearAcceptList() {
			/* 对接方式 */
			vm.sdReportData.acceptTypes = [];
			/* 需求来源 */
			vm.sdReportData.sourceType = null;
			/* 是否需要物流 */
			vm.sdReportData.needLogistics = null;
			vm.sdReportData.country = null;
			vm.sdReportData.province = null;
			vm.sdReportData.city = null;
			this.$refs.form.clearValidate();
		},
		/* 物资其他 */
		resourceOther(goodsName, index) {
			if (goodsName == "其他") {

		/*		/!* 移动端使用 *!/
				this.$prompt('请输入物资名称', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					closeOnClickModal:false,   //点击model背景层不关闭MessageBox
					inputPattern: /([^\s])/g ,
					inputErrorMessage: '物资名称不能为空'
				}).then(({ value, action }) => {
					vm.sdReportData.goodsList[index].goodsName = value;
				}).catch(() => {

				})*/
				//自定义标题风格
				/*  layer.open({
                      title: [
                          '我是标题',
                          'background-color: #FF4351; color:#fff;'
                      ]
                      ,content: $("#resourceName")
                  });*/
				 this.$prompt('请输入物资名称', '提示', {
                     confirmButtonText: '确定',
                     cancelButtonText: '取消',
                     closeOnClickModal:false,   //点击model背景层不关闭MessageBox
                     inputPattern: /([^\s])/g ,
                     inputErrorMessage: '物资名称不能为空'
                 }).then(({ value }) => {
                     vm.sdReportData.goodsList[index].goodsName = value;
                 }).catch(() => {

                 });
			}

		}
	}
})