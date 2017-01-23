package com.dailycation.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	private static final String[] strDigits = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String getMD5(String val) throws NoSuchAlgorithmException{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(val.getBytes());
		byte[] m = md5.digest();//加密
		return getString(m);
	}
	
	private static String getString(byte[] b){
		StringBuffer sb = new StringBuffer();
		 for(int i = 0; i < b.length; i ++){
		  sb.append(b[i]);
		 }
		 return sb.toString();
	}

	public static String md5(String str) throws NoSuchAlgorithmException {
		// 进行MD5
		String md5 = null;
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		md5 = byteToString(md.digest(str.getBytes()));
		return md5;
	}

	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}
}
