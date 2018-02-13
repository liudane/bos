<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page isELIgnored="false" %>
	<head>
		<meta charset="UTF-8">
		<title>角色添加</title>
		<!-- 导入jquery核心类库 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
		<!-- 导入easyui类库 -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/ext/portal.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/ext/jquery.portal.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/ext/jquery.cookie.js"></script>
		<script src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<!-- 导入ztree类库 -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
                //查询权限数据，以复选框checkbox展示
                $.post("${pageContext.request.contextPath}/permissionAction_findAll.action", null, function(data){
                    for(var i = 0; i < data.length; i++){
                        var id = data[i].id;
                        var name = data[i].name;
                        $("#permissionTD").append('<input type="checkbox" name="permissionIds" id="'+id+'" value="'+id+'" /><label for="'+id+'">'+name+'</label>')
                    }
                })
				// 授权树初始化
				var setting = {
					data : {
						key : {
							title : "t"
						},
						simpleData : {
							enable : true //启用简单数据格式：需要使用id/pId表示节点的父子包含关系
						}
					},
					check : {
						enable : true //设置 zTree 的节点上是否显示checkbox
					}
				};
				
				$.ajax({
					url : '${pageContext.request.contextPath}/menuAction_findBySimple.action',
					type : 'POST',
					dataType : 'json',
					success : function(data) {
//						var zNodes = eval("(" + data + ")");//当服务器返回json数据时，需要将字符串转为json对象
						$.fn.zTree.init($("#menuTree"), setting, data);
					},
					error : function(msg) {
						alert('树加载异常!');
					}
				});
				
				// 点击保存
				$('#save').click(function(){
					var r = $("#roleForm").form("validate");
                    if(r){
                        //获取选中的菜单数据id
                        //查询ztree的api，调用ztree方法，必须先获取到ztree对象
                        var treeObj = $.fn.zTree.getZTreeObj("menuTree");
                        var nodes = treeObj.getCheckedNodes(true);
                        var array = new Array();
                        for(var i = 0; i<array.length; i++){
                            array.push(nodes[i].id);
                        }
                        var menuIds = array.join(",");
                        $("#menuIds").val(menuIds);
                        $("#roleForm").submit();
                    }
				});
			});
		</script>
	</head>

	<body class="easyui-layout">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="roleForm" method="post" action="${pageContext.request.contextPath}/roleAction_save.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
						<td>名称</td>
						<td>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td>
							<input type="text" name="keyword" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>描述</td>
						<td>
							<textarea name="description" rows="4" cols="60"></textarea>
						</td>
					</tr>
					<tr>
						<td>权限选择</td>
						<td id="permissionTD">
							<!-- <input type="checkbox" name="permissionIds" value="1" /> 添加快递员
							<input type="checkbox" name="permissionIds" value="2" /> 快递员列表查询
							<input type="checkbox" name="permissionIds" value="3" /> 添加区域 -->
						</td>
					</tr>
					<tr>
						<td>菜单授权</td>
						<td>
							<ul id="menuTree" class="ztree"></ul>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>

</html>