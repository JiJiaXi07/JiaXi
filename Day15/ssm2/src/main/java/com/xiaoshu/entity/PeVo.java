package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PeVo extends Person {
private String bname;
@DateTimeFormat(pattern="yyyy-MM-dd")
private Date start;
@DateTimeFormat(pattern="yyyy-MM-dd")
private Date end;
private String num;
@Override
public String toString() {
	return "PeVo [bname=" + bname + ", start=" + start + ", end=" + end + ", num=" + num + "]";
}
public String getBname() {
	return bname;
}
public void setBname(String bname) {
	this.bname = bname;
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
public String getNum() {
	return num;
}
public void setNum(String num) {
	this.num = num;
}

}
