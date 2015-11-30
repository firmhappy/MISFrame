package com.bean;

public class Vo {
	protected long id,pid;
	protected String name,path,isParent,page,pageSize,className;
	protected int count;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public Vo() {
		super();
	}
	public Vo(long id, long pid, String name, String path, String isParent,
			String page, String pageSize, int count) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.path = path;
		this.isParent = isParent;
		this.page = page;
		this.pageSize = pageSize;
		this.count = count;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String classname) {
		this.className = classname;
	}	

}
