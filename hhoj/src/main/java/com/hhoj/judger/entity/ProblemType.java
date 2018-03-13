package com.hhoj.judger.entity;

import java.io.Serializable;

/**
 * 编程题类型实体
 * @author zhu
 *
 */
public class ProblemType implements Serializable{
	//id
	private Integer typeId;
	// 类型名称
	private String typeName;
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
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
	@Override
	public String toString() {
		return "ProblemType [typeId=" + typeId + ", typeName=" + typeName + "]";
	}
	
}
