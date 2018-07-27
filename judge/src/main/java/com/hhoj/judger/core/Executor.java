package com.hhoj.judger.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.hhoj.judger.constant.Config;
import com.hhoj.judger.docker.client.DockerClient;
import com.hhoj.judger.docker.pool.BlockingPool;
import com.hhoj.judger.docker.pool.ContainerPoolFactory;
import com.hhoj.judger.docker.pool.DockerOperator;
import com.hhoj.judger.entity.ExecResult;

public class Executor {

	/**
	 * 容器池
	 */
	//private BlockingPool<String> pool = ContainerPoolFactory.newLocalContainerPool();

	private BlockingPool<String> pool;
	private static Executor executor = new Executor();

	public static Executor instance() {
		return executor;
	}

	private Executor() { }

	public ExecResult exec(String inputFilePath, String userOutputFilePath, String cmd, String resultFilePath)
			throws Exception {
		String command = cmd + " < " + inputFilePath + " > " + userOutputFilePath;
		return exec(command, resultFilePath);
	}

	public ExecResult exec(String cmd, String resultFilePath) throws Exception {
		DockerClient client=DockerClient.instance();
		String containerId = pool.get();
		// 使用/usr/bin/time 记录程序运行时间 %e 使用内存 %M 退出状态 %x
		String command ="/usr/bin/time -f '%e %M %x' -o " + resultFilePath + " " + cmd;
		String res = client.exec(command, containerId, Config.MAX_WAIT_TIME);
		if(res==null) {
			return null;
		}
		pool.release(containerId);
		return reslove(resultFilePath);
	}

	/**
	 * 从文件中读取结果
	 * 
	 * @param resultFilePath
	 * @return
	 * @throws FileNotFoundException
	 */
	private static ExecResult reslove(String resultFilePath) throws FileNotFoundException {
		File file = new File(resultFilePath);
		Scanner scan = new Scanner(file);
		ExecResult result = new ExecResult();
		String s = scan.nextLine();
		// 当编译或运行的程序有问题时会生成一行 Command exited with non-zero status xxx
		if (s.startsWith("Command")) {
			s = scan.nextLine();
		}
		String[] res = s.split(" ");
		int useTime = (int) (Double.parseDouble(res[0]) * 1000);
		result.setUseTime(useTime);
		result.setUseMemary(Integer.parseInt(res[1]));
		result.setExitCode(Integer.parseInt(res[2]));
		scan.close();
		return result;
	}

	public void close() {
		if(pool!=null){
			pool.shutdown();
		}
	}
}
