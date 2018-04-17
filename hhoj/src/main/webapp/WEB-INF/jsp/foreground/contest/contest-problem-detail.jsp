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
    <li id="problem_detail" role="presentation"  class="active"><a href="#problem_message" onclick="problem_detail(${problem.pid})"  aria-controls="problem_message" role="tab" data-toggle="tab">题目信息</a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">

    <div role="tabpanel" class="tab-pane active problem-position" id="problem_message">
            <p class="problem_name"><font class="font-title">题目</font></p>
           <p class="problem_name"><font class="font-style">时间：${contestProblem.problem.timeLimit} | 内存：${contestProblem.problem.memaryLimit}</font></p>
         <div  class="am-u-sm-12">
         <dl>   
          <dt >描述：</dt>
          <dd>${contestProblem.problem.desc }</dd>
          <dt class="">样例输入：</dt>
          <dd>
            <pre>&lt;p&gt;${contestProblem.problem.inputExample }&lt;/p&gt;</pre>
          </dd>
          <dt class="">样例输出：</dt>
          <dd>
           <pre>&lt;p&gt;${contestProblem.problem.outputExample }&lt;/p&gt;</pre>
          </dd>
          <dt class="">来源：</dt>
          <dd>${contestProblem.problem.source}</dd>
        </dl>
       </div>
              <!-- 选择语言 -->  
              <div  class="problem_language">   
                   <select class="form-control" name="problem_language"  id="problem_language"> 
                        <option value="1">JAVA</option>
                        <option value="2">C/C++</option>

                 </select>
                 
              </div>
     <textarea class="form-control" cols="20" rows="15" id="problem_code"></textarea>
     <button class="btn btn-default" onclick="create_submit(${contestProblem.problem.pid})">提交</button><div><font color="red" id="error"></font></div>
 
   </div>

 
  </div>

</div>
    
  </div>
</body>
<script type="text/javascript">
function submit_list(pid){
	window.location.href="/hhoj/submit/problem/"+pid+"/list/1";

}

function problem_detail(pid){
	window.location.href="/hhoj/problem/detail/"+pid;
}
function create_submit(pid){
	var problem_language=$('#problem_language').val();
	var problem_code=$('#problem_code').val();
	if(problem_language.trim()!=''&&problem_code.trim()!=''){
		var url="/hhoj/submit/problem/"+pid+"/add";
		var contestId=${contest.contestId};
		$.ajax({  
            async:false,   //使用同步的Ajax请求  
            type: "POST",  
            url: url,
            dataType:"json",
            data: {
            	   languageId:problem_language,
            	   code:problem_code,
            	   contestId:contestId
            	   },  
            success: function(result){
            	if(result.success){
            		window.location.href="/hhoj/contest/"+contestId+"/submit/list/1";
            	}else{
            	  window.location.href="/hhoj/user/login";
            	}
            }  
        }); 
	}else{
		$('#error').html("信息未填写完整");
	}
}
</script>
</html>