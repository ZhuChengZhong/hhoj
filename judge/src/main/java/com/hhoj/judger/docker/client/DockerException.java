package com.hhoj.judger.docker.client;

public class DockerException extends RuntimeException{
	public DockerException(Exception e) {
		super(e);
	}
	public DockerException(String msg) {
		super(msg);
	}
	public DockerException(String msg,Exception e) {
		super(msg,e);
	}
}
