package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ConVo extends Content {
private String  yname;
@DateTimeFormat(pattern="yyyy-MM-dd")
private Date  end;
@DateTimeFormat(pattern="yyyy-MM-dd")
private Date  start;
private String  num;
public String getYname() {
	return yname;
}
public void setYname(String yname) {
	this.yname = yname;
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
public String getNum() {
	return num;
}
public void setNum(String num) {
	this.num = num;
}
@Override
public String toString() {
	return "ConVo [yname=" + yname + ", end=" + end + ", start=" + start + ", num=" + num + "]";
}

}
