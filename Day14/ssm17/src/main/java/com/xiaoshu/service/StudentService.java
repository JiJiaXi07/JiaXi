package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.Teacher;

@Service
public class StudentService {
@Autowired
StudentMapper sm;
@Autowired
TeacherMapper tm;

public PageInfo<StuVo> findEmpPage(StuVo e, int pageNum, int pageSize) {
	PageHelper.startPage(pageNum, pageSize);
	List<StuVo> empList = sm.findPage(e);
	PageInfo<StuVo> pageInfo = new PageInfo<StuVo>(empList);
	return pageInfo;
}

//查询所有部门
public List<Teacher> queryDept() {
	return tm.selectAll();
}
// 修改
public void updateEmp(Student de) throws Exception {
	sm.updateByPrimaryKey(de);
};

// 新增
public void addEmp(Student de) throws Exception {
	sm.insert(de);
};
public List<StuVo> getTje() {
	// TODO Auto-generated method stub
	return sm.getTje();
}

public Teacher getCid(String cname) {
	// TODO Auto-generated method stub
	return tm.getCid(cname);
}

//导出
public List<StuVo> daochu() {
	// TODO Auto-generated method stub
	return sm.findPage(null);
}

public void addCompany(Teacher t) {
	tm.insert(t);
	
}

public Student findByname(String sname) {
	// TODO Auto-generated method stub
	return sm.findbyName(sname);
}



}
