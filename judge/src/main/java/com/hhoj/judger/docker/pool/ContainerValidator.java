package com.hhoj.judger.docker.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerValidator implements Validator<String>{
	
	private static Logger logger=LoggerFactory.getLogger(ContainerFactory.class);
	@Override
	public boolean isValid(String containerId) {
		try {
			return DockerOperator.instance().checkContainer(containerId);
		}catch (Exception e) {
			logger.error("验证容器失败!!",e);
		}
		return false;
	}

	@Override
	public void invalidate(String containerId) {
		try {
			DockerOperator.instance().killContainer(containerId);
		}catch (Exception e) {
			logger.error("删除容器失败!!",e);
		}
	}

}
