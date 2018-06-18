package com.hhoj.judger.docker.client;

import java.util.ArrayList;
import java.util.List;

public class ContainerConfig {
	
	private ContainerConfig(){ }
	
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * 数据卷，能在物理主机与容器之间共享数据
	 * @author zhu
	 *
	 */
	public static class Volume{
		private String from;
		private String to;
		public void from(String from) {
			this.from=from;
		}
		public void to(String to) {
			this.to=to;
		}
		public String from() {
			return from;
		}
		public String to() {
			return to;
		}
	}
	public static class Builder{
		private ContainerConfig config=new ContainerConfig();
		public Builder image(String image) {
			config.image=image;
			return this;
		}
		public Builder workspace(String workspace) {
			config.workspace=workspace;
			return this;
		}
		public Builder deamon(boolean deamon) {
			config.deamon=deamon;
			return this;
		}
		public Builder cmd(String[] cmds) {
			config.cmds=cmds;
			return this;
		}
		public Builder addVolume(Volume volume) {
			if(config.volumes==null) {
				config.volumes=new ArrayList<>();
			}
			config.volumes.add(volume);
			return this;
		}
		public ContainerConfig build() {
			return config;
		}
	}
	
	/**
	 * 进入容器之后的默认工作空间
	 */
	private String workspace;
	
	/**
	 * 需要执行的命令
	 */
	private String[] cmds;
	
	/**
	 * 容器以守护态运行  -d参数
	 */
	private boolean deamon;
	
	/**
	 * 镜像名称
	 */
	private String image;
	
	/**
	 * 数据卷，能在物理主机与容器之间共享数据
	 */
	private List<Volume>volumes;

	public String getWorkspace() {
		return workspace;
	}

	public String[] getCmds() {
		return cmds;
	}

	public boolean isDeamon() {
		return deamon;
	}

	public String getImage() {
		return image;
	}

	public List<Volume> getVolumes() {
		return volumes;
	}
}
