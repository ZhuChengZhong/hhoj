package com.hhoj.judger.docker.client;

import java.util.ArrayList;
import java.util.List;

import com.hhoj.judger.docker.pool.LocalContainerFactory;
import com.hhoj.judger.docker.pool.LocalContainerValidator;


/**
 * docker客户端
 * @author zhu
 *
 */
public class DockerClient {
	
	private DockerClient() { }
	
	private static DockerClient client=new DockerClient();
	
	public static DockerClient instance() {
		return client;
	}
	
	
	/**
	 * 创建docker容器
	 * @param config
	 * @return 返回容器id
	 */
	public String createContainer(ContainerConfig config) {
		String cmd=generalConfig(config);
		String containerID=Executor.exec(cmd);
		if(containerID==null||"".equals(containerID)) {
			throw new DockerException("创建容器失败!");
		}
		return containerID.substring(0, 11);
	}
	
	
	
	
	/**
	 * 停止运行的容器
	 * @param containerId
	 */
	public boolean stopContainer(String containerId) {
		if(containerId==null||"".equals(containerId)) {
			return false;
		}
		String cmd="docker stop "+containerId;
		String res=Executor.exec(cmd);
		if(res!=null||!"".equals(res)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 删除容器
	 * @param containerId
	 * @return
	 */
	public boolean rmContainer(String containerId) {
		if(containerId==null||"".equals(containerId)) {
			return false;
		}
		String cmd="docker rm "+containerId;
		String res=Executor.exec(cmd);
		if(res!=null||!"".equals(res)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 关闭并删除正在运行的容器
	 * @param containerId
	 * @return
	 */
	public boolean killContainer(String containerId) {
		if(containerId==null||"".equals(containerId)) {
			return false;
		}
		String cmd="docker kill "+containerId;
		String res=Executor.exec(cmd);
		if(res!=null||!"".equals(res)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 在给定的容器内执行指定的命令
	 * @param cmd
	 * @param containerId
	 * @return
	 */
	public String exec(String cmd,String containerId) {
		String command="docker exec "+containerId+" sh -c \""+cmd+"\"";
		String res=Executor.exec(command);
		return res;
	}
	
	/**
	 * 在给定的容器内执行指定的命令 超时返回null
	 * @param cmd
	 * @param containerId
	 * @param timeout
	 * @return
	 */
	public String exec(String cmd,String containerId,int timeout) {
		String command="docker exec "+containerId+" sh -c \""+cmd+"\"";
		String res=Executor.exec(command, timeout);
		return res;
	}
	
	
	/**
	 * 检查容器是否处于运行状态
	 * @param containerId
	 * @return
	 */
	public boolean checkContainer(String containerId) {
		String command="docker inspect "+containerId+" | grep  -e  Running | sed 's/.*: \\(.*\\),/\\1/'";
		String res=Executor.exec(command);
		if(res==null||"".equals(res)) {
			return false;
		}
		if(res.equals("true\n")) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 检查容器是否处于运行状态
	 * @param containerId
	 * @return
	 */
	public boolean isRunning(String containerId) {
		String command="docker ps | grep "+containerId;
		String res=Executor.exec(command);
		if(res==null||"".equals(res)) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 根据配置生成命令
	 * @param config
	 * @return
	 */
	private String generalConfig(ContainerConfig config){
		StringBuilder sb=new StringBuilder();
		sb.append(" docker run ");
		if(config.isDeamon()) {
			sb.append(" -d ");
		}
		if(config.getVolumes()!=null) {
			List<ContainerConfig.Volume>volumes=config.getVolumes();
			for(int i=0;i<volumes.size();i++) {
				ContainerConfig.Volume volume=volumes.get(i);
				sb.append(" -v ");
				sb.append(" "+volume.from()+":"+volume.to());
			}
		}
		if(config.getImage()!=null) {
			sb.append(" "+config.getImage()+" ");
		}
		if(config.getCmds()!=null) {
			for(String cmd:config.getCmds()) {
				sb.append(" "+cmd+" ");
			}
		}
		return sb.toString();
	}
	
/*	public static void main(String[] args) {
		DockerClient dockerClient=new DockerClient();
		ContainerConfig.Volume v=new ContainerConfig.Volume();
		v.from("/home/zhu");
		v.to("/");
		String[]cmds= {"sh","-c","'while :; do sleep 1; done'"};
		ContainerConfig config=ContainerConfig.builder()
				.deamon(true)
				.image("judger")
				.workspace("/home/zhu")
				.addVolume(v)
				.cmd(cmds)
				.build();
		String id=dockerClient.createContainer(config);
		System.out.println(id);
		String res=dockerClient.exec("ls", id);
		System.out.println(res);
		boolean b=dockerClient.killContainer(id);
		System.out.println(b);
		boolean res=DockerClient.instance().checkContainer("6es");
		System.out.println(res);
		LocalContainerValidator validator=new LocalContainerValidator();
		long start,cost;
		
		start=System.currentTimeMillis();
		String re=DockerClient.instance().exec("ls", "c9a0a8c94ec1");
		System.out.println(re);
		cost=System.currentTimeMillis()-start;
		System.out.println(cost);
		
		
		start=System.currentTimeMillis();
		boolean res=validator.isValid("c9a0a8c94ec1");
		cost=System.currentTimeMillis()-start;
		System.out.println(cost+" "+res);
	}*/
	public static void main(String[] args) {
		Executor.exec("ls");
		long start,cost;
	/*	start=System.currentTimeMillis();
		Executor.exec("g++ -o  /home/zhu/code/example.o /home/zhu/code/example.cpp");
		cost=System.currentTimeMillis()-start;
		System.out.println(cost);*/
		
		start=System.currentTimeMillis();
		DockerClient.instance().exec("g++ -o  /home/zhu/code/example.o /home/zhu/code/example.cpp","c9a0a8c94ec1");
		cost=System.currentTimeMillis()-start;
		System.out.println(cost);
	}
}
