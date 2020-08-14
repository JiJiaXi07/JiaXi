package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class StuVo extends Stu {
private Integer  num;
@DateTimeFormat(pattern="yyyy-MM-dd")
private Date start;
@DateTimeFormat(pattern="yyyy-MM-dd")
private Date end;
private String maname;
public Integer getNum() {
	return num;
}
public void setNum(Integer num) {
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
public String getManame() {
	return maname;
}
public void setManame(String maname) {
	this.maname = maname;
}
@Override
public String toString() {
	return "StuVo "+super.toString()+"[num=" + num + ", start=" + start + ", end=" + end + ", maname=" + maname + "]";
}




}
