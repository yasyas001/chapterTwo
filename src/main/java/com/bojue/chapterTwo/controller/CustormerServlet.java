package com.bojue.chapterTwo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bojue.chapterTwo.model.Customer;
import com.bojue.chapterTwo.service.CustomerService;

@WebServlet("/custormer")
public class CustormerServlet extends HttpServlet {
	
	private CustomerService customerService;
	
	@Override
	public void init() throws ServletException {
		customerService = new CustomerService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Customer> customerList = customerService.queryCustomerList(null);
		req.setAttribute("customerList", customerList);
		req.getRequestDispatcher("/WEB-INF/view/coustomer.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}


}
