package com.excellence.expps.util;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

/**
 * 职责：字符串工具类
 * @author Administrator
 */
public class StringUtil {
	
	/**
	 * 截取字符串
	 * @param src: 源字符串
	 * @param descLength： 截取后字符串保留的长度
	 * @param suffix: 截取后字符串的后缀, 默认是“...”
	 * @return
	 */
	public static String cutSubString(String src, int descLength, String suffix){
		
		int endIndex = descLength - 3;
		if(suffix != null && suffix.length()> 0){
			endIndex = descLength - suffix.length();
		}
		
		if(src == null || src.length() < endIndex){
			return src;
		}else{

			String subString = src.substring(0, endIndex);
			if(suffix != null && suffix.length() > 0){
				return subString.concat(suffix);
			}else{
				return subString.concat("...");
			}
		}
	}
	
	/**
	 * 如果
	 * @param number
	 * @return
	 */
	public static String number2String(Object number) {
		String numStr = "0";
		if (number != null) {
			if (number instanceof Double) { 
				Double d = (Double)number;
				numStr = d.doubleValue() + "";
			} else if (number instanceof Integer) {
				Integer i = (Integer)number;
				numStr = i.intValue() + "";
			} else  if (number instanceof Float) {
				Float f = (Float)number;
				numStr = f.floatValue() + "";
			}
		}
		return numStr;
	}
	
	
	public static String isEmptyDefault(String targetStr,String defaultStr) {
		if (StringUtils.isEmpty(targetStr)) {
			targetStr = defaultStr;
		}
		return targetStr;
	}
	
	/**
	 * 
	 * @param score 
	 * @param point 保留小数点的位数
	 * @param prefix 前缀
	 * @return
	 */
	public static String formatNumber(double score,int point,String prefix) {
		String format = prefix == null ? "#" : prefix;
		String pointStr = "";
		if (point < 0) point = 0;
		for (int i=0;i<point;i++) {
			pointStr += "0";
		}
		if (StringUtils.isNotEmpty(pointStr)) {
			format += "." + pointStr;
		}
		DecimalFormat df = new DecimalFormat(format);
		df.setMinimumIntegerDigits(1);
		df.setMaximumFractionDigits(point);
		df.setMinimumIntegerDigits(1);
		return df.format(score);
	}
	
	/**
	 * 转化为万元
	 * @param divisor
	 * @param dividend
	 * @return
	 */
	public static String division(Double divisor,double dividend) {
		if (divisor == null || divisor.intValue() ==0 || dividend == 0) {
			return "——";
		}
		double sum = divisor.doubleValue()/dividend;
		return formatNumber(sum,3,"￥");
	}

	
	public static void main(String[] args) {
		System.out.println(formatNumber(0.5,3,null));
	}
}



