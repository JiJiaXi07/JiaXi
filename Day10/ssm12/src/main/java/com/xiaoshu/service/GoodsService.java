package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.dao.GoodstypeMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.Goodstype;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class GoodsService {
	@Autowired
	GoodsMapper gm;
	
	@Autowired
	GoodstypeMapper gtm;
	
	public PageInfo<GoodsVo> findUserPage(GoodsVo gv, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<GoodsVo> userList = gm.findAll(gv);
		PageInfo<GoodsVo> pageInfo = new PageInfo<GoodsVo>(userList);
		return pageInfo;
	}

	public List<Goodstype> findGoodstype() {
		// TODO Auto-generated method stub
		return gtm.selectAll();
	}

	public void add(Goods goods) {
		gm.insert(goods);
		
	}

	public void update(Goods goods) {
		// TODO Auto-generated method stub
		gm.updateByPrimaryKey(goods);
	}

	public Goods findById(Goods goods) {
		// TODO Auto-generated method stub
		return gm.findById(goods);
	}

	public List<Goods> findGoods(Goods goods) {
		// TODO Auto-generated method stub
		return gm.findGoods(goods);
	}

	public List<Goodstype> findstatus(Goodstype goodstype) {
		// TODO Auto-generated method stub
		return gtm.findstatus(goodstype);
	}
	@Autowired
	JedisPool jedisPool;
	public void addType(Goodstype goodstype) {
		gtm.insert(goodstype);
		Goodstype one = gtm.selectOne(goodstype);
		Jedis jedis = jedisPool.getResource();
		jedis.hset("goodstype",one.getId()+"", one.toString());
		
	}

	public List<GoodsVo> getTj() {
		// TODO Auto-generated method stub
		return gm.getTj();
	}

}
