<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page isELIgnored="false" %>
	<head>
		<meta charset="UTF-8">
		<title>取派标准</title>
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
		<script type="text/javascript">
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				
				// 收派标准信息表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
                    pageSize: 2,
					pageList: [2, 3, 5],
					pagination : true,
					toolbar : toolbar,
					url : "${pageContext.request.contextPath}/standardAction_pageQuery.action",
					idField : 'id',
					columns : columns
				});
			});	
			
			//工具栏
			var toolbar = [ {
				id : 'button-add',
				text : '增加',
				iconCls : 'icon-add',
				handler : function(){
//                    $("#standardForm").form("clear",rows[0]);
					$("#standardWindow").window("open");
				}
			}, {
				id : 'button-edit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function(){
					//首先判断选中的数据
                    var rows = $("#grid").datagrid("getSelections");
                    if(rows.length == 1){
                        //回显数据：给窗口中的表单输入项赋值
                        //调用表单load方法：加载数据填充表单输入项
                        //load方法参数是字符串：当作请求发送，需要在服务端查询到标准的json对象
                        //load方法参数是json对象，可以直接返回
                        $("#standardForm").form("load",rows[0]);
                        //弹出窗口
                        $("#standardWindow").window("open");
                    }else{
                        $.messager.alert("提示信息","一次只能修改一条记录","warning");
                    }
				}
			},{
				id : 'button-delete',
				text : '作废',
				iconCls : 'icon-cancel',
				handler : function(){
                    //判断选中的数据个数
                    var rows = $("#grid").datagrid("getSelections");
                    if(rows.length>0){
                        $.messager.confirm("提示信息","确定要作废吗？",function(r){
                            if(r){
                                //创建数组对象
                                var array = new Array();
                                for(var i=0; i<rows.length;i++){
                                    var id = rows[i].id;
                                    //push() 向数组末尾添加一个或多个元素，并返回新的长度
                                    array.push(id);
                                }
                                var ids = array.join(",");
                                window.location.href="${pageContext.request.contextPath}/standardAction_invalid.action?ids="+ids;
                            }
                        })
                    }else {
                        $.messager.alert("提示信息","请至少选中一条数据","warning");
                    }
				}
			},{
				id : 'button-restore',
				text : '还原',
				iconCls : 'icon-save',
				handler : function(){
                    //判断选中的数据个数
                    var rows = $("#grid").datagrid("getSelections");
                    if(rows.length>0){
                        $.messager.confirm("提示信息","确定要还原吗？",function(r){
                            if(r){
                                //创建数组对象
                                var array = new Array();
                                for(var i=0; i<rows.length;i++){
                                    var id = rows[i].id;
                                    //push() 向数组末尾添加一个或多个元素，并返回新的长度
                                    array.push(id);
                                }
                                var ids = array.join(",");
                                window.location.href="${pageContext.request.contextPath}/standardAction_validation.action?ids="+ids;
                            }
                        })
                    }else {
                        $.messager.alert("提示信息","请至少选中一条数据","warning");
                    }
				}
			}];
			
			// 定义列
			var columns = [ [ {
				field : 'id',
				checkbox : true
			},{
				field : 'name',
				title : '标准名称',
				width : 120,
				align : 'center'
			}, {
				field : 'minWeight',
				title : '最小重量',
				width : 120,
				align : 'center'
			}, {
				field : 'maxWeight',
				title : '最大重量',
				width : 120,
				align : 'center'
			}, {
				field : 'minLength',
				title : '最小长度',
				width : 120,
				align : 'center'
			}, {
				field : 'maxLength',
				title : '最大长度',
				width : 120,
				align : 'center'
			}, {
				field : 'operator',
				title : '操作人',
				width : 120,
				align : 'center'
			}, {
				field : 'operationTime',
				title : '操作时间',
				width : 120,
				align : 'center'
			}, {
				field : 'operatingCompany',
				title : '操作单位',
				width : 120,
				align : 'center'
			},{
                field : 'status',
                title : '状态',
                width : 120,
                align : 'center',
                formatter : function(data,row, index){
                    //单元格式化程序函数，取三个参数：
                    //data：字段值：当返回结果是简单类型时，使用第一个参数
                    //row：行记录数据
                    //index：行索引
                    if(data=="0"){
                        return "正常使用"
                    }else{
                        return "已作废";
                    }
                }
            } ] ];
		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="grid"></table>
		</div>
		
		<div class="easyui-window" title="对收派标准进行添加或者修改" id="standardWindow" collapsible="false"
             minimizable="false" maximizable="false" modal="true" closed="true" style="width:600px;top:50px;left:200px">
			<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
                    <script type="text/javascript">
                        $("#save").click(function(){
                            var r = $("#standardForm").form("validate");
                            if(r){
                                $("#standardForm").submit();
                            }
                        })
                    </script>
				</div>
			</div>

			<div region="center" style="overflow:auto;padding:5px;" border="false">
				
				<form id="standardForm" action="${pageContext.request.contextPath}/standardAction_save.action" method="post">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">收派标准信息
								<!--提供隐藏域 装载id -->
								<input type="hidden" name="id" />
							</td>
						</tr>
						<tr>
							<td>收派标准名称</td>
							<td>
								<input type="text" name="name" 
									class="easyui-validatebox" data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td>最小重量</td>
							<td>
								<input type="text" name="minWeight" 
										class="easyui-numberbox" required="true" />
							</td>
						</tr>
						<tr>
							<td>最大重量</td>
							<td>
								<input type="text" name="maxWeight" class="easyui-numberbox" required="true" />
							</td>
						</tr>
						<tr>
							<td>最小长度</td>
							<td>
								<input type="text" name="minLength" class="easyui-numberbox" required="true" />
							</td>
						</tr>
						<tr>
							<td>最大长度</td>
							<td>
								<input type="text" name="maxLength" class="easyui-numberbox" required="true" />
							</td>
						</tr>
                        <tr>
                            <td>操作人</td>
                            <td>
                                <input type="text" name="operator" class="easyui-validate" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>操作单位</td>
                            <td>
                                <input type="text" name="operatingCompany" class="easyui-validate" required="true" />
                            </td>
                        </tr>
					</table>
				</form>
			</div>
		</div>
	</body>

</html>