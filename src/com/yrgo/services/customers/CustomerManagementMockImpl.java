package com.yrgo.services.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {
	private HashMap<String,Customer> customerMap;

	public CustomerManagementMockImpl() {
		customerMap = new HashMap<String,Customer>();
		customerMap.put("OB74", new Customer("OB74" ,"Fargo Ltd", "some notes"));
		customerMap.put("NV10", new Customer("NV10" ,"North Ltd", "some other notes"));
		customerMap.put("RM210", new Customer("RM210" ,"River Ltd", "some more notes"));
	}


	@Override
	public void newCustomer(Customer newCustomer) {
		if (!customerMap.containsKey(newCustomer.getCustomerId())) {
			customerMap.put(newCustomer.getCustomerId(), newCustomer);
		}
	}

	@Override
	public void updateCustomer(Customer changedCustomer) {
		if (customerMap.containsKey(changedCustomer.getCustomerId())) {
			customerMap.put(changedCustomer.getCustomerId(), changedCustomer);
		}
	}

	@Override
	public void deleteCustomer(Customer oldCustomer) {
		customerMap.remove(oldCustomer.getCustomerId());
	}

	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		if (!customerMap.containsKey(customerId)) {
			throw new CustomerNotFoundException();
		}
		return customerMap.get(customerId);
	}

	@Override
	public List<Customer> findCustomersByName(String name) {
		List<Customer> matchingCustomers = new ArrayList<>();

		for (Customer customer : customerMap.values()) {
			if (customer.getCompanyName().equalsIgnoreCase(name)) {
				matchingCustomers.add(customer);
			}
		}

		return matchingCustomers;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return new ArrayList<>(customerMap.values());
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		if (!customerMap.containsKey(customerId)) {
			throw new CustomerNotFoundException();
		}
		return customerMap.get(customerId);
	}

	@Override
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
		//First find the customer
		if (!customerMap.containsKey(customerId)) {
			throw new CustomerNotFoundException();
		}
		//Call the addCall on the customer
		customerMap.get(customerId).addCall(callDetails);
	}

}
