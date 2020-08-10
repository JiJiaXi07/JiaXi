package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class StuVo extends Student {
  private String  cname;
  
  private String  num;
  @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date  start;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date  end;
public String getCname() {
	return cname;
}
public void setCname(String cname) {
	this.cname = cname;
}
public String getNum() {
	return num;
}
public void setNum(String num) {
	this.num = num;
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
public StuVo(String cname, String num, Date start, Date end) {
	super();
	this.cname = cname;
	this.num = num;
	this.start = start;
	this.end = end;
}
public StuVo() {
	
}
}
