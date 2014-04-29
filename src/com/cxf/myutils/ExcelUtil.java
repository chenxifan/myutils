/*
 * 创建日期 2012-8-15
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.excellence.expps.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author pengsy
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ExcelUtil {
	
	/**
	 * 导出excel
	 * @param data 导出的数据
	 * @param out 输出流
	 * @param in excel模板输入流
	 */
	public static void exportExcel(Map data,OutputStream out,InputStream in) {
		XLSTransformer transformer = new XLSTransformer();
		HSSFWorkbook wb = transformer.transformXLS(in, data);
		try {
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
                out = null;
                in = null;
            }
		}
	}
    
    /**
     * 导出excel，不关闭流！
     * @param data 导出的数据
     * @param out 输出流
     * @param in excel模板输入流
     */
    public static void exportExcelMin(Map data,OutputStream out,InputStream in) throws Exception {
        XLSTransformer transformer = new XLSTransformer();
        HSSFWorkbook wb = transformer.transformXLS(in, data);
        wb.write(out);
        in.reset();
    }
	
	/**
	 * 功能：利用模版 templatePath 导出文件 fileName
	 * 注意：模版制作使用 dataList 引用传递到excel中的列表数据
	 * 格式：
	 * 	剧目名称	题材类型	集数	主演	导演	编剧	来源	片源
	 *	<jx:forEach items="${programList}" var="programInfo">							
	 *		${programInfo.programName}							
     *  </jx:forEach>							
     * 说明：
     * 1、jx标签的语法类是jstl c标签
     * 2、<jx:forEach items="${programList}" var="programInfo"> 所在的excel 行取消单元格合并
	 * @param response
	 * @param fileName
	 * @param dataList
	 * @param templatePath
	 */
	public static void exportExcel(HttpServletResponse response,String fileName,String templatePath,List dataList)throws Exception{
		InputStream input = null;
  		OutputStream output = null;
		
		//输出信息头设置
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
		response.setContentType("application/vnd.ms-excel;charset=gbk");
		response.setHeader("Location", URLEncoder.encode(fileName, "UTF-8") + ".xls");
		
		try{
			//输出流
			output = response.getOutputStream();
			
			//读取输出模板
			input = new FileInputStream(templatePath);
			
			XLSTransformer transformer = new XLSTransformer();
			
			Map params = new HashMap();
			
			params.put("dataList", dataList);
			
			HSSFWorkbook wb = transformer.transformXLS(input, params);
			
			wb.write(output);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(input != null){
					input.close();
				}
				if(output == null){
					output.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能：利用模版 templatePath 导出文件 fileName
	 * 注意：模版制作使用 params 引用传递到excel中的数据
	 * @param response
	 * @param fileName
	 * @param params
	 * @param templatePath
	 */
	public static void exportExcel(HttpServletResponse response,String fileName,String templatePath,Map params)throws Exception{
	    exportExcel(response,fileName,templatePath,params,0);
	}
	
	/**
	 * 功能：利用模版 templatePath 导出文件 fileName
	 * 注意：模版制作使用 params 引用传递到excel中的数据
	 * @param response
	 * @param fileName
	 * @param params
	 * @param templatePath
	 * @param exportType 0:附件 ； 1：在线
	 */
	public static void exportExcel(HttpServletResponse response,String fileName,String templatePath,Map params,int exportType)throws Exception{
		InputStream input = null;
  		OutputStream output = null;
		
		//输出信息头设置
  		if(exportType == 0){
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
  		}else{
  		    response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
  		}
		response.setContentType("application/vnd.ms-excel;charset=gbk");
		response.setHeader("Location", URLEncoder.encode(fileName, "UTF-8") + ".xls");
		
		try{
			//输出流
			output = response.getOutputStream();
			
			//读取输出模板
			input = new FileInputStream(templatePath);
			
			XLSTransformer transformer = new XLSTransformer();
			
			HSSFWorkbook wb = transformer.transformXLS(input, params);
			
			wb.write(output);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(input != null){
					input.close();
				}
				if(output == null){
					output.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从流中读取excel内容，循环excel文件中的所有sheet，读取数据保存在list中。
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static List parseExcel(InputStream input) throws Exception
	{
		Workbook workbook = Workbook.getWorkbook(input);
		Sheet[] sheets = workbook.getSheets();
		
		/**获取列表标题的索引*/
		Map titlesIndex = new HashMap();
		
		Map data = null;
		
		List datas = new ArrayList();

		String title = "";
		
		for(int i = 0 ; i < sheets.length ; i++){
			Sheet sheet = sheets[i];
			int nbCol = sheet.getRows();
			for (int j = 0; j < sheet.getColumns(); j++) 
			{
				Cell[] cells = sheet.getRow(0);
				if(j>=cells.length)
					break;
				title = cells[j].getContents();
				if(StringUtils.isNotEmpty(title))
				{
					titlesIndex.put(""+j,title.trim());
				}					
			}
			
			for (int j = 1; j < nbCol; j++) 
			{
				Cell[] cells = sheet.getRow(j);
				data = new HashMap();
				for(int col=0;col<cells.length;col++)
				{
					if(cells[col]!=null && cells[col].getContents()!=null)
					{					
						if(titlesIndex.get(""+col)!=null)
						{
							data.put(titlesIndex.get(""+col),cells[col].getContents().trim());						
						}
					}
				}
				datas.add(data);
			}
		}
		
		workbook.close();
		
		return datas;
	}
	
    
}




