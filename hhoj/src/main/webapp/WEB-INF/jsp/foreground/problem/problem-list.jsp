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
    <li role="presentation" class="active"><a href="#problem_list" aria-controls="problem_list" role="tab" data-toggle="tab">题目列表</a></li>
   
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="problem_list">
        <!-- 练习场 begin -->
    <div class="am-g">
      <div class="am-u-sm-12">
        <table class="am-table am-table-striped am-table-hover table-main">   
          <thead >
           <tr bgcolor="#7EC0EE" >
           <th>通过</th>
			<th>题号</th>
			<th>标题</th>
			<th>提交次数</th>
			<th>通过次数</th>
			<th>通过率</th>
			
		    </tr>
          </thead>
          <tbody>
          <c:forEach items="${problemList}" var="problem">
          		<tr>
          		<td>
          			<c:if test="${problem.pass==1 }">
          				<img alt="" width="30" height="30" src="${pageContext.request.contextPath}/assets/images/pass.png">
          			</c:if>
          			
          		</td>
	       		<td >${problem.pid }</td>
	       		<td><a href="${pageContext.request.contextPath}/problem/detail/${problem.pid}">${problem.title }</a></td>
	       		<td><span class="am-badge am-badge-secondary">${problem.submited }</span></td> 
	       		<td><span class="am-badge am-badge-success">+${problem.accepted }</span></td> 
	            <td>${Math.round((problem.accepted)*1000/(problem.submited))/10 }%</td>
         	    </tr>
          </c:forEach>
		 </tbody>
        </table>
      </div>
    </div>
    <!-- 练习场end -->
    
  <div class="am-cf">
  
  
    <!-- 分页 -->
  共 x 条记录
  <div class="am-fr">
    <ul class="am-pagination">
     	${pagination}
    </ul>
  </div>
</div>
    
      <!-- 分页 -->
      <!-- 
        <nav aria-label="Page navigation" class="nav_center">
  <ul class="pagination">
    <li>
      <a href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <li><a href="#">1</a></li>
    <li><a href="#">2</a></li>
    <li><a href="#">3</a></li>
    <li><a href="#">4</a></li>
    <li><a href="#">5</a></li>
    <li><a href="#">6</a></li>
    <li><a href="#">7</a></li>
    <li><a href="#">8</a></li>
    <li><a href="#">9</a></li>
    <li><a href="#">10</a></li>
    <li>
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav> -->
      <!-- 分页end -->
    </div>
   
   
  </div>

</div>
    
  </div>
</body>
</html>