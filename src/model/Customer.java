package model;

import java.util.regex.Pattern;

public class Customer {
    private static final Pattern validEmailPattern = Pattern.compile("^(.+)@(.+).(.+)$");
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) {
        this.emailValidation(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    private void emailValidation(String email) {
        if (!validEmailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Error: Invalid email! Please try again.");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
