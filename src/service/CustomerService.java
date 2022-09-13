package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    private static CustomerService instance;

    public static HashMap<String, Customer> mapOfCustomers;

    public CustomerService(){
        this.mapOfCustomers = new HashMap<>();
    }

    public static CustomerService getInstance(){
        if(instance == null){
            instance = new CustomerService();
        }
        return instance;
    }


    public void addCustomer(String email, String firstName, String lastName) {
        Customer newCustomer = new Customer(firstName, lastName, email);
        this.mapOfCustomers.put(email, newCustomer);
        System.out.println(newCustomer);
    }

     public Customer getCustomer(String customerEmail){
         Customer customer = null;
         customer = this.mapOfCustomers.get(customerEmail);
         return customer;
     }

    public Collection<Customer> getAllCustomers(){
        Set<Customer> mySet = new HashSet<>();
        for(Customer customer: mapOfCustomers.values()){
            mySet.add(customer);
        }
        return mySet;
    }

}
