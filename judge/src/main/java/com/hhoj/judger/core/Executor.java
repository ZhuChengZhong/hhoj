package com.hhoj.judger.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.hhoj.judger.constant.Config;
import com.hhoj.judger.entity.ExecResult;
import com.hhoj.judger.util.DockerOperator;

public class Executor {
	
	public  ExecResult exec(String inputFilePath,String userOutputFilePath,String cmd,String resultFilePath) throws Exception {
		String command=cmd+" < "+inputFilePath+" > "+userOutputFilePath;
		return exec(command, resultFilePath);
	}
	public  ExecResult exec(String cmd,String resultFilePath) throws Exception {
		Map<String,String>volumes=new HashMap<>();
		volumes.put(Config.WORKSPACE, Config.WORKSPACE);
		DockerOperator docker=DockerOperator.instance();
		String containerId=docker.createContainer("judger", volumes);
		docker.startContainer(containerId);
		// 使用/usr/bin/time 记录程序运行时间 %e  使用内存 %M 退出状态 %x
		String []cmds= {"sh","-c","/usr/bin/time -f '%e %M %x' -o "+resultFilePath+" "+cmd};
		String execId=docker.execCmd(containerId, cmds);
		Integer status=docker.waitCmdFinish(execId,Config.MAX_WAIT_TIME);
		if(status==null) {
			return null;
		}
		docker.killContainer(containerId);
		return reslove(resultFilePath);
	}
	
	private static ExecResult reslove(String resultFilePath) throws FileNotFoundException {
		File file=new File(resultFilePath);
		Scanner scan=new Scanner(file);
		ExecResult result=new ExecResult();
		String s=scan.nextLine();
		//当编译或运行的程序有问题时会生成一行 Command exited with non-zero status xxx
		if(s.startsWith("Command")) {
			s=scan.nextLine();
		}
		String[]res=s.split(" ");
		int useTime=(int)(Double.parseDouble(res[0])*1000);
		result.setUseTime(useTime);
		result.setUseMemary(Integer.parseInt(res[1]));
		result.setExitCode(Integer.parseInt(res[2]));
		scan.close();
		return result;
	}
	public static void main(String[] args) throws Exception {
		ExecResult result=reslove("/home/zhu/judger/result/9/res.txt");
		System.out.println(result);
	}
}
