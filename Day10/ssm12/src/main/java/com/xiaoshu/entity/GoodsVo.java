package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class GoodsVo extends Goods {
	private String  gtname;
	private Integer num;
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getGtname() {
		return gtname;
	}
	public void setGtname(String gtname) {
		this.gtname = gtname;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date start;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date end;
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
	@Override
	public String toString() {
		return "GoodsVo [gtname=" + gtname + ", num=" + num + ", start=" + start + ", end=" + end + "]";
	}
	
	
}
