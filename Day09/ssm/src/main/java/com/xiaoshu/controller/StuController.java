package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Course;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.Student;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("emp")
public class StuController {

static Logger logger = Logger.getLogger(StuController.class);

@Autowired
private StudentService ss;

@Autowired
private OperationService operationService;

@RequestMapping("empIndex")
public String index(HttpServletRequest request, Integer menuid) throws Exception {
	List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
	request.setAttribute("operationList", operationList);
	List<Course> deptList = ss.queryDept();
	request.setAttribute("deptList", deptList);
	return "stu";
}

@RequestMapping(value = "empList", method = RequestMethod.POST)
public void empList(StuVo ev, HttpServletRequest request, HttpServletResponse response, String offset, String limit)
		throws Exception {
	try {
	

		Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
		Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
		PageInfo<StuVo> empList = ss.findEmpPage(ev, pageNum, pageSize);
		request.setAttribute("empList", empList);
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
@RequestMapping("reserveStu")
public void reserveEmp(HttpServletRequest request, Student stu, HttpServletResponse response) {
	Integer eid = stu.getId();
	JSONObject result = new JSONObject();
	try {
		if (eid != null) { // userId不为空 说明是修改

			ss.updateEmp(stu);
			result.put("success", true);
		} else {

			ss.addEmp(stu);
			result.put("success", true);
		}
	} catch (Exception e) {
		e.printStackTrace();

		result.put("success", true);
		result.put("errorMsg", "对不起，操作失败");
	}
	WriterUtil.write(response, result.toString());
}
//导出
@RequestMapping("daochu")
public void daochu(HttpServletRequest request, HttpServletResponse response) throws Exception {
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet();
	HSSFRow frow = sheet.createRow(0);
	String[] head = { "员工编号", "姓名", "性别", "年龄", "图片", "时间", "部门" };
	for (int i = 0; i < head.length; i++) {
		frow.createCell(i).setCellValue(head[i]);
	}
	List<StuVo> elist = ss.daochu();
	for (int i = 0; i < elist.size(); i++) {
		StuVo emp = elist.get(i);
		HSSFRow row = sheet.createRow(i + 1);
		row.createCell(0).setCellValue(emp.getId());
		row.createCell(1).setCellValue(emp.getName());
		row.createCell(2).setCellValue(emp.getAge());
		row.createCell(3).setCellValue(emp.getCode());
		row.createCell(4).setCellValue(emp.getGrade());
		row.createCell(5).setCellValue(TimeUtil.formatTime(emp.getCreatetime(), "yyyy-MM-dd"));
		row.createCell(6).setCellValue(TimeUtil.formatTime(emp.getEntrytime(), "yyyy-MM-dd"));
		row.createCell(7).setCellValue(emp.getCname());
	}
	File file = new File("D:/员工信息.xls");
	OutputStream os = new FileOutputStream(file);
	wb.write(os);
	wb.close();
}
//报表
@RequestMapping("getTje")
public void getTje(HttpServletRequest request, HttpServletResponse response) {
	List<StuVo> tje = ss.getTje();
	Object json = JSONObject.toJSON(tje);
	WriterUtil.write(response, json.toString());
}
//新
@RequestMapping("reserveSc")
public void reserveSc(HttpServletRequest request, Course course, HttpServletResponse response) {
	
	Integer eid = course.getId();
	JSONObject result = new JSONObject();
	try {
					System.out.println(course);
			ss.addSc(course);
			result.put("success", true);
		
	} catch (Exception e) {
		e.printStackTrace();

		result.put("success", true);
		result.put("errorMsg", "对不起，操作失败");
	}
	WriterUtil.write(response, result.toString());
}
//导入
@RequestMapping("daoru")
public void daoru(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws Exception {
	JSONObject result = new JSONObject();
	HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
	HSSFSheet sheet = wb.getSheetAt(0);
	int hs = sheet.getLastRowNum();
	for (int i = 1; i <= hs; i++) {
		HSSFRow row = sheet.getRow(i);
		Student emp = new Student();
		emp.setName(row.getCell(1).getStringCellValue());
		emp.setAge(row.getCell(3).getStringCellValue());
		emp.setCode(row.getCell(2).getStringCellValue());
		emp.setGrade(row.getCell(4).getStringCellValue());
		emp.setCreatetime(TimeUtil.ParseTime(row.getCell(5).toString(), "yyyy-MM-dd"));
		emp.setEntrytime(TimeUtil.ParseTime(row.getCell(6).toString(), "yyyy-MM-dd"));
		String cname = row.getCell(7).getStringCellValue();
		Course d = ss.getCid(cname);
		if (d == null) {
			Course d2 = new Course();
			d2.setName(cname);
			ss.addCompany(d2);
			Course d3 = ss.getCid(d2.getName());
			emp.setCourseid(d3.getId());

		} else {
			emp.setCourseid(d.getId());
		}
		ss.addEmp(emp);
	}
	result.put("success", true);
	WriterUtil.write(response, result.toString());
}
}
