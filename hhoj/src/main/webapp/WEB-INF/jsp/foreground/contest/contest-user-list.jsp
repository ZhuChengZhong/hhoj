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
	 <!-- content start -->
  <div class="admin-content">
    <div class="am-cf am-padding">
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">练习场</strong> / <small>problem</small></div>
    </div>

          <div>

  <!-- Nav tabs -->
  <ul class="nav nav-tabs" role="tablist">
    <li id="problem_list" role="presentation"  ><a href="#"  onclick="problem_list(${myCurrentContest.contestId})" aria-controls="problem_list" role="tab" data-toggle="tab">题目列表</a></li>
    <li id="problem_submit" role="presentation" ><a href="#" onclick="submit_list(${myCurrentContest.contestId})" aria-controls="submit_list"  role="tab" data-toggle="tab">运行结果</a></li>
    <li id="user_list" role="presentation" class="active"><a href="#" onclick="user_list(${myCurrentContest.contestId})" aria-controls="user_list" role="tab" data-toggle="tab">排行榜</a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">

   
    <div role="tabpanel" class="tab-pane  active " id="user_list">
       <div class="am-u-sm-12">
        <table class="am-table am-table-bd am-table-striped admin-content-table">
         	        <thead >
           <tr bgcolor="#7EC0EE" >
			<th>排名</th>
			<th>用户</th>
			<th>通过题数</th>
			<th>总耗时</th>
		    </tr>
          </thead>
          <tbody>
          <c:forEach items="${contestUserList}" var="contestUser" varStatus="status">
          		<tr>
	       		<td >${status.index+1 }</td>
	       		<td><span class="am-badge am-badge-secondary">${contestUser.user.userName }</span></td> 
	       		<td><span class="am-badge am-badge-success">+${contestUser.accepted }</span></td> 
	            <td>${contestUser.useTotalTime }</td>
         	    </tr>
          </c:forEach>
		 </tbody>
        </table>
      </div>
	</div>
    </div>
    <div role="tabpanel" class="tab-pane" id="problem_speak">...</div>
  </div>

    
  </div>
</body>
<script type="text/javascript">
function submit_list(contestId){
	window.location.href="/hhoj/contest/"+contestId+"/submit/list/1";

}
function problem_list(contestId){
	window.location.href="/hhoj/contest/"+contestId+"/problem/list";
}
function user_list(contestId){
	window.location.href="/hhoj/contest/"+contestId+"/user/list/1";
}
</script>
</html>