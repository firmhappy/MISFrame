<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ModelManage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
	var treeobj=parent.treeobj;
	
	</script>

  </head>
  
  <body>
 	   菜单项管理
 	 <table width="100%">
 	 	<tr>
 	 		<td><button onclick="editMenu()">编辑</button></td>
 	 		<td><button>增加</button></td>
 	 		<td><button>修改</button></td>
 	 		<td><button>删除</button></td>
 	 	</tr>
 	 	<tr>
 	 		<td colspan="4">
 	 			<p>模块属性</p>
 	 			<table >
 	 				<thead>
 	 					<th>属性名</th>
 	 					<th>属性值</th>
 	 				</thead>
 	 				<tbody>
 	 				
 	 				</tbody>
 	 			</table>
 	 		</td>
 	 	</tr>
 	 </table>
 <script type="text/javascript">
 	function editMenu(){
 		
 	}
 </script>
  </body>
</html>
