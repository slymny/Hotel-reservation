package model;

import java.util.regex.Pattern;

public class Customer {
    String firstName;
    String lastName;
    String email;

    public Customer(String firstName, String lastName, String email) {
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);

        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException();
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "First name: " + firstName +
            "   Last name: " + lastName +
            " Email: " + email;
    }
}
