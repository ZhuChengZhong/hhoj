package com.hhoj.judge.listener;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 监听服务器发送过来的处理请求
 * @author zhu
 *
 */
public class SubmitListener extends Thread{
	private Logger logger=LoggerFactory.getLogger(SubmitListener.class);
	// 存放待处理的 提交 id
	private LinkedBlockingQueue<Integer> submitIdQueue;
	private Socket socket;
	public SubmitListener(LinkedBlockingQueue<Integer> submitIdQueue,String host,int port) {
		this.submitIdQueue=submitIdQueue;
		try {
			socket=new Socket(host, port);
			logger.debug("成功创建连接",socket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		InputStream inputStream = null;
		try {
			inputStream=socket.getInputStream();
			while(!Thread.currentThread().isInterrupted()) {
				int submitId=inputStream.read();
				if(submitId>0) {
					try {
						logger.info("从服务器接受到一个提交处理请求 submit id："+submitId);
						submitIdQueue.put(submitId);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(inputStream!=null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
