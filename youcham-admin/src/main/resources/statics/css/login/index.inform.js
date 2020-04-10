/**
 * 获取列表数据
 * @param iDisplayStart 起始数据
 * @param iDisplayLength 每页条数
 * @param pc 当前页码
 * inform_id 栏目id
 * 	
 */
function queryList(iDisplayStart,iDisplayLength,pc,inform_id,column_parent){
	$("#news-list").html("");
	$("#pagingUl").html("");
	var searchText=$("#searchText").val();
	var url=column_parent!="null"?"getHomeInform":"getHomeColumn";
	if(column_parent==null||column_parent=="null"){
		$("#column-list").show();
		getColumn(inform_id);
		
	}else{
		$("#column-list").hide();
	}
	$.ajax({
		url : "getHomeInform",
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			iDisplayStart:iDisplayStart,
			iDisplayLength:iDisplayLength,
			inform_id:inform_id,
			inform_title:searchText
		},
		type : 'post',
		success : function(r) {
			if(r.beans.length>0){
				$.each(r.beans, function(index, content) {
						$("#news-list").append("<li class='news-focus'><div class='news-items'>" +
								"<h6><a href='#' onclick='return false;' target='_blank'>"+content.title+"</a></h6>" +
										" <p>"+content.summary+"</p><span>"+content.time+"</span></li>"	
										
						);
            });
			
				
					var iTotalRecords=r.iTotalRecords;//总记录数
					 iDisplayLength=r.iDisplayLength;//每页显示条数
					 iDisplayStart=r.iDisplayStart;//起始页数
					var sEcho=r.sEcho;//访问次数
					var pageCode=Math.ceil(iTotalRecords/iDisplayLength);//页码
					if(iTotalRecords>=1){
						$("#pagingUl").append("<li><a class='pageitem'>"+iTotalRecords+"条</a></li>");
					}
					
					if(pc==1){
					
						$("#pagingUl").append("");
					}else{
						$("#pagingUl").append("<li><a href='#' onclick='queryList("+(pc-1-1)*15+",15,"+(pc-1)+","+inform_id+","+column_parent+");return false;' class='previous'>上一页</a></li>");
					}
					
					
					if(pc<=6){
						var tempTotal=7;
						if(pageCode<=6){
							tempTotal=pageCode;
						}
						for(var i=1;i<=tempTotal;i++){
							$("#pagingUl").append("<li><a href='#' onclick='queryList("+(i-1)*15+",15,"+i+","+inform_id+","+column_parent+");return false;' id='pagea_"+i+"'>"+i+"</a></li>");
						}
					}else{
						if(pageCode-pc!=0){
							for(var i=pc-5;i<=pc+1;i++){
								$("#pagingUl").append("<li><a href='#' onclick='queryList("+(i-1)*15+",15,"+i+","+inform_id+","+column_parent+");return false;'  id='pagea_"+i+"'>"+i+"</a></li>");
							}
						}else{
							for(var i=pc-5;i<=pageCode;i++){
								$("#pagingUl").append("<li><a href='#' onclick='queryList("+(i-1)*15+",15,"+i+","+inform_id+","+column_parent+");return false;'  id='pagea_"+i+"'>"+i+"</a></li>");
							}
						}
						
					}
					
					if(pageCode==pc){
					  $("#pagingUl").append("");
					}else{
						if(pageCode!=0){
							$("#pagingUl").append("<li><a href='#' onclick='queryList("+pc*15+",15,"+(pc+1)+","+inform_id+","+column_parent+");return false;' class='previous'>下一页</a></li>");

						}
					}
			}
		}
	});

	
	
}
/**
 * 获取页面参数
 * @returns {Object}
 */
function parseUrl(){
    var url=location.href;
    var i=url.indexOf('?');
    if(i==-1)return;
    var querystr=url.substr(i+1);
    var arr1=querystr.split('&');
    var arr2=new Object();
    for  (i in arr1){
        var ta=arr1[i].split('=');
        arr2[ta[0]]=ta[1];
    }
    return arr2;
}
/**
 * 查询
 */
function searchResult(){
	var dataParams = parseUrl();//解析所有参数
    var column_parent=dataParams["column_parent"];
	var inform_id="";
	var searchText=$("#searchText").val();
	if(searchText!=null&&searchText!=""){
		queryList(0,15,1,inform_id,column_parent);
	}
	  $("#columnName").text("");
	  $("#columnName").text("搜索结果");
	  $("#column-list").hide();
	  $("#h5-culumn").text("");
	  $("#h5-culumn").text("搜索结果");
	
}
/**
 * 获取栏目
 */
function getColumn(inform_id){
	$.ajax({
		url : "getHomeColumn",
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
		
			inform_id:inform_id
		},
		type : 'post',
		success : function(r) {
			$.each(r.beans, function(index, content) {
				$("#column-list").append("<ul class='news'><li class='news-focus'>" +
					"<div class='news-items'><h6><a href='inform.jsp?column_id="+content.column_id+"&column_name="+content.column_name+"&column_parent="+content.column_parent+"' target='_blank'>"+content.column_name+"</h6></a></div></li></ul>" 
				);
            });
			
				
		}
	});
}
/**
 * 获取栏目列表
 */
function getColumnList(){
	$.ajax({
		url : 'getHomeColumn',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {},
		type : 'post',
		success : function(r) {
				$.each(r.beans, function(index, content) {
					if(content.column_id == 1000001778346481){
						$("#indexLi").before("<li>" + "<a href='http://localhost/inspect/steady/html/inform/20180312/1000000949014234.html' target='_blank'>" + content.column_name + "</a></li>");
						return true;	
					}
					
					$("#indexLi").before("    <li>" + 
						"<a href='inform.jsp?column_id="+content.column_id+"&column_name="+content.column_name+"&column_parent="+content.column_parent+"' target='_blank'>" 
					  + content.column_name + "</a></li>");
				});
				
		}
	});
}
$(function() {
	/*var dataParams = parseUrl();//解析所有参数
    var inform_id=dataParams["column_id"];
    $("#columnName").text("");
    $("#columnName").text(decodeURI(dataParams["column_name"]));
    $("#h5-culumn").text("");
    $("#h5-culumn").text(decodeURI(dataParams["column_name"]));
    var column_parent=dataParams["column_parent"];
	queryList(0,15,1,inform_id,column_parent);
	getColumnList();*/
	
})