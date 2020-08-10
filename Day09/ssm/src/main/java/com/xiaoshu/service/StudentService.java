package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.CourseMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Course;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.Student;

@Service
public class StudentService {
@Autowired
StudentMapper sm;
@Autowired
CourseMapper cm;

public PageInfo<StuVo> findEmpPage(StuVo e, int pageNum, int pageSize) {
	PageHelper.startPage(pageNum, pageSize);
	List<StuVo> empList = sm.findPage(e);
	PageInfo<StuVo> pageInfo = new PageInfo<StuVo>(empList);
	return pageInfo;
}

//查询所有部门
public List<Course> queryDept() {
	return cm.selectAll();
}
// 修改
public void updateEmp(Student st) throws Exception {
	sm.updateByPrimaryKey(st);
};

// 新增
public void addEmp(Student st) throws Exception {
	sm.insert(st);
};
//导出
public List<StuVo> daochu() {
	// TODO Auto-generated method stub
	return sm.findPage(null);
}
public List<StuVo> getTje() {
	// TODO Auto-generated method stub
	return sm.getTje();
}

public void addSc(Course course) {
	// TODO Auto-generated method stub
	cm.insert(course);
}
public Course getCid(String cname) {
	Course school = new Course();
	school.setName(cname);

	return cm.selectOne(school);
}

public void addCompany(Course d2) {
	// TODO Auto-generated method stub
	sm.insert(d2);
}
}
