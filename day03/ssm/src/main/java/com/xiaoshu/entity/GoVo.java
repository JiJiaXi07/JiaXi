package com.xiaoshu.entity;

import javax.persistence.Column;

public class GoVo extends Goods {
	 @Column(name = "t_typename")
	    private String tTypename;

	public String gettTypename() {
		return tTypename;
	}

	public void settTypename(String tTypename) {
		this.tTypename = tTypename;
	}


}
