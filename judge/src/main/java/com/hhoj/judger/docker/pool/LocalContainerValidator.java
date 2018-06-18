package com.hhoj.judger.docker.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.docker.client.DockerClient;

public class LocalContainerValidator implements Validator<String>{
	
	private static Logger logger=LoggerFactory.getLogger(ContainerFactory.class);
	@Override
	public boolean isValid(String containerId) {
		boolean res=DockerClient.instance().checkContainer(containerId);
		return res;
	}

	@Override
	public void invalidate(String containerId) {
		DockerClient.instance().killContainer(containerId);
	}

}
