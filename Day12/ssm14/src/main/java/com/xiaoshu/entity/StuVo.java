package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class StuVo extends Stu {
	 private String num;
	   private String dname;
	   @DateTimeFormat(pattern="yyyy-MM-dd")
	   private Date end;
	   @DateTimeFormat(pattern="yyyy-MM-dd")
	   private Date start;
	@Override
	public String toString() {
		return "StuVo [num=" + num + ", dname=" + dname + ", end=" + end + ", start=" + start + "]";
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	
}
