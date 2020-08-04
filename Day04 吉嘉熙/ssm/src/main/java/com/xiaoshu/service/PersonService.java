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
import com.xiaoshu.entity.SonVo;

@Service
public class PersonService {
@Autowired
PersonMapper pm;
@Autowired
CompanyMapper cm;

public PageInfo<SonVo> findUserPage(SonVo contentVo, int pageNum, int pageSize, String ordername, String order) {
	PageHelper.startPage(pageNum, pageSize);

	List<SonVo> userList = pm.findPage(contentVo);
	PageInfo<SonVo> pageInfo = new PageInfo<SonVo>(userList);
	return pageInfo;
}

// 修改
public void updateEmp(Person de) throws Exception {
	pm.updateByPrimaryKey(de);
};

// 新增
public void addEmp(Person de) throws Exception {
	pm.insert(de);
};

//查询所有部门
public List<Company> queryDept() {
	return cm.selectAll();
}
// 删除
public void deleteEmp(Integer id) throws Exception {
	pm.deleteByPrimaryKey(id);
};
}
