package com.hhoj.judger.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {
	private LinkedBlockingQueue<Integer> submitIdQueue;
	private ServerSocket serverSocket;

	public Server(LinkedBlockingQueue<Integer> submitIdQueue) throws IOException {
		this.submitIdQueue = submitIdQueue;
		serverSocket = new ServerSocket(8888);
	}

	class SendTask implements Runnable {
		private Socket socket;

		public SendTask(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			OutputStream out;
			try {
				out = socket.getOutputStream();
			} catch (IOException e) {
				throw new RuntimeException("获取输出流失败");
			}
			try {
				while (!Thread.currentThread().isInterrupted()) {
					try {
						int submitId = submitIdQueue.take();

						System.out.println("send:" + submitId);
						out.write(submitId);

					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			} catch (IOException e) {
				throw new RuntimeException("发送异常");
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					throw new RuntimeException("关闭输出流异常");
				}
			}

		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("有判题机加入");
				new Thread(new SendTask(socket)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		LinkedBlockingQueue<Integer> submitIdQueue = new LinkedBlockingQueue<Integer>();
		Server server = new Server(submitIdQueue);
		server.start();
		for (int i = 0; i < 10; i++) {
			TimeUnit.SECONDS.sleep(1);
			submitIdQueue.put(i);
		}
	}
}
