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
      <li class="am-active"><a href="#tab1">样例</a></li>
    </ul>

    <div class="am-tabs-bd">

      <div class="am-tab-panel am-fade am-in am-active" id="tab1">
        <form class="am-form">
         	<input type="hidden"  id="test_point_id" value="${testPoint.pointId}" />
			<div class="am-g am-margin-top-sm">
            <div class="am-u-sm-2 am-text-right">
              样例输入
            </div>
            <div class="am-u-sm-10">
              <textarea rows="10" cols="1" id="test_point_input">${testPoint.input}</textarea>
            </div>
          </div>
               <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-2 am-text-right">
              样例输出
            </div>
            <div class="am-u-sm-10">
              <textarea rows="10" cols="1" id="test_point_output">${testPoint.output}</textarea>
            </div>
          </div>
        

        </form>
      </div>

    </div>
  </div>

  <div class="am-margin">
  	<div><font  color="red" id="error"></font></div>
    <button type="button" onclick="add_test_point(${pid})" class="am-btn am-btn-primary am-btn-xs">提交保存</button>
    <button type="button" class="am-btn am-btn-primary am-btn-xs">放弃保存</button>
  </div>
</div>
<script type="text/javascript">

/*
 *		添加问题 
 *
 * 		获取表单信息验证后发送请求
 */
	function add_test_point(pid){
	
		var test_point_input=$('#test_point_input').val();
		var test_point_output=$('#test_point_output').val();
		var test_point_id=$('#test_point_id').val();
		
		var url="/hhoj/testpoint/save";
		if(test_point_input!=''&&test_point_output!=''){
			$.ajax({  
                async:false,   //使用同步的Ajax请求  
                type: "POST",  
                url: url,  
                data: {
                	  pointId:test_point_id,
                	   pid:pid,
                	   input:test_point_input,
                	   output:test_point_output
                	   },  
                success: function(result){  
                	window.location.href="/hhoj/testpoint/list/"+pid;
                }  
            }); 
		}else{
			$('#error').html("信息未填写完整");
		}
		
	}
	
</script>
</body>
</html>