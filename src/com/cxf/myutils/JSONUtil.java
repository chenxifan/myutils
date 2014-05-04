/*
 * 创建日期 2012-8-1
 */
package com.cxf.myutils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 职责：JSON工具类
 * @author chenxf
 * @version 1.0
 */
public class JSONUtil {

	/**
	 * 将普通的Java对象转成 JSON对象， 转换规则：根据POJO的getXXX()转换；要求被转换的POJO的属性提供getXXX()方法.
	 * @param pojo
	 *  @param excludeFields: 生成JSON对象时，需要排除的pojo属性
	 * @return JSONObject:返回POJO被转成的JSONObject.
	 */
	public static JSONObject toJSONObject(Object pojo, Collection excludeFields) {
		JSONObject jobject = new JSONObject();
		if(pojo == null){
			System.out.println("waring:需要转换的pojo对象为空！");
			return jobject;
		}
		try {
			Method[] methods = pojo.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String name = method.getName();
				if(name.startsWith("get") && !name.equals("getClass") ){
					Object value = method.invoke(pojo, null);
					
					//将首字母转成小写
					name = name.substring(3);

					String temp = new String(new char[]{name.charAt(0)});
					name = name.replaceFirst(temp, temp.toLowerCase());
					
					//排除 excludeFields 中的属性
					if(excludeFields != null && excludeFields.size() > 0 && excludeFields.contains(name)){
						continue;
					}					
					if(value instanceof String){
						jobject.put(name,StringUtils.trim((String)value));
					}else{
						jobject.put(name,value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobject;
	}
	
	public static JSONObject toJSONObject(Object pojo){
		return toJSONObject(pojo, null);
	}
	
	/**
	 * 将Page中的list数据以json格式，发送到页面，配合easy ui 的datagrid组件使用
	 * @param attrName
	 * @param page
	 * @throws JSONException
	 */
	public static void setJsonAsRequestAttr(HttpServletRequest request,String attrName,Page page, Collection excludeFields) throws JSONException{
		
		if(page == null){
			System.out.println("JSONUtil 调用 setJsonAsRequestAttr 时，形参page为空！");
			return;
		}
        JSONObject jresult = getJSONObject(page, excludeFields);
        request.setAttribute(attrName, jresult.toString());
	}
	
	public static void setJsonAsRequestAttr(HttpServletRequest request,String attrName,Page page) throws JSONException{
		setJsonAsRequestAttr(request,attrName,page,null);
	}
	
	/**
	 * 将 list 数据以json格式，发送到页面，配合easy ui 的datagrid组件使用
	 * @param attrName
	 * @param page
	 * @throws JSONException
	 */
	public static void setJsonAsRequestAttr(HttpServletRequest request,String attrName,List list) throws JSONException{
		
		if(list == null){
			System.out.println("JSONUtil 调用 setJsonAsRequestAttr 时，形参list为空！");
			return;
		}
        JSONObject jresult = getJSONObject(list);
        request.setAttribute(attrName, jresult.toString());
	}
	
	/**
	 * 把Object(只能为Page 和 List 类型) 转换为JSONObject 对象
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getJSONObject(Object dataCol, Collection excludeFields) throws JSONException {
		if(dataCol == null){
			System.out.println("JSONUtil 调用 getJSONObject 时，形参 dataCol 为空！");
			return new JSONObject();
		}
		
		Collection list = null;
		int totleCount = 0;
        JSONObject jresult = new JSONObject();
		if( dataCol instanceof Page){
			Page page = (Page)dataCol;
			list = page.getList();
			totleCount = page.getTotalNumber();
		}else if(dataCol instanceof List){
			list = (List)dataCol;
			totleCount = list.size();
		}
		
		JSONArray jitems = new JSONArray();
        Iterator it = list.iterator();
        while(it.hasNext()){
        	Object obj = it.next();
        	if (obj instanceof Map) {
        		jitems.put(map2EasyuiDatagridData((Map)obj));
        	} else {
        		jitems.put(toJSONObject(obj,excludeFields));
        	}
        }
        
		jresult.put("total",totleCount);
        jresult.put("rows", jitems);
        return jresult;
	}
	
	public static JSONObject getJSONObject(Object dataCol) throws JSONException {
		return getJSONObject(dataCol, null);
	}
	
	/**
	 * 把map的集合对象转化为easyui的json形式
	 * @param map
	 * @return
	 */
	public static JSONObject map2EasyuiDatagridData(Map map) throws JSONException {
		JSONObject returnObj = new JSONObject();
		if (map != null) {
			Set set = map.entrySet();
			Iterator iter = set.iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry)iter.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				returnObj.put(key+"",value);
			}
		}
		return returnObj;
	}
	
	/**
	 * 过滤JSON字符串格式的特殊字符
	 * @param s
	 * @return
	 */
	public static String jsonCharFilter(String src) {  
		if(src == null){
			return "";
		}
        StringBuffer sb = new StringBuffer ();       
        for (int i=0; i<src.length(); i++) {       
  
            char c = src.charAt(i);       
            switch (c) {       
                case '\"':       
                    sb.append("\\\"");       
                    break;       
                case '\\':       
                    sb.append("\\\\");       
                    break;       
                case '/':       
                    sb.append("\\/");       
                    break;       
                case '\b':       
                    sb.append("\\b");       
                    break;       
                case '\f':       
                    sb.append("\\f");       
                    break;       
                case '\n':       
                    sb.append("\\n");       
                    break;       
                case '\r':       
                    sb.append("\\r");       
                    break;       
                case '\t':       
                    sb.append("\\t");       
                    break;       
                default:       
                    sb.append(c);       
            }  
       }  
       return sb.toString();       
    }    
	
	
	//测试
	public static void main(String[] args) throws JSONException {
		ProgramInfoVO vo = new ProgramInfoVO();
		
		vo.setId(new Integer(1));
		vo.setProgramName("张山");
		vo.setBuyerName("李斯");
		
		JSONObject jobj = JSONUtil.toJSONObject(vo);
		
		System.out.println("resutl: " + jobj.toString());
		Map map = new HashMap();
		map.put("programName","节目名称");
		map.put("programType","");
		map.put("buyer",null);//如果传入空对象 jsonobject 则转化为 {}
		JSONObject obj = map2EasyuiDatagridData(map);
		System.out.println("obj = " + obj.toString());
	}

}