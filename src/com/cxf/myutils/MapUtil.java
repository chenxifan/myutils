/*
 * Created on 2012-8-28
 *
 */
package com.excellence.expps.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.excellence.expps.buy.vo.ProBuyContractVO;
import com.excellence.expps.sale.vo.ProSalesContractVO;

/**
 * @author liul
 *  
 */
public class MapUtil {

	public static final String setMethodModify = "set";
	
	/**
	 * 节目类型基本信息
	 */
	private static final List PROGRAMTYPES = ExOAUtil.getDicts("programType");

	/**
	 * 对Map中key，value的值键对做key序列复制，例如key="abc" value = "value", 复制1份，即 <"abc1",
	 * "value">
	 * 
	 * @param map
	 * @param key
	 * @param value
	 * @param num
	 *            副本数量
	 * @param tag
	 *            复制后的值将会被转换成金额大写
	 * @return
	 */
	public static Map copyValue(Map map, String key, Object value, int num,
			boolean isMoney) {
		for (int i = 1; i <= num; i++) {
			if (isMoney && null != value) {
				value = MoneyUtil.toChinese(value.toString());
			}
			map.put(key + i, value);
		}
		return map;
	}

	/**
	 * 把对象的属性反射到map中，除了except集合中的属性
	 * 
	 * @param map
	 * @param obj
	 * @param except
	 * @return
	 * @throws Exception
	 */
	public static Map putKeyValue(Map map, Object obj, Collection except)
			throws Exception {
		Field field = null;
		String name = null;
		Object value = null;

		Method method = null;
		String methodName = null;
		if (obj == null)
			return map;
		DecimalFormat df=new DecimalFormat("#0.00");
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			name = field.getName();
			//System.out.println("name = " + name);
			//过滤
			if (except != null && except.size() > 0 && except.contains(name))
				continue;
			//直接反射字段的值不行，因为obj的属性为私有的，所以调用其相应的get方法
			String fStr = new String(new char[] { name.charAt(0) });
			methodName = "get" + name.replaceFirst(fStr, fStr.toUpperCase());
			if (methodName.equals("getSerialVersionUID"))
				continue;
			method = obj.getClass().getDeclaredMethod(methodName, null);
			value = method.invoke(obj, null);
			if (name.equals("volCount")
					&& (obj instanceof ProSalesContractVO || obj instanceof ProBuyContractVO)) {
				//磁带盘数
				name = "diskCount";
			}
			//去掉null
			if (null == value) {
				map.put(name, "");
			} else {
				map.put(name, value);
				/**解决值大的double转成字符串带E的问题*/
				if(value instanceof Double)
				{
					String strValue = df.format(((Double)value).doubleValue());
					map.put(name,strValue );
				}
			}
		}
		return map;
	}

	/**
	 * 将Map对象转换为 JavaBean对象
	 * @param map: Map对象
	 * @param obj： JavaBean对象
	 * @return Object JavaBean
	 */
	public static Object convertToBean(Map map, Object obj) {
		
		Set entries = map.entrySet();
		Iterator it = entries.iterator();
		Map.Entry entry = null;
		BeanWrapper beanObj = new BeanWrapperImpl(obj); 
		String name = null;
		while(it.hasNext())
		{
			entry = (Map.Entry)it.next();
			name = (String)entry.getKey();
			
			try
			{
				if(beanObj.isWritableProperty(name))
				{
					if(beanObj.getPropertyType(name).getName().equalsIgnoreCase("java.lang.Integer") && entry.getValue() instanceof Long)
					{
						beanObj.setPropertyValue(name,new Integer(Integer.parseInt(entry.getValue().toString())));
					}
					else 
						beanObj.setPropertyValue(name,entry.getValue());
				}
			}
			catch(Exception e)
			{
				System.err.println("属性"+name+"注入失败!");
			}
		}
		return obj;
	}
	
	/**
	 * @return 返回 programType。
	 */
	public static String getProgramType(String programType) {
		String typeStr = "";
		if (PROGRAMTYPES != null && programType != null) {
			for (int i=0;i<PROGRAMTYPES.size();i++) {
				Map map = (Map)PROGRAMTYPES.get(i);
				String name = (String)map.get("name");
				if (programType.equals(name)) {
					typeStr = (String)map.get("content");
					break;
				}
			}
		}
		return typeStr;
	}

}