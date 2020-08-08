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
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.Student;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("emp")
public class StudentController {

static Logger logger = Logger.getLogger(StudentController.class);

@Autowired
private StudentService es;

@Autowired
private OperationService operationService;

@RequestMapping("empIndex")
public String index(HttpServletRequest request, Integer menuid) throws Exception {
	List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
	request.setAttribute("operationList", operationList);
	List<Dept> deptList = es.queryDept();
	request.setAttribute("deptList", deptList);
	return "emp";
}

@RequestMapping(value = "empList", method = RequestMethod.POST)
public void empList(StuVo ev, HttpServletRequest request, HttpServletResponse response, String offset, String limit)
		throws Exception {
	try {
		System.out.println(ev);

		Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
		Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
		PageInfo<StuVo> empList = es.findEmpPage(ev, pageNum, pageSize);
		System.out.println(empList.getList().get(0));

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
// 新增或修改
@RequestMapping("reserveEmp")
public void reserveEmp(HttpServletRequest request, Student emp, HttpServletResponse response, MultipartFile file)
		throws Exception, IOException {
	System.out.println(file);
	if (emp.getImg() != null) {
		String filename = file.getOriginalFilename();
		if (filename != null) {
			String substring = filename.substring(filename.lastIndexOf("."));
			String string = UUID.randomUUID().toString() + substring;
			file.transferTo(new File("D:/pic/" + string));
			emp.setImg(string);
		}

	}

	Integer id = emp.getId();
	JSONObject result = new JSONObject();
	try {
		if (id != null) { // userId不为空 说明是修改
			es.updateEmp(emp);
			result.put("success", true);

		} else { // 添加
			es.addEmp(emp);
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
	List<StuVo> tje = es.getTje();
	Object json = JSONObject.toJSON(tje);
	WriterUtil.write(response, json.toString());
}
}
