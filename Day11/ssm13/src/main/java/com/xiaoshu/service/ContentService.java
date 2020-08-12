package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.ContentCategoryMapper;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.entity.ConVo;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentCategory;
@Service
public class ContentService {
@Autowired
ContentMapper cm;
@Autowired
ContentCategoryMapper ym;

public PageInfo<ConVo> findEmpPage(ConVo e, int pageNum, int pageSize) {
	PageHelper.startPage(pageNum, pageSize);
	List<ConVo> empList = cm.findPage(e);
	PageInfo<ConVo> pageInfo = new PageInfo<ConVo>(empList);
	return pageInfo;
}

//查询所有部门
public List<ContentCategory> queryDept() {
	return ym.queryDept();
}
// 修改
public void updateEmp(Content de) throws Exception {
	cm.updateByPrimaryKey(de);
};

// 新增
public void addEmp(Content de) throws Exception {
	cm.insert(de);
};

public List<ConVo> getTje() {
	// TODO Auto-generated method stub
	return cm.getTje();
}
// 删除
public void deleteEmp(Integer eid) throws Exception {
	cm.deleteByPrimaryKey(eid);
};
}
