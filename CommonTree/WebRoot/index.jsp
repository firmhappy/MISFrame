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
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" href="./plugin/demo.css" type="text/css">
<link rel="stylesheet" href="./plugin/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript" src="./plugin/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="./plugin/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
	var setting = {
		async : {
			enable : true,
			url : getUrl
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeExpand : beforeExpand,
			onAsyncSuccess : onAsyncSuccess,
			onAsyncError : onAsyncError,
			onClick : zTreeonClick,
		}
	};
	var curPage = 0;
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
	function zTreeonClick(event, treeId, treeNode) {
		if(treeNode.id=="0"){
			return;
		}
		document.getElementById("modelframe").setAttribute("src", "./modeljsp/"+treeNode.name+".jsp");
	}
</script>
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

.ztd {
	border: 1px solid black;
}
</style>
</head>

<body onload="load()">
	<table>
		<tr>
			<td height="20%" rowspan="2">
				<div>
					<ul id="zTree" class="ztree"></ul>
				</div>
			</td>
			<td height="20%">
				<h2>模块展示</h2>
			</td>
		</tr>
		<tr>
			<td>
				<iframe src="" id="modelframe"></iframe>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		function load() {
			var className = window.prompt("请输入类名", "");
			var pageSize = window.prompt("请输入页面大小", "");
			if (className == "" || pageSize == "") {
				alert("输入错误");
				return;
			}
			$
					.post(
							"/CommonTree/GetChildrenAction.action",
							{
								pid : -1,
								className : className,
								page : "1",
								pageSize : pageSize,
							},
							function(data, status) {
								$.fn.zTree.init($("#zTree"), setting, data);
								var tth = document.getElementById("entityth");
								var ttd = document.getElementById("entitytd");
								var thstr = "", tdstr = "";
								var readonly = "readonly='readonly'";
								$
										.each(
												data[0].dataobj,
												function(i) {
													thstr += "<th class='ztd'>"
															+ i + "</th>";
													var attribute = "";
													if (i == "id"
															|| i == "page"
															|| i == "pageSize"
															|| i == "className"
															|| i == "count"
															|| i == "path"
															|| i == "isParent") {
														attribute = readonly;
													}
													tdstr += "<td class='ztd'><input "+attribute+"  type='text' id='"+i+"' value='&nbsp;'/></td>";
												});
							});

		}
		function post(param,methodname){
			var treeobj=$.fn.zTree.getZTreeObj("zTree");
			var modelname=treeobj.getSelectedNodes()[0].name;
			$.post("/CommonTree/ExecuteServiceAction.action",{
				param:param,
				methodname:methodname,
				modelname:modelname
			},function(data,status){
				modelframe.contentWindow.repost(data.result);
			});
			
		}
	</script>
</body>
</html>
