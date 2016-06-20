package com.bojue.chapterTwo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bojue.chapterTwo.helper.DatabaseHelper;
import com.bojue.chapterTwo.model.Customer;
import com.bojue.chapterTwo.service.CustomerService;

public class CustomerServiceTest {

	private final CustomerService customerService;

	public CustomerServiceTest() {
		customerService = new CustomerService();
	}

	@Before
	public void init() {
		// TODO 数据库初始化
		DatabaseHelper.executeSqlFile("sql/customer_init.sql");
	}

	@After
	public void clear() {
		// 关闭数据库
	}

	@Test
	public void queryCoustomerList() {
		List<Customer> customerList = customerService.queryCustomerList(null);
		Assert.assertEquals(2, customerList.size());
	}

	@Test
	public void createCoustomer() {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		fieldMap.put("name", "cutomer3");
		fieldMap.put("contact", "John");
		fieldMap.put("telephone", "1212131");
		boolean b = customerService.createrCustomer(fieldMap);
		Assert.assertTrue(b);
	}

	@Test
	public void updateCoustomer() {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		fieldMap.put("name", "cutomer0");
		fieldMap.put("contact", "John111");
		fieldMap.put("telephone", "1212143431");
		boolean b = customerService.updateCustomer(1l, fieldMap);
		Assert.assertTrue(b);
	}

	@Test
	public void getCoustomerById() {
		Customer customer = customerService.getCustomerById(1l);
		Assert.assertNotNull(customer);
	}

	@Test
	public void delCoustomer() {
		boolean b = customerService.delCustomerById(1l);
		Assert.assertTrue(b);
	}
}
