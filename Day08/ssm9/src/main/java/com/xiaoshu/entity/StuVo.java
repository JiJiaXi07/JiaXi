package com.xiaoshu.entity;

public class StuVo extends Student {
private String cname;
private String start;
private String end;
private Integer num;


public Integer getNum() {
	return num;
}

public void setNum(Integer num) {
	this.num = num;
}

public String getStart() {
	return start;
}

public void setStart(String start) {
	this.start = start;
}

public String getEnd() {
	return end;
}

public void setEnd(String end) {
	this.end = end;
}

public String getCname() {
	return cname;
}

public void setCname(String cname) {
	this.cname = cname;
}

@Override
public String toString() {
	return "StuVo [cname=" + cname + ", start=" + start + ", end=" + end + ", num=" + num + "]";
}


}
