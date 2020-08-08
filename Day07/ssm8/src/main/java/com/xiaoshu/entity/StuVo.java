package com.xiaoshu.entity;

public class StuVo extends Student {
   private String dename;

   private Integer num;
   
public Integer getNum() {
	return num;
}

public void setNum(Integer num) {
	this.num = num;
}

@Override
public String toString() {
	return "StuVo [dename=" + dename + ", num=" + num + "]";
}

public String getDename() {
	return dename;
}

public void setDename(String dename) {
	this.dename = dename;
}
   
}
