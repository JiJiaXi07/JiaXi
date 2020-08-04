package com.xiaoshu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Company;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.SonVo;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.PersonService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("goods")
public class PersonController {

static Logger logger = Logger.getLogger(PersonController.class);

@Autowired
private UserService userService;

@Autowired
private RoleService roleService;

@Autowired
private OperationService operationService;
@Autowired
private PersonService ps;

@RequestMapping("goodsIndex")
public String index(HttpServletRequest request, Integer menuid) throws Exception {
	List<Role> roleList = roleService.findRole(new Role());
	List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);

	List<Company> companyList = ps.queryDept();
	request.setAttribute("companyList", companyList);
	
	request.setAttribute("operationList", operationList);
	request.setAttribute("roleList", roleList);
	return "son";
}

@RequestMapping(value = "goodsList", method = RequestMethod.POST)
public void userList(HttpServletRequest request, HttpServletResponse response, String offset, String limit, SonVo gv)
		throws Exception {
	try {

		String order = request.getParameter("order");
		String ordername = request.getParameter("ordername");

		Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
		Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
		PageInfo<SonVo> userList = ps.findUserPage(gv, pageNum, pageSize, ordername, order);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("total", userList.getTotal());
		jsonObj.put("rows", userList.getList());
		WriterUtil.write(response, jsonObj.toString());
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("用户展示错误", e);
		throw e;
	}
}
// 新增或修改    
@RequestMapping("reserveStu")
public void reserveEmp(HttpServletRequest request, Person de, HttpServletResponse response) {
	Integer eid = de.getId();
	JSONObject result = new JSONObject();
	try {
		if (eid != null) { // userId不为空 说明是修改

			ps.updateEmp(de);
			result.put("success", true);
		} else {

			ps.addEmp(de);
			result.put("success", true);
		}
	} catch (Exception e) {
		e.printStackTrace();

		result.put("success", true);
		result.put("errorMsg", "对不起，操作失败");
	}
	WriterUtil.write(response, result.toString());
}
//删除
@RequestMapping("deleteEmp")
public void delEmp(HttpServletRequest request, HttpServletResponse response) {
	JSONObject result = new JSONObject();
	try {
		String[] ids = request.getParameter("ids").split(",");
		for (String id : ids) {
			ps.deleteEmp(Integer.parseInt(id));
		}
		result.put("success", true);
		result.put("delNums", ids.length);
	} catch (Exception e) {
		e.printStackTrace();
		result.put("errorMsg", "对不起，删除失败");
	}
	WriterUtil.write(response, result.toString());
}
}
