/*
 * 创建日期 2012-7-31
 */
package com.cxf.myutils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 职责：JOSON 数据处理类
 * 
 * @author chenxf
 * @version 1.0
 */
public class AjaxUtil {

	/** 返回数据类型为文本 */
	public static final String RESPONCE_TYPE_TEXT = "plain";

	/** 返回数据类型为html代码 */
	public static final String RESPONCE_TYPE_HTML = "html";

	/** 返回数据类型为json格式 */
	public static final String RESPONCE_TYPE_JSON = "json";

	/** 返回数据类型为xml代码 */
	public static final String RESPONCE_TYPE_XML = "xml";

	/**
	 * 职责：处理Ajax输出，返回text/plain格式的数据。
	 * @param response
	 * @param responseText
	 */
	public static void ajaxResponse(HttpServletResponse response, String responseText) {
		ajaxResponse(response, responseText, RESPONCE_TYPE_TEXT);
	}
	
	/**
	 * 职责：处理Ajax输出，返回指定的格式数据。如果不指定数据格式，默认为text/plain <br/>
	 * 重构：2012-8-3 chenxf 增强容错性和缓存刷新问题
	 * 
	 * @param response
	 * @param responseText
	 * @param responseType
	 */
	public static void ajaxResponse(HttpServletResponse response,
			String responseText, String responseType) {
		String contentType = null;

		if (responseType == null || "".equals(responseType.trim())) {
			// System.err.println("注意：返回数据类型为空！");
			responseType = RESPONCE_TYPE_TEXT;
		}

		/*
		 * 不对responseType做匹配，同时也不限制返回类型，方便日后扩展
		 * 
		 * if (RESPONCE_TYPE_HTML.equalsIgnoreCase(responseType)) { contentType
		 * = "text/html;charset=utf-8"; } else if
		 * (RESPONCE_TYPE_JSON.equalsIgnoreCase(responseType)) { contentType =
		 * "text/json;charset=utf-8"; } else if
		 * (RESPONCE_TYPE_TEXT.equalsIgnoreCase(responseType)) { contentType =
		 * "text/plain;charset=utf-8"; } else if
		 * (RESPONCE_TYPE_XML.equalsIgnoreCase(responseType)) { contentType =
		 * "text/xml;charset=utf-8"; } else { contentType =
		 * "text/html;charset=utf-8"; }
		 */

		contentType = "text/" + responseType + ";charset=utf-8";
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType(contentType);
		writer(response, responseText);
	}

	/**
	 * 职责：Ajax数据输出 <br/>
	 * 注意：这个方法只负责数据的输出，并没有设置头信息
	 * 
	 * @param response
	 * @param responseText
	 */
	public static void writer(HttpServletResponse response, String responseText) {
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
