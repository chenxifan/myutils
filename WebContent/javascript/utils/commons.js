// commons.js

// 打开独立的窗口
function openDialogWindow(width, height, url) {
	var top = (window.screen.availHeight - height) / 2;
	var left = (window.screen.availWidth - width) / 2;
	var opt = "height="+ height +", width="+ width +", top="+top+", left="+left
			+ ",toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no";
			
	var newWin = window.open(url, "", opt);
	newWin.focus();
	return newWin;
}

//调用 /pps/js/utils/dialog.js 的 showDialog(url,argument,para,funName,funPara)函数，可以传入回调函数
function openDialogWindowOfCallback(width, height, url, callbackFunName, callbackFunParam) {

	var top = (window.screen.availHeight - height) / 2;
	var left = (window.screen.availWidth - width) / 2;
	
	var ops = "dialogHeight:" + height + "px";
	ops += ops + ";dialogWidth:" + width + "px";
	ops += ops + ";center:yes;resizable:yes;status:no;toolbar=no;menubar=no;scrollbars=no;resizable=no;location=no;"+ "top=" + top + ";left=" + left + ";";
	showDialog(url,null,ops,callbackFunName, callbackFunParam);
}


// æå¼ç¬ç«çå¯ä»¥æ»å¨ççªå£
function openDialogScrollWindow(width, height, url) {
	var top = (window.screen.availHeight - height) / 2;
	var left = (window.screen.availWidth - width) / 2;
	var opt = "height="+ height +", width="+ width +", top="+top+", left="+left
			+ ",toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no";
			
	var newWin = window.open(url, "", opt);
	newWin.focus();
	return newWin;
}

//打开可调节大小的对话框
function openDialogResizeableWindow(width, height, url) {
	var top = (window.screen.availHeight - height) / 2;
	var left = (window.screen.availWidth - width) / 2;
	var opt = "height="+ height +", width="+ width +", top="+top+", left="+left
			+ ",toolbar=no, menubar=no, scrollbars=yes, resizable=yes, fullscreen=no,location=no, status=no";
	var newWin = window.open(url, "", opt);
	newWin.focus();
	
	return newWin;
}

//打开流程表单对话框
function openFlowDialogWindow(url) {
	var width = 900;
	var height = 650;
	
	var top = (window.screen.availHeight - height) / 2;
	var left = (window.screen.availWidth - width) / 2;
	var opt = "height="+ height +", width="+ width +", top="+top+", left="+left
			+ ",toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no";
	var newWin = window.open(url, "", opt);
	newWin.focus();
	return newWin;
}

/*
 * 职责：打开模态对话框
 * args：
 * 可选参数，类型：变体。用来向对话框传递参数。传递的参数类型不限，包括数组等。
 * args.isMutilSelect：默认是false(单选)，在获取弹出模态对话框中列表选择框时，指定是否允许多选；
 * （args参数继续补充）
 */
function openModalWindow(width, height, url, args) {
	var setting = "dialogWidth="+ width +"px;dialogHeight="+ height +"px;status=no;help=no;scrollbars=no";
	var returnValue = window.showModalDialog(url, args, setting);
    return returnValue;
}

/**
 * 此方法用于 弹出模态对话框中列表选择框的获取
 * 参数：dataGridId指定页面中需要校验的列表
 * 返回：一个数组对象，数组包含一个或多个easyui的row对象
 * 注意：为了实现多选的判断，则要对showModalDialog方法中的arg参数设置args.isMutilSelect=true
 */
function ok(dataGridId) {
	var checkeds = null;
	
	if (dataGridId != undefined) {
		checkeds = $("#" + dataGridId).datagrid("getChecked");
	} else {
		checkeds = $(".easyui-datagrid").datagrid("getChecked");
	}
    
	if (checkeds.length == 0) {
		alert("请选择记录");
		return;
	}
	
	var args = window.dialogArguments;
	//alert(args.isMutilSelect);
	
	//args对象非空校验
	if (!args) {
		args = new Object();
	}
	
	//isMutilSelect属性校验
	if (args.isMutilSelect == undefined) {
		//默认是单选
		args.isMutilSelect = false;
	}
	
	//单选、复选校验
	if (args.isMutilSelect == false) {
		if (checkeds.length > 1) {
			alert("只能选择一条记录");
			return;
		}
	}
	
    window.returnValue = checkeds;
    window.close();
}

/**
 *  此方法只适用于 easyui page
 *  把id传递到后台
 */
