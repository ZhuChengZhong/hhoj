package com.hhoj.judger.util;

import com.hhoj.judger.entity.PageBean;

public final class PageUtil {
	
	private PageUtil() { }
	
	public static String getPagination(String url,PageBean pageBean) {
		StringBuilder sb=new StringBuilder();
		int pageCount=pageBean.getPageCount();
		int page=pageBean.getPage();
		int left=page-2>0?page-2:1;
		int right=left+4>pageCount?pageCount:left+4;
		sb.append(" <li><a href='"+url+"/"+1+"'>«</a></li>\n");
		for(int i=left;i<=right;i++) {
			if(i==page) {
				sb.append("<li class='am-active'><a href='"+url+"/"+i+"'>"+i+"</a></li>\n");
			}else {
				sb.append("<li><a href='"+url+"/"+i+"'>"+i+"</a></li>\n");
			}
		}
		sb.append(" <li><a href='"+url+"/"+pageCount+"'>»</a></li>\n");
		return sb.toString();
	}
}
