<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户主页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ include file="/WEB-INF/common.jsp"%>

<link
	href="${path }/resources/css/plugins/bootstrap-table/bootstrap-table.min.css"
	rel="stylesheet">
<link href="${path }/resources/css/animate.css" rel="stylesheet">
<link href="${path }/resources/css/style.css?v=4.1.0" rel="stylesheet">

</head>
<body class="gray-bg">
	<div class="panel-body">
		<div id="toolbar" class="btn-group">
			<c:forEach items="${operationList}" var="oper">
				<privilege:operation operationId="${oper.operationid }" id="${oper.operationcode }" name="${oper.operationname }" clazz="${oper.iconcls }"  color="#093F4D"></privilege:operation>
			</c:forEach>
        </div>
             <div class="row">
			 <div class="col-lg-2">
				<div class="input-group">
			      <span class="input-group-addon">学生姓名 </span>
			      <input type="text" name="name" class="form-control" id="txt_search_username" >
				</div>
			  </div>
			   <div class="col-lg-2">
				<div class="input-group">
					<span class="input-group-addon">所属公司</span>
					<select class="form-control" name="dId" id = "txt_search_roleid">
						<option value="0">---请选择---</option>
			  <c:forEach items="${deptList }" var="d">
			  <option value="${d.dId }">${d.bname }</option>
										</c:forEach>
                	</select>
				</div>
			 </div>
			 
            <button id="btn_search" type="button" class="btn btn-default">
            	<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
            </button>
	  	</div>
        
        <table id="table_user"></table>
		

	
		
	<!-- 新增和修改对话框 -->
	<div class="modal fade" id="xzxg" role="dialog" aria-labelledby="modal_user_edit" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body"> 
					<form id="form_emp" method="post" action="reserveEmp.htm" enctype="multipart/form-data" >
						<input type="hidden" name="id" id="hidden_txt_userid" value=""/>
                   <input type="hidden" name="img" id="pic" value=""/>
						<table style="border-collapse:separate; border-spacing:0px 10px;">
							<tr>
								<td>员工姓名</td>
								<td><input type="text" id="name" name="name"
									class="form-control" aria-required="true" required/></td>
								<td>&nbsp;&nbsp;</td>
							
								
								<td>员工生日</td>
								<td><input type="date" id="time" name="time"
									class="form-control" aria-required="true" required/></td>
							</tr>
		                    <tr>
							<td>员工性别</td>
								<td>
								<input type="radio" name="sex" id="sex" value="男"/>男
								<input type="radio" name="sex" id="sex" value="女"/>女</td>
						
							</tr>
								 <tr>
								
								
							
								<td>&nbsp;&nbsp;</td>
								<td>头像：</td>
								<td>
								<input type="file" name="file">
								<img id="pics" width="50px" height="50px">
								
								</td>
							</tr>
							<tr>
							
								<td>部门：</td>
								<td colspan="4">
									<select class="form-control" name="dId" id = "dId" aria-required="true" required>
										<option value="">---请选择---</option>
										<c:forEach items="${deptList }" var="d">
										 	<option value="${d.dId }">${d.bname }</option>
										</c:forEach>
				                	</select>
								</td>
							</tr>
							
						</table>
						
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"  id="submit_form_user_btn">保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</form>

				</div>
				
			</div>

		</div>

	</div>
	<!-- 导入对话框 -->
	<div class="modal fade" id="modal_user_daoru" role="dialog" aria-labelledby="modal_user_edit" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<form id="form_dr" method="post" action="daoru.htm" enctype="multipart/form-data">
						
						<table style="border-collapse:separate; border-spacing:0px 10px;">
							<tr>
								<td>图片：</td>
								<td>
								<input type="file" name="file">
								</td>
							</tr>
						</table>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"  id="submit_form_user_daoru">导入</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<!--删除对话框 -->
	<div class="modal fade" id="modal_user_del" role="dialog" aria-labelledby="modal_user_del" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					 <h4 class="modal-title" id="modal_user_del_head"> 刪除  </h4>
				</div>
				<div class="modal-body">
							删除所选记录？
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-danger"  id="del_user_btn">刪除</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
			</div>
		</div>
	</div>
		<!--线图 -->
	<div class="modal fade" id="xian" role="dialog" aria-labelledby="modal_user_del" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				
				<div class="modal-body" id="xiantu" style="width:500px;height:500px">
							
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
			</div>
		</div>
	</div>
	
	<!--柱状图 -->
	<div class="modal fade" id="zhu" role="dialog" aria-labelledby="modal_user_del" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				
				<div class="modal-body" id="zhutu" style="width:500px;height:500px">
							
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
			</div>
		</div>
	</div>
	
	<!--饼状图 -->
	<div class="modal fade" id="bing" role="dialog" aria-labelledby="modal_user_del" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				
				<div class="modal-body" id="bingtu" style="width:500px;height:500px">
							
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
			</div>
		</div>
	</div>
	

	
	
	<div class="ui-jqdialog modal-content" id="alertmod_table_user_mod"
		dir="ltr" role="dialog"
		aria-labelledby="alerthd_table_user" aria-hidden="true"
		style="width: 200px; height: auto; z-index: 2222; overflow: hidden;top: 274px; left: 534px; display: none;position: absolute;">
		<div class="ui-jqdialog-titlebar modal-header" id="alerthd_table_user"
			style="cursor: move;">
			<span class="ui-jqdialog-title" style="float: left;">注意</span> <a id ="alertmod_table_user_mod_a"
				class="ui-jqdialog-titlebar-close" style="right: 0.3em;"> <span
				class="glyphicon glyphicon-remove-circle"></span></a>
		</div>
		<div class="ui-jqdialog-content modal-body" id="alertcnt_table_user">
			<div id="select_message"></div>
			<span tabindex="0"> <span tabindex="-1" id="jqg_alrt"></span></span>
		</div>
		<div
			class="jqResize ui-resizable-handle ui-resizable-se glyphicon glyphicon-import"></div>
	</div>
	
	<!-- Peity-->
	<script src="${path }/resources/js/plugins/peity/jquery.peity.min.js"></script>
	
	<!-- Bootstrap table-->
	<script src="${path }/resources/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
	<script src="${path }/resources/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

	<!-- 自定义js-->
	<script src="${path }/resources/js/content.js?v=1.0.0"></script>
	<script src="${path }/resources/js/echarts.min.js"></script>
	 <!-- jQuery Validation plugin javascript-->
    <script src="${path }/resources/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${path }/resources/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${path }/resources/js/echarts.min.js"></script>
   
   	<!-- jQuery form  -->
    <script src="${path }/resources/js/jquery.form.min.js"></script>
    <script src="${path }/resources/js/echarts.min.js"></script>
    
	<script type="text/javascript">
 	<!--  时间插件  -->
	Date.prototype.Format = function (fmt) {
	    var o = {  
	        "M+": this.getMonth() + 1, //月份   
	        "d+": this.getDate(), //日   
	        "H+": this.getHours(), //小时   
	        "m+": this.getMinutes(), //分   
	        "s+": this.getSeconds(), //秒   
	        "S": this.getMilliseconds() //毫秒   
	    };  
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
	    for (var k in o)  
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));  
	    return fmt;  
	};
	
	$(function () {
	    init();
	    $("#btn_search").bind("click",function(){
	    	//先销毁表格  
	        $('#table_user').bootstrapTable('destroy');
	    	init();
	    }); 
	    var validator = $("#form_emp").validate({
    		submitHandler: function(form){
   		      $(form).ajaxSubmit({
   		    	dataType:"json",
   		    	success: function (data) {
   		    		
   		    		if(data.success && !data.errorMsg ){
   		    			
   		    			$('#xzxg').modal('hide');
   		    			$("#btn_search").click();
   		    		}else{
   		    			$("#select_message").text(data.errorMsg);
   		    			$("#alertmod_table_user_mod").show();
   		    		}
                }
   		      });     
   		   }  
	    });
	  
	   //导入
	    var validator = $("#modal_user_daoru").validate({
    		submitHandler: function(form){
   		      $(form).ajaxSubmit({
   		    	dataType:"json",
   		    	success: function (data) {
   		    		
   		    		if(data.success && !data.errorMsg ){
   		    			validator.resetForm();
   		    			$('#form_daoru').modal('hide');
   		    			$("#btn_search").click();
   		    		}else{
   		    			$("#select_message").text(data.errorMsg);
   		    			$("#alertmod_table_user_mod").show();
   		    		}
                }
   		      });     
   		   }  
	    });
	    
	    $("#submit_form_user_btn").click(function(){
	    	$("#form_emp").submit();
	    });
	    $("#submit_form_imp_btn").click(function(){
	    	$("#form_imp").submit();
	    });
	    //导入弹框
	    $("#submit_form_user_daoru").click(function(){
	    	alert(1)
	    	$("#form_dr").submit();
	    });
	});
	
	var init = function () {
		//1.初始化Table
	    var oTable = new TableInit();
	    oTable.Init();
	    //2.初始化Button的点击事件
	    var oButtonInit = new ButtonInit();
	    oButtonInit.Init();
	};
	
	var TableInit = function () {
	    var oTableInit = new Object();
	    //初始化Table
	    oTableInit.Init = function () {
	        $('#table_user').bootstrapTable({
	            url: 'empList.htm',         //请求后台的URL（*）
	            method: 'post',                      //请求方式（*）
	            contentType : "application/x-www-form-urlencoded",
	            toolbar: '#toolbar',                //工具按钮用哪个容器
	            striped: true,                      //是否显示行间隔色
	            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	            pagination: true,                   //是否显示分页（*）
	            sortable: true,                     //是否启用排序
	            sortName: "id",
	            sortOrder: "desc",                   //排序方式
	            queryParams: oTableInit.queryParams,//传递参数（*）
	            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	            pageNumber:1,                       //初始化加载第一页，默认第一页
	            pageSize: 10,                       //每页的记录行数（*）
	            pageList: [10, 25, 50, 75, 100],    //可供选择的每页的行数（*）
	            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	            strictSearch: true,
	            showColumns: true,                  //是否显示所有的列
	            showRefresh: false,                  //是否显示刷新按钮
	            minimumCountColumns: 2,             //最少允许的列数
	            clickToSelect: true,                //是否启用点击选中行
	           // height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
	            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
	            cardView: false,                    //是否显示详细视图
	            detailView: false,                   //是否显示父子表
	            columns: [{
	                checkbox: true
	            },
	            {
	                field: 'id',
	                title: '员工编号',
	                sortable:true
	            },
	            {
	                field: 'name',
	                title: '员工姓名',
	                sortable:true
	            }, 
	            {
	                field: 'sex',
	                title: '员工性别',
	                sortable:true
	            }, 
	           
	            {
	                field: 'img',
	                title: '员工头像',
	                sortable:true,
	                formatter:function(value,row,index){
	                	return "<img  src='/pic/"+value+"' width='50px' height='50px'>";
	                }
	            },
	          
	            {
	                field: 'time',
	                title: '员工生日',
	                sortable:true,
	                formatter:function(value,row,index){
	                	return new Date(value).Format('yyyy-MM-dd');
	                }
	            },{
	                field: 'dename',
	                title: '员工部门',
	                sortable:true
	               
	            }],
	            onClickRow: function (row) {
	            	$("#alertmod_table_user_mod").hide();
	            }
	        });
	    };

	  //得到查询的参数
	    oTableInit.queryParams = function (params) {
	        var temp = {//这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
	            limit: params.limit,   //页面大小
	            offset: params.offset,  //页码
	            name: $("#txt_search_username").val(),
	            dId: $("#txt_search_roleid").val(),
	            start: $("#start").val(),
	            end: $("#end").val(),
	            
	            search:params.search,
	            order: params.order,
	            ordername: params.sort
	        };
	        return temp;
	    };
	    return oTableInit;
	};
	
	
	var ButtonInit = function () {
	    var oInit = new Object();
	    var postdata = {};

	    oInit.Init = function () {
	        //初始化页面上面的按钮事件
	    	$("#xz").click(function(){
	    		document.getElementById("hidden_txt_userid").value='';
	    		$('#xzxg').modal({backdrop: 'static', keyboard: false});
				$('#xzxg').modal('show');
	        });
	        //导入
	  	  $("#btn_daoru").click(function(){
	  	    		$('#modal_user_daoru').modal({backdrop: 'static', keyboard: false});
	  				$('#modal_user_daoru').modal('show');
	  	        });
	  	        //修改
	  	    	$("#btn_edit").click(function(){
	  	    		var getSelections = $('#table_user').bootstrapTable('getSelections');
	  	    		if(getSelections && getSelections.length==1){
	  	    			initEditUser(getSelections[0]);
	  	    			$('#xzxg').modal({backdrop: 'static', keyboard: false});
	  					$('#xzxg').modal('show');
	  	    		}else{
	  	    			$("#select_message").text("请选择其中一条数据");
	  	    			$("#alertmod_table_user_mod").show();
	  	    		}
	  	    		
	  	        });
	  	    	//删除
	  	    	$("#btn_delete").click(function(){
	  	    		var getSelections = $('#table_user').bootstrapTable('getSelections');
	  	    		if(getSelections && getSelections.length>0){
	  	    			$('#modal_user_del').modal({backdrop: 'static', keyboard: false});
	  	    			$("#modal_user_del").show();
	  	    		}else{
	  	    			$("#select_message").text("请选择数据");
	  	    			$("#alertmod_table_user_mod").show();
	  	    		}
	  	        });
	      //线状图
	  	    	$("#btn_xian").click(function(){
	  	    		$('#xian').modal({backdrop: 'static', keyboard: false});
	  	    		  var tb=echarts.init(document.getElementById("xiantu"));
	  	    		     var nums = new Array();
	  	    		     var name = new Array();
	  	    		     $.ajax({
	  	 	    		    url:"getTje.htm",
	  	 	    		    type:"post",
	  	 	    		    success:function(res){
	  	 	    		    	obj = $.parseJSON(res);			
	  	 	    				for(var i=0;i<obj.length;i++){
	  	 	    					nums[i]= obj[i].num;
	  	 	    					name[i]= obj[i].dename;
	  	 	    				}

	  	 	    				option = {
	  	 	    					    xAxis: {
	  	 	    					        type: 'category',
	  	 	    					        data: name
	  	 	    					    },
	  	 	    					    yAxis: {
	  	 	    					        type: 'value'
	  	 	    					    },
	  	 	    					    series: [{
	  	 	    					        data: nums,
	  	 	    					        type: 'line'
	  	 	    					    }]
	  	 	    					};
	  	 	    					tb.setOption(option);
	  	 	    		    }
	  	 	    		});
	  	 				$('#xian').modal('show');
	  	 	        });
	  	    	
	  	    	
	  	    	
	  	    	//柱状图
	  	    	$("#btn_zhu").click(function(){
	  	    		$('#zhu').modal({backdrop: 'static', keyboard: false});
	  	    		 var tb=echarts.init(document.getElementById("zhutu"));
	  		    		
	  		    		//定义人数数组
	  		    		var nums=new Array();
	  		    		//定义部门名数组
	  		    		var dnames=new Array();
	  		    		$.ajax({
	  		    		    url:"getTje.htm",
	  		    		    type:"post",
	  		    		    success:function(res){
	  		    		    	obj = $.parseJSON(res);			
	  		    				for(var i=0;i<obj.length;i++){
	  		    					nums[i]= obj[i].num;
	  		    					dnames[i]= obj[i].dename;
	  		    				}

	  		    				option = {
	  		    					    xAxis: {
	  		    					        type: 'category',
	  		    					        data: dnames
	  		    					    },
	  		    					    yAxis: {
	  		    					        type: 'value'
	  		    					    },
	  		    					    series: [{
	  		    					        data: nums,
	  		    					        type: 'bar',
	  		    					        showBackground: true,
	  		    					        backgroundStyle: {
	  		    					            color: 'rgba(220, 220, 220, 0.8)'
	  		    					        }
	  		    					    }]
	  		    					};



	  		    					tb.setOption(option);
	  		    		    }
	  		    		
	  		    		});
	  					$('#zhu').modal('show');
	  		        });
	  	    	
	  	    	
	  	    	
	  	    	
	  	    	//饼状图
	  	    	$("#btn_bing").click(function(){
	  	    		$('#bing').modal({backdrop: 'static', keyboard: false});
	  	    		  var tb=echarts.init(document.getElementById("bingtu"));
	  		    		
	  		    		//定义人数数组
	  		    		var nums=new Array();
	  		    		//定义部门名数组
	  		    		var dnames=new Array();
	  		    		var zztj=new Array();
	  		    		$.ajax({
	  		    		    url:"getTje.htm",
	  		    		    type:"post",
	  		    		    success:function(res){
	  		    		    	obj = $.parseJSON(res);			
	  		    				for(var i=0;i<obj.length;i++){
	  		    					zztj[i]={value: obj[i].num, name: obj[i].dename};
	  		    				}
	  		    				option = {
	  		    					    series: [
	  		    					        {
	  		    					            type: 'pie',
	  		    					            radius: '65%',
	  		    					            center: ['50%', '50%'],
	  		    					            selectedMode: 'single',
	  		    					            data: zztj
	  		    					        }
	  		    					    ]
	  		    					};
	  		    					tb.setOption(option);
	  		    		    }
	  		    		
	  		    		});
	  					$('#bing').modal('show');
	  		        });
	  	        
	  	    };

	  	    return oInit;
	  	};

	  	
	  	$("#alertmod_table_user_mod_a").click(function(){
	  		$("#alertmod_table_user_mod").hide();
	  	});
	  	//修改回显
	  	function initEditUser(getSelection){
	  		$('#hidden_txt_userid').val(getSelection.id);
	  		$('#name').val(getSelection.name);
	  		$("input[value='"+getSelection.sex+"']").prop('checked',true);
	  		$('#time').val(new Date(getSelection.time).Format('yyyy-MM-dd'));
	  		$('#pics').attr("src","/pic/"+getSelection.pic);
	  		$('#pic').val(getSelection.pic);
	  		
	  		$('#dId').val(getSelection.dId);
	  		
	  		
	  	}
	  	//删除
	  	$("#del_user_btn").click(function(){
	  		var getSelections = $('#table_user').bootstrapTable('getSelections');
	  		var idArr = new Array();
	  		var ids;
	  		getSelections.forEach(function(item){
	  			idArr.push(item.eId);
	  		});
	  		ids = idArr.join(",");
	  		$.ajax({
	  		    url:"deleteEmp.htm",
	  		    dataType:"json",
	  		    data:{"ids":ids},
	  		    type:"post",
	  		    success:function(res){
	  		    	if(res.success){
	  	    			$('#modal_user_del').modal('hide');
	  	    			$("#btn_search").click();
	  	    		}else{
	  	    			$("#select_message").text(res.errorMsg);
	  	    			$("#alertmod_table_user_mod").show();
	  	    		}
	  		    }
	  		});
	  	});
	  	//导出
	  	$("#btn_daochu").click(function(){
	  		$.ajax({
	  		    url:"daochu.htm",
	  		    dataType:"json",
	  		    type:"post",
	  		    success:function(res){
	  		    	alert("导出成功")
	  		    }
	  		});
	  	});

	</script>

</body>
</html>