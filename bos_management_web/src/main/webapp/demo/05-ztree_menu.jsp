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
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body class="easyui-layout">
	<!-- 通过data-options设置属性 -->
	<div data-options="region:'north'" style="height: 120px" title="XX系统">北部区域</div>
	<div data-options="region:'west'" style="width: 200px" title="菜单">
		<!-- 制作折叠面板 -->
		<div class="easyui-accordion" data-options="fit:true">
			<div title="基本菜单">
<!-- 				<input type="button" value="点我" id="btn"> -->
				<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">点我</a>
				<script type="text/javascript">
				$(function(){
					$("#btn").click(function(){
						//判断子面板是否存在
						var r = $("#myTabs").tabs('exists','用户管理');
						if(r){
							//选中
							$("#myTabs").tabs('select','用户管理');
						}else{
							//在中心区域中选项卡面板中增加子面板
							//调用easyui tabs组件方法 新增新面板
							$("#myTabs").tabs("add",{
								 title: '用户管理',
								 iconCls:'icon-edit',
								 closable:true,
								 content:'<iframe frameborder="0" src="../pages/action/courier.jsp" height="100%" width="100%"></iframe>'
							})
						}
					})
				})
				</script>
			</div>
			<div title="系统菜单">
				<!-- 基于ztree的简单数据格式展示菜单数据 -->
				<ul id="myMenu" class="ztree"></ul>
				<script type="text/javascript">
					var setting = {
						data: {
							simpleData: {
								enable: true,
								idKey: "id",
								pIdKey: "pId",
								rootPId: 0,
							}
						},
						callback: {
							onClick: function(event, treeId, treeNode){   //点击回调函数
// 								alert(treeNode.page);
								if(treeNode.page!=undefined){
									//treeNode JSON 被点击的节点 JSON 数据对象
	// 								console.info(treeNode);
									//在中心区域显示面板
									//判断子面板是否存在
									var r = $("#myTabs").tabs('exists', treeNode.name);
									if(r){
										//选中
										$("#myTabs").tabs('select', treeNode.name);
									}else{
										//在中心区域中选项卡面板中增加子面板
										//调用easyui tabs组件方法 新增新面板
										$("#myTabs").tabs("add",{
											 title: treeNode.name,
											 iconCls:'icon-edit',
											 closable:true,
											 content:'<iframe frameborder="0" src="../'+treeNode.page+'" height="100%" width="100%"></iframe>'
										})
									}
								}
							} 
						}
					};
					//发送请求获取菜单数据
					$.post("../data/menu.json",null,function(data){
// 						alert(data);
						//将json对象 输出到浏览器控制台
						console.info(data);
						//调用ztree的初始化方法创建树
						var zTreeObj2 = $.fn.zTree.init($("#myMenu"), setting, data);
					})
				</script>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 制作折叠面板 -->
		<div id="myTabs" class="easyui-tabs" data-options="fit:true">
			<div title="系统管理">
				菜单数据
			</div>
			<div title="客户管理" data-options="closable:true,iconCls:'icon-save'"></div>
		</div>
	</div>
</body>
</html>