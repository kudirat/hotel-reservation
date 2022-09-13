package model;

import java.util.regex.Pattern;

public class Customer {
    public String firstName, lastName, email;
    private final String emailRegex = "^(.+)@(.+).(.+).com$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email){
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Error! Invalid Email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //write getter functions
    public String getEmail(){
        return this.email;
    }

    @Override
    public String toString() {
        return "Customer: " + this.firstName + " " + this.lastName + ", " + "Email: " + this.email;
    }

}
