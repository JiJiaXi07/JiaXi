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
import com.xiaoshu.dao.MajorMapper;
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

@RequestMapping("stuIndex")
public String index(HttpServletRequest request, Integer menuid) throws Exception {
	List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
	request.setAttribute("operationList", operationList);
	List<Major> deptList = ss.queryDept();
	request.setAttribute("deptList", deptList);
	return "stu";
}

@RequestMapping(value = "stuList", method = RequestMethod.POST)
public void empList(StuVo ev, HttpServletRequest request, HttpServletResponse response, String offset, String limit)
		throws Exception {
	try {
		Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
		Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
		PageInfo<StuVo> stuList = ss.findEmpPage(ev, pageNum, pageSize);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("total", stuList.getTotal());
		jsonObj.put("rows", stuList.getList());
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
	Integer eid = de.getSdId();
	JSONObject result = new JSONObject();
	try {
		if (eid != null) { // userId不为空 说明是修改

			ss.updateStu(de);
			result.put("success", true);
		} else {
            System.out.println(de);
			ss.addStu(de);
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
	List<StuVo> tje = ss.getTje();
	System.out.println(tje);
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
	List<StuVo> slist = ss.daochu();
	for (int i = 0; i < slist.size(); i++) {
		StuVo stu = slist.get(i);
		HSSFRow row = sheet.createRow(i + 1);
		if (stu.getDname().equals("大数据") && stu.getSdhobby().contains("吃")) {
			row.createCell(0).setCellValue(stu.getSdId());
			row.createCell(1).setCellValue(stu.getSdname());
			row.createCell(2).setCellValue(stu.getSdsex());
			row.createCell(3).setCellValue(stu.getSdhobby());
			row.createCell(5).setCellValue(TimeUtil.formatTime(stu.getSdbirth(),"yyyy-MM-dd"));
			row.createCell(6).setCellValue(stu.getDname());
		}
	}
	File file = new File("D:/员工信息.xls");
	OutputStream os = new FileOutputStream(file);
	wb.write(os);
	wb.close();
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

@Autowired
MajorMapper mm;
@Autowired
JmsTemplate jmsTemplate;
@Autowired
Destination queueTextDestination;

//Mq
	@RequestMapping("mqmq")
	public void mqmq(HttpServletRequest request, HttpServletResponse response,Major jor) {
		JSONObject result = new JSONObject();
		   mm.insert(jor);
		   jmsTemplate.convertAndSend(queueTextDestination,JSONObject.toJSONString(jor));
			result.put("success", true);
		
		 
				
		WriterUtil.write(response, result.toString());
	}
}
