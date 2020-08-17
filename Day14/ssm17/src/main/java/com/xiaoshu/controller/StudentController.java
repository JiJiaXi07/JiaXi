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
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.TeacherMapper;

import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.Teacher;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("emp")
public class StudentController {

static Logger logger = Logger.getLogger(StudentController.class);

@Autowired
private StudentService ss;
@Autowired
private TeacherMapper tm;
@Autowired
private OperationService operationService;

@Autowired
JmsTemplate jt;
@RequestMapping("empIndex")
public String index(HttpServletRequest request, Integer menuid) throws Exception {
	List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
	request.setAttribute("operationList", operationList);
	List<Teacher> deptList = ss.queryDept();
	request.setAttribute("deptList", deptList);
	return "stu";
}

@RequestMapping(value = "empList", method = RequestMethod.POST)
public void empList(StuVo ev, HttpServletRequest request, HttpServletResponse response, String offset, String limit)
		throws Exception {
	try {
		System.out.println(ev);

		Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
		Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
		PageInfo<StuVo> empList = ss.findEmpPage(ev, pageNum, pageSize);

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
public void reserveEmp(HttpServletRequest request, Student de, HttpServletResponse response) {
	Integer eid = de.getId();
	JSONObject result = new JSONObject();
	String sname = de.getName();
	Student s = ss.findByname(sname);
	try {
		if (eid != null) { // userId不为空 说明是修改

			ss.updateEmp(de);
			result.put("success", true);
		} else {
			if (s==null) {
				ss.addEmp(de);
				result.put("success", true);
			}else {
				result.put("errorMsg", "该用户名被使用");
			}

			
		}
	} catch (Exception e) {
		e.printStackTrace();

		result.put("success", true);
		result.put("errorMsg", "对不起，操作失败");
	}
	WriterUtil.write(response, result.toString());
}
//报表
@RequestMapping("getTje")
public void getTje(HttpServletRequest request, HttpServletResponse response) {
	List<StuVo> tje = ss.getTje();
	Object json = JSONObject.toJSON(tje);
	WriterUtil.write(response, json.toString());
}

//新增部门		
@RequestMapping("reserveSc")
public void reserveBm(HttpServletRequest request, Teacher te, HttpServletResponse response) {

	JSONObject result = new JSONObject();
	
//mq
	          tm.insert(te);
			jt.convertAndSend("queue_text",JSONObject.toJSONString(te));
		/*
		 * //res Major major = new Major(); major.setmName(ma.getmName()); Major one =
		 * mm.selectOne(major)
		 */
			
		result.put("success", true);
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
		emp.setAge(row.getCell(2).getStringCellValue());
		emp.setCode(row.getCell(3).getStringCellValue());
		emp.setGrade(row.getCell(4).getStringCellValue());
		emp.setEntrytime(TimeUtil.ParseTime(row.getCell(5).getStringCellValue(), "yyyy-MM-dd"));
		emp.setCreatetime(TimeUtil.ParseTime(row.getCell(6).getStringCellValue(), "yyyy-MM-dd"));
		String cname = row.getCell(7).getStringCellValue();
		Teacher d = ss.getCid(cname);
		if (d == null) {
			Teacher t = new Teacher();
			t.setName(d.getName());
			ss.addCompany(t);
			Teacher d3 = ss.getCid(t.getName());
			emp.setTeacherid(d3.getId());
			ss.addEmp(emp);

		} else {
			emp.setTeacherid(d.getId());
			ss.addEmp(emp);
		}
	}
	result.put("success", true);
	WriterUtil.write(response, result.toString());
}
//导出
@RequestMapping("daochu")
public void daochu(HttpServletRequest request, HttpServletResponse response) throws Exception {
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet();
	HSSFRow frow = sheet.createRow(0);
	String[] head = { "学生编号", "姓名", "年龄", "年级", "入学时间", "创建时间", "代课老师" };
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
		row.createCell(5).setCellValue(TimeUtil.formatTime(emp.getEntrytime(), "yyyy-MM-dd"));
		row.createCell(6).setCellValue(TimeUtil.formatTime(emp.getCreatetime(), "yyyy-MM-dd"));
		row.createCell(7).setCellValue(emp.getTname());
	}
	File file = new File("D:/学生信息.xls");
	OutputStream os = new FileOutputStream(file);
	wb.write(os);
	wb.close();
}
}
