package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsExample;
import com.xiaoshu.entity.GoodsVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsMapper extends BaseMapper<Goods> {

	List<GoodsVo> findAll(GoodsVo gv);

	Goods findById(Goods goods);

	List<Goods> findGoods(Goods goods);

	List<GoodsVo> getTj();
    
}