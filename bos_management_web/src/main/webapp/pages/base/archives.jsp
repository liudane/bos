<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page isELIgnored="false" %>
	<head>
		<meta charset="UTF-8">
		<title>基本档案信息管理</title>
		<!-- 导入jquery核心类库 -->
		<script type="text/javascript" src="../../js/jquery-1.8.3.js"></script>
		<!-- 导入easyui类库 -->
		<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="../../js/easyui/ext/portal.css">
		<link rel="stylesheet" type="text/css" href="../../css/default.css">
		<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../../js/easyui/ext/jquery.portal.js"></script>
		<script type="text/javascript" src="../../js/easyui/ext/jquery.cookie.js"></script>
		<script src="../../js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});

				// 基础档案信息表格
				$('#archives_grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
					pageList: [30,50,100],
					pagination : true,
					toolbar : toolbar,
					url : "../../data/archives.json",
					idField : 'id',
					columns : columns
				});
				
				// 子档案信息表格
				$('#sub_archives_grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
					pageList: [30,50,100],
					pagination : true,
					url : "../../data/sub_archives.json",
					idField : 'id',
					columns : child_columns
				});
			});	
			
			//工具栏
			var toolbar = [ {
				id : 'button-add',
				text : '增加',
				iconCls : 'icon-add',
				handler : function(){
					$("#archivesWindow").window("open");
				}
			}, {
				id : 'button-edit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function(){
					alert('修改');
				}
			},{
				id : 'button-save',
				text : '保存',
				iconCls : 'icon-save',
				handler : function(){
					alert('保存');
				}
			} ];

			// 定义列
			var columns = [ [ {
				field : 'archiveId',
				title : '基础档案编号',
				width : 120,
				align : 'center'
			},{
				field : 'archiveName',
				title : '基础档案名称',
				width : 120,
				align : 'center'
			}, {
				field : 'hasChild',
				title : '是否分级',
				width : 120,
				align : 'center'
			}, {
				field : 'remark',
				title : '备注',
				width : 300,
				align : 'center'
			} ] ];
			
			var child_columns = [ [ {
				field : 'id',
				title : '档案编码',
				width : 120,
				align : 'center'
			},{
				field : 'subArchiveName',
				title : '档案名称',
				width : 120,
				align : 'center'
			}, {
				field : 'archive.id',
				title : '上级编码',
				width : 120,
				align : 'center'
			}, {
				field : 'mnemonicCode',
				title : '助记码',
				width : 120,
				align : 'center'
			}, {
				field : 'mothballed',
				title : '封存',
				width : 120,
				align : 'center'
			}, {
				field : 'remark',
				title : '备注',
				width : 300,
				align : 'center'
			} ] ];
		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="archives_grid"></table>
		</div>
        <div class="easyui-window" title="对收派标准进行添加或者修改" id="archivesWindow" collapsible="false"
             minimizable="false" maximizable="false" modal="true" closed="true" style="width:600px;top:50px;left:200px">
            <div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
                <div class="datagrid-toolbar">
                    <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
                    <script type="text/javascript">
                        $("#save").click(function(){
                            var r = $("#archivesForm").form("validate");
                            if(r){
                                $("#archivesForm").submit();
                            }
                        })
                    </script>
                </div>
            </div>

            <div region="center" style="overflow:auto;padding:5px;" border="false">

                <form id="archivesForm" action="${pageContext.request.contextPath}/archivesAction_save.action" method="post">
                    <table class="table-edit" width="80%" align="center">
                        <tr class="title">
                            <td colspan="2">基础档案设置
                                <!--提供隐藏域 装载id -->
                                <input type="hidden" name="id" />
                            </td>
                        </tr>

                        <tr>
                            <td>基础档案编号</td>
                            <td>
                                <input type="text" name="archiveId"
                                       class="easyui-numberbox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>基础档案名称</td>
                            <td>
                                <input type="text" name="archiveName" class="easyui-numberbox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>是否分级</td>
                            <td>
                                <input type="text" name="hasChild" class="easyui-numberbox" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td>备注</td>
                            <td>
                                <input type="text" name="remark" class="easyui-numberbox" required="true" />
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
	</body>
</html>
