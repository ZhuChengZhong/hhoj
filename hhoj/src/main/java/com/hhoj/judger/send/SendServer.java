package com.hhoj.judger.send;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.controller.SubmitController;
import com.hhoj.judger.util.PropertiesUtil;

public class SendServer extends Thread {

	private static Logger logger = LoggerFactory.getLogger(SubmitController.class);

	private LinkedBlockingQueue<Integer> submitIdQueue;
	private ServerSocket serverSocket;

	public SendServer(LinkedBlockingQueue<Integer> submitIdQueue) throws IOException {
		this.submitIdQueue = submitIdQueue;
		String port = PropertiesUtil.getParam("hhoj.port");
		serverSocket = new ServerSocket(Integer.parseInt(port));
	}

	class SendTask implements Runnable {
		private Socket socket;

		public SendTask(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			OutputStream out = null;
			try {
				out = socket.getOutputStream();
			} catch (IOException e) {
				logger.error("get output failed", e);
			}
			try {
				while (!Thread.currentThread().isInterrupted()) {
					try {
						int submitId = submitIdQueue.take();
						out.write(submitId);
						logger.info("send submit id to judger  submit id:" + submitId);

					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			} catch (IOException e) {
				logger.error("send failed", e);
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("close failed", e);
				}
			}

		}
	}

	@Override
	public void run() {
		logger.info("通讯服务器启动成功");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				logger.info("成功连接+++connection success with the judger");
				new Thread(new SendTask(socket)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
