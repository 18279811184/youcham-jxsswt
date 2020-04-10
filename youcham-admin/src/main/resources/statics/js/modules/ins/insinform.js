$(function () {
	
    $("#jqGrid").jqGrid({
        url: baseURL + 'ins/insinform/list',
        datatype: "json",
        colModel: [			
			{ label: 'informId', name: 'informId', index: 'inform_id', width: 50, key: true ,hidden:true},
			{ label: '标题名称', name: 'informTitle', index: 'inform_title', width: 80 }, 			
			{ label: '所属栏目', name: 'insColumn.columnName', index: 'column_id', width: 80 },		
			{ label: '发布状态', name: 'bePublish', index: 'be_publish', width: 80 ,
				formatter: function(value, options, row){
					
					if(value == 0){
						return '<span class="label label-danger">待发布</span>'
					}else if(value == 1){
						return '<span class="label label-success">已发布</span>'
					}else if(value == 2){
						return '<span class="label label-success">已过期</span>'
					}else if(value == 3){
						return '<span class="label label-success">已废弃</span>'
					}
				}		
			}, 			
//			{ label: '是否置顶', name: 'beStick', index: 'be_stick', width: 80,
//				formatter: function(value, options, row){
//					return value === 0 ? 
//							'否' : 
//							'是';
//				}		
//			}, 			
//			{ label: '是否标红', name: 'beRed', index: 'be_red', width: 80,
//				formatter: function(value, options, row){
//					return value === 0 ? 
//							'否' : 
//							'是';
//				}		
//			}, 			
			{ label: '发布时间', name: 'createTime', index: 'createTime', width: 80 }, 			
			{ label: '排序编号', name: 'informOrder', index: 'inform_order', width: 80 }		
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		insInform: {},
		colunmns:[],
		colunmns2:[],
		q:{
			informtitle: null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			//vm.showList = false;
			vm.title = "新增";
			vm.insInform = {};
//			vm.getcolunmn();
			showadd();
		},
		update: function (event) {
			var informId = getSelectedRow();
			if(informId == null){
				return ;
			}
			//vm.showList = false;
            vm.title = "修改";
//            vm.getcolunmn();
            
           // vm.getInfo(informId);
            showadd(informId);
            
		},
		
		del: function (event) {
			var informIds = getSelectedRows();
			if(informIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ins/insinform/delete",
                    contentType: "application/json",
				    data: JSON.stringify(informIds),
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
		getInfo: function(informId){
			$.get(baseURL + "ins/insinform/info/"+informId, function(r){
                vm.insInform = r.insInform;
                console.log(r);
                console.log(vm.insInform.informContent+"获取");
                
                $("#divdata").val(vm.insInform.informContent);
                //var getproinfo = $("#divdata").val();
                var proinfo=$("#divdata").text();
                
               /* var proinfo=$("#divdata").val(); 
         		console.log("获得edit内容"+proinfo);
        	    editor.html(proinfo);  //赋值editor_id
                */                
         		console.log("获得edit内容"+proinfo);
         	
         		console.log(vm.insInform.informContent);
         		if(vm.insInform.informContent != "" && vm.insInform.informContent != null){
         			KindEditor.instances[0].insertHtml(vm.insInform.informContent);
         		}
         		
         		
         		//图片赋值
         		console.log("图片id-"+r.insInform.informImage);
         		$("#getallfileid").html(r.insInform.informImage);
            });

		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'informtitle': vm.q.informtitle,'columnid':vm.q.columnId},
                page:page
            }).trigger("reloadGrid");
			$("#getallfileid").html("");
		},
		getcolunmn:function(){
			$.get(baseURL+"ins/inscolumn/select",function(r){
				console.log(r);
				vm.colunmns = r.list;
			})
		}
		
	},
	created : function (event) {
		this.getcolunmn();
		
	},
	
});



function saveOrUpdate() {

	var url = vm2.insInform.informId == null ? "ins/insinform/save" : "ins/insinform/update";
	
//	console.log(JSON.stringify(vm2.insInform));
	var geturl = editor.html().replace(/\"/g,"'");
//	console.log("第一次:"+geturl);
	//geturl.replace(/<[^>]*>/g,"");
	/*geturl.replace("&lt;","<");
	geturl.replace(">","&gt;");*/
	
	$("#content").val(geturl);
//	console.log("dasabibibibi:"+geturl);
	//vm.insInform.informContent = "";
	
	vm2.insInform.informContent = geturl;
	//上传图片的id
	vm2.insInform.informImage = $("#getallfileid").html();
	console.log(JSON.stringify(vm2.insInform));
		$.ajax({
		type: "POST",
	    url: baseURL + url,
        contentType: "application/json",
	    data: JSON.stringify(vm2.insInform),
	    success: function(r){
	    	$("#getallfileid").html("");
	    	if(r.code === 0){
				alert('操作成功', function(index){
					parent.layer.closeAll();
//					 parent.location.reload();
					 
					 parent.vm.reload();
					//vm.reload();
					//self.opener.location.reload(); 
					
				});
			}else{
				alert(r.msg);
			}
		}
	});
}

//新增修改  弹出
function showadd(id){
	/*vm2.update(id);*/
	layer.open({
		type : 2,
		offset : '20px',
		skin : 'layui-layer-molv',
		title : vm.title,
		area : [ '1100px', '500px' ],
		shade : 0.2,
		shadeClose : false,
		/*content : jQuery("#layshow"),*/
		content : baseURL+"modules/ins/insinform2.html",
		btn : [ '确定', '取消' ],
		yes: function(index,layero){
			var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();

            //调用授权提交方法
            iframeWin.saveOrUpdate();
           
           //  vm2.saveOrUpdate();
            // layer.close(index);
             
		},
		end:function(layero, index){
		},
		success:function(layero, index){
			/*vm2.update(id);*/
			var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			
			if(isBlank(id)){// add
//				iframeWin.vm2.getMaxOrderNum();
			}else{
	            //调用授权提交方法
	            iframeWin.vm2.update(id);
			}
		}   
		
	});



}