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
      <li><a href="#tab2">详细描述</a></li>
      <li><a href="#tab3">SEO 选项</a></li>
    </ul>

    <div class="am-tabs-bd">
      <div class="am-tab-panel am-fade am-in am-active" id="tab1">
        <div class="am-g am-margin-top">
          <div class="am-u-sm-2 am-text-right">所属类别</div>
          <div class="am-u-sm-10">
            <select id="problem_type">
              <option value="1">...</option>
              <option value="2">动态规划</option>
              <option value="3">深搜</option>
              <option value="4">排序</option>
              <option value="5">其他</option>
            </select>
          </div>
        </div>

        <div class="am-g am-margin-top">
          <div class="am-u-sm-2 am-text-right">状态</div>
          <div class="am-u-sm-10">
            <div class="am-btn-group" data-am-button>
            
              <label class="am-btn am-btn-default am-btn-xs">
                <input type="radio" name="options" id="option2"> 未发布
              </label>
             
            </div>
          </div>
        </div>

  		<input type="hidden"  id="problem_pid"  value="${problem.pid }" />

       <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-2 am-text-right" >
              时间限制(ms)
            </div>
            <div class="am-u-sm-4 am-u-end">
              <input type="text" class="am-input-sm" id="problem_time_limit" value="${problem.timeLimit}">
            </div>
          </div>

          <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-2 am-text-right">
              内存限制(kb)
            </div>
            <div class="am-u-sm-4 am-u-end">
              <input type="text" class="am-input-sm" id="problem_memary_limit" value="${problem.memaryLimit}">
            </div>
          </div>

      </div>

      <div class="am-tab-panel am-fade" id="tab2">
        <form class="am-form">
          <div class="am-g am-margin-top">
            <div class="am-u-sm-2 am-text-right">
              题目标题
            </div>
            <div class="am-u-sm-4">
              <input type="text" class="am-input-sm" id="problem_title" value="${problem.title}">
            </div>
            <div class="am-u-sm-6"></div>
          </div>

      

          <div class="am-g am-margin-top">
            <div class="am-u-sm-2 am-text-right">
              来源
            </div>
            <div class="am-u-sm-4">
              <input type="text" class="am-input-sm" id="problem_source" value="${problem.source}">
            </div>
            <div class="am-u-sm-6"></div>
          </div>

            <div class="am-g am-margin-top">
            <div class="am-u-sm-2 am-text-right">
              提示
            </div>
            <div class="am-u-sm-4">
              <input type="text" class="am-input-sm" id="problem_hint" value="${problem.hint}">
            </div>
            <div class="am-u-sm-6"></div>
          </div>

          <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-2 am-text-right">
              内容描述
            </div>
            <div class="am-u-sm-10">
                <script id="editor" type="text/plain" name="problem_desc" style="width:800px;height:300px;">
					${problem.desc}
				</script>
            </div>
          </div>

        </form>
      </div>

      <div class="am-tab-panel am-fade" id="tab3">
        <form class="am-form">
         
			<div class="am-g am-margin-top-sm">
            <div class="am-u-sm-2 am-text-right">
              样例输入
            </div>
            <div class="am-u-sm-10">
              <script id="input_editor" type="text/plain" name="problem_input" style="width:600px;height:200px;">
					${problem.inputExample}
				</script>
            </div>
          </div>
               <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-2 am-text-right">
              样例输出
            </div>
            <div class="am-u-sm-10">
               <script id="output_editor" type="text/plain" name="problem_output" style="width:600px;height:200px;">
					${problem.outputExample}
				</script>
            </div>
          </div>
        

        </form>
      </div>

    </div>
  </div>

  <div class="am-margin">
  	<div><font  color="red" id="error"></font></div>
    <button type="button" onclick="add_problem()" class="am-btn am-btn-primary am-btn-xs">提交保存</button>
    <button type="button" class="am-btn am-btn-primary am-btn-xs">放弃保存</button>
  </div>
</div>
<script type="text/javascript">

/*
 *		添加问题 
 *
 * 		获取表单信息验证后发送请求
 */
	function add_problem(){
		var problem_pid=$('#problem_pid').val();
		var problem_type=$('#problem_type').val();
		var problem_title=$('#problem_title').val();
		var problem_hint=$('#problem_hint').val();
		var problem_desc=UE.getEditor('editor').getContent();
		var problem_source=$('#problem_source').val();
		var problem_time_limit=$('#problem_time_limit').val();
		var problem_memary_limit=$('#problem_memary_limit').val();
		var problem_input=UE.getEditor('input_editor').getContent();
		var problem_output=UE.getEditor('output_editor').getContent();
		if(problem_title.trim()!=''&&problem_hint.trim()!=''&&
				problem_desc.trim()!=''&&problem_source.trim()!=''&&
				problem_time_limit.trim()!=''&&problem_memary_limit.trim()!=''&&
				problem_input.trim()!=''&&problem_output.trim()!=''){
			var url="/hhoj/manager/problem/save";
			$.ajax({  
                async:false,   //使用同步的Ajax请求  
                type: "POST",  
                url: url,  
                data: {
                	   pid:problem_pid,
                	   title:problem_title,
                	   hint:problem_hint,
                	   desc:problem_desc,
                	   source:problem_source,
                	   timeLimit:problem_time_limit,
                	   memaryLimit:problem_memary_limit,
                	   inputExample:problem_input,
                	   outputExample:problem_output,
                	   typeId:problem_type
                	   },  
                success: function(result){  
                	window.location.href="/hhoj/manager/problem/list/1";
                }  
            }); 
		}else{
			$('#error').html("信息未填写完整");
		}
		
	}
	
	  var ue = UE.getEditor('editor',{toolbars: [[
          'fullscreen', 'source', '|', 
          'undo', 'redo', '|',
          'bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|', 
          'forecolor', 'backcolor', '|', 
          'insertorderedlist', 'insertunorderedlist', 'cleardoc', '|',
          'paragraph', 'fontfamily', 'fontsize', '|',
          'indent', '|',
          'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 
          'link', '|', 
          'simpleupload', 'emotion', 'insertvideo', '|',
          'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts'
      ]]}); 
	  
	  var ue = UE.getEditor('input_editor',{toolbars: [[
      ]]}); 
	  var ue = UE.getEditor('output_editor',{toolbars: [[
	      ]]}); 
</script>
</body>
</html>