<%@ page language="java" contentType="image/jpeg"%>
<%@page import="com.hhoj.judger.util.RandomImageUtil,javax.imageio.ImageIO,java.awt.image.BufferedImage,java.util.*" %>
<% 
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
	BufferedImage image=RandomImageUtil.createRandImage(100, 40);
	String randomcode=RandomImageUtil.getRandomcode();
	session.setAttribute("randomcode", randomcode);
	ImageIO.write(image, "png", response.getOutputStream());
	out.clear();
	out = pageContext.pushBody();
%>