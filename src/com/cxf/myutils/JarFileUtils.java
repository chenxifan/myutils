package com.excellence.expps.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class JarFileUtils {

	private static Log log = LogFactory.getLog(JarFileUtils.class);

	/**
	 * 功能：创建压缩文件
	 * @param sourcePath
	 * @param savePath
	 * @param encoding
	 */
	public static void zipCreate(String sourcePath, String savePath, String encoding) {
		File sourceFile = new File(sourcePath);
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(savePath));
            out.setEncoding(encoding);
			zipCon(out, sourceFile, "");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				out = null;
			}
		}
	}

	private static void zipCon(ZipOutputStream out, File file, String base)
			throws Exception {
		if (file.isDirectory()) {
			String path = base + "/";
            
			//避免将根目录也打包进去
            if (!"/".equals(path)) {
                out.putNextEntry(new ZipEntry(path));// 创建目录
            }
			
			base = base.length() == 0 ? "" : path;
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				zipCon(out, fileList[i], base + fileList[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = null;
			try {
				in = new FileInputStream(file);
				int b;
				while ((b = in.read()) != -1) {
					out.write(b);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			} finally {
				in.close();
				in = null;
			}
		}
	}
	
	/**
	 * 功能：解压缩JAR包
	 * @param filePath 文件名
	 * @param outputPath 解压输出路径
	 * @throws IOException IO异常
	 */
	public static void unzip(String filePath, String outputPath) throws IOException {
		//追加分隔符
		if (!outputPath.endsWith("\\") && !outputPath.endsWith("/")) {
			outputPath += File.separator;
		}
		
		log.debug(filePath);
		JarFile jf = new JarFile(filePath);

		for (Enumeration e = jf.entries(); e.hasMoreElements(); ) {
			JarEntry je = (JarEntry)e.nextElement();
			String outFileName = outputPath + je.getName();
			File f = new File(outFileName);
			// 创建该路径的目录和所有父目录
			makeSupDir(outFileName);
			// 如果是目录，则直接进入下一个循环
			if (f.isDirectory()) {
				continue;
			}

			InputStream in = null;
			OutputStream out = null;
			try {
				in = jf.getInputStream(je);
				out = new BufferedOutputStream(new FileOutputStream(f));
				byte[] buffer = new byte[2048];
				int nBytes = 0;
				while ((nBytes = in.read(buffer)) > 0) {
					out.write(buffer, 0, nBytes);
				}
			} catch (IOException ioe) {
				throw ioe;
			} finally {
				try {
					if (null != out) {
						out.flush();
						out.close();
					}
				} catch (IOException ee) {
				} finally {
					out = null;
				}
				try {
					if (null != in) {
						in.close();
					}
				} catch (IOException ee) {
				} finally {
					in = null;
				}
			}
		}
	}

	/**
	 * 功能：循环创建父目录
	 * @param outFileName
	 */
	private static void makeSupDir(String outFileName) {
		// 匹配分隔符
		Pattern p = Pattern.compile("[/\\" + File.separator + "]");
		Matcher m = p.matcher(outFileName);
		// 每找到一个匹配的分隔符，则创建一个该分隔符以前的目录
		while (m.find()) {
			int index = m.start();
			String subDir = outFileName.substring(0, index);
			File subDirFile = new File(subDir);
			if (!subDirFile.exists())
				subDirFile.mkdir();
		}
	}

	/**
	 * 功能：删除加压目录及目录下的文件
	 * @param path	存放jar包解压文件的目录
	 */
	public static void cleanUnzip(String path) throws IOException {
		File file = new File(path);
		// 如果该路径不存在
		if (!file.exists()) {
			System.out.println("路径不存在，停止删除：" + path);
		} else {
			FileUtils.deleteDirectory(file);
		}
	}
	
	/**
	 * 功能：获取JAR包内所有的class完整类名
	 * @param filePath jar文件完整路径地址
	 * @return class完整类名集合
	 * @throws IOException
	 */
	public static List findClassNames(String filePath) throws IOException {
		List classNames = new ArrayList();
		JarFile jf = new JarFile(filePath);

		for (Enumeration e = jf.entries(); e.hasMoreElements(); ) {
			JarEntry je = (JarEntry)e.nextElement();
			if (je.isDirectory()) {
				continue;
			}
			
			//例子：org/apache/hadoop/examples/WordCount$TokenizerMapper.class
			//截取以“.class”结束的文件
			String name = je.getName();
			int charIndex = name.lastIndexOf(".");
			if (charIndex != -1 && ".class".equalsIgnoreCase(name.substring(charIndex))) {
				name = name.substring(0, charIndex);
				name = name.replaceAll("/", ".");
				name = name.replaceAll("\\$", ".");
				System.out.println(name);
				classNames.add(name);
			}
		}
		
		return classNames;
	}
}
