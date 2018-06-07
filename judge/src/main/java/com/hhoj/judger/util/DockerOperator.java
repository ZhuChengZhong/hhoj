package com.hhoj.judger.util;
/**
 * docker操作工具
 * @author zhu
 *
 */

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ExecCreation;
import com.spotify.docker.client.messages.ExecState;
import com.spotify.docker.client.messages.HostConfig;

public final class DockerOperator {
	private static DockerOperator dockerOperator;
	private DockerClient docker;
	
	private DockerOperator() throws DockerCertificateException {
		docker=DefaultDockerClient.fromEnv().build();
	}
	/**
	 * 获取 DockerOperator 单例
	 * @return
	 * @throws DockerCertificateException
	 */
	public static DockerOperator instance() throws DockerCertificateException {
		if(dockerOperator==null) {
			synchronized (DockerOperator.class) {
				dockerOperator=new DockerOperator();
			}
		}
		return dockerOperator;
	}
	
	/**
	 * 拉取镜像
	 * @param imageName
	 * @throws DockerException
	 * @throws InterruptedException
	 */
	public void createImage(String imageName) throws DockerException, InterruptedException {
		docker.pull(imageName);
	}
	
	/**
	 * 用Dockerfile 创建镜像 
	 * @param dockerfilePath
	 * @param imageName
	 * @return
	 * @throws InterruptedException
	 * @throws DockerException
	 * @throws IOException
	 */
	public String createImage(Path dockerfilePath, String imageName) throws InterruptedException, DockerException, IOException {
		final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();
		String returnedImageId = docker.build(dockerfilePath, imageName,
				message -> {
					final String imageId = message.buildImageId();
					if (imageId != null)
						imageIdFromMessage.set(imageId);
				});
		return returnedImageId;
	}
	/**
	 * 创建容器
	 * @param image 镜像名称
	 * @param volumes 数据卷
	 * @return 容器id
	 * @throws DockerException
	 * @throws InterruptedException
	 */
	public String createContainer(String image,Map<String,String>volumes) throws DockerException, InterruptedException {
		HostConfig.Bind[] binds=null;
		if(volumes!=null) {
			binds=new HostConfig.Bind[volumes.size()];
			int index=0;
			for(String key:volumes.keySet()) {
				HostConfig.Bind bind=HostConfig.Bind.builder()
					.from(key)
					.to(volumes.get(key))
					//.readOnly(true)
					.build();
				binds[index++]=bind;
			}
		}
		HostConfig hostConfig=HostConfig.builder()
				.binds(binds)
				.build();
		ContainerConfig config=ContainerConfig.builder()
				.image(image)
				.cmd("sh","-c","while :;do sleep 1 ; done")
				.networkDisabled(true)
				.workingDir("/home/")
				.hostConfig(hostConfig)
				.build();
		 return docker.createContainer(config).id();
	}
	
	/**
	 * 运行容器
	 * @param containerId
	 * @throws DockerException
	 * @throws InterruptedException
	 */
	public void startContainer(String containerId) throws DockerException, InterruptedException {
		docker.startContainer(containerId);
	}
	
	/**
	 * 关闭并删除容器
	 * @param containerId
	 * @throws DockerException
	 * @throws InterruptedException
	 */
	public void killContainer(String containerId) throws DockerException, InterruptedException {
		docker.killContainer(containerId);
		docker.removeContainer(containerId);
	}
	
	
	/**
	 * 在指定容器中执行命令
	 * @param containerId 容器id
	 * @param cmd	命令行
	 * @throws DockerException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
/*	public String execCmd(String containerId,String cmd) throws DockerException, InterruptedException, IOException {
		String[] args=cmd.split(" ");
		ExecCreation execCreation=docker.execCreate(containerId, args,
				DockerClient.ExecCreateParam.attachStdout()
				,DockerClient.ExecCreateParam.attachStderr());
		LogStream logStream=docker.execStart(execCreation.id());
		BufferedOutputStream out=new BufferedOutputStream(new ByteOutputStream());
		logStream.attach(out, out);
		logStream.close();
		return out.toString();
	}*/
	public void execCmd(String containerId,String cmd) throws DockerException, InterruptedException, IOException {
		String[] args=cmd.split(":");
		execCmd(containerId, args);
	}
	public String execCmd(String containerId,String[] cmds) throws DockerException, InterruptedException, IOException {
		ExecCreation execCreation=docker.execCreate(containerId, cmds,
				DockerClient.ExecCreateParam.attachStdout()
				,DockerClient.ExecCreateParam.attachStderr());
		docker.execStart(execCreation.id());
		return execCreation.id();
	}
	
	public Integer waitCmdFinish(String execId,int waitTime) throws DockerException, InterruptedException {
		while(waitTime>0) {
			ExecState state=docker.execInspect(execId);
			if(!state.running()) {
				return state.exitCode();
			}
			Thread.sleep(100);
			waitTime-=100;
		}
		return null;
	}
	/**
	 * 关闭
	 */
	public void close() {
		docker.close();
		dockerOperator=null;
	}
	public static void main(String[] args) throws DockerException, InterruptedException, DockerCertificateException, CloneNotSupportedException, IOException {
		try {
			Map<String,String>map=new HashMap<String, String>();
			map.put("/home/zhu/", "/home");
			String containerId=instance().createContainer("judger", map);
			instance().startContainer(containerId);
			instance().execCmd(containerId, "sh:-c:touch /home/aaa.txt");
			//instance().killContainer(containerId);
		}finally {
			instance().close();
		}
		
	}
}
