<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/datetime/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/datetime/build/jquery.datetimepicker.full.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/datetime/jquery.datetimepicker.css"/>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 
</head>
<body>
	<form action="">
		<input id="datetime"  type="text" />
	</form>
</body>
<script>
     $('#datetime').datetimepicker();
  </script>
</html>