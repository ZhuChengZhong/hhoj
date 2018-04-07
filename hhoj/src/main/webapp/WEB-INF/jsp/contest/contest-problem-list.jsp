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
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">表格</strong> / <small>Table</small></div>
    </div>

    <div class="am-g">
      <div class="am-u-md-6 am-cf">
        <div class="am-fl am-cf">
          <div class="am-btn-toolbar am-fl">
            <div class="am-btn-group am-btn-group-xs">
              
              题目编号：<input type="text" id="problem_id" /> 
               <button type="button"  onclick="add_contest_problem(${contestId})"> 新增</button>
            </div>
		<div><font  color="red" id="error"></font></div>
    
          </div>
        </div>
      </div>
     
    </div>

    <div class="am-g">
      <div class="am-u-sm-12">
        <form class="am-form">
          <table class="am-table am-table-striped am-table-hover table-main">
           <thead>
              <tr>
                <th class="table-check"><input type="checkbox" /></th>
                <th class="table-id">编号</th>
                <th class="table-title">标题</th>
                <th class="table-type">来源</th>
                <th class="table-author">时间限制</th>
                <th class="table-date">内存限制</th>
                <th class="table-set">accepted</th>
                <th class="table-set">submited</th>
                <th class="table-set">创建时间</th>
                <th class="table-set">发布状态</th>
              </tr>
          </thead>
          <tbody>
 	<c:forEach items="${contestProblemList}" var="contestProblem">
 		     <tr>
              <td><input type="checkbox" /></td>
              <td>${contestProblem.cpId}</td>
              <td>${contestProblem.problem.title}</td>
              <td>${contestProblem.problem.source }</td>
              <td>${contestProblem.problem.timeLimit}</td>
              <td>${contestProblem.problem.memaryLimit}</td>
              <td>${contestProblem.accepted}</td>
               <td>${contestProblem.submited}</td>
              <td> <fmt:formatDate value="${contestProblem.problem.createTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
               <td>${contestProblem.problem.publish}</td>  
              <td>
                <div class="am-btn-toolbar">
                  <div class="am-btn-group am-btn-group-xs">
                    <button class="am-btn am-btn-default am-btn-xs" onclick="remove_contest_problem(${contestProblem.cpId})"><span class="am-icon-trash-o"></span>删除</button>
                  </div>
                </div>
              </td>
            </tr>
 	</c:forEach>
                  
       </tbody>
        </table>
          <div class="am-cf">
  共 ${count} 条记录
  <div class="am-fr">
    <ul class="am-pagination">
     	${pagination}
    </ul>
  </div>
</div>
          <hr />
          <p>注：.....</p>
        </form>
      </div>

    </div>
  </div>
  <!-- content end -->
</body>

<script type="text/javascript">
	function remove_contest_problem(contest_problem_id) {
		if(confirm("你确定要删除该题目吗？")){
			var url="/hhoj/manager/contest/problem/remove/"+contest_problem_id;
			 $.ajax({  
                async:false,   
                type: "POST",  
                url: url,  
                data: "",  
                success: function(result){  
                	document.location.reload();
                }  
            }); 
		}
	}
	
	

function add_contest_problem(contestId){
	var problem_id=$('#problem_id').val().trim();
	if(problem_id!=''&&contestId!=null&&contestId!=''){
		var url="/hhoj/manager/contest/problem/add";
		 $.ajax({  
             async:false,   //使用同步的Ajax请求  
             type: "POST",  
             url: url,  
             dataType:'json',
             data: {
            	 pid:problem_id,
            	 contestId:contestId
            	 },  
             success: function(result){  
            	 if(result.success){
            		 window.location.href="/hhoj/manager/contest/"+contestId+"/problem/list";
            	 }else{
            		 $('#error').html(result.message); 
            	 }
             }  
         }); 
	}else{
		$('#error').html('请填写要添加的题目编号');
	}
	
}
 
function updateUser(pid){
	window.location.href="/hhoj/manager/problem/update/"+pid;
}
function test_point_list(pid){
	window.location.href="/hhoj/manager/testpoint/list/"+pid;
}
</script>
</html>