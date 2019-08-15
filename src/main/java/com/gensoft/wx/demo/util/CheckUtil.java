package com.gensoft.wx.demo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @ desc：
 * @ Author     ：chenhl01.
 * @ Date       ：Created in 10:21 2019/8/14
 */
public class CheckUtil {

	private static final String token = "chlin7";

	public static boolean checkSignature(String signature, String timestamp, String nonce) {

		System.out.println("==timestamp is :=="+timestamp);
		System.out.println("==nonce is :=="+nonce);
		System.out.println("==signature is :=="+signature);

		String[] arr = new String[]{token, timestamp, nonce};

		Arrays.sort(arr);

		StringBuffer content = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		//sha1加密

		String temp = getSha1(content.toString());

		return temp.equals(signature);
	}

	/**
	 * sha1加密
	 *
	 * @param str
	 * @return
	 */
	public static String getSha1(String str) {
		if (null == str || 0 == str.length()) {
			return null;
		}
		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}
}
