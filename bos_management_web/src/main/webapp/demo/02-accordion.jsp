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
<body class="easyui-layout">
	<!-- 通过data-options设置属性 -->
	<div data-options="region:'north'" style="height: 120px" title="XX系统">北部区域</div>
	<div data-options="region:'west'" style="width: 200px" title="菜单">
		<!-- 制作折叠面板 -->
		<div class="easyui-accordion" data-options="fit:true">
			<div title="基本菜单">
				菜单数据
			</div>
			<div title="系统菜单"></div>
		</div>
	</div>
	<div data-options="region:'center'">中部区域</div>
<!-- 	<div data-options="region:'east'" style="width: 200px">东部区域</div> -->
<!-- 	<div data-options="region:'south'" style="height: 80px">南部区域</div> -->
</body>
</html>