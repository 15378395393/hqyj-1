$(document).ready(function(){
	// 页面加载时候调用相应的js代码块
//	alert("1111111111");
	// 页面加载时候调用相应的js方法
//	test();
	
	/*
	 * 控制导航栏active属性
	 */
	$('.nav li a').each(function(){  
		if($($(this))[0].href==String(window.location))  
			$(this).parent().addClass('active');  
	});
	
	// 调用随机验证码
	var show_num = [];
    draw(show_num);
    
    // 绑定随机验证码框点击事件
    $("#canvas").on('click',function(){
        draw(show_num);
    })
	
    // 绑定用户登录按钮
	$("#loginButton").bind("click", function(){
		var user = {};
		user.userName = $("[name='userName']").val();
		user.password = $("[name='password']").val();
		user.rememberMe = $("[name='rememberMe']").prop('checked');
		if (user.userName == "" || user.password == "") {
			alert("Please input userName or password.");
			return;
		}
		
		var val = $(".input-val").val().toLowerCase();
        var num = show_num.join("");
        if(val==''){
            alert("请输入验证码！");
            return;
        }else if(val != num){
        	alert("验证码错误！请重新输入！");
            $(".input-val").val('');
            draw(show_num);
            return;
        }
		
		$.ajax({
			url : "/account/doLogin",
			type : "post",
			contentType: "application/json",
			data : JSON.stringify(user),
			success : function (data) {
				if (data.status == 200) {
					location.href = "/account/dashboard";
				} else {
					alert(data.message);
				}
			},
			error : function (data) {
				alert(data.responseText);
			}
		});
	});
	
	/*
	 * 用户注册
	 */
	$("#registerButton").bind("click", function() {
		var user = {};
		user.userName = $("[name='userName']").val();
		user.password = $("[name='password']").val();
		$.ajax({
			url : "/account/doRegister",
			type : "post",
			contentType: "application/json",
			data : JSON.stringify(user),
			success : function (data) {
				if (data.status == 200) {
					location.href = "/account/dashboard";
				} else {
					$("#message").html(data.message);
				}
			},
			error : function (data) {
				$("#message").html(data.responseText);
			}
		});
	});
	
	/*
	 * 点击 edit按钮，获取user数据，渲染到编辑框内
	 */
	$("[name='userEdit']").bind("click", function(){
        var userId = $(this).parents("tr").find("[name=userId]").text();
        var userName = $(this).parents("tr").find("[name=userName]").text();
        
        // ajax 获取被选中的 role list，选中相应的checkbox
        $.ajax({
        	url : "/account/roles/user/" + userId,
        	type : "get",
        	contentType: "application/json",
        	success : function (data) {
        		$.each(data, function(i, item){
        			$("input[name='roleCheckbox'][value=" + item.roleId + "]")
        				.attr("checked","checked");
        		});
        	},
        	error : function (data) {
        		$("[name=messageDiv]").show();
        		$("[name=message]").html(data.message);
        	}
        });
        
        $("#userId").val(userId);
        $("#userName").val(userName);
        $("#userList").hide();
		$("#userEdit").show();
	});
	
	/*
	 * 编辑user
	 */
	$("#userSubmit").bind("click", function(){
		var user = {};
		user.userId = $("#userId").val();
		var roles = new Array();
		$.each($("input[name='roleCheckbox']"), function(){
			if(this.checked){
				var role = {};
				role.roleId = $(this).val();
				roles.push(role);
			}
		});
		user.roles = roles;
		$.ajax({
			url : "/account/editUser",
			type : "post",
			contentType: "application/json",
			data : JSON.stringify(user),
			success : function (data) {
				if (data.status == 200) {
					location.href = "/account/users";
				} else {
					$("[name=messageDiv]").show();
					$("[name=message]").html(data.message);
				}
			},
			error : function (data) {
				$("[name=messageDiv]").show();
				$("[name=message]").html(data.message);
			}
		});
	});
	
	/*
	 * 控制role新增页面
	 */
	$("#addRole").bind("click", function(){
		$("#roleList").hide();
		$("#roleEdit").show();
	});
	
	/*
	 * 点击 edit按钮，获取role数据，渲染到编辑框内
	 */
	$("[name='editRole']").bind("click", function(){
        var roleId = $(this).parents("tr").find("[name=roleId]").text();
        var roleName = $(this).parents("tr").find("[name=roleName]").text();
        $("#roleId").val(roleId);
        $("#roleName").val(roleName);
        $("#roleList").hide();
		$("#roleEdit").show();
	});
	
	/*
	 * 新增或修改role
	 */
	$("#roleSubmit").bind("click", function(){
		var role = {};
		role.roleId = $("#roleId").val();
		role.roleName = $("#roleName").val();
		$.ajax({
			url : "/account/editRole",
			type : "post",
			contentType: "application/json",
			data : JSON.stringify(role),
			success : function (data) {
				if (data.status == 200) {
					location.href = "/account/roles";
				} else {
					$("[name=messageDiv]").show();
					$("[name=message]").html(data.message);
				}
			},
			error : function (data) {
				$("[name=messageDiv]").show();
				$("[name=message]").html(data.message);
			}
		});
	});
	
	/*
	 * 控制resource新增页面
	 */
	$("#addResource").bind("click", function(){
		$("#resourceList").hide();
		$("#resourceEdit").show();
	});
	
	/*
	 * 点击 edit按钮，获取相应数据，渲染到编辑框内
	 */
	$("[name='editResource']").bind("click", function(){
        var resourceId = $(this).parents("tr").find("[name=resourceId]").text();
        var resourceName = $(this).parents("tr").find("[name=resourceName]").text();
        var resourceUri = $(this).parents("tr").find("[name=resourceUri]").text();
        var permission = $(this).parents("tr").find("[name=permission]").text();
        
        // ajax 获取被选中的 role list，选中相应的checkbox
        $.ajax({
        	url : "/account/roles/resource/" + resourceId,
        	type : "get",
        	contentType: "application/json",
        	success : function (data) {
        		$.each(data, function(i, item){
        			$("input[name='roleCheckbox'][value=" + item.roleId + "]")
        				.attr("checked","checked");
        		});
        	},
        	error : function (data) {
        		$("[name=messageDiv]").show();
        		$("[name=message]").html(data.message);
        	}
        });
        
        $("#resourceId").val(resourceId);
        $("#resourceName").val(resourceName);
        $("#resourceUri").val(resourceUri);
        $("#permission").val(permission);
        $("#resourceList").hide();
		$("#resourceEdit").show();
	});
	
	/*
	 * 新增或修改resource
	 */
	$("#resourceSubmit").bind("click", function(){
		var resource = {};
		resource.resourceId = $("#resourceId").val();
		resource.resourceName = $("#resourceName").val();
		resource.resourceUri = $("#resourceUri").val();
		resource.permission = $("#permission").val();
		var roles = new Array();
		$.each($("input[name='roleCheckbox']"), function(){
			if(this.checked){
				var role = {};
				role.roleId = $(this).val();
				roles.push(role);
			}
		});
		resource.roles = roles;
		$.ajax({
			url : "/account/editResource",
			type : "post",
			contentType: "application/json",
			data : JSON.stringify(resource),
			success : function (data) {
				if (data.status == 200) {
					location.href = "/account/resources";
				} else {
					$("[name=messageDiv]").show();
					$("[name=message]").html(data.message);
				}
			},
			error : function (data) {
				$("[name=messageDiv]").show();
				$("[name=message]").html(data.message);
			}
		});
	});
});

