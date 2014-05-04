/*
 * 创建日期 2012-8-25
 *
 */
package com.cxf.myutils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;


/**
 * 职责：文件流处理工具类
 * @author zhangcj
 */
public class FileUtil {
	/**
	 * 从系统中读文件流
	 * @param filePath:文件存放路径
	 * @param response:HttpServletResponse
	 */
	public static void downFileStream(String filePath, HttpServletResponse response, String fileName, String formatType)throws Exception{
		OutputStream output = null;	
		InputStream input = null;
		BufferedInputStream bufInputStr = null;
		BufferedOutputStream bufOutputStr = null;
		try{
			StringBuffer downFileName = new StringBuffer();
			downFileName.append(URLEncoder.encode(fileName.trim(),"UTF-8")).append(".").append(formatType.trim());
			System.out.println("downFileName: " + downFileName.toString());
			
			File file = new File(filePath);
			
			if(!file.exists()){
			    throw new IllegalArgumentException("路径:"+filePath+"指定的文件不存在,下载文件失败！");
			}
			
			input =new FileInputStream(file); //读入文件流			
	  		response.addHeader("Content-Disposition", "attachment;filename=" + downFileName.toString()); 
			response.setContentLength(input.available());//设置文件大小
			response.setContentType("www/unknown");
			output = response.getOutputStream();
			
			bufInputStr = new BufferedInputStream(input);
			bufOutputStr = new BufferedOutputStream(output);
			
			byte data[] = new byte[4096];
			int size = 0;
			size = bufInputStr.read(data);
			while(size != -1){
				bufOutputStr.write(data, 0, size);				
				size = bufInputStr.read(data);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try{
				if(bufInputStr != null){
					bufInputStr.close();
				}
				if(bufOutputStr != null){
					bufOutputStr.flush();
					bufOutputStr.close();
				}
				if(input !=null){
					input.close();
				}
				if(output != null){
					output.close();
				}
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
	}
    
    /**
     * 功能：根据根路径删除文件夹，以及文件夹下的文件
     * @param rootPath  根路径
     * @throws Exception
     */
    public static void deleteDirByRootPath(String rootPath) throws Exception {
        File dir = new File(rootPath);
        FileUtils.deleteDirectory(dir);
    }
	
}