/*
 * �������ڣ�2014-4-23
 */
package com.cxf.myutils;

/**
 * ְ���ṩMD5���ò���
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
