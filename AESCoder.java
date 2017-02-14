package com.ccx.credit.data.shared.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.spec.IvParameterSpec;

public class AESCoder {

	public static String Encrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

		return new Base64().encodeToString(encrypted).replace("/", "-")
				.replace("=", "_");
	}

	public static String Decrypt(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new Base64().decode(sSrc.replace("-", "/")
					.replace("_", "="));// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	public static void main(String[] args) {
		String text = "123";
		String secret = "1234567890123456";

		try {
			String b = AESCoder.Encrypt(text, secret);
			System.out.println(b);
		} catch (Exception e) {
			System.out.println(e);
		}
		;
		try {
			String b = AESCoder.Encrypt(text, secret);
			System.out.println(AESCoder.Decrypt(b, secret));
		} catch (Exception e) {
			System.out.println(e);
		}
		;
		
		System.out.println(Constants.WOLONG_TEST_APPSECRET);
		System.out.println(Constants.WOLONG_TEST_APPSECRET.substring(0, 16));
		System.out.println(Constants.WOLONG_TEST_APPSECRET.substring(0, 16).length());
	};

}
