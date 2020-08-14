package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

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
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Stu;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.StuService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;
@Controller
@RequestMapping("stu")
public class StuController {

static Logger logger = Logger.getLogger(StuController.class);

@Autowired
private StuService ss;

@Autowired
private OperationService operationService;

@RequestMapping("empIndex")
public String index(HttpServletRequest request, Integer menuid) throws Exception {
	List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
	request.setAttribute("operationList", operationList);
	List<Major> deptList = ss.queryDept();
	request.setAttribute("deptList", deptList);
	return "stu";
}

@RequestMapping(value = "empList", method = RequestMethod.POST)
public void empList(StuVo ev, HttpServletRequest request, HttpServletResponse response, String offset, String limit)
		throws Exception {
	try {
		System.out.println("jiji"+ev);

		Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
		Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
		PageInfo<StuVo> empList = ss.findEmpPage(ev, pageNum, pageSize);
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
@RequestMapping("reserveStu")
public void reserveEmp(HttpServletRequest request, Stu de, HttpServletResponse response) {
	Integer eid = de.getsId();
	JSONObject result = new JSONObject();
	try {
		if (eid != null) { // userId不为空 说明是修改

			ss.updateEmp(de);
			result.put("success", true);
		} else {

			ss.addEmp(de);
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
			ss.deleteEmp(Integer.parseInt(id));
		}
		result.put("success", true);
		result.put("delNums", ids.length);
	} catch (Exception e) {
		e.printStackTrace();
		result.put("errorMsg", "对不起，删除失败");
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
		
		Stu emp = new Stu();
		emp.setsName(row.getCell(1).getStringCellValue());
		emp.setsSex(row.getCell(2).getStringCellValue());
		emp.setsHobby(row.getCell(3).getStringCellValue());
		emp.setsBirth(row.getCell(4).getDateCellValue());
		String cname = row.getCell(5).getStringCellValue();
		Major d = ss.getCid(cname);
		if (d == null) {
			Major d2 = new Major();
			d2.setmName(cname);
			ss.addCompany(d2);
			Major d3 = ss.getCid(d2.getmName());
			emp.setmId(d3.getmId());

		} else {
			emp.setmId(d.getmId());
		}
		ss.addEmp(emp);
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
	String[] head = { "员工编号", "姓名", "性别", "爱好", "生日", "专业" };
	for (int i = 0; i < head.length; i++) {
		frow.createCell(i).setCellValue(head[i]);
	}
	List<StuVo> elist = ss.daochu();
	for (int i = 0; i < elist.size(); i++) {
		StuVo emp = elist.get(i);
		HSSFRow row = sheet.createRow(i + 1);
		row.createCell(0).setCellValue(emp.getsId());
		row.createCell(1).setCellValue(emp.getsName());
		row.createCell(2).setCellValue(emp.getsSex());
		row.createCell(3).setCellValue(emp.getsHobby());
		row.createCell(4).setCellValue(TimeUtil.formatTime(emp.getsBirth(), "yyyy-MM-dd"));
		row.createCell(5).setCellValue(emp.getManame());
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
}
