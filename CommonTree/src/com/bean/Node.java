package com.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Node entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NODE", schema = "SA")
public class Node implements java.io.Serializable {

	// Fields

	private long id;
	private String name;
	private String other;
	private String path;
	private long pid;
	private String sex;

	// Constructors

	/** default constructor */
	public Node() {
	}

	/** minimal constructor */
	public Node(String name, String other, String path, String sex) {
		this.name = name;
		this.other = other;
		this.path = path;
		this.sex = sex;
	}

	/** full constructor */
	public Node(String name, String other, String path, long pid, String sex) {
		this.name = name;
		this.other = other;
		this.path = path;
		this.pid = pid;
		this.sex = sex;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID",  nullable = false, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 80)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "OTHER", nullable = false, length = 80)
	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Column(name = "PATH", nullable = false, length = 80)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "PID", scale = 0)
	public long getPid() {
		return this.pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	@Column(name = "SEX", nullable = false, length = 80)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}