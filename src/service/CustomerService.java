package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private static final CustomerService customerService = new CustomerService();
    private static final Map<String, Customer> customers = new HashMap<>();

    public static CustomerService getInstance() {
        return customerService;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        if (customers.get(email) == null) {
            Customer customer = new Customer(firstName, lastName, email);
            customers.put(email, customer);
        } else {
            throw new IllegalArgumentException("Error: E-mail address already in use!");
        }
    }

    public Customer getCustomer(String customerEmail) {
        Customer customer = customers.get(customerEmail);
        if (customer == null) {
            throw new IllegalArgumentException("Error: Invalid or wrong email! Please try again.");
        }
        return customer;
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
