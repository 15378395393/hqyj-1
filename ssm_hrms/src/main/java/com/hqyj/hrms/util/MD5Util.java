package com.hqyj.hrms.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * MD5 Util，密码加密
 * @author: HymanHu
 * @date: 2019年10月29日
 */
public class MD5Util {
	private static final String SALT = "&%5123***&&%%$$#@";
	
	public static String getMD5(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		String base = str + "/" + SALT;
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}
}
