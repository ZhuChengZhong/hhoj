package com.hhoj.judge.entity;
/**
 * 编程题类型实体
 * @author zhu
 *
 */
public class ProblemType {
	//id
	private int typeId;
	// 类型名称
	private String typeName;
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public ProblemType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
