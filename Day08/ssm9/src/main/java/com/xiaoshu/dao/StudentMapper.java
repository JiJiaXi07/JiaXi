package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentExample;
import com.xiaoshu.entity.Tje;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper extends BaseMapper<Student> {


	School getCid(String cname);

	List<StuVo> findPage(StuVo e);

	List<StuVo> getTje();
}