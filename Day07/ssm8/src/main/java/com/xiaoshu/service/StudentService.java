package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.DeptMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.Student;
@Service
public class StudentService {
@Autowired
StudentMapper em;
@Autowired
DeptMapper dm;

public PageInfo<StuVo> findEmpPage(StuVo e, int pageNum, int pageSize) {
	PageHelper.startPage(pageNum, pageSize);
	List<StuVo> empList = em.findPage(e);
	PageInfo<StuVo> pageInfo = new PageInfo<StuVo>(empList);
	return pageInfo;
}

//查询所有部门
public List<Dept> queryDept() {
	return dm.selectAll();
}
// 修改
public void updateEmp(Student de) throws Exception {
	em.updateByPrimaryKey(de);
};

// 新增
public void addEmp(Student de) throws Exception {
	em.insert(de);
}
public List<StuVo> getTje() {
	// TODO Auto-generated method stub
	return em.getTje();
}

}
