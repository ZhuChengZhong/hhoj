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
              <button type="button" class="am-btn am-btn-default" onclick="pre_add_type()"><span class="am-icon-plus"></span> 新增</button>
              <button type="button" class="am-btn am-btn-default" ><span class="am-icon-trash-o"></span> 删除</button>
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
                <th class="table-title">测试题类型</th>
              </tr>
          </thead>
          <tbody>
 	<c:forEach items="${typeList}" var="type">
 		     <tr>
              <td><input type="checkbox" /></td>
              <td>${type.typeId}</td>
              <td>${type.typeName}</td>
              <td>
                <div class="am-btn-toolbar">
                  <div class="am-btn-group am-btn-group-xs">
                      <button type="button" class="am-btn am-btn-default" onclick="update_problem_type(${type.typeId})"><span class="am-icon-plus"></span> 修改</button>
              			<button type="button" class="am-btn am-btn-default"  onclick="remove_problem_type(${type.typeId})"><span class="am-icon-trash-o"></span> 删除</button>
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
	function remove_problem_type(typeId) {
		if(confirm("你确定要删除该类型吗？")){
			var url="/hhoj/manager/type/remove/"+typeId;
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
	
	
	function update_problem_type(typeId){
		if(typeId!=''){
			window.location.href="/hhoj/manager/type/update/"+typeId;
		}
		
	}
	 
	function pre_add_type(){
			window.location.href="/hhoj/manager/type/add";
	}
</script>
</html>