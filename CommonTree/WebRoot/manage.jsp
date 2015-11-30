<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="./plugin/demo.css" type="text/css">
<link rel="stylesheet" href="./plugin/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript" src="./plugin/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="./plugin/jquery.ztree.all-3.5.js"></script>

<SCRIPT type="text/javascript">
	var setting = {
		async : {
			enable : true,
			url : getUrl
		},
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		view : {
			addDiyDom : addDiyDom
		},
		callback : {
			beforeExpand : beforeExpand,
			onAsyncSuccess : onAsyncSuccess,
			onAsyncError : onAsyncError,
			onClick : zTreeonClick,
		}
	};
	var curPage = 0;
	var className='<%=request.getParameter("className").toString()%>';
	var pageSize='<%=request.getParameter("pageSize")%>';
	function getUrl(treeId, treeNode) {
		var param = "pid=" + treeNode.id + "&className=" + treeNode.className
				+ "&page=" + treeNode.page + "&pageSize=" + treeNode.pageSize, aObj = $("#"
				+ treeNode.tId + "_a");
		aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 "
				+ treeNode.maxPage + " 页");
		return "/CommonTree/GetChildrenAction.action?" + param;
	}
	function goPage(treeNode, page) {
		treeNode.page = page;
		if (treeNode.page < 1)
			treeNode.page = 1;
		if (treeNode.page > treeNode.maxPage)
			treeNode.page = treeNode.maxPage;
		if (curPage == treeNode.page)
			return;
		curPage = treeNode.page;
		var zTree = $.fn.zTree.getZTreeObj("zTree");
		zTree.reAsyncChildNodes(treeNode, "refresh");
	}
	function beforeExpand(treeId, treeNode) {
		if (treeNode.page == 0)
			treeNode.page = 1;
		return !treeNode.isAjaxing;
	}
	function onAsyncSuccess(event, treeId, treeNode, msg) {

	}
	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
			errorThrown) {
		var zTree = $.fn.zTree.getZTreeObj("zTree");
		alert("异步获取数据出现异常。");
		treeNode.icon = "";
		zTree.updateNode(treeNode);
	}
	function addDiyDom(treeId, treeNode) {
		if (treeNode.count < treeNode.pageSize)
			return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addBtn_" + treeNode.id).length > 0)
			return;
		var addStr = "<span class='button lastPage' id='lastBtn_"
				+ treeNode.id
				+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtn_"
				+ treeNode.id
				+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtn_"
				+ treeNode.id
				+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtn_"
				+ treeNode.id
				+ "' title='first page' onfocus='this.blur();'></span>";
		aObj.after(addStr);
		var first = $("#firstBtn_" + treeNode.id);
		var prev = $("#prevBtn_" + treeNode.id);
		var next = $("#nextBtn_" + treeNode.id);
		var last = $("#lastBtn_" + treeNode.id);
		treeNode.maxPage = Math.round(treeNode.count / treeNode.pageSize - .5)
				+ (treeNode.count % treeNode.pageSize == 0 ? 0 : 1);
		first.bind("click", function() {
			if (!treeNode.isAjaxing) {
				goPage(treeNode, 1);
			}
		});
		last.bind("click", function() {
			if (!treeNode.isAjaxing) {
				goPage(treeNode, treeNode.maxPage);
			}
		});
		prev.bind("click", function() {
			if (!treeNode.isAjaxing) {
				goPage(treeNode, treeNode.page - 1);
			}
		});
		next.bind("click", function() {
			if (!treeNode.isAjaxing) {
				goPage(treeNode, treeNode.page + 1);
			}
		});
	};
	function zTreeonClick(event, treeId, treeNode) {
		var data = treeNode.dataobj;
		$.each(data, function(i) {
			document.getElementById(i).value=data[i];
		});
	}
</SCRIPT>

