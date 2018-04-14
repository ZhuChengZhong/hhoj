<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>黄淮学院在线练习场首页 - ACM－HHOJ</title>
  <meta name="description" content="这是一个 index 页面">
  <meta name="keywords" content="index">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath }/assets/i/favicon.png">
  <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath }/assets/i/app-icon72x72@2x.png">
  <meta name="apple-mobile-web-app-title" content="Amaze UI" />
 
  
  <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/admin.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/hhoj.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/bootstrap/css/bootstrap-theme.min.css">
<script src="${pageContext.request.contextPath }/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="${pageContext.request.contextPath }/bootstrap/js/bootstrap.min.js"></script>

</head>
<%
	String mainPage=(String)request.getAttribute("mainPage");
	if(mainPage==null || "".equals(mainPage)) {
		mainPage="default.jsp";
		request.setAttribute("mainPage", mainPage);
	}
%>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
  以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar admin-header">
  <div class="am-topbar-brand">
    <strong>黄淮学院在线练习场</strong> <small>HHOJ</small>
  </div>

  <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>
<!-- nav试用 -->
  
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="hhoj_navbar_box">  
    </div>
    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">黄淮学院在线练习场-ACM&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</a>


    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li ><a href="${pageContext.request.contextPath }/index"><span class="am-icon-home"></span>练习场 </a></li>
        <li><a href="admin-problemType.html"><span class="am-icon-pencil-square-o"></span>分类</a></li>
        <li><a href="${pageContext.request.contextPath }/contest/list/1"><span class="am-icon-th"></span>竞赛</a></li>
        <li><a href="${pageContext.request.contextPath }/problem/list/1"><span class="am-icon-puzzle-piece">测试</a></li>
        <li><a href="#"><span class="am-icon-file"></span>运行</a></li>
        <c:if test="currentUser!=null&&currentUser.role>1">
        	 <li><a href="${pageContext.request.contextPath }/manager"><span class="am-icon-table"></span>管理</a></li>
        </c:if>
      </ul>
      <form class="navbar-form navbar-left">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">检索</button>
      </form>
      <div class="am-collapse am-topbar-collapse" id="topbar-collapse">

    <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
      <!--  <li><a href="javascript:;"><span class="am-icon-envelope-o"></span> 收件箱 <span class="am-badge am-badge-warning">5</span></a></li>-->
     <c:if test="${currentUser==null}">
     		 <li class="am-dropdown" data-am-dropdown>
		        <a  href='${pageContext.request.contextPath}/user/login' class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          <span class="am-icon-users"></span> 登陆
		        </a>
		      </li>
		       <li class="am-dropdown" data-am-dropdown>
		        <a  href="${pageContext.request.contextPath}/user/register" class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          <span class="am-icon-users"></span> 注册
		        </a>
		        </li>
     </c:if>
     <c:if test="${currentUser!=null}">
     		 <li class="am-dropdown" data-am-dropdown>
		        <a  href='' class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          <span class="am-icon-users"></span> <font color="red">${currentUser.userName}</font>
		        </a>
		      </li>
		       <li class="am-dropdown" data-am-dropdown>
		        <a  href="${pageContext.request.contextPath}/user/logout" class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          <span class="am-icon-users"></span> 退出
		        </a>
		        </li>
     </c:if>
      <li><a href="javascript:;" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>
    </ul>
  </div>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<!-- nav试用结束 -->

</header>

