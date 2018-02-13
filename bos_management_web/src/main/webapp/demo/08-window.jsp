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
</head>
<body>
	<input type="button" value="显示窗口" id="showBtn">
	<div id="standardWindow" class="easyui-window" style="width: 600px;height: 400px" title="XX新增" data-options="collapsible:false" minimizable="false">
		<form id="standardForm" action="standardAction_save.action" method="post">
			<!-- 第一步：加样式。easyui-validatebox:如果用户输入无效的值,它将改变背景颜色,显示报警图标和一个工具提示消息 -->
			<!-- 第二步：设置校验规则 1、非空required 2、其他校验规则通过validType指定   -->
			<!-- 第三步：提交表单之前做表单校验 -->
			名称：<input type="text" name="name" class="easyui-validatebox" required="required" validType="length[3,6]">
			重量：<input type="text" name="weight"><br>
			<input type="button" value="保存" id="sumbitBtn">
		</form>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#sumbitBtn").click(function(){
				
				//validate方法，所有的输入项都合法后才返回true
// 				var r = $("#standardForm").form("validate");
// 				if(r){
// 					$("#standardForm").submit();
// 				}

				//easyui sumibt方法:提交ajax请求
				$("#standardForm").form("submit", {
					url:"xx.action",
					onSubmit:function(){
						//做表单校验
						var isValid = $(this).form('validate');  
// 						alert(isValid);
						return isValid;   //返回false,将阻止表单提交 
					},
					success:function(data){
						
					}
				});
			})
			$("#showBtn").click(function(){
				//调用window组件方法 展示window窗口
				$("#standardWindow").window("open");
			})
		})
	</script>
</body>
</html>