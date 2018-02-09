package com.hhoj.judge.entity;
/**
 * 编程语言实体类 
 * @author zhu
 *
 */
public class Language {
	// id
	private int languageId;
	// 编程语言名称
	private String languageName;
	//编译命令
	private String compileCommand;
	//运行命令
	private String runCommand;
	
	public String getCompileCommand() {
		return compileCommand;
	}
	public void setCompileCommand(String compileCommand) {
		this.compileCommand = compileCommand;
	}
	public String getRunCommand() {
		return runCommand;
	}
	public void setRunCommand(String runCommand) {
		this.runCommand = runCommand;
	}
	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	public Language() {
		super();
		// TODO Auto-generated constructor stub
	}
}
