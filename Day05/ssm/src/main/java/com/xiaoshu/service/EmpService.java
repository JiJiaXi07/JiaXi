package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.DeptMapper;
import com.xiaoshu.dao.EmpMapper;
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.EmpVo;

@Service
public class EmpService {
	@Autowired
	EmpMapper em;
	@Autowired
	DeptMapper dm;

	public PageInfo<EmpVo> findEmpPage(EmpVo e, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<EmpVo> empList = em.findPage(e);
		PageInfo<EmpVo> pageInfo = new PageInfo<EmpVo>(empList);
		return pageInfo;
	}

// 修改
public void updateEmp(Emp de) throws Exception {
	em.updateByPrimaryKey(de);
};

// 新增
public void addEmp(Emp de) throws Exception {
	em.insert(de);
};


//查询所有部门
public List<Dept> queryDept() {
	return dm.selectAll();
}
// 删除
public void deleteEmp(Integer eid) throws Exception {
	em.deleteByPrimaryKey(eid);
};
}
