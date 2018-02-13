<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<a class="easyui-menubutton" data-options="menu:'#mm',iconCls:'icon-help'">控制面板</a>
	<div id="mm">
		<div onclick="updatePwd()">修改密码</div>
		<div onclick="about()">关于系统</div>
		<div>帮助</div>
		<div onclick="logout()">退出系统</div>
	</div>
	<script type="text/javascript">
		function about(){
			//error,question,info,warning.
			$.messager.alert("系统信息","速运快递二期项目","warning",function(){
				alert("点击确定按钮后触发");
			})
		}
		function logout(){
			//error,question,info,warning.
			$.messager.confirm("提示信息","是否确定退出？",function(r){
// 				alert(r);
				if(r){
					//执行目标操作
				}
			})
		}
		
		function updatePwd(){
			$.messager.progress();   
			$.messager.progress('close');  
		}
		
		$(function(){
			$.messager.show({  	
				  title:'系统信息',  	
				  msg:'欢迎【admin】用户登陆',  	
				  timeout:3000,  	
				  showType:'slide'  
				});  
		})
	</script>
</body>
</html>