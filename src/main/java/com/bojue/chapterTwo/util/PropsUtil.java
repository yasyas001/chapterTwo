package com.bojue.chapterTwo.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropsUtil {
	
	private static final Logger log = Logger.getLogger(PropsUtil.class);
	/**
	 * 加载属性文件
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Properties loadProps(String fileName){
		Properties props = null;
		InputStream inputStream = null;
		try {
			inputStream = PropsUtil.class.getResourceAsStream((fileName.indexOf("/")!=0 ? "/" : "") + fileName);
			if(inputStream == null){
				throw new FileNotFoundException(fileName +" file is not found!");
			}
			props = new Properties();
			props.load(inputStream);
		} catch (IOException e) {
			log.error("load properties file failure", e);
		}finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("close input stream failure", e);
				}
			}
		}
		return props;
	}
	
	/**
	 * 获取字符型属性(默认值为空字符创)
	 * @param props
	 * @param key
	 * @return
	 */
	public static String getString(Properties props, String key){
		return getString(props, key, "");
	}
	
	/**
	 * 获取字符型属性(可指定默认值)
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Properties props, String key, String defaultValue) {
		
		String value = defaultValue;
		if(props.containsKey(key)){
			
			value = props.getProperty(key);
		}
		return value;
	}
	/**
	 * 获取数值型属性(默认值为0)
	 * @param props
	 * @param key
	 * @return
	 */
	public static int getInt(Properties props, String key){
		return getInt(props, key, 0);
	}
	
	/**
	 * 获取数值型属性(可指定默认值)
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Properties props, String key, int defaultValue) {
		
		int value = defaultValue;
		if(props.containsKey(key)){
			
			value = CastUtil.castInt(props.getProperty(key));
		}
		return value;
	}
	/**
	 * 获取布尔型属性（默认值为false）
	 */
	public static boolean getBoolean(Properties props, String key){
		return getBoolean(props, key, false);
	}
	/**
	 * 获取布尔型属性（指定默认值）
	 */
	public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
		boolean value = defaultValue;
		if(props.containsKey(key)){
			value = CastUtil.castBoolean(props.getProperty(key));
		}
		return value;
	}
}