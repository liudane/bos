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
	//将html元素渲染为dagtagrid样式
	<table class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>jack</td>
				<td>18</td>
			</tr>
			<tr>
				<td>2</td>
				<td>rose</td>
				<td>19</td>
			</tr>
		</tbody>
	</table>
	<hr>
	//方式二：发送ajax请求获取json数据  easyui-datagrid数据表格：要求数据规范 无分页数据格式：[{},{},{}]
	<table class="easyui-datagrid" data-options="url:'../data/user.json'">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
	</table>
	
	//方式三：通过easyui提供api创建datagrid
	<table id="myTable"></table>
	<script type="text/javascript">
		$(function(){
			$('#myTable').datagrid({ 
				url:'../data/user.json', //发送请求获取数据------数据来源
				columns:[[   //展示数据---显示数据问题
					{field:'id',title:'编号',width:100}, 
					{field:'name',title:'姓名',width:100}, 
					{field:'age',title:'年龄',width:100,align:'right'} 
				]],
				//实现分页要求数据格式：{"total":100, rows:[{},{}]}
				//返回json对象，total:总记录数  rows:当前页数据
				pagination:true,
				pagePosition:'both',
				toolbar:[
						{  		
						  iconCls: 'icon-save',  
						  text:'增加',
						  	handler: function(){  //点击事件
							  	alert('add')
							}  	
						  },
				         {  		
						  iconCls: 'icon-edit',  
						  text:'修改',
						  	handler: function(){  //点击事件
							  	alert('edit')
					  		}  	
						  },{  		
						  iconCls: 'icon-help',  	
						  text:"帮助",
							  handler: function(){
								  alert('help')
							  }  	
					  }],
				striped:true,
				pageNumber:2,
				pageSize:5,
				pageList:[5,6,7],
				rownumbers:true,
				singleSelect:true
			}); 
		})
	</script>
</body>
</html>