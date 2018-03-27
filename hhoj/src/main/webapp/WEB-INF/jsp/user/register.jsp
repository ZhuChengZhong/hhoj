<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>黄淮OJ练习场HTML登录界面 - cssmoban</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <script src="${pageContext.request.contextPath }/bootstrap/js/jquery-1.11.2.min.js"></script>
  <link rel="alternate icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css"/>
  <style>
    .header {
      text-align: center;
    }
    .header h1 {
      font-size: 200%;
      color: #333;
      margin-top: 30px;
    }
    .header p {
      font-size: 14px;
    }
  </style>
</head>
<body>
<div class="header"> 
  <hr />
</div>
<div class="am-g">
  <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
    <h3>用户注册</h3>
    <hr>
    <br>
    <form method="post" action="index.html" class="am-form">
    
     <label for="username">用户名:</label>
      <input type="text" name="" id="userName" value="" placeholder="长度限制">
      <br>
      <label for="password">密码:</label>
      <input type="password" name="" id="password" value="" placeholder="长度限制">
      <br>
      <label for="password-confirm">确认密码:</label>
      <input type="password" name="" id="password_confirm" value="" placeholder="">
      <br>
      <label for="email">邮箱:</label>
      <input type="email" name="" id="email" value="">
      <br>
      <br>
      <label for="remark">自我介绍:</label>
     <textarea  rows="3" id="sign"></textarea>
      <br>
     <label for="remark">验证码:</label>
     <img   alt="" id="random_code" onclick="change_randomcode()" src="${pageContext.request.contextPath}/randomcode.jsp">
      <input type="text" name="" id="randomcode" value="">
      <div><font color="red" id="message">${message}</font></div>
      <div class="am-cf">
      	
        <input type="button" onclick="register()" name="" value="注 册" class="am-btn am-btn-primary am-btn-sm am-fl">
        
      </div>
    </form>
    <hr>
    <p>© 2018 个人版权所有.</p>
  </div>
</div>
</body>
<script type="text/javascript">

	/*
		点击图片切换验证码
	*/
	function change_randomcode(){
		var url="/hhoj/randomcode.jsp?"+Math.random();
		$('#random_code').attr('src',url);
	}

	
	/*
		用户注册
	*/
	function register(){
		
		var userName=$('#userName').val().trim();
		var password=$('#password').val().trim();
		var password_confirm=$('#password_confirm').val().trim();
		var email=$('#email').val().trim();
		var sign=$('#sign').val().trim();
		var randomcode=$('#randomcode').val().trim();
		if(userName==''||password==''||password_confirm==''||email==''||sign==''||randomcode==''){
			$('#message').html("信息未填写完整");
			return;
		}
		if(userName.length<6||userName.length>20){
			$('#message').html("用户名长度不得小于6或大于20");
			return;
		}
		if(password.length<6||password.length>20){
			$('#message').html("密码长度不得小于6或大于20");
			return;
		}
		if(password!=password_confirm){
			$('#message').html("密码不一致");
			return;
		}
		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		 if (!filter.test(email)){
			 $('#message').html("邮箱格式不正确");
				return;
		 }
		var url="/hhoj/user/register";
		 $.ajax({  
             async:false,   //使用同步的Ajax请求  
             type: "POST",  
             url: url, 
             dataType:"json",
             data: {
            	 userName:userName,
            	 password:password,
            	 email:email,
            	 sign:sign,
            	 randomcode:randomcode
             },  
             success: function(result){ 
            	 if(result.success){
            		 window.location.href=result.redirect;
            	 }else{
            		 $('#message').html(message);
            	 }
             }  
         }); 
	}
</script>
</html>