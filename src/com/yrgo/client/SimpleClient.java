package com.yrgo.client;

import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SimpleClient {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
        CustomerManagementService customerService = container.getBean("customerManagementService",
                                                                        CustomerManagementService.class);
        List<Customer> customers = customerService.getAllCustomers();
        System.out.println("List of all customers:");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
}
