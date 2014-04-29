package com.excellence.expps.util;

import java.lang.reflect.Method;

/**
 * Debug工具
 * 
 * @author Administrator
 */
public class DebugUtil {

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