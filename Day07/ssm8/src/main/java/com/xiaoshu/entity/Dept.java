package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Dept implements Serializable {
    @Id
    @Column(name = "d_id")
    private Integer dId;

    private String bname;

    private static final long serialVersionUID = 1L;

    /**
     * @return d_id
     */
    public Integer getdId() {
        return dId;
    }

    /**
     * @param dId
     */
    public void setdId(Integer dId) {
        this.dId = dId;
    }

    /**
     * @return bname
     */
    public String getBname() {
        return bname;
    }

    /**
     * @param bname
     */
    public void setBname(String bname) {
        this.bname = bname == null ? null : bname.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dId=").append(dId);
        sb.append(", bname=").append(bname);
        sb.append("]");
        return sb.toString();
    }
}