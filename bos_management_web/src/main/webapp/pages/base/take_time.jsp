<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page isELIgnored="false" %>
	<head>
		<meta charset="UTF-8">
		<title>收派时间管理</title>
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
				
				// 收派时间管理信息表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
					pageList: [2,3,5],
                    pageSize:2,
					pagination : true,
					toolbar : toolbar,
					url : "${pageContext.request.contextPath}/takeTimeAction_pageQuery.action",
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
					$("#addWindow").window("open");
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
                        $("#addForm").form("load",rows[0]);
                        //弹出窗口
                        $("#addWindow").window("open");
                    }else{
                        $.messager.alert("提示信息","一次只能修改一条记录","warning");
                    }
                }

			},{
				id : 'button-delete',
				text : '删除',
				iconCls : 'icon-cancel',
				handler : function(){
                    //判断选中的数据个数
                    var rows = $("#grid").datagrid("getSelections");
                    if(rows.length>0){
                        $.messager.confirm("提示信息","确定要删除吗？",function(r){
                            if(r){
                                //创建数组对象
                                var array = new Array();
                                for(var i=0; i<rows.length;i++){
                                    var id = rows[i].id;
                                    //push() 向数组末尾添加一个或多个元素，并返回新的长度
                                    array.push(id);
                                }
                                var ids = array.join(",");
                                window.location.href="${pageContext.request.contextPath}/takeTimeAction_delete.action?ids="+ids;
                            }
                        })
                    }else {
                        $.messager.alert("提示信息","请至少选中一条数据","warning");
                    }
				}
			} ];
			
			// 定义列
			var columns = [ [ {
				field : 'id',
				checkbox : true,
                width : 'flexible'
			},{
				field : 'name',
				title : '时间名称',
				width : 120,
				align : 'center'
			}, {
				field : 'normalWorkTime',
				title : '平时上班时间',
				width : 120,
				align : 'center'
			}, {
				field : 'normalDutyTime',
				title : '平时休息时间',
				width : 120,
				align : 'center'
			}, {
				field : 'satWorkTime',
				title : '周六上班时间',
				width : 120,
				align : 'center'
			}, {
				field : 'satDutyTime',
				title : '周六休息时间',
				width : 120,
				align : 'center'
			}, {
				field : 'sunWorkTime',
				title : '周日上班时间',
				width : 120,
				align : 'center'
			}, {
				field : 'sunDutyTime',
				title : '周日休息时间',
				width : 120,
				align : 'center'
			}, {
				field : 'status',
				title : '状态',
				width : 120,
				align : 'center'
			}, {
				field : 'company',
				title : '所属单位',
				width : 120,
				align : 'center'
			} , {
				field : 'operator',
				title : '操作人',
				width : 120,
				align : 'center'
			}, {
				field : 'operationTime',
				title : '操作时间',
				width : 120,
				align : 'center',
			}, {
				field : 'operatingCompany',
				title : '操作单位',
				width : 120,
				align : 'center'
			} ] ];
		</script>
	</head>
	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="grid"></table>
		</div>
        <div class="easyui-window" title="对收派时间进行添加或者修改" id="addWindow" collapsible="false"
             minimizable="false" maximizable="false" modal="true" closed="true" style="width:600px;top:50px;left:200px">
            <div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
                <div class="datagrid-toolbar">
                    <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
                    <script type="text/javascript">
                        $("#save").click(function(){
                            var r = $("#addWindow").form("validate");
                            if(r){
                                $("#addForm").submit();
                            }
                        })
                    </script>
                </div>
            </div>

            <div region="center" style="overflow:auto;padding:5px;" border="false">

                <form id="addForm" action="${pageContext.request.contextPath}/takeTimeAction_save.action" method="post">
                    <table class="table-edit" width="80%" align="center">
                        <tr class="title">
                            <td colspan="2">收派时间管理
                                <!--提供隐藏域 装载id -->
                                <input type="hidden" name="id" />
                            </td>
                        </tr>

                        <tr>
                            <td>时间名称</td>
                            <td>
                                <input type="text" name="name"
                                       class="easyui-validatebox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>平时上班时间</td>
                            <td>
                                <input type="text" name="normalWorkTime" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>平时休息时间</td>
                            <td>
                                <input type="text" name="normalDutyTime" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>周六上班时间</td>
                            <td>
                                <input type="text" name="satWorkTime" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>周六休息时间</td>
                            <td>
                                <input type="text" name="satDutyTime" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>周日上班时间</td>
                            <td>
                                <input type="text" name="sunWorkTime" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>周日休息时间</td>
                            <td>
                                <input type="text" name="sunDutyTime" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>状态</td>
                            <td>
                                <input type="text" name="status" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>所属单位</td>
                            <td>
                                <input type="text" name="company" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>操作人</td>
                            <td>
                                <input type="text" name="operator" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>

                        <tr>
                            <td>操作单位</td>
                            <td>
                                <input type="text" name="operatingCompany" class="easyui-validatebox" required="true" />
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
	</body>
</html>
