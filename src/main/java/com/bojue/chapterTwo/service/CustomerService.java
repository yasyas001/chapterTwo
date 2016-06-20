package com.bojue.chapterTwo.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bojue.chapterTwo.helper.DatabaseHelper;
import com.bojue.chapterTwo.model.Customer;

public class CustomerService {

	private static Logger log = Logger.getLogger(DatabaseHelper.class);

	/**
	 * 查询所有信息
	 */
	public List<Customer> queryCustomerList(String keyword) {
		String sql = "select * from customer";
		return DatabaseHelper.queryEntityList(Customer.class, sql);
	}

	/**
	 * 新增用户
	 */
	public boolean createrCustomer(Map<String, Object> fieldMap) {
		return DatabaseHelper.insertEntity(Customer.class, fieldMap);
	}

	/**
	 * 更新用户
	 */
	public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
		return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
	}

	/**
	 * 指定用户
	 */
	public Customer getCustomerById(long id) {
		String sql = "select * from customer where id=?";
		return DatabaseHelper.queryEntity(Customer.class, sql, id);
	}

	/**
	 * 删除用户
	 */
	public boolean delCustomerById(long id) {
		return DatabaseHelper.delEntity(Customer.class, id);

	}
}
