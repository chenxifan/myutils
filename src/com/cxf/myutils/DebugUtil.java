package com.cxf.myutils;

import java.lang.reflect.Method;

/**
 * 职责：Debug工具
 * 
 * @author chenxf
 * @version 1.0
 */
public class DebugUtil {

	/**
	 * 职责：输出指定对象的get方法
	 * @param obj
	 */
	public static void outputObjectGetMothod(Object obj) {
		try {
			Method[] methods = obj.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String name = method.getName();
				if (name.startsWith("get") && !name.equals("getClass")) {
					Object value = method.invoke(obj, null);
					System.out.println("DebugUtil:-----------------------	" + name + ": " + value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}