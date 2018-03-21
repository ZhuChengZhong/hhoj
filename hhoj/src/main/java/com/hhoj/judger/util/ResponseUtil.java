package com.hhoj.judger.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseUtil {
	
	private static Logger logger=LoggerFactory.getLogger(ResponseUtil.class);
	public static void write(Object o,HttpServletResponse response) {
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(o.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("响应失败",e);
		}
		
	}
}
