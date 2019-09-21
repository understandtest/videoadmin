package com.videoadmin.utils;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String password = MD5Util.md5("000000", SysConstants.MD5_SALT);
		System.out.println(password);

	}

}
