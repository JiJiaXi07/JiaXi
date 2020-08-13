package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.dao.StuMapper;
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.Stu;
import com.xiaoshu.entity.StuVo;

@Service
public class StuService {
@Autowired
StuMapper sm;
@Autowired
MajorMapper mm;

public PageInfo<StuVo> findEmpPage(StuVo e, int pageNum, int pageSize) {
	PageHelper.startPage(pageNum, pageSize);
	List<StuVo> empList = sm.findPage(e);
	PageInfo<StuVo> pageInfo = new PageInfo<StuVo>(empList);
	return pageInfo;
}

//查询所有部门
public List<Major> queryDept() {
	return mm.selectAll();
}
// 修改
public void updateStu(Stu de) throws Exception {
	sm.updateByPrimaryKey(de);
};

// 新增
public void addStu(Stu de) throws Exception {
	sm.insert(de);
};

public List<StuVo> getTje() {
	// TODO Auto-generated method stub
	return sm.getTje();
}
//导出
public List<StuVo> daochu() {
	// TODO Auto-generated method stub
	return sm.findPage(null);
}
// 删除
public void deleteEmp(Integer eid) throws Exception {
	sm.deleteByPrimaryKey(eid);
};
}
