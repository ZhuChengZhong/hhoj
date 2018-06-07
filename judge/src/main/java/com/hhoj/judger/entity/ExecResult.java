package com.hhoj.judger.entity;

public class ExecResult {
	private int exitCode;
	private int useTime;
	private int useMemary;
	public int getExitCode() {
		return exitCode;
	}
	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}
	public int getUseTime() {
		return useTime;
	}
	public void setUseTime(int useTime) {
		this.useTime = useTime;
	}
	public int getUseMemary() {
		return useMemary;
	}
	public void setUseMemary(int useMemary) {
		this.useMemary = useMemary;
	}
	@Override
	public String toString() {
		return "ExecResult [exitCode=" + exitCode + ", useTime=" + useTime + ", useMemary=" + useMemary + "]";
	}
	
}
