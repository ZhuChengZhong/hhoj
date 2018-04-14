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

  <ul class="nav nav-tabs" role="tablist">
    <li id="problem_list" role="presentation"  ><a href="#"  onclick="problem_list(${myCurrentContest.contestId})" aria-controls="problem_list" role="tab" data-toggle="tab">题目列表</a></li>
    <li id="problem_submit" role="presentation"  class="active"><a href="#" onclick="problem_submit(${myCurrentContest.contestId})" aria-controls="problem_submit"  role="tab" data-toggle="tab">运行结果</a></li>
    <li id="user_list" role="presentation"><a href="#" onclick="user_list(${myCurrentContest.contestId})" aria-controls="user_list" role="tab" data-toggle="tab">排行榜</a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">

   
    <div role="tabpanel" class="tab-pane  active " id="problem_submit">
       <div class="am-u-sm-12">
        <table class="am-table am-table-bd am-table-striped admin-content-table">
          <thead>
          <tr>
            <th>运行号</th>
            <th>用户名</th>
            <th>题目</th>
            <th>时间</th>
            <th>内存</th>
            <th>语言</th>
            <th>结果</th>
            <th>提交时间</th>
          </tr>
          </thead>
          <tbody>
         		<c:forEach items="${submitList}" var="submit">
         			 <tr>
			          <td>${submit.sid}</td>
			          <td>${submit.user.userName}</td>
			          <td><a href="${pageContext.request.contextPath}/problem/detail/${submit.problem.pid}">${submit.problem.title }</a></td> 
			         	<c:if test="${submit.judged==1}">
			         		 <td>${submit.useTime}</td>
					          <td>${submit.useMemary}</td>
					          <td>${submit.language.languageName}</td>
					          <c:choose>
					          		<c:when test="${'Accepted' eq submit.result}">
					          			<td><span class="am-badge am-badge-success">${submit.result}</span></td>
					          		</c:when>
					          		<c:otherwise>
					          			<td><span class="am-badge am-badge-danger">${submit.result}</span></td>
					          		</c:otherwise>
					          </c:choose>
			         	</c:if>
			         	<c:if test="${submit.judged==0}">
			         		 <td>--</td>
					          <td>--</td>
					          <td>--</td>
					          <td><span class="am-badge am-badge-warning">判题中...</span></td>
			         	</c:if>
			           <td> <fmt:formatDate value="${submit.submitTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
			            
			          </tr>
         		</c:forEach>
          </tbody>
        </table>
      </div>
    		  <!-- 分页 -->
			  <div class="am-fr">
			    <ul class="am-pagination">
			     	${pagination}
			    </ul>
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