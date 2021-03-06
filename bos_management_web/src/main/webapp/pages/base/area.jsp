<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page isELIgnored="false" %>
	<head>
		<meta charset="UTF-8">
		<title>区域设置</title>
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
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ocupload/jquery.ocupload-1.1.2.js"></script>
		<script type="text/javascript">

			

			
			//工具栏
			var toolbar = [ {
				id : 'button-add',
				text : '增加',
				iconCls : 'icon-add',
				handler : function(){
                    $("#areaWindow").window("open");
                }
			}, {
                id : 'button-edit',
                text : '修改',
                iconCls : 'icon-edit',
                handler : function(){
                    //首先判断选中的数据
                    var rows = $("#grid").datagrid("getSelections");
                    if(rows.length == 1){
                        $("#areaForm").form("load", rows[0]);
                        $("#areaWindow").window("open");
                    }else{
                        $.messager.alert("提示信息","请选中一条信息","warning")
                    }
                }
            }, {
				id : 'button-delete',
				text : '删除',
				iconCls : 'icon-cancel',
				handler : function(){
                    //判断选中的数据个数
                    var rows = $("#grid").datagrid("getSelections");
                    if(rows.length>0){
                        $.messager.confirm("提示信息", "确定要删除这些数据吗？", function (r) {
                            if(r) {
                                //创建数组对象，保存选中的id
                                var array = new Array();
                                for(var i = 0; i < rows.length; i++) {
                                    var id = rows[i].id;
                                    array.push(id);
                                }
                                var ids = array.join(",");
                                //ids后少写了一个“=”号
                                window.location.href="${pageContext.request.contextPath}/areaAction_delete.action?ids="+ids;
                            }
                        })
                    }else{
                        $.messager.alert("提示信息","请至少选中一条信息","warning")
                    }
                }
			}, {
				id : 'button-import',
				text : '导入',
				iconCls : 'icon-redo'
			}];
			// 定义列
			var columns = [ [ {
				field : 'id',
				checkbox : true,
			},{
				field : 'province',
				title : '省',
				width : 120,
				align : 'center'
			}, {
				field : 'city',
				title : '市',
				width : 120,
				align : 'center'
			}, {
				field : 'district',
				title : '区',
				width : 120,
				align : 'center'
			}, {
				field : 'postcode',
				title : '邮编',
				width : 120,
				align : 'center'
			}, {
				field : 'shortcode',
				title : '简码',
				width : 120,
				align : 'center'
			}, {
				field : 'citycode',
				title : '城市编码',
				width : 200,
				align : 'center'
			} ] ];
			
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				
				// 区域管理数据表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
					pageList: [30,50,100],
					pagination : true,
					toolbar : toolbar,
					url : "${pageContext.request.contextPath}/areaAction_pageQuery.action",
					idField : 'id',
					columns : columns,
					onDblClickRow : doDblClickRow
				});

                $("#button-import").upload({
                    action:"${pageContext.request.contextPath}/areaAction_importXls.action",
                    name:'upload',
                    onComplete:function(){
                        //上传成功后
                        $("#grid").datagrid("reload");
                    }
                })
				// 添加、修改区域窗口
				$('#addWindow').window({
			        title: '添加修改区域',
			        width: 400,
			        modal: true,
			        shadow: true,
			        closed: true,
			        height: 400,
			        resizable:false
			    });
				
			});
		
			function doDblClickRow(){
				alert("双击表格数据...");
			}
		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="grid"></table>
		</div>
		<div class="easyui-window" title="区域添加修改" closed="true" id="areaWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
			<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
                    <script type="text/javascript">
                        $("#save").click(function(){
                            var r = $("#areaForm").form("validate");
                            if(r) {
                                $("#areaForm").submit();
                            }
                        });
                    </script>
				</div>
			</div>

			<div region="center" style="overflow:auto;padding:5px;" border="false">
				<form id="areaForm" action="${pageContext.request.contextPath}/areaAction_save.action" method="post">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">区域信息</td>
                            <input type="hidden" name="id"/>
						</tr>
						<tr>
							<td>省</td>
							<td>
								<input type="text" name="province" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>市</td>
							<td>
								<input type="text" name="city" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>区</td>
							<td>
								<input type="text" name="district" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>邮编</td>
							<td>
								<input type="text" name="postcode" class="easyui-validatebox" required="true" />
							</td>
						</tr>
                        <!-- 后台自动生成
						<tr>
							<td>简码</td>
							<td>
								<input type="text" name="shortcode" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>城市编码</td>
							<td>
								<input type="text" name="citycode" class="easyui-validatebox" required="true" />
							</td>
						</tr>-->
					</table>
				</form>
			</div>
		</div>
		
		<!-- 查询区域-->
		<div class="easyui-window" title="查询区域窗口" closed="true" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="width: 400px; top:40px;left:200px">
			<div style="overflow:auto;padding:5px;" border="false">
				<form id="searchForm">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">查询条件</td>
						</tr>
						<tr>
							<td>省份</td>
							<td>
								<input type="text" name="province" />
							</td>
						</tr>
						<tr>
							<td>城市</td>
							<td>
								<input type="text" name="city" />
							</td>
						</tr>
						<tr>
							<td>区（县）</td>
							<td>
								<input type="text" name="district" />
							</td>
						</tr>
						<tr>
							<td colspan="2"><a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>

</html>