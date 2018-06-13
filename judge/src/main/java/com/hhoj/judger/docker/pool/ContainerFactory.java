package com.hhoj.judger.docker.pool;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerFactory implements ObjectFactory<String>{
	
	private static Logger logger=LoggerFactory.getLogger(ContainerFactory.class);
	
	/**
	 * docker客户端操作工具
	 */
	private DockerOperator dockerOperator=DockerOperator.instance();
	
	/**
	 * docker镜像名称
	 */
	private String imageName;
	
	/**
	 * docker数据卷
	 */
	private Map<String,String>volumes;
	
	public ContainerFactory(String imageName,Map<String,String>volumes) {
			this.imageName=imageName;
			this.volumes=volumes;
	}
	public String create() {
		try {
			String containerId=dockerOperator.createContainer(imageName, volumes);
			dockerOperator.startContainer(containerId);
			return containerId;
		}catch(Exception e) {
			logger.error("创建容器失败!!!");
			throw new RuntimeException(e);
		}
	}

}
