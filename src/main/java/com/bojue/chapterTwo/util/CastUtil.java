package com.bojue.chapterTwo.util;

import org.apache.log4j.Logger;

/** 
* @author  yansongbai
* @date 2016年3月28日 下午10:10:32 
* @version 1.0 
* @parameter  
* @since  
* @return  
* 转型操作工具类
*/
public class CastUtil {
	private static final Logger log = Logger.getLogger(CastUtil.class);
	/**
	 * 转为String型(默认值为"")
	 */
	public static String castString(Object obj){
		
		return CastUtil.castString(obj, "");
	}
	/**
	 * 转为String型(可指定默认值)
	 */
	public static String castString(Object obj, String defaultValue) {
		
		return obj != null ? String.valueOf(obj) : defaultValue;
	}
	/**
	 * 转为double型(默认值为"")
	 */
	public static double castDouble(Object obj){
		
		return CastUtil.castDouble(obj, 0);
	}
	/**
	 * 转为double型(可指定默认值)
	 */
	public static double castDouble(Object obj, double defaultValue) {
		double doubleVal = defaultValue;
		if(obj != null){
			String strString = castString(obj);
			if(StringUtil.isNotEmpty(strString)){
				try {
					doubleVal = Double.parseDouble(strString);
				} catch (NumberFormatException e) {
					log.error("number format exception", e);
				}
			}
		}
		
		return doubleVal;
	}
	/**
	 * 转为long型(默认值为"")
	 */
	public static long castLong(Object obj){
		
		return CastUtil.castLong(obj, 0);
	}
	/**
	 * 转为long型(可指定默认值)
	 */
	public static long castLong(Object obj, long defaultValue) {
		long longVal = defaultValue;
		if(obj != null){
			String strString = castString(obj);
			if(StringUtil.isNotEmpty(strString)){
				try {
					longVal = Long.parseLong(strString);
				} catch (NumberFormatException e) {
					log.error("number format exception", e);
				}
			}
		}
		
		return longVal;
	}
	/**
	 * 转为int型(默认值为"")
	 */
	public static int castInt(Object obj){
		
		return CastUtil.castInt(obj, 0);
	}
	/**
	 * 转为int型(可指定默认值)
	 */
	public static int castInt(Object obj, int defaultValue) {
		int intVal = defaultValue;
		if(obj != null){
			String strString = castString(obj);
			if(StringUtil.isNotEmpty(strString)){
				try {
					intVal = Integer.parseInt(strString);
				} catch (NumberFormatException e) {
					log.error("number format exception", e);
				}
			}
		}
		
		return intVal;
	}
	/**
	 * 转为boolean型(默认值为"")
	 */
	public static boolean castBoolean(Object obj){
		
		return CastUtil.castBoolean(obj, false);
	}
	/**
	 * 转为boolean型(可指定默认值)
	 */
	public static boolean castBoolean(Object obj, boolean defaultValue) {
		boolean booleanVal = defaultValue;
		if(obj != null){
			booleanVal = Boolean.parseBoolean(castString(obj));
		}
		
		return booleanVal;
	}
}