// 画随机验证码框
function draw(show_num) {
    var canvas_width=$('#canvas').width();
    var canvas_height=$('#canvas').height();
    var canvas = document.getElementById("canvas");//获取到canvas的对象，演员
    var context = canvas.getContext("2d");//获取到canvas画图的环境，演员表演的舞台
    canvas.width = canvas_width;
    canvas.height = canvas_height;
    var sCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
    var aCode = sCode.split(",");
    var aLength = aCode.length;//获取到数组的长度
    
    for (var i = 0; i <= 3; i++) {
        var j = Math.floor(Math.random() * aLength);//获取到随机的索引值
        var deg = Math.random() * 30 * Math.PI / 180;//产生0~30之间的随机弧度
        var txt = aCode[j];//得到随机的一个内容
        show_num[i] = txt.toLowerCase();
        var x = 10 + i * 20;//文字在canvas上的x坐标
        var y = 20 + Math.random() * 8;//文字在canvas上的y坐标
        context.font = "bold 23px 微软雅黑";

        context.translate(x, y);
        context.rotate(deg);

        context.fillStyle = randomColor();
        context.fillText(txt, 0, 0);

        context.rotate(-deg);
        context.translate(-x, -y);
    }
    for (var i = 0; i <= 5; i++) { //验证码上显示线条
        context.strokeStyle = randomColor();
        context.beginPath();
        context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.stroke();
    }
    for (var i = 0; i <= 30; i++) { //验证码上显示小点
        context.strokeStyle = randomColor();
        context.beginPath();
        var x = Math.random() * canvas_width;
        var y = Math.random() * canvas_height;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.stroke();
    }
}

// 生成随机数
function randomColor() {//得到随机的颜色值
    var r = Math.floor(Math.random() * 256);
    var g = Math.floor(Math.random() * 256);
    var b = Math.floor(Math.random() * 256);
    return "rgb(" + r + "," + g + "," + b + ")";
}

// 测试方法
function test() {
	alert("22222222");
}