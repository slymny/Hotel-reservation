package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    public static final CustomerService customerService = new CustomerService();

    private static final Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(String email, String firstName, String lastName) {
        if (customers.get(email) == null) {
            Customer customer = new Customer(firstName, lastName, email);
            customers.put(email, customer);
        } else {
            throw new IllegalArgumentException("Customer is already exist!");
        }
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
