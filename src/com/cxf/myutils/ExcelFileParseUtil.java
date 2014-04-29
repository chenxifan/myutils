/*
 * 创建日期 2012-9-3
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.excellence.expps.util;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 解析Excel文件
 */
public class ExcelFileParseUtil 
{
	private String[] titles = null;
	private List datas = null;
	private InputStream is = null;
	public String[] getTitles() {
		return titles;
	}
	public List getDatas() {
		return datas;
	}
	public ExcelFileParseUtil(InputStream isExcelFile)
	{
		this.is = isExcelFile;
	}

	public void parse() throws Exception
	{
		Workbook workbook = Workbook.getWorkbook(this.is);
		Sheet[] sheets = workbook.getSheets();
		/**获取列表标题的索引*/
		Map titlesIndex = new LinkedHashMap();
		Map data = null;
		datas = new ArrayList();
		for (int i = 0; i < 1; i++)
		{
			Sheet sheet = sheets[i];
			System.out.println("----"+sheet.getName());
			int nbCol = sheet.getRows();

			String title = "";
			for (int j = 0; j < sheet.getColumns(); j++) 
			{
				Cell[] cells = sheet.getRow(0);
				if(j>=cells.length)
					break;
				title = cells[j].getContents();
				
				if(title!=null && title.trim().length()>0)
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
		is.close();
		Collection values = titlesIndex.values();
		
		
		
		Iterator it = values.iterator();
		titles = new String[values.size()];
		int index = 0;
		while(it.hasNext())
		{
			titles[index] = (String)it.next();
			index++;
		}
	}
	public void genSql(String sqlDir) throws Exception
	{
		Workbook workbook = Workbook.getWorkbook(this.is);
		Sheet[] sheets = workbook.getSheets();
		/**获取列表标题的索引*/
		Map titlesIndex = new HashMap();
		Map data = null;
		datas = new ArrayList();
		String sheetName = null;
		StringBuffer sql = new StringBuffer();
		for (int i = 0; i < sheets.length; i++)
		{
			Sheet sheet = sheets[i];
			int nbCol = sheet.getRows();
			if(nbCol<1)
			{
				break;
			}
			sheetName = sheet.getName();
			FileWriter wr = new FileWriter(sqlDir+"/"+sheetName+".sql");
			String title = "";
			for (int j = 0; j < sheet.getColumns(); j++) 
			{
				Cell[] cells = sheet.getRow(0);
				if(j>=cells.length)
					break;
				title = cells[j].getContents();
				if(title!=null && title.trim().length()>0)
				{
					titlesIndex.put(""+j,title.trim());
				}					
			}
			for (int j = 1; j < nbCol; j++) 
			{
				Cell[] cells = sheet.getRow(j);
				data = new HashMap();
				sql = new StringBuffer();
				StringBuffer sqlValues = new StringBuffer();
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
				if(!data.isEmpty())
				{
					sql.append("insert into "+sheetName+"(");
					Set keys = data.keySet();
					Iterator it = keys.iterator();
					int index = 0;
					while(it.hasNext())
					{
						String fieldName = (String)it.next();
						String value = null;
						if(fieldName!=null && fieldName.trim().length()>0)
						{
							value = (String)data.get(fieldName);
							value = value==null?"":value;
							if(index==0)
							{
								sql.append(fieldName);
								sqlValues.append("'"+value+"'");
							}
							else
							{
								sql.append(","+fieldName);
								sqlValues.append(",'"+value+"'");
							}
							index++;
						}
					}
					sql.append(")");
					sql.append(" values("+sqlValues+")");
				}
				datas.add(data);
				if(sql.length()>0)
				{
					wr.write(sql.toString()+";\r\n");
				}
			}
			wr.flush();
			wr.close();
		}
		workbook.close();
		is.close();
		Collection values = titlesIndex.values();
		Iterator it = values.iterator();
		titles = new String[values.size()];
		int index = 0;
		while(it.hasNext())
		{
			titles[index] = (String)it.next();
			index++;
		}
	}	
	public static void main(String[] args) throws Exception
	{
		InputStream is = new FileInputStream("c:/Users/pengsy/Desktop/收视情况导出.xls");
		ExcelFileParseUtil util = new ExcelFileParseUtil(is);
		
		
		
		util.parse();
		List datas = util.getDatas();
		Map data = null;
		for(int i=0;i<datas.size();i++)
		{
			data = (Map)datas.get(i);
			System.err.println("data="+data);
		}
	}
}
