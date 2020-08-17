package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.BankMapper;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.entity.Bank;
import com.xiaoshu.entity.PeVo;
import com.xiaoshu.entity.Person;
@Service
public class PersonService {
@Autowired
PersonMapper pm;
@Autowired
BankMapper bm;

public PageInfo<PeVo> findEmpPage(PeVo e, int pageNum, int pageSize) {
	PageHelper.startPage(pageNum, pageSize);
	List<PeVo> empList = pm.findPage(e);
	PageInfo<PeVo> pageInfo = new PageInfo<PeVo>(empList);
	return pageInfo;
}

//查询所有部门
public List<Bank> queryDept() {
	return bm.selectAll();
}
// 修改
public void updateEmp(Person de) throws Exception {
	pm.updateByPrimaryKey(de);
};

// 新增
public void addEmp(Person de) throws Exception {
	pm.insert(de);
};
public List<PeVo> getTje() {
	// TODO Auto-generated method stub
	return pm.getTje();
}
//导出
public List<PeVo> daochu() {
	// TODO Auto-generated method stub
	return pm.findPage(null);
}
@Autowired
Destination queueTextDestination;

@Autowired
JmsTemplate jmsTemplate;
public void addBank(Bank b) {
	
	bm.insert(b);
	Bank bank = new Bank();
	bank.setbName(b.getbName());
	
	Bank one = bm.selectOne(bank);
	System.out.println(one);
	jmsTemplate.convertAndSend(queueTextDestination,JSONObject.toJSONString(one));
	
}
}