function editBySelected(w,h,url,id) {
	var checkeds;
	if (id != undefined) {
		checkeds = $("#"+id).datagrid("getChecked");
	} else {
		checkeds = $(".easyui-datagrid").datagrid("getChecked");
	}

	if (checkeds.length == 0) {
		alert("请选择记录");
		return;
	}
	if (checkeds.length > 1) {
		alert("只能选择一条记录");
		return;
	}
	var id = checkeds[0].id;
	url += "&id=" + id;
	openDialogWindow(w,h,url);
}

/**
 * 此方法只适用于 easyui page
 * 必须有id属性 ，后台用ids接收id 成功删除后 返回一字符串 true  
 */
function deleteBySelected(id,url) {
	var checkeds = $("#" + id).datagrid("getChecked");
	if (checkeds.length > 0 ) {
		if (!confirm("是否确定删除?")) return;
		var ids = "";
		$.each(checkeds,function (index,checked){
			ids += checked.id + ",";
		});
		url += "&ids=" + ids;
		$.ajax({url:url , success : function (data){
			if (data == "true") {
				alert("删除成功");
				$("#" + id).datagrid("reload");
			} else {
				alert("删除失败,请联系管理员");
			}
		}});
	} else {
		alert("请选择记录");
	}
}

/**
 *  分页重载
 *  jqueryFormObj form的jquery对象
 */
function queryDatagrid(jqueryFormObj,id) {
	if (id) {
		$("#"+id).datagrid("load",serializeForm(jqueryFormObj));
	} else {
		$(".easyui-datagrid").datagrid("load",serializeForm(jqueryFormObj));
	}
}

/* 
 * 将form表单元素的值序列化成对象
 * 常用于表单查询
 * 参数form：jquery对象
 */
