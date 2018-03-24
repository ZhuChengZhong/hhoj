<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/assets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/assets/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/assets/ueditor/lang/zh-cn/zh-cn.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
	<div class="admin-content">

  <div class="am-cf am-padding">
    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">表单</strong> / <small>form</small></div>
  </div>

  <div class="am-tabs am-margin" data-am-tabs>
    <ul class="am-tabs-nav am-nav am-nav-tabs">
      <li class="am-active"><a href="#tab1">基本信息</a></li>
    </ul>

    <div class="am-tabs-bd">

      <div class="am-tab-panel am-fade am-in am-active" id="tab1">
        <form class="am-form">
         	<input type="hidden"  id="problem_type_id" value="${problemType.typeId}" />
			<div class="am-g am-margin-top-sm">
            <div class="am-u-sm-2 am-text-right">
              测试题类型
            </div>
            <div class="am-u-sm-10">
            <input type="text"  id="problem_type_name"  value="${problemType.typeName}"/>
            </div>
          </div>
             

        </form>
      </div>

    </div>
  </div>

  <div class="am-margin">
  	<div><font  color="red" id="error"></font></div>
    <button type="button" onclick="save_problem_type()" class="am-btn am-btn-primary am-btn-xs">提交保存</button>
    <button type="button" class="am-btn am-btn-primary am-btn-xs">放弃保存</button>
  </div>
</div>
<script type="text/javascript">

/*
 *		添加问题 
 *
 * 		获取表单信息验证后发送请求
 */
	function save_problem_type(){
	
		var problem_type_id=$('#problem_type_id').val();
		var problem_type_name=$('#problem_type_name').val();
		var url="/hhoj/manager/type/save";
		if(problem_type_name.trim()!=''){
			$.ajax({  
                async:false,   //使用同步的Ajax请求  
                type: "POST",  
                url: url,  
                data: {
                		typeId:problem_type_id,
                		typeName:problem_type_name
                	   },  
                success: function(result){  
                	window.location.href="/hhoj/manager/type/list";
                }  
            }); 
		}else{
			$('#error').html("信息未填写完整");
		}
		
	}
	
</script>
</body>
</html>