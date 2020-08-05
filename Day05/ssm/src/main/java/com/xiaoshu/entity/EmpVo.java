package com.xiaoshu.entity;

import java.util.Date;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

public class EmpVo extends Emp {
	  @Column(name = "d_dname")
	    private String dDname;
	  
	  @DateTimeFormat(pattern="yyyy-MM-dd")
		private Date start;
	    @DateTimeFormat(pattern="yyyy-MM-dd")
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

	public String getdDname() {
		return dDname;
	}

	public void setdDname(String dDname) {
		this.dDname = dDname;
	}

	@Override
	public String toString() {
		return "EmpVo "+super.toString()+"[dDname=" + dDname + ", start=" + start + ", end=" + end + "]";
	}
	
	  
}
