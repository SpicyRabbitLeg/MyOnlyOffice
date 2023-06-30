package com.onlyoffice.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

@Component
public class MD5Util {
	/**
	 * 获取md5字符串
	 */
	public String encrypt(String dataStr) {
		return encrypt(dataStr.getBytes(StandardCharsets.UTF_8));
	}


	public String encrypt(byte[] bytes){
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(bytes);
			byte s[] = m.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return StringUtils.EMPTY;
	}


	public String key(String u){
		Random random = new Random();
		StringBuilder sbr = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			sbr.append(u.charAt(random.nextInt(u.length())));
		}
		return sbr.toString();
	}
}
