package com.stackroute.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.stackroute.bean.Customer;

public class CustomerDAOImpl implements CustomerDAO {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {

		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;
	}


	public int insert(Customer c) {
		int custId = c.getCustomerId();
		String name = c.getCustomerName();
		String address = c.getCustomerAddress();
		int rows = jdbcTemplate.update("insert into customer values(?,?,?)", custId, name, address);

		return rows;
	}

	public int deleteCustomer(int customerId) {
		String query = "DELETE from customer where custid='"+customerId+"'";
		return jdbcTemplate.update(query);

	}


	public void updateCustomer(Customer customer) {
		//String query = "UPDATE customer SET custname=?,city=? WHERE custid=?";
		 jdbcTemplate.update("UPDATE customer SET custname=?,city=? WHERE custid=?",customer.getCustomerName(),customer.getCustomerAddress(),customer.getCustomerId());

	}


	public Customer selectCustomer(int customerId) {
		final Customer customer = new Customer();
		String quer = "SELECT * FROM customer WHERE custid='" + customerId+"'";
		return (Customer) jdbcTemplate.query(quer, new ResultSetExtractor<Customer>() {
			public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					customer.setCustomerId(rs.getInt(1));
					customer.setCustomerName(rs.getString(2));
					customer.setCustomerAddress(rs.getString(3));
				}
				return customer;
			}
		});
	}

}
