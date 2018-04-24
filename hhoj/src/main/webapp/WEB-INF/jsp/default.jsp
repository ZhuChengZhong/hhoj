<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
          <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	  <div class="admin-content">

    <div class="am-cf am-padding">
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">练习场</strong> / <small>黄淮学院ACM－HHOJ</small></div>
    
    </div>
    
    <ul class="am-avg-sm-1 am-avg-md-4 am-margin am-padding am-text-center admin-content-list ">
      <li><a href="#" class="am-text-success"><span class="am-icon-btn am-icon-file-text"></span><br/>题库总数<br/>${globalProblemCount }</a></li>
      <li><a href="#" class="am-text-warning"><span class="am-icon-btn am-icon-briefcase"></span><br/>AC总数<br/>${globalAcceptedCount }</a></li>
      <li><a href="#" class="am-text-danger"><span class="am-icon-btn am-icon-recycle"></span><br/>昨日访问<br/>80082</a></li>
      <li><a href="#" class="am-text-secondary"><span class="am-icon-btn am-icon-user-md"></span><br/>用户总数<br/>${globalUserCount }</a></li>
    </ul>
 
 <div role="tabpanel" class="tab-pane" id="problem_result">
       <div class="am-u-sm-12">
        <table class="am-table am-table-bd am-table-striped admin-content-table">
          <thead>
          <tr>
            <th>ID</th><th>用户名</th><th>个性签名</th><th>共通过题数</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${globalACUsers}" var="user">
            <tr>
            <td>${user.uid }</td>
            <td>${user.userName }</td>
            <td>${user.sign}</td> 
            <td><span class="am-badge am-badge-success">+${user.solved}</span></td>
          </tr>
          </c:forEach>
        
          </tbody>
        </table>
      </div>

   
   
  </div>
</body>
</html>