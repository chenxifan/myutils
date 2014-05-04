<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html:html>
<HEAD>
<TITLE>文件上传界面</TITLE>

<link rel="stylesheet" type="text/css" href="<c:url value='/styles/purple/basePage.css'/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value='/js/fancyupload2/multiFileUpload.css'/>"></link>
<script language="JavaScript" type="text/javascript" src="<c:url value='/js/fancyupload2/mootools-1.2-core-nc.js'/>"></script>
<script language="Javascript" type="text/javascript" src="<c:url value='/js/fancyupload2/Swiff.Uploader.js'/>"></script>
<script language="Javascript" type="text/javascript" src="<c:url value='/js/fancyupload2/Fx.ProgressBar.js'/>"></script>
<script language="Javascript" type="text/javascript" src="<c:url value='/js/fancyupload2/FancyUpload2.js'/>" charset="utf-8"></script>
<SCRIPT type="text/javascript">

//是否允许多选
var isMultiple = <bean:write name="isMultiple"/>;

//单独一个文件的大小限制
var singleFileSizeMax = <bean:write name="singleFileSizeMax"/>;

//所有上传文件的大小总和
var maxTotalFilesSize = <bean:write name="maxTotalFilesSize"/>;

//允许最少上传的文件数
var minFiles = <bean:write name="minFiles"/>;

//允许最多上传的文件数
var maxFiles = <bean:write name="maxFiles"/>;

//可选文件格式过滤
var typeFilter = <bean:write name="typeFilter" filter="false" />;

//上传文件序列号，用于标识这组文件在数据库临时表的标识
var sequenceNo = '<bean:write name="sequenceNo"/>';

//服务器接收文件的路径
var uploadURL = "/upload/uploadFiles?sequenceNo=" + sequenceNo;

//文件上传控件对象
var swiffy = null;

//flash控件存放路径
var swiffyPath = '<c:url value="/js/fancyupload2/Swiff.Uploader.swf"/>';

window.addEvent('domready', function() {
	if(flashCheck()){
		swiffy = new FancyUpload2($('progress-status'), $('myupqueue'), {
		    target: 'selectFileField',
		    url : uploadURL,
			path : swiffyPath,
			multiple: isMultiple,	
			limitSize : maxTotalFilesSize,
			limitFiles : maxFiles,
			typeFilter: typeFilter,
			validateFile : function(file){
				//单独校验每个文件的大小
				return (file.size <= singleFileSizeMax);
			},
			fileInvalid: fileInvalidHandler,
			onSelect: onSelectHandler,
			onComplete : function (file, response) {
			    if (!isMultiple) {
			        //如果是单选，则不会抛出onAllComplete事件
					completeUploadFiles();
			    }
			},
			onAllComplete : function(current){
				completeUploadFiles();
			}	
		});
	}
	
	//初始已经上传的文件
	if(parent.initUploadFiled){
		parent.initUploadFiled();
	}

	//判断是否隐藏“下载”按钮,一般在编辑界面显示，在新增界面隐藏
	if(!parent.isEditPage){
		 document.getElementById("fileDown").style.display= "none";
	}else{
		//初始化下载按钮的URL
		var downEle = document.getElementById("fileDown");
		downEle.href= parent.downParams.url;
	}

});

function fileInvalidHandler(caller, errors) {
    var errorMsg = "系统提示：\n\n";
    for (var i=0; i<errors.length; ++i) {
	    switch (errors[i]) {
	    	case 'size':
	    	    errorMsg +=  "[" +(i+1) +"] 文件总大小将超过 " + this.sizeToKB(maxTotalFilesSize) + " 限制 \n";
	    	    break;
	    	case 'length':
	    	    errorMsg +=  "[" +(i+1) +"] 文件数将超过 " + maxFiles + " 个的限制 \n";
	    	    break;
	    	case 'duplicate':
	    	    errorMsg +=  "[" +(i+1) +"] 只允许单选 \n";
	    	    break;
	    	case 'custom':
	    	    errorMsg +=  "[" +(i+1) +"] 单个文件(" + this.sizeToKB(caller.size) + ")大小超过 " + this.sizeToKB(singleFileSizeMax) + " 的限制 \n";
	    	    break;
	    	default:
	    	    errorMsg +=  "[" +(i+1) +"] 未知错误，请联系管理员 \n";
	    	    break;
    	}
    }
    alert(errorMsg);
}

