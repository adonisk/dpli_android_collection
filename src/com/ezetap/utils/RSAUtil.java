package com.ezetap.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class RSAUtil {

	public static byte[] encrypt(byte[] data) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(generateRandomSessionKey(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(generateRandomSessionKey(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(encrypted);
	}
	
	private static byte[] generateRandomSessionKey() {
		byte[] key = {70, -101, -33, -5, 49, 97, 0, -53, 73, 8, -6, -36, 50, -5, -119, -27, -95, 31, 26, -55, -104, 73, -20, -14};
		return key;
	}
}
