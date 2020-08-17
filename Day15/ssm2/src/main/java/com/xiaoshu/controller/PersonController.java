package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.jms.Destination;
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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.BankMapper;
import com.xiaoshu.entity.Bank;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.PeVo;
import com.xiaoshu.entity.Person;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.PersonService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("per")
public class PersonController {

static Logger logger = Logger.getLogger(PersonController.class);

@Autowired
private PersonService ps;
@Autowired
private BankMapper bm;
@Autowired
JmsTemplate jmsTemplate;
@Autowired
Destination queueTextDestination;
@Autowired
private OperationService operationService;

@RequestMapping("peIndex")
public String index(HttpServletRequest request, Integer menuid) throws Exception {
	List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
	request.setAttribute("operationList", operationList);
	List<Bank> deptList = ps.queryDept();
	request.setAttribute("deptList", deptList);
	return "person";
}

@RequestMapping(value = "peList", method = RequestMethod.POST)
public void empList(PeVo ev, HttpServletRequest request, HttpServletResponse response, String offset, String limit)
		throws Exception {
	try {
		System.out.println(ev);

		Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
		Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
		PageInfo<PeVo> empList = ps.findEmpPage(ev, pageNum, pageSize);

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
@RequestMapping("reservePer")
public void reserveEmp(HttpServletRequest request, Person de, HttpServletResponse response) {
	Integer eid = de.getpId();
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
//报表
@RequestMapping("getTje")
public void getTje(HttpServletRequest request, HttpServletResponse response) {
	List<PeVo> tje = ps.getTje();
	Object json = JSONObject.toJSON(tje);
	WriterUtil.write(response, json.toString());
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
	List<PeVo> elist = ps.daochu();
	for (int i = 0; i < elist.size(); i++) {
		PeVo pv = elist.get(i);
		HSSFRow row = sheet.createRow(i + 1);
		row.createCell(0).setCellValue(pv.getpId());
		row.createCell(1).setCellValue(pv.getpName());
		row.createCell(2).setCellValue(pv.getpSex());
		row.createCell(3).setCellValue(pv.getpAge());
		row.createCell(4).setCellValue(TimeUtil.formatTime(pv.getCreatetime(), "yyyy-MM-dd"));
		row.createCell(5).setCellValue(pv.getpLike());
		row.createCell(6).setCellValue(pv.getpCount());

		row.createCell(7).setCellValue(pv.getBname());
	}
	File file = new File("D:/员工信息.xls");
	OutputStream os = new FileOutputStream(file);
	wb.write(os);
	wb.close();
}


//Mq
@RequestMapping("reserveSc")
public void mqmq(HttpServletRequest request, HttpServletResponse response, Bank jor) {
	JSONObject result = new JSONObject();
	try {
		
		ps.addBank(jor);
		result.put("success", true);

	
} catch (Exception e) {
	e.printStackTrace();
	result.put("success", true);
	result.put("errorMsg", "对不起，操作失败");
}

	WriterUtil.write(response, result.toString());
}
}
