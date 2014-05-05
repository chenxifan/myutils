/*
 * Create on 2014-5-5
 */
package com.cxf.myutils;

import java.text.DecimalFormat;

/**
 * 职责：数字处理类，例如金额的格式化等功能
 * @author chenxf
 * @version 1.0
 */
public class NumberUtil {
	
	/**
	 * 格式化数字：将数字格式化为小数点后2位
	 * @param score
	 * @return String:格式化后的数字
	 */
	public static String formatNumber(double score) {
		DecimalFormat df = new DecimalFormat("#.00");
		df.setMinimumIntegerDigits(1);
		df.setMaximumFractionDigits(2);
		df.setMinimumIntegerDigits(2);
		return df.format(score);
	}
}
