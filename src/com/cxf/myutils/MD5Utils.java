/*
 * Create on 2014-5-4
 */
package com.cxf.myutils;

/**
 * 职责：MD5工具类
 * 
 * @author chenxf
 * @version 1.0
 */
public class MD5Utils {
	public static void main(String[] args) {
		MD5 md5 = new MD5();
		String pwd = md5.getMD5ofStr("123456");
		System.out.println(pwd);
	}
}
