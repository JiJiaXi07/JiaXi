package com.xiaoshu.entity;

import java.util.Date;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

public class PersonVo extends Person {

	  @Column(name = "express_name")
	    private String Name;
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date start;
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date end;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	
	 
}
