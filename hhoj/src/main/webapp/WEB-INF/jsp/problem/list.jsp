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
              <button type="button" class="am-btn am-btn-default" onclick="pre_add()"><span class="am-icon-plus"></span> 新增</button>
              <button type="button" class="am-btn am-btn-default" ><span class="am-icon-save"></span> 保存</button>
              <button type="button" class="am-btn am-btn-default"><span class="am-icon-archive"></span> 审核</button>
              <button type="button" class="am-btn am-btn-default"><span class="am-icon-trash-o"></span> 删除</button>
            </div>

    
          </div>
        </div>
      </div>
      <div class="am-u-md-3 am-cf">
        <div class="am-fr">
          <div class="am-input-group am-input-group-sm">
            <input type="text" class="am-form-field">
                <span class="am-input-group-btn">
                  <button class="am-btn am-btn-default" type="button">搜索</button>
                </span>
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
                <th class="table-set">类型</th>
                <th class="table-set">创建时间</th>
                <th class="table-set">发布状态</th>
              </tr>
          </thead>
          <tbody>
 	<c:forEach items="${problemList}" var="problem">
 		     <tr>
              <td><input type="checkbox" /></td>
              <td>${problem.pid}</td>
              <td>${problem.title}</td>
              <td>${problem.source }</td>
              <td>${problem.timeLimit}</td>
              <td>${problem.memaryLimit}</td>
              <td>${problem.accepted}</td>
               <td>${problem.submited}</td>
                <td>${problem.type.typeName}</td>             
              <td> <fmt:formatDate value="${problem.createTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
               <td>${problem.publish}</td>  
              <td>
                <div class="am-btn-toolbar">
                  <div class="am-btn-group am-btn-group-xs">
                    <button type="button" class="am-btn am-btn-default"  onclick="updateUser(${problem.pid})"><span class="am-icon-save"></span> 编辑</button>
                     <button type="button" class="am-btn am-btn-default"  onclick="test_point_list(${problem.pid})"><span class="am-icon-save"></span> 测试用例</button>
                    <button class="am-btn am-btn-default am-btn-xs" onclick="removeUser(${problem.pid})"><span class="am-icon-trash-o"></span>删除</button>
                  </div>
                </div>
              </td>
            </tr>
 	</c:forEach>
                  
       </tbody>
        </table>
          <div class="am-cf">
  共 15 条记录
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
	function removeUser(pid) {
		if(confirm("你确定要删除该题目吗？")){
			var url="/hhoj/manager/problem/remove/"+pid;
			/* $.post(url, {}, function(result){
				
				//document.location.reload();
			}); */
			 $.ajax({  
                async:false,   //使用同步的Ajax请求  
                type: "POST",  
                url: url,  
                data: "",  
                success: function(result){  
                	document.location.reload();
                }  
            }); 
		}
	}
	
	

function pre_add(){
	//alert("asd");
	window.location.href="/hhoj/manager/problem/add";
}
 
function updateUser(pid){
	window.location.href="/hhoj/manager/problem/update/"+pid;
}
function test_point_list(pid){
	window.location.href="/hhoj/manager/testpoint/list/"+pid;
}
</script>
</html>