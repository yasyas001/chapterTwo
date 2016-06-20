package com.bojue.chapterTwo.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
//import org.apache.log4j.Logger;

import com.bojue.chapterTwo.util.CollectionUtil;
import com.bojue.chapterTwo.util.PropsUtil;

public class DatabaseHelper {

//	private static Logger log = Logger.getLogger(DatabaseHelper.class);

	private static final QueryRunner QUERY_RUNNER;

	private static final ThreadLocal<Connection> CONNECTION_HOLDER;
	
	private static final BasicDataSource DATA_SOURCE;
	
	static {
		CONNECTION_HOLDER = new ThreadLocal<Connection>();
		QUERY_RUNNER = new QueryRunner();
		
		Properties conf = PropsUtil.loadProps("config.properties");
		String driver = conf.getProperty("jdbc.driver");
		String url = conf.getProperty("jdbc.url");
		String username = conf.getProperty("jdbc.username");
		String password = conf.getProperty("jdbc.password");
		
		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(driver);
		DATA_SOURCE.setUrl(url);
		DATA_SOURCE.setUsername(username);
		DATA_SOURCE.setPassword(password);
	}

	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql,
			Object... params) {

		List<T> entityList;
		try {
			Connection conn = getConnection();
			entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(
					entityClass), params);
		} catch (Exception e) {
//			log.error("query entity list failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}

		return entityList;
	}

	public static <T> T queryEntity(Class<T> entityClass, String sql,
			Object... params) {

		T entity;
		try {
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(
					entityClass), params);
		} catch (Exception e) {
//			log.error("query entity failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}

		return entity;
	}

	public static List<Map<String, Object>> executeQuery(String sql,
			Object... params) {
		List<Map<String, Object>> result = null;
		try {
			Connection conn = getConnection();
			result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
		} catch (Exception e) {
//			log.error("execute query failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return result;
	}

	public static int executeUpdate(String sql, Object... params) {
		
		int rows = 0;
		try {
			Connection conn = getConnection();
			rows = QUERY_RUNNER.update(conn, sql, params);
		} catch (Exception e) {
//			log.error("execute update failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return rows;
		
	}
	
	public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
		
		if(CollectionUtil.isEmpty(fieldMap)){
//			log.error("can not update entity: fieldMap is empty");
			return false;
		}
		StringBuilder columns = new StringBuilder();
		for (String fieldName : fieldMap.keySet()) {
			columns.append(fieldName).append("=?, ");
		}
		String sql = "update " + getTableName(entityClass) + " set ";
		sql += columns.substring(0, columns.lastIndexOf(", ")) + " where id=?";
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.addAll(fieldMap.values());
		paramsList.add(id);
		return executeUpdate(sql, paramsList.toArray()) == 1;
		
	}
	
	public static <T> boolean insertEntity(Class<T> entityClass, Map<String,Object> fieldMap){
		if(CollectionUtil.isEmpty(fieldMap)){
//			log.error("can not update entity: fieldMap is empty");
			return false;
		}
		StringBuilder columns = new StringBuilder();
		columns.append("(");
		StringBuilder values = new StringBuilder();
		values.append("(");
		Iterator<String> iterator = fieldMap.keySet().iterator();
		while (iterator.hasNext()) {
			String fieldName = (String) iterator.next();
			columns.append(fieldName).append(", ");
			values.append("?, ");
		}
		columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
		values.replace(values.lastIndexOf(", "), values.length(), ")");
		String sql = "insert into " + getTableName(entityClass) + columns + " values "+ values;
		return executeUpdate(sql, fieldMap.values().toArray()) == 1;
	}
	
	public static <T> boolean delEntity(Class<T> entityClass, long id){
		String sql = "delete from " + getTableName(entityClass) + " where id=? ";
		return executeUpdate(sql, id) == 1;
	}
	
	public static String getTableName(Class<?> entityClass) {
		return entityClass.getSimpleName();
	}
	public static Connection getConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if (conn == null) {
			try {
				conn = DATA_SOURCE.getConnection();
			} catch (Exception e) {
//				log.error("execute sql failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
		return conn;
	}

	public static void closeConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
//				log.error("close connection failure", e);
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}

	public static void executeSqlFile(String filePath) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try {
			String sql;
			while ((sql = reader.readLine()) != null) {
				executeUpdate(sql);
			}
		} catch (Exception e) {
//			log.error("execute sql file failure", e);
			throw new RuntimeException(e);
		}
	}
}
