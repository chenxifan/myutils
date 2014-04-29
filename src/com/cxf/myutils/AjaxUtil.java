/*
 * 创建日期 2012-7-31
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.excellence.expps.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Administrator
 * 
 * TODO JOSON 数据处理类
 */
public class AjaxUtil {
	
	public static final String RESPONCE_TYPE_TEXT = "text";
	
	public static final String RESPONCE_TYPE_HTML = "html";
	
	public static final String RESPONCE_TYPE_JSON = "json";
	
	public static final String RESPONCE_TYPE_XML = "xml";
    
	public static String getJSONOutputString(HttpServletRequest request,
			HttpServletResponse response, JSONObject jobj) {
		boolean scriptTag = false;
		String cb = request.getParameter("callback");
		if (cb != null) {
			scriptTag = true;
			response.setContentType("text/javascript");
		} else {
			response.setContentType("application/x-json");
		}
		StringBuffer jsonbuffer = new StringBuffer();
		if (scriptTag) {
			jsonbuffer.append(cb + "(");
		}
		//response.getWriter().print(jsonData);
		jsonbuffer.append(jobj.toString());
		if (scriptTag) {
			jsonbuffer.append(");");
		}
		return jsonbuffer.toString();

	}

	public static String getJSONOutputString(HttpServletRequest request,
			HttpServletResponse response, JSONArray jobj) {
		boolean scriptTag = false;
		String cb = request.getParameter("callback");
		if (cb != null) {
			scriptTag = true;
			response.setContentType("text/javascript");
		} else {
			response.setContentType("application/x-json");
		}
		StringBuffer jsonbuffer = new StringBuffer();
		if (scriptTag) {
			jsonbuffer.append(cb + "(");
		}
		//response.getWriter().print(jsonData);
		jsonbuffer.append(jobj.toString());
		if (scriptTag) {
			jsonbuffer.append(");");
		}
		return jsonbuffer.toString();

	}
	    
	/**
	 * 处理Ajax输出，返回html格式数据
	 * @param response
	 * @param responseText
     * 重构：2012-8-3 chenxf 增强容错性和缓存刷新问题
	 */
	public static void ajaxResponse(HttpServletResponse response, String responseText, String responseType) {
        String contentType = null;
        
        if (RESPONCE_TYPE_HTML.equalsIgnoreCase(responseType)) {
            contentType = "text/html;charset=utf-8";
        } else if (RESPONCE_TYPE_JSON.equalsIgnoreCase(responseType)) {
            contentType = "text/json;charset=utf-8";
        } else if (RESPONCE_TYPE_TEXT.equalsIgnoreCase(responseType)) {
            contentType = "text/plain;charset=utf-8";
        } else if (RESPONCE_TYPE_XML.equalsIgnoreCase(responseType)) {
            contentType = "text/xml;charset=utf-8";
        } else {
            contentType = "text/html;charset=utf-8";
        }
        
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType(contentType);
        writer(response, responseText);
	}
    
    /**
     * 职责：Ajax数据输出
     * 注意：这个方法只负责数据的输出，并没有设置头信息
     * @param response
     * @param responseText
     */
    public static void writer(HttpServletResponse response, String responseText) {
        //TODO:delete
       // System.out.println(responseText);
        
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(responseText);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
