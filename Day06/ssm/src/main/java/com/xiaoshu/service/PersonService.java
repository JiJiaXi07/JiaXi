package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.CompanyMapper;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.entity.Company;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonVo;
@Service
public class PersonService {
@Autowired
PersonMapper pm;
@Autowired
CompanyMapper cm;

public PageInfo<PersonVo> findEmpPage(PersonVo e, int pageNum, int pageSize) {
	PageHelper.startPage(pageNum, pageSize);
	List<PersonVo> empList = pm.findPage(e);
	PageInfo<PersonVo> pageInfo = new PageInfo<PersonVo>(empList);
	return pageInfo;
}

//查询所有部门
public List<Company> queryDept() {
	return cm.selectAll();
}
// 修改
public void updateEmp(Person de) throws Exception {
	pm.updateByPrimaryKey(de);
};

// 新增
public void addEmp(Person de) throws Exception {
	pm.insert(de);
};
// 删除
public void deleteEmp(Integer eid) throws Exception {
	pm.deleteByPrimaryKey(eid);
};
}
