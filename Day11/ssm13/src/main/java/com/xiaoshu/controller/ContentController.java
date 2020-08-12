package com.xiaoshu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.ConVo;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentCategory;
import com.xiaoshu.entity.Operation;

import com.xiaoshu.service.ContentService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("emp")
public class ContentController {

static Logger logger = Logger.getLogger(ContentController.class);

@Autowired
private ContentService cs;

@Autowired
private OperationService operationService;

@RequestMapping("empIndex")
public String index(HttpServletRequest request, Integer menuid) throws Exception {
	List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
	request.setAttribute("operationList", operationList);
	List<ContentCategory> deptList = cs.queryDept();
	request.setAttribute("deptList", deptList);
	return "content";
}

@RequestMapping(value = "empList", method = RequestMethod.POST)
public void empList(ConVo ev, HttpServletRequest request, HttpServletResponse response, String offset, String limit)
		throws Exception {
	try {
		System.out.println(ev);

		Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
		Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
		PageInfo<ConVo> empList = cs.findEmpPage(ev, pageNum, pageSize);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("total", empList.getTotal());
		jsonObj.put("rows", empList.getList());
		WriterUtil.write(response, jsonObj.toString());
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("用户展示错误", e);
		throw e;
	}
}
//新增或修改
@RequestMapping("reserveStu")
public void reserveEmp(HttpServletRequest request, Content stu, HttpServletResponse response, MultipartFile file)
		throws Exception, IOException {
	if (file!=null) {
		String filename = file.getOriginalFilename();
		if (filename != null) {
			String substring = filename.substring(filename.lastIndexOf("."));
			String string = UUID.randomUUID().toString() + substring;
			file.transferTo(new File("D:/pic/" + string));
			stu.setPicpath(string);
		}
	}
	Integer id = stu.getContentid();
	JSONObject result = new JSONObject();
	try {
		if (id != null) { // userId不为空 说明是修改
			cs.updateEmp(stu);
			result.put("success", true);

		} else { // 添加
			cs.addEmp(stu);
			result.put("success", true);
		}
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("保存用户信息错误", e);
		result.put("success", true);
		result.put("errorMsg", "对不起，操作失败");
	}
	WriterUtil.write(response, result.toString());
}
//报表
@RequestMapping("getTje")
public void getTje(HttpServletRequest request, HttpServletResponse response) {
	List<ConVo> tje = cs.getTje();
	Object json = JSONObject.toJSON(tje);
	WriterUtil.write(response, json.toString());
}
//删除
@RequestMapping("deleteEmp")
public void delEmp(HttpServletRequest request, HttpServletResponse response) {
	JSONObject result = new JSONObject();
	try {
		String[] ids = request.getParameter("ids").split(",");
		for (String id : ids) {
			cs.deleteEmp(Integer.parseInt(id));
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