function onSelectHandler() {
    document.getElementById('hahaupload').style.border = "1px solid #7F9DB9";
}

function completeUploadFiles() {
	if(parent.completeUploadFiles != null){
		parent.completeUploadFiles(sequenceNo);
	}
}

function executeUpload() {
	if (!uploadCheck()) {
	    return false;
	}
	
	if (swiffy.files.length == 0) {
	    return false;
	}
	
	$('progress-status').removeClass('hide');
	swiffy.upload();
	return true;
}

function uploadCheck() {
    if (swiffy.files.length < minFiles) {
	    document.getElementById('hahaupload').style.border = "1px solid #ab0f52";
		if(arguments[0]){
			alert(arguments[0]);
		}else{
			alert('请选择要上传的文件！');
		}		
		return false;
	}
	
	//限制上传张数
	if(swiffy.files.length > maxFiles){		
		alert("您选择的文件数量超过了 " + maxFiles + " 个，请删除 " + (swiffy.files.length - maxFiles) + " 个文件后再上传.");
		return false;
	}
	
	return true;
}

function needUpload() {
   return (swiffy.files.length > 0);
}

<%--flash版本检查--%>
function flashCheck(){
	var flashVrn=Browser.Plugins.Flash.version;
	var flashBuild=Browser.Plugins.Flash.build;
	if(flashVrn<10){
		versionTip();
		return false;
	}else{
		return true;
	}
}
	
<%--版本信息提示--%>
function versionTip(){
	var flashVrn=Browser.Plugins.Flash.version;
	if(flashVrn<10)
	{
		flashVrn = 10;
	}	
	var flashBuild=Browser.Plugins.Flash.build;
	var msg="<font size=\"3\"><b>您当前浏览器安装的flash插件的版本偏低["+flashVrn+"."+flashBuild+"]，从而造成无法正常使用多文件上传！<a href=\"/pps/js/fancyupload2/Flash_Player_10_ActiveX.rar\" target=\"_self\">请点击这里，下载安装程序！</a></b></font>";
	var spnEl=new Element('div',{
		'html':msg,
		'styles':{'color':'red'}
	});
	spnEl.inject($('mysub'),'after');
}

</SCRIPT>
</HEAD>

<body style="margin:0px; padding:0px;">

	<c:if test="${not empty attachType}">
	<span  style="height:18px; margin:0px 0px 2px 2px;"><label for="selectType"><c:out value="${attachType.typeLabel}"/>:</label></span>
	<select id="<c:out value='${attachType.typeName}'/>" name="<c:out value='${attachType.typeName}'/>" style="margin:0px 5px 3px 2px; width:80px;">
		<c:forEach items="${attachType.options}" var="option">
			<option value="<c:out value='${option.value}'/>"><c:out value="${option.name}"/></option>
		</c:forEach>
	</select>
	</c:if>

	<button id="selectFileField" style="margin:0px 0px 2px 0px;">选择文件</button>
	
	<div style="border:1px solid #7F9DB9; width:<bean:write name="width"/>; height:<bean:write name='height'/>; overflow: auto;" id="hahaupload">
		<div id="progress-status" class="hide">
			<div>
				<strong class="overall-title">总进度</strong><br />
				<img src="/pps/js/fancyupload2/image/bar.gif" class="progress overall-progress" />
			</div>
			<div>
				<strong class="current-title">文件进度</strong><br />
				<img src="/pps/js/fancyupload2/image/bar.gif" class="progress current-progress" />
			</div>
			<div class="current-text"></div>
		</div>
		<ul id="myupqueue">				
		</ul>
		<a id="fileDown" class="down">下载</a>
	</div>
	
</body>

</html:html>