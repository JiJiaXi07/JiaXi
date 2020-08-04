package com.xiaoshu.entity;

import javax.persistence.Column;

public class SonVo extends Person {
	   @Column(name = "company_name")
	    private String companyName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	   
}
