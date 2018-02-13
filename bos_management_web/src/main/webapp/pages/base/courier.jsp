<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page isELIgnored="false" %>
	<head>
		<meta charset="UTF-8">
		<title>管理取派员</title>
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


			//工具栏
			var toolbar = [ {
                id : 'button-search',
                text : '查询',
                iconCls : 'icon-search',
                handler : function (){
                    $("#searchWindow").window("open");
                }
            },{
                id : 'button-add',
				text : '增加',
				iconCls : 'icon-add',
				handler : function (){
                    $("#courierWindow").window("open");
                }
			}, {
				id : 'button-edit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function (){
                    //首先判断选中的数据
                    var rows = $("#grid").datagrid("getSelections");
                    if(rows.length == 1){
                        //回显数据：给窗口中的表单输入项赋值
                        //调用表单load方法：加载数据填充表单输入项
                        //load方法参数是字符串：当作请求发送，需要在服务端查询到标准的json对象
                        //load方法参数是json对象，可以直接返回
                        $("#courierForm").form("load",rows[0]);
                        //弹出窗口
                        $("#courierWindow").window("open");
                    }else{
                        $.messager.alert("提示信息","一次只能修改一条记录","warning");
                    }
                }
			}, {
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
                                window.location.href="${pageContext.request.contextPath}/courierAction_invalid.action?ids="+ids;
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
				handler : function (){
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
                                window.location.href="${pageContext.request.contextPath}/courierAction_validation.action?ids="+ids;
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
				checkbox : true,
			},{
				field : 'courierNum',
				title : '工号',
				width : 80,
				align : 'center'
			},{
				field : 'name',
				title : '姓名',
				width : 80,
				align : 'center'
			}, {
				field : 'telephone',
				title : '手机号',
				width : 120,
				align : 'center'
			}, {
				field : 'checkPwd',
				title : '查台密码',
				width : 120,
				align : 'center'
			}, {
				field : 'pda',
				title : 'PDA号',
				width : 120,
				align : 'center'
			}, {
				field : 'standard.name',
				title : '取派标准',
				width : 120,
				align : 'center',
				formatter : function(data,row, index){
                    //单元格式化程序函数，取三个参数：
                    //data：字段值：当返回结果是简单数据类型时，使用
                    //row：行记录数据：当返回结果是对象类型时，使用
                    //index：行索引
					if(row.standard != null){
						return row.standard.name;
					}
					return "暂无数据";
				}
			}, {
				field : 'type',
				title : '取派员类型',
				width : 120,
				align : 'center'
			}, {
				field : 'company',
				title : '所属单位',
				width : 200,
				align : 'center'
			}, {
				field : 'deltag',
				title : '是否作废',
				width : 80,
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
			}, {
				field : 'vehicleType',
				title : '车型',
				width : 100,
				align : 'center'
			}, {
				field : 'vehicleNum',
				title : '车牌号',
				width : 120,
				align : 'center'
			} ] ];
			
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				
				// 取派员信息表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
                    pageSize:2,
					pageList: [2,3,5],
					pagination : true,
					toolbar : toolbar,
					url : "${pageContext.request.contextPath}/courierAction_pageQuery.action",
					idField : 'id',
					columns : columns,
					onDblClickRow : doDblClickRow,
                    onLoadError:function(data){
                        $.messager.alert("提示信息","数据加载异常，请稍后再试","error");
                    }
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
		<div class="easyui-window" closed="true" title="对收派员进行添加或者修改" id="courierWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
			<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
                    <script type="text/javascript">
                        $("#save").click(function(){
                           var r = $("#courierForm").form("validate");
                            if(r){
                                $("#courierForm").submit();
                            }
                        })
                    </script>
				</div>
			</div>

			<div region="center" style="overflow:auto;padding:5px;" border="false">
				<form id="courierForm" action="${pageContext.request.contextPath}/courierAction_save.action" method="post">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="4">收派员信息
                                <!--提供隐藏域 装载id -->
                                <input type="hidden" name="id" />
                            </td>
						</tr>
						<tr>
							<td>快递员工号</td>
							<td>
								<input type="text" name="courierNum" class="easyui-validatebox" required="true" />
							</td>
							<td>姓名</td>
							<td>
								<input type="text" name="name" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>手机</td>
							<td>
								<input type="text" name="telephone" class="easyui-validatebox" required="true" />
							</td>
							<td>所属单位</td>
							<td>
								<input type="text" name="company" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>查台密码</td>
							<td>
								<input type="text" name="checkPwd" class="easyui-validatebox" required="true" />
							</td>
							<td>PDA号码</td>
							<td>
								<input type="text" name="pda" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>取派员类型</td>
							<td>
								<input type="text" name="type" class="easyui-validatebox" required="true" />
							</td>
							<td>取派标准</td>
							<td>
								<input type="text" name="standard.id" 
										class="easyui-combobox" 
										data-options="required:true,valueField:'id',textField:'name',
											url:'${pageContext.request.contextPath}/standardAction_findAll.action'"/>
							</td>
						</tr>
						<tr>
							<td>车型</td>
							<td>
								<input type="text" name="vehicleType" class="easyui-validatebox" required="true" />
							</td>
							<td>车牌号</td>
							<td>
								<input type="text" name="vehicleNum" class="easyui-validatebox" required="true" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<!-- 查询快递员-->
		<div class="easyui-window" title="查询快递员窗口" closed="true" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="width: 400px; top:40px;left:200px">
			<div style="overflow:auto;padding:5px;" border="false">
				<form id="searchForm" >
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">查询条件</td>
						</tr>
						<tr>
							<td>工号</td>
							<td>
								<input type="text" name="courierNum" />
							</td>
						</tr>
						<tr>
							<td>收派标准</td>
							<td>
								<input type="text" name="standard.name" />
							</td>
						</tr>
						<tr>
							<td>所属单位</td>
							<td>
								<input type="text" name="company" />
							</td>
						</tr>
						<tr>
							<td>类型</td>
							<td>
								<input type="text" name="type" />
							</td>
						</tr>
						<tr>
							<td colspan="2"><a id="searchBtn"  class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                                <script type="text/javascript">
                                    $("#searchBtn").click(function(){

                                        //将查询条件获取-->调用工具方法，将表单中输入项序列化为json
                                        var condition = $("#searchForm").serializeJson();

                                        //发送请求，提交参数：查询条件、将分页查询的page，rows两个参数提交--->让数据表格重新发出请求pageQuery.action
                                        $("#grid").datagrid("load", condition);

                                        console.info(condition);

                                        //关闭查询窗口
                                        $("#searchWindow").window("close");
                                    })

                                    //将表单中的输入项序列化为json
                                    $.fn.serializeJson = function(){

                                        var serializeObj = {};
                                        var array = this.serializeArray();
                                        var str = this.serialize();

                                        $(array).each(function () {
                                            if(serializeObj[this.name]){
                                                if($.isArray(serializeObj[this.name])){
                                                    serializeObj[this.name].push(this.value);
                                                }else{
                                                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                                                }
                                            }else{
                                                serializeObj[this.name]=this.value;
                                            }
                                        });
                                        return serializeObj;
                                    }
                                </script>
                            </td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>

</html>