package com.yrgo.integrationtests;

import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration({"/other-tiers.xml", "/datasource-test.xml"})
@Transactional
public class CustomerManagementIntegrationTest {

    @Autowired
    private CustomerManagementService customers;

    @Test
    public void newCustomerTest() {
        Customer testCustomer = new Customer("1", "Dennys Cars", "denny@cars.com",
                "076198765", "Sells and buys cars");

        customers.newCustomer(testCustomer);
        try {
            Customer retrievedCustomer = customers.findCustomerById("1");
            assertEquals(retrievedCustomer.getCustomerId(), testCustomer.getCustomerId());
            assertEquals(retrievedCustomer.getCompanyName(), testCustomer.getCompanyName());
            assertEquals(retrievedCustomer.getEmail(), testCustomer.getEmail());
            assertEquals(retrievedCustomer.getTelephone(), testCustomer.getTelephone());
            assertEquals(retrievedCustomer.getNotes(), testCustomer.getNotes());
        } catch (CustomerNotFoundException e) {
            System.out.println("Could not find customer");
        }
    }

    @Test
    public void findCustomerByIdTest() {
        Customer testCustomer = new Customer("1", "Dennys Cars", "denny@cars.com",
                "076198765", "Sells and buys cars");

        customers.newCustomer(testCustomer);
        try {
            Customer retrievedCustomer = customers.findCustomerById("1");
            assertEquals(retrievedCustomer.getCustomerId(), testCustomer.getCustomerId());
            assertEquals(retrievedCustomer.getCompanyName(), testCustomer.getCompanyName());
            assertEquals(retrievedCustomer.getEmail(), testCustomer.getEmail());
            assertEquals(retrievedCustomer.getTelephone(), testCustomer.getTelephone());
            assertEquals(retrievedCustomer.getNotes(), testCustomer.getNotes());
        } catch (CustomerNotFoundException e) {
            System.out.println("Could not find customer");
        }
    }

}
