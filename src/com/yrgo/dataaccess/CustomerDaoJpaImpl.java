package com.yrgo.dataaccess;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomerDaoJpaImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Customer customer) {
        em.persist(customer);
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            throw new RecordNotFoundException();
        }
        return customer;
    }

    @Override
    public List<Customer> getByName(String name) {
        TypedQuery<Customer> query = em.createQuery(
                "SELECT c FROM Customer c WHERE c.companyName = :name", Customer.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        Customer existingCustomer = em.find(Customer.class, customerToUpdate.getCustomerId());
        if (existingCustomer == null) {
            throw new RecordNotFoundException();
        }
        em.merge(customerToUpdate);
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, oldCustomer.getCustomerId());
        if (customer == null) {
            throw new RecordNotFoundException();
        }
        em.remove(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        TypedQuery<Customer> query = em.createQuery(
                "SELECT c FROM Customer c LEFT JOIN FETCH c.calls WHERE c.customerId = :customerId",
                Customer.class);
        query.setParameter("customerId", customerId);

        Customer customer = query.getResultStream().findFirst().orElse(null);
        if (customer == null) {
            throw new RecordNotFoundException();
        }
        return customer;
    }

    @Override
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            throw new RecordNotFoundException();
        }
        customer.addCall(newCall);
    }
}