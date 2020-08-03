package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.dao.TypeMapper;
import com.xiaoshu.entity.GoVo;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.Type;

@Service
public class GoodsService {
@Autowired
private GoodsMapper gm;
@Autowired
private TypeMapper tm;


public PageInfo<GoVo> findUserPage(GoVo GoVo, int pageNum, int pageSize, String ordername, String order) {
	PageHelper.startPage(pageNum, pageSize);

	List<GoVo> userList = gm.findPage(GoVo);
	PageInfo<GoVo> pageInfo = new PageInfo<GoVo>(userList);
	return pageInfo;
}

// 修改
public void updateEmp(Goods good) throws Exception {
	gm.updateByPrimaryKey(good);
};

// 新增
public void addEmp(Goods good) throws Exception {
	gm.insert(good);
};

//查询所有部门
public List<Type> queryDept() {
	return tm.selectAll();
}
// 删除
public void deleteEmp(Integer gId) throws Exception {
	gm.deleteByPrimaryKey(gId);
};
}