function serializeForm(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

/*
	jsonData:datagrid??????????
	datagridId?datagrid?id
	loadUrl?datagrid?url??
*/
function initDatagrid(jsonData,datagridId,loadUrl){
	
	jsonData = jsonData.replace(/&#034;/gi,'"');

	var jsonObj = jQuery.parseJSON(jsonData);

	$("#"+datagridId).datagrid("loadData",jsonObj);
	
	var options = $("#"+datagridId).datagrid("options");

	var loader = options.loader;

	options.loader = function(){
		return false;
	}
	
	options.url = loadUrl;

	options.onBeforeLoad = function(){
		options.loader = loader;
	};
}

/**
 *  获取临时附件的 sequenceNo
 */
function getTempAttach(id) {
	var datas = $("#" + id).datagrid("getData").rows;
	var sequenceNo = new Array();
	if (datas != undefined) {
		$.each(datas,function (index,data){
			if (data.sequenceNo != undefined && ($.inArray(data.sequenceNo,sequenceNo) == -1)) {
				sequenceNo.push(data.sequenceNo);
			}
		});
	}
	return sequenceNo;
}


/**
 *  删除列表的行
 *  id：datagrid的id
 */
function deleteDatagridRow(id) {
	var checkeds = $("#" + id).datagrid("getChecked");
	if (checkeds.length == 0) {
		alert("请选择需要删除的记录");
		return;
	}
	if (confirm("是否确定删除")) {
		for (var i=checkeds.length -1;i>=0;i--) {
			var checked = checkeds[i];
			var index = $("#" + id).datagrid("getRowIndex",checked);
			$("#" + id).datagrid("deleteRow",index);
		}
		return checkeds;
	}
}


/**
 * 初始化年份
 * n 前后 n年
 */
function initYear(n,id,obj) {
	var nowDate = new Date();
	var nowYear = nowDate.getFullYear();
	var datas = new Array();
	if (obj) datas.push(obj);
	for (var i=nowYear - n; i <= (nowYear + n) ; i++) {
		var obj = {value : i , text : i};
		datas.push(obj);
	}
	$("#" + id).combobox("loadData",datas);
}

/**
 *  初始化月份
 */
function initMonth(id,obj) {
	var datas = new Array();
	if (obj) datas.push(obj);
	for (var i=1;i<=12;i++) {
		var month = i < 10 ? "0" + i : i;
		datas.push({value : month , text : month});
	}
	$("#" + id).combobox("loadData",datas);
}

/**
 *  设置下拉框长度
 * width : 长度
 * index : 第index个组件
 * 组件的id
 */
function setComboWidth(width,index,id){
	if ($.type(index) == "number") {
		$(".combo-text")[index].style.width = width;
		if (id) {
			$("#" + id).css("width",width);
			$("#" + id).combobox({panelWidth : width+20});
		}
	} else {
		$.each($(".combo-text"),function (index,data) {
			data.style.width = width;
		});
	}
}

/**
 *  设置datagrid 的 editor 的高度
 */
function setEditorHeight(obj,rowIndex,height) {
	var editors = $(obj).datagrid("getEditors",rowIndex);
	$.each(editors,function (i,editor){
		editor.target.css("height",height);
	});
}
/**
 * 格式化一个节目详细信息链接
 */
function renderProgramName(value,row)
{
	return '<a href="javascript:programDetailView('+row.id+')">'+value+'</a>';
}


/**
 * 格式化一个节目详细信息链接，使用programId
 */
function renderProgramNamebyProgramId(value,row)
{
	return '<a href="javascript:programDetailView('+row.programId+')">'+value+'</a>';
}
/**
 * 格式化一个节目购买详细信息链接
 */
function renderProgramBuyName(value,row)
{
	return '<a href="javascript:programBuyDetailView('+row.id+')">'+value+'</a>';
}

/**
 * 打开节目购买详细信息的公共方法
 */
function programBuyDetailView(id) {
	if(id=='undefined'||id==''||id==null){
		alert("没有选中剧目.");
		return
	}
    var width = window.screen.availWidth;
	var height = window.screen.availHeight;
	var url = "/pps/proBuyPlan.do?method=proBuyPlanView&id="+id;
	openDialogScrollWindow(width, height, url);
}

/**
 * 打开节目详细信息的公共方法
 */
function programDetailView(id) {
	if(id=='undefined'||id==''||id==null){
		alert("没有选中剧目或者剧目没有注册到系统中！");
		return
	}
    var width = window.screen.availWidth;
	var height = window.screen.availHeight;
	var url = "/pps/program/programInfo.do?method=programInfoView&id="+id;
	openDialogScrollWindow(width, height, url);
}

/**
 *  设置 列表编辑框 高度
 */
function initEditorHeight(height) {
	if (!height) height = 24;
	//设置 列表编辑框 高度 开始
	$(".datagrid-row-editing").each(function (index,edit){
		edit.style.height = height;
	});
	$(".datagrid-editable").each(function (index,edit){
		edit.style.padding = "0px";
		$(edit).children().attr("cellPadding",0);
		$(edit).children().children().children().children().attr("height",height);
	});
	//设置 列表编辑框 高度 结束
}


/**
 *  按数字大小排序
 */
function sortNumber(a,b) {
	return (a-b);
}

/**
 *  按字符串大小排序
 */
function sortString(a,b) {
	if (a>b) {
		return 1;
	} else if (a<b) {
		return -1;
	} else {
		return 0;
	}
}


/**
 * 此方法只适用于 easyui page
 * 必须有id属性 ，后台用ids接收id 成功删除后 返回一字符串 true  
 */
function getDatagridMore(id) {
	var checkeds = $("#" + id).datagrid("getChecked");
	var ids = "";
	if (checkeds.length > 0 ) {
		$.each(checkeds,function (index,checked){
			ids += checked.id + ",";
		});
	} else {
		alert("请选择记录");
	}
	return ids;
}

/**
 * 获取供应商的联系人信息 并且把数据注入到combobox组建中
 * associaterId 合作伙伴id
 * comboboxId 下拉组建id
 * linkmanName 联系人
 */
function loadAssociaterContactDatas(associaterId,comboboxId,linkmanName,phoneCodeId,isSelect) {
	var url = "/pps/basedata/associater/associaterInfo.do?method=loadAssociaterContactDatas&associaterId=" + associaterId;
	$.getJSON(url,function (datas){
		if (datas) {
			$("#" + comboboxId).combobox({
	    	valueField: "id" ,
	    	textField: "text" ,
	    	onSelect : function (record) {
	    		var text = record.text;
	    		for (var i=0;i<datas.length;i++) {
		    		var data = datas[i];
		    		if (text == data.text) {
		    			$("#" + phoneCodeId).val(data.phoneCode);
		    			break;
		    		}
		    	}
	    	}});
	    	var arr = new Array();
	    	for (var i=0;i<datas.length;i++) {
	    		var data = datas[i];
	    		var obj = new Object();
	    		obj["id"] = data.text;
	    		obj["text"] = data.text;
	    		obj["conId"] = data.id;
	    		if (i == 0) {
	    			obj["selected"] = true;
	    			if (isSelect != undefined) {
	    				$("#" + phoneCodeId).val(data.phoneCode);
	    			}
	    		}
	    		arr.push(obj);
	    	}
	    	$("#" + comboboxId).combobox("loadData",arr);
		}
	});
	$("#" + comboboxId).combobox("setValue",linkmanName);
}



