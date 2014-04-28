/*
 * 创建日期：2014-4-23
 */
package com.cxf.myutils;

/**
 * 职责：提供MD5常用操作
 * 
 * @author chenxf
 * 
 */
public class MD5Utils {
	public static void main(String[] args) {
		MD5 md5 = new MD5();
		String pwd = md5.getMD5ofStr("123456");
		System.out.println(pwd);
	}
}
