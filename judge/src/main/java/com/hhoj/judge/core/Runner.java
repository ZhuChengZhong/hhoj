package com.hhoj.judge.core;

import java.util.Map;

public  class Runner {
	
	static {
		System.loadLibrary("linux_judger");
	}
	
	public native Map<String, Object> runCommand(String commandLine,
			String systemUsername, String systemPassword, String inputFilePath,
			String outputFilePath, int timeLimit, int memoryLimit);
	
	/*public static void main(String[] args) {
		Runner runner=new Runner();
		String inputFilePath="/home/zhu/datas/data.in";
		String outputFilePath="/home/zhu/datas/data.out";
		int timeLimit=10000;
		int memoryLimit=10000000;
		String commandLine="g++ /home/zhu/program/test.cpp -o /home/zhu/program/test.out";
		Map<String,Object>map=runner.runCommand(commandLine, null, null, inputFilePath, outputFilePath, timeLimit, memoryLimit);
	}*/
	
	public static void compile() {
		Runner runner=new Runner();
		String inputFilePath="/home/zhu/datas/data.in";
		String outputFilePath="/home/zhu/datas/data.out";
		int timeLimit=10000;
		int memoryLimit=10000000;
		String commandLine="g++ /home/zhu/program/test.cpp -o /home/zhu/program/test.out";
		Map<String,Object>map=runner.runCommand(commandLine, null, null, inputFilePath, outputFilePath, timeLimit, memoryLimit);
	}
	public static void run() {
		Runner runner=new Runner();
		String inputFilePath="/home/zhu/datas/data.in";
		String outputFilePath="/home/zhu/datas/data.out";
		int timeLimit=10000;
		int memoryLimit=10000000;
		String commandLine="/home/zhu/program/test.out";
		Map<String,Object>map=runner.runCommand(commandLine, null, null, inputFilePath, outputFilePath, timeLimit, memoryLimit);
		System.out.println(map);
	}
	public static void main(String[] args) {
		//compile();
		run();
	}
}
