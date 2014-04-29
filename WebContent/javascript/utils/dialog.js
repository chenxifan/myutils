/*
    dialog.js
    ====================================
    Create and use a dialog window
    Cross browser version
    By Giuliano Cesar <giulianojordao@yahoo.com.br>
*/
var Dialog_modal;
var Dialog_returnValue;
var Dialog_arguments;
var Dialog_funName;
var Dialog_funPara;
function DialogFunClear()
 {
  Dialog_modal = "";
  Dialog_returnValue = "";
  Dialog_arguments = "";
  Dialog_funName = "";
  Dialog_funPara = "";
 }
function parentEvent(ev) 
  {
     if (Dialog_modal && !Dialog_modal.closed) 
     {
      Dialog_modal.focus();
      ev.preventDefault();
      ev.stopPropagation();
      }
  };

function relwin(w) 
 {
   w.removeEventListener("mousedown", parentEvent, true);
   w.removeEventListener("click", parentEvent, true);
  };

function capwin(w) 
 {
    w.addEventListener("click", parentEvent, true);
    w.addEventListener("mousedown",parentEvent, true);
  };
//0:IE, 1:firefox
function browseType()
{
 var type = navigator.userAgent;
 if(type.indexOf("MSIE")!= -1) 
  return 0;
 else 
  return 1; 
}

function getDialogString(f_String,repString)
 {
  var t_String = f_String;
  var returnValue = 0;
  if(t_String.toLowerCase().indexOf(repString.toLowerCase())==-1)
  {
    return 0;
  }  
  else
  {
   var begin = t_String.toLowerCase().indexOf(repString.toLowerCase()) + repString.length;
   t_String = t_String.substring(begin,t_String.length);
   returnValue = t_String.substring(0,t_String.indexOf(";"));   
  }
  return returnValue;
 }


function urlProc(url)
{
 var t_string = ",toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, statu s=no";
 var t_width = getDialogString(url,"dialogWidth:");
 var t_height = getDialogString(url,"dialogHeight:");
 t_value = "height=" + t_height + ", width=" + t_width + t_string;   
 return t_value;
}

function showDialog(url,argument,para,funName,funPara)
{
 DialogFunClear();
 Dialog_funName = funName; 
 Dialog_funPara = funPara;
 //firefox process
 if(browseType()==1)
 {
  Dialog_arguments = argument;
  para = urlProc(para);
  Dialog_modal = window.open(url,"center",para);
  capwin(window);
  for (var i = 0; i < window.frames.length; i++)
    capwin(window.frames[i]);
   if(window.opener)
  {
   capwin(window.opener);
    for(var i = 0; i < window.opener.frames.length; i++)
    {
     capwin(window.opener.frames[i]);
    }
  }  
  if(!Dialog_modal.closed)
   {
    setTimeout("delyTime()",1000); 
   }
  } 
 else
 {
  //IE Browse Process
  Dialog_modal = window.showModalDialog(url,argument,para); 
  dialogAction();
 }
 
}
//delay time
function delyTime()
{
 if(!Dialog_modal.closed)
  {
  //alert("delay");
   setTimeout("delyTime()",1000);
  }
 else
  {
     //process 
    dialogAction();
    //clear event
    relwin(window);
    for(var i = 0; i < window.frames.length; i++)
    relwin(window.frames[i]);
    if(window.opener)
    {
     relwin(window.opener);
      for(var i = 0; i < window.opener.frames.length; i++)
      {
       relwin(window.opener.frames[i]);
      }
     }
    //clear  para
    Dialog_modal = null;
    Dialog_arguments = null;
  } 
}

function getDialogReturnValue()
{
  if(browseType()==1)
  {
   return Dialog_returnValue;
  }else
  {
   return Dialog_modal;
  }
}

function setDialogReturnValue(returnValue)
{
 if(browseType()==1)
  {
   //window.opener.Dialog_returnValue = returnValue;
   top.window.opener.Dialog_returnValue = returnValue;
  }
  else
  {
   window.returnValue = returnValue;
  }

}

function getDialogArgument()
{
  if(browseType()==1)
  {
   return window.opener.Dialog_arguments;
  }
  else
  {
   return window.dialogArguments;
  }
}

function dialogAction()
{
 if(Dialog_funName!="")
 if(Dialog_funName)
  {
   var count = getParaCount();
   var value0,value1,value2,value3,value4;
   value0 = getParaItem(0);
   value1 = getParaItem(1);
   value2 = getParaItem(2);
   value3 = getParaItem(3);
   value4 = getParaItem(4); 
   switch(count)
    { 
    case 1: 
    Dialog_funName(value0);
    break; 
    case 2: 
    Dialog_funName(value0,value1);
    break; 
    case 3: 
    Dialog_funName(value0,value1,value2);
    break; 
    case 4: 
    Dialog_funName(value0,value1,value2,value3);
    break; 
    case 5: 
    Dialog_funName(value0,value1,value2,value3,value4);
    break; 
    default: 
    Dialog_funName();
    Dialog_funName = null;
   }
 }
}
function getParaCount()
{
 if(Dialog_funPara != "")
 { 
  var returnValue = 1;
  var tempValue = Dialog_funPara;
  while(tempValue.indexOf(",")!=-1)
  {
   returnValue ++
   tempValue = tempValue.substring(tempValue.indexOf(",")+1,tempValue.length);
  }
  return returnValue; 
 }
else
 {
  return 0;
 }
}

function getParaItem(index)
 {
  var tempValue = Dialog_funPara;
  var returnValue = null;
  var count = getParaCount();
  if(index < 0)
  {
    alert("越界!!!");
  }
  if(index>=0 && index<count)
  {
   var j = 0;
   while(j != index)
   {
     j++; 
     tempValue = tempValue.substring(tempValue.indexOf(",")+1,tempValue.length);
    }
   if(j!= count -1)
    returnValue = tempValue.substring(0,tempValue.indexOf(",")); 
   else
    returnValue = tempValue;
  }
  return returnValue;  
 }


//vb的MsgBox
//	strMsg:显示的信息
//	iType:对话框按钮和图标	0--"确定",1--"确定"和"取消"，2--"终止" "重试" "忽略"	3--"确定" "否" "取消" 4--"是" "否" 5--"重试" "取消";加上 64提问图标 48 信息图标
//	strCaption:对话框标题
//if (browseType()==0){
//	document.write('<SCRIPT LANGUAGE=VBScript\> \n');
//	document.write('function  vbMsgBox(strMsg,iType,strCaption)\n');
//	document.write('  dim iRet\n');
//	document.write('  iRet = 0\n');
//	document.write('  iRet = MsgBox(strMsg,iType,strCaption)\n');
//	document.write('  vbMsgBox = iRet\n');
//	document.write('end function\n');
//	document.write('</SCRIPT\> \n');
//}

//function showMsgBox(strMsg,iType,strCaption){
//	if (browseType()==0){
//		return vbMsgBox(strMsg,iType,strCaption);
//	}else{
//		if (
//	}
//	return 0;
//}