<style type="text/css">
.ztree li span.button.firstPage {
	float: right;
	margin-left: 2px;
	margin-right: 0;
	background-position: -144px -16px;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.prevPage {
	float: right;
	margin-left: 2px;
	margin-right: 0;
	background-position: -144px -48px;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.nextPage {
	float: right;
	margin-left: 2px;
	margin-right: 0;
	background-position: -144px -64px;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.lastPage {
	float: right;
	margin-left: 2px;
	margin-right: 0;
	background-position: -144px -32px;
	vertical-align: top;
	*vertical-align: middle
}
.ztd{
	border:1px solid black;
}
</style>
</head>

<body onload="load()">
	<button onclick="update()">update</button>
	<button onclick="insert()">insert</button>
	<button onclick="entityremove()">delete</button>
	<table>
		<tr>
			<td>
				<div>
					<ul id="zTree" class="ztree"></ul>
				</div>
			</td>
			<td>
				<div>
					<table style="border-collapse:collapse">
						<thead>
							<tr id="entityth">
							</tr>

						</thead>
						<tbody>
							<tr id="entitytd">
							</tr>
						</tbody>
					</table>
				</div>
			</td>
		</tr>
	</table>


</body>
<script type="text/javascript">
	function load() {
		if(className==""||pageSize==""){
			alert("输入错误");
			return;
		}
		$.post("/CommonTree/GetChildrenAction.action", {
			pid : -1,
			className : className,
			page : "1",
			pageSize : pageSize,
		}, function(data, status) {
			$.fn.zTree.init($("#zTree"), setting, data);
			var tth = document.getElementById("entityth");
			var ttd = document.getElementById("entitytd");
			var thstr = "", tdstr = "";
			var readonly="readonly='readonly'";
			$.each(data[0].dataobj, function(i) {
				thstr += "<th class='ztd'>" + i + "</th>";
				var attribute="";
				if(i=="id"||i=="page"||i=="pageSize"||i=="className"||i=="count"||i=="path"||i=="isParent"){
					attribute=readonly;
				}
				tdstr += "<td class='ztd'><input "+attribute+"  type='text' id='"+i+"' value='&nbsp;'/></td>";
			});
			ttd.innerHTML = tdstr;
			tth.innerHTML = thstr;
		});
	}
	function makeparam(){		
		var jsonstr="";
		var ths=document.getElementById("entityth").getElementsByTagName("th");
		for(var i=0;i<ths.length;i++){
			var key=ths[i].innerHTML;
			var value=document.getElementById(key).value;
			if(i!=0){
				jsonstr+=",";
			}
			jsonstr+=("\""+key+"\":"+"\""+value+"\"");
		}		
		var param="{"+jsonstr+"}";
		return param;
	}
	
	function update(){
		
		var param=makeparam();
		$.post("/CommonTree/UpdateAction.action",{
			param:param
		},function(data,status){
			var treenode=treeobj.getNodeByParam("id",data.id);
			var optree=treeobj.getNodeByParam("id",treenode.pid);
			var nptree=treeobj.getNodeByParam("id",data.pid);
			treeobj.reAsyncChildNodes(nptree, "refresh");
			treeobj.reAsyncChildNodes(optree, "refresh");
		});
	}
	function insert(){
		var treeobj=$.fn.zTree.getZTreeObj("zTree");
		var param=makeparam();
		$.post("/CommonTree/InsertAction.action",{
			param:param
		},function(data,status){
			var parent=treeobj.getNodeByParam("id",data.pid);
			data.pageSize=parent.pageSize;
			treeobj.addNodes(parent,data);
			treeobj.reAsyncChildNodes(parent,"refresh");
		});
	}
	function entityremove(){
		var treeobj=$.fn.zTree.getZTreeObj("zTree");
		var param=makeparam();
		$.post("/CommonTree/DeleteAction.action",{
			param:param
		},function(data,status){
			var parent=treeobj.getNodeByParam("id",data.pid);
			treeobj.reAsyncChildNodes(parent,"refresh");
		});
	}
</script>
</html>
