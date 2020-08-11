package com.xiaoshu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.Goodstype;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.GoodsService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("goods")
public class GoodsController {
	@Autowired
	GoodsService gs;

	@Autowired
	private OperationService operationService;

	@RequestMapping("goodsIndex")
	public String index(HttpServletRequest request, Integer menuid) throws Exception {
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		return "goods";
	}

	@RequestMapping(value = "goodsList", method = RequestMethod.POST)
	public void userList(GoodsVo gv, HttpServletRequest request, HttpServletResponse response, String offset,
			String limit) throws Exception {
		try {
			Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
			Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
			PageInfo<GoodsVo> userList = gs.findUserPage(gv, pageNum, pageSize);
			List<Goodstype> gtList = gs.findGoodstype();
			request.getSession().setAttribute("gtList", gtList);

			Goods goods = new Goods();
			List<Goods> ggList = gs.findGoods(goods);
			request.getSession().setAttribute("ggList", ggList);

			Goodstype goodstype = new Goodstype();
			List<Goodstype> gttList = gs.findstatus(goodstype);
			request.getSession().setAttribute("gttList", gttList);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total", userList.getTotal());
			jsonObj.put("rows", userList.getList());
			WriterUtil.write(response, jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request, Goods goods, HttpServletResponse response) {
		Integer id = goods.getId();
		JSONObject result = new JSONObject();
		Goods g = gs.findById(goods);
		try {
			if (id != null) { // userId不为空 说明是修改
				gs.update(goods);
				result.put("success", true);

			} else { // 添加
				if (g == null) {
					gs.add(goods);
					result.put("success", true);
				} else {
					result.put("errorMsg", "该编号已被使用");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}

	// 新增或修改
	@RequestMapping("addType")
	public void addType(HttpServletRequest request, Goodstype goodstype, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {

			gs.addType(goodstype);
			result.put("success", true);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("getTj")
	public void getTj(HttpServletRequest request,HttpServletResponse response){
		List<GoodsVo> g = gs.getTj();
		System.out.println(g.toString());
		Object json = JSONObject.toJSON(g);
		WriterUtil.write(response, json.toString());
	}
}
