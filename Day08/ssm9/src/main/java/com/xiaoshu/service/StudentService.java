package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.SchoolMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.Tje;

@Service
public class StudentService {
@Autowired
StudentMapper em;
@Autowired
SchoolMapper dm;

public PageInfo<StuVo> findEmpPage(StuVo e, int pageNum, int pageSize) {
	PageHelper.startPage(pageNum, pageSize);
	List<StuVo> empList = em.findPage(e);
	PageInfo<StuVo> pageInfo = new PageInfo<StuVo>(empList);
	return pageInfo;
}

//查询所有部门
public List<School> queryDept() {
	return dm.selectAll();
}
// 修改
public void updateEmp(Student stu) throws Exception {
	em.updateByPrimaryKey(stu);
};

// 新增
public void addEmp(Student stu) throws Exception {
	em.insert(stu);
};

// 删除
public void deleteEmp(Integer eid) throws Exception {
	em.deleteByPrimaryKey(eid);
}


//导出
public List<StuVo> daochu() {
	// TODO Auto-generated method stub
	return em.findPage(null);
}
public School getCid(String cname) {
	School school = new School();
	school.setScname(cname);

	return dm.selectOne(school);
}

public void addCompany(School d2) {
	// TODO Auto-generated method stub
	dm.insert(d2);
}
public List<StuVo> getTje() {
	// TODO Auto-generated method stub
	return em.getTje();
}
}
