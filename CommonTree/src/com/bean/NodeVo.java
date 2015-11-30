package com.bean;

public class NodeVo extends Vo {
	private String other;
	private String sex;
	
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public NodeVo() {
		super();
	}

	public NodeVo(String other, String sex,long id, long pid, String name, String path, String isParent,
			String page, String pageSize, int count) {
		super(id,pid,name,path,isParent,page,pageSize,count);
		this.other = other;
		this.sex = sex;
	}
	

}
