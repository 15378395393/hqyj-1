<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="renderer" content="webkit">
		<title>人事管理系统</title>
		
		<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		<script src="https://cdn.bootcss.com/jquery-datetimepicker/2.5.20/jquery.datetimepicker.full.js"></script>
		<script src="${pageContext.request.contextPath}/static/js/jqPaginator.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/static/js/hrms.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/static/js/common.js" type="text/javascript"></script>
		
	    <link href="${pageContext.request.contextPath}/static/css/hrms.css" type="text/css" rel="stylesheet">
	    <link href="${pageContext.request.contextPath}/static/css/admin.css" type="text/css" rel="stylesheet">
	    <link href="https://cdn.bootcss.com/jquery-datetimepicker/2.5.20/jquery.datetimepicker.css" type="text/css" rel="stylesheet">
	</head>
	<body>
		<div class="panel admin-panel">
			<div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>添加雇员</strong></div>
			<div class="body-content">
			
				<!-- message -->
				<div name="messageDiv" class="row" style="display:none;">
					<div class="col-lg-12">
						<div class="alert alert-info alert-dismissable">
							<i class="fa fa-info-circle"></i> <strong name="message" style="color: red;"></strong>
						</div>
					</div>
				</div>
				
				<form id="form1" method="post" class="form-x" action="cdsacdas">
					<div class="form-group">
						<div class="label">
							<label>帐号：</label>
						</div>
						<div class="field">
							<input type="text" class="input w50" value="" name="account" />
							<div class="tips"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>密码：</label>
						</div>
						<div class="field">
							<input type="password" class="input w50" value="" name="password" />
							<div class="tips"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>姓名：</label>
						</div>
						<div class="field">
							<input type="text" class="input w50" value="" name="userName" />
							<div class="tips"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>性别：</label>
						</div>
						<div class="field" style="padding-top:8px;">
							<input name="userSex" type="radio" value="男" />男
							<input name="userSex" type="radio" value="女" />女
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>手机：</label>
						</div>
						<div class="field">
							<input type="text" class="input w50" value="" name="userTelephone" />
							<div class="tips"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>电子邮箱：</label>
						</div>
						<div class="field">
							<input type="text" class="input w50" value="" name="userEmail" />
							<div class="tips"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>家庭地址：</label>
						</div>
						<div class="field">
							<input type="text" class="input w50" value="" name="userAddress" />
							<div class="tips"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>入职日期：</label>
						</div>
						<div class="field">
							<input type="text" class="input w50" value="" name="userEntrytime" />
							<div class="tips"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>出生时间：</label>
						</div>
						<div class="field">
							<input type="text" class="input w50" value="" name=userBirthday />
							<div class="tips"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>学历：</label>
						</div>
						<div class="field">
							<select name="userDiploma" class="input" style="width:120px; line-height:17px; display:inline-block">
								<option value="大专">大专</option>
								<option value="本科">本科</option>
								<option value="硕士">硕士 </option>
								<option value="博士">博士</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>部门安排：</label>
						</div>
						<div class="field">
							<select name="departId" class="input" style="width:120px; line-height:17px; display:inline-block">
								<c:forEach items="${departments}" var="department">
									<option value="${department.departId}">${department.departName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>职位安排：</label>
						</div>
						<div class="field">
							<select name="positionId" class="input" style="width:120px; line-height:17px; display:inline-block">
								<c:forEach items="${positions}" var="position">
									<option value="${position.positionId}">${position.positionName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="label">
							<label>指定角色：</label>
						</div>
						<div class="field" style="padding-top:8px;">
							<c:forEach items="${roles}" var="role">
								<input name="userRoles" type="checkbox" value="${role.roleId}"/>
								${role.roleDesc}
							</c:forEach>
						</div>
					</div>
					
					<div class="form-group">
						<div class="label">
							<label></label>
						</div>
						<div class="field">
							<button class="button bg-main icon-check-square-o" name="submitButton"> 提交</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			
			//时间插件
			$('[name=userEntrytime]').datetimepicker({
				format: 'Y-m-d'
			});
			$('[name=userBirthday]').datetimepicker({
				format: 'Y-m-d'
			});
			
			// 部门选择事件
			$("[name=departId]").bind("change", function(){
				$.ajax({
					url : "/organization/positionsByDepart?departId=" + $("[name=departId]").val(),
					type : "get",
					contentType: "application/json",
					success : function (data) {
						$("[name=positionId]").empty();
						$.each(data, function(i, item){
							$("[name=positionId]").append("<option value="+
									item.positionId+">"+item.positionName+"</option>");
						});
					},
					error : function (data) {
						$("[name=messageDiv]").show();
						$("[name=message]").text(data.message);
					}
				});
			});
			
			// 添加
			$("[name=submitButton]").bind("click", function() {
				var account = $("[name=account]").val();
				var password = $("[name=password]").val();
				if (account == "" || password == "") {
					$("[name=messageDiv]").show();
					$("[name=message]").text("请输入内容");
					return false;
				} else {
					$.ajax({
		        		url :"/account/addUser",
		        		type : "POST",
		        		data : $('#form1').serialize(),
		        		dataType : "text",
		        		success : function(data) {
		        			var result = eval("(" + data + ")");
		        			if (result.status == 200) {
		        				$("[name=messageDiv]").hide();
								location.replace("/account/userListPage");
		    				} else {
		    					$("[name=messageDiv]").show();
								$("[name=message]").text(result.message);
		    				}
		        		},
		        		error : function() {
		        			layer.msg('无法连接服务器', {icon: 2});
		        		}
		        	});
					return false;
				}
			});
		});
	</script>
</html>