package com.hhoj.judger.docker.pool;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.constant.Config;
import com.hhoj.judger.docker.client.ContainerConfig;
import com.hhoj.judger.docker.client.DockerClient;
import com.hhoj.judger.docker.client.DockerException;

public class LocalContainerFactory implements ObjectFactory<String>{

	private static Logger logger=LoggerFactory.getLogger(ContainerFactory.class);
	
	private DockerClient client=DockerClient.instance();
	
	@Override
	public String create() {
		ContainerConfig.Volume v=new ContainerConfig.Volume();
		v.from(Config.WORKSPACE);
		v.to(Config.WORKSPACE);
		String[]cmds= {"sh","-c","'while :; do sleep 1; done'"};
		ContainerConfig config=ContainerConfig.builder()
				.deamon(true)
				.image(Config.JUDGER_IMAGE)
				.addVolume(v)
				.cmd(cmds)
				.build();
		String id=client.createContainer(config);
		return id;
	}
	
}
