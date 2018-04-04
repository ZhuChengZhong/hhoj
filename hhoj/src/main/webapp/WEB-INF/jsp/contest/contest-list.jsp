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
                <th class="table-type">参加人数</th>
                <th class="table-author">开始时间</th>
                <th class="table-date">结束时间</th>
                <th class="table-set">报名时间</th>
                <th class="table-set">报名截止</th>
                <th class="table-set">比赛创建人</th>
                <th class="table-set">比赛状态</th>
              </tr>
          </thead>
          <tbody>
 	<c:forEach items="${contestList}" var="contest">
 		     <tr>
              <td><input type="checkbox" /></td>
              <td>${contest.contestId}</td>
              <td>${contest.title}</td>
              <td>${contest.joinNumber}</td>
              <td><fmt:formatDate value="${contest.startTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
              <td><fmt:formatDate value="${contest.endTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
               <td><fmt:formatDate value="${contest.startJoinTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                <td><fmt:formatDate value="${contest.endJoinTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>             
               <td>${contest.initiator.userName}</td>  
                <td>${contest.status}</td>  
              <td>
                <div class="am-btn-toolbar">
                  <div class="am-btn-group am-btn-group-xs">
                    <button type="button" class="am-btn am-btn-default"  onclick="updateUser(${contest.contestId})"><span class="am-icon-save"></span> 编辑</button>
                     <button type="button" class="am-btn am-btn-default"  onclick="test_point_list(${contest.contestId})"><span class="am-icon-save"></span> 测试用例</button>
                    <button class="am-btn am-btn-default am-btn-xs" onclick="removeUser(${contest.contestId})"><span class="am-icon-trash-o"></span>删除</button>
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
	window.location.href="/hhoj/manager/contest/add";
}
 
function update_contest(pid){
	window.location.href="/hhoj/manager/contest/update/"+pid;
}
function contest_list(pid){
	window.location.href="/hhoj/manager/contest/list/"+pid;
}
</script>
</html>