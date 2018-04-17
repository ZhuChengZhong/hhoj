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
    <li  role="presentation" class="active" ><a href="#problem_list"   aria-controls="problem_list" role="tab" data-toggle="tab">题目列表</a></li>
    <li  role="presentation" ><a href="#"  aria-controls="problem_submit"  role="tab" data-toggle="tab">运行结果</a></li>
    <li  role="presentation"><a href="#"  aria-controls="problem_ranking" role="tab" data-toggle="tab">排行榜</a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">

   
    <div role="tabpanel" class="tab-pane  active " id="problem_list">
       <div class="am-u-sm-12">
        <table class="am-table am-table-bd am-table-striped admin-content-table">
         	        <thead >
           <tr bgcolor="#7EC0EE" >
			<th>题号</th>
			<th>标题</th>
			<th>提交次数</th>
			<th>通过次数</th>
			<th>通过率</th>
			<th>状态</th>
		    </tr>
          </thead>
          <tbody>
          <c:forEach items="${contestProblemList}" var="contestProblem" varStatus="status">
          		<tr>
	       		<td >${status.index+1 }</td>
	       		<td>${contestProblem.problem.title }</td>
	       		<td><span class="am-badge am-badge-secondary">${contestProblem.submited }</span></td> 
	       		<td><span class="am-badge am-badge-success">+${contestProblem.accepted }</span></td> 
	            <td>${Math.round((contestProblem.accepted)*1000/(contestProblem.submited))/10 }%</td>
	            <td>X</td>
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

</script>
</html>