<div class="am-cf admin-main">
  <!-- sidebar start -->
  <div class="admin-sidebar">

 
 
   
   
   
     <div class="am-panel am-panel-default admin-sidebar-panel" style="height: 600px">
      <div class="am-panel-bd">
        <p><span class="am-icon-bookmark"></span><font size="11">${contest.title }</font></p>
        <p> 参加人数： ${contest.joinNumber }</p>
        <p> 开始时间：<font id="begin_time"><fmt:formatDate value="${contest.startTime }" pattern="yyyy-MM-dd hh:mm"/></font></p>
        <p>距开始还有：<font id="left_time"></font></p>
        <p>比赛时常：${contest.timeLimit }小时</p>
		<c:choose>
		<c:when test="${contest.status==0}">
			<c:if test="${contest.userStatus==0}">
				<button  onclick="join_contest(${contest.contestId})"  style="width:200px;height:38px;background-color: #1cb0f6;border-radius: 20px;"><font color="white">报名参加比赛</font></button>
			</c:if>
			<c:if test="${contest.userStatus==1}">
				<button   onclick="exit_contest(${contest.contestId})"  style="width:200px;height:38px;background-color: #1cb0f6;border-radius: 20px;"><font color="white">退出比赛</font></button>
			</c:if>
		</c:when>
		<c:when test="${contest.status==2}">
			
				<button  style="width:200px;height:38px;background-color: #ade2fc;border-radius: 20px;"><font color="white">比赛已结束</font></button>
		</c:when>
		<c:otherwise>
			<c:if test="${contest.userStatus==0}">
				<button style="width:200px;height:38px;background-color: #ade2fc;border-radius: 20px;"><font color="white">您未参加本次比赛</font></button>
			</c:if>
			<c:if test="${contest.userStatus==1}">
				<button   onclick=""  style="width:200px;height:38px;background-color: #1cb0f6;border-radius: 20px;"><font color="white">进入比赛</font></button>
			</c:if>
		</c:otherwise>
	</c:choose>
      </div>
    </div>
    
    
  </div>
  <!-- sidebar end -->

  <!-- content start -->
		 <div class="admin-content" style="height: 620px;padding-left: 30px;padding-top: 30px">
				${contest.desc }
		</div>
  <!-- content end -->

</div>

<footer>
  <hr>
  <p class="am-padding-left">© 2018 hhoj版权所有者.</p>
</footer>


<!--[if (gte IE 9)|!(IE)]><!-->
<script src="${pageContext.request.contextPath }/assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/assets/js/amazeui.min.js"></script>
<!--<![endif]-->
<script src="${pageContext.request.contextPath }/assets/js/app.js"></script>
</body>
  <script type="text/javascript">
		function join_contest(contest_id){
			var url="/hhoj/contest/join/"+contest_id;
			$.ajax({  
                async:false,   //使用同步的Ajax请求  
                type: "POST",  
                url: url,  
                data: {},  
                dataType:'json',
                success: function(result){  
					if(result.success){
						window.location.href="/hhoj/contest/"+contest_id;
                	}
                }  
            }); 
		}
		
		function exit_contest(contest_id){
			var url="/hhoj/contest/exit/"+contest_id;
			$.ajax({  
                async:false,   //使用同步的Ajax请求  
                type: "POST",  
                url: url,  
                data: {},  
                dataType:'json',
                success: function(result){  
					if(result.success){
						window.location.href="/hhoj/contest/"+contest_id;
                	}
                }  
            }); 
		}
		left_time();
	function left_time(){
		var begin_time=$('#begin_time').html();
		
		var year=begin_time.substring(0,4);
		var month=begin_time.substring(5,7);
		var day=begin_time.substring(8,10);
		var hour=begin_time.substring(11,13);
		var minute=begin_time.substring(14,16);
		var second=0;
		leftTimer(year,month,day,hour,minute,second);
	}
	function leftTimer(year,month,day,hour,minute,second){ 
		  var leftTime = (new Date(year,month-1,day,hour,minute,second)) - (new Date()); //计算剩余的毫秒数 
		  if(leftTime<=0){
			  $('#left_time').html(0+"天" + 0+"小时" + 0+"分"+0+"秒");  
			  return ;
		  }
		  var days = parseInt(leftTime / 1000 / 60 / 60 / 24 , 10); //计算剩余的天数 
		  var hours = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时 
		  var minutes = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟 
		  var seconds = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数 
		  days = checkTime(days); 
		  hours = checkTime(hours); 
		  minutes = checkTime(minutes); 
		  seconds = checkTime(seconds); 
		  setInterval("leftTimer(2016,11,11,11,11,11)",1000); 
		  $('#left_time').html(days+"天" + hours+"小时" + minutes+"分"+seconds+"秒");  
		} 
		function checkTime(i){ //将0-9的数字前面加上0，例1变为01 
		  if(i<10) 
		  { 
		    i = "0" + i; 
		  } 
		  return i; 
		} 
		
</script>
</html>
