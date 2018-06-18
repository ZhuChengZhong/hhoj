package com.hhoj.judger.docker.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class Executor {
	private static Runtime runtime=Runtime.getRuntime();
	
	/**
	 * 执行命令
	 * @param cmdarray
	 * @return
	 */
	public static String exec(String cmd)   {
		try {
			String[]cmdarray= {"/bin/bash","-c",cmd};
			Process process=runtime.exec(cmdarray);
			process.waitFor();
			InputStream in=process.getInputStream();
			byte[]buffer=new byte[256];
			int len;
			StringBuilder res=new StringBuilder();
			while((len=in.read(buffer))>0) {
				res.append(new String(buffer,0,len));
			}
			in.close();
			return res.toString();
		} catch (Exception e) {
			throw new DockerException("命令执行失败",e);
		}
	}
	
	/**
	 * 执行命令 超时返回null
	 * @param cmdarray
	 * @param timeout 超时时间
	 * @return
	 */
	public static String exec(String cmd,int timeout)   {
		try {
			String[]cmdarray= {"/bin/bash","-c",cmd};
			Process process=runtime.exec(cmdarray);
			boolean wait=process.waitFor(timeout, TimeUnit.MILLISECONDS);
			if(!wait) {
				return null;
			}
			InputStream in=process.getInputStream();
			byte[]buffer=new byte[256];
			int len;
			StringBuilder res=new StringBuilder();
			while((len=in.read(buffer))>0) {
				res.append(new String(buffer,0,len));
			}
			in.close();
			return res.toString();
		} catch (Exception e) {
			throw new DockerException("命令执行失败",e);
		}
	}
}
