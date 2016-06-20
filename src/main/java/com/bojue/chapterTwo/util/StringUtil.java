package com.bojue.chapterTwo.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author yansongbai
 *
 */
public class StringUtil {

	/**
	 * 判断字符为空
	 */
	public static boolean isEmpty(String str){
		
		return StringUtils.isEmpty(str);
	}
	
	/**
	 * 判断字符不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
}
