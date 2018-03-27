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
  <link rel="alternate icon" type="image/png" href="${pageContext.request.contextPath }/assets/i/favicon.png">
  <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/amazeui.min.css"/>
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
    <h3>用户登录</h3>
    <hr>
    <div class="am-btn-group">
      <a href="#" class="am-btn am-btn-secondary am-btn-sm"><i class="am-icon-github am-icon-sm"></i> Github</a>
      <a href="#" class="am-btn am-btn-success am-btn-sm"><i class="am-icon-google-plus-square am-icon-sm"></i> Google+</a>
      <a href="#" class="am-btn am-btn-primary am-btn-sm"><i class="am-icon-stack-overflow am-icon-sm"></i> stackOverflow</a>
    </div>
    <br>
    <br>

    <form method="post" onsubmit="return check_login()" action="${pageContext.request.contextPath}/user/login" class="am-form">
       <label for="username">用户名:</label>
      <input type="text" name="userName" id="userName" value="" >
      <br>
      <label for="password">密码:</label>
      <input type="password" name="password" id="password" value="">
      <br>
     <!--  <label for="remember-me">
        <input id="remember-me" type="checkbox">
        记住密码
      </label> -->
      	<label for="remark">验证码:</label>
     	<img   alt="" id="random_code" onclick="change_randomcode()" src="${pageContext.request.contextPath}/randomcode.jsp">
      	<input type="text" name="randomcode" id="randomcode" value="">
      	<div><font color="red" id="message">${message}</font></div>
      <br />
      <div class="am-cf">
        <input type="submit" name="" value="登 录" class="am-btn am-btn-primary am-btn-sm am-fl">
        <input type="submit" name="" value="忘记密码 ^_^? " class="am-btn am-btn-default am-btn-sm am-fr">
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
function check_login(){
	
	var userName=$('#userName').val().trim();
	var password=$('#password').val().trim();
	var randomcode=$('#randomcode').val().trim();
	if(userName==''||password==''||randomcode==''){
		$('#message').html("请填写完整");
		return false;
	}
	if(userName.length<6||userName.length>20){
		$('#message').html("用户名长度不得小于6或大于20");
		return false;
	}
	if(password.length<6||password.length>20){
		$('#message').html("密码长度不得小于6或大于20");
		return false;
	}
	return true;
}
</script>
</html>