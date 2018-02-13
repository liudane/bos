<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body>
	//使用ztree标准json数据展示树  关键点：通过children指定子节点
	<ul id="ztree1" class="ztree"></ul>
	<script type="text/javascript">
		// 配置信息 如果使用标准数据格式 使用缺省值
		var setting1 = {};
		
		//标准的 JSON 数据需要嵌套表示节点的父子包含关系   
		var nodes1 = [{"id":1,"name":"节点一",children:[{"id":101,"name":"1.1"}]},{"id":2,"name":"节点二"}];
		
		//调用ztree的初始化方法创建树
		//参数1：展示数据dom容器
		//参数2：设置信息
		//参数3：数据
		//返回zTree 对象，提供操作 zTree 的各种方法
		var zTreeObj = $.fn.zTree.init($("#ztree1"), setting1, nodes1);
	</script>
	
	<hr>
	//使用ztree简单json数据展示树  关键点：通过id/pId指定父子关系
	<ul id="ztree2" class="ztree"></ul>
	<script type="text/javascript">
		var setting2 = {
				data: {
					simpleData: {
						enable: true,  //启用简单数据格式
						idKey: "id",   //通过id表示节点数据
						pIdKey: "pId", //通过pId指定父节点数据
						rootPId: 0,    //顶级节点pId 为0
					}
		}};
		var nodes2 = [{"id":1,"pId":2,"name":"节点1"},{"id":2,"pId":3,"name":"节点2"},{"id":3,"pId":0,"name":"节点3"}];
		var zTreeObj2 = $.fn.zTree.init($("#ztree2"), setting2, nodes2);
	</script>
</body>
</html>